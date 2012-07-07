package com.soronthar.rpg.demiurge.components.tilesets;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static com.soronthar.rpg.Utils.normalizePointToTile;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class TilesetsPanel extends JTabbedPane {
    public static final String TILE = "tile";
    TilesetsModel model;

    public TilesetsPanel(TilesetsModel model) {
        this.model = model;
        this.setTabPlacement(JTabbedPane.BOTTOM);
        this.setMinimumSize(Utils.getScaledTileDimension(12, 8).addPadding(23, 49).toAWT());
        this.setMaximumSize(Utils.getScaledTileDimension(12, 12).addPadding(23, 49).toAWT());
        this.setPreferredSize(Utils.getScaledTileDimension(12, 12).addPadding(23, 49).toAWT());

        setTileSets(model.getTileSets());
        this.model.addPropertyChangeListener(TilesetsModel.ALL_TILESETS,new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setTileSets(TilesetsPanel.this.model.getTileSets());
            }
        });
    }

    public TilesetsPanel() {
        this(new TilesetsModel());
    }


    public void setTileSets(TileSetBag tilesets) {
        this.removeAll();
        for (TileSet tileset : tilesets) {
            addTilesetTab(tileset);
        }
        revalidate();
    }

    private void addTilesetTab(TileSet tileSet) {
        JScrollPane jScrollPane = new JScrollPane(createTilePanelForSet(tileSet));
        this.addTab(Integer.toString(this.getTabCount()), null, jScrollPane, tileSet.getName());
        this.revalidate();
    }

    private TilePanel createTilePanelForSet(TileSet tileSet) {
        TilePanel panel = new TilePanel(tileSet.getName(), tileSet.image());
        MyMouseInputAdapter mouseInputAdapter = new MyMouseInputAdapter(panel);
        panel.addMouseListener(mouseInputAdapter);
        panel.addMouseMotionListener(mouseInputAdapter);
        return panel;
    }

    public class MyMouseInputAdapter extends MouseInputAdapter {
        private TilePanel tilePanel;

        private Point startPoint;
        private Point lastPoint;
        private Point topLeft;

        private Dimension dimension = new Dimension(0, 0);


        public MyMouseInputAdapter(TilePanel tilePanel) {
            this.tilePanel = tilePanel;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            startPoint = normalizePointToTile(e.getPoint());
            topLeft = startPoint;
            lastPoint = startPoint;
            dimension = Utils.getTileDimension().toAWT();
            tilePanel.selectTileAt(topLeft, dimension);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point =  normalizePointToTile(e.getPoint());
            if (this.lastPoint.equals(point)) return;

            this.lastPoint = point;
            calculateNewSelection(this.startPoint, point);
            tilePanel.selectTileAt(topLeft, dimension);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Point point =  normalizePointToTile(e.getPoint());
            if (point.x >= this.tilePanel.getWidth() || point.y >= this.tilePanel.getHeight()) return;

            calculateNewSelection(this.startPoint, point);
            Tile tile = new Tile(tilePanel.getName(), topLeft, dimension);

            this.tilePanel.selectTileAt(topLeft, dimension);
            TilesetsPanel.this.firePropertyChange(TILE,null,tile);

            startPoint = null;
            topLeft = null;
        }

        private void calculateNewSelection(Point startPoint, Point endPoint) {
            this.topLeft = new Point(min(startPoint.x, endPoint.x), min(startPoint.y, endPoint.y));
            Point bottomRight = new Point(max(startPoint.x, endPoint.x), max(startPoint.y, endPoint.y));
            int w = bottomRight.x - this.topLeft.x + Tile.TILE_SIZE;
            int h = bottomRight.y - topLeft.y + Tile.TILE_SIZE;
            this.dimension = new Dimension(w, h);
        }
    }
}
