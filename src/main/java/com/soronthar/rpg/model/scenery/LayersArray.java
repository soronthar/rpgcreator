package com.soronthar.rpg.model.scenery;

import java.util.Iterator;

public class LayersArray implements Iterable<Layer> {
    public static final String[] LAYER_NAMES = {"Base Layer", "Grass Layer", "Objects Layer", "Sprite Layer", "Above Layer"};
    public static final int LAYER_COUNT = LAYER_NAMES.length;
    public static final int SPRITE_LAYER_INDEX = 3;

    private Layer[] layers = new Layer[LAYER_COUNT];

    public LayersArray() {
        int length = layers.length;
        for (int i = 0; i < length; i++) {
            layers[i] = new Layer(i);
        }
    }

    public int size() {
        return layers.length;
    }

    public Layer layerAt(int i) {
        return layers[i];
    }

    public Iterator<Layer> iterator() {
        return new LayerIterator(this);
    }

    private class LayerIterator implements Iterator<Layer> {
        LayersArray layers;
        private int currentIndex;

        LayerIterator(LayersArray layers) {
            this.layers = layers;
            this.currentIndex = 0;
        }

        public boolean hasNext() {
            return currentIndex < layers.size();
        }

        public Layer next() {
            return this.layers.layerAt(this.currentIndex++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
