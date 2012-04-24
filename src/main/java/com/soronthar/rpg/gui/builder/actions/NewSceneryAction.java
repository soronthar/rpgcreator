package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class NewSceneryAction extends WithControllerAction {

    public NewSceneryAction(Controller controller) {
        super("New Scenery", controller);
        this.setIcon(new ImageIcon("icons/addscenery.png"));

    }

    public void actionPerformed(ActionEvent e) {
        getController().addNewScenery("New Scenery " + System.currentTimeMillis());
    }
}