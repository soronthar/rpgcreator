package com.soronthar.rpg.adventure.project.xtream;

import com.soronthar.rpg.adventure.scenery.objects.actors.*;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.awt.*;

public class SpriteConverter implements Converter {

    public static final String ON_ACTION = "on-action";
    public static final String ACTION_SHOW_TEXT = "show-text";
    public static final String TEXT = "text";

    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Sprite sprite = (Sprite) o;
        writer.startNode("special");
        writer.addAttribute("id", sprite.getId());
        writer.addAttribute("solid", Boolean.toString(sprite.isSolid()));
        writer.addAttribute("visible", Boolean.toString(sprite.isVisible()));
        if (sprite instanceof MobNpc) {
            writer.addAttribute("type", "mob");
        } else {
            writer.addAttribute("type", "npc");
        }
        writer.addAttribute("frames", sprite.getFramesImageName());
        writer.startNode("location");
        writer.addAttribute("x", Integer.toString(sprite.getLocation().x));
        writer.addAttribute("y", Integer.toString(sprite.getLocation().y));
        writer.addAttribute("facing", sprite.getFacing().name());
        writer.endNode();

        writer.startNode(ON_ACTION);
        SpriteActions actions = sprite.getActions();

        for (SpriteActions.SpriteAction spriteAction : actions) {
            if (spriteAction instanceof SpriteActions.ShowText) {
                writer.startNode(ACTION_SHOW_TEXT);
                writer.setValue(((SpriteActions.ShowText) spriteAction).getText());
                writer.endNode();
            }
        }
        writer.endNode();

        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        boolean isVisible = Boolean.valueOf(reader.getAttribute("visible"));
        boolean isSolid = Boolean.valueOf(reader.getAttribute("solid"));
        String id = reader.getAttribute("id");
        String type = reader.getAttribute("type");
        String frames = reader.getAttribute("frames");

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

        sprite.setVisible(isVisible);
        sprite.setSolid(isSolid);

        reader.moveUp();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            if (reader.getNodeName().equals(ON_ACTION)) {
                while (reader.hasMoreChildren()) {
                    reader.moveDown();
                    if (reader.getNodeName().equals(ACTION_SHOW_TEXT)) {
                        sprite.getActions().add(new SpriteActions.ShowText(reader.getValue()));
                    }
                    reader.moveUp();
                }
            }
            reader.moveUp();
        }

        return sprite;
    }

    public boolean canConvert(Class aClass) {
        return Sprite.class.isAssignableFrom(aClass);
    }
}
