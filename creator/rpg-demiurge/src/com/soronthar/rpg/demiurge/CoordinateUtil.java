package com.soronthar.rpg.demiurge;

import com.soronthar.rpg.Utils;

import java.awt.*;

public class CoordinateUtil {
    private CoordinateUtil() {
    }

    public static java.awt.Point normalizePointToTile(Point point) {
        return Utils.normalizePointToTile(com.soronthar.rpg.util.Point.fromAWT(point)).toAWT();
    }

    public static Point pointToTile(Point p, Dimension canvasSize) {
        Point point = pointToOrthoTile(p);
        Point bound = pointToOrthoTile(new Point(canvasSize.width,canvasSize.height));
        point.y=bound.y-point.y;
        return point;
    }

    private static Point pointToOrthoTile(Point p) {
        com.soronthar.rpg.util.Point point = com.soronthar.rpg.util.Point.fromAWT(p);
        point.setX(Utils.pixelToTile(point.getX()));
        point.setY(Utils.pixelToTile(point.getY()));
        return point.toAWT();
    }

    public static Point tileToPoint(Point p, Dimension canvasSize) {
        Point point = (Point) p.clone();
        Point bound = pointToOrthoTile(new Point(canvasSize.width,canvasSize.height));
        point.y=bound.y-p.y;
        return tileToOrthoPoint(point);
    }

    private static Point tileToOrthoPoint(Point p) {
        com.soronthar.rpg.util.Point point = com.soronthar.rpg.util.Point.fromAWT(p);
        point.setX(Utils.tileTopixel(point.getX()));
        point.setY(Utils.tileTopixel(point.getY()));
        return point.toAWT();
    }

}
