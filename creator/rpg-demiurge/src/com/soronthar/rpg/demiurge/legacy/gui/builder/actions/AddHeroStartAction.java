package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;

public class AddHeroStartAction extends ToggleControllerModeAction {

    public AddHeroStartAction(RpgCreatorController controller) {
        super("Hero Start", "icons/herostart.png", Model.SpecialModes.HERO_START, controller);
    }
}
