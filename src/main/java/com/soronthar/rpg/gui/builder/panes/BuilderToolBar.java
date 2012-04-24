package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.actions.ActionsManager;

import javax.swing.*;

public class BuilderToolBar extends JToolBar {
    public BuilderToolBar(final ActionsManager actions) {
        super(JToolBar.HORIZONTAL);
        this.setFloatable(false);
//        this.setBackground(Color.BLUE);

        this.add(actions.newProjectAction());
        this.add(actions.loadProjectAction());
        this.add(actions.saveProjectAction());
        this.addSeparator();
        this.add(actions.newSceneryAction());
        this.addSeparator();
        this.add(actions.runProjectAction());
        this.addSeparator();
        this.add(new SpecialsToogleToolbar(actions));


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
