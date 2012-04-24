package com.soronthar.rpg.gui.builder.actions.base;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;

public abstract class ShowDialogAction extends WithControllerAction {
    private JFrame frame;

    public ShowDialogAction(String name, String iconName, JFrame frame, Controller controller) {
        super(name, iconName, controller);
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }
}
