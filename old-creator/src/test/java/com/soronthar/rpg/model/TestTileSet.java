package com.soronthar.rpg.model;

import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.adventure.tileset.TileSet;
import junit.framework.TestCase;

import java.awt.image.BufferedImage;

public class TestTileSet extends TestCase {

    public void test() {
        TileSet tileSet = new TileSet("TILESET", "TILESET", new BufferedImage(Tile.TILE_SIZE * 10, Tile.TILE_SIZE * 20, BufferedImage.TYPE_INT_ARGB));
        assertEquals(10, tileSet.getWidth());
        assertEquals(20, tileSet.getHeight());
        assertEquals("TILESET", tileSet.getName());
    }
}
