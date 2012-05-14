package com.soronthar.rpg.model.project.xtream;

import com.soronthar.rpg.model.objects.sprites.Facing;
import com.soronthar.rpg.model.objects.sprites.MobNpc;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.objects.sprites.StandingNpc;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.awt.*;

public class SpriteConverter implements Converter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Sprite sprite = (Sprite) o;
        writer.startNode("sprite");
        writer.addAttribute("id", sprite.getId());
        writer.addAttribute("facing", sprite.getFacing().name());
        writer.addAttribute("x", Integer.toString(sprite.getLocation().x));
        writer.addAttribute("y", Integer.toString(sprite.getLocation().y));
        writer.addAttribute("solid", Boolean.toString(sprite.isSolid()));
        writer.addAttribute("visible", Boolean.toString(sprite.isVisible()));
        if (sprite instanceof MobNpc) {
            writer.addAttribute("type", "mob");
            writer.addAttribute("frames", ((MobNpc) sprite).getFramesImageName());
        } else {
            writer.addAttribute("type", "npc");
            writer.addAttribute("frames", ((StandingNpc) sprite).getFramesImageName());
        }
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        int x = Integer.parseInt(reader.getAttribute("x"));
        int y = Integer.parseInt(reader.getAttribute("y"));
        boolean isVisible = Boolean.valueOf(reader.getAttribute("visible"));
        boolean isSolid = Boolean.valueOf(reader.getAttribute("solid"));
        String facing = reader.getAttribute("facing");
        String id = reader.getAttribute("id");
        String type = reader.getAttribute("type");
        Sprite sprite;
        if (type.equals("mob")) {
            sprite = new MobNpc(id, new Point(x, y), Facing.valueOf(facing));
            ((MobNpc) sprite).setFramesImage(reader.getAttribute("frames"));
        } else {
            sprite = new StandingNpc(id, new Point(x, y), Facing.valueOf(facing));
            ((StandingNpc) sprite).setFramesImage(reader.getAttribute("frames"));
        }

        sprite.setVisible(isVisible);
        sprite.setSolid(isSolid);
        return sprite;
    }

    public boolean canConvert(Class aClass) {
        return Sprite.class.isAssignableFrom(aClass);
    }
}
