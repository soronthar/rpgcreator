package com.soronthar.rpg.persister;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.OrderedMap;
import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Facing;
import com.soronthar.rpg.adventure.scenery.objects.actors.MobNpc;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.adventure.scenery.objects.actors.StandingNpc;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.util.Dimension;
import com.soronthar.rpg.util.Point;

import java.io.Reader;
import java.util.Iterator;

//TODO: there is duplication between this class and ProjectReader
public class SceneryReader {

    public Scenery read(Scenery scenery, Reader reader) {
        OrderedMap map = parseSceneryDataFromJSON(reader);
        return fillSceneryData(scenery, map);
    }

    public Scenery read(Reader reader) {
        OrderedMap map = parseSceneryDataFromJSON(reader);

        long id = Long.parseLong((String) map.get("id"));
        String name = (String) map.get("name");
        Scenery scenery=new Scenery(id,name);
        return fillSceneryData(scenery, map);
    }

    private OrderedMap parseSceneryDataFromJSON(Reader reader) {
        JsonReader jsonReader=new JsonReader();
        return (OrderedMap) jsonReader.parse(reader);
    }

    private Scenery fillSceneryData(Scenery scenery, OrderedMap data) {
        scenery.setDimension(parseDimension((String) data.get("size")));

        int index=0;
        for (OrderedMap layerInfo : (Array<OrderedMap>) data.get("layers")) {
            for (OrderedMap tileInfo : (Array<OrderedMap>) layerInfo.get("tiles")) {
                String tilesetName= (String) tileInfo.get("tileSetName");
                Dimension dimension= parseDimension((String) tileInfo.get("dimension"));

                Point tilePos= parsePoint((String) tileInfo.get("tile"));

                Tile tile=new Tile(tilesetName, tilePos, dimension);

                Array<String> points= (Array<String>) tileInfo.get("pos");
                for (Iterator<String> iterator = points.iterator(); iterator.hasNext(); ) {
                    Point point = parsePoint(iterator.next());
                    scenery.setTile(tile, index, point);
                }
            }
            index++;
        }

        Array<String> obstacles = (Array<String>) data.get("obstacles");
        for (Iterator<String> iterator = obstacles.iterator(); iterator.hasNext(); ) {
            String obstacle = iterator.next();
            scenery.addObstacleAt(convert(parsePoint(obstacle)));
        }

        Array<OrderedMap> jumps = (Array<OrderedMap>) data.get("jumps");
        for (Iterator iterator = jumps.iterator(); iterator.hasNext(); ) {
            OrderedMap jumpInfo = (OrderedMap) iterator.next();
            Point pos = convert(parsePoint((String) jumpInfo.get("pos")));
            long target = Long.parseLong((String) jumpInfo.get("target"));
            scenery.addJumpPoint(new JumpPoint(pos, target));
        }

//        scenery.setHeroStartingPoint(parsePoint((String)data.get("heroStartingPoint")));
        scenery.setHeroStartingPoint(convert(parsePoint((String)data.get("heroStartingPoint"))));

        Array<OrderedMap> sprites = (Array<OrderedMap>) data.get("sprites");
        for (Iterator<OrderedMap> iterator = sprites.iterator(); iterator.hasNext(); ) {
            OrderedMap spriteInfo = iterator.next();
            String spriteId= (String) spriteInfo.get("id");
            boolean isSolid=Boolean.valueOf((String) spriteInfo.get("solid"));
            boolean isVisible=Boolean.valueOf((String) spriteInfo.get("visible"));
            String type= (String) spriteInfo.get("type");
            String frames= (String) spriteInfo.get("frames");
            Point point=convert(parsePoint((String) spriteInfo.get("pos")));
            Facing facing=Facing.valueOf((String) spriteInfo.get("facing"));
            Sprite sprite;
            if (type.equals("npc")) {
                sprite = new StandingNpc(spriteId, point,facing);
            } else {
                sprite = new MobNpc(spriteId, point,facing);
            }
            sprite.setFramesImage(frames);
            sprite.setSolid(isSolid);
            sprite.setVisible(isVisible);
            scenery.addSprite(sprite);
        }

        return scenery;
    }

    private Point convert(Point point) {
        point.setX(Utils.tileTopixel(point.getX()));
        point.setY(Utils.tileTopixel(point.getY()));
        return point;
    }

    private Point parsePoint(String point) {
        String[] dim=point.split(",");
        return new Point(Integer.parseInt(dim[0]),Integer.parseInt(dim[1]));

    }

    private Dimension parseDimension(String dimension) {
        String[] dim=dimension.split("x");
        return new Dimension(Integer.parseInt(dim[0]),Integer.parseInt(dim[1]));
    }

}
