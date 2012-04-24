package com.soronthar.rpg;

import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.geom.Dimension;
import org.soronthar.geom.Point;


public class Utils {

    private Utils() {
    }

    public static Point normalizePointToTile(java.awt.Point point) {
        Point p = new Point();
        p.x = normalizeDimensionToTile(point.x);
        p.y = normalizeDimensionToTile(point.y);
        return p;
    }

    public static int normalizeDimensionToTile(int pixels) {
        return Tile.TILE_SIZE * (pixels / Tile.TILE_SIZE);
    }

    public static int pixetToTile(int pixels) {
        return pixels / Tile.TILE_SIZE;
    }

    public static Dimension getTileDimension() {
        return (Dimension) Tile.TILE_DIMENSION.clone();
    }

    public static Dimension getScaledTileDimension(int x, int y) {
        return getTileDimension().scale(x, y);
    }
}
