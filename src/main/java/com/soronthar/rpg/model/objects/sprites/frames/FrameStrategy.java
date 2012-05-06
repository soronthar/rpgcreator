package com.soronthar.rpg.model.objects.sprites.frames;

import com.soronthar.rpg.model.objects.sprites.Sprite;

import java.awt.*;

public interface FrameStrategy {
    Image getFrame(Sprite sprite);
}
