package com.soronthar.rpg.demiurge.legacy.gui.builder;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.legacy.ImageLoader;

import java.awt.image.BufferedImage;

public class Model {
    public enum SpecialModes {
        NONE, OBSTACLE, HERO_START, JUMP, SPRITE
    }

    private TileSetBag tileSets = new TileSetBag();
    private Project project;
    private Scenery activeScenery = Scenery.NULL_SCENERY;
    private int activeLayerIndex;
    private int lastActiveLayerIndex;
    private Tile activeTile;


    SpecialModes mode = SpecialModes.NONE;
    private BufferedImage drawingPen;


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

    public Model() {

    }

    public TileSet getTileSet(String name) {
        return tileSets.get(name);
    }

    public void setTileSets(TileSetBag tileSets) {
        this.tileSets = tileSets.clone();
        for (TileSet tileSet : this.tileSets) {
            tileSet.setImage(new ImageLoader().load("tilesets/" + tileSet.getResourceName()));
        }
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

    public boolean isSpecialMode() {
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

}
