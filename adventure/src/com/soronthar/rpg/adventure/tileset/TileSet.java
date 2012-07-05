package com.soronthar.rpg.adventure.tileset;

import com.soronthar.rpg.util.Dimension;
import com.soronthar.rpg.util.Point;

import java.awt.image.BufferedImage;

public class TileSet {
    private BufferedImage image;
    private String name;
    private String resourceName;

    public TileSet(String name, String resourceName, BufferedImage image) {
        this.image = image;
        this.name = name;
        this.resourceName=resourceName;
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

    public String getResourceName() {
        return resourceName;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
