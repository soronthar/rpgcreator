package com.soronthar.rpg.demiurge.legacy.gui.compiler;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.demiurge.components.tilesets.TilesetsPanel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.panes.PaintPanel;

import javax.swing.*;
import java.awt.*;

public class TilesetCompilerGUI extends JSplitPane {
    private PaintPanel paintPanel;
    private TilesetsPanel tilesetsPanel;

    public TilesetCompilerGUI(TilesetCompilerController controller) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        Dimension tileDimension = Utils.getScaledTileDimension(16, 32).toAWT();
        paintPanel = new PaintPanel(controller, tileDimension.width, tileDimension.height);
        tilesetsPanel = new TilesetsPanel();
        tilesetsPanel.setTileSets(controller.loadTilesets());
        tilesetsPanel.setTabPlacement(JTabbedPane.TOP);
        addComponentsToPanel();
    }

    private void addComponentsToPanel() {
        this.add(tilesetsPanel);
        this.add(paintPanel);
    }
}
