package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.runner.GameAction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


/**
 * The InputManager manages input of key and mouse events.
 * Events are mapped to GameActions.
 */
public class InputManager implements KeyListener {

    // key codes are defined in java.awt.KeyEvent.
    // most of the codes (except for some rare ones like
    // "alt graph") are less than 600.
    private static final int NUM_KEY_CODES = 600;

    private GameAction[] keyActions =
            new GameAction[NUM_KEY_CODES];

    /**
     * Maps a GameAction to a specific key. The key codes are
     * defined in java.awt.KeyEvent. If the key already has
     * a GameAction mapped to it, the new GameAction overwrites
     * it.
     */
    public void mapToKey(GameAction gameAction, int keyCode) {
        keyActions[keyCode] = gameAction;
    }


    /**
     * Clears all mapped keys actions to this
     * GameAction.
     */
    public void clearMap(GameAction gameAction) {
        for (int i = 0; i < keyActions.length; i++) {
            if (keyActions[i] == gameAction) {
                keyActions[i] = null;
            }
        }

        gameAction.reset();
    }


    /**
     * Gets a List of names of the keys actions mapped
     * to this GameAction. Each entry in the List is a String.
     */
    public List<String> getMaps(GameAction gameCode) {
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < keyActions.length; i++) {
            if (keyActions[i] == gameCode) {
                list.add(getKeyName(i));
            }
        }

        return list;
    }


    /**
     * Resets all GameActions so they appear like they haven't
     * been pressed.
     */
    public void resetAllGameActions() {
        for (GameAction keyAction : keyActions) {
            if (keyAction != null) {
                keyAction.reset();
            }
        }
    }


    /**
     * Gets the name of a key code.
     */
    public static String getKeyName(int keyCode) {
        return KeyEvent.getKeyText(keyCode);
    }


    private GameAction getKeyAction(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode < keyActions.length) {
            return keyActions[keyCode];
        } else {
            return null;
        }
    }


    public void keyPressed(KeyEvent e) {

        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.press();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }


    public void keyReleased(KeyEvent e) {

        GameAction gameAction = getKeyAction(e);
        if (gameAction != null) {
            gameAction.release();
        }
        // make sure the key isn't processed for anything else
        e.consume();
    }


    public void keyTyped(KeyEvent e) {
        // make sure the key isn't processed for anything else
        e.consume();
    }

    public void addMaps(InputManager inputManager) {
        GameAction[] actions = inputManager.keyActions;
        for (int i = 0; i < actions.length; i++) {
            GameAction gameAction = actions[i];
            if (gameAction!=null) {
                this.keyActions[i]=gameAction;
            }
        }
    }

    public void removeAll(InputManager inputManager) {
        GameAction[] actions = inputManager.keyActions;
        for (int i = 0; i < actions.length; i++) {
            GameAction gameAction = actions[i];
            if (gameAction!=null) {
                this.keyActions[i]=null;
            }
        }
    }
}
