package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddSpriteAction extends ToggleControllerModeAction {
    public AddSpriteAction(DemiurgueController controller) {
        super("Add Sprite", "icons/run.png", Model.SpecialModes.SPRITE, controller);
    }
}
