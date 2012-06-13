package com.soronthar.rpg.alchemist.model;

import com.soronthar.rpg.adventure.scenery.DrawnTile;
import com.soronthar.rpg.adventure.scenery.Layer;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Facing;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.adventure.scenery.objects.actors.StandingNpc;
import com.soronthar.rpg.utils.Dimension;
import com.soronthar.rpg.utils.Point;
import junit.framework.TestCase;

import java.io.StringReader;
import java.util.Collection;
import java.util.Map;

public class TestSceneryReader extends TestCase {

    public void test() {
        Scenery scenery= new SceneryReader().read(new StringReader(TEST));
        assertNotNull(scenery);
        assertEquals(1336692255933L, scenery.getId());
        assertEquals("Shop",scenery.getName());
        assertEquals(288,scenery.getWidth());
        assertEquals(256,scenery.getHeight());

        Layer layer = scenery.getLayer(0);
        assertEquals(7,layer.tileCount());

        DrawnTile drawnTile = layer.getTileAt(new Point(0, 0));
        assertEquals(new Point(0,0), drawnTile.getPoint());
        assertEquals(new Point(0,0), drawnTile.getTile().getPoint());
        assertEquals(new Dimension(32,32), drawnTile.getTile().getDimension());
        assertEquals("A5", drawnTile.getTile().getTilesetName());

        drawnTile = layer.getTileAt(new Point(0, 32));
        assertEquals(new Point(0,32), drawnTile.getPoint());
        assertEquals(new Point(0,32), drawnTile.getTile().getPoint());
        assertEquals(new Dimension(32,32), drawnTile.getTile().getDimension());
        assertEquals("A5", drawnTile.getTile().getTilesetName());

        drawnTile = layer.getTileAt(new Point(32, 32));
        assertEquals(new Point(32,32), drawnTile.getPoint());
        assertEquals(new Point(0,32), drawnTile.getTile().getPoint());
        assertEquals(new Dimension(32,32), drawnTile.getTile().getDimension());
        assertEquals("A5", drawnTile.getTile().getTilesetName());

        drawnTile = layer.getTileAt(new Point(32, 64));
        assertEquals(new Point(32,64), drawnTile.getPoint());
        assertEquals(new Point(192,192), drawnTile.getTile().getPoint());
        assertEquals(new Dimension(32,32), drawnTile.getTile().getDimension());
        assertEquals("A5", drawnTile.getTile().getTilesetName());

        drawnTile = layer.getTileAt(new Point(64, 64));
        assertEquals(new Point(64,64), drawnTile.getPoint());
        assertEquals(new Point(192,192), drawnTile.getTile().getPoint());
        assertEquals(new Dimension(32,32), drawnTile.getTile().getDimension());
        assertEquals("A5", drawnTile.getTile().getTilesetName());

        drawnTile = layer.getTileAt(new Point(96, 64));
        assertEquals(new Point(96,64), drawnTile.getPoint());
        assertEquals(new Point(192,192), drawnTile.getTile().getPoint());
        assertEquals(new Dimension(32,32), drawnTile.getTile().getDimension());
        assertEquals("A5", drawnTile.getTile().getTilesetName());

        drawnTile = layer.getTileAt(new Point(0, 64));
        assertEquals(new Point(0,64), drawnTile.getPoint());
        assertEquals(new Point(256,0), drawnTile.getTile().getPoint());
        assertEquals(new Dimension(32,32), drawnTile.getTile().getDimension());
        assertEquals("black", drawnTile.getTile().getTilesetName());

        assertEquals(0, scenery.getLayer(1).tileCount());
        assertEquals(0,scenery.getLayer(2).tileCount());
        assertEquals(0,scenery.getLayer(3).tileCount());
        assertEquals(0,scenery.getLayer(4).tileCount());

        Collection<Point> obstacles = scenery.getObstacles();
        assertTrue(obstacles.contains(new Point(256, 224)));
        assertTrue(obstacles.contains(new Point(0, 96)));

        Collection<JumpPoint> jumpPoints = scenery.getJumpPoints();
        assertTrue(jumpPoints.contains(new JumpPoint(32, 224, 1336686674947L)));

        assertEquals(new Point(32,192),scenery.getHeroStartingPoint());

        Map<String,Sprite> spriteMap = scenery.getSpriteMap();
        Sprite sprite = spriteMap.get("Sprite-0");
        assertNotNull(sprite);
        assertTrue(sprite.isSolid());
        assertTrue(sprite.isVisible());
        assertTrue(sprite instanceof StandingNpc);
        assertEquals("miscsprite.png",sprite.getFramesImageName());
        assertEquals(new Point(128,64),sprite.getLocation());
        assertEquals(Facing.DOWN,sprite.getFacing());
    }






    public final String TEST="{\n" +
            "    \"id\":\"1336692255933\",\n" +
            "    \"name\":\"Shop\",\n" +
            "    \"size\":\"288x256\",\n" +
            "    \"layers\":[\n" +
            "        {\n" +
            "            \"tiles\":[\n" +
            "                { \"tileSetName\":\"A5\", \"tile\":\"0,0\", \"dimension\":\"32x32\", \"pos\":[\"0,0\"] },\n" +
            "                { \"tileSetName\":\"A5\", \"tile\":\"0,32\", \"dimension\":\"32x32\", \"pos\":[\"0,32\",\"32,32\"] },\n" +
            "                { \"tileSetName\":\"A5\", \"tile\":\"192,192\", \"dimension\":\"32x32\", \"pos\":[\"32,64\", \"64,64\", \"96,64\"] },\n" +
            "                { \"tileSetName\":\"black\", \"tile\":\"256,0\", \"dimension\":\"32x32\", \"pos\":[ \"0,64\" ] }" +
            "                      ]\n" +
            "        }, { \"tiles\":[ ] }, { \"tiles\":[ ]\n" +
            "        }, { \"tiles\":[ ] }, { \"tiles\":[ ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"obstacles\":[\n" +
            "        \"256,224\", \"0,96\"\n" +
            "    ],\n" +
            "    \"specials\":[\n" +
            "        { \"id\":\"Sprite-0\", \"solid\":\"true\", \"visible\":\"true\", \"type\":\"npc\", \"frames\":\"miscsprite.png\", \"pos\":\"128,64\", \"facing\":\"DOWN\" }\n" +
            "    ],\n" +
            "    \"jumps\":[\n" +
            "        { \"pos\":\"32,224\", \"target\":\"1336686674947\" }\n" +
            "    ],\n" +
            "    \"heroStartingPoint\":\"32,192\"\n" +
            "}";
}
