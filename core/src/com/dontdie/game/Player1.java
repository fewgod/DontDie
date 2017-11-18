package com.dontdie.game;

import com.badlogic.gdx.math.Vector2; //how to create position at the center of image (right now it's bottom left)

public class Player1 {
	public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_DOWN = 3;
    public static final int DIRECTION_LEFT = 4;
    public static final int DIRECTION_STILL = 0;
    public int faceDir = DIRECTION_UP;
    private int PLAYER_MOVE_SPEED = 4;
    public Vector2 currPos;
 
  //snake image size is 22*41
  	private float IMAGE_SIZE_X = 22;
  	private float IMAGE_SIZE_Y = 41;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X;
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
  	private float CURR_CENTER_X;
  	private float CURR_CENTER_Y;
    
    private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0},
        {0,1},
        {1,0},
        {0,-1},
        {-1,0}
    };
    
    public Player1(World world , int x, int y) { //when first init give spawn position to player 1
        currPos = new Vector2(x,y);
        CURR_CENTER_X = currPos.x + GET_CENTER_X;
		CURR_CENTER_Y = currPos.y + GET_CENTER_Y;
    }    
 
    public Vector2 getPosition() { // for other class to get current position of player1
    	CURR_CENTER_X = currPos.x + GET_CENTER_X;
		CURR_CENTER_Y = currPos.y + GET_CENTER_Y;
        return currPos;    
    }
    
    public float getCurrentXPos() 
    {
    	return CURR_CENTER_X = currPos.x + GET_CENTER_X;
    }
    
    public float getCurrentYPos() 
    {
    	return CURR_CENTER_Y = currPos.y + GET_CENTER_Y;
    }
    
    public void move(int dir) { 
    	if(CURR_CENTER_X < 10) // prevent player walk off screen
    	{
    		currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[2][0];
    	}
    	if(CURR_CENTER_X > 880)
    	{
    		currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[4][0];
    	}
    	if(CURR_CENTER_Y < 5)
    	{
    		currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[1][1];
    	}
    	if(CURR_CENTER_Y >665)
    	{
    		currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[3][1];
    	}
    	currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][0];
    	currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][1];
    	CURR_CENTER_X = currPos.x + GET_CENTER_X;
		CURR_CENTER_Y = currPos.y + GET_CENTER_Y;
        // first [dir] is chose which {,} to use, second [] chose first para or second para in {,}
    }
}