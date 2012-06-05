package com.soronthar.rpg.model.objects.actors;

import com.soronthar.rpg.model.objects.actors.movement.DefaultMovementStrategy;

import java.awt.*;

public class MobNpc extends Sprite {

    public MobNpc(String id, Point location, Facing facing) {
        super(id, location, facing);
        setMovementStrategy(new DefaultMovementStrategy(this));
        getMovementStrategy().init();
    }


}
