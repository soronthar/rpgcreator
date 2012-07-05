package com.soronthar.rpg.demiurge.legacy.gui.builder;

import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.legacy.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

public class TileSetBagPersister {

    public TileSetBag loadTilesets() {
        TileSetBag tileSets = new TileSetBag();

        File dir = new ImageLoader().getDirectoryFor("tilesets");

        BufferedImage image;

        File[] tilesetFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });

        for (File tilesetFile : tilesetFiles) {
            String resourceName = tilesetFile.getName();
            image = new ImageLoader().load("tilesets/" + resourceName);
            TileSet tileSet = new TileSet(resourceName, resourceName, image);
            tileSets.put(tileSet);
        }

        return tileSets;
    }
}
