package com.soronthar.rpg.model.scenery;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.objects.ObjectMap;
import com.soronthar.rpg.model.objects.Obstacle;
import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.geom.Dimension;

import java.awt.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class Scenery implements Serializable {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    private long id;
    private Dimension dimension;
    private String name;
    private LayersArray layers = new LayersArray();
    private ObjectMap objects = new ObjectMap();
    private Point heroStartingPoint;

    public static final Scenery NULL_SCENERY = new Scenery(0, "Null") {
        public void setTile(Point p, Tile tile) {

        }
    };


    public Scenery(long id, String name) {
        this.id = id;
        this.heroStartingPoint = new Point(0, 0);
        this.name = name;
        this.dimension = new Dimension(WIDTH, HEIGHT);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setTile(Tile tile, int layer, Point p) {
        if (tile != null) {
            Dimension tileDimension = tile.getDimension();
            String tileSetName = tile.getTilesetName();
            Point tilePointInTileSet = tile.getPoint();

            int width = Utils.pixelToTile(tileDimension.width);
            int height = Utils.pixelToTile(tileDimension.height);

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Point pointInTileset = new Point(tilePointInTileSet);
                    pointInTileset.translate(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
                    Tile fragment = new Tile(tileSetName, pointInTileset, Tile.TILE_DIMENSION);

                    Point pointInScenery = new Point(p);
                    pointInScenery.translate(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
                    layers.layerAt(layer).addTile(new DrawnTile(pointInScenery, fragment));
                }
            }
        } else {
            layers.layerAt(layer).removeTileAt(p);
        }
    }


    public String toString() {
        return this.name;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public int getWidth() {
        return this.dimension.width;
    }

    public int getHeight() {
        return this.dimension.height;
    }

    public Layer getLayer(int i) {
        return layers.layerAt(i);
    }

    public LayersArray getLayers() {
        return layers;
    }

    public Collection<Sprite> getSprites() {
        return this.objects.getSpritesById().values();
    }

    public Point getHeroStartingPoint() {
        return new Point(heroStartingPoint);
    }

    public void setHeroStartingPoint(Point heroStartingPoint) {
        this.heroStartingPoint = heroStartingPoint;
    }

    public void addSprite(Sprite sprite) {
        this.objects.add(sprite);
    }

    public Collection<Point> getObstacles() {
        return objects.getObstacles();
    }

    public void addObstacleAt(Point point) {
        objects.add(new Obstacle(point));
    }

    public void removeObstacleAt(Point point) {
        objects.removeObjectAt(point);
    }

    public Collection<JumpPoint> getJumpPoints() {
        return objects.getJumpPoints();

    }

    public void addJumpPoint(JumpPoint jump) {
        objects.add(jump);
    }

    public void removeJumpAt(Point point) {
        objects.removeObjectAt(point);
    }

    public SpecialObject getSpecialAt(Point point) {
        return objects.getObjectAt(point);
    }

    public long getId() {
        return id;
    }

    public void removeSpriteAt(Point p) {
        objects.removeObjectAt(p);
    }

    public Map<String, Sprite> getSpriteMap() {
        return objects.getSpritesById();
    }
}
