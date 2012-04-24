package com.soronthar.rpg.model.scenery;

import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;
import java.io.Serializable;

public class DrawnTile implements Serializable {
    private Point point;
    private Tile tile;

    public DrawnTile(Point point, Tile tile) {
        this.point = point;
        this.tile = tile;
    }

    public Point getPoint() {
        return point;
    }

    public Tile getTile() {
        return tile;
    }
}
