package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.ImageLoader;
import com.soronthar.rpg.gui.image.TranslucentImage;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Hero extends MoveableSprite {
    private BufferedImage spriteImages;


    public Hero(Point location, Rectangle bound) {
        super(location, bound);
        this.spriteImages = new ImageLoader().load("hero.png");
    }

    public void setSpeed(int dx, int dy) {
        if (facing == Facing.UP || facing == Facing.DOWN) {
            if (dy != 0) {
                this.dy = dy;
                this.dx = 0;
            } else {
                this.dy = 0;
                this.dx = dx;
            }
        }


        if (facing == Facing.LEFT || facing == Facing.RIGHT) {
            if (dx != 0) {
                this.dx = dx;
                this.dy = 0;
            } else {
                this.dx = 0;
                this.dy = dy;
            }
        }
    }

    public Image getFrame() {
        BufferedImage image = new TranslucentImage(Tile.TILE_SIZE, Tile.TILE_SIZE);
        Graphics g = image.getGraphics();
        int xPos = getFacing().getXPosInAnim();
        int yPos;
        if (isSpeedZero()) {
            yPos = 0;
        } else {
            yPos = (getSteps() % 2) + 1;
        }

        BufferedImage frame = this.spriteImages.getSubimage(xPos * Tile.TILE_SIZE, yPos * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
        g.drawImage(frame, 0, 0, null);
        g.dispose();

        return image;
    }

    public void update(long elapsedTime) {
        determineFacing();
        move();
    }
}
