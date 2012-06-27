package com.soronthar.rpg.alchemist.tileset;


import com.badlogic.gdx.graphics.Pixmap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TileSetMap implements Iterable<TileSetMap.TileSetData> {
    private Map<String, TileSetData> map = new LinkedHashMap<String, TileSetData>();

    public Pixmap get(String name) {
        return map.get(name).image();
    }

    public void put(TileSetData tileSet) {
        map.put(tileSet.getName(), tileSet);
    }

    public Iterator<TileSetData> iterator() {
        return map.values().iterator();
    }

    public static class TileSetData {
        private Pixmap image;
        private String name;

        public TileSetData(String name, Pixmap image) {
            this.image = image;
            this.name = name;
        }

        public Pixmap image() {
            return image;
        }

        public String getName() {
            return name;
        }
    }
}
