package com.dontdie.game;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class IronBall extends Object{
	private final int BALL_MOVE_SPEED = 10;
	private final int BALL_PUSH_POWER = BALL_MOVE_SPEED * 10;
	private final int BALL_ATTACK_POWER = 2;
	
	//snake image size is 53*53
	private static final float IMAGE_SIZE_X = 53;
	private static final float IMAGE_SIZE_Y = 53;
	private float GET_CENTER_X = IMAGE_SIZE_X/2;
	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
	private float currCenter_X;
	private float currCenter_Y;
	
	public Vector2 currPos;
	private World world;
	private Player player1;
	private Player player2;
	
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
		super(x,y,IMAGE_SIZE_X,IMAGE_SIZE_Y);
		this.world = world;
		currPos = new Vector2(x,y);
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		faceDir = dir;
		
		//If it want to check whether it collide with player or not ,it must init (must have these 2 line)
		setImgRadius(IMAGE_RADIUS_X,IMAGE_RADIUS_Y); //send to Object class
		setWorldandSetPlayer(world);
		//--------------------------------------------------------------------------------------
		
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
	
	public void update(float delta) //make snake do things
    {
		setCurrPos(currPos);// must update to get the lastest current position of this object
		move();
		checkIfCollideWithPlayer();
		checkIfOutBound();
    }

	private void move() 
	{
		if(world.timeStop <= 0) //check if whether the time is stop and is it in unmovable state? , if not it can move.
		{
			currPos.x += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][0];
			currPos.y += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][1];
		}
		else if(world.timeStop >= 0 && gotAttack > 0) //if got attack will move regardless of timestop or not
		{
			currPos.x += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][0];
			currPos.y += BALL_MOVE_SPEED * DIR_OFFSETS[faceDir][1];
		}
		else if (world.timeStop > 0 && world.timeStop <= 75 && gotAttack == 0) //add breaking timestop animation when timestop is running out
		{
			currPos.x += rand.nextInt(3);
			currPos.x -= rand.nextInt(3);
		}
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
	}

	private void checkIfCollideWithPlayer() //when collide will deal damage to player
	{		
		if(ifHitPlayer1() == true) //ifHitPlayer method is from parent class
		{
			pushPlayer(1 ,BALL_PUSH_POWER ,faceDir);
			player1.takeDamage(BALL_ATTACK_POWER);
			world.ball_list.remove(this);
		}
		if(ifHitPlayer2() == true)
		{
			pushPlayer(2 ,BALL_PUSH_POWER ,faceDir);
			player2.takeDamage(BALL_ATTACK_POWER);
			world.ball_list.remove(this);
		}
	}

	public void changeDirection(int dirToChangeTo) { //if got attacked by player will trigger this function to change it's direction
		faceDir = dirToChangeTo;
		gotAttack +=1;
		
	}
}
