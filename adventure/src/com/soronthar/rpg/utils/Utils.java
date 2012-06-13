package com.soronthar.rpg.utils;

import com.soronthar.rpg.adventure.scenery.objects.actors.Facing;
import com.soronthar.rpg.adventure.tileset.Tile;
import org.soronthar.geom.Dimension;


public class Utils {

    public static java.awt.Point normalizePointToTile(java.awt.Point point) {
        return normalizePointToTile(Point.fromAWT(point)).toAWT();
        
    }
    public static Point normalizePointToTile(Point point) {
        Point p = new Point();
        p.x = normalizeDimensionToTile(point.x);
        p.y = normalizeDimensionToTile(point.y);
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
        return (Dimension) Tile.TILE_DIMENSION.clone();
    }

    public static Dimension getScaledTileDimension(int x, int y) {
        return getTileDimension().scale(x, y);
    }

    public static Point getTileLocationForPoint(Facing facing,Point location) {
        Point newLocation = location.clone();
        Point tileLocationDelta = new Point(location.x % Tile.TILE_SIZE, location.y % Tile.TILE_SIZE);
        if (facing == Facing.UP) {
            tileLocationDelta.y *= -1;
        } else if (facing == Facing.LEFT) {
            tileLocationDelta.x *= -1;
        }

        newLocation.translate(tileLocationDelta.x, tileLocationDelta.y);
        return newLocation;
    }
}
