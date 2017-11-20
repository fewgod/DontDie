package com.dontdie.game;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2; //how to create position at the center of image (right now it's bottom left)

public class Attack {
	public static final int SCREEN_WIDTH = 900;
	public static final int SCREEN_HEIGHT = 700;
	
	public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_DOWN = 3;
    public static final int DIRECTION_LEFT = 4;
    public static final int DIRECTION_STILL = 0;
    public int faceDir;
    private int ATTACK_MOVE_SPEED = 4;
    private int ATTACK_PUSH_POWER = ATTACK_MOVE_SPEED * 15;
    public Vector2 currPos;
    private World world;
 
  //attack image size is 50*75
  	private float IMAGE_SIZE_X = 50;
  	private float IMAGE_SIZE_Y = 75;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
	private int availableTime;
    
    private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0},
        {0,1},
        {1,0},
        {0,-1},
        {-1,0}
    };
    
    public Attack(World world ,int playerDirection, float playerCenterX, float playerCenterY) { //when first init give spawn position to player 1
    	this.world = world;
        currPos = new Vector2(playerCenterX,playerCenterY);
        currCenter_X = currPos.x + GET_CENTER_X;
        currCenter_Y = currPos.y + GET_CENTER_Y;
        availableTime = 10;
        faceDir = playerDirection;
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

    public int getCurrentFace() 
    {
    	return faceDir;
    }
    
    public void move(int dir) { 
    	currPos.x += ATTACK_MOVE_SPEED * DIR_OFFSETS[dir][0];
    	currPos.y += ATTACK_MOVE_SPEED * DIR_OFFSETS[dir][1];
    	currCenter_X = currPos.x + GET_CENTER_X;
    	currCenter_Y = currPos.y + GET_CENTER_Y;
        // first [dir] is chose which {,} to use, second [] chose first para or second para in {,}
    }
    
    public void update(float delta)
    {
    	getCurrentXPos();
    	getCurrentYPos();
    	checkIfHitEnemy();
    	dissapearTime();
    }
    
    private void checkIfHitEnemy()
    {
    	for(int i =0 ; i< world.snake_list.size() ; i++) //update every snake in snake_list
    	{
    		if(world.snake_list.get(i).getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && world.snake_list.get(i).getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within 20 radius.x of this enemy
			{
				if(world.snake_list.get(i).getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && world.snake_list.get(i).getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within 20 radius.y of this enemy
				{
					pushEnemy(world.snake_list.get(i));
					world.snake_list.get(i).takeDamage(1);
				}
			}
    	}
    }
    
    private void pushEnemy(Snake snake) 
	{
    	snake.currPos.x += ATTACK_PUSH_POWER * DIR_OFFSETS[faceDir][0];
    	snake.currPos.y += ATTACK_PUSH_POWER * DIR_OFFSETS[faceDir][1];
	}
    
    private void dissapearTime()
    {
    	availableTime -=1;
    	if(availableTime <= 0)
    	{
    		world.attack_list.remove(this);
    	}
    }
}