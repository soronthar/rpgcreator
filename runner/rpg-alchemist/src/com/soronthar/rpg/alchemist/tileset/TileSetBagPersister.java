package com.soronthar.rpg.alchemist.tileset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;

public class TileSetBagPersister {

    public TileSetMap fillTilesetBag(TileSetBag bag) {
        TileSetMap tileSets = new TileSetMap();
        Pixmap image;
        for (TileSet tileSet : bag) {
            image = new Pixmap(Gdx.files.internal("projects/FirstProject/tilesets/" + tileSet.getResourceName()));
            TileSetMap.TileSetData tileSetData = new TileSetMap.TileSetData(tileSet.getName(), image);
            tileSets.put(tileSetData);
        }
        return tileSets;
    }
}
