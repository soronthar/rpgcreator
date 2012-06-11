package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Model;
import com.soronthar.rpg.gui.builder.RpgCreatorController;

public class AddHeroStartAction extends ToggleControllerModeAction {

    public AddHeroStartAction(RpgCreatorController controller) {
        super("Hero Start", "icons/herostart.png", Model.SpecialModes.HERO_START, controller);
    }
}
