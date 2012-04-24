package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.actions.ActionsManager;

import javax.swing.*;

public class SpecialsToogleToolbar extends JToolBar {
    public SpecialsToogleToolbar(final ActionsManager actions) {
        this.add(new JToggleButton(actions.addObstacleAction()));
        this.add(new JToggleButton(actions.addHeroStartAction()));
        this.add(new JToggleButton(actions.addJumpAction()));
    }
}
