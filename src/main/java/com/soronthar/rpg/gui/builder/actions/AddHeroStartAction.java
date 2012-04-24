package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.Model;

public class AddHeroStartAction extends ToggleControllerModeAction {

    public AddHeroStartAction(Controller controller) {
        super("Hero Start", "icons/herostart.png", Model.SpecialModes.HERO_START, controller);
    }
}
