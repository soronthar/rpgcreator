package com.soronthar.rpg.model;

import com.soronthar.rpg.model.objects.SpecialObject;
import com.soronthar.rpg.model.objects.sprites.*;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.project.ProjectPersister;
import com.soronthar.rpg.model.scenery.DrawnTile;
import com.soronthar.rpg.model.scenery.Layer;
import com.soronthar.rpg.model.scenery.LayersArray;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSet;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.soronthar.error.TechnicalException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TestProjectXtreamPersister extends TestCase {
    public void testSaveByWriter() throws URISyntaxException, IOException {
        Project project = createTestProject();

        ProjectPersister persister = new ProjectPersister();
        StringWriter out = new StringWriter();
        persister.generateProjectXML(project, out);

        File file = new File(ProjectPersister.buildProjectFilePath("SmallProject"));

        String testProject = FileUtils.readFileToString(file, "UTF-8");
        //line endings, tabs and whitespaces at the begining of the lines may fool the equals method,
        // messing up the test. So, filter them up.
        String expected = testProject.replaceAll("\r\n", "\n").replaceAll("(\\s)\\s+", "$1");
        String actual = out.toString().replaceAll("\r\n", "\n").replaceAll("(\\s)\\s+", "$1");
        assertEquals(expected, actual);
    }

    public void testSaveByPath() throws URISyntaxException, IOException {
        String projectName = "AnotherSmallProject";
        File file = new File(ProjectPersister.buildProjectFilePath(projectName));
        FileUtils.deleteQuietly(file);

        //TODO:read the resulting XML and compare it with the SmallProject xml
        Project project = createTestProject(projectName);
        ProjectPersister persister = new ProjectPersister();
        persister.save(project);
        assertLoadedProject(persister.load(project.getName()), projectName);

        FileUtils.deleteQuietly(file);
    }

    private Project createTestProject() {
        return createTestProject("SmallProject");
    }
    
    
    private Project createTestProject(String projectName) {
        TileSet tileSet = new TileSet("TILESET", new BufferedImage(Tile.TILE_SIZE * 10, Tile.TILE_SIZE * 20, BufferedImage.TYPE_INT_ARGB));
        Scenery firstScenery = new Scenery(1, "first");
        for (int i = 0; i < LayersArray.LAYER_COUNT; i++) {
            if (i != LayersArray.SPRITE_LAYER_INDEX) {
                firstScenery.setTile(tileSet.getTile(new Point(0, i), Tile.TILE_DIMENSION), i, new org.soronthar.geom.Point(i, 0));
            }
        }
        MobNpc alltrue = new MobNpc("alltrue", new Point(4, 0), Facing.LEFT);
        alltrue.setFramesImage("cat.png");
        alltrue.getActions().add(new SpriteActions.ShowText("text"));
        firstScenery.addSprite(alltrue);

        Sprite notsolid = new StandingNpc("notsolid", new Point(5, 0));
        notsolid.setSolid(false);
        firstScenery.addSprite(notsolid);

        Sprite notvisible = new StandingNpc("notvisible", new Point(6, 0));
        notvisible.setVisible(false);
        firstScenery.addSprite(notvisible);

        firstScenery.addObstacleAt(new Point(8, 1));
        firstScenery.setHeroStartingPoint(new Point(5, 8));

        firstScenery.addJumpPoint(new JumpPoint(new Point(6, 2), 2));

        Scenery secondScenery = new Scenery(2, "second");
        for (int i = 0; i < LayersArray.LAYER_COUNT; i++) {
            if (i != LayersArray.SPRITE_LAYER_INDEX) {
                secondScenery.setTile(tileSet.getTile(new Point(1, i), Tile.TILE_DIMENSION), i, new org.soronthar.geom.Point(i, 1));
            }
        }


        Project project = new Project(projectName);
        project.addScenery(firstScenery);
        project.addScenery(secondScenery);
        return project;
    }

    public void testSaveNewProject() {
        String newProjectName = "TestSaveNewProject";
        File file = new File(ProjectPersister.buildProjectFilePath(newProjectName));
        File parentFile = file.getParentFile();
        assertNotNull(parentFile);
        if (file.exists()) {
            FileUtils.deleteQuietly(parentFile);
        }
        
        Project testProject = createTestProject(newProjectName);
        ProjectPersister persister = new ProjectPersister();
        persister.save(testProject);

        assertTrue(file.exists());
        assertEquals(newProjectName, parentFile.getName());

//        SceneryBag sceneries = testProject.getSceneries();
//        for(Scenery scenery:sceneries) {
//            File sceneryFile=new File(parentFile,Long.toString(scenery.getId()));
//            assertTrue(sceneryFile.exists());
//            assertTrue(sceneryFile.isDirectory());
//        }
        if (file.exists()) {
            FileUtils.deleteQuietly(parentFile);
        }
    }
    
    public void testLoadByPath() throws FileNotFoundException, URISyntaxException {
        ProjectPersister persister = new ProjectPersister();
        String smallProject = "SmallProject";
        Project project = new ProjectPersister().load(smallProject);

        assertLoadedProject(project,smallProject);
    }

    public void testLoadByReader() throws FileNotFoundException, URISyntaxException {
        ProjectPersister persister = new ProjectPersister();
        File file = new File(ProjectPersister.buildProjectFilePath("SmallProject"));
        assertLoadedProject(persister.load(new FileReader(file)),"SmallProject");
    }

    private void assertLoadedProject(Project project,String projectName) {
        assertEquals(projectName, project.getName());
        assertEquals(2, project.getSceneries().size());
        Scenery nonexistent = project.getScenery(0);
        assertNull(nonexistent);

        assertEquals("0.1", project.getFileVersion());
        assertFirstScenery(project.getScenery(1));
        assertSecondScenery(project.getScenery(2));
    }

    private void assertFirstScenery(Scenery scenery) {
        assertLoadedScenery(scenery, 0);

        Map<String, Sprite> sprites = scenery.getSpriteMap();
        assertEquals("Scenery " + scenery.getName(), 3, sprites.size());

        Sprite sprite = sprites.get("notsolid");
        assertNotNull(sprite);
        assertEquals(Facing.DOWN, sprite.getFacing());
        assertFalse(sprite instanceof MobNpc);
        assertFalse(sprite.isSolid());
        assertFalse(sprite.isMoving());
        assertTrue(sprite.isVisible());
        assertFalse(sprite.canInteract());
        assertTrue(sprite.getActions().isEmpty());

        SpecialObject specialAt = scenery.getSpecialAt(sprite.getLocation());
        assertEquals(sprite, specialAt);

        sprite = sprites.get("alltrue");
        assertNotNull(sprite);
        assertTrue(sprite instanceof MobNpc);
        assertEquals(Facing.LEFT, sprite.getFacing());
        assertEquals("cat.png", sprite.getFramesImageName());
        assertTrue(sprite.isSolid());
        assertTrue(sprite.isVisible());
        assertTrue(sprite.isMoving());
        assertTrue(sprite.canInteract());

        List<SpriteActions.SpriteAction> actions=sprite.getActions();
        assertEquals(1,actions.size());

        SpriteActions.SpriteAction spriteAction = actions.get(0);
        assertTrue(spriteAction instanceof SpriteActions.ShowText);

        sprite = sprites.get("notvisible");
        assertNotNull(sprite);
        assertFalse(sprite instanceof MobNpc);
        assertEquals(Facing.DOWN, sprite.getFacing());
        assertTrue(sprite.isSolid());
        assertFalse(sprite.isMoving());
        assertFalse(sprite.isVisible());
        assertFalse(sprite.canInteract());
        assertTrue(sprite.getActions().isEmpty());

        Collection<Point> obstacles = scenery.getObstacles();
        assertEquals(1, obstacles.size());
        Point point = obstacles.iterator().next();
        assertEquals(8, point.x);
        assertEquals(1, point.y);

        assertEquals(new Point(5, 8), scenery.getHeroStartingPoint());

        Collection<JumpPoint> jump = scenery.getJumpPoints();
        assertEquals(1, jump.size());

        JumpPoint jumpPoint = jump.iterator().next();
        assertEquals(new Point(6, 2), jumpPoint.getLocation());
        assertEquals(2, jumpPoint.getTargetId());

    }


    private void assertSecondScenery(Scenery scenery) {
        assertLoadedScenery(scenery, 1);
        Collection<Point> obstacles = scenery.getObstacles();
        assertEquals(0, obstacles.size());

        assertEquals(new Point(0, 0), scenery.getHeroStartingPoint());

    }


    private void assertLoadedScenery(Scenery scenery, int index) {
        assertNotNull(scenery);
        assertEquals(index + 1, scenery.getId());
        LayersArray layers = scenery.getLayers();
        assertEquals(5, layers.size());
        for (int i = index; i < LayersArray.LAYER_COUNT; i++) {
            Layer layer = scenery.getLayer(i);
            if (i == LayersArray.SPRITE_LAYER_INDEX) {
                continue;
            } else {
                assertNotNull(layer);
            }

            assertNull(layer.getTileAt(new Point(-1, -1)));
            DrawnTile drawnTile = layer.getTileAt(new Point(i, index));
            String fixtureId = identifyScenery(scenery, i);
            assertNotNull(fixtureId, drawnTile);
            Point point = drawnTile.getPoint();
            assertEquals(fixtureId, i, point.x);
            assertEquals(fixtureId, index, point.y);

            Tile tile = drawnTile.getTile();
            assertEquals(fixtureId, "TILESET", tile.getTilesetName());
            Point point1 = tile.getPoint();
            assertEquals(fixtureId, index, point1.x);
            assertEquals(fixtureId, i, point1.y);
            assertEquals(fixtureId, Tile.TILE_DIMENSION, tile.getDimension());
        }


    }

    private String identifyScenery(Scenery scenery, int i) {
        return "Scenery " + scenery.getName() + " layer " + i;
    }

    public void testExceptionOnSave() {
        Project project = new Project("?*?"); //this should be an invalid name in all systems
        ProjectPersister persister = new ProjectPersister();
        try {
            persister.save(project);
            fail();
        } catch (TechnicalException e) {
        }

    }


    public void testExceptionOnLoad() {
        ProjectPersister persister = new ProjectPersister();
        try {
            persister.load("?*?");
            fail();
        } catch (TechnicalException e) {
        }
    }

}
