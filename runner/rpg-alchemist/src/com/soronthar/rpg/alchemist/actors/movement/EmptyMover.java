package com.soronthar.rpg.alchemist.actors.movement;

public class EmptyMover implements MovementController {
    @Override
    public void move(float delta) {
    }

    @Override
    public boolean isMoving() {
        return false;
    }

    @Override
    public int getSteps() {
        return 0;
    }

    @Override
    public int getFacing() {
        return 1;
    }

    @Override
    public void dispose() {

    }
}
