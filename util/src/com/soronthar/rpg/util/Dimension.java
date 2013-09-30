package com.soronthar.rpg.util;


import org.soronthar.error.ExceptionHandler;

/**
 * This class exists for the sole purpose of removing the dependency
 * with java.awt and android classes
 */
public class Dimension implements Cloneable {
    private int width;
    private int height;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Dimension clone() {
        try {
            return (Dimension) super.clone();
        } catch (CloneNotSupportedException e) {
            ExceptionHandler.handleException(e);
            return null;
        }
    }
    
    public static Dimension fromAWT(java.awt.Dimension d) {
        return new Dimension(d.width,  d.height);
    }

    public java.awt.Dimension toAWT() {
        return new java.awt.Dimension(width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dimension dimension = (Dimension) o;

        if (height != dimension.height) return false;
        if (width != dimension.width) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }

    public Dimension scale(int x, int y) {
        this.setSize(width * x, height * y);
        return this;
    }

    private void setSize(int w, int h) {
        this.width=w;
        this.height=h;
    }

    public Dimension scale(int x) {
        this.setSize(width * x, height * x);
        return this;
    }

    public Dimension addPadding(int x, int y) {
        this.setSize(width + x, height + y);
        return this;
    }

}
