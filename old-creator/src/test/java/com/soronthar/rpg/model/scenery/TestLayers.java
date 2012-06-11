package com.soronthar.rpg.model.scenery;

import com.soronthar.rpg.model.tiles.Tile;
import junit.framework.TestCase;
import org.soronthar.geom.Dimension;

import java.awt.*;

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
