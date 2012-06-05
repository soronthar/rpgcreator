package com.soronthar.rpg.libgdxrunner.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.soronthar.rpg.libgdxrunner.LibGdxRunner;
import com.soronthar.rpg.model.objects.actors.Hero;
import com.soronthar.rpg.model.objects.actors.frames.FrameStrategy;

public class HeroActor extends Actor implements InputProcessor {
    private ShapeRenderer heroRenderer = new ShapeRenderer();
    public static final double DELAY = 0.1;
    public static final int STEP = 16;

    int facing = 1;
    int steps = 0;

    int dx;
    int dy;

    float tick;
    private BitmapFont font;
    private final TextureRegion[][] split;
    private FrameStrategy frameStrategy;

    @Override
    public void act(float delta) {
        if (tick > DELAY) {

            if (dx>0) {
                facing=3;
            } else if (dx<0) {
                facing=2;
            } else if (dy<0) {
                facing=1;
            } else if (dy>0) {
                facing=0;
            }

            float oldX = this.x;
            float oldY = this.y;
            this.x += dx;
            this.y += dy;

            Actor hit = stage.hit(this.x, this.y);
            if (hit != null) {
                this.x = oldX;
                this.y = oldY;
                this.dx=0;
                this.dy=0;
            }
            steps++;


            tick = 0;
        } else {
            tick += delta;
        }
    }

    public HeroActor(Hero hero) {
        this.x = hero.getLocation().x;
        this.y = hero.getLocation().y;
        font = new BitmapFont();
        Texture spriteSheet = new Texture(Gdx.files.internal("resources/sprites/herop2.png"));
        split = TextureRegion.split(spriteSheet, 32, 32);
        this.frameStrategy=hero.getFrameStrategy();

    }

    @Override
    public Actor hit(float x, float y) {
        return null; //TODO: this is not exactly true...
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        heroRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        heroRenderer.begin(ShapeRenderer.ShapeType.Rectangle);
        heroRenderer.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        heroRenderer.rect(this.x, this.y, 32, 32);
        heroRenderer.end();
        if (LibGdxRunner.DEBUG) {
            font.setColor(Color.BLUE);
            font.draw(batch, Integer.toString((int) this.x), this.x + 3, this.y + 30);
            font.draw(batch, Integer.toString((int) this.y), this.x + 3, this.y + 15);
        } else {
            int index=0;
            if (dx!=0 || dy!=0) {
                index=1+ steps%2;
            }
            batch.draw(split[index][facing], x, y);
        }
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean touchMoved(int x, int y) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
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
        return super.keyTyped(character);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
