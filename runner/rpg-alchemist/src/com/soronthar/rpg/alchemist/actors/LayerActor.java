package com.soronthar.rpg.alchemist.actors;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LayerActor extends Actor {
    private Texture region;
    private ShapeRenderer outliner = new ShapeRenderer();

    public LayerActor(Texture region) {
        this.x=0;
        this.y=0;
        this.region = region;
        this.width=region.getWidth();
        this.height=region.getHeight();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.draw(region, 0,0);
        batch.end();

        outliner.setProjectionMatrix(batch.getProjectionMatrix());
        outliner.begin(ShapeRenderer.ShapeType.Rectangle);
        outliner.setColor(Color.BLUE);
        outliner.rect(this.x, this.y, this.width, this.height);
        outliner.end();

        batch.begin();
    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
