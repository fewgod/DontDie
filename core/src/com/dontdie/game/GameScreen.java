package com.dontdie.game;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends ScreenAdapter {
	 
    private DontDieGame dontdieGame;
    private Texture playerImg;
 
    public GameScreen(DontDieGame dontdieGame) {
        this.dontdieGame = dontdieGame;
        playerImg = new Texture("Player1.png");
    }
    
    @Override
    public void render(float delta) {
        SpriteBatch batch = dontdieGame.batch;
        batch.begin();
        batch.draw(playerImg, 100, 100);
        batch.end();
    }
}