package com.soronthar.rpg.demiurge.devel;

import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.components.tilesets.TilesetsModel;
import com.soronthar.rpg.demiurge.components.tilesets.TilesetsPanel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.TileSetBagPersister;
import com.soronthar.rpg.util.Dimension;
import com.soronthar.rpg.util.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TilesetTester extends JFrame {
    public TilesetTester() throws HeadlessException {
        super("Tileset Tester");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TileSetBag tilesets = new TileSetBagPersister().loadTilesets();

        TilesetsPanel tilesetsPanel = new TilesetsPanel(new TilesetsModel(tilesets));
        this.setLayout(new FlowLayout());

        this.add(tilesetsPanel);
        final TilePanel comp = new TilePanel(tilesets);
        this.add(comp);
        this.pack();
        
       tilesetsPanel.addPropertyChangeListener(TilesetsPanel.TILE, new PropertyChangeListener() {
           @Override
           public void propertyChange(PropertyChangeEvent evt) {
               Tile tile = (Tile) evt.getNewValue();
               comp.setTileToDraw(tile);
               TilesetTester.this.pack();
               TilesetTester.this.repaint();
           }
       });
        
        
    }

    public static void main(String[] args) {
        new TilesetTester().setVisible(true);
    }
    
    private class TilePanel extends JPanel {
        private TileSetBag tileSets;
        private Tile tileToDraw=null;

        private TilePanel(TileSetBag tileSets) {
            this.tileSets = tileSets;
            this.setOpaque(true);
            this.setPreferredSize(Tile.TILE_DIMENSION.toAWT());
        }

        
        public void setTileToDraw(Tile tileToDraw) {
            this.tileToDraw = tileToDraw;
            this.setPreferredSize(tileToDraw.getDimension().toAWT());
            this.setSize(tileToDraw.getDimension().toAWT());
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (tileToDraw!=null) {
                TileSet tileSet = tileSets.get(tileToDraw.getTilesetName());
                Point point = tileToDraw.getPoint();
                Dimension dimension = tileToDraw.getDimension();
                BufferedImage image = tileSet.image().getSubimage(point.getX(), point.getY(), dimension.getWidth(), dimension.getHeight());
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            } else {
                g.setColor(Color.BLUE);
                g.fillRect(0,0,getWidth(),getHeight());
            }
        }
    }
}
