package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.gui.image.TranslucentImage;
import com.soronthar.rpg.model.scenery.DrawnTile;
import com.soronthar.rpg.model.scenery.Layer;
import com.soronthar.rpg.model.scenery.LayersArray;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSet;
import com.soronthar.rpg.model.tiles.TileSetBag;
import org.soronthar.error.ApplicationException;
import org.soronthar.geom.Dimension;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.soronthar.rpg.Utils.normalizePointToTile;

public class TilesetRenderer {

    public static BufferedImage createLayer(int width, int height, TileSetBag tileSets, Layer sceneryLayer) {
        TranslucentImage layer = new TranslucentImage(width, height);
        Graphics2D g = (Graphics2D) layer.getGraphics();
        for (DrawnTile drawnTile : sceneryLayer) {
            BufferedImage drawingPen = null;
            Tile info = drawnTile.getTile();
            if (info != null) {
                Dimension dimension = info.getDimension();
                TileSet tileSet = tileSets.get(info.getTilesetName());
                if (tileSet == null) {
                    throw new ApplicationException("Tileset " + info.getTilesetName() + " is not loaded");
                }
                BufferedImage image = tileSet.image();
                Point point = info.getPoint();
                drawingPen = image.getSubimage(point.x, point.y, dimension.width, dimension.height);

                Point p = normalizePointToTile(drawnTile.getPoint());
                BufferedImage tile = drawingPen;
                if (tile != null) {
                    g.drawImage(tile, p.x, p.y, null);
                }

            }
        }

        g.dispose();


        return layer;
    }

    public static BufferedImage[] createLayers(TileSetBag tileSets, Scenery scenery) {
        LayersArray sceneryLayers = scenery.getLayers();
        BufferedImage[] layers = new BufferedImage[sceneryLayers.size()];
        int i = 0;
        for (Layer sceneryLayer : sceneryLayers) {
            layers[i] = createLayer(scenery.getWidth(), scenery.getHeight(), tileSets, sceneryLayer);
            i++;
        }
        return layers;
    }
}
