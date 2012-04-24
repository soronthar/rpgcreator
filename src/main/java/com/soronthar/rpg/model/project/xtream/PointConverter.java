package com.soronthar.rpg.model.project.xtream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.soronthar.geom.Point;

public class PointConverter implements Converter {
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext marshallingContext) {
        Point point = (Point) o;
        writer.addAttribute("x", Integer.toString((int) point.getX()));
        writer.addAttribute("y", Integer.toString((int) point.getY()));
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext unmarshallingContext) {
        String x = reader.getAttribute("x");
        String y = reader.getAttribute("y");
        return new Point(Integer.parseInt(x), Integer.parseInt(y));
    }

    public boolean canConvert(Class aClass) {
        return aClass.equals(Point.class);
    }
}
