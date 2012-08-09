package com.soronthar.rpg.alchemist.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Rafael Alvarez
 * Date: 8/6/12
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextDialog extends Actor {

    private String text;
    private Label label;
    private ShapeRenderer backgground = new ShapeRenderer();
    private String[] lines;
    private BitmapFont font;
    private int linesToShow;
    
    private int offset=0;
    public static final int R_PAD = 10;
    public static final int L_PAD = 10;
    public static final int TOP_PAD = 10;


    public TextDialog(String text) {

        final InputProcessor inputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode== Input.Keys.Z) {
                    advance();
                    return true;
                } else {
                    return inputProcessor.keyDown(keycode);
                }
            }

            @Override
            public boolean keyUp(int keycode) {
                return inputProcessor.keyUp(keycode);
            }

            @Override
            public boolean keyTyped(char character) {
                return inputProcessor.keyTyped(character);
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                return inputProcessor.touchDown(x, y, pointer, button);
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                return inputProcessor.touchUp(x, y, pointer, button);
            }

            @Override
            public boolean touchDragged(int x, int y, int pointer) {
                return inputProcessor.touchDragged(x, y, pointer);

            }

            @Override
            public boolean touchMoved(int x, int y) {
                return inputProcessor.touchMoved(x, y);
            }

            @Override
            public boolean scrolled(int amount) {
                return inputProcessor.scrolled(amount);
            }
        });

//        FileHandle fileHandle = Gdx.files.internal("fonts/arial-black-10.fnt");
        font = new BitmapFont();
//        font = new BitmapFont(fileHandle,false);

        this.width = Gdx.graphics.getWidth();
        this.height =  Gdx.graphics.getHeight() / 3;
        this.height = (int)(this.height/ this.font.getLineHeight())*this.font.getLineHeight();

        this.text = text;
        ArrayList<String> linesArray = new ArrayList<String>();
        int start = 0;
        int end = -1;
        int length = text.length();
        while (start < length) {
            end = start + font.computeVisibleGlyphs(text, start, length, this.width-R_PAD-L_PAD);
            String substring = text.substring(start, end);
            linesArray.add(substring);
            start = end;
        }
        this.lines = linesArray.toArray(new String[linesArray.size()]);
        this.linesToShow = (int) (this.height / this.font.getLineHeight())-1;


    }

    private void advance() {
        offset+=linesToShow;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if (!isVisible()) return;
        
        Camera camera = getStage().getCamera();
        Vector3 vec = new Vector3(0, 0, 0);
        camera.project(vec);
        batch.end();
        backgground.setProjectionMatrix(batch.getProjectionMatrix());
        backgground.begin(ShapeRenderer.ShapeType.FilledRectangle);
        backgground.setColor(Color.BLUE);
        backgground.filledRect(-vec.x, -vec.y, this.width, this.height+TOP_PAD);

        backgground.end();
        backgground.begin(ShapeRenderer.ShapeType.FilledRectangle);
        backgground.setColor(Color.BLUE);
        backgground.filledRect(-vec.x, -vec.y, this.width, this.height);
        backgground.end();

        backgground.begin(ShapeRenderer.ShapeType.Line);
        backgground.setColor(Color.YELLOW);

        int z= (int) (-vec.y + (this.font.getLineHeight() * (linesToShow+1)));
        for (int i = offset; i < offset+linesToShow && i < lines.length; i++) {
            backgground.line(-vec.x, z, this.width, z);
            z-=this.font.getLineHeight();
        }

        backgground.end();


        batch.begin();
        int y= (int) (-vec.y + (this.font.getLineHeight() * (linesToShow+1)));
        for (int i = offset; i < offset+linesToShow && i < lines.length; i++) {
            font.draw(batch, lines[i], -vec.x+ L_PAD, y);
            y-=this.font.getLineHeight();
        }
    }

    private boolean isVisible() {
        return offset<lines.length;
    }

    @Override
    public Actor hit(float x, float y) {
        return null;
    }
}
