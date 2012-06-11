package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Model;
import com.soronthar.rpg.gui.builder.RpgCreatorController;

public class AddSpriteAction extends ToggleControllerModeAction {
    public AddSpriteAction(RpgCreatorController controller) {
        super("Add Sprite", "icons/run.png", Model.SpecialModes.SPRITE, controller);
    }
}
