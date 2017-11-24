package com.dontdie.game;

import com.badlogic.gdx.math.Vector2;

public class Player1 {

	public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_DOWN = 3;
    public static final int DIRECTION_LEFT = 4;
    public static final int DIRECTION_STILL = 0;
    public int faceDir;
    private int PLAYER_MOVE_SPEED = 5;
    public Vector2 currPos;
    private World world;
    private double MAX_HITPOINTS = 15;
    private double hitPoints;
    private double hpPercentage; //for draw hp bar
    public long hpScale;
    public int attackCoolDown;
    public int skillCastingTime;
    private int SKILL_REVIVE_MAX_CAST_TIME = 200;
    private int SKILL_FIREBALL_MAX_CAST_TIME = 40;
    private int SKILL_SLOW_FIREBALL_MAX_CAST_TIME = 112;
    private int SKILL_PROVOKE_MAX_CAST_TIME = 33;
    public int maxSkillCastTime;
    public int invisibleTime;
    public int slowDownTime;
    public int provokeCoolDown;
    
  //snake image size is 22*41
  	private float IMAGE_SIZE_X = 22;
  	private float IMAGE_SIZE_Y = 41;
  	private float GET_CENTER_X = IMAGE_SIZE_X/2;
  	private float GET_CENTER_Y = IMAGE_SIZE_Y/2;
  	private float IMAGE_RADIUS_X = GET_CENTER_X; //just different name for easier use and understanding
  	private float IMAGE_RADIUS_Y = GET_CENTER_Y;
  	private float currCenter_X;
  	private float currCenter_Y;
	public boolean isPlayerDead;
    
    private static final int [][] DIR_OFFSETS = new int [][] { // for use with move method
        {0,0},
        {0,1},
        {1,0},
        {0,-1},
        {-1,0}
    };
    
