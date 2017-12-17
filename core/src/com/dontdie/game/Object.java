package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Object {

    public Vector2 currPos;
	private World world;
	private Player player1;
	private Player player2;

  	private float IMAGE_SIZE_X;
  	private float IMAGE_SIZE_Y;
  	private float GET_CENTER_X;
  	private float GET_CENTER_Y;
	private float IMAGE_RADIUS_X;
	private float IMAGE_RADIUS_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
    
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
  	
    public Object(int x, int y ,float img_size_x ,float img_size_y) { //when first init give spawn position to player 1
        currPos = new Vector2(x,y);
        IMAGE_SIZE_X = img_size_x;
        IMAGE_SIZE_Y = img_size_y;
      	GET_CENTER_X = IMAGE_SIZE_X/2;
      	GET_CENTER_Y = IMAGE_SIZE_Y/2;
        currCenter_X = currPos.x + GET_CENTER_X;
        currCenter_Y = currPos.y + GET_CENTER_Y;

    }
 
    public void setCurrPos(Vector2 pos) //to set current position of the object that use this class ... If not set others class and itself wont know where it is
    {
    	currPos = pos;
    }
    
    public void setWorldandSetPlayer(World receiveWorld) //for those enemy and item class to call this method
    {
    	world = receiveWorld;
    	player1 = world.getPlayer1();
    	player2 = world.getPlayer2();
    }
    
    public void setImgRadius(float img_radius_x ,float img_radius_y) //for those enemy and item class to call this method to use with check if hit player afterward
    {
    	IMAGE_RADIUS_X = img_radius_x;
    	IMAGE_RADIUS_Y = img_radius_y;
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
    
    public boolean ifHitPlayer1() 
    {
    	boolean hit = false;
    	if(player1.isPlayerDead == false)
		{
			if(player1.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player1.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player1 is within radius.x of this enemy
			{
				if(player1.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player1.getCurrentYPos() < currCenter_Y + IMAGE_RADIUS_Y) //if player1 is within radius.y of this enemy
				{
					hit = true;
				}
			}
		}
    	return hit;
    }
    
    public boolean ifHitPlayer2() 
    {
    	boolean hit = false;
		if(player2.isPlayerDead== false)
		{
			if(player2.getCurrentXPos() > currCenter_X - IMAGE_RADIUS_X && player2.getCurrentXPos() < currCenter_X + IMAGE_RADIUS_X)  //if player2 is within radius.x of this enemy
			{
				if(player2.getCurrentYPos() > currCenter_Y - IMAGE_RADIUS_Y && player2.getCurrentYPos()< currCenter_Y + IMAGE_RADIUS_Y) //if player2 is within radius.y of this enemy
				{
					hit = true;
				}
			}
		}
    	return hit;
    }

	public void pushPlayer(int whichPlayer , int pushPower , int faceDir) //if hit player will push player by this function
	{
		int playerNumber = whichPlayer;
		if(playerNumber == 1)
		{
			if(world.player1.invisibleTime <= 0)
			{
				world.player1.currPos.x += pushPower * DIR_OFFSETS[faceDir][0];
				world.player1.currPos.y += pushPower * DIR_OFFSETS[faceDir][1];
			}
		}
		if(playerNumber == 2)
		{
			if(world.player2.invisibleTime <= 0)
			{
				world.player2.currPos.x += pushPower * DIR_OFFSETS[faceDir][0];
				world.player2.currPos.y += pushPower * DIR_OFFSETS[faceDir][1];
			}
		}
	}
}