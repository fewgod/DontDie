package com.dontdie.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class World { // what happen to the game will be create here
    public Player1 player1;
    public Player1 player2;
    private DontDieGame dontdieGame;
    public int timeStop; //init the time stop count to 0
    public int provokeTime;
    
    Sound bgm = Gdx.audio.newSound(Gdx.files.internal("sound/game_bgm.mp3"));
    Sound game_over = Gdx.audio.newSound(Gdx.files.internal("sound/game_over.mp3"));
    Sound game_start = Gdx.audio.newSound(Gdx.files.internal("sound/game_start.wav"));
    Sound enemydead = Gdx.audio.newSound(Gdx.files.internal("sound/enemy_dead.wav"));
    Sound swordswing = Gdx.audio.newSound(Gdx.files.internal("sound/sword_swing.wav"));
    Sound magiccasting = Gdx.audio.newSound(Gdx.files.internal("sound/magic_casting.wav"));
    Sound playergothit = Gdx.audio.newSound(Gdx.files.internal("sound/player_got_hit.wav"));
    Sound playerdead = Gdx.audio.newSound(Gdx.files.internal("sound/player_dead.mp3"));
    Sound heal = Gdx.audio.newSound(Gdx.files.internal("sound/heal.wav"));
    Sound provoke = Gdx.audio.newSound(Gdx.files.internal("sound/provoke.mp3"));
    Sound pickitem = Gdx.audio.newSound(Gdx.files.internal("sound/pick_item.wav"));
    
    public ArrayList<Snake> snake_list = new ArrayList<Snake>();
    public ArrayList<IronBall> ball_list = new ArrayList<IronBall>();
    public ArrayList<Timestopper> timestopper_list = new ArrayList<Timestopper>();
    public ArrayList<PotionHeal> potion_heal_list = new ArrayList<PotionHeal>();
    public ArrayList<Attack> attack_list = new ArrayList<Attack>();
    public int score;
    public static int hiScore;
    private float gainingScoreTime;
    
    public World world;
    
    public static final int STATE_INSTRUCTION = 1;
    public static final int STATE_START_GAME = 2;
    public static final int STATE_GAME_OVER = 3;
	public int gameState;
	public boolean chose2Player;
	private Random rand = new Random(); //for random things such as number
	
	private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_RIGHT = 2;
    private static final int DIRECTION_DOWN = 3;
    private static final int DIRECTION_LEFT = 4;
	
    public int waveNumber;
    private long nextWaveTime;
    
    long tStart;
    long tEnd;
    long tRes;
    long timeSec;
    int timePotion;
    int timeStopItem;
    int maxtimePotion;
    int maxtimeStopItem;
	public boolean revivingSomeone;
    
    public World(DontDieGame dontdieGame , int worldState) {
    	world = this;
        this.dontdieGame = dontdieGame;
        gameState = worldState;
        player1 = new Player1(world, 300,150);
        player2 = new Player1(world, 600,150);
        if(gameState == STATE_START_GAME) // if finished reading instruction or after restart game will go to this state
        {
        	game_start.play(0.8f);
        	tStart = System.nanoTime();
        	timeStop = 0;
        	waveNumber = 0;
        	nextWaveTime = 1;
        }
        maxtimePotion = 11 + rand.nextInt(5);
        maxtimeStopItem = 8 + rand.nextInt(3);
        long id = bgm.play(0.55f); //1.0f is for volumn 1.0 for maximum possible
        bgm.setLooping(id, true); 
    }
 
    Player1 getPlayer1() {
        return player1;
    }
    Player1 getPlayer2() {
        return player2;
    }
    Snake getSnake(int i) 
    {
        return snake_list.get(i);
    }
    
    public void somePlayerIsDead() { //or somePlayer being revived 
    	// to let other class call this method to reconfig all enemies to change pattern and change who to chase
    	//wont run unless being call
    	for(int i =0 ; i< snake_list.size() ; i++) //update every snake in snake_list
    	{
    		snake_list.get(i).init(); //force every snake to chase the other after that one is dead;
    	}
    }
    
    private void provokeSkillPlayer1() 
    {
    	if(world.provokeTime > 0 && world.provokeTime % 125 == 0) //while in provoke duration
    	{
    		player1.healPlayer(1);
    	}
    }
    
    private void waveSpawnEnemy(int waveNumber) 
    {
		if(timeSec == nextWaveTime) 
		{
			long currStartWaveTime = timeSec; //time that start this wave
			int numberofEnemy = 2*this.waveNumber + rand.nextInt(this.waveNumber +1) +1;
			spawnSnake(numberofEnemy -1);
			if(this.waveNumber % 3 == 2) 
			{
				spawnBall(rand.nextInt(this.waveNumber * rand.nextInt(3)+1)- this.waveNumber + 1);
			}
			nextWaveTime = currStartWaveTime + 2 * numberofEnemy + this.waveNumber *3; //if player use too much time will go to next wave
			world.waveNumber += 1;
			score += (this.waveNumber-1)*20;
		}
    }
    
    private void timeSpawnItem()
    { 
    	timePotion = Math.round(timeSec);
    	timeStopItem = Math.round(timeSec);
    	if(timePotion >= maxtimePotion)
    	{
    		potion_heal_list.add( new PotionHeal(world, rand.nextBoolean() , rand.nextInt(dontdieGame.SCREEN_WIDTH -50) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT +40) -30));
    		maxtimePotion = timePotion + 13 + rand.nextInt(8);
    	}
    	if(timeStopItem >= maxtimeStopItem)
    	{
    		timestopper_list.add( new Timestopper(world, rand.nextInt(dontdieGame.SCREEN_WIDTH -50) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT +40) -30));
    		maxtimeStopItem = timePotion + 11 + rand.nextInt(12);
    	}
    }
    
    private void timeSpawnEnemy() // like spawn ball every 10 sec when time is more than 20 sec
    { 
    	if(timeSec >= 17)
    	{
    		if(waveNumber >= 3)
    		{
    			if(rand.nextInt(1400) <= waveNumber*1.2) //gradually spawn iron ball by random number
    			{
    				spawnBall(1);
    			}
    		}
    	}
    	if(timeSec >= 20)
    	{
    		if(rand.nextInt(1000) <= waveNumber*2.1) //gradually spawn iron ball by random number
    		{
    			spawnSnake(1);
    		}
    	}
    }
    
    private void randomSpawnEnemy() 
    {
    	if(timeStop <= 0)
    	{
    		if(rand.nextInt(1000) <= 11) //gradually spawn snake by random number
    		{
    			spawnSnake(1);
    		}
    		if(waveNumber >= 3)
    		{
    			if(rand.nextInt(1150) <= 3) //gradually spawn iron ball by random number
    			{
    				spawnBall(1);
    			}
    		}
    	}
    }
    
    private void spawnSnake(int numberofSnake) 
    {
    	for(int i =0 ; i< numberofSnake ; i++) 
    	{
    		int laneNumber = rand.nextInt(4)+1;
    		if(laneNumber == DIRECTION_UP)
    		{
    			snake_list.add( new Snake(world, rand.nextInt(dontdieGame.SCREEN_WIDTH) , dontdieGame.SCREEN_HEIGHT +50));
    		}
    		if(laneNumber == DIRECTION_DOWN)
    		{
    			snake_list.add( new Snake(world, rand.nextInt(dontdieGame.SCREEN_WIDTH) , -50));
    		}
    		if(laneNumber == DIRECTION_LEFT)
    		{
    			snake_list.add( new Snake(world, -50 , rand.nextInt(dontdieGame.SCREEN_HEIGHT)));
    		}
    		if(laneNumber == DIRECTION_RIGHT)
    		{
    			snake_list.add( new Snake(world, dontdieGame.SCREEN_WIDTH +50 , rand.nextInt(dontdieGame.SCREEN_HEIGHT)));
    		}
    	}
    }
    
    private void spawnBall(int numberofBall) 
    {
    	for(int i =0 ; i< numberofBall ; i++) 
    	{
    		int laneNumber = rand.nextInt(4)+1;
    		if(laneNumber == DIRECTION_UP) //spawn from down and go up
    		{
    			ball_list.add( new IronBall(world,DIRECTION_UP,rand.nextInt(dontdieGame.SCREEN_WIDTH) , -30));
    		}
    		if(laneNumber == DIRECTION_DOWN) //spawn from up and go down
    		{
    			ball_list.add( new IronBall(world,DIRECTION_DOWN, rand.nextInt(dontdieGame.SCREEN_WIDTH) , dontdieGame.SCREEN_HEIGHT +30));
    		}
    		if(laneNumber == DIRECTION_LEFT) // spawn from right and go left
    		{
    			ball_list.add( new IronBall(world,DIRECTION_LEFT,dontdieGame.SCREEN_WIDTH +30, rand.nextInt(dontdieGame.SCREEN_HEIGHT)));
    		}
    		if(laneNumber == DIRECTION_RIGHT)//spawn from left and go right
    		{
    			ball_list.add( new IronBall(world,DIRECTION_RIGHT, -30, rand.nextInt(dontdieGame.SCREEN_HEIGHT)));
    		}
    	}
    }
    
    private void randomSpawnItem() 
    { //gradually spawn item by random number
    	if(rand.nextInt(16000) <= 4) //for healing potion
		{
    		potion_heal_list.add( new PotionHeal(world, rand.nextBoolean() , rand.nextInt(dontdieGame.SCREEN_WIDTH -50) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT +40) -30));
		}
    	if(rand.nextInt(16000) <= 5) //for time stop item
		{
    		timestopper_list.add( new Timestopper(world, rand.nextInt(dontdieGame.SCREEN_WIDTH -50) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT +40) -30));
		}
    }
    
    private void isItGameOver()  //for make it game over and game over screen appear
    {
    	if(player1.isPlayerDead == true && player2.isPlayerDead == true)
    	{
    		bgm.stop();
    		game_over.play(0.8f);
    		gameState = STATE_GAME_OVER;
    	}
    }
    
    public void timeUpdate () 
    {
    	timeStop -= 1; 
    	provokeTime -=1;
    	tEnd = System.nanoTime();
    	if(player1.isPlayerDead == false || player2.isPlayerDead == false) { //time will only count if some of player is still alive
    		tRes = tEnd - tStart;
    	}
    	timeSec = tRes/1000000000; //make time unit from nano second into second
    }
    
    public void scoreFunction() 
    {
    	if(player1.isPlayerDead == false || player2.isPlayerDead == false)
    	{
    		gainingScoreTime += 1;
    		if(gainingScoreTime == 60)
    		{
    			gainingScoreTime =0;
    			score +=1;
    		}
    	}
    	if(score > hiScore)
    	{
    		hiScore = score;
    	}
    }
    
	private void updateItemandEnemy(float delta)
    {
    	for(int i =0 ; i< attack_list.size() ; i++) //update every attack
    	{
    		attack_list.get(i).update(delta);
    	}
    	
    	for (Snake snake : snake_list) { //update every snake in snake_list
    		snake.update(delta);
    	}
    	
    	for(int i =0 ; i< ball_list.size() ; i++) //update every ball
    	{
    		ball_list.get(i).update(delta);
    	}
    	
    	for(int i =0 ; i< timestopper_list.size() ; i++) //update every time stop item
    	{
    		timestopper_list.get(i).update(delta);
    	}
    	
    	for(int i =0 ; i< potion_heal_list.size() ; i++) //update every potion
    	{
    		potion_heal_list.get(i).update(delta);
    	}
    }
    
    public void update(float delta)//for make every object update itself
	{
    	if(gameState == STATE_START_GAME || gameState == STATE_GAME_OVER)
    	{	
    		timeUpdate();
    		player1.update(delta);
    		provokeSkillPlayer1();
    		player2.update(delta);
    	
    		updateItemandEnemy(delta);
    	
    		scoreFunction();
    		waveSpawnEnemy(waveNumber); //for wave mode
    		timeSpawnEnemy(); // for wave mode
    		randomSpawnEnemy(); // for survival mode
    		randomSpawnItem();//for both mode
    		timeSpawnItem(); //for both mode
    	}
    	if(gameState == STATE_START_GAME) //to use this function only once
    	{
    		isItGameOver();
    	}
    }
}