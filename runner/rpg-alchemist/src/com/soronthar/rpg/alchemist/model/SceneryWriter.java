package com.soronthar.rpg.alchemist.model;

import com.badlogic.gdx.utils.JsonWriter;
import com.soronthar.rpg.adventure.scenery.DrawnTile;
import com.soronthar.rpg.adventure.scenery.Layer;
import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.MobNpc;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.utils.Dimension;
import com.soronthar.rpg.utils.Point;
import org.soronthar.error.ExceptionHandler;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

public class SceneryWriter {
    public void write(Scenery scenery, Writer out) {
        try {
            JsonWriter jw=new JsonWriter(out);

            jw.object();
            writeSceneryInfo(scenery, jw);
            writeLayers(scenery.getLayers(), jw);
            writeObstacles(scenery.getObstacles(), jw);
            writeSprites(scenery.getSprites(), jw);
            writeJumps(scenery.getJumpPoints(), jw);
            writeHeroStartingPoint(scenery.getHeroStartingPoint(), jw);

            jw.pop();

        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }

    }

    private void writeHeroStartingPoint(Point heroStartingPoint, JsonWriter jw) throws IOException {
        writeAttribute(jw, "heroStartingPoint", renderPoint(heroStartingPoint));
    }

    private void writeJumps(Collection<JumpPoint> jumpPoints, JsonWriter jw) throws IOException {
        jw.array("jumps");
        for (JumpPoint jumpPoint : jumpPoints) {
            jw.object();
            writeAttribute(jw, "pos", renderPoint(jumpPoint.getLocation()));
            writeAttribute(jw, "target", Long.toString(jumpPoint.getTargetId()));
            jw.pop();
        }
        jw.pop();
    }

    private void writeSprites(Collection<Sprite> sprites, JsonWriter jw) throws IOException {
        jw.array("sprites");
        for (Sprite sprite : sprites) {
            jw.object();
            writeAttribute(jw, "id", sprite.getId());
            writeAttribute(jw, "solid", Boolean.toString(sprite.isSolid()));
            writeAttribute(jw, "visible", Boolean.toString(sprite.isVisible()));
            writeAttribute(jw, "type", sprite instanceof MobNpc ? "mob" : "npc");
            writeAttribute(jw, "frames", sprite.getFramesImageName());
            writeAttribute(jw, "pos", renderPoint(sprite.getLocation()));
            writeAttribute(jw, "facing",sprite.getFacing().toString());
            jw.pop();
        }

        jw.pop();
    }


    private void writeObstacles(Collection<Point> obstacles, JsonWriter jw) throws IOException {
        jw.array("obstacles");
        for (Point point : obstacles) {
            jw.value(renderPoint(point));
        }
        jw.pop();
    }

    private void writeLayers(LayersArray layers, JsonWriter jw) throws IOException {
        jw.array("layers");
        for (Layer layer : layers) {
            writeLayer(layer,jw);
        }
        jw.pop();
    }

    private void writeLayer(Layer layer, JsonWriter jw) throws IOException {
        LinkedHashMap<Tile, ArrayList<Point>> tileMap = collectTiledata(layer);

        jw.object();
        jw.array("tiles");
        Set<Tile> tiles = tileMap.keySet();
        for (Tile tile : tiles) {
            writeTile(jw, tile, tileMap);
        }
        jw.pop();
        jw.pop();
    }

    private void writeTile(JsonWriter jw, Tile tile, LinkedHashMap<Tile, ArrayList<Point>> tileMap) throws IOException {
        jw.object();
        writeAttribute(jw, "tileSetName", tile.getTilesetName());
        writeAttribute(jw, "tile", renderPoint(tile.getPoint()));
        writeAttribute(jw, "dimension", renderDimension(tile.getDimension()));
        writeTileLocations(jw, tile, tileMap);

        jw.pop();
    }

    private void writeTileLocations(JsonWriter jw, Tile tile, LinkedHashMap<Tile, ArrayList<Point>> tileMap) throws IOException {
        jw.array("pos");
        ArrayList<Point> points = tileMap.get(tile);
        for (Point point : points) {
        jw.value(renderPoint(point));
    }
        jw.pop();
    }

    private LinkedHashMap<Tile, ArrayList<Point>> collectTiledata(Layer layer) {
        LinkedHashMap<Tile,ArrayList<Point>> tileMap=new LinkedHashMap<Tile, ArrayList<Point>>();
        for (DrawnTile drawnTile : layer) {
            Tile tile = drawnTile.getTile();
            ArrayList<Point> points = tileMap.get(tile);
            if (points==null) {
                points=new ArrayList<Point>();
                tileMap.put(tile,points);
            }
            points.add(drawnTile.getPoint());
        }
        return tileMap;
    }


    private void writeSceneryInfo(Scenery scenery, JsonWriter jw) throws IOException {
        writeAttribute(jw, "id", Long.toString(scenery.getId()));
        writeAttribute(jw, "name", scenery.getName());
        writeAttribute(jw, "size", scenery.getWidth() + "x"+scenery.getHeight());
    }



    private void writeAttribute(JsonWriter jw, String name, String value) throws IOException {
        jw.name(name);
        jw.value(value);
    }

    private String renderPoint(Point tilePoint) {
        return tilePoint.getX() + "," + tilePoint.getY();
    }

    private String renderDimension(Dimension dimension) {
        return dimension.getWidth() + "x" + dimension.getHeight();
    }


}
