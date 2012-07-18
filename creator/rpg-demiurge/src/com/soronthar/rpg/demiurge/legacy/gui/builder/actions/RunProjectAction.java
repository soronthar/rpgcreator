package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;

public class RunProjectAction extends WithControllerAction {
    public RunProjectAction(DemiurgueController controller) {
        super("Run Project", "icons/run.png", controller);
    }

    public void actionPerformed(ActionEvent e) {
    }
}