    public Player1(World world , int x, int y) { //when first init give spawn position to player 1
    	this.world = world;
    	faceDir = DIRECTION_UP;
        currPos = new Vector2(x,y);
        currCenter_X = currPos.x + GET_CENTER_X;
        currCenter_Y = currPos.y + GET_CENTER_Y;
        
        isPlayerDead = false;
        attackCoolDown = 0;
        hitPoints = MAX_HITPOINTS;
        hpPercentage = (hitPoints / MAX_HITPOINTS)*100 ;
        hpScale = Math.round(hpPercentage/10);
        invisibleTime = 0;
        slowDownTime = 0;
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
    
    public void takeDamage(int damageReceive) {
    	skillCastingTime = 0;
    	if(invisibleTime <=0)
    	{
    		hitPoints -= damageReceive;
    		if(hitPoints <= 0)
    		{
    			hitPoints = 0;
    			world.playerdead.play(0.8f);
    		}
    		if(hitPoints > 0)
    		{
    			world.playergothit.play(0.6f);
    		}
    		checkIfAlive();
    		invisibleTime = 15;
    	}
    }
    
    public void healPlayer(int healReceive) {
    	if(isPlayerDead == false)
    	{
    		hitPoints += healReceive;
    		if(hitPoints >= MAX_HITPOINTS)
        	{
        		hitPoints = MAX_HITPOINTS;
        	}
    	}
    }
    
    private void revivePlayer(int whoToRevive) 
    {
    	world.magiccasting.play(0.75f);
    	if(whoToRevive == 1)
    	{
    		world.player1.isPlayerDead = false;
			world.player1.healPlayer(5);
			world.player1.invisibleTime = 200;
    	}
    	if(whoToRevive == 2)
    	{
    		world.player2.isPlayerDead = false;
    		world.player2.healPlayer(5);
    		world.player2.invisibleTime = 200;
    	}
		world.somePlayerIsDead();
    }
    
    public void castSkillRevive(int whichPlayerIsCasting)
    {
    	maxSkillCastTime = SKILL_REVIVE_MAX_CAST_TIME;
    	faceDir = DIRECTION_DOWN;
    	skillCastingTime += 1;
    	if(skillCastingTime >= maxSkillCastTime)
    	{
    		if(whichPlayerIsCasting == 1) //if player 1 is casting
    		{
    			revivePlayer(2);
    		}
    		if(whichPlayerIsCasting == 2) //if player 2 is casting
    		{
    			revivePlayer(1);
    		}
    		skillCastingTime = 0;
    	}
    }

    public void castSkillFireBall(int whichPlayerIsCasting)
    {
    	maxSkillCastTime = SKILL_FIREBALL_MAX_CAST_TIME;
    	skillCastingTime += 1;
    	if(skillCastingTime >= maxSkillCastTime)
    	{
    		if(whichPlayerIsCasting == 1) //if player 1 is casting
    		{
    			world.attack_list.add( new Attack(world, world.player1.faceDir , 2, world.player1.getCurrentXPos() , world.player1.getCurrentYPos()));
    		}
    		if(whichPlayerIsCasting == 2) //if player 2 is casting
    		{
    			world.attack_list.add( new Attack(world, world.player2.faceDir , 2, world.player2.getCurrentXPos() , world.player2.getCurrentYPos()));
    		}
    		skillCastingTime = 0;
    	}
    }
    public void castSkillSlowFireBall(int whichPlayerIsCasting)
    {
    	maxSkillCastTime = SKILL_SLOW_FIREBALL_MAX_CAST_TIME;
    	skillCastingTime += 1;
    	if(skillCastingTime >= maxSkillCastTime)
    	{
    		world.magiccasting.play(0.67f);
    		if(whichPlayerIsCasting == 1) //if player 1 is casting
    		{
    			world.attack_list.add( new Attack(world, world.player1.faceDir , 3, world.player1.getCurrentXPos() , world.player1.getCurrentYPos()));
    		}
    		if(whichPlayerIsCasting == 2) //if player 2 is casting
    		{
    			world.attack_list.add( new Attack(world, world.player2.faceDir , 3, world.player2.getCurrentXPos() , world.player2.getCurrentYPos()));
    		}
    		skillCastingTime = 0;
    	}
    }
    
    public void castSkillProvoke()
    {
    	maxSkillCastTime = SKILL_PROVOKE_MAX_CAST_TIME;
    	skillCastingTime += 1;
    	if(skillCastingTime >= maxSkillCastTime)
    	{
    		world.provokeTime = 1300;
    		provokeCoolDown = 4000;
    		world.provoke.play(0.80f);
    		for(int i =0 ; i< world.snake_list.size() ; i++) //update every snake in snake_list
        	{
        		world.snake_list.get(i).init(); //force every snake to chase the other after that one is dead;
        	}
    		skillCastingTime = 0;
    	}
    }
    
    public void checkIfAlive() {
    	if(hitPoints <= 0)
    	{
    		isPlayerDead = true;
    		world.somePlayerIsDead();
    	}
    	else
    	{
    		isPlayerDead = false;
    	}
    }
    
    public void move(int dir) { 
    	if(slowDownTime <=0 && skillCastingTime == 0) //if dont cast skill and dont slow
    	{
    		currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][0];
    		currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[dir][1];
    	}
    	else if(slowDownTime > 0 && skillCastingTime == 0) // if slow only
    	{
    		currPos.x += (PLAYER_MOVE_SPEED/2) * DIR_OFFSETS[dir][0];
    		currPos.y += (PLAYER_MOVE_SPEED/2) * DIR_OFFSETS[dir][1];
    	}
    	else // if casting skill player wont be able to move
    	{
    		currPos.x += 0 * DIR_OFFSETS[dir][0];
    		currPos.y += 0 * DIR_OFFSETS[dir][1];
    	}
    	currCenter_X = currPos.x + GET_CENTER_X;
    	currCenter_Y = currPos.y + GET_CENTER_Y;
        // first [dir] is chose which {,} to use, second [] chose first para or second para in {,}
    }
    
    public void update(float delta)
    {
    	if(currCenter_X < 0) // prevent player walk off screen
    	{
    		currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[2][0];
    	}
    	if(currCenter_X > DontDieGame.SCREEN_WIDTH - 0)
    	{
    		currPos.x += PLAYER_MOVE_SPEED * DIR_OFFSETS[4][0];
    	}
    	if(currCenter_Y < 0)
    	{
    		currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[1][1];
    	}
    	if(currCenter_Y > DontDieGame.SCREEN_HEIGHT )
    	{
    		currPos.y += PLAYER_MOVE_SPEED * DIR_OFFSETS[3][1];
    	}
    	hpPercentage = (hitPoints / MAX_HITPOINTS)*100 ;
        hpScale = Math.round(hpPercentage/10);
        attackCoolDown -= 1;
        slowDownTime -= 1;
        invisibleTime -= 1;
        provokeCoolDown -=1;
    	getCurrentXPos();
    	getCurrentYPos();
    }
}