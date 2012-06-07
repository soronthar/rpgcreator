package com.soronthar.rpg.libgdxrunner.actors.movement;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.soronthar.rpg.libgdxrunner.actors.HeroActor;

public class HeroMovementController extends MobMovementController implements InputProcessor {

    public HeroMovementController(HeroActor heroActor) {
        super(heroActor);
        dx=0;
        dy=0;
    }

    @Override
    protected void handleCollision(float newX, float newY) {
        dx=0;
        dy=0;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                dx = -STEP;
                break;
            case Input.Keys.RIGHT:
                dx = +STEP;
                break;
            case Input.Keys.UP:
                dy = +STEP;
                break;
            case Input.Keys.DOWN:
                dy = -STEP;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
                dx = 0;
                break;
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                dy = 0;
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean touchMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
