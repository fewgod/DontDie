package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Timestopper extends Item {
	private Vector2 currPos;
	private World world;
	private Player player1;
	private Player player2;
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
  	private int TIME_STOP_POTENCY = 290;
	
	
	public Timestopper(World world, int x, int y) { // do this method all time
		super(world,x,y);
		this.world = world;
		currPos = new Vector2(x,y);
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		isItemPickUp = false;
	}

	public void update(float delta) //make snake do things
    {
		checkIfCollideWithPlayer();
    }
	
	public void timestop() 
	{
		world.timeStop = TIME_STOP_POTENCY;
		if(player1.isPlayerDead == false)
		{
			player1.invisibleTime = world.timeStop = TIME_STOP_POTENCY;
		}
		if(player2.isPlayerDead == false)
		{
			player2.invisibleTime = world.timeStop = TIME_STOP_POTENCY;
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
				if(player1.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player1.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within radius.x of this item
				{
					if(player1.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player1.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within radius.y of this item
					{
						timestop();
						world.score += 5;
						world.pickitem.play(0.8f);
					}
				}
			}
        	
			if(player2.isPlayerDead == false)
			{
				if(player2.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player2.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player2 is within radius.x of this item
				{
					if(player2.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player2.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player2 is within radius.y of this item
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
