package com.soronthar.rpg.demiurge.components.paint;

import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.demiurge.CoordinateUtil;
import com.soronthar.rpg.demiurge.components.GlassSelectLayer;
import com.soronthar.rpg.demiurge.legacy.gui.image.TranslucentImage;
import org.soronthar.geom.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class PaintCanvas extends JPanel {
    private GlassSelectLayer glassLayer;
    private TranslucentImage base;

    private TranslucentImage[] layers = new TranslucentImage[LayersArray.LAYER_COUNT + 1];

    PaintCanvasModel canvasModel;

    public PaintCanvas(PaintCanvasModel canvasModel) {
        this.canvasModel=canvasModel;
        this.setCanvasSize(canvasModel.getCanvasSize());
        this.canvasModel.addChangeListener(PaintCanvasModel.LOCATION, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Point location = (Point) evt.getNewValue();
                movePaintPointerTo(location);
            }
        });

        this.canvasModel.addChangeListener(PaintCanvasModel.Action.HIDE_POINTER.name(), new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                hidePaintPointer();
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

        this.setSize(dimension);
        this.setPreferredSize(dimension);
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
            if (canvasModel.isLayerVisible(i)) {
                g.drawImage(layer, 0, 0, null);
            }
        }
        glassLayer.paintIt(g);
    }


    public void movePaintPointerTo(Point p) {
        p= CoordinateUtil.tileToPoint(p,canvasModel.getCanvasSize());
        BufferedImage tile = canvasModel.getDrawingPen();
        if (tile != null) {
            glassLayer.drawSelectOutline(p, new Dimension(tile.getWidth(), tile.getHeight()));
        } else {
            glassLayer.drawSelectSquareAtPoint(p);
        }
    }

    public void hidePaintPointer() {
        glassLayer.clearSelectSquare();
    }

    public void drawTileOnPoint(Point p ) {
        BufferedImage tile = canvasModel.getDrawingPen();
        Point point = CoordinateUtil.tileToPoint(p, canvasModel.getCanvasSize());
        if (tile != null) {
            Graphics2D g = (Graphics2D) layers[canvasModel.getActiveLayer()].getGraphics();
            g.drawImage(tile, point.x, point.y, null);
            g.dispose();
        }
    }

    public void eraseTileOnPoint(Point p) {
        BufferedImage tile = canvasModel.getDrawingPen();
        int width;
        int height;
        if (tile!=null) {
            width = tile.getWidth();
            height = tile.getHeight();
        } else {
            width = Tile.TILE_SIZE;
            height= Tile.TILE_SIZE;
        }
        Point point = CoordinateUtil.tileToPoint(p, canvasModel.getCanvasSize());
        Graphics2D g = (Graphics2D) layers[canvasModel.getActiveLayer()].getGraphics();
        g.clearRect(point.x, point.y, width, height);
        g.dispose();
    }

    public BufferedImage getLayerImage(int layer) {
        return layers[layer];
    }
}
