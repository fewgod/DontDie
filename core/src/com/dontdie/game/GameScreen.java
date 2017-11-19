package com.dontdie.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameScreen extends ScreenAdapter {
	 
    private DontDieGame dontdieGame;
    private Player1 player1;
    private Player1 player2;
	private WorldRenderer worldRender;
    World world;

    public GameScreen(DontDieGame dontdieGame) { //receive input and final drawn after receive from WorldRenderer if dont have Worldrenderer will only accept input but not draw anything
        this.dontdieGame = dontdieGame;
        world = new World(dontdieGame); //create
        player1 = world.getPlayer1();
        player2 = world.getPlayer2();
        worldRender = new WorldRenderer(this.dontdieGame,world); //what is the difference if use dontdieGame instead of this.dontdieGame
    }
    
    @Override
    public void render(float delta) {
    	//update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRender.render(delta);
        update(delta);
    }
    
    private void update(float delta) 
    {
    	if(player1.isPlayerDead == false)
        {
    		moveplayer1();
        }
    	if(player2.isPlayerDead == false)
        {
    		moveplayer2();
        }
    }
    private void moveplayer1()//input for player1 movement
    {
    	if(Gdx.input.isKeyPressed(Keys.W)) 
        {
    		player1.faceDir = Player1.DIRECTION_UP;
            player1.move(Player1.DIRECTION_UP);
        }
        if(Gdx.input.isKeyPressed(Keys.D)) 
        {
            player1.faceDir = Player1.DIRECTION_RIGHT;
            player1.move(Player1.DIRECTION_RIGHT);
        }
        if(Gdx.input.isKeyPressed(Keys.S)) 
        {
            player1.faceDir = Player1.DIRECTION_DOWN;
            player1.move(Player1.DIRECTION_DOWN);
        }
        if(Gdx.input.isKeyPressed(Keys.A)) 
        {
            player1.faceDir = Player1.DIRECTION_LEFT;
            player1.move(Player1.DIRECTION_LEFT);
        }
        if(Gdx.input.isKeyPressed(Keys.E)) 
        {
        	if(player1.attackCoolDown <= 0)
        	{
        		world.attack_list.add( new Attack(world,player1.faceDir, player1.getCurrentXPos() , player1.getCurrentYPos())); //draw from center of player1.
        		player1.attackCoolDown = 15;
        	}
        }
    }
    
    private void moveplayer2()//input for player2 movement
    {
        if(Gdx.input.isKeyPressed(Keys.UP)) 
        {
        	player2.faceDir = Player1.DIRECTION_UP;
            player2.move(Player1.DIRECTION_UP);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) 
        {
        	player2.faceDir = Player1.DIRECTION_RIGHT;
            player2.move(Player1.DIRECTION_RIGHT);
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)) 
        {
        	player2.faceDir = Player1.DIRECTION_DOWN;
            player2.move(Player1.DIRECTION_DOWN);
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) 
        {
        	player2.faceDir = Player1.DIRECTION_LEFT;
            player2.move(Player1.DIRECTION_LEFT);
        }
        if(Gdx.input.isKeyPressed(Keys.R)) 
        {
        	if(player2.attackCoolDown <= 0)
        	{
        		world.attack_list.add( new Attack(world, player2.faceDir , player2.getCurrentXPos() , player2.getCurrentYPos())); //draw from center of player2.
        		player2.attackCoolDown = 15;
        	}
        }
    }
}