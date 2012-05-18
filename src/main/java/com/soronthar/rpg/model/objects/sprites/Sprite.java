package com.soronthar.rpg.model.objects.sprites;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.objects.sprites.frames.FrameStrategy;
import com.soronthar.rpg.model.objects.sprites.frames.NullFrameStrategy;
import com.soronthar.rpg.model.objects.sprites.frames.WholeImageFrameStrategy;
import com.soronthar.rpg.model.objects.sprites.movement.MovementStrategy;
import com.soronthar.rpg.model.tiles.Tile;
import org.soronthar.util.StringUtils;

import java.awt.*;


public class Sprite extends SpecialObject {
    private String id;
    private FrameStrategy strategy = new NullFrameStrategy();
    private MovementStrategy movementStrategy = MovementStrategy.NO_MOVE;

    private Facing facing = Facing.DOWN;
    private int steps;
    protected String imageName = "";
    private String text="";
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


    public Image getFrame() {
        return strategy.getFrame(this);
    }

    public String getId() {
        return id;
    }


    protected void setFrameStrategy(FrameStrategy strategy) {
        this.strategy = strategy;
    }

    protected FrameStrategy getFrameStrategy() {
        return this.strategy;
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
        if (StringUtils.isEmptyOrNullValue(imageName)) {
            setFrameStrategy(new NullFrameStrategy());
        } else {
            setFrameStrategy(new WholeImageFrameStrategy(imageName));
        }
    }

    public String getFramesImageName() {
        return imageName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SpriteActions getActions() {
        return actions;
    }

    public void setActions(SpriteActions actions) {
        this.actions = actions;
    }
}
