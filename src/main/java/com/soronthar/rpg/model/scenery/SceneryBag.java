package com.soronthar.rpg.model.scenery;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class SceneryBag implements Serializable, Iterable<Scenery> {
    private LinkedHashMap<Long, Scenery> map = new LinkedHashMap<Long, Scenery>();

    public Scenery get(long name) {
        return map.get(name);
    }

    public void put(Scenery scenery) {
        map.put(scenery.getId(), scenery);
    }

    public Iterator<Scenery> iterator() {
        return map.values().iterator();
    }

    public long size() {
        return map.size();
    }
}
