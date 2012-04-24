package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.Model;

public class AddObstacleAction extends ToggleControllerModeAction {
    public AddObstacleAction(Controller controller) {
        super("Add Obstacle", "icons/run.png", Model.SpecialModes.OBSTACLE, controller);
    }
}
