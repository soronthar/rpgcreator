package com.soronthar.rpg.adventure.project.xtream;

import com.soronthar.rpg.adventure.scenery.Layer;
import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.soronthar.util.xstream.ConverterAdapter;

public class LayerArrayConverter extends ConverterAdapter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        LayersArray layersArray = (LayersArray) o;
        for (Layer layer : layersArray) {
            if (layer.isEmpty()) continue;

            writer.startNode("layer");
            context.convertAnother(layer);
            writer.endNode();
        }
    }

    public boolean canConvert(Class aClass) {
        return LayersArray.class.equals(aClass);
    }
}
