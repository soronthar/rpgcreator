package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddJumpAction extends WithControllerAction {
    boolean state = false;

    public AddJumpAction(Controller controller) {
        super("Add Jump", controller);
        this.setIcon(new ImageIcon("icons/run.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        state = !state;
        getController().setAddJump(state);
    }
}
