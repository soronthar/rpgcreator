package com.soronthar.rpg.model.project.xtream;

import com.soronthar.rpg.adventure.scenery.DrawnTile;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.soronthar.geom.Point;

public class DrawnTileConverter implements Converter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        DrawnTile tile = (DrawnTile) o;
        writer.startNode("drawnTile");
        writer.addAttribute("pos", (int) tile.getPoint().getX() + "," + (int) tile.getPoint().getY());
        context.convertAnother(tile.getTile());
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String pos = reader.getAttribute("pos");
        String[] splitPos = pos.split(",");
        Point point = new Point(Integer.parseInt(splitPos[0]), Integer.parseInt(splitPos[1]));
        Tile tile = (Tile) context.convertAnother(context.currentObject(), Tile.class);
        return new DrawnTile(point, tile);
    }

    public boolean canConvert(Class aClass) {
        return DrawnTile.class.equals(aClass);
    }
}
