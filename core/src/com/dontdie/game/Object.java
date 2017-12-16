package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Object {

    public Vector2 currPos;
    
  //snake image size is 22*41
  	private float IMAGE_SIZE_X;
  	private float IMAGE_SIZE_Y;
  	private float GET_CENTER_X;
  	private float GET_CENTER_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
    
    public Object(int x, int y ,float img_size_x ,float img_size_y) { //when first init give spawn position to player 1
        currPos = new Vector2(x,y);
        IMAGE_SIZE_X = img_size_x;
        IMAGE_SIZE_Y = img_size_y;
      	GET_CENTER_X = IMAGE_SIZE_X/2;
      	GET_CENTER_Y = IMAGE_SIZE_Y/2;
        currCenter_X = currPos.x + GET_CENTER_X;
        currCenter_Y = currPos.y + GET_CENTER_Y;

    }
 
    public void setCurrPos(Vector2 pos) //to set current position of the object that use this class
    {
    	currPos = pos;
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
}