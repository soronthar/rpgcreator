package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.Obstacle;
import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.scenery.SceneryBag;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.List;

public class MapManager {
    public static final String SCENERY = "scenery";
    Hero hero;
    List<Sprite> sprites = new ArrayList<Sprite>();
    SpecialsPerPoint solidItems = new SpecialsPerPoint();
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Scenery scenery;
    private SceneryBag sceneries;

    public MapManager(SceneryBag sceneries) {
        this.sceneries = sceneries;
    }

    public void addSceneryListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(SCENERY, pcl);
    }

    public void setScenery(Scenery scenery) {
        Scenery oldValue = this.scenery;
        this.scenery = scenery;
        this.sprites.clear();
        this.solidItems.clear();

        Rectangle screenBounds = new Rectangle(scenery.getWidth() - Tile.TILE_SIZE, scenery.getHeight() - Tile.TILE_SIZE);
        this.hero = new Hero(scenery.getHeroStartingPoint(), screenBounds);
        for (Sprite sprite : scenery.getSprites().values()) {
            this.sprites.add(sprite);
        }

        Collection<Point> obstacles = scenery.getObstacles();
        for (Point point : obstacles) {
            this.solidItems.add(point, new Obstacle(point));
        }
        pcs.firePropertyChange(SCENERY, oldValue, scenery);
    }

    public void init() {
        setScenery(sceneries.iterator().next());
    }

    private class SpecialsPerPoint {
        Map<Point, List<SpecialObject>> spriteMap = new HashMap<Point, List<SpecialObject>>();


        public void add(Point location, SpecialObject sprite) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites == null) {
                sprites = new ArrayList<SpecialObject>();
                spriteMap.put(location, sprites);
            }
            sprites.add(sprite);
        }

        public void remove(Point location, Sprite sprite) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites == null) return;
            sprites.remove(sprite);
        }

        public boolean haveSpritesAt(Point location) {
            return spriteMap.containsKey(location);
        }

        public boolean haveSpriteAt(SpecialObject sprite, Point location) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites == null || sprites.isEmpty()) return false;
            return sprites.contains(sprite);
        }

        public boolean haveSolidSpritesAt(Point location) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites == null || sprites.isEmpty()) return false;

            for (SpecialObject specialObject : sprites) {
                if (specialObject.isSolid()) return true;
            }
            return false;
        }

        public void clear() {
            this.spriteMap.clear();
        }
    }

    public Point centerGuidePoint(Rectangle viewBounds) {
        int xTiles = viewBounds.width >> 5;
        int yTiles = viewBounds.height >> 5;

        int centerX = xTiles / 2;
        int centerY = yTiles / 2;
        return new Point(centerX << 5, centerY << 5);
    }


    public void update(long elapsedTime) {
        updateHero(elapsedTime);
        updateSprites(elapsedTime);
    }

    private void updateHero(long elapsedTime) {
        updateSprite(hero, elapsedTime);
        //TODO: aqui quede
    }

    private void updateSprites(long elapsedTime) {
        for (Sprite sprite : sprites) {
            updateSprite(sprite, elapsedTime);
        }
    }

    private void updateSprite(Sprite sprite, long elapsedTime) {
        Point oldLocation = sprite.getLocation();
        sprite.update(elapsedTime);

        if (sprite.isMoving()) { //TODO: there is a nasty bug that may creep here. Check "canMove" instead of "isMoving"
            Point tileLocation = sprite.getTileLocation();
            if (hasCollition(sprite, tileLocation)) {
                sprite.setLocation(oldLocation);
            }
        }
    }

    private boolean hasCollition(SpecialObject sprite, Point tileLocation) {
        return solidItems.haveSolidSpritesAt(tileLocation);
    }

    public Hero getHero() {
        return hero;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }


}
