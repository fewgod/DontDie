package com.dontdie.game;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Snake {
	private int SNAKE_MOVE_SPEED = 3;
	private int SNAKE_PUSH_POWER = SNAKE_MOVE_SPEED * 3;
	
	//snake image size is 40*40
	private float IMAGE_SIZE_X = 40;
	private float IMAGE_SIZE_Y = 40;
	private float GET_CENTER_X = IMAGE_SIZE_X/2;
	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
	private float IMAGE_RADIUS_X = GET_CENTER_X; //only for easy use
	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
	private float CURR_CENTER_X;
	private float CURR_CENTER_Y;
	
	private Vector2 currPos;
	private World world;
	private Player1 player1;
	private Player1 player2;
	private boolean chasingPlayer1 = false;
	private boolean chasingPlayer2 = false;
	private boolean initWhoToChase = false;
	
	public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_BOTTOM = 3;
    public static final int DIRECTION_LEFT = 4;
    public static final int DIRECTION_UPPER_RIGHT = 5; // from 5 to 8 will walk in both axis
    public static final int DIRECTION_UPPER_LEFT = 6;
    public static final int DIRECTION_BOTTOM_RIGHT = 7;
    public static final int DIRECTION_BOTTOM_LEFT = 8;
    public static final int DIRECTION_STILL = 0;
	private int faceDir;
	private Random rand = new Random(); // to random change to face player
	
	//when first init decide which player is to chase
	
	private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0}, //still
        {0,1}, //move up
        {1,0}, // move right
        {0,-1},// move down
        {-1,0},// move left
        {1,1}, // move UR
        {-1,1}, // move UL
        {1,-1}, // move DR
        {-1,-1} // move DL
    };

	public Snake(World world, int x, int y) { // do this method all time
		this.world = world;
		currPos = new Vector2(x,y);
		CURR_CENTER_X = currPos.x + GET_CENTER_X;
		CURR_CENTER_Y = currPos.y + GET_CENTER_Y;
		if(world.player1IsDead == false) 
		{
			player1 = world.getPlayer1();
		}
		if(world.player2IsDead == false) 
		{
			player2 = world.getPlayer2();
		}
	}

	public void init() //run this method every time snake is created or every time someone is dead
	{
		chasingPlayer1 = false;
		chasingPlayer2 = false;
		if(world.player1IsDead == false && world.player2IsDead == false) // if both alive will random
		{	
			int randomedNumber = rand.nextInt(100);
			if(randomedNumber < 50) //if got less than 50 will chase player 1
			{
				chasingPlayer1 = true;
			}
			else
			{
				chasingPlayer2 = true;
			}
		}
		else if (world.player1IsDead == false) //if player2 is dead
		{
			chasingPlayer1 = true;
		}
		else if (world.player2IsDead == false) //if player1 is dead
		{
			chasingPlayer2 = true;
		}
	}
	
	public Vector2 getPosition() { // for other class to get current position of snake
		CURR_CENTER_X = currPos.x + GET_CENTER_X;
		CURR_CENTER_Y = currPos.y + GET_CENTER_Y;
        return currPos;    
    }
	
	public void update(float delta) //make snake do things
    {
		if(initWhoToChase == false)
		{
			init(); //init who to chase for the first time
			initWhoToChase = true;
		}
		if(world.player1IsDead == false && world.player2IsDead == false) // will only change target when both player is alive
		{	
			shouldItChangePlayerToChase();
		}
		shouldItTurnTowardPlayer();
		if(world.player1IsDead == false || world.player2IsDead == false) //if both are dead will stop moving
		{
			move();
		}
		checkIfCollideWithPlayer();
    }

	private void move() 
	{
		/*if(faceDir >= 5) //if snake move in both axis will use trigometry to make the movement speed the same as move in 1 axis
		{
			currPos.x += Math.pow((Math.pow(SNAKE_MOVE_SPEED,2) + Math.pow(SNAKE_MOVE_SPEED,2)),1/2)* DIR_OFFSETS[faceDir][0];
			currPos.y += Math.pow((Math.pow(SNAKE_MOVE_SPEED,2) + Math.pow(SNAKE_MOVE_SPEED,2)),1/2)* DIR_OFFSETS[faceDir][1];
		}*/
		if(world.timestop <0) //check if whether the time is stop, if not it can move.
		{
			currPos.x += SNAKE_MOVE_SPEED * DIR_OFFSETS[faceDir][0];
			currPos.y += SNAKE_MOVE_SPEED * DIR_OFFSETS[faceDir][1];
			CURR_CENTER_X = currPos.x + GET_CENTER_X;
			CURR_CENTER_Y = currPos.y + GET_CENTER_Y;
		}
	}
	
	private void shouldItChangePlayerToChase()
	{
		if(rand.nextInt(10000) < 10) //if got less than x will change target
		{
			if((chasingPlayer1 == true) && (world.player2IsDead == false)) 
			{
				chasingPlayer1 = false;
				chasingPlayer2 = true;
			}
			else if((chasingPlayer2 == true) && (world.player1IsDead == false)) 
			{
				chasingPlayer2 = false;
				chasingPlayer1 = true;
			}
		}
	}
	
	private void shouldItTurnTowardPlayer() 
	{
		if((chasingPlayer1 == true) && (world.player1IsDead == false))
		{	if(rand.nextInt(100)< 11) //if less got less than x number will turn toward player1
			{
				checkWhereIsPlayer1();
			}
		}
		if((chasingPlayer2 == true) && (world.player2IsDead == false))
		{	if(rand.nextInt(100)< 11) //if less got less than x number will turn toward player2
			{
				checkWhereIsPlayer2();
			}
		}
	}
	
	private void checkWhereIsPlayer1() //make snake turn toward player1 
	{
		Vector2 player1Pos = player1.getPosition(); //get position of player 1
		if(player1.getCurrentYPos() - rand.nextInt(10) > CURR_CENTER_Y) //if player is at the top of snake 
        { //add + - randint for more variety ways of moving.. suppose to ..?
			if(player1.getCurrentXPos() - rand.nextInt(10) > CURR_CENTER_X) //if player is at the top right of snake
	        {
				faceDir = DIRECTION_UPPER_RIGHT;
	        }
			else if(player1.getCurrentXPos()+ rand.nextInt(10) < CURR_CENTER_X) //if player is at the top left of snake
	        {
				faceDir = DIRECTION_UPPER_LEFT;
	        }
			else //if player is at the top of snake only
			{
				faceDir = DIRECTION_UP;
			}
        }
		else if(player1.getCurrentYPos() + rand.nextInt(10) < CURR_CENTER_Y) //if player is at the bottom of snake
        {
			if(player1.getCurrentXPos() - rand.nextInt(10) > CURR_CENTER_X) //if player is  at the bottom right of snake
	        {
				faceDir = DIRECTION_BOTTOM_RIGHT;
	        }
			else if(player1.getCurrentXPos() + rand.nextInt(10)< CURR_CENTER_X) //if player is at the bottom left of snake
	        {
				faceDir = DIRECTION_BOTTOM_LEFT;
	        }
			else //if player is at the bottom of snake only
			{
				faceDir = DIRECTION_BOTTOM;
			}
        }
		else if(player1.getCurrentXPos() - rand.nextInt(10) > CURR_CENTER_X) //if player is at the right of snake
		{
			faceDir = DIRECTION_RIGHT;
		}
		else if(player1.getCurrentXPos() + rand.nextInt(10)< CURR_CENTER_X) //if player is at the left of snake
		{
			faceDir = DIRECTION_LEFT;
		}
	}
	
	private void checkWhereIsPlayer2() //make snake turn toward player1 
	{
        //Vector2 player2Pos = player2.getPosition(); //get position of player 2
        if(player2.getCurrentYPos() - rand.nextInt(10) > currPos.y) //if player is at the top of snake 
        { //add + - randint for more variety ways of moving.. suppose to ..?
			if(player2.getCurrentXPos() - rand.nextInt(10) > CURR_CENTER_X) //if player2 is at the top right of snake
	        {
				faceDir = DIRECTION_UPPER_RIGHT;
	        }
			else if(player2.getCurrentXPos() + rand.nextInt(10) < CURR_CENTER_X) //if player2 is at the top left of snake
	        {
				faceDir = DIRECTION_UPPER_LEFT;
	        }
			else //if player is at the top of snake only
			{
				faceDir = DIRECTION_UP;
			}
        }
        else if(player2.getCurrentYPos() + rand.nextInt(10) < CURR_CENTER_Y) //if player2 is at the bottom of snake
        {
			if(player2.getCurrentXPos() - rand.nextInt(10) > CURR_CENTER_X) //if player2 is  at the bottom right of snake
	        {
				faceDir = DIRECTION_BOTTOM_RIGHT;
	        }
			else if(player2.getCurrentXPos() + rand.nextInt(10)< CURR_CENTER_X) //if player2 is at the bottom left of snake
	        {
				faceDir = DIRECTION_BOTTOM_LEFT;
	        }
			else //if player is at the bottom of snake only
			{
				faceDir = DIRECTION_BOTTOM;
			}
        }
		else if(player2.getCurrentXPos() - rand.nextInt(10) > CURR_CENTER_X) //if player2 is at the right of snake
		{
			faceDir = DIRECTION_RIGHT;
		}
		else if(player2.getCurrentXPos() + rand.nextInt(10)< CURR_CENTER_X) //if player2 is at the left of snake
		{
			faceDir = DIRECTION_LEFT;
		}
	}
	
	private void checkIfCollideWithPlayer() //when collide will remove this enemy
	{
		if(world.player1IsDead == false)
		{
			Vector2 player1Pos = player1.getPosition(); //get position of player 1
			if(player1Pos.x > CURR_CENTER_X - IMAGE_RADIUS_X && player1Pos.x < CURR_CENTER_X + IMAGE_RADIUS_X)  //if player1 is within 20 radius.x of this enemy
			{
				if(player1Pos.y > CURR_CENTER_Y - IMAGE_RADIUS_Y && player1Pos.y < CURR_CENTER_Y + IMAGE_RADIUS_Y) //if player1 is within 20 radius.y of this enemy
				{
					//pushPlayer(1);
					world.killPlayer1();
					world.somePlayerIsDead(); //for snake it will change player to chase
				}
			}
		}
		
		if(world.player2IsDead == false)
		{
			Vector2 player2Pos = player2.getPosition(); //get position of player 1
			if(player2Pos.x > CURR_CENTER_X - IMAGE_RADIUS_X && player2Pos.x < CURR_CENTER_X + IMAGE_RADIUS_X)  //if player2 is within 20 radius.x of this enemy
			{
				if(player2Pos.y > CURR_CENTER_Y - IMAGE_RADIUS_Y && player2Pos.y < CURR_CENTER_Y + IMAGE_RADIUS_Y) //if player2 is within 20 radius.y of this enemy
				{
					//pushPlayer(2);
					world.killPlayer2();
					world.somePlayerIsDead(); //for snake it will change player to chase
				}
			}
		}
	}
	
	private void pushPlayer(int player) 
	{
		int playerNumber = player;
		if(playerNumber == 1)
		{
			world.player1.currPos.x += SNAKE_PUSH_POWER * DIR_OFFSETS[faceDir][0];
			world.player1.currPos.y += SNAKE_PUSH_POWER * DIR_OFFSETS[faceDir][1];
		}
		if(playerNumber == 2)
		{
			world.player2.currPos.x += SNAKE_PUSH_POWER * DIR_OFFSETS[faceDir][0];
			world.player2.currPos.y += SNAKE_PUSH_POWER * DIR_OFFSETS[faceDir][1];
		}
	}
}
