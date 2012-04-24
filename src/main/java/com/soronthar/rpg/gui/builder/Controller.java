package com.soronthar.rpg.gui.builder;

import com.soronthar.rpg.gui.builder.components.scenery.SceneryTree;
import com.soronthar.rpg.gui.builder.panes.PaintPanel;
import com.soronthar.rpg.gui.builder.panes.TilesetsPanel;
import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.MainEngine;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.project.NewProjectPersister;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.*;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSet;
import org.soronthar.error.ApplicationException;
import org.soronthar.error.ExceptionHandler;
import org.soronthar.error.TechnicalException;
import org.soronthar.geom.Dimension;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.soronthar.rpg.Utils.normalizePointToTile;

public class Controller {
    private Model model;
    private TilesetsPanel tilesetsPanel;
    private PaintPanel paintPanel;
    private SceneryTree sceneryTree;

    public Controller(Model model) {
        this.model = model;

    }


    public void handleToggleLayerVisibilityEvent(int layerIndex) {
        model.toggleLayerVisibility(layerIndex);
        this.paintPanel.getCanvas().repaint();
        this.paintPanel.repaint();
    }

    public void setActiveLayer(int layerIndex) {
        this.model.setActiveLayerIndex(layerIndex);
        notifyChangeDrawingPen(model.getActiveTile());
    }


    public void setActiveTile(Tile activeTile) {
        model.setActiveTile(activeTile);
        if (activeTile != null) {
            notifyChangeDrawingPen(activeTile);
        }
    }

    public void addTileToActiveSceneryAtPoint(Point p) {
        if (model.isPaintObstacles()) {
            model.getActiveScenery().addObstacleAt(p);
            this.paintPanel.drawTileAtPoint(normalizePointToTile(p));
        } else if (model.isHeroStartMode()) {
            model.getActiveScenery().setHeroStartingPoint(p);
            this.paintPanel.drawTileAtPoint(normalizePointToTile(p));
        } else if (model.isAddJumpMode()) {
            Scenery activeScenery = model.getActiveScenery();
            activeScenery.addJumpPoint(new JumpPoint(p, activeScenery.getName()));
            this.paintPanel.drawTileAtPoint(normalizePointToTile(p));
        } else {
            if (!model.isInSpecialLayer()) {
                Tile activeTile = model.getActiveTile();
                if (activeTile != null) {
                    model.getActiveScenery().setTile(activeTile, model.getActiveLayerIndex(), p);
                    this.paintPanel.drawTileAtPoint(normalizePointToTile(p));
                }
            }
        }
    }

    public void removeTileAtPoint(Point p) {
        if (model.isPaintObstacles()) {
            model.getActiveScenery().removeObstacleAt(p);
            this.paintPanel.handleEraseTileEvent(p);
        } else if (model.isHeroStartMode()) {
            model.getActiveScenery().setHeroStartingPoint(new Point(0, 0));
            this.paintPanel.handleEraseTileEvent(p);
        } else if (model.isAddJumpMode()) {
            model.getActiveScenery().removeJumpAt(p);
            this.paintPanel.handleEraseTileEvent(p);
        } else {
            if (!model.isInSpecialLayer()) {
                Scenery activeScenery = model.getActiveScenery();
                activeScenery.setTile(null, model.getActiveLayerIndex(), p);
                this.paintPanel.handleEraseTileEvent(p);
            }
        }
    }

    public void saveProject() {
        new NewProjectPersister().save(model.getProject());
    }

    public void loadProject(String projectName) {

        try {
            this.paintPanel.clearMap();

            Project project = new NewProjectPersister().load(projectName);
//            Project project = new ProjectPersister().load(projectName);
            SceneryBag sceneryBag = project.getSceneries();
            if (sceneryBag.size() > 0) {
                Scenery activeScenery = sceneryBag.iterator().next();
                model.setActiveScenery(activeScenery);
                drawScenery(activeScenery);
            }
            model.setProject(project);
            paintPanel.setEnabled(true);
            this.sceneryTree.updateSceneriesForProject(project);
            this.setActiveLayer(0);
        } catch (Exception e) {
            throw new TechnicalException(e);
        }
    }

    public void createNewProject(String projectName) {
        Project project = new Project(projectName);
        Scenery scenery = new Scenery("New Scenery");
        project.addScenery(scenery);
        model.setProject(project);
        model.setActiveScenery(scenery);
        this.paintPanel.clearMap();
        this.sceneryTree.clearSceneryTree(project);
        this.paintPanel.setEnabled(true);
        this.sceneryTree.addSceneryToProjectTree(scenery);
        this.setActiveLayer(0);
    }

    public void addNewScenery(String sceneryName) {
        Scenery scenery = new Scenery(sceneryName);
        model.addScenery(scenery);
        model.setActiveScenery(scenery);
        this.paintPanel.clearMap();
        drawScenery(scenery);
        this.sceneryTree.addSceneryToProjectTree(scenery);
    }

    public void selectScenery(String sceneryName) {
        model.setActiveScenery(model.getProject().getScenery(sceneryName));
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

            if (layerIndex == LayersArray.SPRITE_LAYER_INDEX) {
                Map<Point, Sprite> sprites = scenery.getSprites();
                Set<Point> points = sprites.keySet();
                for (Point point : points) {
                    this.paintPanel.drawTileAtPoint(normalizePointToTile(point));
                }
            } else {
                Layer sceneryLayer = layers.layerAt(layerIndex);
                for (DrawnTile drawnTile : sceneryLayer) {
                    notifyChangeDrawingPen(drawnTile.getTile());
                    this.paintPanel.drawTileAtPoint(normalizePointToTile(drawnTile.getPoint()));
                }
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

        setMode(Model.SpecialModes.NONE);
    }

    private void notifyChangeDrawingPen(Tile info) {
        BufferedImage drawingPen = null;
        if (info != null) {
            TileSet tileSet = model.getTileSet(info.getTilesetName());
            if (tileSet == null) {
                throw new ApplicationException("Tileset " + info.getTilesetName() + " is not loaded");
            }
            BufferedImage image = tileSet.image();
            Point point = info.getPoint();
            Dimension dimension = info.getDimension();
            drawingPen = image.getSubimage(point.x, point.y, dimension.width, dimension.height);
        }
        model.setDrawingPen(drawingPen);
    }

    public void loadTilesets() {
        try {
            Properties tilesetsDef = new Properties();
            tilesetsDef.load(new FileInputStream("tileset.properties"));
            BufferedImage image;

            Enumeration<?> enumeration = tilesetsDef.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                image = MainEngine.getInstance().imageLoader().load("resources/" + tilesetsDef.getProperty(key));
                TileSet tileSet = new TileSet(key, image);
                model.getTilesets().put(tileSet);
            }
        } catch (IOException e) {
            ExceptionHandler.handleException(e);
        }
        tilesetsPanel.setTileSets(model.getTilesets());

    }


    public void setTilesetPanel(TilesetsPanel tilesetsPanel) {
        this.tilesetsPanel = tilesetsPanel;
    }

    public void setPaintPanel(PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
    }

    public void setSceneryTree(SceneryTree sceneryTree) {
        this.sceneryTree = sceneryTree;
    }

    public Project getProject() {
        return this.model.getProject();
    }

    public void setMode(Model.SpecialModes mode) {
        model.setMode(mode);
    }


    public Model getModel() {
        return model;
    }

    //TODO: remove
    public PaintPanel getPaintPanel() {
        return paintPanel;
    }
}
