package com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;

import javax.swing.*;

public abstract class WithControllerAction extends IconedAction {
    private DemiurgeController controller;

    public WithControllerAction(String name, String iconName, DemiurgeController controller) {
        super(name, iconName);
        this.controller = controller;
        setTooltip(name);
    }

    public DemiurgeController getController() {
        return controller;
    }

    public void setIcon(Icon icon) {
        this.putValue(SMALL_ICON, icon);
    }

    public void setTooltip(String text) {
        this.putValue(SHORT_DESCRIPTION, text);
    }
}
