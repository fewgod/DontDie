package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Timestopper {
	private Vector2 currPos;
	private World world;
	private Player1 player1;
	private Player1 player2;
	private boolean isItemPickUp;
	
	public Timestopper(World world, int x, int y) { // do this method all time
		this.world = world;
		currPos = new Vector2(x,y);
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		isItemPickUp = false;
	}
	
	public Vector2 getPosition() { // for other class to get current position of snake
        return currPos;    
    }
	

	public void update(float delta) //make snake do things
    {
		checkIfCollideWithPlayer();
    }
	
	private void checkIfCollideWithPlayer() //player1 test only
	{
		if(isItemPickUp == false)
		{
			Vector2 player1Pos = player1.getPosition(); //get position of player 1
        	Vector2 player2Pos = player2.getPosition(); //get position of player 2
        	if(player1Pos.x > currPos.x - 30 && player1Pos.x < currPos.x + 30)  //if player1 is within 30 radius.x of this item
        	{
        		if(player1Pos.y > currPos.y - 30 && player1Pos.y < currPos.y + 30) //if player1 is within 30 radius.y of this item
        		{
        			world.timestop = 250;
        			isItemPickUp = true;
        			world.timestopper_list.remove(0); // work only if 1 item of this class exist
        		}
        	}
        	if(player2Pos.x > currPos.x - 30 && player2Pos.x < currPos.x + 30)  //if player2 is within 30 radius.x of this item
        	{
        		if(player2Pos.y > currPos.y - 30 && player2Pos.y < currPos.y + 30) //if player2 is within 30 radius.y of this item
        		{
        			world.timestop = 250;
        			isItemPickUp = true;
        			world.timestopper_list.remove(0); // work only if 1 item of this class exist
        		}
        	}
		}
	}
}
