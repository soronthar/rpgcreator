package com.soronthar.rpg.libgdxrunner.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.soronthar.rpg.libgdxrunner.LibGdxRunner;
import com.soronthar.rpg.libgdxrunner.actors.frames.DebugRenderer;
import com.soronthar.rpg.libgdxrunner.actors.frames.MobActorRenderer;
import com.soronthar.rpg.libgdxrunner.actors.movement.HeroMovementController;
import com.soronthar.rpg.model.objects.actors.Hero;

public class HeroActor extends Mob {


    public HeroActor(Hero hero) {
        super("Hero", hero);
        if (LibGdxRunner.DEBUG) {
            setRenderer(new DebugRenderer(Color.BLUE, this));
        } else {
            setRenderer(new MobActorRenderer("herop2.png", this));
        }
        HeroMovementController mover = new HeroMovementController(this);
        Gdx.input.setInputProcessor(mover);
        setMover(mover);
    }

}
