package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.ShowDialogAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ManageTilesetAction extends ShowDialogAction {

    public ManageTilesetAction(JFrame frame, DemiurgeController controller) {
        super("Manage Tilesets", "", frame,controller);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(getFrame(), "good");
    }
}
