package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class PotionHeal {
	private Vector2 currPos;
	private World world;
	private Player player1;
	private Player player2;
	private boolean isItemPickUp;
	
	//Potion_HealOne image size is 40*45
	private float IMAGE_SIZE_X = 40;
  	private float IMAGE_SIZE_Y = 45;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
  	private int HEALING_POTENCY_ONE = 7;
  	private int HEALING_POTENCY_ALL = 4;
  	public boolean isThisItemHealOne;
	
	public PotionHeal(World world,boolean isHealone, int x, int y) {
		this.world = world;
		currPos = new Vector2(x,y);
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		isThisItemHealOne = isHealone;
		isItemPickUp = false;
	}
	
	public Vector2 getPosition() { // for other class to get current position of potion
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
        return currPos;    
    }
	
	public float getCurrentXPos() 
    {
    	return currCenter_X = currPos.x + GET_CENTER_X;
    }
    
    public float getCurrentYPos() 
    {
    	return currCenter_Y = currPos.y + GET_CENTER_Y;
    }

	public void update(float delta)
    {
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
		else
		{
			player1.healPlayer(HEALING_POTENCY_ALL);
			player2.healPlayer(HEALING_POTENCY_ALL);
		}
		isItemPickUp = true;
		world.potion_heal_list.remove(this);
	}
	
	private void checkIfCollideWithPlayer() //player1 test only
	{
		if(isItemPickUp == false)
		{
			if(player1.isPlayerDead == false)
			{
				if(player1.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player1.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within 20 radius.x of this item
				{
					if(player1.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player1.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within 20 radius.y of this item
					{
						heal(1);
						world.score += 5;
						world.heal.play(0.8f);
					}
				}
			}
        	
			if(player2.isPlayerDead == false)
			{
				if(player2.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player2.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player2 is within 30 radius.x of this item
				{
					if(player2.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player2.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player2 is within 30 radius.y of this item
					{
						heal(2);
						world.score += 5;
						world.heal.play(0.8f);
					}
				}
        	}
		}
	}
}

