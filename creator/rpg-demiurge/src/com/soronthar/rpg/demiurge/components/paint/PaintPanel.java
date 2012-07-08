package com.soronthar.rpg.demiurge.components.paint;

import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Controller;
import com.soronthar.rpg.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class PaintPanel extends JScrollPane {
    private PaintCanvas canvas;
    Controller controller;
    private PaintCanvasModel canvasModel;


    public PaintPanel(Controller controller,PaintCanvasModel canvasModel) {
        this(controller, canvasModel, Scenery.WIDTH - 1, Scenery.HEIGHT - 1);
    }

    //TODO: scrolls are not working as they should
    public PaintPanel(Controller controller, PaintCanvasModel canvasModel, int w, int h) {
        this.controller = controller;
        this.canvasModel=canvasModel;
        this.setViewportView(initializeCanvas(w, h));
        this.setAutoscrolls(true);
        this.setBackground(Color.blue.brighter());
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        forceRepaintOnScroll();
        controller.setPaintPanel(this);
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

    private JPanel initializeCanvas(int w, int h) {
        JPanel panel = createPanel(w, h);
        setMouseAdapter(panel);
        return panel;
    }

    private JPanel createPanel(int w, int h) {
        JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        canvas = new PaintCanvas(w, h, this.controller.getModel(),canvasModel);
        panel.add(canvas);
        panel.setPreferredSize(new Dimension(w, h));
        return panel;
    }

    private void setMouseAdapter(JPanel panel) {
        PaintPanelMouseInputAdapter mouseInputAdapter = new PaintPanelMouseInputAdapter(controller,canvasModel);
        panel.addMouseListener(mouseInputAdapter);
        panel.addMouseMotionListener(mouseInputAdapter);
    }

    public PaintCanvas getCanvas() {
        return canvas;
    }


    public void drawTileAtPoint(Point p) {
        this.getCanvas().drawTileOnPoint(p);
        this.repaint();
    }

    public void handleEraseTileEvent(Point p) {
        this.getCanvas().eraseTileOnPoint(p.toAWT());
        this.repaint();
    }


    public void clearMap() {
        this.getCanvas().clearMap();
        this.repaint();
    }

    public void setCanvasSize(Dimension dimension) {
        this.getCanvas().setCanvasSize(dimension);
    }
}
