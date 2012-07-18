package com.soronthar.rpg.demiurge;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.util.Point;

public class CoordinateUtil {
    private CoordinateUtil() {
    }

    public static java.awt.Point normalizePointToTile(java.awt.Point point) {
        return Utils.normalizePointToTile(Point.fromAWT(point)).toAWT();
    }

    public static Point pointToTile(Point p) {
        Point point = p.clone();
        point.setX(Utils.pixelToTile(point.getX()));
        point.setY(Utils.pixelToTile(point.getY()));
        return point;
    }

}
