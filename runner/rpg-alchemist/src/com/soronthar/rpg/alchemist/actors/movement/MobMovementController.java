package com.soronthar.rpg.alchemist.actors.movement;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.alchemist.actors.Mob;

public class MobMovementController implements MovementController {
    public static final int STEP = Tile.TILE_SIZE / 4;
    public static final double DELAY = 0.1;

    protected Actor actor;
    public int facing = 0;//TODO: make FACING a constant. Using enums reduce the FPS
    public int steps = 0;
    int dx;
    int dy;
    float tick;

    public MobMovementController(Mob mob) {
        this.actor = mob;
        dy=-STEP;
    }

    @Override
    public void move(float delta) {
        if (tick > DELAY) {

            if (dx > 0) {
                facing = 3;
            } else if (dx < 0) {
                facing = 2;
            } else if (dy < 0) {
                facing = 1;
            } else if (dy > 0) {
                facing = 0;
            }

            float newX = actor.x+dx;
            float newY = actor.y+dy;

            Actor hit = actor.getStage().hit(newX, newY);
            if (hit != null && !hit.equals(actor)) {
                handleCollision(newX, newY);
            } else {
                actor.x = newX;
                actor.y = newY;
            }
            steps++;


            tick = 0;
        } else {
            tick += delta;
        }
    }

    protected void handleCollision(float newX, float newY) {
        switch (facing) {
            case 0:
                goLeft();
                break;
            case 1:
                goRight();
                break;
            case 2:
                goDown();
                break;
            case 3:
                goUp();
                break;
        }
    }

    private void goUp() {
        this.dx=0;
        this.dy=STEP;
    }

    private void goDown() {
        this.dx=0;
        this.dy=-STEP;
    }

    private void goRight() {
        this.dy=0;
        this.dx=STEP;
    }

    private void goLeft() {
        this.dy=0;
        this.dx=-STEP;
    }

    @Override
    public boolean isMoving() {
        return !(dx == 0 && dy == 0);
    }

    @Override
    public int getSteps() {
        return steps;
    }

    @Override
    public int getFacing() {
        return facing;
    }

    @Override
    public void dispose() {
        this.actor=null;
    }
}
