package com.soronthar.rpg.model.objects.sprites;

import java.awt.*;

public class UnmoveableSprite extends Sprite {
    public UnmoveableSprite(Point p) {
        super(p);
    }

    public UnmoveableSprite(Point p, Facing facing) {
        this(p);
        this.facing = facing;
    }

    @Override
    public void update(long elapsedTime) {
    }

    @Override
    public Image getFrame() {
        return null;
    }

}
