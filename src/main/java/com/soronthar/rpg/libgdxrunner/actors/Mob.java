package com.soronthar.rpg.libgdxrunner.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soronthar.rpg.libgdxrunner.LibGdxRunner;
import com.soronthar.rpg.libgdxrunner.actors.frames.ActorRenderer;
import com.soronthar.rpg.libgdxrunner.actors.frames.DebugRenderer;
import com.soronthar.rpg.libgdxrunner.actors.frames.MobActorRenderer;
import com.soronthar.rpg.libgdxrunner.actors.movement.MobMovementController;
import com.soronthar.rpg.libgdxrunner.actors.movement.MovementController;
import com.soronthar.rpg.model.tiles.Tile;

public class Mob extends Actor {
    private ActorRenderer renderer;
    private MovementController mover;

    public Mob(String name, com.soronthar.rpg.model.objects.Actor actor) {
        super(name);
        this.x = actor.getLocation().x;
        this.y = actor.getLocation().y;
        this.width= Tile.TILE_SIZE;
        this.height=Tile.TILE_SIZE;

        if (LibGdxRunner.DEBUG) {
            this.renderer = new DebugRenderer(Color.PINK, this);
        } else {
            this.renderer = new MobActorRenderer("herop2.png", this);
        }
        mover = new MobMovementController(this);
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
        return !(rect.x >= width ||
                rect.x + rect.width <= 0 ||
                rect.y >= height ||
                rect.y + rect.height <= 0) ? this : null;
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
