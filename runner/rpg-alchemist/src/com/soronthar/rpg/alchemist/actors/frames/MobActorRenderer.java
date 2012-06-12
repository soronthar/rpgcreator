package com.soronthar.rpg.alchemist.actors.frames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.soronthar.rpg.alchemist.actors.Mob;

public class MobActorRenderer implements ActorRenderer {
    private final TextureRegion[][] frames;
    private Mob mob;

    public MobActorRenderer(String imageName, Mob mob) {
        Texture spriteSheet = new Texture(Gdx.files.internal("projects/FirstProject/sprites/" + imageName));
        frames = TextureRegion.split(spriteSheet, 32, 32);
        this.mob = mob;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        int index = 0;
        if (mob.isMoving()) {
            index = 1 + mob.getSteps()% 2;
        }

        batch.draw(frames[index][mob.getFacing()], mob.x, mob.y);
    }
}
