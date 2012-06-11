package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.RpgCreatorController;
import com.soronthar.rpg.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;


public class NewSceneryAction extends WithControllerAction {

    public NewSceneryAction(RpgCreatorController controller) {
        super("New Scenery", "icons/addscenery.png", controller);
    }

    public void actionPerformed(ActionEvent e) {
        getController().addNewScenery("New Scenery " + System.currentTimeMillis());
    }
}