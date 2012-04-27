package com.soronthar.rpg.model;

import com.soronthar.rpg.model.objects.SpecialObject;

import java.awt.*;

public class JumpPoint extends SpecialObject {
    private long target;

    public JumpPoint(Point location, long target) {
        super(location);
        this.target = target;
    }

    public long getTargetId() {
        return target;
    }
}
