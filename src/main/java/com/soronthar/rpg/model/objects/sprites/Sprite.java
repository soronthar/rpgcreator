package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;


public abstract class Sprite extends SpecialObject {
    private String id;
    private boolean isSolid = true;
    private boolean visible = true;


    private Image[] frames;
    protected Facing facing = Facing.DOWN;

    protected Sprite(String id, Point location) {
        super(location);
        this.id = id;
    }

    protected Sprite(String id, Point location, Facing facing) {
        super(location);
        this.id = id;
        this.facing = facing;
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
        return isSolid;
    }

    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    public boolean isMoving() {
        return false;
    }

    public boolean isVisible() {
        return visible;
    }

    abstract public void update(long elapsedTime);

    public Image getFrame() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getId() {
        return id;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
