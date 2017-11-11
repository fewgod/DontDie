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
	//private Texture player2Img;
	private Texture backgroundImg;
	
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
		
		//player2Img = new Texture("Player2.png");
		backgroundImg = new Texture("background.jpg");
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		
		snakeImg = new Texture ("snake.png");
		snake1 = world.getSnake();
	}
	
	public void render(float delta) { // this draw game animation to window
		world.update(delta); //this make the world update which will order enemy and object to update afterward
        SpriteBatch batch = dontdieGame.batch; //what is this line??
        batch.begin();
        Vector2 player1Pos = player1.getPosition(); //get position of player 1 to draw it
        Vector2 player2Pos = player2.getPosition(); //get position of player 2 to draw it
        Vector2 snake1Pos = snake1.getPosition(); //why no need to declare snake1Pos variable??
        batch.draw(backgroundImg, 0, 0);
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
        //batch.draw(player2Img, player2Pos.x, player2Pos.y);
        batch.draw(snakeImg, snake1Pos.x, snake1Pos.y);
        batch.end();
    }
}