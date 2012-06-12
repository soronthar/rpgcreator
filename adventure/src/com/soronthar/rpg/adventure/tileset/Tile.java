package com.soronthar.rpg.adventure.tileset;

import org.soronthar.geom.Dimension;

import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable {
    public static final int TILE_SIZE = 32;
    public static final Dimension TILE_DIMENSION = new Dimension(TILE_SIZE, TILE_SIZE);

    String tilesetName;
    Point point;
    Dimension dimension;

    public Tile(String tilesetName, Point point, Dimension dimension) {
        this.tilesetName = tilesetName;
        this.point = point;
        this.dimension = dimension;
    }

    public String getTilesetName() {
        return tilesetName;
    }

    public Point getPoint() {
        return (Point) point.clone();
    }

    public Dimension getDimension() {
        return (Dimension) dimension.clone();
    }

    public String toString() {
        return tilesetName + "[" + dimension + "]@" + point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tileInfo = (Tile) o;
        return dimension.equals(tileInfo.dimension) && point.equals(tileInfo.point) && tilesetName.equals(tileInfo.tilesetName);

    }

    @Override
    public int hashCode() {
        int result;
        result = tilesetName.hashCode();
        result = 31 * result + point.hashCode();
        result = 31 * result + dimension.hashCode();
        return result;
    }


}
