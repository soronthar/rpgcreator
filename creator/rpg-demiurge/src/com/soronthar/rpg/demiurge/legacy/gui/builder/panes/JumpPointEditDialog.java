package com.soronthar.rpg.demiurge.legacy.gui.builder.panes;

import com.soronthar.rpg.adventure.project.Project;
import com.soronthar.rpg.adventure.scenery.Scenery;
import com.soronthar.rpg.adventure.scenery.SceneryBag;
import com.soronthar.rpg.adventure.scenery.objects.JumpPoint;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Controller;

import javax.swing.*;
import java.awt.*;
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
public class JumpPointEditDialog extends JDialog {

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
                controller.getModel().getActiveScenery().addJumpPoint(new JumpPoint(point.x,point.y, selectedItem.getId()));
                setVisible(false);
            }
        });
        this.setLocationRelativeTo(this.getOwner());
        this.pack();
    }


    private static JFrame getAncestor(Controller controller) {
        return null;
    }
}
