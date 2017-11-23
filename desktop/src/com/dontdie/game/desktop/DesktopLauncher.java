package com.dontdie.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
//need to cover more radius in attack
//reduced snake number in each spawn wave
//ball will only spawn after wave 3 or more in both random and time spawn and wave spawn enemy function
//spawn snake by wave is reduced by 1

//player hp is 15
//heal one is from 5 to 8
//heal all is from 3 to 5