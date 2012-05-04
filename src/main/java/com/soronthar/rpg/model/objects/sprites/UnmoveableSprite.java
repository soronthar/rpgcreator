package com.soronthar.rpg.model.objects.sprites;

import java.awt.*;

public class UnmoveableSprite extends Sprite {
    public UnmoveableSprite(String id, Point p) {
        super(id, p);
    }

    public UnmoveableSprite(String id, Point p, Facing facing) {
        super(id, p, facing);
    }

    @Override
    public void update(long elapsedTime) {
    }

    @Override
    public Image getFrame() {
        return null;
    }

}
