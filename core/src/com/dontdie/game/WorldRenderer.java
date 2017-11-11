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
	private Texture player1Img;
	private Texture player2Img;
	private Texture backgroundImg;
	
	private Snake snake1;
	private Texture snakeImg;
	public WorldRenderer(DontDieGame dontdieGame, World world) {
		this.dontdieGame = dontdieGame;
		batch = dontdieGame.batch;

		this.world = world;

		player1Img = new Texture("Player1.png");
		player2Img = new Texture("Player2.png");
		backgroundImg = new Texture("background.jpg");
		player1 = world.getPlayer1();
		player2 = world.getPlayer2();
		
		snakeImg = new Texture ("snake.png");
		snake1 = world.getSnake();
	}
	
	public void render(float delta) { // this draw game animation to window
        SpriteBatch batch = dontdieGame.batch; //what is this line??
        batch.begin();
        Vector2 player1Pos = player1.getPosition(); //get position of player 1 to draw it
        Vector2 player2Pos = player2.getPosition(); //get position of player 2 to draw it
        Vector2 snake1Pos = snake1.getPosition(); //why no need to declare snake1Pos variable??
        batch.draw(backgroundImg, 0, 0);
        batch.draw(player1Img, player1Pos.x, player1Pos.y);
        batch.draw(player2Img, player2Pos.x, player2Pos.y);
        batch.draw(snakeImg, snake1Pos.x, snake1Pos.y);
        batch.end();
    }
}