package com.dontdie.game;

import com.badlogic.gdx.math.Vector2; //how to create position at the center of image (right now it's bottom left)

public class Player1 {
	public static final int SCREEN_WIDTH = 900;
	public static final int SCREEN_HEIGHT = 700;
	
	public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_DOWN = 3;
    public static final int DIRECTION_LEFT = 4;
    public static final int DIRECTION_STILL = 0;
    public int faceDir;
    private int PLAYER_MOVE_SPEED = 4;
    public Vector2 currPos;
    private World world;
    private int hitPoints; //should be private but for test will let it be public
 
  //snake image size is 22*41
  	private float IMAGE_SIZE_X = 22;
  	private float IMAGE_SIZE_Y = 41;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
	public boolean isPlayerDead;
    
    private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0},
        {0,1},
        {1,0},
        {0,-1},
        {-1,0}
    };
    
    public Player1(World world , int x, int y) { //when first init give spawn position to player 1
    	this.world = world;
    	faceDir = DIRECTION_UP;
        currPos = new Vector2(x,y);
        currCenter_X = currPos.x + GET_CENTER_X;
        currCenter_Y = currPos.y + GET_CENTER_Y;
        
        isPlayerDead = false;
        hitPoints = 3;
    }    
 
    public Vector2 getPosition() { // for other class to get current position of player1
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
    
    public void takeDamage(int damageReceive) {
    	hitPoints -= damageReceive;
    	checkIfAlive();
    }
    
    public void checkIfAlive() {
    	if(hitPoints <= 0)
    	{
    		isPlayerDead = true;
    		world.somePlayerIsDead();
    	}
    	else
    	{
    		isPlayerDead = false;
    	}
    }
    
    public void move(int dir) { 
    	if(currCenter_X < 0) // prevent player walk off screen
    	{
    		currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[2][0];
    	}
    	if(currCenter_X > SCREEN_WIDTH - 0)
    	{
    		currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[4][0];
    	}
    	if(currCenter_Y < 0)
    	{
    		currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[1][1];
    	}
    	if(currCenter_Y > SCREEN_HEIGHT - 0)
    	{
    		currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[3][1];
    	}
    	currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][0];
    	currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][1];
    	currCenter_X = currPos.x + GET_CENTER_X;
    	currCenter_Y = currPos.y + GET_CENTER_Y;
        // first [dir] is chose which {,} to use, second [] chose first para or second para in {,}
    }
    
    public void update(float delta)
    {
    	
    	getCurrentXPos();
    	getCurrentYPos();
    }
}