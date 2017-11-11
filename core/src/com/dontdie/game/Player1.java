package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Player1 {
	public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_DOWN = 3;
    public static final int DIRECTION_LEFT = 4;
    public static final int DIRECTION_STILL = 0;
    private int PLAYER_MOVE_SPEED = 5;
    private Vector2 currPos;
 
    private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0},
        {0,-1},
        {1,0},
        {0,1},
        {-1,0}
    };
    
    public Player1(int x, int y) { //when first init give spawn position to player 1
        currPos = new Vector2(x,y);
    }    
 
    public Vector2 getPosition() { // for other class to get current position of player1
        return currPos;    
    }
    
    public void move(int dir) { 
    	currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][0];
        currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][1];
        // first [dir] is chose which {,} to use, second [] chose first para or second para in {,}
    }
}