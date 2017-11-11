package com.dontdie.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer { //what happen to the game world will be drawn here
	private DontDieGame dontdieGame;
	private SpriteBatch batch;
	private World world;
	private Player1 player1;
	private Player1 player2;
	private Texture player1Img;
	private Texture player2Img;

	public WorldRenderer(DontDieGame dontdieGame, World world) {
		this.dontdieGame = dontdieGame;
		batch = dontdieGame.batch;

		this.world = world;

		player1Img = new Texture("Player1.png");
		//player2Img = new Texture("Player2.png");
		player1 = world.getPlayer1();
		//player2 = world.getPlayer2();
		if(this.world.chose2Player == true) 
		{
			player2Img = new Texture("Player2.png");
			player2 = world.getPlayer2();
		}
	}
	
	public void render(float delta) { // this drawn game animation to window
        SpriteBatch batch = dontdieGame.batch; //what is this line??
        batch.begin();
        Vector2 player1Pos = player1.getPosition(); //get position of player 1 to draw it
        //Vector2 player2Pos = player2.getPosition(); //get position of player 2 to draw it
        batch.draw(player1Img, player1Pos.x, player1Pos.y);
        //batch.draw(player2Img, player2Pos.x, player2Pos.y);
        if(this.world.chose2Player == true) 
		{
        	Vector2 player2Pos = player2.getPosition(); //get position of player 2 to draw it
        	batch.draw(player2Img, player2Pos.x, player2Pos.y);
		}
        batch.end();
    }
}