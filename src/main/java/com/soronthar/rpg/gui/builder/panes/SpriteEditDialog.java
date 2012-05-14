package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.model.objects.sprites.MobNpc;
import com.soronthar.rpg.model.objects.sprites.Sprite;
import com.soronthar.rpg.model.objects.sprites.StandingNpc;
import com.soronthar.rpg.model.scenery.Scenery;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        final JTextField image = new JTextField();

        this.add(image);
        final JComboBox comboBox = new JComboBox(new String[]{"Npc", "Mob"});
        this.add(comboBox);

        if (sprite instanceof MobNpc) {
            image.setText(((MobNpc) sprite).getFramesImageName());
            comboBox.setSelectedIndex(1);
        } else {
            comboBox.setSelectedIndex(0);
            image.setText(((StandingNpc) sprite).getFramesImageName());
        }


        JButton button = new JButton("OK");
        this.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scenery activeScenery = controller.getModel().getActiveScenery();
                if (comboBox.getSelectedItem().equals("Mob") && sprite instanceof StandingNpc) {
                    activeScenery.removeSpriteAt(point);
                    MobNpc newSprite = new MobNpc(sprite.getId(), point, sprite.getFacing());
                    activeScenery.addSprite(newSprite);
                    newSprite.setVisible(isVisible.isSelected());
                    newSprite.setSolid(isSolid.isSelected());
                    newSprite.setFramesImage(image.getText());
                } else if (comboBox.getSelectedItem().equals("Npc") && sprite instanceof MobNpc) {
                    activeScenery.removeSpriteAt(point);
                    StandingNpc newSprite = new StandingNpc(sprite.getId(), point, sprite.getFacing());
                    activeScenery.addSprite(newSprite);
                    newSprite.setVisible(isVisible.isSelected());
                    newSprite.setSolid(isSolid.isSelected());
                    newSprite.setFramesImage(image.getText());
                } else if (sprite instanceof MobNpc) {
                    sprite.setVisible(isVisible.isSelected());
                    sprite.setSolid(isSolid.isSelected());
                    ((MobNpc) sprite).setFramesImage(image.getText());
                } else {
                    ((StandingNpc) sprite).setFramesImage(image.getText());
                }

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
