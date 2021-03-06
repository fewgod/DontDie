package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

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
 
  //attack image size is 50*75 for left swing
  	private float IMAGE_SIZE_X;
  	private float IMAGE_SIZE_Y;
  	private float GET_CENTER_X;
  	private float GET_CENTER_Y;
  	private float IMAGE_RADIUS_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
	private int availableTime;  //time for this swing will appear the more the longer
	public int attackType; //1 for normal attack ,2 for fireball ,3 for slow big fireball
    
    private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0},
        {0,1},
        {1,0},
        {0,-1},
        {-1,0}
    };
    
    public Attack(World world ,int playerDirection ,int attackType , float playerCenterX, float playerCenterY) { //when first init give spawn position to player 1
    	this.world = world;
    	this.attackType = attackType;
    	faceDir = playerDirection;
        currPos = new Vector2(playerCenterX,playerCenterY);
        
        if(faceDir == DIRECTION_LEFT)
        {
        	IMAGE_SIZE_X = 60; //already centrealize the image in 60 *95 size
          	IMAGE_SIZE_Y = 95;
          	currPos.x -= 58;
        	currPos.y -= 50;
        }
        else if(faceDir == DIRECTION_RIGHT)
        {
        	IMAGE_SIZE_X = 60;
          	IMAGE_SIZE_Y = 95;
          	currPos.x += 0;
        	currPos.y -= 45;
        }
        else if(faceDir == DIRECTION_UP)
        {
        	IMAGE_SIZE_X = 95;
          	IMAGE_SIZE_Y = 60;
          	currPos.x -= 39;
        	currPos.y += 6;
        }
        else if(faceDir == DIRECTION_DOWN)
        {
        	IMAGE_SIZE_X = 95;
          	IMAGE_SIZE_Y = 60;
          	currPos.x -= 50;
        	currPos.y -= 66;
        }
        
        if(attackType == 1)
        {
        	availableTime = 8;
        }
        else if(attackType ==2)
        {
        	availableTime = 33;
        	IMAGE_SIZE_X = 50;
          	IMAGE_SIZE_Y = 50;
          	currPos.x += 15;
          	currPos.y += 10;
        }
        else if(attackType == 3)
        {
        	availableTime = 277;
        	IMAGE_SIZE_X = 75;
          	IMAGE_SIZE_Y = 75;
          	currPos.x += 15;
          	currPos.y += 10;
        }
      	GET_CENTER_X = IMAGE_SIZE_X/2;
      	GET_CENTER_Y = IMAGE_SIZE_Y/2;
      	IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
      	IMAGE_RADIUS_Y = GET_CENTER_Y;
    	currCenter_X = currPos.x + GET_CENTER_X;
    	currCenter_Y = currPos.y + GET_CENTER_Y;
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
    	if(attackType == 3)
    	{
    		currPos.x += ATTACK_MOVE_SPEED*0.5 * DIR_OFFSETS[dir][0];
    		currPos.y += ATTACK_MOVE_SPEED*0.5 * DIR_OFFSETS[dir][1];
    	}
    	else if(attackType == 2)
    	{
    		currPos.x += ATTACK_MOVE_SPEED*1.3 * DIR_OFFSETS[dir][0];
    		currPos.y += ATTACK_MOVE_SPEED*1.3 * DIR_OFFSETS[dir][1];
    	}
    	currCenter_X = currPos.x + GET_CENTER_X;
    	currCenter_Y = currPos.y + GET_CENTER_Y;
        // first [dir] is chose which {,} to use, second [] chose first para or second para in {,}
    }
    
    public void update(float delta)
    {
    	getCurrentXPos();
    	getCurrentYPos();
    	if(attackType > 1)
    	{
    		move(faceDir);
    	}
    	checkIfHitEnemy();
    	dissapearTime();
    }
    
    private void checkIfHitEnemy()
    {
    	for(int i =0 ; i< world.snake_list.size() ; i++) //if hit snake in snake in snake_list
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
    	for(int i =0 ; i< world.ball_list.size() ; i++) //if hit ball
    	{
    		if(world.ball_list.get(i).getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && world.ball_list.get(i).getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within 20 radius.x of this enemy
			{
				if(world.ball_list.get(i).getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && world.ball_list.get(i).getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within 20 radius.y of this enemy
				{
					world.ball_list.get(i).changeDirection(faceDir); //will make ball change direction to the direction of this attack
					world.score += 5;
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