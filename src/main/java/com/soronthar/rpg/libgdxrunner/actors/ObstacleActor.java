package com.soronthar.rpg.libgdxrunner.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soronthar.rpg.libgdxrunner.LibGdxRunner;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;


public class ObstacleActor extends Actor {
    private ShapeRenderer renderer= new ShapeRenderer();
    private BitmapFont font;
    private Rectangle rect=new Rectangle();

    public ObstacleActor(Point loc) {
        this.x=loc.x;
        this.y=loc.y;
        this.width=Tile.TILE_SIZE;
        this.height=Tile.TILE_SIZE;
        font=new BitmapFont();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (!LibGdxRunner.DEBUG) return;

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.begin(ShapeRenderer.ShapeType.Rectangle);
        renderer.setColor(Color.GREEN);
        renderer.rect(x, y, width, height);
        renderer.setColor(Color.RED);
        renderer.rect(rect.x+this.x,rect.y+this.y,32,32);

        renderer.end();

        font.setColor(Color.GREEN);
        font.draw(batch, Integer.toString((int) x), x + 3, y + 30);
        font.draw(batch, Integer.toString((int) y), x + 3, y + 15);
    }

    @Override
    public Actor hit(float x, float y) {
        rect = new Rectangle(x,y,32,32);
        return !(rect.x >=  width ||
                rect.x + rect.width <= 0 ||
                rect.y >= height ||
                rect.y + rect.height <=0) ? this : null;
    }
}
