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
