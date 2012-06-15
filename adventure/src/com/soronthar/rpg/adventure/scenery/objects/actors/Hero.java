package com.soronthar.rpg.adventure.scenery.objects.actors;

import com.soronthar.rpg.utils.Point;

public class Hero extends Sprite {

    public Hero(Point location) {
        super("hero", location);
        setFramesImage("herop2.png");
    }


}
