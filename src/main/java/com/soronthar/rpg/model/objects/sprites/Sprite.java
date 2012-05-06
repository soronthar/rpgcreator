package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.objects.sprites.frames.FrameStrategy;
import com.soronthar.rpg.model.objects.sprites.frames.NullFrameStrategy;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;


public class Sprite extends SpecialObject {
    private String id;
    private boolean solid = true;
    private boolean visible = true;
    private FrameStrategy strategy = new NullFrameStrategy();

    private Facing facing = Facing.DOWN;
    private int dx;
    private int dy;
    private int steps;
    private Rectangle bounds;

    public Sprite(String id, Point location) {
        super(location);
        this.id = id;
    }

    public Sprite(String id, Point location, Facing facing) {
        super(location);
        this.id = id;
        this.facing = facing;
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


    private Point getTileLocationForPoint(Point location) {
        Point newLocation = new Point(location);
        Point tileLocationDelta = new Point(location.x % Tile.TILE_SIZE, location.y % Tile.TILE_SIZE);
        if (facing == Facing.UP) {
            tileLocationDelta.y *= -1;
        } else if (facing == Facing.LEFT) {
            tileLocationDelta.x *= -1;
        }

        newLocation.translate(tileLocationDelta.x, tileLocationDelta.y);
        return newLocation;
    }

    public Point getTileLocation() {
        return getTileLocationForPoint(this.location);
    }

    public Facing getFacing() {
        return facing;
    }


    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }


    public boolean isVisible() {
        return visible;
    }


    public Image getFrame() {
        return strategy.getFrame(this);
    }

    public String getId() {
        return id;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setFrameStrategy(FrameStrategy strategy) {
        this.strategy = strategy;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setLocation(Point location) {
        this.location = normalizePointToBounds(location, bounds);
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

    public boolean isSpeedZero() {
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

    public void handleCollitionAt(Point tileLocation) {
    }
}
