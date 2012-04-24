package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.components.tiles.TilePanel;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSet;
import com.soronthar.rpg.model.tiles.TileSetBag;
import org.soronthar.geom.Dimension;
import org.soronthar.geom.Point;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

import static com.soronthar.rpg.Utils.normalizePointToTile;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class TilesetsPanel extends JTabbedPane {
    private Controller controller;

    public TilesetsPanel(Controller controller) {
        this.setTabPlacement(JTabbedPane.BOTTOM);
        this.setMinimumSize(Utils.getScaledTileDimension(8, 8).addPadding(23, 49));
        this.setMaximumSize(Utils.getScaledTileDimension(8, 12).addPadding(23, 49));
        this.setPreferredSize(Utils.getScaledTileDimension(8, 12).addPadding(23, 49));
        controller.setTilesetPanel(this);
        this.controller = controller;
    }


    public void setTileSets(TileSetBag tilesets) {
        for (TileSet tileset : tilesets) {
            addTilesetTab(tileset);
        }
    }

    public void addTilesetTab(TileSet tileSet) {
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
            dimension = Utils.getTileDimension();
            tilePanel.selectTileAt(topLeft, dimension);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Point point = normalizePointToTile(e.getPoint());
            if (this.lastPoint.equals(point)) return;

            this.lastPoint = point;
            calculateNewSelection(this.startPoint, point);
            tilePanel.selectTileAt(topLeft, dimension);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Point point = normalizePointToTile(e.getPoint());
            if (point.x >= this.tilePanel.getWidth() || point.y >= this.tilePanel.getHeight()) return;

            calculateNewSelection(this.startPoint, point);
            Tile tile = new Tile(tilePanel.getName(), topLeft, dimension);

            this.tilePanel.tileSelectedEvent(tile);
            controller.setActiveTile(tile);

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
