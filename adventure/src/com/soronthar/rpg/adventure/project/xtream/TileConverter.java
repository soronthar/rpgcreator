package com.soronthar.rpg.adventure.project.xtream;

import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.utils.Point;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.soronthar.geom.Dimension;


public class TileConverter implements Converter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext marshallingContext) {
        Tile tile = (Tile) o;
        writer.addAttribute("tileSetName", tile.getTilesetName());
        writer.addAttribute("tile", (int) tile.getPoint().getX() + "," + (int) tile.getPoint().getY());
        writer.addAttribute("dimension", (int) tile.getDimension().getWidth() + "x" + (int) tile.getDimension().getHeight());
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
        String tileSetName = reader.getAttribute("tileSetName");
        String pos = reader.getAttribute("tile");
        String[] splitPos = pos.split(",");
        Point point = new Point(Integer.parseInt(splitPos[0]), Integer.parseInt(splitPos[1]));

        String dim = reader.getAttribute("dimension");
        String[] splitDim = dim.split("x");
        Dimension dimension = new Dimension(Integer.parseInt(splitDim[0]), Integer.parseInt(splitDim[1]));

        return new Tile(tileSetName, point, dimension);
    }

    public boolean canConvert(Class aClass) {
        return aClass.equals(Tile.class);
    }
}
