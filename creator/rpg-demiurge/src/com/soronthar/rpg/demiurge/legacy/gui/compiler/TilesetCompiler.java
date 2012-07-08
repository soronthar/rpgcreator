package com.soronthar.rpg.demiurge.legacy.gui.compiler;

import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
import com.soronthar.rpg.demiurge.legacy.gui.builder.actions.ActionsManager;
import org.soronthar.error.TechnicalException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TilesetCompiler extends JFrame {

    private TilesetCompiler() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1024, 736));
        this.setTitle("Tileset Compiler");

        final TilesetCompilerController controller = new TilesetCompilerController(new Model());

        final TilesetCompilerGUI contentPane = new TilesetCompilerGUI(controller);
        this.setContentPane(contentPane);


        //(RAF) this is the last thing that should be done.
        controller.loadTilesets();

        JMenu menu = new JMenu("Tileset");
        menu.add(new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                BufferedImage image = contentPane.getPaintPanel().getCanvas().getLayerImage(0);
                File file = new File("test.png");
                try {
                    ImageIO.write(image, "png", file);
                } catch (IOException e) {
                    throw new TechnicalException(e);
                }
            }
        }));

        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        this.setJMenuBar(bar);
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
