package com.soronthar.rpg.util;

import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.utils.Utils;
import junit.framework.TestCase;

public class TestUtils extends TestCase {

    public void test() {
        assertEquals(1, Utils.pixelToTile(Tile.TILE_SIZE));
        assertEquals(0, Utils.pixelToTile(0));
        assertEquals(0, Utils.pixelToTile(Tile.TILE_SIZE - 1));
        assertEquals(1, Utils.pixelToTile((2 * Tile.TILE_SIZE) - 1));
        assertEquals(2, Utils.pixelToTile(2 * Tile.TILE_SIZE));

        assertEquals(0, Utils.tileTopixel(0));
        assertEquals(32, Utils.tileTopixel(1));
        assertEquals(64, Utils.tileTopixel(2));
        assertEquals(96, Utils.tileTopixel(3));
    }
}
