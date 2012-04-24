package com.soronthar.rpg.model.objects;


import java.awt.*;

public class Obstacle extends SpecialObject {


    public Obstacle(Point location) {
        super(location);
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
