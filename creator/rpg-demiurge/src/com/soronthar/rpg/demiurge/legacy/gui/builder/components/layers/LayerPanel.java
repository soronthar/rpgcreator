package com.soronthar.rpg.demiurge.legacy.gui.builder.components.layers;

import com.soronthar.rpg.Utils;
import com.soronthar.rpg.adventure.scenery.LayersArray;
import com.soronthar.rpg.demiurge.components.paint.PaintCanvasModel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class LayerPanel extends JScrollPane {


    private final JTable layersTable;

    public LayerPanel(final DemiurgeController controller, final PaintCanvasModel canvasModel) {
        layersTable = new JTable(new MyTableModel());
        layersTable.setMinimumSize(Utils.getScaledTileDimension(8, 2).addPadding(23, 49).toAWT());
        layersTable.setMaximumSize(Utils.getScaledTileDimension(8, 2).addPadding(23, 49).toAWT());
        layersTable.setPreferredScrollableViewportSize(Utils.getScaledTileDimension(8, 2).addPadding(23, 49).toAWT());
        layersTable.setFillsViewportHeight(true);
        setViewportView(layersTable);
        layersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        layersTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getLastRow();
                    canvasModel.toggleLayerVisibility(row);
                }
            }
        });


        layersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = layersTable.getSelectedRow();
                    canvasModel.setActiveLayer(row);
                }
            }
        });
    }

    class MyTableModel extends AbstractTableModel {
        public final int ROWS = LayersArray.LAYER_COUNT + 1;
        private String[] columnNames = {"Is Visible", "Layer"};
        private boolean[] isVisible = new boolean[ROWS];

        MyTableModel() {
            for (int i = 0; i < isVisible.length; i++) {
                isVisible[i] = true;
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return ROWS;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    return isVisible[row];
                case 1:
                    if (row == ROWS - 1) {
                        return "Specials";
                    } else {
                        return LayersArray.LAYER_NAMES[row];
                    }
                default:
                    return null;
            }
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            return col == 0;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (col == 0) {
                isVisible[row] = (Boolean) value;
                fireTableCellUpdated(row, col);
            }
        }

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.layersTable.setEnabled(enabled);
    }
}
