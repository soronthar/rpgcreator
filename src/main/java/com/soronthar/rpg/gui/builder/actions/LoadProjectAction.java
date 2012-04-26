package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.actions.base.ShowDialogAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class LoadProjectAction extends ShowDialogAction {
    public LoadProjectAction(JFrame frame, Controller controller) {
        super("Load Project", "icons/open.png", frame, controller);
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        int returnVal = fc.showOpenDialog(getFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            getController().loadProject(file.getAbsolutePath());
        }
    }
}
