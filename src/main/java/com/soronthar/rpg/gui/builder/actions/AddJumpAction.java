package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.Model;

public class AddJumpAction extends ToggleControllerModeAction {
    public AddJumpAction(Controller controller) {
        super("Add Jump", "icons/run.png", Model.SpecialModes.JUMP, controller);
    }
}
