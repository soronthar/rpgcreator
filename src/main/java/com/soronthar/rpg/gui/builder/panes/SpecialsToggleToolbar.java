package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.actions.ActionsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SpecialsToggleToolbar extends JToolBar {


    private final JToggleButton[] toggles = new JToggleButton[3];


    public SpecialsToggleToolbar(final ActionsManager actions) {
        toggles[0] = new JToggleButton(new MyAction(actions.addObstacleAction(), 0));
        toggles[1] = new JToggleButton(new MyAction(actions.addHeroStartAction(), 1));
        toggles[2] = new JToggleButton(new MyAction(actions.addJumpAction(), 2));

        for (JToggleButton toggle : toggles) {
            this.add(toggle);
        }
    }

    class MyAction extends AbstractAction {
        private int index;
        private Action action;

        MyAction(final Action a, int index) {
            super((String) a.getValue(Action.NAME), (Icon) a.getValue(Action.SMALL_ICON));
            this.index = index;
            this.action = a;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.actionPerformed(e);
            JToggleButton source = (JToggleButton) e.getSource();
            if (source.isSelected()) {
                for (int i = 0; i < toggles.length; i++) {
                    if (i != index) {
                        toggles[i].setSelected(false);
                    }
                }
            }
        }
    }

}
