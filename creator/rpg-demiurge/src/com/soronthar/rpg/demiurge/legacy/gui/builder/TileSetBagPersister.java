package com.soronthar.rpg.demiurge.legacy.gui.builder;

import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.legacy.ImageLoader;
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
                String resourceName = tilesetsDef.getProperty(key);
                image = new ImageLoader().load("tilesets/" + resourceName);
                TileSet tileSet = new TileSet(key, resourceName,image);
                tileSets.put(tileSet);
            }
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
        return tileSets;
    }
}
