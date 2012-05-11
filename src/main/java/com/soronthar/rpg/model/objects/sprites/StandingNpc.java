package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.NullFrameStrategy;
import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;
import org.soronthar.util.StringUtils;

import java.awt.*;

public class StandingNpc extends Sprite {
    private String imageName = "";

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
        this.imageName = imageName;
        if (StringUtils.isEmptyOrNullValue(imageName)) {
            setFrameStrategy(new NullFrameStrategy());
        } else {
            setFrameStrategy(new WholeImageFrameStrategy(imageName));
        }
    }

    public String getFrameMapName() {
        return this.imageName;
    }
}
