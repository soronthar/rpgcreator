package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddJumpAction extends ToggleControllerModeAction {
    public AddJumpAction(DemiurgeController controller) {
        super("Add Jump", "icons/run.png", Model.SpecialModes.JUMP, controller);
    }
}
