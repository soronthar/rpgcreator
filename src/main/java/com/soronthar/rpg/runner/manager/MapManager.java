package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.objects.Hero;
import com.soronthar.rpg.model.objects.Obstacle;
import com.soronthar.rpg.model.objects.Sprite;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MapManager {
    Hero hero;
    List<Sprite> sprites = new ArrayList<Sprite>();
    SpritesPerPoint solidItems = new SpritesPerPoint();


    public MapManager(Scenery scenery) {
        Rectangle screenBounds = new Rectangle(scenery.getWidth() - Tile.TILE_SIZE, scenery.getHeight() - Tile.TILE_SIZE);
        this.hero = new Hero();
        this.hero.constraintTo(screenBounds);

        for (Sprite sprite : scenery.getSprites().values()) {
            this.sprites.add(sprite);
        }

        Collection<Point> obstacles = scenery.getObstacles();
        for (Point point : obstacles) {
            this.solidItems.add(point, new Obstacle());
        }

        hero.setLocation(scenery.getHeroStartingPoint());
    }

    private class SpritesPerPoint {
        Map<Point, List<Sprite>> spriteMap = new HashMap<Point, List<Sprite>>();


        public void add(Point location, Sprite sprite) {
            List<Sprite> sprites = spriteMap.get(location);
            if (sprites == null) {
                sprites = new ArrayList<Sprite>();
                spriteMap.put(location, sprites);
            }
            sprites.add(sprite);
        }

        public void remove(Point location, Sprite sprite) {
            List<Sprite> sprites = spriteMap.get(location);
            if (sprites == null) return;
            sprites.remove(sprite);
        }

        public boolean haveSpritesAt(Point location) {
            return spriteMap.containsKey(location);
        }

        public boolean haveSpriteAt(Sprite sprite, Point location) {
            List<Sprite> sprites = spriteMap.get(location);
            if (sprites == null || sprites.isEmpty()) return false;
            return sprites.contains(sprite);
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
    }

    private void updateSprites(long elapsedTime) {
        for (Sprite sprite : sprites) {
            updateSprite(sprite, elapsedTime);
        }
    }

    private void updateSprite(Sprite sprite, long elapsedTime) {
        Point oldLocation = sprite.getLocation();
        sprite.update(elapsedTime);

        if (sprite.isMoving()) { //there is a nasty bug that may creep here. Check "canMove" instead of "isMoving"
            Point tileLocation = sprite.getTileLocation();
            if (hasCollition(sprite, tileLocation)) {
                sprite.setLocation(oldLocation);
            }
        }


    }

    private boolean hasCollition(Sprite sprite, Point tileLocation) {
        return solidItems.haveSpritesAt(tileLocation); //&& !solidItems.haveSpriteAt(sprite,tileLocation);
    }

    public Hero getHero() {
        return hero;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }


}
