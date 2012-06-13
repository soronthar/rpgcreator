package com.soronthar.rpg.adventure.scenery.objects;

import com.soronthar.rpg.utils.Point;

public abstract class Actor {
    protected Point location;
    private boolean solid = true;
    private boolean visible = true;

    public Actor(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location.clone();
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
