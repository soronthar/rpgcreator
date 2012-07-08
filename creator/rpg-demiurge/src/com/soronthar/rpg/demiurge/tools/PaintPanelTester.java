package com.soronthar.rpg.demiurge.tools;

import com.soronthar.rpg.demiurge.components.ColoredJPanel;
import com.soronthar.rpg.demiurge.components.paint.PaintCanvasModel;
import com.soronthar.rpg.demiurge.components.paint.PaintPanel;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Controller;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PaintPanelTester extends JFrame {

    public PaintPanelTester() throws HeadlessException {
        super("Paint Panel Tester");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        Model model = new Model();
        Controller controller = new Controller(model);
        PaintCanvasModel canvasModel = new PaintCanvasModel();
        PaintPanel paintPanel=new PaintPanel(controller, canvasModel);

        this.add(paintPanel,BorderLayout.CENTER);

        ColoredJPanel statusBar = new ColoredJPanel(Color.lightGray);
        final JLabel label = new JLabel("0,0");
        statusBar.add(label);
        canvasModel.addChangeListener(PaintCanvasModel.LOCATION, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Point point = (Point) evt.getNewValue();
                label.setText(point.x + "," + point.y);
            }
        });
        
        this.add(statusBar,BorderLayout.SOUTH);

        pack();
    }

    public static void main(String[] args) {
        new PaintPanelTester().setVisible(true);
    }
}
