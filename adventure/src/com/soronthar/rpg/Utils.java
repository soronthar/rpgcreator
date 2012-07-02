package com.soronthar.rpg;


import com.soronthar.rpg.adventure.scenery.objects.actors.Facing;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.util.Dimension;
import com.soronthar.rpg.util.Point;

public class Utils {

    public static java.awt.Point normalizePointToTile(java.awt.Point point) {
        return normalizePointToTile(Point.fromAWT(point)).toAWT();
        
    }
    public static Point normalizePointToTile(Point point) {
        Point p = new Point();
        p.setX(normalizeDimensionToTile(point.getX()));
        p.setY(normalizeDimensionToTile(point.getY()));
        return p;
    }


    public static int normalizeDimensionToTile(int pixels) {
        return Tile.TILE_SIZE * (pixels / Tile.TILE_SIZE);
    }

    public static int pixelToTile(int pixels) {
        return pixels / Tile.TILE_SIZE;
    }

    public static int tileTopixel(int tile) {
        return tile * Tile.TILE_SIZE;
    }

    public static Dimension getTileDimension() {
        return Tile.TILE_DIMENSION.clone();
    }

    public static Dimension getScaledTileDimension(int x, int y) {
        return getTileDimension().scale(x, y);
    }

    public static Point getTileLocationForPoint(Facing facing,Point location) {
        Point newLocation = location.clone();
        Point tileLocationDelta = new Point(location.getX() % Tile.TILE_SIZE, location.getY() % Tile.TILE_SIZE);
        if (facing == Facing.UP) {
            tileLocationDelta.flipY();
        } else if (facing == Facing.LEFT) {
            tileLocationDelta.flipX();
        }

        newLocation.translate(tileLocationDelta.getX(), tileLocationDelta.getY());
        return newLocation;
    }
}
