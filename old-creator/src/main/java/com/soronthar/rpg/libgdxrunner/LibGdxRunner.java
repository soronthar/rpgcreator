package com.soronthar.rpg.libgdxrunner;

import com.badlogic.gdx.Game;
import com.soronthar.rpg.alchemist.map.MapScreen;

public class LibGdxRunner extends Game {

    @Override
    public void create() {
        setScreen(new MapScreen());
    }



}
