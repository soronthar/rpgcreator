package com.soronthar.rpg.gui.compiler;

import com.soronthar.rpg.gui.builder.panes.PaintPanel;
import com.soronthar.rpg.gui.builder.panes.TilesetsPanel;

import javax.swing.*;

public class BuilderGUI extends JSplitPane {
    private PaintPanel paintPanel;
    private TilesetsPanel tilesetsPanel;

    public BuilderGUI(TilesetCompilerController controller, JFrame parent) {
        super(JSplitPane.HORIZONTAL_SPLIT);
        paintPanel = new PaintPanel(controller);
        tilesetsPanel = new TilesetsPanel(controller);
        addComponentsToPanel();
    }

    private void addComponentsToPanel() {
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        jSplitPane.add(tilesetsPanel);
        jSplitPane.add(paintPanel);
        this.add(jSplitPane);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.tilesetsPanel.setEnabled(enabled);
        this.paintPanel.setEnabled(enabled);
    }
}
