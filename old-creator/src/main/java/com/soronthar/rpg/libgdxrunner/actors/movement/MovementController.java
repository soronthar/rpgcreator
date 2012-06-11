package com.soronthar.rpg.libgdxrunner.actors.movement;

/**
 * Created by IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 6/5/12
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MovementController {
    void move(float delta);

    boolean isMoving();

    int getSteps();

    int getFacing();

    void dispose();
}
