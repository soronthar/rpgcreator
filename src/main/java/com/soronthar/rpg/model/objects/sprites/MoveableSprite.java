package com.soronthar.rpg.model.objects.sprites;

import java.awt.*;

public class MoveableSprite extends Sprite {

    public MoveableSprite(String id, Point location) {
        super(id, location);
    }

    public MoveableSprite(String id, Point location, Facing facing) {
        super(id, location, facing);
    }


}
