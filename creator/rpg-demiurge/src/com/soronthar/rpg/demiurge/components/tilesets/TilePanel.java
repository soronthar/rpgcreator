package com.soronthar.rpg.demiurge.components.tilesets;

import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.demiurge.components.GlassSelectLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


class TilePanel extends JPanel {
    private TilePalette tilePalette = new TilePalette("", new BufferedImage(512, 1024, BufferedImage.TYPE_4BYTE_ABGR));
    private GlassSelectLayer glassLayer = new GlassSelectLayer(512, 1024, Color.red, GlassSelectLayer.Fit.EXACT_FIT);


    public TilePanel() {
        this.setLayout(new OverlayLayout(this));
        //This is needed to prevent "garbage" in the rendering
        //See http://java.sun.com/products/jfc/tsc/articles/painting/index.html
        //for a complete explanation
        this.setOpaque(false);

        setEnabled(false);
    }

    public void selectTileAt(Point topLeft, Dimension dimension) {
        glassLayer.drawSelectOutline(topLeft, dimension);
        this.repaint();
    }

    private void setTilesetImage(String name, BufferedImage tileSetImage) {
        this.remove(tilePalette);
        this.remove(glassLayer);
        setEnabled(true);
        this.tilePalette = new TilePalette(name, tileSetImage);
        this.glassLayer = new GlassSelectLayer(tileSetImage.getWidth(), tileSetImage.getHeight(), Color.red, GlassSelectLayer.Fit.EXACT_FIT);
        this.setName(name);
        this.add(glassLayer);
        this.add(tilePalette);
        this.setPreferredSize(new Dimension(tileSetImage.getWidth(), tileSetImage.getHeight()));
        this.repaint();
    }

    public Dimension getPreferredSize() {
        return tilePalette.getPreferredSize();
    }

    public void setTileset(TileSet tileSet) {
        this.setTilesetImage(tileSet.getName(), tileSet.image());
    }

}
