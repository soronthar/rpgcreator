package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;
import com.soronthar.rpg.gui.builder.actions.base.WithControllerAction;
import com.soronthar.rpg.runner.manager.GameManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RunProjectAction extends WithControllerAction {
    public RunProjectAction(Controller controller) {
        super("Run Project", "icons/run.png", controller);
    }

    public void actionPerformed(ActionEvent e) {
        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    GameManager manager = new GameManager(getController().getProject());
                    manager.executeMainLoop();
                } catch (Exception e1) {
                    e1.printStackTrace();  //TODO: proper error management
                }
                return null;
            }
        };

        worker.execute();
    }
}
