package com.soronthar.rpg.model.objects;

import com.soronthar.rpg.model.JumpPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ObjectMap {
    private Map<Point, java.util.List<SpecialObject>> spriteMap = new HashMap<Point, java.util.List<SpecialObject>>();

    private Map<Point, JumpPoint> jumpPoints = new HashMap<Point, JumpPoint>();
    private Map<Point, Obstacle> obstacles = new HashMap<Point, Obstacle>();


    public void add(SpecialObject specialObject) {
        Point location = specialObject.getLocation();
        java.util.List<SpecialObject> sprites = spriteMap.get(location);
        if (sprites == null) {
            sprites = new ArrayList<SpecialObject>();
            spriteMap.put(location, sprites);
        }
        sprites.add(specialObject);

        if (specialObject instanceof JumpPoint) {
            this.jumpPoints.put(specialObject.getLocation(), (JumpPoint) specialObject);
        } else if (specialObject instanceof Obstacle) {
            this.obstacles.put(specialObject.getLocation(), (Obstacle) specialObject);
        }
    }

    public void remove(Point location, SpecialObject sprite) {
        java.util.List<SpecialObject> sprites = spriteMap.get(location);
        if (sprites == null) return;
        sprites.remove(sprite);
    }

    public boolean haveSpritesAt(Point location) {
        return spriteMap.containsKey(location);
    }

    public boolean haveSpriteAt(SpecialObject sprite, Point location) {
        java.util.List<SpecialObject> sprites = spriteMap.get(location);
        if (sprites == null || sprites.isEmpty()) return false;
        return sprites.contains(sprite);
    }

    public boolean haveSolidSpritesAt(Point location) {
        java.util.List<SpecialObject> sprites = spriteMap.get(location);
        if (sprites == null || sprites.isEmpty()) return false;

        for (SpecialObject specialObject : sprites) {
            if (specialObject.isSolid()) return true;
        }
        return false;
    }


    public Collection<JumpPoint> getJumpPoints() {
        return this.jumpPoints.values();
    }

    public void removeJumpAt(Point point) {
        JumpPoint jumpPoint = this.jumpPoints.remove(point);
        if (jumpPoint != null) {
            remove(point, jumpPoint);
        }
    }

    public Collection<Point> getObstacles() {
        return this.obstacles.keySet();
    }

    public void removeObstacleAt(Point point) {
        Obstacle obstacle = this.obstacles.remove(point);
        if (obstacle != null) {
            remove(point, obstacle);
        }

    }

    public SpecialObject getObjectAt(Point point) {
        return this.jumpPoints.get(point);
    }
}
