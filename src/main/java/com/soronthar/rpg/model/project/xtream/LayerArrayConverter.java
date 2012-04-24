package com.soronthar.rpg.model.project.xtream;

import com.soronthar.rpg.model.scenery.Layer;
import com.soronthar.rpg.model.scenery.LayersArray;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LayerArrayConverter implements Converter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        LayersArray layersArray = (LayersArray) o;
        for (Layer layer : layersArray) {
            if (layer.isEmpty()) continue;

            writer.startNode("layer");
            context.convertAnother(layer);
            writer.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canConvert(Class aClass) {
        return LayersArray.class.equals(aClass);
    }
}
