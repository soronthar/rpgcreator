package com.soronthar.rpg.adventure.project.xtream;

import com.soronthar.rpg.adventure.scenery.DrawnTile;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.utils.Point;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.soronthar.geom.Dimension;

import java.util.Collection;

public class SceneryConverter implements Converter {

    private static final String HERO_STARTING_POINT = "heroStartingPoint";
    private static final String X_COORD = "x";
    private static final String Y_COORD = "y";
    private static final String JUMP_POINT = "jumpPoint";
    private static final String TARGET = "target";
    private static final String OBSTACLE = "obstacle";
    private static final String SPECIAL = "special";
    private static final String NAME = "name";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String LAYER = "layer";
    private static final String INDEX = "index";
    private static final String ID = "id";

    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        Scenery scenery = (Scenery) o;
        writer.addAttribute(ID, Long.toString(scenery.getId()));
        writer.addAttribute(NAME, scenery.getName());
        writer.addAttribute(WIDTH, Integer.toString(scenery.getWidth()));
        writer.addAttribute(HEIGHT, Integer.toString(scenery.getHeight()));
        context.convertAnother(scenery.getLayers());

        Collection<Point> obstacles = scenery.getObstacles();
        for (Point obstacle : obstacles) {
            writer.startNode(OBSTACLE);
            writer.addAttribute(X_COORD, Integer.toString(obstacle.getX()));
            writer.addAttribute(Y_COORD, Integer.toString(obstacle.getY()));
            writer.endNode();
        }

        for (Sprite sprite : scenery.getSprites()) {
            context.convertAnother(sprite);
        }


        for (JumpPoint jump : scenery.getJumpPoints()) {
            writer.startNode(JUMP_POINT);
            writer.addAttribute(X_COORD, Integer.toString(jump.getLocation().getX()));
            writer.addAttribute(Y_COORD, Integer.toString(jump.getLocation().getY()));
            writer.addAttribute(TARGET, Long.toString(jump.getTargetId()));
            writer.endNode();
        }


        writer.startNode(HERO_STARTING_POINT);
        writer.addAttribute(X_COORD, Integer.toString(scenery.getHeroStartingPoint().getX()));
        writer.addAttribute(Y_COORD, Integer.toString(scenery.getHeroStartingPoint().getY()));
        writer.endNode();

    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

        String name = reader.getAttribute(NAME);
        String id = reader.getAttribute(ID);
        Scenery scenery = new Scenery(Long.parseLong(id), name);

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
            } else if (reader.getNodeName().equals(SPECIAL)) {
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
                JumpPoint jump = new JumpPoint(new Point(x, y), Long.parseLong(target));
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
