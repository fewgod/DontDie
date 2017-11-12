package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Snake {
	private int SNAKE_MOVE_SPEED = 3;
	private Vector2 currPos;
	private World world;
	private Player1 player1;
	private Player1 player2;

	public Snake(World world, int x, int y) { // do this method all time
		this.world = world;
		currPos = new Vector2(x,y);
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
	}

	public Vector2 getPosition() { // for other class to get current position of player1
        return currPos;    
    }
	
	public void update(float delta) //snake will move to the right
    {
		move();
    }

	private void move() 
	{
		Vector2 player1Pos = player1.getPosition(); //get position of player 1
        Vector2 player2Pos = player2.getPosition(); //get position of player 2
        if(player1Pos.x > currPos.x) 
        {
        	currPos.x += SNAKE_MOVE_SPEED;
        }
        if(player1Pos.x < currPos.x) 
        {
        	currPos.x -= SNAKE_MOVE_SPEED;
        }
        if(player1Pos.y > currPos.y) 
        {
        	currPos.y += SNAKE_MOVE_SPEED;
        }
        if(player1Pos.y < currPos.y) 
        {
        	currPos.y -= SNAKE_MOVE_SPEED;
        }
	}
}
