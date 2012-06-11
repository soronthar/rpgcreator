package com.soronthar.rpg.model;

import com.soronthar.rpg.model.objects.Actor;

import java.awt.*;

public class JumpPoint extends Actor {
    private long target;

    public JumpPoint(Point location, long target) {
        super(location);
        this.target = target;
    }

    public long getTargetId() {
        return target;
    }
}
