package com.soronthar.rpg.gui.builder.actions;

import javax.swing.*;

public abstract class IconedAction extends AbstractAction {

    protected IconedAction(String name, String iconName) {
        super(name, new ImageIcon(iconName));
    }
}
