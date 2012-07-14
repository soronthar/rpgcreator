package com.soronthar.rpg.persister;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;
import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.util.Dimension;

import java.io.Reader;
import java.util.Iterator;

public class ProjectReader {
    
    public Project read(Reader reader) {
        JsonReader jsonReader=new JsonReader();
        OrderedMap map = (OrderedMap) jsonReader.parse(reader);

        Project project=new Project((String) map.get("name"));
        Array<OrderedMap> sceneries = (Array<OrderedMap>) map.get("sceneries");
        for (OrderedMap sceneryData : sceneries) {
            Scenery scenery=new Scenery(Long.parseLong((String) sceneryData.get("id")), (String) sceneryData.get("name"));
            String size= (String) sceneryData.get("size");
            String[] dim=size.split("x");
            scenery.setDimension(new Dimension(Integer.parseInt(dim[0]),Integer.parseInt(dim[1])));
            project.addScenery(scenery);
        }

        OrderedMap<String,String> tilesets= (OrderedMap) map.get("tilesets");
        ObjectMap.Keys tilesetNames= tilesets.keys();
        for (Iterator iterator = tilesetNames.iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            project.addTileset(key,tilesets.get(key));
        }

        return project;

        
    }
}
