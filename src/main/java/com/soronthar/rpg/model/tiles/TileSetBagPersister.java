package com.soronthar.rpg.model.tiles;

import com.soronthar.rpg.ImageLoader;
import org.soronthar.error.ExceptionHandler;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class TileSetBagPersister {

    public TileSetBag loadTilesets() {
        TileSetBag tileSets = new TileSetBag();

        try {
            Properties tilesetsDef = new Properties();
            tilesetsDef.load(new FileInputStream("tileset.properties"));
            BufferedImage image;

            Enumeration<?> enumeration = tilesetsDef.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                image = new ImageLoader().load("resources/" + tilesetsDef.getProperty(key));
                TileSet tileSet = new TileSet(key, image);
                tileSets.put(tileSet);
            }
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
        return tileSets;
    }
}
