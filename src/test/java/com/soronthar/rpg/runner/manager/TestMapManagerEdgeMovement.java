package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.Point;
import java.net.URISyntaxException;

public class TestMapManagerEdgeMovement extends BaseMapManagerTest {

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


}
