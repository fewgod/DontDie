package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Timestopper extends Object{
	private Vector2 currPos;
	private World world;
	private Player player1;
	private Player player2;
	private boolean isItemPickUp;
	
	//timestopper image size is 34*34
	private static float IMAGE_SIZE_X = 34;
  	private static float IMAGE_SIZE_Y = 34;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y +12; //+12 for easier pickup
  	private float currCenter_X;
  	private float currCenter_Y;
  	private int TIME_STOP_POTENCY = 290;
	
	
	public Timestopper(World world, int x, int y) { // do this method all time
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
		isItemPickUp = false;
	}
	
	public void update(float delta) //make snake do things
    {
		//setCurrPos(currPos); // must update to get the lastest current position of this object
		checkIfCollideWithPlayer();
    }
	
	public void timestop() 
	{
		world.timeStop = TIME_STOP_POTENCY;
		if(player1.isPlayerDead == false)
		{
			player1.invisibleTime = TIME_STOP_POTENCY;
		}
		if(player2.isPlayerDead == false)
		{
			player2.invisibleTime = TIME_STOP_POTENCY;
		}
		isItemPickUp = true;
		world.pickitem.play(0.8f);
		world.timestopper_list.remove(this);
	}
	
	private void checkIfCollideWithPlayer() //when collide will call function timestop
	{		
		if(ifHitPlayer1() == true) //ifHitPlayer method is from parent class
		{
			timestop();
			world.score += 5;
		}
		if(ifHitPlayer2() == true)
		{
			timestop();
			world.score += 5;
		}
	}
}