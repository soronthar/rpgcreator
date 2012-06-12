package com.soronthar.rpg.alchemist;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class RpgAlchemistDesktop {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "rpg-alchemist";
		cfg.useGL20 = false;
		cfg.width = 200;
		cfg.height = 100;
		
		new LwjglApplication(new RpgAlchemist(), cfg);
	}
}
