package com.soronthar.rpg.adventure.scenery.objects.actors;


import com.soronthar.rpg.utils.Point;

public class MobNpc extends Sprite {

    public MobNpc(String id, Point location, Facing facing) {
        super(id, location, facing);
    }


    public MobNpc(String id, java.awt.Point point, Facing facing) {
        super(id, Point.fromAWT(point), facing);
    }
}
