package com.soronthar.rpg.model.objects.actors;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.model.objects.Actor;
import com.soronthar.rpg.model.objects.actors.movement.MovementStrategy;
import com.soronthar.rpg.model.tiles.Tile;

import java.awt.*;


public class Sprite extends Actor {
    private String id;
    private MovementStrategy movementStrategy = MovementStrategy.NO_MOVE;

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

    protected void move() {
        this.location.translate(movementStrategy.getDx(), movementStrategy.getDy());
        increaseSteps();
    }

    protected void increaseSteps() {
        this.steps++;
    }

    public int getDx() {
        return movementStrategy.getDx();
    }

    public int getDy() {
        return movementStrategy.getDy();
    }

    protected void determineFacing() {
        Facing newFacing = facing;
        if (getDx() > 0) {
            newFacing = Facing.RIGHT;
        } else if (getDx() < 0) {
            newFacing = Facing.LEFT;
        } else if (getDy() > 0) {
            newFacing = Facing.DOWN;
        } else if (getDy() < 0) {
            newFacing = Facing.UP;
        }

        if (this.facing != newFacing) {
            resetStepCount();
        }
        facing = newFacing;
    }

    private void resetStepCount() {
        this.steps = 0;
    }

    public boolean isSpeedZero() {
        return !movementStrategy.isMoving();
    }

    public int getSteps() {
        return steps;
    }

    public boolean isMoving() {
        return movementStrategy.isMoving();
    }

    boolean isMovingBetweenTiles() {
        int xRemainder = this.location.x % Tile.TILE_SIZE;
        int yRemainder = this.location.y % Tile.TILE_SIZE;

        return xRemainder != 0 || yRemainder != 0;
    }

    public void setSpeed(int dx, int dy) {
        movementStrategy.setSpeed(dx, dy);
    }
    
    public Point getSpeed() {
        return new Point(movementStrategy.getDx(),movementStrategy.getDy());
    }

    public void update(long elapsedTime) {
        determineFacing();
        move();
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

    public void handleCollisionAt(Point tileLocation) {
        getMovementStrategy().handleCollisionAt(tileLocation);
    }

    public void handleAtEdge(Rectangle bounds) {
        getMovementStrategy().handleAtEdge(bounds);
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
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
