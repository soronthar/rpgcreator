package com.soronthar.rpg.demiurge.legacy.gui.builder.actions;

import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgueController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.base.ShowDialogAction;
import org.soronthar.util.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class NewProjectAction extends ShowDialogAction {

    public NewProjectAction(JFrame frame, DemiurgueController controller) {
        super("New Project", "icons/newproject.png", frame, controller);
    }

    public void actionPerformed(ActionEvent e) {
        String projectName = JOptionPane.showInputDialog(
                getFrame(),
                "Project name:", "New Project",
                JOptionPane.PLAIN_MESSAGE);

        if (!StringUtils.isEmpty(projectName)) {
            getController().createNewProject(projectName);
        }
    }
}
