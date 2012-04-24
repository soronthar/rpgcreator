package com.soronthar.rpg.model.project.xtream;

import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.scenery.DrawnTile;
import com.soronthar.rpg.model.scenery.Scenery;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.soronthar.geom.Dimension;

import java.awt.*;
import java.util.Collection;

public class SceneryConverter implements Converter {

    private static final String HERO_STARTING_POINT = "heroStartingPoint";
    private static final String X_COORD = "x";
    private static final String Y_COORD = "y";
    private static final String JUMP_POINT = "jumpPoint";
    private static final String TARGET = "target";
    private static final String OBSTACLE = "obstacle";
    private static final String SPRITE = "sprite";
    private static final String NAME = "name";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String LAYER = "layer";
    private static final String INDEX = "index";

    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Scenery scenery = (Scenery) o;
        writer.addAttribute(NAME, scenery.getName());
        writer.addAttribute(WIDTH, Integer.toString(scenery.getWidth()));
        writer.addAttribute(HEIGHT, Integer.toString(scenery.getHeight()));
        context.convertAnother(scenery.getLayers());

        Collection<Point> obstacles = scenery.getObstacles();
        for (Point obstacle : obstacles) {
            writer.startNode(OBSTACLE);
            writer.addAttribute(X_COORD, Integer.toString(obstacle.x));
            writer.addAttribute(Y_COORD, Integer.toString(obstacle.y));
            writer.endNode();
        }

        for (Sprite sprite : scenery.getSprites().values()) {
            context.convertAnother(sprite);
        }


        for (JumpPoint jump : scenery.getJumpPoints()) {
            writer.startNode(JUMP_POINT);
            writer.addAttribute(X_COORD, Integer.toString(jump.getLocation().x));
            writer.addAttribute(Y_COORD, Integer.toString(jump.getLocation().y));
            writer.addAttribute(TARGET, jump.getTargetName());
            writer.endNode();
        }


        writer.startNode(HERO_STARTING_POINT);
        writer.addAttribute(X_COORD, Integer.toString(scenery.getHeroStartingPoint().x));
        writer.addAttribute(Y_COORD, Integer.toString(scenery.getHeroStartingPoint().y));
        writer.endNode();

    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

        String name = reader.getAttribute(NAME);
        Scenery scenery = new Scenery(name);

        String width = reader.getAttribute(WIDTH);
        String height = reader.getAttribute(HEIGHT);
        scenery.setDimension(new Dimension(Integer.parseInt(width), Integer.parseInt(height)));
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            if (reader.getNodeName().equals(LAYER)) {
                int index = Integer.parseInt(reader.getAttribute(INDEX));

                while (reader.hasMoreChildren()) {
                    reader.moveDown();
                    DrawnTile drawnTile = (DrawnTile) context.convertAnother(scenery.getLayer(index), DrawnTile.class);
                    scenery.setTile(drawnTile.getTile(), index, drawnTile.getPoint());
                    reader.moveUp();
                }
            } else if (reader.getNodeName().equals(SPRITE)) {
                Sprite sprite = (Sprite) context.convertAnother(scenery, Sprite.class);
                scenery.addSprite(sprite);
            } else if (reader.getNodeName().equals(OBSTACLE)) {
                int x = Integer.parseInt(reader.getAttribute(X_COORD));
                int y = Integer.parseInt(reader.getAttribute(Y_COORD));
                scenery.addObstacleAt(new Point(x, y));
            } else if (reader.getNodeName().equals(HERO_STARTING_POINT)) {
                int x = Integer.parseInt(reader.getAttribute(X_COORD));
                int y = Integer.parseInt(reader.getAttribute(Y_COORD));
                scenery.setHeroStartingPoint(new Point(x, y));
            } else if (reader.getNodeName().equals(JUMP_POINT)) {
                int x = Integer.parseInt(reader.getAttribute(X_COORD));
                int y = Integer.parseInt(reader.getAttribute(Y_COORD));
                String target = reader.getAttribute(TARGET);
                JumpPoint jump = new JumpPoint(new Point(x, y), target);
                scenery.addJumpPoint(jump);
            }
            reader.moveUp();
        }

        return scenery;
    }

    public boolean canConvert(Class aClass) {
        return Scenery.class.equals(aClass);
    }
}
