package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Model;
import com.soronthar.rpg.gui.builder.RpgCreatorController;

public class AddObstacleAction extends ToggleControllerModeAction {
    public AddObstacleAction(RpgCreatorController controller) {
        super("Add Obstacle", "icons/run.png", Model.SpecialModes.OBSTACLE, controller);
    }
}
