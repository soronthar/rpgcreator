package com.soronthar.rpg.adventure.project.xtream;

import com.soronthar.rpg.adventure.scenery.DrawnTile;
import com.soronthar.rpg.adventure.scenery.Layer;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.soronthar.util.xstream.ConverterAdapter;

public class LayerConverter extends ConverterAdapter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Layer layer = (Layer) o;
        writer.addAttribute("index", Integer.toString(layer.getIndex()));
        for (DrawnTile tile : layer) {
            context.convertAnother(tile);
        }
    }

    public boolean canConvert(Class aClass) {
        return Layer.class.equals(aClass);
    }
}
