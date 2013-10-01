package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.WithControllerAction;

import java.awt.event.ActionEvent;

public class ExportProjectAction extends WithControllerAction {
    public ExportProjectAction(DemiurgeController controller) {
        super("Export Project", "icons/exportproject.png", controller);

    }

    public void actionPerformed(ActionEvent e) {
        getController().exportProject();
    }
}
