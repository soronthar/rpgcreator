package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.scenery.SceneryBag;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
* Created by IntelliJ IDEA.
* User: Administrador
* Date: 07/05/12
* Time: 03:54 AM
* To change this template use File | Settings | File Templates.
*/
class JumpPointEditDialog extends JDialog {

    public JumpPointEditDialog(JumpPoint jumpPoint, final Point point, final Controller controller) {
        super(getAncestor(controller), "Edit JumpPoint", true);
        this.setLayout(new FlowLayout());

        Project project = controller.getModel().getProject();
        SceneryBag sceneries = project.getSceneries();
        Vector vector = new Vector();
        int i = 0;
        int selected = 0;
        for (Scenery scenery : sceneries) {
            vector.add(scenery);
            if (scenery.getId() == jumpPoint.getTargetId()) {
                selected = i;
            }
            i++;
        }
        final JComboBox combo = new JComboBox(vector);
        combo.setSelectedIndex(selected);
        JLabel label = new JLabel("Choose Scenery:");
        this.add(label);
        this.add(combo);
        JButton button = new JButton("OK");
        this.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scenery selectedItem = (Scenery) combo.getSelectedItem();
                controller.getModel().getActiveScenery().addJumpPoint(new JumpPoint(point, selectedItem.getId()));
                setVisible(false);
            }
        });
        this.setLocationRelativeTo(this.getOwner());
        this.pack();
    }


    private static JFrame getAncestor(Controller controller) {
        return (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, controller.getPaintPanel());
    }
}
