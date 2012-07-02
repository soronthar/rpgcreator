package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;

public class SaveProjectAction extends WithControllerAction {
    public SaveProjectAction(RpgCreatorController controller) {
        super("Save Project", "icons/save.png", controller);

    }

    public void actionPerformed(ActionEvent e) {
        getController().saveProject();
    }
}
