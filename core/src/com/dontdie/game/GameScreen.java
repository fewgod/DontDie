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
    private Player player1;
    private Player player2;
	private WorldRenderer worldRender;
    World world;

    public GameScreen(DontDieGame dontdieGame) {
        this.dontdieGame = dontdieGame;
        world = new World(dontdieGame,1); //create world, and give instruction ,once for first time
        player1 = world.getPlayer1();
        player2 = world.getPlayer2();
        worldRender = new WorldRenderer(this.dontdieGame,world);
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
    	if(world.gameState == World.STATE_INSTRUCTION)
    	{
    		if(Gdx.input.isKeyPressed(Keys.ANY_KEY))
    		{
    			world.gameState = World.STATE_START_GAME;
    			world.tStart = System.nanoTime();
    		}
    	}
    	if(world.gameState == World.STATE_START_GAME)
    	{
    		if(player1.isPlayerDead == false)
    		{
    			movePlayer1();
    		}
    		if(player2.isPlayerDead == false)
    		{
    			movePlayer2();
    		}
    	}
    	if(Gdx.input.isKeyPressed(Keys.F1)) 
        {
    		if(world.gameState == World.STATE_START_GAME)
    		{
    			world.bgm.stop();
    		}
    		if(world.gameState == World.STATE_GAME_OVER)
    		{
    			world.game_over.stop();
    		}
        	this.dontdieGame = dontdieGame;
        	world = new World(this.dontdieGame,2); //create world but this time skip instruction
        	player1 = world.getPlayer1();
            player2 = world.getPlayer2();
            worldRender = new WorldRenderer(this.dontdieGame,world);
        }
        
    }
    private void movePlayer1()//input for player1 movement & action
    {
    	if(Gdx.input.isKeyPressed(Keys.W)) 
        {
    		player1.faceDir = Player.DIRECTION_UP;
    		player1.move(Player.DIRECTION_UP);
    	}
    	if(Gdx.input.isKeyPressed(Keys.D)) 
    	{
    		player1.faceDir = Player.DIRECTION_RIGHT;
        	player1.move(Player.DIRECTION_RIGHT);
    	}
    	if(Gdx.input.isKeyPressed(Keys.S)) 
    	{
    		player1.faceDir = Player.DIRECTION_DOWN;
        	player1.move(Player.DIRECTION_DOWN);
    	}
    	if(Gdx.input.isKeyPressed(Keys.A)) 
    	{
    		player1.faceDir = Player.DIRECTION_LEFT;
        	player1.move(Player.DIRECTION_LEFT);
        }
        if(Gdx.input.isKeyPressed(Keys.SPACE)) 
        {
        	if(player1.attackCoolDown <= 0)
        	{
        		world.swordswing.play(0.48f);
        		world.attack_list.add( new Attack(world,player1.faceDir, 1, player1.getCurrentXPos() , player1.getCurrentYPos())); //draw from center of player1.
        		player1.attackCoolDown = 18;
        		player1.slowDownTime = 20;
        	}
        }
        else if(Gdx.input.isKeyPressed(Keys.E)) 
        {
    			if(world.player2.isPlayerDead == true)
    			{
    				world.player1.castSkillRevive(1);
    				world.revivingSomeone = true;
    			}
    			else if(world.player1.provokeCoolDown <= 0)
            	{
            		world.player1.castSkillProvoke(); //provoke can only be use when both is alive
            	}
        }
        else
        {
        	world.player1.skillCastingTime = 0;
        	world.revivingSomeone = false;
        }
    }
    
    private void movePlayer2()//input for player2 movement & action
    {
    	if(Gdx.input.isKeyPressed(Keys.UP)) 
    	{
    		player2.faceDir = Player.DIRECTION_UP;
    		player2.move(Player.DIRECTION_UP);
    	}
    	if(Gdx.input.isKeyPressed(Keys.RIGHT)) 
    	{
    		player2.faceDir = Player.DIRECTION_RIGHT;
        	player2.move(Player.DIRECTION_RIGHT);
    	}
    	if(Gdx.input.isKeyPressed(Keys.DOWN)) 
    	{
    		player2.faceDir = Player.DIRECTION_DOWN;
        	player2.move(Player.DIRECTION_DOWN);
    	}
    	if(Gdx.input.isKeyPressed(Keys.LEFT)) 
    	{
    		player2.faceDir = Player.DIRECTION_LEFT;
        	player2.move(Player.DIRECTION_LEFT);
        }
    	
    	if(Gdx.input.isKeyPressed(Keys.NUMPAD_1)) 
    	{
    		if(player2.attackCoolDown <= 0)
    		{
    			world.swordswing.play(0.45f);
        		world.attack_list.add( new Attack(world, player2.faceDir , 1, player2.getCurrentXPos() , player2.getCurrentYPos())); //draw from center of player2.
        		player2.attackCoolDown = 18;
        		player2.slowDownTime = 20;
        	}
        }
    	//skill zone
    	if(Gdx.input.isKeyPressed(Keys.NUMPAD_2)) 
        {
    		world.player2.castSkillFireBall(2);
        }
    	else if(Gdx.input.isKeyPressed(Keys.NUMPAD_3)) 
        {
    			if(world.player1.isPlayerDead == true)
    			{
    				world.player2.castSkillRevive(2);
    				world.revivingSomeone = true;
    			}
    			else 
    			{
    				world.player2.castSkillSlowFireBall(2); //this skill can only be use when both is alive
    			}
        }
    	else
        {
        	world.player2.skillCastingTime = 0;
        	world.revivingSomeone = false;
        }
    		
    }
}