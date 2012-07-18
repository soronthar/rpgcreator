package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;

public class AddObstacleAction extends ToggleControllerModeAction {
    public AddObstacleAction(DemiurgueController controller) {
        super("Add Obstacle", "icons/run.png", Model.SpecialModes.OBSTACLE, controller);
    }
}
