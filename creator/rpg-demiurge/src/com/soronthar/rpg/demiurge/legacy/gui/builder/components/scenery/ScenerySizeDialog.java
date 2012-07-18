package com.soronthar.rpg.demiurge.legacy.gui.builder.components.scenery;

import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScenerySizeDialog extends JDialog {
    public ScenerySizeDialog(final SceneryTree sceneryTree, Scenery scenery, final DemiurgueController controller) {
        super((Frame) SwingUtilities.getAncestorOfClass(JFrame.class, sceneryTree), "Scenery " + scenery.getName() + " Size", true);
        this.setLayout(new FlowLayout());
        JLabel width = new JLabel("Width:");
        final JLabel height = new JLabel("Height:");
        final JTextField widthValue = new JTextField(Integer.toString(scenery.getWidth()));
        final JTextField heightValue = new JTextField(Integer.toString(scenery.getHeight()));

        this.add(width);
        this.add(widthValue);
        this.add(height);
        this.add(heightValue);

        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) sceneryTree.getSelectionPath().getLastPathComponent();
                Scenery scenery = (Scenery) node.getUserObject();

                controller.setScenerySize(scenery.getId(), new org.soronthar.geom.Dimension(Integer.parseInt(widthValue.getText()), Integer.parseInt(heightValue.getText())));
                ScenerySizeDialog.this.setVisible(false);
            }
        });
        this.add(button);
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            this.setLocationRelativeTo(this.getOwner());
            this.pack();
        }
        super.setVisible(b);
    }
}
