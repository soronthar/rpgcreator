package com.soronthar.rpg.gui.builder;

import com.soronthar.rpg.gui.builder.components.paint.Palette;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.LayersArray;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSet;
import com.soronthar.rpg.model.tiles.TileSetBag;

import java.awt.image.BufferedImage;

public class Model {
    private TileSetBag tileSets = new TileSetBag();
    private Project project;
    private Scenery activeScenery = Scenery.NULL_SCENERY;
    private int activeLayerIndex;
    private Tile activeTile;

    private boolean[] visibility = new boolean[LayersArray.LAYER_COUNT + 1];


    private boolean paintObstacles = false;
    private boolean heroStartMode = false;
    private boolean addJumpMode = false;
    private BufferedImage drawingPen;


    public boolean isLayerVisible(int layer) {
        return visibility[layer];
    }

    public void setLayerVisibility(int layer, boolean visibility) {
        this.visibility[layer] = visibility;
    }

    public void toggleLayerVisibility(int layer) {
        this.visibility[layer] = !this.visibility[layer];
    }


    public boolean isPaintObstacles() {
        return paintObstacles;
    }

    public boolean isHeroStartMode() {
        return heroStartMode;
    }

    public boolean isAddJumpMode() {
        return addJumpMode;
    }

    public void setPaintObstacles(boolean paintObstacles) {
        this.paintObstacles = paintObstacles;
    }

    public void setHeroStartMode(boolean heroStartMode) {
        this.heroStartMode = heroStartMode;
    }

    public void setAddJumpMode(boolean addJumpMode) {
        this.addJumpMode = addJumpMode;
    }


    public BufferedImage getDrawingPen() {
        BufferedImage tile;
        if (isPaintObstacles()) {
            tile = Palette.createObstacleDrawingPen();
        } else if (isHeroStartMode()) {
            tile = Palette.createHeroStartDrawingPen();
        } else if (isAddJumpMode()) {
            tile = Palette.createJumpPointDrawingPen();
        } else {
            switch (getActiveLayerIndex()) {
                case LayersArray.SPRITE_LAYER_INDEX:
                    tile = Palette.createSpriteDrawingPen();
                    break;
                default:
                    tile = drawingPen;
                    break;
            }
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
        if (isPaintObstacles() || isHeroStartMode() || isAddJumpMode()) {
            return LayersArray.LAYER_COUNT;
        } else {
            return activeLayerIndex;
        }
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
}
