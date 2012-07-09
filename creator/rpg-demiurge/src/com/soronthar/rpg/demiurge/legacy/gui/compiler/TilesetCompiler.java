package com.soronthar.rpg.demiurge.legacy.gui.compiler;

import com.soronthar.rpg.demiurge.legacy.gui.builder.Model;
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

        JMenu menu = new JMenu("Tileset");
        menu.add(new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                BufferedImage image = contentPane.getPaintPanel().getFlattenImage();
                File file = new File("test.png");
                System.out.println("TilesetCompiler.actionPerformed:37 - file.getAbsolutePath() = " + file.getAbsolutePath());
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





    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new TilesetCompiler().setVisible(true);
    }
}
