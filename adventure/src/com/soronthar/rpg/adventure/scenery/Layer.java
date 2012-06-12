package com.soronthar.rpg.adventure.scenery;


import java.awt.*;
import java.util.Iterator;
import java.util.LinkedHashMap;

//TODO: Sprites per layer
public class Layer implements Iterable<DrawnTile> {
    LinkedHashMap<Point, DrawnTile> drawnTiles = new LinkedHashMap<Point, DrawnTile>();
    private int index;

    public Layer(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Iterator<DrawnTile> iterator() {
        return drawnTiles.values().iterator();
    }

    public void addTile(DrawnTile drawnTile) {
        this.drawnTiles.put(drawnTile.getPoint(), drawnTile);
    }

    public void removeTileAt(Point p) {
        this.drawnTiles.remove(p);
    }

    public boolean isEmpty() {
        return this.drawnTiles.isEmpty();
    }

    public DrawnTile getTileAt(Point point) {
        return this.drawnTiles.get(point);
    }
}
