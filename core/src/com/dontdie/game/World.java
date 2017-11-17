package com.dontdie.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class World { // what happen to the game will be create here
    private Player1 player1;
    private Player1 player2;
    private DontDieGame dontdieGame;
    public ArrayList<Snake> snake_list = new ArrayList<Snake>();
    
    public int timestop  = 0; //init the time stop count to 0
    public ArrayList<Timestopper> timestopper_list = new ArrayList<Timestopper>();
    
    public World world;
    
    public static final int CHOOSE_PLAYER_STATE = 1;
    public static final int START_GAME_STATE = 2;
	public int gameState;
	public boolean chose2Player;
	private Random rand = new Random(); //for random things such as number
	
    public World(DontDieGame dontdieGame) {
    	world = this;
        this.dontdieGame = dontdieGame; //? why must use this and why it must be 'this.dontdieGame = dontdieGame';
        gameState = CHOOSE_PLAYER_STATE;
        player1 = new Player1(world, 400,400); // create class in class??
        player2 = new Player1(world, 400,100);
        
        timestopper_list.add( new Timestopper(world, rand.nextInt(600)+50 , rand.nextInt(500)+50));//add 1 timestop item to the world
        for(int i = 0 ; i< rand.nextInt(20) ; i++)  //add i number of snake 
        {
        	snake_list.add( new Snake(world, rand.nextInt(600)+50 , rand.nextInt(500)+50)); //add 1 snake to snake_list at random position
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

    public void removePlayer() { // test not working
    	snake_list.remove(0);//just to make it doen't give blank method
    }
    
    public void update(float delta) //for make every object update itself
    {
    	for(int i =0 ; i< snake_list.size() ; i++) //update every snake in snake_list
    	{
    		snake_list.get(i).update(delta);
    	}
    	for(int i =0 ; i< timestopper_list.size() ; i++) //update every time stopper item in the list
    	{
    		timestopper_list.get(i).update(delta);
    	}
    	timestop -= 1; //test time stop count down timer
    }
}