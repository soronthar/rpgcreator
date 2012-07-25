package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddSpriteAction extends ToggleControllerModeAction {
    public AddSpriteAction(DemiurgeController controller) {
        super("Add Sprite", "icons/run.png", Model.SpecialModes.SPRITE, controller);
    }
}
