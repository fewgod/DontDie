package com.dontdie.game;

public class World { // what happen to the game will be create here
    private Player1 player1;
    private DontDieGame dontdieGame;
 
    World(DontDieGame dontdieGame) {
        this.dontdieGame = dontdieGame; //? why must use this and why it must be 'this.dontdieGame = dontdieGame';
 
        player1 = new Player1(100,100);
    }
 
    Player1 getPlayer1() {
        return player1;
    }
}