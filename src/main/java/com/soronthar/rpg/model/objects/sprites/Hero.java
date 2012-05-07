package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;

import java.awt.*;

public class Hero extends Sprite {
    public Hero(Point location) {
        super("hero", location);
        setFrameStrategy(new WholeImageFrameStrategy("hero.png"));
    }
}
