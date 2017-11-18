package com.dontdie.game;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Snake {
	private int SNAKE_MOVE_SPEED = 3;
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
		if(world.player1IsDead == false)
        {
			player1 = world.getPlayer1();
        }
		player2 = world.getPlayer2();
	}

	private void initWhoToChase() 
	{
		if(rand.nextInt(100) < 50 && world.player1IsDead == false) //if got less than 50 will chase player 1
		{
			chasingPlayer1 = true;
		}
		else
		{
			chasingPlayer2 = true;
		}
	}
	
	public Vector2 getPosition() { // for other class to get current position of snake
        return currPos;    
    }
	
	public void update(float delta) //make snake do things
    {
		if(initWhoToChase == false)
		{
			initWhoToChase(); //init who to chase for the first time
			initWhoToChase = true;
		}
		shouldItChangePlayerToChase();
		shouldItTurnTowardPlayer();
		move();
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
		}
	}
	
	private void shouldItChangePlayerToChase()
	{
		if(rand.nextInt(10000) < 10) //if got less than x will change target
		{
			if(chasingPlayer1 == true && world.player1IsDead == false) 
			{
				chasingPlayer1 = false;
				chasingPlayer2 = true;
			}
			else if(chasingPlayer2 == true) 
			{
				chasingPlayer2 = false;
				chasingPlayer1 = true;
			}
		}
	}
	
	private void shouldItTurnTowardPlayer() 
	{
		if(chasingPlayer1 == true && world.player1IsDead == false)
		{	if(rand.nextInt(100)< 11) //if less got less than x number will turn toward player1
			{
				checkWhereIsPlayer1();
			}
		}
		if(chasingPlayer2 == true)
		{	if(rand.nextInt(100)< 11) //if less got less than x number will turn toward player1
			{
				checkWhereIsPlayer2();
			}
		}
	}
	private void checkWhereIsPlayer1() //make snake turn toward player1 
	{
		Vector2 player1Pos = player1.getPosition(); //get position of player 1
		if(player1Pos.y - rand.nextInt(20) > currPos.y) //if player is at the top of snake 
        { //add + - randint for more variety ways of moving.. suppose to ..?
			if(player1Pos.x - rand.nextInt(20) > currPos.x) //if player is at the top right of snake
	        {
				faceDir = DIRECTION_UPPER_RIGHT;
	        }
			else if(player1Pos.x + rand.nextInt(20) < currPos.x) //if player is at the top left of snake
	        {
				faceDir = DIRECTION_UPPER_LEFT;
	        }
			else //if player is at the top of snake only
			{
				faceDir = DIRECTION_UP;
			}
        }
		if(player1Pos.y + rand.nextInt(20) < currPos.y) //if player is at the bottom of snake
        {
			if(player1Pos.x - rand.nextInt(20) > currPos.x) //if player is  at the bottom right of snake
	        {
				faceDir = DIRECTION_BOTTOM_RIGHT;
	        }
			else if(player1Pos.x + rand.nextInt(20)< currPos.x) //if player is at the bottom left of snake
	        {
				faceDir = DIRECTION_BOTTOM_LEFT;
	        }
			else //if player is at the bottom of snake only
			{
				faceDir = DIRECTION_BOTTOM;
			}
        }
	}
	
	private void checkWhereIsPlayer2() //make snake turn toward player1 
	{
        Vector2 player2Pos = player2.getPosition(); //get position of player 2
		if(player2Pos.y - rand.nextInt(20) > currPos.y) //if player is at the top of snake 
        { //add + - randint for more variety ways of moving.. suppose to ..?
			if(player2Pos.x - rand.nextInt(20) > currPos.x) //if player is at the top right of snake
	        {
				faceDir = DIRECTION_UPPER_RIGHT;
	        }
			else if(player2Pos.x + rand.nextInt(20) < currPos.x) //if player is at the top left of snake
	        {
				faceDir = DIRECTION_UPPER_LEFT;
	        }
			else //if player is at the top of snake only
			{
				faceDir = DIRECTION_UP;
			}
        }
		if(player2Pos.y + rand.nextInt(20) < currPos.y) //if player is at the bottom of snake
        {
			if(player2Pos.x - rand.nextInt(20) > currPos.x) //if player is  at the bottom right of snake
	        {
				faceDir = DIRECTION_BOTTOM_RIGHT;
	        }
			else if(player2Pos.x + rand.nextInt(20)< currPos.x) //if player is at the bottom left of snake
	        {
				faceDir = DIRECTION_BOTTOM_LEFT;
	        }
			else //if player is at the bottom of snake only
			{
				faceDir = DIRECTION_BOTTOM;
			}
        }
	}
	
	private void checkIfCollideWithPlayer() //when collide will remove this enemy
	{
		if(world.player1IsDead == false)
		{
			Vector2 player1Pos = player1.getPosition(); //get position of player 1
			if(player1Pos.x > currPos.x - 30 && player1Pos.x < currPos.x + 30)  //if player1 is within 30 radius.x of this enemy
			{
				if(player1Pos.y > currPos.y - 30 && player1Pos.y < currPos.y + 30) //if player1 is within 30 radius.y of this enemy
				{
					world.player1IsDead = true;
					world.player1 = null;
    			//world.snake_list.remove(this);
				}
			}
		}
        Vector2 player2Pos = player2.getPosition(); //get position of player 2
	}
}
