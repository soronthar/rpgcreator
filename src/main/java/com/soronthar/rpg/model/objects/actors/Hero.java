package com.soronthar.rpg.model.objects.actors;

import com.soronthar.rpg.model.objects.actors.frames.WholeImageFrameStrategy;
import com.soronthar.rpg.model.objects.actors.movement.BaseMovementStrategy;

import java.awt.*;

public class Hero extends Sprite {
    private Object speed;

    public Hero(Point location) {
        super("hero", location);
        setFrameStrategy(new WholeImageFrameStrategy("hero.png"));
        setMovementStrategy(new BaseMovementStrategy(this));
    }


    //TODO: perhaps a special strategy can be created and registered on the hero, to make unnecessary
    //TODO: to publish the move metod to the world.
    @Override
    public void move() {
        super.move();
    }

    public void stop() {
        this.setSpeed(0,0);
    }

}
