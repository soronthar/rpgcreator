package com.soronthar.rpg.alchemist.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.soronthar.rpg.adventure.scenery.objects.actors.Hero;
import com.soronthar.rpg.alchemist.RpgAlchemist;
import com.soronthar.rpg.alchemist.actors.frames.DebugRenderer;
import com.soronthar.rpg.alchemist.actors.frames.MobActorRenderer;
import com.soronthar.rpg.alchemist.actors.movement.HeroMovementController;

public class HeroActor extends Mob {


    public HeroActor(Hero hero) {
        super("Hero", hero);
        if (RpgAlchemist.DEBUG) {
            setRenderer(new DebugRenderer(Color.BLUE, this));
        } else {
            setRenderer(new MobActorRenderer("herop2.png", this));
        }
        HeroMovementController mover = new HeroMovementController(this);
        Gdx.input.setInputProcessor(mover);
        setMover(mover);
    }

}
