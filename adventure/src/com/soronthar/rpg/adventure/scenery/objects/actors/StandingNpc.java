package com.soronthar.rpg.adventure.scenery.objects.actors;

import java.awt.*;

public class StandingNpc extends Sprite {

    public StandingNpc(String id, Point location) {
        super(id, location);
    }

    public StandingNpc(String id, Point point, Facing facing) {
        super(id, point, facing);
    }

}
