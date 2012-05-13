package com.soronthar.rpg.model.objects.sprites.movement;

import com.soronthar.rpg.model.objects.sprites.Facing;
import com.soronthar.rpg.model.objects.sprites.Sprite;

import java.awt.*;

public class BaseMovementStrategy implements MovementStrategy {
    protected Sprite sprite;
    private int dx;
    private int dy;

    public BaseMovementStrategy(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void init() {
    }

    @Override
    public void setSpeed(int dx, int dy) {
        if (sprite.getFacing() == Facing.UP || sprite.getFacing() == Facing.DOWN) {
            if (dy != 0) {
                this.dy = dy;
                this.dx = 0;
            } else {
                this.dy = 0;
                this.dx = dx;
            }
        }


        if (sprite.getFacing() == Facing.LEFT || sprite.getFacing() == Facing.RIGHT) {
            if (dx != 0) {
                this.dx = dx;
                this.dy = 0;
            } else {
                this.dx = 0;
                this.dy = dy;
            }
        }
    }

    @Override
    public int getDy() {
        return dy;
    }

    @Override
    public int getDx() {
        return dx;
    }

    @Override
    public boolean isMoving() {
        return dx != 0 || dy != 0;
    }

    @Override
    public void handleCollisionAt(Point tileLocation) {
    }

    @Override
    public void handleAtEdge(Rectangle bounds) {
    }
}
