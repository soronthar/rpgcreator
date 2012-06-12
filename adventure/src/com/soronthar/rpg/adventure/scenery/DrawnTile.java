package com.soronthar.rpg.adventure.scenery;


import com.soronthar.rpg.adventure.tileset.Tile;

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
