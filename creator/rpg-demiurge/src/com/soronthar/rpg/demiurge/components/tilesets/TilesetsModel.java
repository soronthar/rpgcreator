package com.soronthar.rpg.demiurge.components.tilesets;

import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.components.BaseComponentModel;

public class TilesetsModel extends BaseComponentModel {
    public static final String ALL_TILESETS = "ALLTILESETS";
    TileSetBag tileSets;

    public TilesetsModel(TileSetBag tileSets) {
        this.tileSets = tileSets;
    }

    public TilesetsModel() {
        this(new TileSetBag());
    }

    public TileSetBag getTileSets() {
        return tileSets;
    }

    public void setTileSets(TileSetBag tileSets) {
        this.tileSets=tileSets;
        firePropertyChange(ALL_TILESETS, false, true);
    }

}
