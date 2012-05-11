package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;

import java.awt.Point;

public class StandingNpc extends Sprite {
    private String imageName;

    public StandingNpc(String id, Point location) {
        super(id, location);
    }

    public StandingNpc(String id, Point point, Facing facing) {
        super(id, point, facing);
    }

    @Override
    public void setSpeed(int dx, int dy) {
    }

    public void move() {
    }

    public void setFrameMapName(String imageName) {
        this.imageName=imageName;
        setFrameStrategy(new WholeImageFrameStrategy(imageName));
    }

    public String getFrameMapName() {
        return this.imageName;
    }
}
