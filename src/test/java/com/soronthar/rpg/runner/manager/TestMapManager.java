package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.project.ProjectPersister;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import junit.framework.TestCase;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Collection;

/**
 * All the test of movements beyond the borders are using 1 and 2 steps to guarantee that no matter the speed
 * the hero is confined inside the boundaries
 */
public class TestMapManager extends TestCase {

    /**
     * There are some subtle movement bugs that may creep in when the step size is a fraction of a tile
     * When reading this test, bear in mind that each tile now need two steps. This means that the
     * tile (2,4) is in the location (4,8).
     *
     */
    public void testEdgeMovementWithSmallSteps() {
        int stepSize = Tile.TILE_SIZE / 2;
        MapManager manager = initManager();
        HeroMover mover = new HeroMover(manager, stepSize);
        mover.up();
        mover.assertLocation().at(0, 0);
        mover.left();
        mover.assertLocation().at(0, 0);

        mover.setLocation(0, 8);
        mover.down();
        mover.assertLocation().at(0, 8);
        mover.left();
        mover.assertLocation().at(0, 8);

        mover.setLocation(18, 8);
        mover.down();
        mover.assertLocation().at(18, 8);
        mover.right();
        mover.assertLocation().at(18, 8);


        mover.setLocation(18, 0);
        mover.up();
        mover.assertLocation().at(18, 0);
        mover.right();
        mover.assertLocation().at(18, 0);
    }


    /**
     * Tests that the hero can move around in one or more steps at a time.
     */
    public void testSimpleMovement() {
        MapManager manager = initManager();
        int stepSize = Tile.TILE_SIZE; //Move tile to tile. It is easier to follow the test.
        HeroMover mover = new HeroMover(manager, stepSize);

        mover.assertLocation().at(0, 0);
        mover.right();
        mover.assertLocation().at(1, 0);
        mover.right(2);
        mover.assertLocation().at(3, 0);
        mover.down();
        mover.assertLocation().at(3, 1);
        mover.down(2);
        mover.assertLocation().at(3, 3);
        mover.left();
        mover.assertLocation().at(2, 3);
        mover.left(2);
        mover.assertLocation().at(0, 3);
        mover.up();
        mover.assertLocation().at(0, 2);
        mover.up(2);
        mover.assertLocation().at(0, 0);
    }



    public void testJumpPoints() {
        MapManager manager = createMapManager();

        SceneryChangeListener listener = new SceneryChangeListener();
        manager.addSceneryListener(listener);
        assertEquals(0, listener.getTimesCalled());
        assertNull(listener.getLastNewValue());

        manager.init();
        Hero hero = manager.getHero();

        int stepSize = Tile.TILE_SIZE; //Move tile to tile. It is easier to follow the test.
        HeroMover mover = new HeroMover(manager, stepSize);

        assertEquals(1, listener.getTimesCalled());
        assertEquals(manager.getActiveScenery(), listener.getLastNewValue());

        //move above the Jumppoint at (9,3) <jumpPoint x="288" y="96" target="Second"/> (
        mover.setLocation(9, 2);
        mover.down();

        assertEquals(2, listener.getTimesCalled());
        Scenery secondScenery = manager.getScenery(2);
        Hero secondHero = manager.getHero();
        assertNotSame(hero, secondHero);
        assertEquals(secondScenery, manager.getActiveScenery());
        assertEquals(secondScenery, listener.getLastNewValue());
        assertEquals(secondScenery.getHeroStartingPoint(), secondHero.getLocation());
    }


    public void testObstacles() {
        int stepSize = Tile.TILE_SIZE; //Move tile to tile. It is easier to follow the test.
        MapManager manager = createMapManager();
        manager.init();
        HeroMover mover = new HeroMover(manager, stepSize);

        Scenery scenery= manager.getActiveScenery();

        Collection<Point> obstacles = scenery.getObstacles();
        for (Point point : obstacles) {
            assertTrue(manager.solidItems.haveSolidSpritesAt(point));
        }

        //move below the obstacle at (5,2)
        mover.setLocation(5, 3);
        mover.up();
        mover.assertLocation().at(5, 3);

        //move to the left side of the obstacle
        mover.setLocation(4, 2);
        mover.right();
        mover.assertLocation().at(4, 2);

        //move to the top side of the obstacle
        mover.setLocation(5, 1);
        mover.down();
        mover.assertLocation().at(5, 1);

        //move to the right side of the obstacle
        mover.setLocation(6, 2);
        mover.left();
        mover.assertLocation().at(6, 2);
    }

