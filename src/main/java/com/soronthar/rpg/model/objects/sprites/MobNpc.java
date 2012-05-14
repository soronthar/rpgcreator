package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.movement.DefaultMovementStrategy;

import java.awt.*;

public class MobNpc extends Sprite {

    public MobNpc(String id, Point location, Facing facing) {
        super(id, location, facing);
        setFramesImage("cat.png");  //TODO: this should NOT be here
        setMovementStrategy(new DefaultMovementStrategy(this));
        getMovementStrategy().init();
    }
}
