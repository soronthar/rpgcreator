package com.soronthar.rpg.demiurge;

import com.javadocking.DockingManager;
import com.javadocking.dock.Position;
import com.javadocking.dock.SingleDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockingMode;
import com.javadocking.model.FloatDockModel;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.demiurge.components.ColoredJPanel;
import com.soronthar.rpg.demiurge.components.paint.PaintCanvasModel;
import com.soronthar.rpg.demiurge.components.paint.PaintPanel;
import com.soronthar.rpg.demiurge.components.tilesets.TilesetsPanel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.ActionsManager;
import com.soronthar.rpg.demiurge.legacy.gui.builder.components.layers.LayerPanel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.components.scenery.SceneryTree;
import com.soronthar.rpg.demiurge.legacy.gui.builder.panes.BuilderToolBar;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class RpgDemiurgeContent extends JPanel {
    private PaintPanel paintPanel;
    private BuilderToolBar toolBar;
    private TilesetsPanel tilesetsPanel;
    private SceneryTree sceneryTree;
    private ColoredJPanel statusBar;
    private FloatDockModel dockModel;
    private LayerPanel layerPanel;

    public RpgDemiurgeContent(RpgCreatorController controller, ActionsManager actionsManager, JFrame parent) {
        super(new GridBagLayout());
        // Create the dock model for the docks.
        dockModel = new FloatDockModel();
        dockModel.addOwner("frame0", parent);

        // Give the dock model to the docking manager.
        DockingManager.setDockModel(dockModel);

        createComponents(controller, actionsManager);
        addComponentsToPanel();
        controller.setMainGUI(this);
    }

    private void createComponents(final RpgCreatorController controller, ActionsManager actionsManager) {
        PaintCanvasModel canvasModel = new PaintCanvasModel();

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

        paintPanel = new PaintPanel(controller, canvasModel);
        toolBar = new BuilderToolBar(actionsManager);
        toolBar.setEnabled(false);
        tilesetsPanel = new TilesetsPanel(controller.getTilesetModel());
        tilesetsPanel.addPropertyChangeListener(TilesetsPanel.TILE,new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                controller.setDrawingPen((Tile) evt.getNewValue());
            }
        });

        sceneryTree = new SceneryTree(controller);
        layerPanel = new LayerPanel(controller);
        statusBar = new ColoredJPanel(Color.lightGray);
        final JLabel label = new JLabel("0,0");
        statusBar.add(label);
        canvasModel.addChangeListener(PaintCanvasModel.LOCATION, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Point point = (Point) evt.getNewValue();
                label.setText(point.x + "," + point.y);
            }
        });

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
        TabDock tabs = new TabDock();
        Dockable sceneriesDock = new DefaultDockable("Scenery", sceneryTree, "Scenery", null, DockingMode.ALL);
        tabs.addDockable(sceneriesDock, new Position(0));
        Dockable layersDock = new DefaultDockable("Layers", layerPanel, "Layers", null, DockingMode.ALL);
        tabs.addDockable(layersDock, new Position(1));
        tabs.setSelectedDockable(sceneriesDock);
        dockModel.addRootDock("sceneryTree", tabs, dockModel.getOwner(0));
        contentPane.add(tabs, g);
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


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.layerPanel.setEnabled(enabled);
        this.sceneryTree.setEnabled(enabled);
        this.tilesetsPanel.setEnabled(enabled);
        this.paintPanel.setEnabled(enabled);
        this.toolBar.setEnabled(enabled);
    }

}
