package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Timestopper {
	private Vector2 currPos;
	private World world;
	private Player1 player1;
	private Player1 player2;
	private boolean isItemPickUp;
	
	//timestopper image size is 34*34
	private float IMAGE_SIZE_X = 34;
  	private float IMAGE_SIZE_Y = 34;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
  	private int TIME_STOP_POTENCY = 285;
	
	
	public Timestopper(World world, int x, int y) { // do this method all time
		this.world = world;
		currPos = new Vector2(x,y);
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		isItemPickUp = false;
	}
	
	public Vector2 getPosition() { // for other class to get current position of snake
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

	public void update(float delta) //make snake do things
    {
		checkIfCollideWithPlayer();
    }
	
	public void timestop() 
	{
		world.timestop = TIME_STOP_POTENCY;
		if(player1.isPlayerDead == false)
		{
			player1.invisibleTime = world.timestop = TIME_STOP_POTENCY;
		}
		if(player2.isPlayerDead == false)
		{
			player2.invisibleTime = world.timestop = TIME_STOP_POTENCY;
		}
		isItemPickUp = true;
		world.timestopper_list.remove(this);
	}
	
	private void checkIfCollideWithPlayer()
	{
		if(isItemPickUp == false)
		{
			if(player1.isPlayerDead == false)
			{
				Vector2 player1Pos = player1.getPosition(); //get position of player 1
				if(player1.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player1.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within 20 radius.x of this item
				{
					if(player1.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player1.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within 20 radius.y of this item
					{
						timestop();
						world.score += 5;
						world.pickitem.play(0.8f);
					}
				}
			}
        	
			if(player2.isPlayerDead == false)
			{
				Vector2 player2Pos = player2.getPosition(); //get position of player 2
				if(player2.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player2.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player2 is within 30 radius.x of this item
				{
					if(player2.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player2.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player2 is within 30 radius.y of this item
					{
						timestop();
						world.score += 5;
						world.pickitem.play(0.8f);
					}
				}
        	}
		}
	}
}
