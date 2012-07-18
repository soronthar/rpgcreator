package com.soronthar.rpg.demiurge.legacy.gui.compiler;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.demiurge.components.paint.PaintCanvasModel;
import com.soronthar.rpg.demiurge.components.paint.PaintPanel;
import com.soronthar.rpg.demiurge.components.tilesets.TilesetsModel;
import com.soronthar.rpg.demiurge.components.tilesets.TilesetsPanel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TilesetCompilerGUI extends JSplitPane {
    private PaintPanel paintPanel;
    private TilesetsPanel tilesetsPanel;

    public TilesetCompilerGUI(final TilesetCompilerController controller) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        PaintCanvasModel canvasModel = new PaintCanvasModel();
        controller.setCanvasModel(canvasModel);
        canvasModel.addChangeListener(PaintCanvasModel.Action.DRAW.name(), new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.addTileToActiveSceneryAtPoint((java.awt.Point) evt.getNewValue());
            }
        });

        canvasModel.addChangeListener(PaintCanvasModel.Action.ERASE.name(), new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.removeTileAtPoint((java.awt.Point) evt.getNewValue());
            }
        });
        canvasModel.setCanvasSize(Utils.getScaledTileDimension(16, 32).toAWT());
        //TODO: it is redundant to pass the model and the dimension
        paintPanel = new PaintPanel(canvasModel);
        tilesetsPanel = new TilesetsPanel(new TilesetsModel(controller.loadTilesets()));
        tilesetsPanel.addPropertyChangeListener(TilesetsPanel.TILE,new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setDrawingPen((Tile) evt.getNewValue());
            }
        });
        tilesetsPanel.setTabPlacement(JTabbedPane.TOP);
        addComponentsToPanel();
    }

    private void addComponentsToPanel() {
        this.add(tilesetsPanel);
        this.add(paintPanel);
    }

    public PaintPanel getPaintPanel() {
        return this.paintPanel;
    }
}
