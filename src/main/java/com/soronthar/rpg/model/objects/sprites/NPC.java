package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;

public class NPC extends Sprite {
    private static final int STEP_SIZE = Tile.TILE_SIZE / 2;

    public NPC(String id, Point location, Facing facing) {
        super(id, location, facing);
        setFrameStrategy(new WholeImageFrameStrategy("miscsprite.png"));
        switch (facing) {
            case UP:
                setSpeed(0, -STEP_SIZE);
                break;
            case DOWN:
                setSpeed(0, STEP_SIZE);
                break;
            case LEFT:
                setSpeed(-STEP_SIZE, 0);
                break;
            case RIGHT:
                setSpeed(STEP_SIZE, 0);
                break;
        }
    }

    @Override
    public void handleCollitionAt(Point tileLocation) {
        switch (getFacing()) {
            case UP:
                setSpeed(STEP_SIZE, 0);
                break;
            case DOWN:
                setSpeed(-STEP_SIZE, 0);
                break;
            case LEFT:
                setSpeed(0, -STEP_SIZE);
                break;
            case RIGHT:
                setSpeed(0, STEP_SIZE);
                break;
        }
    }
}
