package com.soronthar.rpg.adventure.scenery.objects.actors;


import com.soronthar.rpg.util.Point;

public class Hero extends Sprite {

    public Hero(Point location) {
        super("hero", Type.HERO,location);
        setFramesImage("herop2.png");
    }


}
