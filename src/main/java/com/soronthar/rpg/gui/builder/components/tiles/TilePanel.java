package com.soronthar.rpg.gui.builder.components.tiles;

import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.geom.Dimension;
import org.soronthar.geom.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class TilePanel extends JPanel {
    private TilePalette tilePalette;
    private GlassSelectLayer glassLayer;
    private Point frontier;

    public TilePanel(String name, BufferedImage tileSetImage) {
        this.setName(name);
        this.setLayout(new OverlayLayout(this));
        this.setPreferredSize(new Dimension(tileSetImage.getWidth(), tileSetImage.getHeight()));
        //This is needed to prevent "garbage" in the rendering
        //See http://java.sun.com/products/jfc/tsc/articles/painting/index.html
        //for a complete explanation
        this.setOpaque(false);

        this.tilePalette = new TilePalette(name, tileSetImage);
        this.glassLayer = new GlassSelectLayer(tileSetImage.getWidth(), tileSetImage.getHeight(), Color.red, GlassSelectLayer.Fit.EXACT_FIT);
        this.add(glassLayer);
        this.add(tilePalette);
        this.frontier = new Point(tileSetImage.getWidth(), tileSetImage.getHeight());
    }

    public void tileSelectedEvent(Tile tileInfo) {
        if (tilePalette.getName().equals(tileInfo.getTilesetName())) {
            glassLayer.drawSelectOutline(tileInfo.getPoint(), tileInfo.getDimension());
            getParent().repaint();
        }
    }

    public void selectTileAt(java.awt.Point topLeft, Dimension dimension) {
        glassLayer.drawSelectOutline(topLeft, dimension);
        this.repaint();
    }

    public java.awt.Dimension getPreferredSize() {
        return tilePalette.getPreferredSize();
    }


}
