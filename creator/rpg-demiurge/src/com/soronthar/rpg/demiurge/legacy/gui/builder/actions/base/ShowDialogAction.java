package com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;

import javax.swing.*;

public abstract class ShowDialogAction extends WithControllerAction {
    private JFrame frame;

    public ShowDialogAction(String name, String iconName, JFrame frame, DemiurgeController controller) {
        super(name, iconName, controller);
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }
}
