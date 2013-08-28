package com.soronthar.rpg.demiurge;


import com.soronthar.rpg.demiurge.legacy.gui.builder.DemiurgeController;
import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.ActionsManager;

import javax.swing.*;
import java.awt.*;

public class RpgDemiurge extends JFrame {
    private RpgDemiurge() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1024, 600));
        this.setTitle("Rpg Demiurge");

        DemiurgeController controller = new DemiurgeController(new Model());
        ActionsManager actionsManager = new ActionsManager(this, controller);

        Container contentPane = new RpgDemiurgeContent(controller, actionsManager, this);
        contentPane.setEnabled(false);
        this.setContentPane(contentPane);
        this.setJMenuBar(createMenu(actionsManager));
        controller.setActionManager(actionsManager);
    }

    private JMenuBar createMenu(ActionsManager controller) {
        return new MenuBar(controller);
    }

    public class MenuBar extends JMenuBar {
        public MenuBar(final ActionsManager actions) {
            JMenu project = new JMenu("Project");

            project.add(new JMenuItem(actions.newProjectAction()));
            project.add(new JMenuItem(actions.loadProjectAction()));
            project.add(new JMenuItem(actions.saveProjectAction()));
            project.add(new JMenuItem(actions.newSceneryAction()));
            project.addSeparator();
            project.add(new JMenuItem(actions.exitAction()));

            JMenu run = new JMenu("Run");
            run.add(new JMenuItem(actions.runProjectAction()));

            JMenu database = new JMenu("Database");
            database.add(new JMenuItem(actions.manageTilesetsAction()));
            
            this.add(project);
            this.add(run);
            this.add(database);

        }
    }


    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new RpgDemiurge().setVisible(true);
    }
}
