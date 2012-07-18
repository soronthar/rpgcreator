package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddJumpAction extends ToggleControllerModeAction {
    public AddJumpAction(DemiurgueController controller) {
        super("Add Jump", "icons/run.png", Model.SpecialModes.JUMP, controller);
    }
}
