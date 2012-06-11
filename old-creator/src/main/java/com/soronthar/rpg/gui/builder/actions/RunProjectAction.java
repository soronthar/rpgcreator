package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.RpgCreatorController;
import com.soronthar.rpg.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;

public class RunProjectAction extends WithControllerAction {
    public RunProjectAction(RpgCreatorController controller) {
        super("Run Project", "icons/run.png", controller);
    }

    public void actionPerformed(ActionEvent e) {
    }
}
