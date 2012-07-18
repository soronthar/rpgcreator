package com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;

import javax.swing.*;

public abstract class WithControllerAction extends IconedAction {
    private DemiurgueController controller;

    public WithControllerAction(String name, String iconName, DemiurgueController controller) {
        super(name, iconName);
        this.controller = controller;
        setTooltip(name);
    }

    public DemiurgueController getController() {
        return controller;
    }

    public void setIcon(Icon icon) {
        this.putValue(SMALL_ICON, icon);
    }

    public void setTooltip(String text) {
        this.putValue(SHORT_DESCRIPTION, text);
    }
}
