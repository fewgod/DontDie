package com.dontdie.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dontdie.game.DontDieGame;

public class DesktopLauncher {
	public static final int SCREEN_WIDTH = 900;
	public static final int SCREEN_HEIGHT = 700;
	public static void main (String[] warg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SCREEN_WIDTH;
        config.height = SCREEN_HEIGHT;
		new LwjglApplication(new DontDieGame(), config);
	}
}

//need face direction for attack & recenter attack position and spawn point