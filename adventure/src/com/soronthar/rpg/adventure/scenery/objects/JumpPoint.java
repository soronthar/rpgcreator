package com.soronthar.rpg.adventure.scenery.objects;


import com.soronthar.rpg.utils.Point;

public class JumpPoint extends Actor {
    private long target;

    public JumpPoint(Point location, long target) {
        super(location);
        this.target = target;
    }

    public JumpPoint(int x, int y, long target) {
        this(new Point(x,y),target);
    }

    public long getTargetId() {
        return target;
    }
}
