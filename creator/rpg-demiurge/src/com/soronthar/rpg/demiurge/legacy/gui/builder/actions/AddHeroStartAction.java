package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddHeroStartAction extends ToggleControllerModeAction {

    public AddHeroStartAction(DemiurgeController controller) {
        super("Hero Start", "icons/herostart.png", Model.SpecialModes.HERO_START, controller);
    }
}
