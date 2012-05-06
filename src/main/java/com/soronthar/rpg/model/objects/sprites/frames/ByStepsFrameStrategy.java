package com.soronthar.rpg.model.objects.sprites.frames;

import com.soronthar.rpg.model.objects.sprites.Sprite;

import java.awt.*;

public class ByStepsFrameStrategy implements FrameStrategy {
    Image[] frames;
    int stepSize = 1;

    public ByStepsFrameStrategy() {


    }

    @Override
    public Image getFrame(Sprite sprite) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
