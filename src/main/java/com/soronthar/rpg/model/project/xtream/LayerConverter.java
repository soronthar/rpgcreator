package com.soronthar.rpg.model.project.xtream;

import com.soronthar.rpg.model.scenery.DrawnTile;
import com.soronthar.rpg.model.scenery.Layer;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LayerConverter implements Converter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Layer layer = (Layer) o;
        writer.addAttribute("index", Integer.toString(layer.getIndex()));
        for (DrawnTile tile : layer) {
            context.convertAnother(tile);
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean canConvert(Class aClass) {
        return Layer.class.equals(aClass);
    }
}
