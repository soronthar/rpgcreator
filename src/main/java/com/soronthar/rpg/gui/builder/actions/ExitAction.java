package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends WithControllerAction {
    public ExitAction(Controller controller) {
        super("Exit", controller);
        this.setIcon(new ImageIcon("icons/exit.png"));
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
