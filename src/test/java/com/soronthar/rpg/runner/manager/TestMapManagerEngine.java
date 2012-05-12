package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;
import java.util.Collection;

/**
 * All the test of movements beyond the borders are using 1 and 2 steps to guarantee that no matter the speed
 * the hero is confined inside the boundaries
 */
public class TestMapManagerEngine extends BaseMapManagerTest {

    /**
     * There are some subtle movement bugs that may creep in when the step size is a fraction of a tile
     * When reading this test, bear in mind that each tile now need two steps. This means that the
     * tile (2,4) is in the location (4,8).
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

        Scenery scenery = manager.getActiveScenery();

        Collection<Point> obstacles = scenery.getObstacles();
        for (Point point : obstacles) {
            assertTrue(manager.specialsPerPoint.haveSolidAt(point));
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


    public void testSolidSprites() {
        int stepSize = Tile.TILE_SIZE; //Move tile to tile. It is easier to follow the test.
        MapManager manager = createMapManager();
        manager.init();
        HeroMover mover = new HeroMover(manager, stepSize);

        assertTrue(manager.specialsPerPoint.haveSolidAt(new Point(64, 64)));//64,64 => 2,2

        mover.setLocation(1, 2);
        mover.right();
        mover.assertLocation().at(1, 2);

        mover.setLocation(2, 1);
        mover.down();
        mover.assertLocation().at(2, 1);

        mover.setLocation(2, 3);
        mover.up();
        mover.assertLocation().at(2, 3);

        mover.setLocation(3, 2);
        mover.left();
        mover.assertLocation().at(3, 2);
    }


    public void testCollision() {
        MapManager manager = createMapManager();
        manager.init();
        manager.setActiveScenery(manager.getScenery(2));

        Sprite solidA = manager.getActiveScenery().getSpriteMap().get("solidA");
        solidA.setSpeed(Tile.TILE_SIZE, 0);
        assertEquals(new Point(32, 64), solidA.getLocation());

        Sprite nonsolidA = manager.getActiveScenery().getSpriteMap().get("nonsolidA");
        nonsolidA.setSpeed(0, Tile.TILE_SIZE);
        assertEquals(new Point(64, 32), nonsolidA.getLocation());

        Sprite solidB = manager.getActiveScenery().getSpriteMap().get("solidB");
        assertEquals(new Point(64, 64), solidB.getLocation());

        manager.update(System.currentTimeMillis());
        assertEquals(new Point(32, 64), solidA.getLocation());
        assertEquals(new Point(64, 64), nonsolidA.getLocation());
        assertEquals(new Point(64, 64), solidB.getLocation());


    }

    public void testHeroLocationIsCloned() {
        Hero hero = new Hero(new Point(0, 0));
        Point location = new Point(1, 2);
        hero.setLocation(location);
        assertNotSame(location, hero.getLocation());

        location.translate(5, 5);
        assertEquals(new Point(6, 7), location);
        assertEquals(new Point(1, 2), hero.getLocation());
    }


}
