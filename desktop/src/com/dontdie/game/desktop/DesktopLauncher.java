package com.dontdie.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dontdie.game.DontDieGame;

public class DesktopLauncher {
	public static void main (String[] warg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = DontDieGame.SCREEN_WIDTH;
        config.height = DontDieGame.SCREEN_HEIGHT;
		new LwjglApplication(new DontDieGame(), config);
	}
}
