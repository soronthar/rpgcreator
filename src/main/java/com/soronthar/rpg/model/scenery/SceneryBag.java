package com.soronthar.rpg.model.scenery;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class SceneryBag implements Serializable, Iterable<Scenery> {
    private LinkedHashMap<String, Scenery> map = new LinkedHashMap<String, Scenery>();

    public Scenery get(String name) {
        return map.get(name);
    }

    public void put(Scenery scenery) {
        map.put(scenery.getName(), scenery);
    }

    public Iterator<Scenery> iterator() {
        return map.values().iterator();
    }

    public long size() {
        return map.size();
    }
}
