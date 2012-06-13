package com.soronthar.rpg.utils;

import org.soronthar.error.ExceptionHandler;

/**
 * This class exists for the sole purpose of removing dependency
 * over java.awt package or android packages
 */
public class Point implements Cloneable {
    int x;
    int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Point clone() {
        try {
            return (Point) super.clone();
        } catch (CloneNotSupportedException e) {
            ExceptionHandler.handleException(e);
            return null;
        }
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setLocation(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public static Point fromAWT(java.awt.Point point) {
        return new Point(point.x,point.y);
    }

    public org.soronthar.geom.Point toAWT() {
        return new org.soronthar.geom.Point(x,y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
