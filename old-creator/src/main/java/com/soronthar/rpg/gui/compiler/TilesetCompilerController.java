package com.soronthar.rpg.gui.compiler;

import com.soronthar.rpg.ImageLoader;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;

public class TilesetCompilerController extends Controller {

    public TilesetCompilerController(Model model) {
        super(model);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void loadTilesets() {
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
            TileSet tileSet = new TileSet(tilesetFile.getName(), image);
            model.getTilesets().put(tileSet);
        }
        tilesetsPanel.setTileSets(model.getTilesets());

    }


    public BufferedImage getTilesetImage() {
        return this.getPaintPanel().getCanvas().getLayerImage(0);
    }
}
