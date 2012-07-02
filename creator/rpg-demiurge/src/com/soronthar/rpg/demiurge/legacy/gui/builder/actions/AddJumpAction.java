package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;

public class AddJumpAction extends ToggleControllerModeAction {
    public AddJumpAction(RpgCreatorController controller) {
        super("Add Jump", "icons/run.png", Model.SpecialModes.JUMP, controller);
    }
}
