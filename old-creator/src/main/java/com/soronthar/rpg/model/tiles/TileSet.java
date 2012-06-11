package com.soronthar.rpg.model.tiles;

import org.soronthar.geom.Dimension;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileSet {
    private BufferedImage image;
    private String name;

    public TileSet(String name, BufferedImage image) {
        this.image = image;
        this.name = name;
    }

    public BufferedImage image() {
        return image;
    }

    /**
     * @return Width in tiles
     */
    public int getWidth() {
        return image.getWidth() / Tile.TILE_SIZE;
    }

    /**
     * @return Height in tiles
     */
    public int getHeight() {
        return image.getHeight() / Tile.TILE_SIZE;
    }

    public String getName() {
        return name;
    }

    public Tile getTile(Point topLeft, Dimension dimension) {
        return new Tile(name, topLeft, dimension);
    }
}
