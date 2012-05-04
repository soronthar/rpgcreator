package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;


public abstract class Sprite extends SpecialObject {
    private Image[] frames;
    protected Facing facing = Facing.UP;

    protected Sprite(Point location) {
        super(location);
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


    @Override
    public boolean isSolid() {
        return true;
    }

    public boolean isMoving() {
        return false;
    }


    public void update(long elapsedTime) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Image getFrame() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
