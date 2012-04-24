package com.soronthar.rpg.gui.builder;

import com.soronthar.rpg.ImageLoader;
import com.soronthar.rpg.gui.builder.actions.ActionsManager;
import com.soronthar.rpg.model.MainEngine;

import javax.swing.*;
import java.awt.*;

public class RpgCreator extends JFrame {

    private RpgCreator() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1024, 768));
        this.setTitle("RPG Creator");

        //TODO: this order of initialization is kind of brittle
        MainEngine mainEngine = MainEngine.getInstance();
        mainEngine.setImageLoader(new ImageLoader());

        Controller controller = new Controller(new Model());
        ActionsManager actionsManager = new ActionsManager(this, controller);

        Container contentPane = new BuilderGUI(controller, actionsManager, this);

        this.setContentPane(contentPane);
        this.setJMenuBar(createMenu(actionsManager));
        //(RAF) this is the last thing that should be done.
        controller.loadTilesets();


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


            this.add(project);
            this.add(run);
        }
    }


    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new RpgCreator().setVisible(true);
    }
}
