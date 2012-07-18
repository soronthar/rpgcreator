package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddHeroStartAction extends ToggleControllerModeAction {

    public AddHeroStartAction(DemiurgueController controller) {
        super("Hero Start", "icons/herostart.png", Model.SpecialModes.HERO_START, controller);
    }
}
