package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;

import java.awt.*;

public class Hero extends Sprite {
    public Hero(Point location, Rectangle bound) {
        super("hero", location);
        setBounds(bound);
        setFrameStrategy(new WholeImageFrameStrategy("hero.png"));
    }
}
