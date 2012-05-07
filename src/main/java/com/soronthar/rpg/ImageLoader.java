package com.soronthar.rpg;

import com.sixlegs.png.PngImage;
import org.soronthar.error.TechnicalException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public static ImageLoader NOP_IMAGELOADER = new ImageLoader() {
        @Override
        public BufferedImage load(String fileName) {
            return new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        }
    };

    public BufferedImage load(String fileName) {
        BufferedImage image;
        try {
            image = new PngImage().read(new java.io.File("resources/" + fileName));
        } catch (IOException e) {
            throw new TechnicalException(e);
        }
        return image;
    }
}
