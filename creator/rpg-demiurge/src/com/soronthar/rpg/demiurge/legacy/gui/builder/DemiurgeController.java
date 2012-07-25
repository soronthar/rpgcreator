package com.soronthar.rpg.demiurge.legacy.gui.builder;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.*;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.demiurge.CoordinateUtil;
import com.soronthar.rpg.demiurge.components.paint.PaintCanvasModel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.ActionsManager;
import com.soronthar.rpg.demiurge.legacy.gui.builder.components.paint.Palette;
import com.soronthar.rpg.demiurge.legacy.gui.builder.components.scenery.SceneryTree;
import com.soronthar.rpg.persister.ProjectPersister;
import com.soronthar.rpg.util.Point;
import org.soronthar.error.TechnicalException;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

//TODO: if a tile is selected and a proyect is loaded, the first tile to be drawn will be use the last tile selected
//TODO: If the "special" layer is selected, it is possible to draw on it. it shouldn;t be.
public class DemiurgeController extends Controller {

    private JPanel builderGUI;
    protected SceneryTree sceneryTree;
    private ActionsManager actionManager;


    public DemiurgeController(Model model) {
        super(model);
    }

    public void setActiveLayer(int layerIndex) {
        this.model.setActiveLayerIndex(layerIndex);
    }

    public void saveProject() {
        new ProjectPersister().save(model.getProject());
    }

    public void loadProject(String projectName) {

        try {
            this.builderGUI.setEnabled(true);
            this.actionManager.setAllEnabled();
            clearMap();

            Project project = new ProjectPersister().load(projectName);
            model.setTileSets(project.getTileSetBag());
            this.tilesetModel.setTileSets(project.getTileSetBag());
            SceneryBag sceneryBag = project.getSceneries();
            if (sceneryBag.size() > 0) {
                Scenery activeScenery = sceneryBag.iterator().next();
                model.setActiveScenery(activeScenery);
                drawScenery(activeScenery);
            }
            model.setProject(project);
            this.sceneryTree.updateSceneriesForProject(project);
            this.setActiveLayer(0);
        } catch (Exception e) {
            throw new TechnicalException(e);
        }
    }

    private void clearMap() {
        canvasModel.registerAction(PaintCanvasModel.Action.CLEAR);
    }

    public void createNewProject(String projectName) {
        Project project = new Project(projectName);
        Scenery scenery = new Scenery(System.currentTimeMillis(), "New Scenery");
        project.addScenery(scenery);
        model.setProject(project);
        model.setActiveScenery(scenery);
        this.builderGUI.setEnabled(true);
        this.actionManager.setAllEnabled();

        clearMap();
        this.sceneryTree.clearSceneryTree(project);
        this.sceneryTree.addSceneryToProjectTree(scenery);
        this.setActiveLayer(0);
    }

    public void addNewScenery(String sceneryName) {
        Scenery scenery = new Scenery(System.currentTimeMillis(), sceneryName);
        model.addScenery(scenery);
        model.setActiveScenery(scenery);
        clearMap();
        drawScenery(scenery);
        this.sceneryTree.addSceneryToProjectTree(scenery);
    }

    public void selectScenery(long id) {
        model.setActiveScenery(model.getProject().getScenery(id));
        clearMap();
        drawScenery(model.getActiveScenery());
    }

    //TODO: when the scenery is changed several times, a ConcurrentModificationException happens
    private void drawScenery(Scenery scenery) {
        model.setLoading(true);
        int height = scenery.getHeight() - 1;
        int width = scenery.getWidth() - 1;

        this.canvasModel.setCanvasSize(new Dimension(width, height));
        LayersArray layers = scenery.getLayers();
        for (int layerIndex = 0; layerIndex < layers.size(); layerIndex++) {
            this.canvasModel.setActiveLayer(layerIndex);

            Layer sceneryLayer = layers.layerAt(layerIndex);
            for (DrawnTile drawnTile : sceneryLayer) {
                notifyChangeDrawingPen(drawnTile.getTile());
                drawTileAt(CoordinateUtil.pointToTile(drawnTile.getPoint().toAWT(), canvasModel.getCanvasSize()));
            }
        }

        Collection<Point> obstacles = scenery.getObstacles();
        setMode(Model.SpecialModes.OBSTACLE);
        for (Point point : obstacles) {
            drawTileAt(point.toAWT());
        }

        setMode(Model.SpecialModes.HERO_START);
        drawTileAt(scenery.getHeroStartingPoint().toAWT());

        setMode(Model.SpecialModes.JUMP);
        for (JumpPoint jump : scenery.getJumpPoints()) {
            drawTileAt(jump.getLocation().toAWT());
        }

        setMode(Model.SpecialModes.SPRITE);
        Collection<Sprite> sprites = scenery.getSprites();
        for (Sprite sprite : sprites) {
            drawTileAt(sprite.getLocation().toAWT());
        }

        setMode(Model.SpecialModes.NONE);
        model.setLoading(false);

    }

    private void drawTileAt(java.awt.Point point) {
        canvasModel.fireAction(PaintCanvasModel.Action.DRAW, point);
    }

    public void setSceneryTree(SceneryTree sceneryTree) {
        this.sceneryTree = sceneryTree;
    }

    public void setMainGUI(JPanel builderGUI) {
        this.builderGUI = builderGUI;
    }

    public void setScenerySize(long id, Dimension dimension) {
        Scenery scenery = this.model.getProject().getScenery(id);
        scenery.setDimension(com.soronthar.rpg.util.Dimension.fromAWT(dimension));
        clearMap();
        drawScenery(model.getActiveScenery());//TODO: nasty bug... is it possible to change the size of an inactive scenery?
    }

    public void setActionManager(ActionsManager actionManager) {
        this.actionManager = actionManager;
    }

    public void setMode(Model.SpecialModes mode) {
        model.setMode(mode);
        PaintCanvasModel canvasModel = this.getCanvasModel();

        switch (mode) {
            case HERO_START:
                canvasModel.setDrawingPen(Palette.createHeroStartDrawingPen());
                canvasModel.setSpecialMode(true);
                break;
            case JUMP:
                canvasModel.setDrawingPen(Palette.createJumpPointDrawingPen());
                canvasModel.setSpecialMode(true);
                break;
            case OBSTACLE:
                canvasModel.setDrawingPen(Palette.createObstacleDrawingPen());
                canvasModel.setSpecialMode(true);
                break;
            case NONE:
                notifyChangeDrawingPen(model.getActiveTile());
                canvasModel.setSpecialMode(false);
                break;
            case SPRITE:
                canvasModel.setDrawingPen(Palette.createSpriteDrawingPen());
                canvasModel.setSpecialMode(true);
                break;
        }
    }


}
