package com.soronthar.rpg.adventure.tileset;

import com.soronthar.rpg.utils.Dimension;
import com.soronthar.rpg.utils.Point;

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

    public Tile getTile(Point topLeft, java.awt.Dimension dimension) {
        return new Tile(name, topLeft, Dimension.fromAWT(dimension));
    }
}
