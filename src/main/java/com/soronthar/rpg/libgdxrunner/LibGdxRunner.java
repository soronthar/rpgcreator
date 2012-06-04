package com.soronthar.rpg.libgdxrunner;

import com.badlogic.gdx.Game;

public class LibGdxRunner extends Game {
    public static final boolean DEBUG=Boolean.valueOf(System.getProperty("debug.mode","false"));

    @Override
    public void create() {
        setScreen(new MapScreen());


    }


}
