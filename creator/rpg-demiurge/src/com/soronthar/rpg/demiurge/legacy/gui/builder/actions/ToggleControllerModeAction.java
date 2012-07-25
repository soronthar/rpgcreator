package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.WithControllerAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ToggleControllerModeAction extends WithControllerAction {
    private Model.SpecialModes mode;

    public ToggleControllerModeAction(String name, String iconName, Model.SpecialModes mode, DemiurgeController controller) {
        super(name, iconName, controller);
        this.mode = mode;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean selected = ((JToggleButton) e.getSource()).isSelected();
        if (selected) {
            getController().setMode(mode);
        } else getController().setMode(Model.SpecialModes.NONE);
    }
}
