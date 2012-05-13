package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;
import com.soronthar.rpg.model.objects.sprites.movement.DefaultMovementStrategy;

import java.awt.*;

public class MobNpc extends Sprite {
    private String imageName;

    public MobNpc(String id, Point location, Facing facing) {
        super(id, location, facing);
        setFrameMapName("cat.png");  //TODO: this should NOT be here
        setMovementStrategy(new DefaultMovementStrategy(this));
        getMovementStrategy().init();
    }

    public void setFrameMapName(String imageName) {
        this.imageName = imageName;
        setFrameStrategy(new WholeImageFrameStrategy(imageName));
    }


    public String getFrameMapName() {
        return imageName;
    }

}
