package com.dontdie.game;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Snake extends Object{
	private int SNAKE_MOVE_SPEED = 3;
	private int SNAKE_PUSH_POWER = SNAKE_MOVE_SPEED * 10;
	private int SNAKE_ATTACK_POWER = 1;
	
	//snake image size is 40*40
	private static float IMAGE_SIZE_X = 40;
	private static float IMAGE_SIZE_Y = 40;
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
	boolean chasingPlayer1 = false;
	boolean chasingPlayer2 = false;
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
	private Random rand = new Random(); // to random chance to face player
	
	private int cooldown_movetime; // if less than 0 will be able to move
	private int MAX_HITPOINTS = 2;
    private int hitPoints;
	
	
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

	public Snake(World world, int x, int y) {
		super(x,y,IMAGE_SIZE_X,IMAGE_SIZE_Y);
		this.world = world;
		currPos = new Vector2(x,y);
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		
		//If it want to check whether it collide with player or not ,it must init (must have these 2 line)
		setImgRadius(IMAGE_RADIUS_X,IMAGE_RADIUS_Y); //send to Object class
		setWorldandSetPlayer(world);
		//--------------------------------------------------------------------------------------
		
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		cooldown_movetime = 0;
		hitPoints = MAX_HITPOINTS;
	}

	public void init() //run this method every time snake is created or every time someone is dead or being revived
	{
		chasingPlayer1 = false;
		chasingPlayer2 = false;
		if(player1.isPlayerDead == false && player2.isPlayerDead == false) // if both alive will random
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
		else if (player1.isPlayerDead == false) //if player2 is dead
		{
			chasingPlayer1 = true;
		}
		else if (player2.isPlayerDead == false) //if player1 is dead
		{
			chasingPlayer2 = true;
		}
		
		if(world.provokeTime >0 && player1.isPlayerDead == false)
		{
			chasingPlayer2 = false;
			chasingPlayer1 = true;
		}
		if(world.provokeTime == 0)
		{
			if(player1.isPlayerDead == false && player2.isPlayerDead == false) // if both alive will random
			{	
				int randomedNumber = rand.nextInt(100);
				if(randomedNumber < 50)
				{
					chasingPlayer1 = true;
				}
				else
				{
					chasingPlayer2 = true;
				}
			}
		}
	}
	
	public void takeDamage(int damageReceive) {
    	hitPoints -= damageReceive;
    	checkIfAlive();
    }
	
	public void checkIfAlive() {
		if(hitPoints <=0)
		{
			world.enemydead.play(0.63f);
			world.snake_list.remove(this);
			world.score += 3;
		}
	}
	
	public void update(float delta) //make snake do things
    {
		if(initWhoToChase == false)
		{
			init(); //init who to chase for the first time
			initWhoToChase = true;
		}
		if(player1.isPlayerDead == false && player2.isPlayerDead == false) // will only change target when both player is alive
		{	
			if(world.provokeTime <= 0 ) //if provoke will not change target
			{
				shouldItChangePlayerToChase();
			}
		}
		shouldItTurnTowardPlayer();
		if(player1.isPlayerDead == false || player2.isPlayerDead == false) //if both are dead will stop moving
		{
			move();
		}
		setCurrPos(currPos);// must update to get the lastest current position of this object
		checkIfCollideWithPlayer();
    }

	private void move() 
	{
		if(world.timeStop <= 0 && cooldown_movetime <= 0) //check if whether the time is stop and is it in unmovable state? , if not it can move.
		{
			currPos.x += SNAKE_MOVE_SPEED * DIR_OFFSETS[faceDir][0];
			currPos.y += SNAKE_MOVE_SPEED * DIR_OFFSETS[faceDir][1];
		}
		else if (world.timeStop >= 0 && world.timeStop <= 75) //add breaking timestop animation when timestop is running out
		{
			currPos.x += rand.nextInt(3);
			currPos.x -= rand.nextInt(3);
		}
		currCenter_X = currPos.x + GET_CENTER_X;
		currCenter_Y = currPos.y + GET_CENTER_Y;
		cooldown_movetime -=1;
	}
	
	private void shouldItChangePlayerToChase()
	{
		if(rand.nextInt(10000) < 12) //if got less than x will change target
		{
			if(chasingPlayer1 == true && player2.isPlayerDead == false) 
			{
				chasingPlayer1 = false;
				chasingPlayer2 = true;
			}
			else if(chasingPlayer2 == true && player1.isPlayerDead== false) 
			{
				chasingPlayer2 = false;
				chasingPlayer1 = true;
			}
		}
	}
	
	private void shouldItTurnTowardPlayer() 
	{
		if(chasingPlayer1 == true && player1.isPlayerDead == false)
		{	if(rand.nextInt(100)<= 6) //if less got less than x number will turn toward player1
			{
				checkWhereIsPlayer1();
			}
		}
		if(chasingPlayer2 == true && player2.isPlayerDead == false)
		{	if(rand.nextInt(100)<= 6) //if less got less than x number will turn toward player2
			{
				checkWhereIsPlayer2();
			}
		}
	}
	
	private void checkWhereIsPlayer1() //make snake turn toward player1 
	{
		if(player1.getCurrentYPos() - rand.nextInt(10) > currCenter_Y) //if player is at the top of snake 
        { //add + - randint for more variety ways of moving.. suppose to ..?
			if(player1.getCurrentXPos() - rand.nextInt(10) > currCenter_X) //if player is at the top right of snake
	        {
				faceDir = DIRECTION_UPPER_RIGHT;
	        }
			else if(player1.getCurrentXPos()+ rand.nextInt(10) < currCenter_X) //if player is at the top left of snake
	        {
				faceDir = DIRECTION_UPPER_LEFT;
	        }
			else //if player is at the top of snake only
			{
				faceDir = DIRECTION_UP;
			}
        }
		else if(player1.getCurrentYPos() + rand.nextInt(10) < currCenter_Y) //if player is at the bottom of snake
        {
			if(player1.getCurrentXPos() - rand.nextInt(10) > currCenter_X) //if player is  at the bottom right of snake
	        {
				faceDir = DIRECTION_BOTTOM_RIGHT;
	        }
			else if(player1.getCurrentXPos() + rand.nextInt(10)< currCenter_X) //if player is at the bottom left of snake
	        {
				faceDir = DIRECTION_BOTTOM_LEFT;
	        }
			else //if player is at the bottom of snake only
			{
				faceDir = DIRECTION_BOTTOM;
			}
        }
		else if(player1.getCurrentXPos() - rand.nextInt(10) > currCenter_X) //if player is at the right of snake
		{
			faceDir = DIRECTION_RIGHT;
		}
		else if(player1.getCurrentXPos() + rand.nextInt(10)< currCenter_X) //if player is at the left of snake
		{
			faceDir = DIRECTION_LEFT;
		}
	}
	
	private void checkWhereIsPlayer2() //make snake turn toward player1 
	{
        if(player2.getCurrentYPos() - rand.nextInt(10) > currCenter_Y) //if player is at the top of snake 
        { //add + - randint for more variety ways of moving..
			if(player2.getCurrentXPos() - rand.nextInt(10) > currCenter_X) //if player2 is at the top right of snake
	        {
				faceDir = DIRECTION_UPPER_RIGHT;
	        }
			else if(player2.getCurrentXPos() + rand.nextInt(10) < currCenter_X) //if player2 is at the top left of snake
	        {
				faceDir = DIRECTION_UPPER_LEFT;
	        }
			else //if player is at the top of snake only
			{
				faceDir = DIRECTION_UP;
			}
        }
        else if(player2.getCurrentYPos() + rand.nextInt(10) < currCenter_Y) //if player2 is at the bottom of snake
        {
			if(player2.getCurrentXPos() - rand.nextInt(10) > currCenter_X) //if player2 is  at the bottom right of snake
	        {
				faceDir = DIRECTION_BOTTOM_RIGHT;
	        }
			else if(player2.getCurrentXPos() + rand.nextInt(10)< currCenter_X) //if player2 is at the bottom left of snake
	        {
				faceDir = DIRECTION_BOTTOM_LEFT;
	        }
			else //if player is at the bottom of snake only
			{
				faceDir = DIRECTION_BOTTOM;
			}
        }
		else if(player2.getCurrentXPos() - rand.nextInt(10) > currCenter_X) //if player2 is at the right of snake
		{
			faceDir = DIRECTION_RIGHT;
		}
		else if(player2.getCurrentXPos() + rand.nextInt(10)< currCenter_X) //if player2 is at the left of snake
		{
			faceDir = DIRECTION_LEFT;
		}
	}
	
	private void checkIfCollideWithPlayer() //when collide will deal damage to player
	{		
		if(ifHitPlayer1() == true) //ifHitPlayer method is from parent class
		{
			pushPlayer(1 ,SNAKE_PUSH_POWER ,faceDir);
			player1.takeDamage(SNAKE_ATTACK_POWER);
			if(player1.invisibleTime <=0)
			{
				cooldown_movetime = 10;
			}
		}
		if(ifHitPlayer2() == true)
		{
			pushPlayer(2 ,SNAKE_PUSH_POWER ,faceDir);
			player2.takeDamage(SNAKE_ATTACK_POWER);
			if(player1.invisibleTime <=0)
			{
				cooldown_movetime = 10;
			}
		}
	}
	
}