package com.soronthar.rpg.model.project.xtream;

import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class SceneryBagConverter implements Converter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        SceneryBag bag = (SceneryBag) o;
        for (Scenery scenery : bag) {
            writer.startNode("scenery");
            context.convertAnother(scenery);
            writer.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        SceneryBag bag = new SceneryBag();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            Scenery scenery = (Scenery) context.convertAnother(bag, Scenery.class);
            bag.put(scenery);
            reader.moveUp();
        }
        return bag;
    }

    public boolean canConvert(Class aClass) {
        return SceneryBag.class.equals(aClass);

    }
}
