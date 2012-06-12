package com.soronthar.rpg.gui.builder;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.*;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.gui.builder.actions.ActionsManager;
import com.soronthar.rpg.gui.builder.components.scenery.SceneryTree;
import com.soronthar.rpg.model.project.ProjectPersister;
import org.soronthar.error.TechnicalException;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import static com.soronthar.rpg.utils.Utils.normalizePointToTile;

public class RpgCreatorController extends Controller {

    private JPanel builderGUI;
    protected SceneryTree sceneryTree;
    private ActionsManager actionManager;

    public RpgCreatorController(Model model) {
        super(model);
    }

    public void toggleLayerVisibility(int layerIndex) {
        model.toggleLayerVisibility(layerIndex);
        this.paintPanel.getCanvas().repaint();
        this.paintPanel.repaint();
    }

    public void setActiveLayer(int layerIndex) {
        this.model.setActiveLayerIndex(layerIndex);
        notifyChangeDrawingPen(model.getActiveTile());
    }

    public void saveProject() {
        new ProjectPersister().save(model.getProject());
    }

    public void loadProject(String projectName) {

        try {
            this.builderGUI.setEnabled(true);
            this.actionManager.setAllEnabled();
            this.paintPanel.clearMap();

            Project project = new ProjectPersister().load(projectName);
//            Project project = new ProjectPersister().load(projectName);
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

    public void createNewProject(String projectName) {
        Project project = new Project(projectName);
        Scenery scenery = new Scenery(System.currentTimeMillis(), "New Scenery");
        project.addScenery(scenery);
        model.setProject(project);
        model.setActiveScenery(scenery);
        this.builderGUI.setEnabled(true);
        this.actionManager.setAllEnabled();

        this.paintPanel.clearMap();
        this.sceneryTree.clearSceneryTree(project);
        this.sceneryTree.addSceneryToProjectTree(scenery);
        this.setActiveLayer(0);
    }

    public void addNewScenery(String sceneryName) {
        Scenery scenery = new Scenery(System.currentTimeMillis(), sceneryName);
        model.addScenery(scenery);
        model.setActiveScenery(scenery);
        this.paintPanel.clearMap();
        drawScenery(scenery);
        this.sceneryTree.addSceneryToProjectTree(scenery);
    }

    public void selectScenery(long id) {
        model.setActiveScenery(model.getProject().getScenery(id));
        this.paintPanel.clearMap();
        drawScenery(model.getActiveScenery());
    }

    private void drawScenery(Scenery scenery) {
        int height = scenery.getHeight() - 1;
        int width = scenery.getWidth() - 1;

        this.paintPanel.setCanvasSize(new java.awt.Dimension(width, height));

        LayersArray layers = scenery.getLayers();
        for (int layerIndex = 0; layerIndex < layers.size(); layerIndex++) {
            this.model.setActiveLayerIndex(layerIndex);

            Layer sceneryLayer = layers.layerAt(layerIndex);
            for (DrawnTile drawnTile : sceneryLayer) {
                notifyChangeDrawingPen(drawnTile.getTile());
                this.paintPanel.drawTileAtPoint(normalizePointToTile(drawnTile.getPoint()));
            }
        }

        Collection<Point> obstacles = scenery.getObstacles();
        setMode(Model.SpecialModes.OBSTACLE);
        for (Point point : obstacles) {
            this.paintPanel.drawTileAtPoint(normalizePointToTile(point));
        }

        setMode(Model.SpecialModes.HERO_START);
        this.paintPanel.drawTileAtPoint(normalizePointToTile(scenery.getHeroStartingPoint()));

        setMode(Model.SpecialModes.JUMP);
        for (JumpPoint jump : scenery.getJumpPoints()) {
            this.paintPanel.drawTileAtPoint(normalizePointToTile(jump.getLocation()));
        }

        setMode(Model.SpecialModes.SPRITE);
        Collection<Sprite> sprites = scenery.getSprites();
        for (Sprite sprite : sprites) {
            this.paintPanel.drawTileAtPoint(normalizePointToTile(sprite.getLocation()));
        }

        setMode(Model.SpecialModes.NONE);
    }

    public void setSceneryTree(SceneryTree sceneryTree) {
        this.sceneryTree = sceneryTree;
    }

    public Project getProject() {
        return this.model.getProject();
    }


    public void setMainGUI(JPanel builderGUI) {
        this.builderGUI = builderGUI;
    }

    public void setScenerySize(long id, org.soronthar.geom.Dimension dimension) {
        Scenery scenery = this.model.getProject().getScenery(id);
        scenery.setDimension(dimension);
        this.paintPanel.clearMap();
        drawScenery(model.getActiveScenery());//TODO: nasty bug... is it possible to change the size of an inactive scenery?
    }

    public void setActionManager(ActionsManager actionManager) {
        this.actionManager = actionManager;
    }
}
