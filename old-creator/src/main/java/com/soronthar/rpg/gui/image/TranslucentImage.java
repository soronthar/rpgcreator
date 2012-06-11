package com.soronthar.rpg.gui.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TranslucentImage extends BufferedImage {
    private static final Color TRANSPARENT_BACKGROUND = new Color(0, 0, 0, 0);

    public TranslucentImage(int width, int height) {
        super(width, height, BufferedImage.TYPE_INT_ARGB);
    }


    public Graphics getGraphics() {
        Graphics2D g = (Graphics2D) super.getGraphics();
        g.setComposite(AlphaComposite.Src);
        g.setBackground(TRANSPARENT_BACKGROUND);
        return g;
    }
}
