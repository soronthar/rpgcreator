package com.soronthar.rpg.demiurge.components.tilesets;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.tileset.Tile;
import com.soronthar.rpg.adventure.tileset.TileSet;
import com.soronthar.rpg.adventure.tileset.TileSetBag;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static com.soronthar.rpg.demiurge.CoordinateUtil.normalizePointToTile;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class TilesetsPanel extends JPanel {
    public static final String TILE = "tile";
    private TilesetsModel model;
    private JComboBox tilesetList = new JComboBox(new DefaultComboBoxModel());

    public TilesetsPanel(TilesetsModel model) {
        this.model = model;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.setMinimumSize(Utils.getScaledTileDimension(12, 8).addPadding(23, 49).toAWT());
        this.setMaximumSize(Utils.getScaledTileDimension(12, 12).addPadding(23, 49).toAWT());
        this.setPreferredSize(Utils.getScaledTileDimension(12, 12).addPadding(23, 49).toAWT());
        this.add(tilesetList);
        setTileSets(model.getTileSets());
        this.model.addChangeListener(TilesetsModel.ALL_TILESETS, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setTileSets(TilesetsPanel.this.model.getTileSets());
            }
        });
        final TilePanel tilePanelForSet = createTilePanelForSet();

        tilesetList.setRenderer(new BasicComboBoxRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                TileSet tileSet = (TileSet) value;

                return super.getListCellRendererComponent(list, (tileSet != null ? tileSet.getName() : null), index, isSelected, cellHasFocus);
            }
        });
        tilesetList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                TileSet tileSet = (TileSet) cb.getSelectedItem();

                if (tileSet != null) {
                    tilePanelForSet.setTileset(tileSet);
                }
                revalidate();
            }
        });
        tilesetList.setPreferredSize(new Dimension(150,25));
        tilesetList.setMaximumSize(new Dimension(1024,25));
        this.add(tilesetList);

        JScrollPane jScrollPane = new JScrollPane(tilePanelForSet);

        jScrollPane.setEnabled(false);
        this.add(jScrollPane);

    }


    public void setTileSets(TileSetBag tilesets) {
        DefaultComboBoxModel defaultComboBoxModel = (DefaultComboBoxModel) tilesetList.getModel();
        defaultComboBoxModel.removeAllElements();

        for (TileSet tileset : tilesets) {
            defaultComboBoxModel.addElement(tileset);
        }

        revalidate();
    }

    private TilePanel createTilePanelForSet() {
        TilePanel panel = new TilePanel();
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
            if (!this.tilePanel.isEnabled()) return;
            startPoint = normalizePointToTile(e.getPoint());
            topLeft = startPoint;
            lastPoint = startPoint;
            dimension = Utils.getTileDimension().toAWT();
            tilePanel.selectTileAt(topLeft, dimension);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!this.tilePanel.isEnabled()) return;

            Point point = normalizePointToTile(e.getPoint());
            if (this.lastPoint.equals(point)) return;

            this.lastPoint = point;
            calculateNewSelection(this.startPoint, point);
            tilePanel.selectTileAt(topLeft, dimension);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!this.tilePanel.isEnabled()) return;
            Point point = normalizePointToTile(e.getPoint());
            if (point.x >= this.tilePanel.getWidth() || point.y >= this.tilePanel.getHeight()) return;

            calculateNewSelection(this.startPoint, point);
            Tile tile = new Tile(tilePanel.getName(), topLeft, dimension);

            this.tilePanel.selectTileAt(topLeft, dimension);
            TilesetsPanel.this.firePropertyChange(TILE, null, tile);

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
