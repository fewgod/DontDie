package com.dontdie.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class World { // what happen to the game will be create here
    private Player1 player1;
    private Player1 player2;
    private DontDieGame dontdieGame;
    public ArrayList<Snake> snake_list = new ArrayList<Snake>();
    public World world;
    
    public static final int CHOOSE_PLAYER_STATE = 1;
    public static final int START_GAME_STATE = 2;
	public int gameState;
	public boolean chose2Player;
 // now can play 2 players if delete those comment symbol
    World(DontDieGame dontdieGame) {
    	world = this;
        this.dontdieGame = dontdieGame; //? why must use this and why it must be 'this.dontdieGame = dontdieGame';
        gameState = CHOOSE_PLAYER_STATE;
        player1 = new Player1(400,400); // create class in class??
        player2 = new Player1(400,100);
        for(int i = 0 ; i<50 ; i++)  //add i number of snake 
        {
        	snake_list.add( new Snake(world,i,100)); //add 1 snake to snake_list at ... position
        }
    }
 
    Player1 getPlayer1() {
        return player1;
    }
    Player1 getPlayer2() {
        return player2;
    }
    Snake getSnake(int i) 
    { //return type is snake
        return snake_list.get(i);
    }
    World getWorld() 
    { //
        return world;
    }
    
    public void update(float delta) //for make every object update itself
    {
    	for(int i =0 ; i< snake_list.size() ; i++) //update every snake in snake_list
    	{
    		snake_list.get(i).update(delta);
    	}
    	//snake1.update(delta);
    }
}