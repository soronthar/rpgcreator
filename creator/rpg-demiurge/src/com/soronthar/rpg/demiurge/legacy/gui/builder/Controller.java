package com.soronthar.rpg.demiurge.legacy.gui.builder;

import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Facing;
import com.soronthar.rpg.adventure.scenery.objects.actors.MobNpc;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.legacy.gui.builder.panes.PaintPanel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.panes.TilesetsPanel;
import com.soronthar.rpg.util.Point;
import org.soronthar.error.ApplicationException;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.soronthar.rpg.Utils.normalizePointToTile;


public class Controller {
    protected Model model;
    protected TilesetsPanel tilesetsPanel;
    protected PaintPanel paintPanel;

    public Controller(Model model) {
        this.model = model;
    }


    public void setDrawingPen(Tile activeTile) {
        model.setActiveTile(activeTile);
        if (activeTile != null) {
            notifyChangeDrawingPen(activeTile);
        }
    }

    @Deprecated
    public void addTileToActiveSceneryAtPoint(java.awt.Point point) {
        addTileToActiveSceneryAtPoint(Point.fromAWT(point));
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
            activeScenery.addJumpPoint(new JumpPoint(p, activeScenery.getId()));
            this.paintPanel.drawTileAtPoint(normalizePointToTile(p));
        } else if (model.isAddSpriteMode()) {
            Scenery activeScenery = model.getActiveScenery();
            int size = activeScenery.getSpriteMap().size();
            activeScenery.addSprite(new MobNpc("Sprite-" + size, p, Facing.DOWN));
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

    public void removeTileAtPoint(java.awt.Point point) {
        removeTileAtPoint(Point.fromAWT(point));
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
        } else if (model.isAddSpriteMode()) {
            model.getActiveScenery().removeSpriteAt(p);
            this.paintPanel.handleEraseTileEvent(p);
        } else {
            if (!model.isInSpecialLayer()) {
                Scenery activeScenery = model.getActiveScenery();
                activeScenery.setTile(null, model.getActiveLayerIndex(), p);
                this.paintPanel.handleEraseTileEvent(p);
            }
        }
    }

    protected void notifyChangeDrawingPen(Tile info) {
        BufferedImage drawingPen = null;
        if (info != null) {
            TileSet tileSet = model.getTileSet(info.getTilesetName());
            if (tileSet == null) {
                throw new ApplicationException("Tileset " + info.getTilesetName() + " is not loaded");
            }

            BufferedImage image = tileSet.image();
            Point point = info.getPoint();
            Dimension dimension = info.getDimension().toAWT();
            drawingPen = image.getSubimage(point.getX(), point.getY(), dimension.width, dimension.height);
        }
        model.setDrawingPen(drawingPen);
    }

    public void loadTilesets() {
        TileSetBag tileSets = new TileSetBagPersister().loadTilesets();
        model.setTileSets(tileSets);
        tilesetsPanel.setTileSets(model.getTilesets());
    }


    public void setTilesetPanel(TilesetsPanel tilesetsPanel) {
        this.tilesetsPanel = tilesetsPanel;
    }

    public void setPaintPanel(PaintPanel paintPanel) {
        this.paintPanel = paintPanel;
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
