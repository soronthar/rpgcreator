package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddObstacleAction extends WithControllerAction {
    private boolean addObstacle = false;

    public AddObstacleAction(Controller controller) {
        super("Add Obstacle", controller);
        this.setIcon(new ImageIcon("icons/run.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addObstacle = !addObstacle;
        getController().setAddObstacle(addObstacle);
    }
}
