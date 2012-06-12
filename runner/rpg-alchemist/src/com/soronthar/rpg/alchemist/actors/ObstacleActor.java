package com.soronthar.rpg.alchemist.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.alchemist.RpgAlchemist;
import com.soronthar.rpg.alchemist.actors.frames.ActorRenderer;
import com.soronthar.rpg.alchemist.actors.frames.DebugRenderer;
import com.soronthar.rpg.alchemist.actors.frames.EmptyRenderer;

import java.awt.*;


public class ObstacleActor extends Actor {
    private ActorRenderer frameStrategy;

    public ObstacleActor(Point loc) {
        this.x=loc.x;
        this.y=loc.y;
        this.width=Tile.TILE_SIZE;
        this.height= Tile.TILE_SIZE;
        BitmapFont font = new BitmapFont();
        font.setColor(Color.GREEN);
        if (RpgAlchemist.DEBUG) {
            this.frameStrategy=new DebugRenderer(Color.GREEN,this);
        }else {
            this.frameStrategy=new EmptyRenderer();
        }
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        frameStrategy.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public Actor hit(float x, float y) {
        Rectangle rect = new Rectangle(x, y, 32, 32);
        return !(rect.x >=  width ||
                rect.x + rect.width <= 0 ||
                rect.y >= height ||
                rect.y + rect.height <=0) ? this : null;
    }
}
