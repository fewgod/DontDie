package com.dontdie.game;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Snake {
	private int SNAKE_MOVE_SPEED = 3;
	private Vector2 currPos;
	private World world;
	private Player1 player1;
	private Player1 player2;
	
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
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
	}

	public Vector2 getPosition() { // for other class to get current position of player1
        return currPos;    
    }
	
	public void update(float delta) //snake will move to the right
    {
		if(rand.nextInt(100)< 7) //if less got less than x number will turn toward player1
		{
			checkWhereIsPlayer1();
		}
		move();
    }

	private void move() 
	{
		/*if(faceDir >= 5) //if snake move in both axis will use trigometry to make the movement speed the same as move in 1 axis
		{
			currPos.x += Math.pow((Math.pow(SNAKE_MOVE_SPEED,2) + Math.pow(SNAKE_MOVE_SPEED,2)),1/2)* DIR_OFFSETS[faceDir][0];
			currPos.y += Math.pow((Math.pow(SNAKE_MOVE_SPEED,2) + Math.pow(SNAKE_MOVE_SPEED,2)),1/2)* DIR_OFFSETS[faceDir][1];
		}*/
		currPos.x += SNAKE_MOVE_SPEED * DIR_OFFSETS[faceDir][0];
		currPos.y += SNAKE_MOVE_SPEED * DIR_OFFSETS[faceDir][1];
	}
	private void checkWhereIsPlayer1() 
	{
		Vector2 player1Pos = player1.getPosition(); //get position of player 1
        Vector2 player2Pos = player2.getPosition(); //get position of player 2
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
}
