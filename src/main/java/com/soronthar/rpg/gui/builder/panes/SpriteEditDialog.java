package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.model.JumpPoint;
import com.soronthar.rpg.model.objects.sprites.Npc;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.scenery.SceneryBag;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

class SpriteEditDialog extends JDialog {

    public SpriteEditDialog(final Sprite sprite, final Point point, final Controller controller) {
        super(getAncestor(controller), "Edit Sprite", true);
        this.setLayout(new FlowLayout());

        final JCheckBox isVisible = new JCheckBox("Visible:");
        isVisible.setSelected(sprite.isVisible());
        final JCheckBox isSolid = new JCheckBox("Solid:");
        isSolid.setSelected(sprite.isSolid());

        this.add(isVisible);
        this.add(isSolid);
        this.add(new JLabel("Frame Map Image:"));
        final JTextField image = new JTextField(((Npc) sprite).getFrameMapName());
        this.add(image);
        JButton button = new JButton("OK");
        this.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sprite.setVisible(isVisible.isSelected());
                sprite.setSolid(isSolid.isSelected());
                    ((Npc) sprite).setFrameMapName(image.getText());
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
