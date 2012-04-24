package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;

public abstract class ShowDialogAction extends WithControllerAction {
    private JFrame frame;

    public ShowDialogAction(String name, JFrame frame, Controller controller) {
        super(name, controller);
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }
}
