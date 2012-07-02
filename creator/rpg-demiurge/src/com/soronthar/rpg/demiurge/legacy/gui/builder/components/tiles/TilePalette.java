package com.soronthar.rpg.demiurge.legacy.gui.builder.components.tiles;

import com.soronthar.rpg.adventure.tileset.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


class TilePalette extends JPanel {
    private BufferedImage tileSet;

    public TilePalette(String name, BufferedImage tileSet) {
        this.tileSet = tileSet;
        this.setName(name);
    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, tileSet.getWidth(), tileSet.getHeight());
        g.drawImage(tileSet, 0, 0, null);
    }

    public BufferedImage getTile(int x, int y) {
        return tileSet.getSubimage(x, y, Tile.TILE_SIZE, Tile.TILE_SIZE);
    }

    public Dimension getPreferredSize() {
        return new Dimension(tileSet.getWidth(), tileSet.getHeight());
    }
}
