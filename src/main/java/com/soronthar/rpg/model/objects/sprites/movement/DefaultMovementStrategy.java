package com.soronthar.rpg.model.objects.sprites.movement;

import com.soronthar.rpg.model.objects.sprites.Facing;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;

//TODO: create a test for this.. and all the other strategies
public class DefaultMovementStrategy implements MovementStrategy {
    public static final int STEP_SIZE = Tile.TILE_SIZE / 2;
    private Sprite sprite;

    public DefaultMovementStrategy(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void init() {
        switch (sprite.getFacing()) {
            case UP:
                goUp();
                break;
            case DOWN:
                goDown();
                break;
            case LEFT:
                goLeft();
                break;
            case RIGHT:
                goRight();
                break;
        }
    }

    private void goUp() {
        sprite.setSpeed(0, -STEP_SIZE);
    }

    private void goDown() {
        sprite.setSpeed(0, STEP_SIZE);
    }

    private void goLeft() {
        sprite.setSpeed(-STEP_SIZE, 0);
    }

    private void goRight() {
        sprite.setSpeed(STEP_SIZE, 0);
    }

    @Override
    public void handleCollisionAt(Point tileLocation) {
        switch (sprite.getFacing()) {
            case UP:
                goLeft();
                break;
            case DOWN:
                goRight();
                break;
            case LEFT:
                goDown();
                break;
            case RIGHT:
                goUp();
                break;
        }
    }

    @Override
    public void handleAtEdge(Rectangle bounds) {
        Point location = sprite.getLocation();
        Facing facing = sprite.getFacing();
        if (location.y == 0 && facing == Facing.UP) {
            goLeft();
        } else if (location.y == bounds.height && facing == Facing.DOWN) {
            goRight();
        } else if (location.x == bounds.width && facing == Facing.RIGHT) {
            goUp();
        } else if (location.x == 0 && facing == Facing.LEFT) {
            goDown();
        }

    }
}
