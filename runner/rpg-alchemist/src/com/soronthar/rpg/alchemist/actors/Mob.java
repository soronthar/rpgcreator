package com.soronthar.rpg.alchemist.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.alchemist.RpgAlchemist;
import com.soronthar.rpg.alchemist.actors.frames.ActorRenderer;
import com.soronthar.rpg.alchemist.actors.frames.DebugRenderer;
import com.soronthar.rpg.alchemist.actors.frames.MobActorRenderer;
import com.soronthar.rpg.alchemist.actors.movement.EmptyMover;
import com.soronthar.rpg.alchemist.actors.movement.MobMovementController;
import com.soronthar.rpg.alchemist.actors.movement.MovementController;

public class Mob extends Actor {
    private ActorRenderer renderer;
    private MovementController mover;

    public Mob(String name, com.soronthar.rpg.adventure.scenery.objects.actors.Sprite  sprite) {
        super(name);
        this.x = Utils.tileTopixel(sprite.getLocation().getX());
        this.y = Utils.tileTopixel(sprite.getLocation().getY());
        this.width= Tile.TILE_SIZE;
        this.height= Tile.TILE_SIZE;

        Color debugColor;
        switch (sprite.getType()) {
            case MOB:
                mover = new MobMovementController(this);
                debugColor = Color.PINK;
                break;
            case NPC:
                debugColor = Color.PINK;
                mover = new EmptyMover();
                break;
            case HERO:
                debugColor = Color.BLUE;
                mover = new EmptyMover();
                break;
            default:
                debugColor = Color.WHITE;
                mover = new EmptyMover();
                break;
        }

        if (RpgAlchemist.DEBUG) {
            this.renderer = new DebugRenderer(debugColor, this);
        } else {
            this.renderer = new MobActorRenderer(sprite.getFramesImageName(), this);
        }
    }

    protected void setRenderer(ActorRenderer renderer) {
        this.renderer = renderer;
    }

    protected void setMover(MovementController mover) {
        this.mover.dispose();
        this.mover = mover;
    }

    protected MovementController getMover() {
        return mover;
    }

    protected ActorRenderer getRenderer() {
        return renderer;
    }

    @Override
    public void act(float delta) {
        mover.move(delta);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        renderer.draw(batch, parentAlpha);
    }

    @Override
    public Actor hit(float x, float y) {
        Rectangle rect = new Rectangle(x, y, 32, 32);
        boolean b = rect.x < width && rect.x + rect.width > 0 && rect.y < height && rect.y + rect.height > 0;
        return b ? this : null;
    }

    public boolean isMoving() {
        return mover.isMoving();
    }

    public int getSteps() {
        return mover.getSteps();
    }

    public int getFacing() {
        return mover.getFacing();
    }
}
