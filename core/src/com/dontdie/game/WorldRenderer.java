package com.dontdie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer { //what happen to the game world will be draw here
	private DontDieGame dontdieGame;
	private SpriteBatch batch;
	private World world;
	private Player1 player1;
	private Player1 player2;
	//image
	private Texture player1RightImg;
	private Texture player1LeftImg;
	private Texture player1UpImg;
	private Texture player1DownImg;
	private Texture player2RightImg;
	private Texture player2LeftImg;
	private Texture player2UpImg;
	private Texture player2DownImg;
	private Texture timestopperImg;
	private Texture backgroundImg;
	
	//health bar images
	private Texture healthBar1Img;
	private Texture healthBar2Img;
	
	private Texture attackLeft_Img;
	private Texture attackRight_Img;
	private Texture attackDown_Img;
	private Texture attackUp_Img;
	
	private Snake snake1;
	private Texture snakeImg;
	public WorldRenderer(DontDieGame dontdieGame, World world) {
		this.dontdieGame = dontdieGame;
		batch = dontdieGame.batch;

		this.world = world;

		player1RightImg = new Texture("Player1_Right.png");
		player1LeftImg = new Texture("Player1_Left.png");
		player1UpImg = new Texture("Player1_Up.png");
		player1DownImg = new Texture("Player1_Down.png");
		player2RightImg = new Texture("Player2_Right.png");
		player2LeftImg = new Texture("Player2_Left.png");
		player2UpImg = new Texture("Player2_Up.png");
		player2DownImg = new Texture("Player2_Down.png");
		
		timestopperImg = new Texture("stopwatch.png");
		
		backgroundImg = new Texture("background.jpg");
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		attackLeft_Img = new Texture("sword_ani_left.png");
		attackRight_Img = new Texture("sword_ani_right.png");
		attackUp_Img = new Texture("sword_ani_up.png");
		attackDown_Img = new Texture("sword_ani_down.png");
		
		snakeImg = new Texture ("snake.png");
	}
	
	public void render(float delta) { // this draw game animation to window
		world.update(delta); //this make the world update which will order enemy and object to update afterward
        SpriteBatch batch = dontdieGame.batch; //what is this line??
        batch.begin();
        batch.draw(backgroundImg, 0, 0);
        
        //when draw:draw currPos position, but when calculate, use CURR_CENTER so hit box of that object will always be at center of image
        if(player1.isPlayerDead == false)
        {
        	Vector2 player1Pos = player1.getPosition(); //get position of player 1 to draw it
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
        
        if(player2.isPlayerDead == false)
        {
        	Vector2 player2Pos = player2.getPosition(); //get position of player 2 to draw it
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
        
        for(int i =0;i< world.attack_list.size(); i++) //draw every currently available attack
    	{
        	Vector2 attack_i = world.attack_list.get(i).getPosition();
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
        
        for(int i =0;i< world.snake_list.size(); i++) //draw every snake in snake_list
    	{
        	batch.draw(snakeImg, world.snake_list.get(i).getPosition().x, world.snake_list.get(i).getPosition().y);
    	}
        
        for(int i =0;i< world.timestopper_list.size(); i++) //draw every timestopper item in timestopper_list 
    	{
        	batch.draw(timestopperImg, world.timestopper_list.get(i).getPosition().x, world.timestopper_list.get(i).getPosition().y);
    	}
        
        healthBar1Img = new Texture("healthbar_"+player1.hpScale+".png");
        healthBar2Img = new Texture("healthbar_"+player2.hpScale+".png");
        batch.draw(healthBar1Img, 10 ,15);
        batch.draw(healthBar2Img, 610 ,15);
        batch.end();
    }
	
}