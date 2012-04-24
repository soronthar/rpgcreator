package com.soronthar.rpg.model;

import java.awt.*;

public class JumpPoint {
    private Point location;
    private String target;

    public JumpPoint(Point location, String target) {
        this.location = location;
        this.target = target;
    }

    public Point getLocation() {
        return location;
    }

    public String getTargetName() {
        return target;
    }
}
