package com.soronthar.rpg.demiurge.legacy.gui.builder;

import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.adventure.scenery.objects.actors.Facing;
import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;
import com.soronthar.rpg.demiurge.CoordinateUtil;
import com.soronthar.rpg.demiurge.components.paint.PaintCanvasModel;
import com.soronthar.rpg.demiurge.components.tilesets.TilesetsModel;
import com.soronthar.rpg.util.Point;
import org.soronthar.error.ApplicationException;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Controller {
    protected Model model;
    protected PaintCanvasModel canvasModel;
    protected TilesetsModel tilesetModel;

    public void setCanvasModel(PaintCanvasModel canvasModel) {
        this.canvasModel = canvasModel;
    }

    public Controller(Model model) {
        this.model = model;
        this.tilesetModel = new TilesetsModel();
    }


    public void setDrawingPen(Tile activeTile) {
        model.setActiveTile(activeTile);
        if (!model.isSpecialMode()) {
            notifyChangeDrawingPen(activeTile);
        }
    }

    public void addTileToActiveSceneryAtPoint(java.awt.Point point) {
        addTileToActiveSceneryAtPoint(Point.fromAWT(point));
    }

    public void addTileToActiveSceneryAtPoint(Point p) {
        if (model.isLoading()) return;
        if (model.isPaintObstacles()) {
            model.getActiveScenery().addObstacleAt(p);
        } else if (model.isHeroStartMode()) {
            model.getActiveScenery().setHeroStartingPoint(p);
        } else if (model.isAddJumpMode()) {
            Scenery activeScenery = model.getActiveScenery();
            activeScenery.addJumpPoint(new JumpPoint(p, activeScenery.getId()));
        } else if (model.isAddSpriteMode()) {
            Scenery activeScenery = model.getActiveScenery();
            int size = activeScenery.getSpriteMap().size();
            activeScenery.addSprite(new Sprite("Sprite-" + size, Sprite.Type.MOB, p, Facing.DOWN));
        } else {
            if (!model.isInSpecialLayer()) {
                Point point= Point.fromAWT(CoordinateUtil.tileToPoint(p.toAWT(),canvasModel.getCanvasSize()));
                Tile activeTile = model.getActiveTile();
                if (activeTile != null) {
                    model.getActiveScenery().setTile(activeTile, model.getActiveLayerIndex(), point);
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
        } else if (model.isHeroStartMode()) {
            model.getActiveScenery().setHeroStartingPoint(new Point(0, 0));
        } else if (model.isAddJumpMode()) {
            model.getActiveScenery().removeJumpAt(p);
        } else if (model.isAddSpriteMode()) {
            model.getActiveScenery().removeSpriteAt(p);
        } else {
            if (!model.isInSpecialLayer()) {
                Point point= Point.fromAWT(CoordinateUtil.tileToPoint(p.toAWT(),canvasModel.getCanvasSize()));
                Scenery activeScenery = model.getActiveScenery();
                activeScenery.setTile(null, model.getActiveLayerIndex(), point);
            }
        }
    }

    public void notifyChangeDrawingPen(Tile info) {
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
        getCanvasModel().setDrawingPen(drawingPen);
    }

    public TileSetBag loadTilesets() {
        TileSetBag tileSets = new TileSetBagPersister().loadTilesets();
        model.setTileSets(tileSets);
        return tileSets;
    }




    public Model getModel() {
        return model;
    }

    public TilesetsModel getTilesetModel() {
        return tilesetModel;
    }

    public PaintCanvasModel getCanvasModel() {
        return canvasModel;
    }
}
