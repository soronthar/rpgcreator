package com.soronthar.rpg.demiurge.legacy.gui.builder.components.tiles;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.demiurge.legacy.gui.image.TranslucentImage;

import javax.swing.*;
import java.awt.*;


public class GlassSelectLayer extends JPanel {
    public enum Fit {
        EXACT_FIT, OUTER_BORDER
    }

    private TranslucentImage image;
    private boolean exactFit;

    private Point topLeft = new Point();
    private Dimension dimension = new Dimension(0, 0);
    private Color color;

    public GlassSelectLayer(int width, int height, Color color, Fit fit) {
        image = new TranslucentImage(width, height);
        this.exactFit = (fit == Fit.EXACT_FIT);
        this.color = color;
    }

    public void paintIt(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    protected void paintComponent(Graphics g) {
        paintIt(g);
    }

    public void drawSelectSquareAtPoint(Point point) {
        drawSelectOutline(point, Utils.getTileDimension().toAWT());
    }

    public void drawSelectOutline(Point topLeft, Dimension dimension) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        clearOldSelection(g);
        g.setColor(this.color);
        this.topLeft = topLeft;
        this.dimension = dimension;
        drawNewSelection(g);
        g.dispose();
    }

    public void clearSelectSquare() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(this.color);
        clearOldSelection(g);
    }

    private void drawNewSelection(Graphics2D g) {
        if (exactFit) {
            g.drawRect(topLeft.x, topLeft.y, dimension.width - 1, dimension.height - 1);
        } else {
            g.drawRect(topLeft.x - 1, topLeft.y - 1, dimension.width, dimension.height);
        }
    }

    private void clearOldSelection(Graphics2D g) {
        if (exactFit) {
            g.clearRect(topLeft.x, topLeft.y, dimension.width, dimension.height);
        } else {
            g.clearRect(topLeft.x - 1, topLeft.y - 1, dimension.width + 1, dimension.height + 1);
        }
    }

}
