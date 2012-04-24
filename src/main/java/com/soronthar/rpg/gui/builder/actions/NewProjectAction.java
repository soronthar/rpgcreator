package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.actions.base.ShowDialogAction;
import org.soronthar.util.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class NewProjectAction extends ShowDialogAction {

    public NewProjectAction(JFrame frame, Controller controller) {
        super("New Project", "icons/newproject.png", frame, controller);
    }

    public void actionPerformed(ActionEvent e) {
        String projectName = JOptionPane.showInputDialog(
                getFrame(),
                "Project name:", "New Project",
                JOptionPane.PLAIN_MESSAGE);

        if (!StringUtils.isEmpty(projectName)) {
            getController().createNewProject(projectName + ".xml");
        }
    }
}
