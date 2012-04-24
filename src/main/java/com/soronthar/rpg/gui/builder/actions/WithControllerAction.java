package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;

public abstract class WithControllerAction extends AbstractAction {
    private Controller controller;

    public WithControllerAction(String name, Controller controller) {
        super(name);
        this.controller = controller;
        setTooltip(name);
    }

    public Controller getController() {
        return controller;
    }

    public void setIcon(Icon icon) {
        this.putValue(SMALL_ICON, icon);
    }

    public void setTooltip(String text) {
        this.putValue(SHORT_DESCRIPTION, text);
    }
}
