package com.soronthar.rpg.model.objects;

import java.awt.*;

public abstract class SpecialObject {
    protected Point location;
    private boolean solid = true;
    private boolean visible = true;

    public SpecialObject(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return new Point(location);
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
