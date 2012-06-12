package com.soronthar.rpg.adventure.scenery.objects;

import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ObjectMap {
    private Map<Point, Sprite> spritesByPoint = new HashMap<Point, Sprite>();
    private Map<String, Sprite> spritesById = new HashMap<String, Sprite>();

    private Map<Point, JumpPoint> jumpPoints = new HashMap<Point, JumpPoint>();
    private Map<Point, Obstacle> obstacles = new HashMap<Point, Obstacle>();


    public void add(Actor specialObject) {
        if (specialObject instanceof JumpPoint) {
            this.jumpPoints.put(specialObject.getLocation(), (JumpPoint) specialObject);
        } else if (specialObject instanceof Obstacle) {
            this.obstacles.put(specialObject.getLocation(), (Obstacle) specialObject);
        } else if (specialObject instanceof Sprite) {
            Sprite sprite = (Sprite) specialObject;
            this.spritesByPoint.put(sprite.getLocation(), sprite);
            this.spritesById.put(sprite.getId(), sprite);
        }
    }

    public void removeObjectAt(Point point) {
        JumpPoint jump = jumpPoints.remove(point);
        if (jump == null) {
            Obstacle obstacle = obstacles.remove(point);
            if (obstacle == null) {
                Sprite sprite = spritesByPoint.remove(point);
                if (sprite != null) {
                    spritesById.remove(sprite.getId());
                }
            }
        }
    }


    public Collection<JumpPoint> getJumpPoints() {
        return this.jumpPoints.values();
    }

    public Collection<Point> getObstacles() {
        return this.obstacles.keySet();
    }


    public Actor getObjectAt(Point point) {
        Actor object = jumpPoints.get(point);
        if (object == null) {
            object = obstacles.get(point);
            if (object == null) {
                object = spritesByPoint.get(point);
            }
        }
        return object;
    }

    public Map<String, Sprite> getSpritesById() {
        return spritesById;
    }

}
