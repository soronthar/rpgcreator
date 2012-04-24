package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveProjectAction extends WithControllerAction {
    public SaveProjectAction(Controller controller) {
        super("Save Project", controller);
        this.setIcon(new ImageIcon("icons/save.png"));

    }

    public void actionPerformed(ActionEvent e) {
        getController().saveProject();
    }
}
