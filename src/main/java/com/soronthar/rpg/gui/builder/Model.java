package com.soronthar.rpg.gui.builder;

import com.soronthar.rpg.gui.builder.components.paint.Palette;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.LayersArray;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSet;
import com.soronthar.rpg.model.tiles.TileSetBag;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model {

    public static final String LOCATION = "location";


    public enum SpecialModes {
        NONE, OBSTACLE, HERO_START, JUMP, SPRITE
    }

    private TileSetBag tileSets = new TileSetBag();
    private Project project;
    private Scenery activeScenery = Scenery.NULL_SCENERY;
    private int activeLayerIndex;
    private Tile activeTile;
    private Point location;

    private boolean[] visibility = new boolean[LayersArray.LAYER_COUNT + 1];
    SpecialModes mode = SpecialModes.NONE;
    private BufferedImage drawingPen;

    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void setPointerLocation(Point location) {
        Point oldValue = this.location;
        this.location = location;
        pcs.firePropertyChange(LOCATION, oldValue, location);
    }

    public boolean isLayerVisible(int layer) {
        return visibility[layer];
    }


    public void toggleLayerVisibility(int layer) {
        this.visibility[layer] = !this.visibility[layer];
    }


    public boolean isPaintObstacles() {
        return mode == SpecialModes.OBSTACLE;
    }

    public boolean isHeroStartMode() {
        return mode == SpecialModes.HERO_START;
    }

    public boolean isAddJumpMode() {
        return mode == SpecialModes.JUMP;
    }

    public boolean isAddSpriteMode() {
        return mode == SpecialModes.SPRITE;
    }

    public void setMode(SpecialModes mode) {
        this.mode = mode;
    }

    public BufferedImage getDrawingPen() {
        BufferedImage tile;
        if (isPaintObstacles()) {
            tile = Palette.createObstacleDrawingPen();
        } else if (isHeroStartMode()) {
            tile = Palette.createHeroStartDrawingPen();
        } else if (isAddJumpMode()) {
            tile = Palette.createJumpPointDrawingPen();
        } else if (isAddSpriteMode()) {
            tile = Palette.createSpriteDrawingPen();
        } else {
            tile = drawingPen;
        }
        return tile;
    }


    public void setDrawingPen(BufferedImage drawingPen) {
        this.drawingPen = drawingPen;
    }

    public Model() {
        for (int i = 0; i < visibility.length; i++) {
            visibility[i] = true;
        }
    }

    public TileSet getTileSet(String name) {
        return tileSets.get(name);
    }

    public void setTileSets(TileSetBag tileSets) {
        this.tileSets = tileSets;
    }

    public Tile getActiveTile() {
        return activeTile;
    }

    protected void setActiveTile(Tile activeTile) {
        this.activeTile = activeTile;
    }

    public Scenery getActiveScenery() {
        return activeScenery;
    }

    public void setActiveScenery(Scenery activeScenery) {
        this.activeScenery = activeScenery;
    }


    public int getActiveLayerIndex() {
        if (isSpecialMode()) {
            return LayersArray.LAYER_COUNT;
        } else {
            return activeLayerIndex;
        }
    }

    private boolean isSpecialMode() {
        return mode != SpecialModes.NONE;
    }

    public void setActiveLayerIndex(int activeLayerIndex) {
        this.activeLayerIndex = activeLayerIndex;
    }

    public TileSetBag getTilesets() {
        return tileSets;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void addScenery(Scenery scenery) {
        this.project.addScenery(scenery);
    }

    public boolean isInSpecialLayer() {
        return activeLayerIndex == LayersArray.LAYER_COUNT;
    }

    public void addChangeListener(String name, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(name, listener);
    }

}
