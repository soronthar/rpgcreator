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
        writer.addAttribute("solid", Boolean.toString(sprite.isSolid()));
        writer.addAttribute("visible", Boolean.toString(sprite.isVisible()));
        if (sprite instanceof MobNpc) {
            writer.addAttribute("type", "mob");
        } else {
            writer.addAttribute("type", "npc");
        }
        writer.addAttribute("frames", sprite.getFramesImageName());
        writer.addAttribute("can-interact", Boolean.toString(sprite.canInteract()));
        writer.startNode("location");
        writer.addAttribute("x", Integer.toString(sprite.getLocation().x));
        writer.addAttribute("y", Integer.toString(sprite.getLocation().y));
        writer.addAttribute("facing", sprite.getFacing().name());
        writer.endNode();
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        boolean isVisible = Boolean.valueOf(reader.getAttribute("visible"));
        boolean isSolid = Boolean.valueOf(reader.getAttribute("solid"));
        String id = reader.getAttribute("id");
        String type = reader.getAttribute("type");
        String frames = reader.getAttribute("frames");
        String attribute = reader.getAttribute("can-interact");

        reader.moveDown();

        int x = Integer.parseInt(reader.getAttribute("x"));
        int y = Integer.parseInt(reader.getAttribute("y"));
        String facing = reader.getAttribute("facing");
        Sprite sprite;
        if (type.equals("mob")) {
            sprite = new MobNpc(id, new Point(x, y), Facing.valueOf(facing));
        } else {
            sprite = new StandingNpc(id, new Point(x, y), Facing.valueOf(facing));
        }
        sprite.setFramesImage(frames);
        sprite.setCanInteract(Boolean.valueOf(attribute));

        sprite.setVisible(isVisible);
        sprite.setSolid(isSolid);

        reader.moveUp();

        return sprite;
    }

    public boolean canConvert(Class aClass) {
        return Sprite.class.isAssignableFrom(aClass);
    }
}
