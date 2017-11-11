package com.dontdie.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter {
	 
    private DontDieGame dontdieGame;
    private Texture player1Img;
	private Texture player2Img;
    private Player1 player1;
    private Player1 player2;
	private WorldRenderer worldRender;
    World world;

    public GameScreen(DontDieGame dontdieGame) { //receive input and final drawn after receive from WorldRenderer
        this.dontdieGame = dontdieGame;
        player1Img = new Texture("Player1.png");
        player2Img = new Texture("Player2.png");
        world = new World(dontdieGame); //create
        player1 = world.getPlayer1();
        player2 = world.getPlayer2();
        worldRender = new WorldRenderer(this.dontdieGame,world); //what is the difference if use dontdieGame instead of this.dontdieGame
    }
    
    @Override
    public void render(float delta) {
    	update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRender.render(delta);
    }

    private void update(float delta) 
    {

        if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) 
        {
            player1.move(Player1.DIRECTION_UP);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) 
        {
            player1.move(Player1.DIRECTION_RIGHT);
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) 
        {
            player1.move(Player1.DIRECTION_DOWN);
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) 
        {
            player1.move(Player1.DIRECTION_LEFT);
        }
    }
}