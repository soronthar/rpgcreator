package com.soronthar.rpg.model.objects.sprites.frames;

import com.soronthar.rpg.ImageLoader;
import com.soronthar.rpg.gui.image.TranslucentImage;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WholeImageFrameStrategy implements FrameStrategy {
    private BufferedImage spriteImageMap;
    private final String imageName;

    public WholeImageFrameStrategy(String imageName) {
        this.imageName = imageName;
        this.spriteImageMap = new ImageLoader().load("sprites/"+this.imageName);

    }

    @Override
    public Image getFrame(Sprite sprite) {
        BufferedImage image = new TranslucentImage(Tile.TILE_SIZE, Tile.TILE_SIZE);
        Graphics g = image.getGraphics();
        int xPos = sprite.getFacing().getXPosInAnim();
        int yPos;
        if (sprite.isSpeedZero()) {
            yPos = 0;
        } else {
            yPos = (sprite.getSteps() % 2) + 1;
        }

        BufferedImage frame = this.spriteImageMap.getSubimage(xPos * Tile.TILE_SIZE, yPos * Tile.TILE_SIZE, Tile.TILE_SIZE, Tile.TILE_SIZE);
        g.drawImage(frame, 0, 0, null);
        g.dispose();

        return image;

    }

    public String getImageName() {
        return imageName;
    }
}
