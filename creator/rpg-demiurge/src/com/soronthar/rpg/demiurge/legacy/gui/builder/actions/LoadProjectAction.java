package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.RpgCreatorController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.ShowDialogAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadProjectAction extends ShowDialogAction {
    public LoadProjectAction(JFrame frame, RpgCreatorController controller) {
        super("Load Project", "icons/open.png", frame, controller);
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir")+"/resources/projects");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        fc.setMultiSelectionEnabled(false);

        int returnVal = fc.showOpenDialog(getFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            getController().loadProject(file.getName());
        }
    }
}
