package com.soronthar.rpg.model.objects.actors;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.model.objects.Actor;

import java.awt.*;


public class Sprite extends Actor {
    private String id;

    private Facing facing = Facing.DOWN;
    private int steps;
    protected String imageName = "";
    private SpriteActions actions=new SpriteActions();


    public Sprite(String id, Point location) {
        super(location);
        this.id = id;
    }

    public Sprite(String id, Point location, Facing facing) {
        super(location);
        this.id = id;
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



    protected void increaseSteps() {
        this.steps++;
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
}
