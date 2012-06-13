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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JumpPoint jumpPoint = (JumpPoint) o;

        if (target != jumpPoint.target) return false;
        if (location != null ? !location.equals(jumpPoint.location) : jumpPoint.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (target ^ (target >>> 32));
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
