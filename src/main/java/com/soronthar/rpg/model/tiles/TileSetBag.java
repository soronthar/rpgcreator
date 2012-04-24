package com.soronthar.rpg.model.tiles;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TileSetBag implements Iterable<TileSet> {
    private Map<String, TileSet> map = new LinkedHashMap<String, TileSet>();

    public TileSet get(String name) {
        return map.get(name);
    }

    public void put(TileSet tileSet) {
        map.put(tileSet.getName(), tileSet);
    }

    public Iterator<TileSet> iterator() {
        return map.values().iterator();
    }
}
