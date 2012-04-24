package com.soronthar.rpg.model.scenery;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.objects.Sprite;
import com.soronthar.rpg.model.objects.UnmoveableSprite;
import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.geom.Dimension;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Scenery implements Serializable {
    private Dimension dimension;
    private String name;
    private LayersArray layers = new LayersArray();
    private Map<Point, Sprite> sprites = new HashMap<Point, Sprite>();
    private Set<Point> obstacles = new HashSet<Point>();
    private Map<Point, JumpPoint> jumpPoints = new HashMap<Point, JumpPoint>();

    private Point heroStartingPoint;
    public static final Scenery NULL_SCENERY = new Scenery("Null") {
        public void setTile(Point p, Tile tile) {

        }
    };
    public static final int WIDTH = 320;
    public static final int HEIGHT = 160;

    public Scenery() {
        this.sprites = new HashMap<Point, Sprite>();
        this.heroStartingPoint = new Point(0, 0);
    }

    public Scenery(String name) {
        this();
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
        if (layer == LayersArray.SPRITE_LAYER_INDEX) {
            if (tile != null) {
                sprites.put(p, new UnmoveableSprite(p));
            } else {
                sprites.remove(p);
            }
        }
        if (tile != null) {
            Dimension tileDimension = tile.getDimension();
            String tileSetName = tile.getTilesetName();
            Point tilePointInTileSet = tile.getPoint();

            int width = Utils.pixetToTile(tileDimension.width);
            int height = Utils.pixetToTile(tileDimension.height);

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
        return this.name + " " + super.toString();
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
        if (i == LayersArray.SPRITE_LAYER_INDEX) {
            return null;
        } else {
            return layers.layerAt(i);
        }
    }

    public LayersArray getLayers() {
        return layers;
    }

    public Map<Point, Sprite> getSprites() {
        return sprites;
    }

    public Point getHeroStartingPoint() {
        return heroStartingPoint;
    }

    public void setHeroStartingPoint(Point heroStartingPoint) {
        this.heroStartingPoint = heroStartingPoint;
    }

    public void addSprite(Sprite sprite) {
        this.sprites.put(sprite.getLocation(), sprite);
    }

    public Collection<Point> getObstacles() {
        return obstacles;
    }

    public void addObstacleAt(Point point) {
        obstacles.add(point);
    }

    public void removeObstacleAt(Point point) {
        obstacles.remove(point);
    }

    public Collection<JumpPoint> getJumpPoints() {
        return jumpPoints.values();
    }

    public void addJumpPoint(JumpPoint jump) {
        this.jumpPoints.put(jump.getLocation(), jump);
    }

    public void removeJumpAt(Point point) {
        this.jumpPoints.remove(point);
    }
}
