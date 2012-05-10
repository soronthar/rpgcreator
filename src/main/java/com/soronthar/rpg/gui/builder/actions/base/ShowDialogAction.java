package com.soronthar.rpg.gui.builder.actions.base;

import com.soronthar.rpg.gui.builder.RpgCreatorController;

import javax.swing.*;

public abstract class ShowDialogAction extends WithControllerAction {
    private JFrame frame;

    public ShowDialogAction(String name, String iconName, JFrame frame, RpgCreatorController controller) {
        super(name, iconName, controller);
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }
}
