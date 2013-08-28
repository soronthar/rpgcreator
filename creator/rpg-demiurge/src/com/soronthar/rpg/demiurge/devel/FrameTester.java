package com.soronthar.rpg.demiurge.devel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 7/30/12
 * Time: 7:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrameTester extends JFrame {
    private class Content extends JPanel {

        private Content() {
            setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            JList jList = new JList(new String[]{"asda", "asdasd", "asdasd"});
            JPanel panel=new JPanel();
            panel.setBackground(Color.lightGray);
            panel.setSize(256,1024);
            panel.setPreferredSize(new Dimension(256,1024));
            this.add(jList);
            this.add(panel);
        }
    }
    
    
    public FrameTester() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new Content());
        pack();
    }

    public static void main(String[] args) {
        new FrameTester().setVisible(true);
    }
}
