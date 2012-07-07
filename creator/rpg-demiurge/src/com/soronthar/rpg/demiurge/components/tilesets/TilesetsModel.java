package com.soronthar.rpg.demiurge.components.tilesets;

import com.soronthar.rpg.adventure.tileset.TileSetBag;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TilesetsModel {
    public static final String ALL_TILESETS = "ALLTILESETS";
    TileSetBag tileSets;
    PropertyChangeSupport pcs=new PropertyChangeSupport(this);

    public TilesetsModel(TileSetBag tileSets) {
        this.tileSets = tileSets;
    }

    public TilesetsModel() {
        this(new TileSetBag());
    }

    public TileSetBag getTileSets() {
        return tileSets;
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(name,listener);
    }
    public void removePropertyChangeListener(String name, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(name,listener);
    }

    public void setTileSets(TileSetBag tileSets) {
        this.tileSets=tileSets;
        pcs.firePropertyChange(ALL_TILESETS,false,true);

    }
}
