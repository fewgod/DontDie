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
    public int timestop; //init the time stop count to 0
    public int provokeTime;
    
    Sound bgm = Gdx.audio.newSound(Gdx.files.internal("sound/game_bgm.mp3"));
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
    
    public static final int CHOOSE_PLAYER_STATE = 1;
    public static final int START_GAME_STATE = 2;
	public int gameState;
	public boolean chose2Player;
	private Random rand = new Random(); //for random things such as number
	
	private static final int SCORE_BY_TIME = 1;
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
    
    public World(DontDieGame dontdieGame) {
    	world = this;
        this.dontdieGame = dontdieGame; //? why must use this and why it must be 'this.dontdieGame = dontdieGame';
        gameState = CHOOSE_PLAYER_STATE;
        player1 = new Player1(world, 300,150); // create class in class??
        player2 = new Player1(world, 600,150);
        //timestopper_list.add( new Timestopper(world, rand.nextInt(dontdieGame.SCREEN_WIDTH -30) +30 , rand.nextInt(dontdieGame.SCREEN_HEIGHT -30) +30));
        //potion_heal_list.add( new PotionHeal(world, rand.nextBoolean() , rand.nextInt(dontdieGame.SCREEN_WIDTH -30) +30 , rand.nextInt(dontdieGame.SCREEN_HEIGHT -30) +30));
        //spawnSnake(5); for instantly spawn 5 snake
        tStart = System.nanoTime();
        timestop = 0;
        waveNumber = 0;
        nextWaveTime = 1;
        long id = bgm.play(0.62f); //1.0f is for volumn 1.0 for maximum possible
        bgm.setLooping(id, true); 
        maxtimePotion = 11 + rand.nextInt(5);
        maxtimeStopItem = 8 + rand.nextInt(2);
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

    public void somePlayerIsDead() { //or somePlayer being revived 
    	// to let other class call this method to reconfig all enemies to change pattern and change who to chase
    	//wont run unless being call
    	for(int i =0 ; i< snake_list.size() ; i++) //update every snake in snake_list
    	{
    		snake_list.get(i).init(); //force every snake to chase the other after that one is dead;
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
				spawnBall(rand.nextInt(this.waveNumber * rand.nextInt(3)+1)- this.waveNumber + 2);
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
    		potion_heal_list.add( new PotionHeal(world, rand.nextBoolean() , rand.nextInt(dontdieGame.SCREEN_WIDTH -40) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT -40) +40));
    		maxtimePotion = timePotion + 13 + rand.nextInt(7);
    	}
    	if(timeStopItem >= maxtimeStopItem)
    	{
    		timestopper_list.add( new Timestopper(world, rand.nextInt(dontdieGame.SCREEN_WIDTH -40) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT -40) +40));
    		maxtimeStopItem = timePotion + 11 + rand.nextInt(10);
    	}
    }
    
    private void timeSpawnEnemy() // like spawn ball every 10 sec when time is more than 20 sec
    { 
    	if(timeSec >= 17)
    	{
    		if(waveNumber >=3)
    		{
    			if(rand.nextInt(1000) <= waveNumber*1.9) //gradually spawn iron ball by random number
    			{
    				spawnBall(1);
    			}
    		}
    	}
    	if(timeSec >= 20)
    	{
    		if(rand.nextInt(1000) <= waveNumber*2.2) //gradually spawn iron ball by random number
    		{
    			spawnSnake(1);
    		}
    	}
    }
    
    private void randomSpawnEnemy() 
    {
    	if(timestop <= 0)
    	{
    		if(rand.nextInt(1000) <= 11) //gradually spawn snake by random number
    		{
    			spawnSnake(1);
    		}
    		if(waveNumber >= 3)
    		{
    			if(rand.nextInt(1000) <= 8) //gradually spawn iron ball by random number
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
    			ball_list.add( new IronBall(world,DIRECTION_UP,rand.nextInt(dontdieGame.SCREEN_WIDTH) , -50));
    		}
    		if(laneNumber == DIRECTION_DOWN) //spawn from up and go down
    		{
    			ball_list.add( new IronBall(world,DIRECTION_DOWN, rand.nextInt(dontdieGame.SCREEN_WIDTH) , dontdieGame.SCREEN_HEIGHT+50));
    		}
    		if(laneNumber == DIRECTION_LEFT) // spawn from right and go left
    		{
    			ball_list.add( new IronBall(world,DIRECTION_LEFT,dontdieGame.SCREEN_WIDTH+50, rand.nextInt(dontdieGame.SCREEN_HEIGHT)));
    		}
    		if(laneNumber == DIRECTION_RIGHT)//spawn from left and go right
    		{
    			ball_list.add( new IronBall(world,DIRECTION_RIGHT,  -50, rand.nextInt(dontdieGame.SCREEN_HEIGHT)));
    		}
    	}
    }
    
    private void randomSpawnItem() 
    { //gradually spawn item by random number
    	if(rand.nextInt(15000) <= 4) //for healing potion
		{
    		potion_heal_list.add( new PotionHeal(world, rand.nextBoolean() , rand.nextInt(dontdieGame.SCREEN_WIDTH -40) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT -40) +40));
		}
    	if(rand.nextInt(15000) <= 6) //for time stop item
		{
    		timestopper_list.add( new Timestopper(world, rand.nextInt(dontdieGame.SCREEN_WIDTH -40) +40 , rand.nextInt(dontdieGame.SCREEN_HEIGHT -40) +40));
		}
    }
    
    public void timeUpdate () 
    {
    	timestop -= 1; //test time stop count down timer
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
    	/*if(player1.isPlayerDead == true && player2.isPlayerDead == true) //it should memorize higest score of all time
    	{
    		if(lastHiScore > hiScore)
    		{
    			lastHiScore = hiScore;
    		}
    	}*/
    }
    
    public void update(float delta){//for make every object update itself
    	timeUpdate();
    	player1.update(delta);
    	if(provokeTime > 0 && provokeTime % 125 == 0) //while in provoke duration
    	{
    		player1.healPlayer(1);
    	}
    	player2.update(delta);
    	
    	for(int i =0 ; i< attack_list.size() ; i++) //update every attack
    	{
    		attack_list.get(i).update(delta);
    	}
    	
    	for(int i =0 ; i< snake_list.size() ; i++) //update every snake in snake_list
    	{
    		snake_list.get(i).update(delta);
    	}
    	
    	for(int i =0 ; i< ball_list.size() ; i++) //update every ball
    	{
    		ball_list.get(i).update(delta);
    	}
    	
    	for(int i =0 ; i< timestopper_list.size() ; i++) //update every time stopper item in the list
    	{
    		timestopper_list.get(i).update(delta);
    	}
    	for(int i =0 ; i< potion_heal_list.size() ; i++) //update every potion item in the list
    	{
    		potion_heal_list.get(i).update(delta);
    	}
    	
    	scoreFunction();
    	waveSpawnEnemy(waveNumber); //for wave mode
    	timeSpawnEnemy(); // for wave mode
    	randomSpawnEnemy(); // for survival mode
    	randomSpawnItem();//for both mode
    	timeSpawnItem(); //for both mode
    	
    }
}