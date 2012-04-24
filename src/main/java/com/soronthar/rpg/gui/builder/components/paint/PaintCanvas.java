package com.soronthar.rpg.gui.builder.components.paint;

import com.soronthar.rpg.gui.builder.Model;
import com.soronthar.rpg.gui.builder.components.tiles.GlassSelectLayer;
import com.soronthar.rpg.gui.image.TranslucentImage;
import com.soronthar.rpg.model.scenery.LayersArray;
import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.geom.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintCanvas extends JPanel {
    private GlassSelectLayer glassLayer;
    private TranslucentImage base;

    private TranslucentImage[] layers = new TranslucentImage[LayersArray.LAYER_COUNT + 1];

    private Model model;


    public PaintCanvas(int w, int h, Model model) {
        this.setCanvasSize(new Dimension(w, h));
        this.model = model;
    }

    public void setCanvasSize(java.awt.Dimension dimension) {
        int w = dimension.width;
        int h = dimension.height;

        glassLayer = new GlassSelectLayer(w, h, Color.yellow, GlassSelectLayer.Fit.OUTER_BORDER);
        base = new TranslucentImage(w, h);
        clearMap();

        Graphics g = base.getGraphics();
        g.setColor(Color.gray); //TODO:hardwired
        g.fillRect(0, 0, w + 1, h + 1);
        g.dispose();
    }


    public void clearMap() {
        for (int i = 0; i < layers.length; i++) {
            layers[i] = new TranslucentImage(base.getWidth(), base.getHeight());
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(base, 0, 0, null);
        for (int i = 0, layersLength = layers.length; i < layersLength; i++) {
            TranslucentImage layer = layers[i];
            if (model.isLayerVisible(i)) {
                g.drawImage(layer, 0, 0, null);
            }
        }
        glassLayer.paintIt(g);
    }


    public void movePaintPointerTo(Point p) {
        BufferedImage tile = model.getDrawingPen();

        if (tile != null) {
            glassLayer.drawSelectOutline(p, new Dimension(tile.getWidth(), tile.getHeight()));
        } else {
            glassLayer.drawSelectSquareAtPoint(p);
        }
    }

    public void hidePaintPointerEvent() {
        glassLayer.clearSelectSquare();
    }

    public void drawTileOnPoint(Point p) {
        BufferedImage tile;
        tile = model.getDrawingPen();
        if (tile != null) {
            Graphics2D g = (Graphics2D) layers[model.getActiveLayerIndex()].getGraphics();
            g.drawImage(tile, p.x, p.y, null);
            g.dispose();
        }
    }

    public void eraseTileOnPoint(Point p) {
        Graphics2D g;
        g = (Graphics2D) layers[model.getActiveLayerIndex()].getGraphics();
        g.clearRect(p.x, p.y, Tile.TILE_SIZE, Tile.TILE_SIZE);
        g.dispose();
    }
}
