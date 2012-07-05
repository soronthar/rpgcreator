package com.soronthar.rpg.adventure.tileset;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TileSetBag implements Iterable<TileSet>, Cloneable {
    private Map<String, TileSet> map = new LinkedHashMap<String, TileSet>();

    public TileSetBag() {
    }

    private TileSetBag(Map<String, TileSet> map) {
        this.map.putAll(map);
    }

    public TileSet get(String name) {
        return map.get(name);
    }

    public void put(TileSet tileSet) {
        map.put(tileSet.getName(), tileSet);
    }

    public Iterator<TileSet> iterator() {
        return map.values().iterator();
    }

    public int size() {
        return map.size();
    }

    @Override
    public TileSetBag clone() {
        return new TileSetBag(this.map);
    }
}
