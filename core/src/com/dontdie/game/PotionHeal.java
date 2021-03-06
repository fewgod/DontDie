package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class PotionHeal extends Object{
	private Vector2 currPos;
	private World world;
	private Player player1;
	private Player player2;
	private boolean isItemPickUp;
	
	//Potion_HealOne image size is 40*45
	private static float IMAGE_SIZE_X = 40;
  	private static float IMAGE_SIZE_Y = 42; //+2for easier pick up ;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y+4; //+4for easier pickup
  	private float currCenter_X;
  	private float currCenter_Y;
  	private int HEALING_POTENCY_ONE = 7;
  	private int HEALING_POTENCY_ALL = 4;
  	public boolean isThisItemHealOne;
	
	public PotionHeal(World world,boolean isHealone, int x, int y) {
		super(x,y,IMAGE_SIZE_X,IMAGE_SIZE_Y);
		this.world = world;
		currPos = new Vector2(x,y);
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		
		setCurrPos(currPos); // because item cannot move so you just need to know it's position once
		//If it want to check whether it collide with player or not ,it must init (must have these 2 line)
		setImgRadius(IMAGE_RADIUS_X,IMAGE_RADIUS_Y); //send to Object class
		setWorldandSetPlayer(world);
		//--------------------------------------------------------------------------------------
		
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		isThisItemHealOne = isHealone;
		isItemPickUp = false;
	}

	public void update(float delta)
    {
		//setCurrPos(currPos);// must update to get the lastest current position of this object
		checkIfCollideWithPlayer();
    }
	
	public void heal(int whichPlayerPick)  // do healing function here
	{
		if(isThisItemHealOne == true)  // is this item heal the one who pick or heal both players?
		{
			if(whichPlayerPick ==1)
			{
				player1.healPlayer(HEALING_POTENCY_ONE);
			}
			else
			{
				player2.healPlayer(HEALING_POTENCY_ONE);
			}
		}
		else //if it's heal all item it's doesnt matter who pick it
		{
			player1.healPlayer(HEALING_POTENCY_ALL);
			player2.healPlayer(HEALING_POTENCY_ALL);
		}
		isItemPickUp = true;
		world.heal.play(0.8f);
		world.potion_heal_list.remove(this);
	}

	private void checkIfCollideWithPlayer() //when collide will call medthod heal
	{		
		if(ifHitPlayer1() == true) //ifHitPlayer method is from parent class
		{
			heal(1); //send which player is pick up this item
			world.score += 5;
		}
		if(ifHitPlayer2() == true)
		{
			heal(2);
			world.score += 5;
		}
	}
}