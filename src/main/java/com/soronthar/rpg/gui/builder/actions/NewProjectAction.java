package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import org.soronthar.util.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class NewProjectAction extends ShowDialogAction {

    public NewProjectAction(JFrame frame, Controller controller) {
        super("New Project", frame, controller);
        this.setIcon(new ImageIcon("icons/newproject.png"));


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
