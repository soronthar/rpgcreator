package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.actions.ActionsManager;

import javax.swing.*;

public class BuilderToolBar extends JToolBar {

    private final SpecialsToggleToolbar toggleToolbar;

    public BuilderToolBar(final ActionsManager actions) {
        super(JToolBar.HORIZONTAL);
        this.setFloatable(false);

        this.add(actions.newProjectAction());
        this.add(actions.loadProjectAction());
        this.add(actions.saveProjectAction());
        this.addSeparator();
        this.add(actions.newSceneryAction());
        this.addSeparator();
        this.add(actions.runProjectAction());
        this.addSeparator();
        toggleToolbar = new SpecialsToggleToolbar(actions);
        this.add(toggleToolbar);


    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.toggleToolbar.setEnabled(enabled);
    }

    @Override
    protected JButton createActionComponent(Action action) {
        JButton button = new JButton(action);
        button.setRequestFocusEnabled(false);
        button.setFocusable(false);
        button.setHideActionText(true);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        return button;
    }
}
