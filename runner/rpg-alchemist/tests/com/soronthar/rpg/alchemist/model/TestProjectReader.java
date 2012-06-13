package com.soronthar.rpg.alchemist.model;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import junit.framework.TestCase;

import java.io.StringReader;

public class TestProjectReader extends TestCase {

    public void test() {
        ProjectReader projectReader=new ProjectReader();
        
        Project project=projectReader.read(new StringReader(TEST_STRING));
        
        assertNotNull(project);
        assertEquals("First Project",project.getName());
        SceneryBag sceneries = project.getSceneries();
        assertEquals(2,sceneries.size());
        Scenery scenery = project.getScenery(1336686674947l);
        assertEquals("Town",scenery.getName());
        assertEquals(640,scenery.getWidth());
        assertEquals(480,scenery.getHeight());

        scenery = project.getScenery(1336692255933l);
        assertEquals("Shop",scenery.getName());
        assertEquals(288,scenery.getWidth());
        assertEquals(256,scenery.getHeight());

    }


    private static final String TEST_STRING="{\n" +
            "    \"name\":\"First Project\",\n" +
            "    \"tilesets\":[\n" +
            "        {\n" +
            "            \"A5\":\"Shop-TileA5.png\",\n" +
            "            \"stuff\":\"Town-Rural.png\",\n" +
            "            \"black\":\"Shop-Blacksmith.png\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"sceneries\":[\n" +
            "        {\n" +
            "            \"id\":\"1336686674947\",\n" +
            "            \"name\":\"Town\",\n" +
            "            \"size\":\"640x480\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\":\"1336692255933\",\n" +
            "            \"name\":\"Shop\",\n" +
            "            \"size\":\"288x256\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
