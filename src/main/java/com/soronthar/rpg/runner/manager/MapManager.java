package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.objects.Obstacle;
import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.objects.sprites.Facing;
import com.soronthar.rpg.model.objects.sprites.Hero;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.objects.sprites.StandingNpc;
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
    Hero hero=new Hero(new Point(0,0));
    List<Sprite> sprites = new ArrayList<Sprite>(); //TODO: Sprites are being registered in the list and in the map
    SpecialsPerPoint specialsPerPoint = new SpecialsPerPoint();
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Scenery activeScenery;
    private SceneryBag sceneries;
    private Rectangle screenBounds;

    public MapManager(SceneryBag sceneries) {
        this.sceneries = sceneries;
    }

    public void addSceneryListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(SCENERY, pcl);
    }

    public Scenery getActiveScenery() {
        return this.activeScenery;
    }

    public void setActiveScenery(Scenery activeScenery) {
        Scenery oldValue = this.activeScenery;
        this.activeScenery = activeScenery;
        this.sprites.clear();
        this.specialsPerPoint.clear();

        screenBounds = new Rectangle(activeScenery.getWidth() - Tile.TILE_SIZE, activeScenery.getHeight() - Tile.TILE_SIZE);
        this.hero.setLocation(activeScenery.getHeroStartingPoint());
        for (Sprite sprite : activeScenery.getSprites()) {
            this.sprites.add(sprite);
            this.specialsPerPoint.add(sprite.getLocation(), sprite);
        }

        Collection<Point> obstacles = activeScenery.getObstacles();
        for (Point point : obstacles) {
            this.specialsPerPoint.add(point, new Obstacle(point));
        }
        pcs.firePropertyChange(SCENERY, oldValue, activeScenery);
    }

    public void init() {
        setActiveScenery(sceneries.iterator().next());
    }

    public Scenery getScenery(int id) {
        return sceneries.get(id);
    }

    public boolean isHeroFacingActiveNPC() {
        Point location = getHeroFacingTile();

        return specialsPerPoint.haveActiveNPCAt(location);
    }

    private Point getHeroFacingTile() {
        Point location = this.getHero().getTileLocation();
        Facing facing = this.getHero().getFacing();
        switch (facing) {
            case DOWN:
                location.translate(0, Tile.TILE_SIZE);
                break;
            case UP:
                location.translate(0, -Tile.TILE_SIZE);
                break;
            case LEFT:
                location.translate(-Tile.TILE_SIZE, 0);
                break;
            case RIGHT:
                location.translate(Tile.TILE_SIZE, 0);
                break;
        }
        return location;
    }

    public StandingNpc getNPCToInteract() {
        return specialsPerPoint.getNpcAt(getHeroFacingTile());
    }

    public class SpecialsPerPoint {
        Map<Point, List<SpecialObject>> spriteMap = new HashMap<Point, List<SpecialObject>>();


        public void add(Point location, SpecialObject sprite) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites == null) {
                sprites = new ArrayList<SpecialObject>();
                spriteMap.put(location, sprites);
            }
            sprites.add(sprite);
        }


        public boolean haveSolidAt(Point location) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites == null || sprites.isEmpty()) return false;

            for (SpecialObject specialObject : sprites) {
                if (specialObject instanceof Sprite && ((Sprite) specialObject).isSolid()) return true;
                if (specialObject instanceof Obstacle) return true;
            }
            return false;
        }

        public void clear() {
            this.spriteMap.clear();
        }

        public void remove(Point location, SpecialObject object) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites != null) {
                sprites.remove(object);
            }
        }

        public boolean haveActiveNPCAt(Point location) {
            List<SpecialObject> sprites = spriteMap.get(location);
            if (sprites == null || sprites.isEmpty()) return false;

            boolean result = false;
            for (Iterator<SpecialObject> iterator = sprites.iterator(); iterator.hasNext() && !result; ) {
                SpecialObject next = iterator.next();
                result = ((Sprite) next).canInteract();
            }

            return result;
        }

        public StandingNpc getNpcAt(Point heroFacingLocation) {
            List<SpecialObject> sprites = spriteMap.get(heroFacingLocation);
            StandingNpc result = null;
            for (Iterator<SpecialObject> iterator = sprites.iterator(); iterator.hasNext() || result == null; ) {
                SpecialObject next = iterator.next();
                if (next instanceof StandingNpc) {
                    result = (StandingNpc) next;
                }
            }
            return result;
        }
    }

    public void update(long elapsedTime) {
        updateHero(elapsedTime);
        updateSprites(elapsedTime);
    }

    private void updateHero(long elapsedTime) {
        updateSprite(hero, elapsedTime);

        Point location = hero.getLocation();
        SpecialObject specialAt = activeScenery.getSpecialAt(location);
        if (specialAt instanceof JumpPoint) {
            JumpPoint jump = (JumpPoint) specialAt;
            Scenery scenery1 = sceneries.get(jump.getTargetId());
            this.setActiveScenery(scenery1);
        }
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
            if (isOutsideBounds(sprite)) {
                sprite.setLocation(normalizePointToBounds(sprite.getLocation(), screenBounds));
                sprite.handleAtEdge(screenBounds);
            }

            if (hasCollition(sprite, tileLocation)) {
                sprite.setLocation(oldLocation);
                sprite.handleCollisionAt(tileLocation);
            }

            specialsPerPoint.remove(oldLocation, sprite);
            specialsPerPoint.add(sprite.getLocation(), sprite);
        }
    }

    protected static Point normalizePointToBounds(Point newLocation, Rectangle bounds) {
        if (bounds == null) return newLocation;
        if (newLocation.x < bounds.x) newLocation.x = bounds.x;
        if (newLocation.y < bounds.y) newLocation.y = bounds.y;
        if (newLocation.x >= bounds.width) newLocation.x = bounds.width;
        if (newLocation.y >= bounds.height) newLocation.y = bounds.height;
        return newLocation;
    }

    private boolean isOutsideBounds(Sprite sprite) {

        if (screenBounds == null) return false;
        Point tileLocation = sprite.getLocation();
        return tileLocation.x < screenBounds.x ||
                tileLocation.y < screenBounds.y ||
                tileLocation.x >= screenBounds.width ||
                tileLocation.y >= screenBounds.height;
    }

    private boolean hasCollition(SpecialObject sprite, Point tileLocation) {
        return sprite.isSolid() && specialsPerPoint.haveSolidAt(tileLocation);
    }

    public Hero getHero() {
        return hero;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }


}
