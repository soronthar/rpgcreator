package com.soronthar.rpg.gui.builder.panes;

import com.soronthar.rpg.gui.builder.actions.ActionsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SpecialsToggleToolbar extends JToolBar {


    private final JToggleButton[] toggles;


    public SpecialsToggleToolbar(final ActionsManager actions) {

        toggles = new JToggleButton[]{
                new MyToggleButton(actions.addObstacleAction()),
                new MyToggleButton(actions.addHeroStartAction()),
                new MyToggleButton(actions.addJumpAction()),

        };
        for (JToggleButton toggle : toggles) {
            this.add(toggle);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (JToggleButton toggle : toggles) {
            toggle.setEnabled(enabled);
        }
    }

    class MyToggleButton extends JToggleButton {
        MyToggleButton(Action a) {
            super(new MyAction(a));
        }
    }

    class MyAction extends AbstractAction {
        private Action action;

        MyAction(final Action a) {
            super((String) a.getValue(Action.NAME), (Icon) a.getValue(Action.SMALL_ICON));
            this.action = a;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.actionPerformed(e);
            JToggleButton source = (JToggleButton) e.getSource();
            if (source.isSelected()) {
                for (JToggleButton toggle : toggles) {
                    if (!source.equals(toggle)) {
                        toggle.setSelected(false);
                    }
                }
            }
        }
    }

}
