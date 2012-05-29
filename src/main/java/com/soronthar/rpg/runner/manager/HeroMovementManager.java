package com.soronthar.rpg.runner.manager;

import com.soronthar.rpg.model.tiles.Tile;
import com.soronthar.rpg.runner.GameAction;

import java.awt.event.KeyEvent;

public class HeroMovementManager implements InputController {
    public static final int STEP_SIZE = Tile.TILE_SIZE / 2;

    private GameAction moveLeft;
    private GameAction moveRight;
    private GameAction moveUp;
    private GameAction moveDown;

    private InputManager inputManager;
    private MapManager mapManager;

    public HeroMovementManager(MapManager mapManager) {
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        moveUp = new GameAction("moveUp");
        moveDown = new GameAction("moveDown");

        inputManager = new InputManager();
        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        inputManager.mapToKey(moveDown, KeyEvent.VK_DOWN);
        inputManager.mapToKey(moveUp, KeyEvent.VK_UP);

        this.mapManager = mapManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void checkMovementKeys() {
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

    public void stopHero() {
        mapManager.getHero().stop();
    }
}
