package com.soronthar.rpg.alchemist.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.OrderedMap;
import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.utils.Dimension;

import java.io.Reader;

//TODO: The tilesets are not being loaded
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

        return project;

        
    }
}
