package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;

public class AddObstacleAction extends ToggleControllerModeAction {
    public AddObstacleAction(RpgCreatorController controller) {
        super("Add Obstacle", "icons/run.png", Model.SpecialModes.OBSTACLE, controller);
    }
}
