package com.soronthar.rpg.alchemist.actors.frames;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DebugRenderer implements ActorRenderer {
    private BitmapFont font;
    private Actor actor;
    private ShapeRenderer outliner = new ShapeRenderer();

    public DebugRenderer(Color color, Actor actor) {
        font = new BitmapFont();
        font.setColor(color);
        this.actor = actor;

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        batch.end();
        outliner.setProjectionMatrix(batch.getProjectionMatrix());
        outliner.begin(ShapeRenderer.ShapeType.Rectangle);
        outliner.setColor(Color.BLUE);
        outliner.rect(actor.x, actor.y, 32, 32);
        outliner.end();

        outliner.begin(ShapeRenderer.ShapeType.FilledCircle);
        outliner.setColor(Color.RED);
        outliner.filledCircle(actor.x, actor.y, 5);
        outliner.end();

        batch.begin();
        font.draw(batch, Integer.toString((int) actor.x), actor.x + 3, actor.y + 30);
        font.draw(batch, Integer.toString((int) actor.y), actor.x + 3, actor.y + 15);
    }
}
