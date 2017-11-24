package com.dontdie.game;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class IronBall {
	private int BALL_MOVE_SPEED = 11;
	private int BALL_PUSH_POWER = BALL_MOVE_SPEED * 9;
	
	//snake image size is 53*53
	private float IMAGE_SIZE_X = 53;
	private float IMAGE_SIZE_Y = 53;
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
	
	private int faceDir;
	private Random rand = new Random();
	public int gotAttack;
	
	private static final int [][] DIR_OFFSETS = new int [][] {
        {0,0}, //still
        {0,1}, //move up
        {1,0}, // move right
        {0,-1},// move down
        {-1,0},// move left
    };

	public IronBall(World world,int dir, int x, int y) {
		this.world = world;
		currPos = new Vector2(x,y);
		faceDir = dir;
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		gotAttack = 0;
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
	}
	
	public void checkIfOutBound() {
		if(currCenter_X < -70 || currCenter_X > DontDieGame.SCREEN_WIDTH +70 || currCenter_Y < -70 || currCenter_Y > DontDieGame.SCREEN_HEIGHT +70)
		{
			world.ball_list.remove(this);
		}
	}
	public Vector2 getPosition() { // for other class to get current position of ball
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
			currPos.x += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][0];
			currPos.y += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][1];
		}
		else if(world.timestop >= 0 && gotAttack > 0) //if got attack will move regardless of timestop or not
		{
			currPos.x += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][0];
			currPos.y += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][1];
		}
		else if (world.timestop > 0 && world.timestop <= 75 && gotAttack == 0) //add breaking timestop animation when timestop is running out
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
			if(player1.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player1.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within radius.x of this enemy
			{
				if(player1.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player1.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within radius.y of this enemy
				{
					pushPlayer(1);
					player1.takeDamage(3);
					world.ball_list.remove(this);
				}
			}
		}
		
		if(player2.isPlayerDead== false)
		{
			if(player2.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player2.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player2 is within radius.x of this enemy
			{
				if(player2.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player2.getCurrentYPos()< currCenter_Y + IMAGE_RADIUS_Y) //if player2 is within radius.y of this enemy
				{
					pushPlayer(2);
					player2.takeDamage(3);
					world.ball_list.remove(this);
				}
			}
		}
	}
	
	private void pushPlayer(int player) //if hit player will push player by this function
	{
		int playerNumber = player;
		if(playerNumber == 1)
		{
			if(world.player1.invisibleTime <= 0)
			{
				world.player1.currPos.x += BALL_PUSH_POWER * DIR_OFFSETS[faceDir][0];
				world.player1.currPos.y += BALL_PUSH_POWER * DIR_OFFSETS[faceDir][1];
			}
		}
		if(playerNumber == 2)
		{
			if(world.player2.invisibleTime <= 0)
			{
				world.player2.currPos.x += BALL_PUSH_POWER * DIR_OFFSETS[faceDir][0];
				world.player2.currPos.y += BALL_PUSH_POWER * DIR_OFFSETS[faceDir][1];
			}
		}
	}

	public void changeDirection(int dirToChangeTo) { //if got attacked by player will trigger this function to change it's direction
		faceDir = dirToChangeTo;
		gotAttack +=1;
		
	}
}
