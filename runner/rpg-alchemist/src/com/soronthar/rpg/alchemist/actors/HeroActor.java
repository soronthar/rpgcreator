package com.soronthar.rpg.alchemist.actors;


import com.badlogic.gdx.Gdx;
import com.soronthar.rpg.adventure.scenery.objects.actors.Hero;
import com.soronthar.rpg.alchemist.actors.movement.HeroMovementController;

public class HeroActor extends Mob {


    public HeroActor(Hero hero) {
        super("Hero", hero);
        HeroMovementController mover = new HeroMovementController(this);
        Gdx.input.setInputProcessor(mover);
        setMover(mover);
    }

}
