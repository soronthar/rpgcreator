package com.soronthar.rpg.demiurge.legacy.gui.builder.components.paint;

import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.components.tiles.GlassSelectLayer;
import com.soronthar.rpg.demiurge.legacy.gui.image.TranslucentImage;
import org.soronthar.geom.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PaintCanvas extends JPanel {
    private GlassSelectLayer glassLayer;
    private TranslucentImage base;

    private TranslucentImage[] layers = new TranslucentImage[LayersArray.LAYER_COUNT + 1];

    private Model model;


    public PaintCanvas(int w, int h, Model model) {
        this.setCanvasSize(new Dimension(w, h));
        this.model = model;
        this.model.addChangeListener(Model.LOCATION, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Point location = (Point) evt.getNewValue();
                movePaintPointerTo(location);
            }
        });
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

    public void drawTileOnPoint(com.soronthar.rpg.util.Point p ) {
        BufferedImage tile = model.getDrawingPen();
        if (tile != null) {
            Graphics2D g = (Graphics2D) layers[model.getActiveLayerIndex()].getGraphics();
            g.drawImage(tile, p.getX(), p.getY(), null);
            g.dispose();
        }
    }

    public void eraseTileOnPoint(Point p) {
        BufferedImage tile = model.getDrawingPen();

        Graphics2D g;
        g = (Graphics2D) layers[model.getActiveLayerIndex()].getGraphics();
        g.clearRect(p.x, p.y, tile.getWidth(), tile.getHeight());
        g.dispose();
    }

    public BufferedImage getLayerImage(int layer) {
        return layers[layer];
    }
}
