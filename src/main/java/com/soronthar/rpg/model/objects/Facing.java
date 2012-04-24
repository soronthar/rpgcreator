package com.soronthar.rpg.model.objects;

public enum Facing {
    UP(Axis.X, 0), DOWN(Axis.X, 1), LEFT(Axis.Y, 2), RIGHT(Axis.Y, 3);

    private Axis axis;
    private int xPosInAnim;

    Facing(Axis axis, int xPosInAnim) {
        this.axis = axis;
        this.xPosInAnim = xPosInAnim;
    }

    public boolean hasSameAxis(Facing facing) {
        return this.axis == facing.axis;
    }

    public int getXPosInAnim() {
        return xPosInAnim;
    }
}
