package com.soronthar.rpg.model.objects.sprites.frames;

import com.soronthar.rpg.gui.image.TranslucentImage;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;

public class NullFrameStrategy implements FrameStrategy {
    @Override
    public Image getFrame(Sprite sprite) {
        return new TranslucentImage(Tile.TILE_SIZE, Tile.TILE_SIZE);
    }
}
