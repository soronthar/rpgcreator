package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;

public class ExitAction extends WithControllerAction {
    public ExitAction(Controller controller) {
        super("Exit", "icons/exit.png", controller);
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
