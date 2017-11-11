package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Snake {
	private int SNAKE_MOVE_SPEED = 5;
	private Vector2 currPos;

	public Snake(int x, int y) {
		currPos = new Vector2(x,y);
	}

	public Vector2 getPosition() { // for other class to get current position of player1
        return currPos;    
    }
	
	public void update(float delta) //snake will move to the right
    {
    	currPos.x += SNAKE_MOVE_SPEED;
    	if(currPos.x >880)
    	{
    		currPos.x = 10;
    	}
    }
}
