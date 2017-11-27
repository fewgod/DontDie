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

/*
update 24/11/2017
need to cover more radius in attack 
reduced snake number in each spawn wave
ball will only spawn after wave 3 or more in both random and time spawn and wave spawn enemy function
spawn snake by wave is reduced by 1

update 25/11/2017
player hp is now 14 from 10
heal one is from 5 to 7
heal all is from 3 to 54
timestop is now 295 from 275
 
greatly decrease balls spawn rate (ball spawn rate ---)
-ball spawn rate is now 1.1*wave number in 1200 range(was 1000 and *1500)
-ball spawn rate is now 3in 1000(was 5)
-ball damage deal is only 2 (was 3)
randint to timestop item for timespawn item function is increase ( timestop spawn rate -)
spawn time stop by random is now 16000 <=5 from 15000 <= 6( timestop spawn rate --)
spawn potion by radom is now 16000 from 15000( potion spawn rate -)
*/