package com.soronthar.rpg.gui.compiler;

import com.soronthar.rpg.gui.builder.Model;
import com.soronthar.rpg.gui.builder.actions.ActionsManager;

import javax.swing.*;
import java.awt.*;

public class TilesetCompiler extends JFrame {

    private TilesetCompiler() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1024, 736));
        this.setTitle("Tileset Compiler");

        TilesetCompilerController controller = new TilesetCompilerController(new Model());

        Container contentPane = new TilesetCompilerGUI(controller);
        this.setContentPane(contentPane);

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
        new TilesetCompiler().setVisible(true);
    }
}
