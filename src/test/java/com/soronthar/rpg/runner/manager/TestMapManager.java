package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.project.ProjectPersister;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import junit.framework.TestCase;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Collection;

public class TestMapManager extends TestCase {
    private static final int STEP_SIZE = Tile.TILE_SIZE; //Move tile to tile. It is easier to follow the test.

    public void test() throws URISyntaxException {
        ProjectPersister persister = new ProjectPersister();
        File file = new File(this.getClass().getResource("/MapManagerTest.xml").toURI());
        Project project = persister.load(file.getAbsolutePath());
        MapManager manager = new MapManager(project.getSceneries());
        SceneryChangeListener listener = new SceneryChangeListener();
        manager.addSceneryListener(listener);
        assertNull(manager.getActiveScenery());
        assertEquals(0, listener.getTimesCalled());
        assertNull(listener.getLastNewValue());

        manager.init();
        Scenery scenery = manager.getActiveScenery();

        assertEquals(1, listener.getTimesCalled());
        assertEquals(scenery, listener.getLastNewValue());


        assertEquals("First", scenery.getName());
        Hero hero = manager.getHero();
        assertEquals(scenery.getHeroStartingPoint(), hero.getLocation());
        assertEquals(new Point(0, 0), hero.getLocation());  //This is just to document the fact
        // that the hero starts at 0,0

        Collection<Point> obstacles = scenery.getObstacles();
        for (Point point : obstacles) {
            assertTrue(manager.solidItems.haveSolidSpritesAt(point));
        }

        hero.setSpeed(STEP_SIZE, 0); //move to the right
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(STEP_SIZE, 0), hero.getLocation());

        hero.setSpeed(8 * STEP_SIZE, 0); //move to the right, bigger speed
        manager.update(System.currentTimeMillis()); //Hero should be positioned at the edge of the scenery
        assertEquals(new Point(9 * STEP_SIZE, 0), hero.getLocation());

        manager.update(System.currentTimeMillis());
        assertEquals(new Point(9 * STEP_SIZE, 0), hero.getLocation()); //at the right edge, so don't move


        hero.setSpeed(0, -STEP_SIZE);
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(9 * STEP_SIZE, 0), hero.getLocation()); //at the top edge, so don't move

        hero.setSpeed(-10 * STEP_SIZE, 0); //move to the top left edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 0), hero.getLocation());

        hero.setSpeed(-10 * STEP_SIZE, 0); //try move beyond the left edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 0), hero.getLocation());

        hero.setSpeed(0, -10 * STEP_SIZE); //try move beyond the top edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 0), hero.getLocation());

        hero.setSpeed(-STEP_SIZE, -STEP_SIZE); //try move beyond the top left edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 0), hero.getLocation());


        hero.setSpeed(0, 10 * STEP_SIZE);
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 128), hero.getLocation());

        hero.setSpeed(-STEP_SIZE, 0); //try to move beyond the left edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 128), hero.getLocation());

        hero.setSpeed(0, STEP_SIZE); //try to move beyond the bottom edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 128), hero.getLocation());

        hero.setSpeed(STEP_SIZE, STEP_SIZE); //try to move beyond the bottom-left corner
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(0, 128), hero.getLocation());


        hero.setSpeed(10 * STEP_SIZE, 0); //move to the bottom-right edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(288, 128), hero.getLocation());

        hero.setSpeed(STEP_SIZE, 0); //try to move beyond the right edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(288, 128), hero.getLocation());

        hero.setSpeed(0, STEP_SIZE); //try to move beyond the bottom edge
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(288, 128), hero.getLocation());

        hero.setSpeed(STEP_SIZE, STEP_SIZE); //try to move beyond the bottom-right corner
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(288, 128), hero.getLocation());

        //move below the the obstacle at <obstacle x="160" y="64"/>
        hero.setSpeed(-4 * STEP_SIZE, 0);
        manager.update(System.currentTimeMillis());
        hero.setSpeed(0, -STEP_SIZE);
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(160, 96), hero.getLocation());

        hero.setSpeed(0, -STEP_SIZE); //try to go up, and fail
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(160, 96), hero.getLocation());

        //move to the left side of the obstacle
        hero.setSpeed(-STEP_SIZE, 0);
        manager.update(System.currentTimeMillis());
        hero.setSpeed(0, -STEP_SIZE);
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(128, 64), hero.getLocation());

        hero.setSpeed(STEP_SIZE, 0); //try to go right, and fail
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(128, 64), hero.getLocation());

        //move to the top side of the obstacle
        hero.setSpeed(0, -STEP_SIZE);
        manager.update(System.currentTimeMillis());
        hero.setSpeed(STEP_SIZE, 0);
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(160, 32), hero.getLocation());

        hero.setSpeed(0, STEP_SIZE); //try to go down, and fail
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(160, 32), hero.getLocation());

        //move to the right side of the obstacle
        hero.setSpeed(STEP_SIZE, 0);
        manager.update(System.currentTimeMillis());
        hero.setSpeed(0, STEP_SIZE);
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(192, 64), hero.getLocation());

        hero.setSpeed(-STEP_SIZE, 0); //try to go left, and fail
        manager.update(System.currentTimeMillis());
        assertEquals(new Point(192, 64), hero.getLocation());


        //move the to jump point at <jumpPoint x="288" y="96" target="Second"/>
        hero.setSpeed(0, STEP_SIZE);
        manager.update(System.currentTimeMillis());
        hero.setSpeed(4 * STEP_SIZE, 0);
        manager.update(System.currentTimeMillis());

        assertEquals(2, listener.getTimesCalled());
        Scenery secondScenery = project.getScenery(2);
        Hero secondHero = manager.getHero();
        assertNotSame(hero, secondHero);
        assertEquals(secondScenery, manager.getActiveScenery());
        assertEquals(secondScenery, listener.getLastNewValue());
        assertEquals(secondScenery.getHeroStartingPoint(), secondHero.getLocation());
    }

    private class SceneryChangeListener implements PropertyChangeListener {
        int timesCalled;
        Scenery lastNewValue;

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            timesCalled++;
            lastNewValue = (Scenery) evt.getNewValue();
        }

        public int getTimesCalled() {
            return timesCalled;
        }

        public Scenery getLastNewValue() {
            return lastNewValue;
        }
    }

}