    public void testHeroLocationIsCloned() {
        Hero hero=new Hero(new Point(0,0));
        Point location=new Point(1,2);
        hero.setLocation(location);
        assertNotSame(location,hero.getLocation());

        location.translate(5,5);
        assertEquals(new Point(6,7),location);
        assertEquals(new Point(1,2),hero.getLocation());
    }

    public void testBoundaries() throws URISyntaxException {
        int stepSize = Tile.TILE_SIZE; //Move tile to tile. It is easier to follow the test.
        MapManager manager = createMapManager();
        manager.init();
        Scenery scenery= manager.getActiveScenery();
        assertEquals("First", scenery.getName());
        Hero hero = manager.getHero();

        assertEquals(scenery.getHeroStartingPoint(), hero.getLocation());
        assertEquals(new Point(0, 0), hero.getLocation()); //This is just to document the fact that the hero starts at 0,0

        //The scenery is a 10x5 grid
        assertEquals(10, scenery.getWidth() / Tile.TILE_SIZE);
        assertEquals(5, scenery.getHeight() / Tile.TILE_SIZE);

        HeroMover mover = new HeroMover(manager, stepSize);

        assertCannotMoveBeyondLeft(mover, 0, 0);
        assertCannotMoveBeyondTop(mover, 0, 0);
        assertCannotMoveBeyondTopLeft(mover);

        mover.toTopRight();
        assertCannotMoveBeyondRight(mover, 9, 0);
        assertCannotMoveBeyondTop(mover, 9, 0);
        assertCannotMoveBeyondTopRight(mover);

        mover.toBottomLeft();
        assertCannotMoveBeyondLeft(mover, 0, 4);
        assertCannotMoveBeyondBottom(mover, 0, 4);
        assertCannotMoveBeyondBottomLeft(mover);

        mover.toBottomRight();
        assertCannotMoveBeyondRight(mover, 9, 4);
        assertCannotMoveBeyondBottom(mover, 9, 4);
        assertCannotMoveBeyondBottomRight(mover);
    }

    private void assertCannotMoveBeyondBottomLeft(HeroMover mover) {
        mover.bottomLeft();
        mover.assertLocation().atBottomLeft();
    }

    private void assertCannotMoveBeyondBottomRight(HeroMover mover) {
        mover.bottomRight();
        mover.assertLocation().atBottomRight();
    }

    private void assertCannotMoveBeyondTopLeft(HeroMover mover) {
        mover.topLeft();
        mover.assertLocation().atTopLeft();
    }

    private void assertCannotMoveBeyondTopRight(HeroMover mover) {
        mover.topRight();
        mover.assertLocation().atTopRight();
    }

    private void assertCannotMoveBeyondTop(HeroMover mover, int x, int y) {
        mover.up();
        mover.assertLocation().at(x, y);

        mover.up(2);
        mover.assertLocation().at(x, y);
    }

    private void assertCannotMoveBeyondBottom(HeroMover mover, int x, int y) {
        mover.down();
        mover.assertLocation().at(x, y);

        mover.down(2);
        mover.assertLocation().at(x, y);
    }

    private void assertCannotMoveBeyondRight(HeroMover mover, int x, int y) {
        mover.right();
        mover.assertLocation().at(x, y);

        mover.right(2);
        mover.assertLocation().at(x, y);
    }

    private void assertCannotMoveBeyondLeft(HeroMover mover, int x, int y) {
        mover.left();
        mover.assertLocation().at(x, y);

        mover.left(2);
        mover.assertLocation().at(x, y);
    }



    private MapManager initManager() {
        MapManager manager = createMapManager();
        assertNull(manager.getActiveScenery());
        manager.init();
        return manager;
    }

    private MapManager createMapManager() {
        try {
            ProjectPersister persister = new ProjectPersister();
            File file = new File(this.getClass().getResource("/MapManagerTest.xml").toURI());
            Project project = persister.load(file.getAbsolutePath());
            return new MapManager(project.getSceneries());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}
