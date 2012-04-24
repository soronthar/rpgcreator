package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.components.paint.PaintCanvas;
import com.soronthar.rpg.model.scenery.Scenery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class PaintPanel extends JScrollPane {
    private PaintCanvas canvas;
    Controller controller;


    public PaintPanel(Controller controller) {
        this.controller = controller;
        this.setViewportView(initializeCanvas());
        this.setBackground(Color.blue.brighter());
        this.setEnabled(false);
        forceRepaintOnScroll();
        controller.setPaintPanel(this);
    }

    private void forceRepaintOnScroll() {
        this.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                ((Component) e.getSource()).getParent().repaint();
            }
        });
        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                ((Component) e.getSource()).getParent().repaint();
            }
        });
    }

    private JPanel initializeCanvas() {
        JPanel panel = createPanel();
        setMouseAdapter(panel);
        return panel;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        canvas = new PaintCanvas(Scenery.WIDTH - 1, Scenery.HEIGHT - 1, this.controller.getModel());
        panel.add(canvas);
        panel.setPreferredSize(new Dimension(800, 640));
        return panel;
    }

    private void setMouseAdapter(JPanel panel) {
        PaintPanelMouseInputAdapter mouseInputAdapter = new PaintPanelMouseInputAdapter(controller);
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
}
