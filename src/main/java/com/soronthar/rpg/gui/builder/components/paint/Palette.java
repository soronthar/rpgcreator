package com.soronthar.rpg.gui.builder.components.paint;

import com.soronthar.rpg.gui.image.TranslucentImage;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Palette {
    public static BufferedImage createSpriteDrawingPen() {
        BufferedImage image = new TranslucentImage(Tile.TILE_SIZE, Tile.TILE_SIZE);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, Tile.TILE_SIZE - 1, Tile.TILE_SIZE - 1);
        for (int x = 0; x < Tile.TILE_SIZE; x++) {
            for (int y = x % 2; y < Tile.TILE_SIZE; y += 2) {
                g.drawLine(x, y, x, y);
            }
        }
        g.dispose();
        return image;
    }

    private static BufferedImage drawSmallSquare(int padding, Color color) {
        BufferedImage image = new TranslucentImage(Tile.TILE_SIZE, Tile.TILE_SIZE);
        Graphics g = image.getGraphics();
        g.setColor(color);
        int sideSize = Tile.TILE_SIZE - (padding * 2) - 1;
        g.drawRect(padding, padding, sideSize, sideSize);
        g.setColor(color);

        for (int x = padding; x < Tile.TILE_SIZE - padding - 1; x++) {
            for (int y = padding + (x % 2); y < Tile.TILE_SIZE - padding; y += 2) {
                g.drawLine(x, y, x, y);
            }
        }
        g.dispose();
        return image;
    }


    public static BufferedImage createObstacleDrawingPen() {
        return drawSmallSquare(10, Color.red);
    }


    public static BufferedImage createHeroStartDrawingPen() {
        return drawSmallSquare(10, Color.blue);
    }

    public static BufferedImage createJumpPointDrawingPen() {
        return drawSmallSquare(10, Color.yellow);
    }
}
