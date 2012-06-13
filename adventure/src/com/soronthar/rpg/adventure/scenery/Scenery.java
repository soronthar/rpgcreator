package com.soronthar.rpg.adventure.scenery;

import com.soronthar.rpg.adventure.scenery.objects.Actor;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.ObjectMap;
import com.soronthar.rpg.adventure.scenery.objects.Obstacle;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.utils.Dimension;
import com.soronthar.rpg.utils.Point;
import com.soronthar.rpg.utils.Utils;

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

            int width = Utils.pixelToTile(tileDimension.getWidth());
            int height = Utils.pixelToTile(tileDimension.getHeight());

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Point pointInTileset = tilePointInTileSet.clone();
                    pointInTileset.translate(i * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
                    Tile fragment = new Tile(tileSetName, pointInTileset, Tile.TILE_DIMENSION);

                    Point pointInScenery = p.clone();
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

    public int getWidth() {
        return this.dimension.getWidth();
    }

    public int getHeight() {
        return this.dimension.getHeight();
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
        return heroStartingPoint.clone();
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

    @Deprecated
    public Actor getSpecialAt(java.awt.Point point) {
        return getSpecialAt(Point.fromAWT(point));
    }

    public Actor getSpecialAt(Point point) {
        return objects.getObjectAt(point);
    }

    public long getId() {
        return id;
    }

    @Deprecated
    public void removeSpriteAt(java.awt.Point p) {
        removeSpriteAt(Point.fromAWT(p));
    }

    public void removeSpriteAt(Point p) {
        objects.removeObjectAt(p);
    }

    public Map<String, Sprite> getSpriteMap() {
        return objects.getSpritesById();
    }

    public void setDimension(Dimension dimension) {
    }
}
