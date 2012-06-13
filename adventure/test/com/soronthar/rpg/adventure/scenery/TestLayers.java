package com.soronthar.rpg.adventure.scenery;

import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.utils.Dimension;
import com.soronthar.rpg.utils.Point;
import junit.framework.TestCase;

public class TestLayers extends TestCase {

    public void testLayersArrayCantRemove() {
        LayersArray layers = new LayersArray();
        try {
            layers.iterator().remove();
            fail();
        } catch (UnsupportedOperationException e) {

        }
    }

    public void testTileOperations() {
        Layer layer = new Layer(0);
        Point testPoint = new Point(0, 0);
        Tile testTile = new Tile("test", testPoint, new Dimension(0, 0));
        DrawnTile testDrawnTile = new DrawnTile(testPoint, testTile);
        layer.addTile(testDrawnTile);
        assertEquals(testDrawnTile, layer.getTileAt(testPoint));

        layer.removeTileAt(testPoint);
        assertNull(layer.getTileAt(testPoint));

    }
}
