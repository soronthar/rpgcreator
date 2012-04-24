package com.soronthar.rpg.gui.builder.actions;

import com.soronthar.rpg.gui.builder.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 4/16/12
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddHeroStartAction extends WithControllerAction {
    private boolean addObstacle = false;

    public AddHeroStartAction(Controller controller) {
        super("Hero Start", controller);
        this.setIcon(new ImageIcon("icons/herostart.png"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addObstacle = !addObstacle;
        getController().setHeroStartMode(addObstacle);

    }
}
