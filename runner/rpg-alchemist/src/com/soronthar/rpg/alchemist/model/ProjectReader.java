package com.soronthar.rpg.alchemist.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.OrderedMap;
import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.utils.Dimension;

import java.io.Reader;
import java.util.HashMap;

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

    private static class JsonProjectReader extends JsonReader {
        private enum Contexts {
            NONE,PROJECT, SCENERY
        }

        private Project project;
        private Contexts context= Contexts.NONE;
        HashMap<String,String> values=new HashMap<String, String>();

        @Override
        protected void startObject(String name) {
            if (project ==null) {
                project=new Project();
                context=Contexts.PROJECT;
            }
        }

        @Override
        protected void startArray(String name) {
            if (name.equals("sceneries")) {
                context=Contexts.SCENERY;
            }
        }

        @Override
        protected void pop() {
            if (context==Contexts.SCENERY) {
                long id = Long.parseLong(values.get("id"));
                String name = values.get("name");
                Scenery scenery = new Scenery(id, name);

            }
            context=Contexts.NONE;
        }

        @Override
        protected void string(String name, String value) {
            if (context==Contexts.PROJECT) {
                if (name.equals("name")) {
                    project.setName(value);
                }
            } else if (context==Contexts.SCENERY) {
                values.put(name,value);
            }
        }

        @Override
        protected void number(String name, float value) {
        }

        @Override
        protected void bool(String name, boolean value) {
        }


        @Override
        public Project parse(Reader reader) {
            super.parse(reader);
            return this.project;
        }
    }
}
