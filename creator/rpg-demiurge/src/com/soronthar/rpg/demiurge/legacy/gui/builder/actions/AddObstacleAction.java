package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddObstacleAction extends ToggleControllerModeAction {
    public AddObstacleAction(DemiurgeController controller) {
        super("Add Obstacle", "icons/run.png", Model.SpecialModes.OBSTACLE, controller);
    }
}
