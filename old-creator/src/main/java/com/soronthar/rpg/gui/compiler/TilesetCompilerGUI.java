package com.soronthar.rpg.gui.compiler;

import com.soronthar.rpg.gui.builder.panes.PaintPanel;
import com.soronthar.rpg.gui.builder.panes.TilesetsPanel;
import com.soronthar.rpg.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class TilesetCompilerGUI extends JSplitPane {
    private PaintPanel paintPanel;
    private TilesetsPanel tilesetsPanel;

    public TilesetCompilerGUI(TilesetCompilerController controller) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        Dimension tileDimension = Utils.getScaledTileDimension(12, 28).toAWT();
        paintPanel = new PaintPanel(controller, tileDimension.width, tileDimension.height);
        tilesetsPanel = new TilesetsPanel(controller);
        tilesetsPanel.setTabPlacement(JTabbedPane.TOP);
        addComponentsToPanel();
    }

    private void addComponentsToPanel() {
        this.add(tilesetsPanel);
        this.add(paintPanel);
    }
}
