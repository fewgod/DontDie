package com.dontdie.game;

import com.badlogic.gdx.graphics.Texture;

public class World { // what happen to the game will be create here
    private Player1 player1;
    private Player1 player2;
    private DontDieGame dontdieGame;
    
    public static final int CHOOSE_PLAYER_STATE = 1;
    public static final int START_GAME_STATE = 2;
	public int gameState;
	public boolean chose2Player;
 // now can play 2 players if delete those comment symbol
    World(DontDieGame dontdieGame) {
        this.dontdieGame = dontdieGame; //? why must use this and why it must be 'this.dontdieGame = dontdieGame';
        gameState = CHOOSE_PLAYER_STATE;
        player1 = new Player1(400,400);
        player2 = new Player1(400,100);
    }
 
    Player1 getPlayer1() {
        return player1;
    }
    Player1 getPlayer2() {
        return player2;
    }
}