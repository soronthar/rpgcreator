package com.soronthar.rpg.persister;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import junit.framework.TestCase;

import java.io.StringReader;
import java.io.StringWriter;

public class TestProjectReaderWriter extends TestCase {

    public void testReader() {
        ProjectReader projectReader=new ProjectReader();
        
        Project project=projectReader.read(new StringReader(TEST_STRING));

        assertNotNull(project);
        assertEquals("First Project", project.getName());
        SceneryBag sceneries = project.getSceneries();
        assertEquals(2, sceneries.size());
        Scenery scenery = project.getScenery(1336686674947l);
        assertEquals("Town", scenery.getName());
        assertEquals(640, scenery.getWidth());
        assertEquals(480, scenery.getHeight());

        scenery = project.getScenery(1336692255933l);
        assertEquals("Shop", scenery.getName());
        assertEquals(288, scenery.getWidth());
        assertEquals(256, scenery.getHeight());

        TileSetBag tileSetBag = project.getTileSetBag();
        assertEquals(3, tileSetBag.size());

        TileSet a5 = tileSetBag.get("A5");
        assertNotNull(a5);
        assertEquals(a5.getName(), "A5");
        assertEquals(a5.getResourceName(), "Shop-TileA5.png");

        TileSet stuff = tileSetBag.get("stuff");
        assertNotNull(stuff);
        assertEquals(stuff.getName(), "stuff");
        assertEquals(stuff.getResourceName(), "Town-Rural.png");

        TileSet black = tileSetBag.get("black");
        assertNotNull(black);
        assertEquals(black.getName(), "black");
        assertEquals(black.getResourceName(), "Shop-Blacksmith.png");
    }

    public void testWriter() {
        ProjectReader projectReader=new ProjectReader();
        Project project=projectReader.read(new StringReader(TEST_STRING));
        
        StringWriter writer=new StringWriter();
        ProjectWriter projectWriter=new ProjectWriter();
        projectWriter.write(project,writer);
        
        assertEquals(TEST_STRING, writer.toString());
    }

    private static final String TEST_STRING="{" +
            "\"name\":\"First Project\"," +
            "\"tilesets\":" +
            "{" +
            "\"A5\":\"Shop-TileA5.png\"," +
            "\"stuff\":\"Town-Rural.png\"," +
            "\"black\":\"Shop-Blacksmith.png\"" +
            "}" +
            "," +
            "\"sceneries\":[" +
            "{" +
            "\"id\":\"1336686674947\"," +
            "\"name\":\"Town\"," +
            "\"size\":\"640x480\"" +
            "}," +
            "{" +
            "\"id\":\"1336692255933\"," +
            "\"name\":\"Shop\"," +
            "\"size\":\"288x256\"" +
            "}" +
            "]" +
            "}";
}
