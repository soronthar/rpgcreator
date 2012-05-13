package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.objects.sprites.frames.FrameStrategy;
import com.soronthar.rpg.model.objects.sprites.frames.NullFrameStrategy;
import com.soronthar.rpg.model.objects.sprites.movement.MovementStrategy;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;


public abstract class Sprite extends SpecialObject {
    private String id;
    private FrameStrategy strategy = new NullFrameStrategy();
    private MovementStrategy movementStrategy = MovementStrategy.NO_MOVE;

    private Facing facing = Facing.DOWN;
    private int dx;
    private int dy;
    private int steps;

    public Sprite(String id, Point location) {
        super(location);
        this.id = id;
    }

    public Sprite(String id, Point location, Facing facing) {
        super(location);
        this.id = id;
        this.facing = facing;
    }

    public Point getTileLocation() {
        return Utils.getTileLocationForPoint(facing, this.location);
    }

    public Facing getFacing() {
        return facing;
    }


    public Image getFrame() {
        return strategy.getFrame(this);
    }

    public String getId() {
        return id;
    }


    protected void setFrameStrategy(FrameStrategy strategy) {
        this.strategy = strategy;
    }

    protected FrameStrategy getFrameStrategy() {
        return this.strategy;
    }

    public void setLocation(Point location) {
        this.location = (Point) location.clone();
    }

    protected void move() {
        this.location.translate(dx, dy);
        increaseSteps();
    }

    protected void increaseSteps() {
        this.steps++;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    protected void determineFacing() {
        Facing newFacing = facing;
        if (dx > 0) {
            newFacing = Facing.RIGHT;
        } else if (dx < 0) {
            newFacing = Facing.LEFT;
        } else if (dy > 0) {
            newFacing = Facing.DOWN;
        } else if (dy < 0) {
            newFacing = Facing.UP;
        }

        if (this.facing != newFacing) {
            resetStepCount();
        }
        facing = newFacing;
    }

    private void resetStepCount() {
        this.steps = 0;
    }

    public boolean isSpeedZero() {
        return this.dx == 0 && this.dy == 0;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isMoving() {
        return getDx() != 0 || getDy() != 0;
    }

    boolean isMovingBetweenTiles() {
        int xRemainder = this.location.x % Tile.TILE_SIZE;
        int yRemainder = this.location.y % Tile.TILE_SIZE;

        return xRemainder != 0 || yRemainder != 0;
    }

    public void setSpeed(int dx, int dy) {
        if (facing == Facing.UP || facing == Facing.DOWN) {
            if (dy != 0) {
                this.dy = dy;
                this.dx = 0;
            } else {
                this.dy = 0;
                this.dx = dx;
            }
        }


        if (facing == Facing.LEFT || facing == Facing.RIGHT) {
            if (dx != 0) {
                this.dx = dx;
                this.dy = 0;
            } else {
                this.dx = 0;
                this.dy = dy;
            }
        }
    }

    public void update(long elapsedTime) {
        determineFacing();
        move();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprite sprite = (Sprite) o;

        if (id != null ? !id.equals(sprite.id) : sprite.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void handleCollisionAt(Point tileLocation) {
        getMovementStrategy().handleCollisionAt(tileLocation);
    }

    public void handleAtEdge(Rectangle bounds) {
        getMovementStrategy().handleAtEdge(bounds);
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }
}
