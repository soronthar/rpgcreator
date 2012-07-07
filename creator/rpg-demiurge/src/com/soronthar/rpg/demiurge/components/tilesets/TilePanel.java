package com.soronthar.rpg.demiurge.components.tilesets;

import com.soronthar.rpg.demiurge.legacy.gui.builder.components.tiles.GlassSelectLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


class TilePanel extends JPanel {
    private TilePalette tilePalette;
    private GlassSelectLayer glassLayer;

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
    }

    public void selectTileAt(Point topLeft, Dimension dimension) {
        glassLayer.drawSelectOutline(topLeft, dimension);
        this.repaint();
    }

    public Dimension getPreferredSize() {
        return tilePalette.getPreferredSize();
    }
}
