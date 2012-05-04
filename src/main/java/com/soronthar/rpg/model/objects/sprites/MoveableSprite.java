package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;

public class MoveableSprite extends Sprite {
    protected int dx;
    protected int dy;
    private int steps;
    private Rectangle bounds;

    public MoveableSprite(String id, Point location) {
        super(id, location);
    }

    public MoveableSprite(String id, Point location, Facing facing) {
        super(id, location, facing);
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setLocation(Point location) {
        this.location = normalizePointToBounds(location, bounds);
    }


    //TODO: move somewhere else
    private static Point normalizePointToBounds(Point newLocation, Rectangle bounds) {
        if (bounds == null) return newLocation;
        if (newLocation.x < bounds.x) newLocation.x = bounds.x;
        if (newLocation.y < bounds.y) newLocation.y = bounds.y;
        if (newLocation.x >= bounds.width) newLocation.x = bounds.width;
        if (newLocation.y >= bounds.height) newLocation.y = bounds.height;
        return newLocation;
    }

    @Override
    public void update(long elapsedTime) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void move() {
        this.location.translate(dx, dy);
        this.location = normalizePointToBounds(location, bounds);
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

//        if (!this.facing.hasSameAxis(newFacing)) {
//            adjustPositionToTile(true);
//        }
        if (this.facing != newFacing) {
            resetStepCount();
        }
        facing = newFacing;
    }

    private void resetStepCount() {
        this.steps = 0;
    }

    boolean isSpeedZero() {
        return this.dx == 0 && this.dy == 0;
    }

    public int getSteps() {
        return steps;
    }


    public boolean isMoving() {
        return getDx() != 0 || getDy() != 0;
    }

    public void constraintTo(Rectangle screenBounds) {
        this.bounds = screenBounds;
    }


    boolean isMovingBetweenTiles() {
        int xRemainder = this.location.x % Tile.TILE_SIZE;
        int yRemainder = this.location.y % Tile.TILE_SIZE;

        return xRemainder != 0 || yRemainder != 0;
    }

}
