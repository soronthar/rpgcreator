package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;


public class NewSceneryAction extends WithControllerAction {

    public NewSceneryAction(RpgCreatorController controller) {
        super("New Scenery", "icons/addscenery.png", controller);
    }

    public void actionPerformed(ActionEvent e) {
        getController().addNewScenery("New Scenery " + System.currentTimeMillis());
    }
}
