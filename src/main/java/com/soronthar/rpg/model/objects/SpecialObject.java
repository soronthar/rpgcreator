package com.soronthar.rpg.model.objects;

import java.awt.*;

public abstract class SpecialObject {
    protected Point location;

    public SpecialObject(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return new Point(location);
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
