package com.soronthar.rpg.demiurge.legacy.gui.compiler;

import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.legacy.ImageLoader;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Controller;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

public class TilesetCompilerController extends Controller {

    public TilesetCompilerController(Model model) {
        super(model);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public TileSetBag loadTilesets() {
        File dir = new ImageLoader().getDirectoryFor("tilesets");

        BufferedImage image;

        File[] tilesetFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".png");
            }
        });

        for (File tilesetFile : tilesetFiles) {
            image = new ImageLoader().load("tilesets/" + tilesetFile.getName());
            TileSet tileSet = new TileSet(tilesetFile.getName(), tilesetFile.getName(),image);
            model.getTilesets().put(tileSet);
        }
        return model.getTilesets();

    }
}
