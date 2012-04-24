package com.soronthar.rpg.gui.builder;

import com.javadocking.DockingManager;
import com.javadocking.dock.Position;
import com.javadocking.dock.SingleDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockingMode;
import com.javadocking.model.FloatDockModel;
import com.soronthar.rpg.gui.builder.actions.ActionsManager;
import com.soronthar.rpg.gui.builder.components.layers.LayerPanel;
import com.soronthar.rpg.gui.builder.components.scenery.SceneryTree;
import com.soronthar.rpg.gui.builder.panes.BuilderToolBar;
import com.soronthar.rpg.gui.builder.panes.PaintPanel;
import com.soronthar.rpg.gui.builder.panes.TilesetsPanel;

import javax.swing.*;
import java.awt.*;

public class BuilderGUI extends JPanel {
    private PaintPanel paintPanel;
    private BuilderToolBar toolBar;
    private TilesetsPanel tilesetsPanel;
    private SceneryTree sceneryTree;
    private ColoredJPanel statusBar;
    private FloatDockModel dockModel;
    private LayerPanel layerPanel;

    public BuilderGUI(Controller controller, ActionsManager actionsManager, RpgCreator rpgCreator) {
        super(new GridBagLayout());
        // Create the dock model for the docks.
        dockModel = new FloatDockModel();
        dockModel.addOwner("frame0", rpgCreator);

        // Give the dock model to the docking manager.
        DockingManager.setDockModel(dockModel);

        createComponents(controller, actionsManager);
        addComponentsToPanel();
    }

    private void createComponents(Controller controller, ActionsManager actionsManager) {
        paintPanel = new PaintPanel(controller);
        toolBar = new BuilderToolBar(actionsManager);
        tilesetsPanel = new TilesetsPanel(controller);
        sceneryTree = new SceneryTree(controller);
        layerPanel = new LayerPanel(controller, actionsManager);
        statusBar = new ColoredJPanel(Color.BLACK);
    }

    private void addComponentsToPanel() {
        GridBagConstraints g = new GridBagConstraints(0, 0, 0, 0, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
        addToolBar(g, toolBar);
        addTileSetsPanel(g, tilesetsPanel);
        addMapTree(g, sceneryTree);
        addStatusBar(g, statusBar);
        addPaintPanel(g, paintPanel);
    }

    private void addPaintPanel(GridBagConstraints g, PaintPanel paintPanel) {
        Container contentPane = this;
        g.gridx = 1;
        g.gridy = 1;
        g.gridheight = 3;
        g.gridwidth = 1;
        g.weightx = 1;
        g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        contentPane.add(paintPanel, g);
    }

    private void addStatusBar(GridBagConstraints g, JPanel statusBar) {
        Container contentPane = this;
        g.gridx = 0;
        g.gridy = 4;
        g.gridheight = 1;
        g.gridwidth = 2;
        g.weightx = 1;
        g.weighty = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(statusBar, g);
    }

    private void addMapTree(GridBagConstraints g, SceneryTree sceneryTree) {
        Container contentPane = this;
        g.gridx = 0;
        g.gridy = 2;
        g.gridheight = 2;
        g.gridwidth = 1;
        g.weightx = 0;
        g.weighty = 0.2;
        g.fill = GridBagConstraints.BOTH;
        TabDock leftDock = new TabDock();
        Dockable dockable = new DefaultDockable("Window1", sceneryTree, "Scenery", null, DockingMode.ALL);
        leftDock.addDockable(dockable, new Position(0));

        Dockable layers = new DefaultDockable("Window2", layerPanel, "Layers", null, DockingMode.ALL);
        leftDock.addDockable(layers, new Position(1));

        dockModel.addRootDock("sceneryTree", leftDock, dockModel.getOwner(0));
        contentPane.add(leftDock, g);
    }

    private void addTileSetsPanel(GridBagConstraints g, TilesetsPanel tilesetsPanel) {
        Container contentPane = this;
        g.gridx = 0;
        g.gridy = 1;
        g.gridheight = 1;
        g.gridwidth = 1;
        g.weightx = 0;
        g.weighty = .2;
        g.fill = GridBagConstraints.BOTH;

        SingleDock leftDock = new SingleDock();
        Dockable dockable = new DefaultDockable("Window2", tilesetsPanel, "Tilesets", null, DockingMode.ALL);
        leftDock.addDockable(dockable, new Position(0));
        dockModel.addRootDock("tilesetsPanel", leftDock, dockModel.getOwner(0));
        contentPane.add(leftDock, g);
    }

    private void addToolBar(GridBagConstraints g, BuilderToolBar toolbar) {
        Container contentPane = this;
        g.gridx = 0;
        g.gridy = 0;
        g.gridheight = 1;
        g.gridwidth = 2;
        g.weightx = 1;
        g.weighty = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(toolbar, g);
    }

    private static class ColoredJPanel extends JPanel {
        public ColoredJPanel(Color color) {
            this.setBackground(color);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }
    }

}
