package com.soronthar.rpg.model.objects.actors.frames;

import com.soronthar.rpg.model.objects.actors.Sprite;

import java.awt.*;

public interface FrameStrategy {
    Image getFrame(Sprite sprite);
}
