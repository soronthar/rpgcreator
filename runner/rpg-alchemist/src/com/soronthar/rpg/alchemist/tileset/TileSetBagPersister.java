package com.soronthar.rpg.alchemist.tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import org.soronthar.error.ExceptionHandler;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class TileSetBagPersister {

    public TileSetBag loadTilesets() {
        TileSetBag tileSets = new TileSetBag();
        try {
            Properties tilesetsDef = new Properties();
            tilesetsDef.load(Gdx.files.internal("projects/FirstProject/tilesets/tilesets.properties").reader());
            Pixmap image;

            Enumeration<?> enumeration = tilesetsDef.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                image = new Pixmap(Gdx.files.internal("projects/FirstProject/tilesets/"+tilesetsDef.getProperty(key)));
                TileSet tileSet = new TileSet(key, image);
                tileSets.put(tileSet);
            }
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
        return tileSets;
    }
}
