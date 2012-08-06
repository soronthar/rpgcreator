package com.soronthar.rpg.adventure.scenery.objects.actors;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.scenery.objects.Actor;
import com.soronthar.rpg.util.Point;


public class Sprite extends Actor {
    public static enum Type {
        HERO,MOB, NPC
    }

    private String id;
    private Type type=Type.MOB;
    
    private Facing facing = Facing.DOWN;
    protected String imageName = "";
    private SpriteActions actions=new SpriteActions();


    public Sprite(String id, Type type, Point location) {
        super(location);
        this.id = id;
        this.type=type;
    }

    public Sprite(String id, Type type, Point location, Facing facing) {
        this(id,type,location);
        this.facing = facing;
    }

    public Point getTileLocation() {
        return Utils.getTileLocationForPoint(facing, this.location);
    }

    public boolean canInteract() {
        return !actions.isEmpty();
    }

    public Facing getFacing() {
        return facing;
    }

    public String getId() {
        return id;
    }

    public void setLocation(Point location) {
        this.location = (Point) location.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sprite sprite = (Sprite) o;

        if (id != null ? !id.equals(sprite.id) : sprite.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    public void setFramesImage(String imageName) {
        this.imageName = imageName;
    }

    public String getFramesImageName() {
        return imageName;
    }

    public SpriteActions getActions() {
        return actions;
    }

    public void setActions(SpriteActions actions) {
        this.actions = actions;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
