package com.soronthar.rpg.runner.manager;


import com.soronthar.rpg.model.objects.sprites.StandingNpc;
import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.runner.GameAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;

public class GameEngine {
    private ScreenManager screenManager;


    private boolean isRunning = true;
    private JFrame frame;
    private static final int DELAY = 100;
    private final MapManager mapManager;
    private Project project;

    private HeroMovementManager heroMovementManager;
    private GameAction action;
    private InputManager inputManager;


    public GameEngine(Project project) {
        this.project = project;
        screenManager = new ScreenManager();
        mapManager = new MapManager(project.getSceneries());
        mapManager.addSceneryListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                screenManager.setScenery((Scenery) evt.getNewValue());
                if (frame != null) frame.pack();
            }
        });
        mapManager.init();

        heroMovementManager = new HeroMovementManager(mapManager);
        createAndInitializeFrame();
        initializeInputManager();

    }

    private void initializeInputManager() {
        action = new GameAction("action", GameAction.DETECT_INITAL_PRESS_ONLY);

        inputManager = new InputManager();
        inputManager.addMaps(heroMovementManager.getInputManager());
        inputManager.mapToKey(action, KeyEvent.VK_SPACE);


        frame.addKeyListener(inputManager);
    }


    private void createAndInitializeFrame() {
        frame = new JFrame();
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(screenManager);
        frame.pack();
        frame.setVisible(true);
        frame.setFocusTraversalKeysEnabled(false);// allow input of keys normally used for focus traversal
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isRunning = false;
            }
        });
        initializeDoubleBuffering();
    }


    public void executeMainLoop() {

        try {
            mailLoop();
        } catch (Exception e) {
            e.printStackTrace();
            frame.setVisible(false);
            frame.dispose();
            System.exit(-1);
        }

    }

    private void mailLoop() {
        long currTime = System.currentTimeMillis();
        while (isRunning) {
            long elapsedTime = System.currentTimeMillis() - currTime;

            updateGame(elapsedTime);
            currTime += elapsedTime;
            sync(currTime);

        }
    }

    /**
     * This needs to be done in the main loop, after sreenManager is visible, or an exception will be thrown
     */
    private void initializeDoubleBuffering() {
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    screenManager.createBufferStrategy(2);
                }
            });
        } catch (InterruptedException ex) {
        } catch (InvocationTargetException ex) {
        }
    }

    /**
     * The game will run in "ticks", their approximate length controlled by the field DELAY
     * This methods guarantees that at least DELAY ms pass before cycling again. This "pasive waits"
     * saves CPU time.
     *
     * @param currTime
     */
    private void sync(long currTime) {
        try {
            Thread.sleep(Math.max(0, (currTime + DELAY) - System.currentTimeMillis()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates the game state
     *
     * @param elapsedTime
     */
    private void updateGame(long elapsedTime) {

        checkInput();
        updateMap(elapsedTime);
        screenManager.paint(mapManager.getHero(), mapManager.getSprites());
    }


    private void checkInput() {
        checkAction();
        heroMovementManager.checkMovementKeys();
    }

    private SpriteActionQueue actionQueue = new SpriteActionQueue();
    private boolean flag = false;

    private void checkAction() {
        if (action.isPressed()) {

            if (mapManager.isHeroFacingActiveNPC()) {
                inputManager.resetAllGameActions();
                heroMovementManager.stopHero();
                inputManager.removeAll(heroMovementManager.getInputManager());

                StandingNpc npc = mapManager.getNPCToInteract();
                if (!flag) {
                    actionQueue.setActions(npc.getActions());
                    flag = true;
                }

                actionQueue.executeAction(this);

                if (!actionQueue.isActive()) {
                    flag = false;
                    inputManager.addMaps(heroMovementManager.getInputManager());
                }
            }
        }
    }


    private void updateMap(long elapsedTime) {
        mapManager.update(elapsedTime);
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

}
