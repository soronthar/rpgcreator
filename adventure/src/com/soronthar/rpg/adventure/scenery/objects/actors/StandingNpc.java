package com.soronthar.rpg.adventure.scenery.objects.actors;


import com.soronthar.rpg.util.Point;

public class StandingNpc extends Sprite {

    public StandingNpc(String id, Point location) {
        super(id, location);
    }

    public StandingNpc(String id, Point point, Facing facing) {
        super(id, point, facing);
    }

    @Deprecated
    public StandingNpc(String id, java.awt.Point point, Facing facing) {
        super(id, Point.fromAWT(point), facing);
    }
}
