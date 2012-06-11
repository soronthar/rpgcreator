package com.soronthar.rpg.gui.builder.actions.base;

import javax.swing.*;

public abstract class IconedAction extends AbstractAction {

    protected IconedAction(String name, String iconName) {
        super(name, new ImageIcon(iconName));
    }
}
