package com.soronthar.rpg.alchemist;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "rpg-alchemist";
		cfg.useGL20 = false;
		cfg.width = 1024;
		cfg.height = 600;
		
		new LwjglApplication(new RpgAlchemist(), cfg);
	}
}
