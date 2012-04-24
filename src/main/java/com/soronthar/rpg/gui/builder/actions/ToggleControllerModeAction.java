package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ToggleControllerModeAction extends WithControllerAction {
    private Model.SpecialModes mode;

    public ToggleControllerModeAction(String name, String iconName, Model.SpecialModes mode, Controller controller) {
        super(name, controller);
        this.setIcon(new ImageIcon(iconName));
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
