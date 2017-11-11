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
	private Texture player1Img;

	public WorldRenderer(DontDieGame dontdieGame, World world) {
		this.dontdieGame = dontdieGame;
		batch = dontdieGame.batch;

		this.world = world;

		player1Img = new Texture("Player1.png");
		player1 = world.getPlayer1();
	}
	
	public void render(float delta) { // this drawn game animation to window
        SpriteBatch batch = dontdieGame.batch; //what is this line??
        batch.begin();
        Vector2 player1Pos = player1.getPosition(); //get position of player 1 to draw it
        batch.draw(player1Img, player1Pos.x, player1Pos.y);
        batch.end();
    }
}