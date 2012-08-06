package com.soronthar.rpg.demiurge.legacy.gui.builder.panes;

import com.soronthar.rpg.adventure.scenery.objects.actors.Sprite;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpriteEditDialog extends JDialog {

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
        final JTextField image = new JTextField();

        this.add(image);
        final JComboBox comboBox = new JComboBox(new String[]{Sprite.Type.NPC.toString(),Sprite.Type.MOB.toString()});
        this.add(comboBox);

        switch (sprite.getType()) {
            case NPC:
                comboBox.setSelectedIndex(0);
                break;
            case MOB:
                comboBox.setSelectedIndex(1);
                break;
            default:
                break;
        }
        image.setText(sprite.getFramesImageName());


        JButton button = new JButton("OK");
        this.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String type = (String) comboBox.getSelectedItem();
                sprite.setType(Sprite.Type.valueOf(type));
                sprite.setVisible(isVisible.isSelected());
                sprite.setSolid(isSolid.isSelected());
                sprite.setFramesImage(image.getText());
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
