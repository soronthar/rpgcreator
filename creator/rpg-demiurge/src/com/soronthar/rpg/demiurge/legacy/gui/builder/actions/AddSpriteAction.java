package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;

public class AddSpriteAction extends ToggleControllerModeAction {
    public AddSpriteAction(RpgCreatorController controller) {
        super("Add Sprite", "icons/run.png", Model.SpecialModes.SPRITE, controller);
    }
}
