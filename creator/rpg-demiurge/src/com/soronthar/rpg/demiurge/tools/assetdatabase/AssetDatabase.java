package com.soronthar.rpg.demiurge.tools.assetdatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 10/10/13
 * Time: 12:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssetDatabase extends JFrame {
    private AssetDatabase() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1024, 736));
        this.setTitle("Assets Database");


        final AssetDatabaseGUI contentPane = new AssetDatabaseGUI();
        this.setContentPane(contentPane);

        JMenu menu = new JMenu("Tileset");
        menu.add(new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent ev) {
//                BufferedImage image = contentPane.getPaintPanel().getFlattenImage();
                File file = new File("test.png");
//                try {
//                    ImageIO.write(image, "png", file);
//                } catch (IOException e) {
//                    throw new TechnicalException(e);
//                }
            }
        }));

        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        this.setJMenuBar(bar);
    }
}
