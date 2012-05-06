package com.soronthar.rpg.model.project.xtream;

import com.soronthar.rpg.model.objects.sprites.Facing;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.awt.*;

public class SpriteConverter implements Converter {
    //            <sprite facing="UP" x="3" y="0"/>
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Sprite sprite = (Sprite) o;
        writer.startNode("sprite");
        writer.addAttribute("id", sprite.getId());
        writer.addAttribute("facing", sprite.getFacing().name());
        writer.addAttribute("x", Integer.toString(sprite.getLocation().x));
        writer.addAttribute("y", Integer.toString(sprite.getLocation().y));
        writer.addAttribute("solid", Boolean.toString(sprite.isSolid()));
        writer.addAttribute("visible", Boolean.toString(sprite.isVisible()));
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        int x = Integer.parseInt(reader.getAttribute("x"));
        int y = Integer.parseInt(reader.getAttribute("y"));
        String facing = reader.getAttribute("facing");
        String id = reader.getAttribute("id");
        Sprite sprite;
        sprite = new Sprite(id, new Point(x, y), Facing.valueOf(facing));
        return sprite;
    }

    public boolean canConvert(Class aClass) {
        return Sprite.class.isAssignableFrom(aClass);
    }
}
