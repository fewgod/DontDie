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
-now cover more radius in attack 
-reduced snake number in each spawn wave
-ball will only spawn after wave 3 or more in both random and time spawn and wave spawn enemy function
-spawn snake by wave is reduced by 1

update 25/11/2017
-player hp is now 14, was 10
-heal one is now 7, was 5
-heal all is now 4, was 3
-timestop is now 295 from 275
greatly decrease balls spawn rate (ball spawn rate ---)
-ball spawn rate is now 1.1*wave number in 1200 range(was 1000 and *1500)
-ball spawn rate is now 3in 1000(was 5)
-ball damage deal is only 2 (was 3)
randint to timestop item for timespawn item function is increase ( timestop spawn rate -)
spawn time stop by random is now 16000 <=5 from 15000 <= 6( timestop spawn rate --)
spawn potion by radom is now 16000 from 15000( potion spawn rate -)

update 1/12/2017
-optimize some more code

update 5/12/2017
-timespawnenemy ball is decrease from 1200 to 1400
-randomspawnenemy ball is decrease from 1000 to 1150
-increase ball push power from 9 to 10
-increase player die sfx 0.1 to 1.0
-decrease slow fireball cast sfx 0.67 to 0.63
-decrease enemy dead sfx 0.68 to 0.63

update 13/12/2017
-add world and world renderer dispose fucntion
-rename Player1 class to Player
-increase Timestopper image y radius by 12
-increase PotionHeal image y radius by 4
-decrease ball spawn rate ranspawn rand.nextInt(1550) <= 3 was 1150 time spawn 1750, was 1400
-potionheal(rand.nextInt(17500) <= 4) was 16000
-maxtimePotion = timePotion + 14 + rand.nextInt(8), was 13,8
-maxtimeStopItem = timeStop + 12 + rand.nextInt(10), was 11,12
-timestop(rand.nextInt(17500) <= 5), was 16000

update 16/12/2017
-add Object class
-Item,Player,Enemy inherit from Object class which include getPosition getPosition_X&Y
-Item,Player,Enemy now when update will call setCurrPos from object class
 to set Current Position to use in other function (such as getPositionX&Y and getPosition Vector)
-maxtimeStopItem = timePotion + 13 + rand.nextInt(12), was 12,10
-reduce ball random spawn to 1600 <=3 was 1550
-optimize worldrenderer code (delete unused send parameters)

update 17/12/2017
-Item&Enemy which need to check if it collide with player now can use those If hit player from parent class (Object class)
-Revert Player class to none object sub class (13/12/2017)
-Change item spawn random position (will spawn at least y>=100)
-Timestop and Provoke time now need to call world method to set its value
*/