package com.dontdie.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class World { // what happen to the game will be create here
    public Player1 player1;
    public Player1 player2;
    private DontDieGame dontdieGame;
    public ArrayList<Snake> snake_list = new ArrayList<Snake>();
    
    public int timestop  = 0; //init the time stop count to 0
    public ArrayList<Timestopper> timestopper_list = new ArrayList<Timestopper>();
    public ArrayList<Attack> attack_list = new ArrayList<Attack>();
    
    public World world;
    
    public static final int CHOOSE_PLAYER_STATE = 1;
    public static final int START_GAME_STATE = 2;
	public int gameState;
	public boolean chose2Player;
	private Random rand = new Random(); //for random things such as number
	
	private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_RIGHT = 2;
    private static final int DIRECTION_DOWN = 3;
    private static final int DIRECTION_LEFT = 4;
	
    public World(DontDieGame dontdieGame) {
    	world = this;
        this.dontdieGame = dontdieGame; //? why must use this and why it must be 'this.dontdieGame = dontdieGame';
        gameState = CHOOSE_PLAYER_STATE;
        player1 = new Player1(world, 300,150); // create class in class??
        player2 = new Player1(world, 600,150);
        
        timestopper_list.add( new Timestopper(world, rand.nextInt(600)+50 , rand.nextInt(500)+50));//add 1 timestopper item to the world
        
        //spawnSnake(5);
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

    public void killPlayer()
    {
    	if(player1.isPlayerDead == true)
    	{
    		world.player1 = null;
    	}
    	if(player2.isPlayerDead == true)
    	{
    		world.player2 = null;
    	}
    }

    public void somePlayerIsDead() { //or somePlayer being revived 
    	// to let other class call this method to reconfig all enemies to change pattern and change who to chase
    	//wont run unless being call
    	for(int i =0 ; i< snake_list.size() ; i++) //update every snake in snake_list
    	{
    		snake_list.get(i).init(); //force every snake to chase the other after player1 is dead;
    	}
    }
    
    private void spawnSnake(int numberofSnake) 
    {
    	for(int i =0 ; i< numberofSnake ; i++) 
    	{
    		int laneNumber = rand.nextInt(4)+1;
    		if(laneNumber == DIRECTION_UP)
    		{
    			snake_list.add( new Snake(world, rand.nextInt(900) , 750));
    		}
    		if(laneNumber == DIRECTION_DOWN)
    		{
    			snake_list.add( new Snake(world, rand.nextInt(900) , -50));
    		}
    		if(laneNumber == DIRECTION_LEFT)
    		{
    			snake_list.add( new Snake(world, -50 , rand.nextInt(700)));
    		}
    		if(laneNumber == DIRECTION_RIGHT)
    		{
    			snake_list.add( new Snake(world, 950 , rand.nextInt(700)));
    		}
    	}
    }
    
    private void randomSpawnEnemy() 
    {
    	if(timestop <= 0)
    	{
    		if(rand.nextInt(100) <= 2) //gradually spawn snake by random number
    		{
    			spawnSnake(1);
    		}
    	}
    }
    public void update(float delta) //for make every object update itself
    {
    	player1.update(delta);
    	player2.update(delta);
    	
    	for(int i =0 ; i< attack_list.size() ; i++) //update every snake in snake_list
    	{
    		attack_list.get(i).update(delta);
    	}
    	
    	for(int i =0 ; i< snake_list.size() ; i++) //update every snake in snake_list
    	{
    		snake_list.get(i).update(delta);
    	}
    	for(int i =0 ; i< timestopper_list.size() ; i++) //update every time stopper item in the list
    	{
    		timestopper_list.get(i).update(delta);
    	}
    	randomSpawnEnemy();
    	timestop -= 1; //test time stop count down timer
    }
}