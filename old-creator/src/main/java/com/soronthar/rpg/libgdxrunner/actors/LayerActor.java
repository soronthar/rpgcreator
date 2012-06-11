package com.soronthar.rpg.libgdxrunner.actors;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LayerActor extends Actor {
    private Texture region;

    public LayerActor(Texture region) {
        this.region = region;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(region, 0,0);
    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
