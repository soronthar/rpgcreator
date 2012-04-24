package com.soronthar.rpg.model;

import com.soronthar.rpg.model.objects.SpecialObject;

import java.awt.*;

public class JumpPoint extends SpecialObject {
    private String target;

    public JumpPoint(Point location, String target) {
        super(location);
        this.target = target;
    }

    public String getTargetName() {
        return target;
    }
}
