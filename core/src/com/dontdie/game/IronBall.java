package com.dontdie.game;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class IronBall {
	private int BALL_MOVE_SPEED = 11;
	private int BALL_PUSH_POWER = BALL_MOVE_SPEED * 9;
	
	//snake image size is 50*50
	private float IMAGE_SIZE_X = 50;
	private float IMAGE_SIZE_Y = 50;
	private float GET_CENTER_X = IMAGE_SIZE_X/2;
	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
	private float currCenter_X;
	private float currCenter_Y;
	
	public Vector2 currPos;
	private World world;
	private Player1 player1;
	private Player1 player2;
	
	private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_RIGHT = 2;
    private static final int DIRECTION_BOTTOM = 3;
    private static final int DIRECTION_LEFT = 4;
    private static final int DIRECTION_STILL = 0;
	private int spawnDir;
	private Random rand = new Random();
	
	private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0}, //still
        {0,1}, //move up
        {1,0}, // move right
        {0,-1},// move down
        {-1,0},// move left
    };

	public IronBall(World world,int dir, int x, int y) { // do this method all time
		this.world = world;
		currPos = new Vector2(x,y);
		spawnDir = dir;
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
	}
	
	public void checkIfOutBound() {
		if(currCenter_X < 0 || currCenter_X > DontDieGame.SCREEN_WIDTH || currCenter_Y < 0 || currCenter_Y > DontDieGame.SCREEN_HEIGHT)
		{
			world.ball_list.remove(this);
		}
	}
	public Vector2 getPosition() { // for other class to get current position of snake
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
	
	public void update(float delta) //make snake do things
    {
		move();
		checkIfCollideWithPlayer();
		checkIfOutBound();
    }

	private void move() 
	{
		if(world.timestop <= 0) //check if whether the time is stop and is it in unmovable state? , if not it can move.
		{
			currPos.x -= BALL_MOVE_SPEED * DIR_OFFSETS[spawnDir][0];
			currPos.y -= BALL_MOVE_SPEED * DIR_OFFSETS[spawnDir][1]; //spawn which corner will move opposite corner
		}
		else if (world.timestop >= 0 && world.timestop <= 75) //add breaking timestop animation when timestop is running out
		{
			currPos.x += rand.nextInt(3);
			currPos.x -= rand.nextInt(3);
		}
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
	}
	private void checkIfCollideWithPlayer() //when collide will remove this enemy
	{
		if(player1.isPlayerDead == false)
		{
			Vector2 player1Pos = player1.getPosition(); //get position of player 1
			if(player1.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player1.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within 20 radius.x of this enemy
			{
				if(player1.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player1.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within 20 radius.y of this enemy
				{
					pushPlayer(1);
					player1.takeDamage(3);
					world.ball_list.remove(this);
				}
			}
		}
		
		if(player2.isPlayerDead== false)
		{
			Vector2 player2Pos = player2.getPosition(); //get position of player 1
			if(player2.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player2.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player2 is within 20 radius.x of this enemy
			{
				if(player2.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player2.getCurrentYPos()< currCenter_Y + IMAGE_RADIUS_Y) //if player2 is within 20 radius.y of this enemy
				{
					pushPlayer(2);
					player2.takeDamage(3);
					world.ball_list.remove(this);
				}
			}
		}
	}
	
	private void pushPlayer(int player) 
	{
		int playerNumber = player;
		if(playerNumber == 1)
		{
			if(world.player1.invisibleTime <= 0)
			{
				world.player1.currPos.x -= BALL_PUSH_POWER * DIR_OFFSETS[spawnDir][0];
				world.player1.currPos.y -= BALL_PUSH_POWER * DIR_OFFSETS[spawnDir][1];
			}
		}
		if(playerNumber == 2)
		{
			if(world.player2.invisibleTime <= 0)
			{
				world.player2.currPos.x -= BALL_PUSH_POWER * DIR_OFFSETS[spawnDir][0];
				world.player2.currPos.y -= BALL_PUSH_POWER * DIR_OFFSETS[spawnDir][1];
			}
		}
	}
}