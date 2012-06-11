package com.soronthar.rpg.model;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Administrador
 * Date: 15/07/2010
 * Time: 07:48:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestTilesPixelConversion extends TestCase {
    public void test() {
        assertEquals(1, pixelsToTiles(32));
        assertEquals(20, pixelsToTiles(640));
    }

    private int pixelsToTiles(int pixels) {
        return pixels >> 5;
    }

    private int tilesToPixels(int tiles) {
        return tiles << 5;
    }

}
