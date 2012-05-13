package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;

import java.awt.*;

public class Hero extends Sprite {
    public Hero(Point location) {
        super("hero", location);
        setFrameStrategy(new WholeImageFrameStrategy("hero.png"));
    }


    //TODO: perhaps a special strategy can be created and registered on the hero, to make unnecessary
    //TODO: to publish the move metod to the world.
    @Override
    public void move() {
        super.move();
    }
}
