package com.soronthar.rpg.libgdxrunner;

import com.badlogic.gdx.graphics.Pixmap;
import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.geom.Dimension;

import java.awt.*;

public class TileSet {
    private Pixmap image;
    private String name;

    public TileSet(String name, Pixmap image) {
        this.image = image;
        this.name = name;
    }

    public Pixmap image() {
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
