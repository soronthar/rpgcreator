package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;

public class test extends Sprite {
    private static final int STEP_SIZE = Tile.TILE_SIZE / 2;

    public test(String id, Point location, Facing facing) {
        super(id, location);
        setFrameStrategy(new WholeImageFrameStrategy("miscsprite.png"));
        switch (facing) {
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



    @Override
    public void handleCollitionAt(Point tileLocation) {
        switch (getFacing()) {
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
        Point location = this.getLocation();
        if (location.y==0 && getFacing()==Facing.UP) {
            goLeft();
        } else if (location.y==bounds.height && getFacing()==Facing.DOWN) {
            goRight();
        } else if (location.x==bounds.width && getFacing()==Facing.RIGHT) {
            goUp();
        } else if (location.x==0 && getFacing()==Facing.LEFT) {
            goDown();
        }

    }

    private void goUp() {
        setSpeed(0, -STEP_SIZE);
    }

    private void goDown() {
        setSpeed(0, STEP_SIZE);
    }

    private void goLeft() {
        setSpeed(-STEP_SIZE, 0);
    }

    private void goRight() {
        setSpeed(STEP_SIZE, 0);
    }

}
