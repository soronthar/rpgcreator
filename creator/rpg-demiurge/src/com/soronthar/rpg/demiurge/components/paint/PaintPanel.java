package com.soronthar.rpg.demiurge.components.paint;

import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.adventure.scenery.Scenery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class PaintPanel extends JScrollPane {
    public static final String EDIT_SPECIAL = "EDIT_SPECIAL";
    private PaintCanvas canvas;
    private final PaintPanelMouseInputAdapter mouseInputAdapter;


    public PaintPanel(PaintCanvasModel canvasModel) {
        this(canvasModel, Scenery.WIDTH - 1, Scenery.HEIGHT - 1);
    }

    public PaintPanel(PaintCanvasModel canvasModel, int w, int h) {
        canvas = new PaintCanvas(w, h, canvasModel);
        mouseInputAdapter = new PaintPanelMouseInputAdapter(this,canvasModel);
        canvas.addMouseListener(mouseInputAdapter);
        canvas.addMouseMotionListener(mouseInputAdapter);

        this.setViewportView(canvas);
        this.setAutoscrolls(true);
        this.setBackground(Color.blue.brighter());
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        forceRepaintOnScroll();

        canvasModel.addChangeListener(PaintCanvasModel.LOCATION, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                SwingUtilities.getRoot(PaintPanel.this).repaint();
            }
        });

        canvasModel.addChangeListener(PaintCanvasModel.Action.DRAW, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                drawTileAtPoint((Point) evt.getNewValue());
            }
        });

        canvasModel.addChangeListener(PaintCanvasModel.Action.ERASE, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                eraseTileAtPoint((Point) evt.getNewValue());
            }
        });

        canvasModel.addChangeListener(PaintCanvasModel.Action.CLEAR, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                clearMap();
            }
        });

        canvasModel.addChangeListener(PaintCanvasModel.LAYER, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                repaint();
            }
        });

        this.setPreferredSize(new Dimension(w, h));
    }

    private void forceRepaintOnScroll() {
        this.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Component root = SwingUtilities.getRoot(PaintPanel.this);
                root.repaint();
            }
        });
        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Component root = SwingUtilities.getRoot(PaintPanel.this);
                root.repaint();
            }
        });
    }

    private PaintCanvas getCanvas() {
        return canvas;
    }

    public BufferedImage getFlattenImage() {
        BufferedImage layerImage = canvas.getLayerImage(0);
        BufferedImage finalImage = new BufferedImage(layerImage.getWidth(), layerImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) finalImage.getGraphics();
        g.setBackground(new Color(0, 0, 0, 0));
        for (int i = 0; i < LayersArray.LAYER_COUNT; i++) {
            layerImage = canvas.getLayerImage(0);
            g.drawImage(layerImage, 0, 0, null);
            layerImage = canvas.getLayerImage(1);
            g.drawImage(layerImage, 0, 0, null);
        }
        g.dispose();
        return finalImage;
    }


    public void drawTileAtPoint(Point p) {
        this.getCanvas().drawTileOnPoint(p);
        this.repaint();
    }

    public void eraseTileAtPoint(Point p) {
        this.getCanvas().eraseTileOnPoint(p);
        this.repaint();
    }


    public void clearMap() {
        this.getCanvas().clearMap();
        this.repaint();
    }

    public void setCanvasSize(Dimension dimension) {
        this.getCanvas().setCanvasSize(dimension);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        canvas.setEnabled(enabled);
        mouseInputAdapter.setEnabled(enabled);
    }

    ArrayList<SpecialEditEventListener> listeners=new ArrayList<SpecialEditEventListener>();
    
    public void addSpecialEditRequestListener(SpecialEditEventListener listener) {
        listeners.add(listener);
    }

    public void notifySpecialEditRequest(Point point) {
        for (SpecialEditEventListener listener : listeners) {
            listener.onSpecialEditRequestAt(point);
        }    
    }
}
