package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;

public class ExitAction extends WithControllerAction {
    public ExitAction(RpgCreatorController controller) {
        super("Exit", "icons/exit.png", controller);
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
