package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.scenery.Scenery;
import junit.framework.Assert;

import java.awt.Point;

import static junit.framework.Assert.assertEquals;

/**
 * Utility class to test MapManager
 */
class HeroMover {
    private Hero hero;
    private MapManager manager;
    private int stepSize = 0;
    private Asserter asserter;
    private final Point topRight;
    private final Point bottomLeft;
    private final Point topLeft;
    private final Point bottomRight;

    public void setLocation(int x, int y) {
        hero.setLocation(new Point(x * stepSize, y * stepSize));
    }

    public void toBottomLeft() {
        hero.setLocation(bottomLeft);
    }

    public void toTopLeft() {
        hero.setLocation(topLeft);
    }

    public void toBottomRight() {
        hero.setLocation(bottomRight);
    }
    
    public void toTopRight() {
        hero.setLocation(topRight);
    }


    public class Asserter {
        public void at(int x, int y) {
            assertEquals(new Point(x * stepSize, y * stepSize), hero.getLocation());
        }

        public void atBottomLeft() {
            assertEquals(bottomLeft, hero.getLocation());
        }

        public void atTopLeft() {
            assertEquals(topLeft, hero.getLocation());
        }

        public void atTopRight() {
            assertEquals(topRight, hero.getLocation());
        }

        public void atBottomRight() {
            assertEquals(bottomRight, hero.getLocation());
        }

    }

    HeroMover(MapManager manager, int stepSize) {
        this.hero = manager.getHero();
        this.manager = manager;
        this.stepSize = stepSize;
        this.asserter = new Asserter();

        Scenery scenery = manager.getActiveScenery();
        int w = (scenery.getWidth() / stepSize);
        int h = (scenery.getHeight() / stepSize);
        topLeft = new Point(0, 0);
        topRight = new Point((w - 1) * this.stepSize, 0);
        bottomLeft = new Point(0, (h - 1) * this.stepSize);
        bottomRight = new Point((w - 1) * this.stepSize, (h - 1) * this.stepSize);
    }


    public Asserter assertLocation() {
        return asserter;
    }

    public void right() {
        right(1);
    }

    public void right(int steps) {
        hero.setSpeed(steps * stepSize, 0);
        manager.update(System.currentTimeMillis());
    }

    public void left() {
        left(1);
    }

    public void left(int steps) {
        hero.setSpeed(-steps * stepSize, 0);
        manager.update(System.currentTimeMillis());
    }

    public void up() {
        up(1);
    }

    public void up(int steps) {
        hero.setSpeed(0, -steps * stepSize);
        manager.update(System.currentTimeMillis());
    }

    public void down() {
        down(1);
    }

    public void down(int steps) {
        hero.setSpeed(0, steps * stepSize);
        manager.update(System.currentTimeMillis());
    }

    public void topLeft() {
        hero.setSpeed(-stepSize, -stepSize);
        manager.update(System.currentTimeMillis());
    }

    public void topRight() {
        hero.setSpeed(stepSize, -stepSize);
        manager.update(System.currentTimeMillis());
    }

    public void bottomLeft() {
        hero.setSpeed(-stepSize, stepSize);
        manager.update(System.currentTimeMillis());
    }

    public void bottomRight() {
        hero.setSpeed(stepSize, stepSize);
        manager.update(System.currentTimeMillis());
    }


}
