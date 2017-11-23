package com.dontdie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer { //what happen to the game world will be draw here
	BitmapFont font = new BitmapFont();
	
	private DontDieGame dontdieGame;
	private SpriteBatch batch;
	private World world;
	private Player1 player1;
	private Player1 player2;
	//image
	private Texture instructionImg;
	private Texture backgroundImg;
	private Texture gameOverImg;
	
	private Texture player1RightImg;
	private Texture player1LeftImg;
	private Texture player1UpImg;
	private Texture player1DownImg;
	private Texture player2RightImg;
	private Texture player2LeftImg;
	private Texture player2UpImg;
	private Texture player2DownImg;
	private Texture player1KO_Img;
	private Texture player2KO_Img;
	//health bar images
	private Texture healthBar1Img;
	private Texture healthBar2Img;
	
	private Texture attackLeft_Img;
	private Texture attackRight_Img;
	private Texture attackDown_Img;
	private Texture attackUp_Img;
	private Texture fireball_Img;
	private Texture fireball_Big_Img;
	private Texture provoke_Img;
	
	private Texture skillcasting1_Img;
	private Texture skillcasting2_Img;
	private Texture revive1_Img;
	private Texture revive2_Img;
	
	private Texture timestopperImg;
	private Texture potionHealOneImg;
	private Texture potionHealAllImg;
	
	private Texture snakeImg;
	private Texture ballImg;
	
	public WorldRenderer(DontDieGame dontdieGame, World world) {
		this.dontdieGame = dontdieGame;
		batch = dontdieGame.batch;

		this.world = world;
		
		//images here//
		instructionImg = new Texture("Instruction.png");
		backgroundImg = new Texture("Background.jpg");
		gameOverImg = new Texture("GameOver.png");
		player1RightImg = new Texture("Player1_Right.png");
		player1LeftImg = new Texture("Player1_Left.png");
		player1UpImg = new Texture("Player1_Up.png");
		player1DownImg = new Texture("Player1_Down.png");
		player1KO_Img = new Texture("Player1_KO.png");
		player2RightImg = new Texture("Player2_Right.png");
		player2LeftImg = new Texture("Player2_Left.png");
		player2UpImg = new Texture("Player2_Up.png");
		player2DownImg = new Texture("Player2_Down.png");
		player2KO_Img = new Texture("Player2_KO.png");
		
		timestopperImg = new Texture("stopwatch.png");
		potionHealOneImg = new Texture("potion_healOne.png");
		potionHealAllImg = new Texture("potion_healAll.png");
		
		attackLeft_Img = new Texture("sword_ani_left.png");
		attackRight_Img = new Texture("sword_ani_right.png");
		attackUp_Img = new Texture("sword_ani_up.png");
		attackDown_Img = new Texture("sword_ani_down.png");
		fireball_Img = new Texture("fireball.png");
		fireball_Big_Img = new Texture("fireballBig.png");
		provoke_Img = new Texture("provoke.png");
		
		skillcasting1_Img = new Texture("skillcasting1.png");
		skillcasting2_Img = new Texture("skillcasting2.png");
		revive1_Img = new Texture("revive1.png");
		revive2_Img = new Texture("revive2.png");
		
		snakeImg = new Texture ("snake.png");
		ballImg = new Texture ("ball.png");
		
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
	}
	
	private void renderSkillCastAnimation(Vector2 player1Pos , Vector2 player2Pos)
	{
		 if(player1.skillCastingTime >= player1.maxSkillCastTime/10 && player1.skillCastingTime < player1.maxSkillCastTime/2.5)
	        {
	        	batch.draw(skillcasting1_Img, player1Pos.x-15, player1Pos.y-6);
	        }
	        else if(player1.skillCastingTime >= player1.maxSkillCastTime/2.5 && player1.skillCastingTime < player1.maxSkillCastTime)
	        {
	        	batch.draw(skillcasting2_Img, player1Pos.x-17, player1Pos.y-8);
	        }
	        if(player2.skillCastingTime >= player2.maxSkillCastTime/10 && player2.skillCastingTime < player2.maxSkillCastTime/2.5)
	        {
	        	batch.draw(skillcasting1_Img, player2Pos.x-15, player2Pos.y-6);
	        }
	        else if(player2.skillCastingTime >= player2.maxSkillCastTime/2.5 && player2.skillCastingTime < player2.maxSkillCastTime)
	        {
	        	batch.draw(skillcasting2_Img, player2Pos.x-17, player2Pos.y-8);
	        }
	        if(world.provokeTime > 100 && player1.isPlayerDead == false)
	        {
	        	batch.draw(provoke_Img, player1Pos.x-15, player1Pos.y-6);
	        }
	}
	
	private void renderPlayers(Vector2 player1Pos , Vector2 player2Pos) 
	{
		if(player1.isPlayerDead == false)
        {
        	//render player1 face
        	if(player1.faceDir == player1.DIRECTION_RIGHT)
        	{
        		batch.draw(player1RightImg, player1Pos.x, player1Pos.y);
        	}
        	if(player1.faceDir == player1.DIRECTION_LEFT)
        	{
        		batch.draw(player1LeftImg, player1Pos.x, player1Pos.y);
        	}
        	if(player1.faceDir == player1.DIRECTION_UP)
        	{
        		batch.draw(player1UpImg, player1Pos.x, player1Pos.y);
        	}
        	if(player1.faceDir == player1.DIRECTION_DOWN)
        	{
        		batch.draw(player1DownImg, player1Pos.x, player1Pos.y);
        	}
        }
        else
        {
        	batch.draw(player1KO_Img, player1Pos.x, player1Pos.y);
        }
        if(player2.isPlayerDead == false)
        {
        	//render player2 face
        	if(player2.faceDir == player2.DIRECTION_RIGHT)
        	{
        		batch.draw(player2RightImg, player2Pos.x, player2Pos.y);
        	}
        	if(player2.faceDir == player2.DIRECTION_LEFT)
        	{
        		batch.draw(player2LeftImg, player2Pos.x, player2Pos.y);
        	}
        	if(player2.faceDir == player2.DIRECTION_UP)
        	{
        		batch.draw(player2UpImg, player2Pos.x, player2Pos.y);
        	}
        	if(player2.faceDir == player2.DIRECTION_DOWN)
        	{
        		batch.draw(player2DownImg, player2Pos.x, player2Pos.y);
        	}
        }
        else
        {
        	batch.draw(player2KO_Img, player2Pos.x, player2Pos.y);
        }
	}
	
	private void renderReviveAnimation(Vector2 player1Pos , Vector2 player2Pos) 
	{
		if(world.revivingSomeone == true)
        {
        	if(player1.skillCastingTime >= player1.maxSkillCastTime/6 && player1.skillCastingTime < player1.maxSkillCastTime/2.5)
        	{
        		batch.draw(revive1_Img, player2Pos.x-5, player2Pos.y-6);
        	}
        	else if(player1.skillCastingTime >= player1.maxSkillCastTime/2.5 && player1.skillCastingTime < player1.maxSkillCastTime)
        	{
        		batch.draw(revive2_Img, player2Pos.x-5, player2Pos.y-10);
        	}
        	if(player2.skillCastingTime >= player2.maxSkillCastTime/6 && player2.skillCastingTime < player2.maxSkillCastTime/2.5)
        	{
        		batch.draw(revive1_Img, player1Pos.x-5, player1Pos.y-6);
        	}
        	else if(player2.skillCastingTime >= player2.maxSkillCastTime/2.5 && player2.skillCastingTime < player2.maxSkillCastTime)
        	{
        		batch.draw(revive2_Img, player1Pos.x-5, player1Pos.y-10);
        	}
        }
	}

	private void renderAttackAnimation(Vector2 player1Pos , Vector2 player2Pos) 
	{
		for(int i =0;i< world.attack_list.size(); i++) //draw every currently available attack
    	{
        	Vector2 attack_i = world.attack_list.get(i).getPosition();
        	if(world.attack_list.get(i).attackType == 1) //draw normal attack
        	{
        		if(world.attack_list.get(i).getCurrentFace() == Attack.DIRECTION_LEFT)
        		{	
        			batch.draw(attackLeft_Img, attack_i.x, attack_i.y); //change from draw current center to draw current position
        		}
        		if(world.attack_list.get(i).getCurrentFace() == Attack.DIRECTION_RIGHT)
        		{	
        			batch.draw(attackRight_Img, attack_i.x, attack_i.y); //change from draw current center to draw current position
        		}
        		if(world.attack_list.get(i).getCurrentFace() == Attack.DIRECTION_UP)
        		{	
        			batch.draw(attackUp_Img, attack_i.x, attack_i.y); //change from draw current center to draw current position
        		}
        		if(world.attack_list.get(i).getCurrentFace() == Attack.DIRECTION_DOWN)
        		{	
        			batch.draw(attackDown_Img, attack_i.x, attack_i.y); //change from draw current center to draw current position
        		}
        	}
        	else if(world.attack_list.get(i).attackType == 2) //draw small fireball
        	{
        		batch.draw(fireball_Img, attack_i.x , attack_i.y +5);
        	}
        	else
        	{
        		batch.draw(fireball_Big_Img, attack_i.x -10, attack_i.y); //draw big fireball
        	}
    	}
	}
	
	private void renderItem(Vector2 player1Pos , Vector2 player2Pos)
	{
		for(int i =0;i< world.timestopper_list.size(); i++) //draw every timestopper item in timestopper_list 
    	{
        	batch.draw(timestopperImg, world.timestopper_list.get(i).getPosition().x, world.timestopper_list.get(i).getPosition().y);
    	}
        for(int i =0;i< world.potion_heal_list.size(); i++) //draw every potion item in potion_healOne_list
    	{
        	if(world.potion_heal_list.get(i).isThisItemHealOne == true)
        	{
        		batch.draw(potionHealOneImg, world.potion_heal_list.get(i).getPosition().x, world.potion_heal_list.get(i).getPosition().y);
        	}
        	else
        	{
        		batch.draw(potionHealAllImg, world.potion_heal_list.get(i).getPosition().x, world.potion_heal_list.get(i).getPosition().y);
        	}
    	}
	}
	
	private void renderEnemy(Vector2 player1Pos , Vector2 player2Pos) 
	{
		for(int i =0;i< world.snake_list.size(); i++) //draw every snake in snake_list
    	{
        	batch.draw(snakeImg, world.snake_list.get(i).getPosition().x, world.snake_list.get(i).getPosition().y);
    	}
        for(int i =0 ; i< world.ball_list.size() ; i++) //update every ball
    	{
        	batch.draw(ballImg, world.ball_list.get(i).getPosition().x, world.ball_list.get(i).getPosition().y);
    	}
        if(player1.provokeCoolDown > 0)
        {
        	batch.draw(provoke_Img, 18,39);
        	font.draw(batch,"Ready in: " +player1.provokeCoolDown/60 +"  sec",65,65);
        }
	}
	
	private void renderHealthBar() 
	{
		healthBar1Img = new Texture("healthbar_"+player1.hpScale+".png");
        healthBar2Img = new Texture("healthbar_"+player2.hpScale+".png");
        batch.draw(healthBar1Img, 10 ,15);
        batch.draw(healthBar2Img, 610 ,15);
	}
	
	private void renderText() 
	{
		font.draw(batch,"Wave: " +world.waveNumber,DontDieGame.SCREEN_WIDTH -100,DontDieGame.SCREEN_HEIGHT - 25);
        font.draw(batch,"Time: " +world.timeSec + "  sec",DontDieGame.SCREEN_WIDTH -100,DontDieGame.SCREEN_HEIGHT - 50);
        font.draw(batch,"Score: " +world.score, 30 , DontDieGame.SCREEN_HEIGHT - 25);
        font.draw(batch,"Hi-Score: " +world.hiScore,30 ,DontDieGame.SCREEN_HEIGHT - 50);
	}
	
	public void render(float delta) { // this draw game animation to window
		world.update(delta); //this make the world update which will order enemy and object to update afterward
        SpriteBatch batch = dontdieGame.batch; //what is this line??
        batch.begin();
        batch.draw(backgroundImg, 0, 0);
       
        Vector2 player1Pos = player1.getPosition(); //get position of player 1 to draw it
        Vector2 player2Pos = player2.getPosition(); //get position of player 2 to draw it
        
        //when draw:draw currPos position, but when calculate, use CURR_CENTER so hit box of that object will always be at center of image
        
        //draw skill casting animation must behind actor
        renderSkillCastAnimation(player1Pos,player2Pos);
        renderPlayers(player1Pos,player2Pos);
        
        //draw revivng animation must be in front of actor
        renderReviveAnimation(player1Pos,player2Pos);
        
        //draw every currently available attack
        renderAttackAnimation(player1Pos,player2Pos);
        
        /////draw item////
        renderItem(player1Pos,player2Pos);
        
        /////draw enemy////////
        renderEnemy(player1Pos,player2Pos);
        
        //draw game information
        renderText();
        if(world.gameState == World.INSTRUCTION_STATE)
        {
        	batch.draw(instructionImg, 100 ,100);
        }
        else if(world.gameState == World.GAME_OVER_STATE)
        {
        	batch.draw(gameOverImg, 100 ,100);
        	font.draw(batch,"Wave: " +world.waveNumber,DontDieGame.SCREEN_WIDTH/2 -30,225);
            font.draw(batch,"Time Survived: " +world.timeSec + "  sec",DontDieGame.SCREEN_WIDTH/2 -30, 200);
            font.draw(batch,"Gained Score: " +world.score,DontDieGame.SCREEN_WIDTH/2 -30, 175);
            font.draw(batch,"Highest-Score: " +world.hiScore,DontDieGame.SCREEN_WIDTH/2  -30,150);
        }
        //draw players health bar
        renderHealthBar();
        batch.end();
    }
	
}