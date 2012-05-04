package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.Model;

public class AddSpriteAction extends ToggleControllerModeAction {
    public AddSpriteAction(Controller controller) {
        super("Add Sprite", "icons/run.png", Model.SpecialModes.SPRITE, controller);
    }
}
