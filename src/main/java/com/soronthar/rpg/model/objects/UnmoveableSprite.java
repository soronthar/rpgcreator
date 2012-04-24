package com.soronthar.rpg.model.objects;

import java.awt.*;

public class UnmoveableSprite extends Sprite {
    public UnmoveableSprite(Point p) {
        this.setLocation(p);
    }

    public UnmoveableSprite(Point p, Facing facing) {
        this.setLocation(p);
        this.facing = facing;
    }

    @Override
    public void update(long elapsedTime) {
    }

    @Override
    public Image getFrame() {
        return null;
    }

    protected void move() {

    }
}
