package com.soronthar.rpg.runner.manager;


import com.soronthar.rpg.model.project.Project;
import com.soronthar.rpg.model.scenery.Scenery;
import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.model.tiles.TileSetBag;
import com.soronthar.rpg.model.tiles.TileSetBagPersister;
import com.soronthar.rpg.runner.GameAction;
import org.soronthar.geom.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

public class GameManager {
    private ScreenManager screenManager;
    private GameAction moveLeft;
    private GameAction moveRight;
    private GameAction moveUp;
    private GameAction moveDown;
    private boolean isRunning = true;

    private JFrame frame;
    private static final int DELAY = 100;
    private static final int STEP_SIZE = Tile.TILE_SIZE / 2;
    private final MapManager mapManager;


    public GameManager(Project project) {
        TileSetBag tileSets = new TileSetBagPersister().loadTilesets();
        Scenery scenery = project.getSceneries().iterator().next();
        BufferedImage[] layers = TilesetRenderer.createLayers(tileSets, scenery);
        Dimension viewSize = new Dimension(scenery.getWidth(), scenery.getHeight());

        mapManager = new MapManager(scenery);
        screenManager = new ScreenManager(viewSize, layers);


        createAndInitializeFrame();
        initializeInputManager();
    }

    private void initializeInputManager() {
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        moveUp = new GameAction("moveUp");
        moveDown = new GameAction("moveDown");
        InputManager inputManager = new InputManager(frame);
        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        inputManager.mapToKey(moveDown, KeyEvent.VK_DOWN);
        inputManager.mapToKey(moveUp, KeyEvent.VK_UP);
    }

    private void createAndInitializeFrame() {
        frame = new JFrame();
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(screenManager);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isRunning = false;
            }
        });
    }


    public void executeMainLoop() {
        initializeDoubleBuffering();

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
        checkMovementKeys();
    }

    private void checkMovementKeys() {
        int speedX = 0;
        int speedY = 0;
        if (moveLeft.isPressed()) {
            speedX -= STEP_SIZE;
        }
        if (moveRight.isPressed()) {
            speedX += STEP_SIZE;
        }
        if (moveUp.isPressed()) {
            speedY -= STEP_SIZE;
        }
        if (moveDown.isPressed()) {
            speedY += STEP_SIZE;
        }

        mapManager.getHero().setSpeed(speedX, speedY);
    }

    private void updateMap(long elapsedTime) {
        mapManager.update(elapsedTime);
    }

}
