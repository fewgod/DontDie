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
    private Texture playerImg;
    private Player1 player1;

    public GameScreen(DontDieGame dontdieGame) {
        this.dontdieGame = dontdieGame;
        playerImg = new Texture("Player1.png");
        player1 = new Player1(100,100); //player starting pos
    }
    
    @Override
    public void render(float delta) {
    	update(delta);
    	Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        SpriteBatch batch = dontdieGame.batch;
        batch.begin();
        Vector2 player1Pos = player1.getPosition();
        batch.draw(playerImg, player1Pos.x, player1Pos.y);
        batch.end();
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