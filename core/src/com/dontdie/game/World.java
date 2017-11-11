package com.dontdie.game;

public class World {
    private Player1 player1;
    private DontDieGame dontdieGame;
 
    World(DontDieGame dontdieGame) {
        this.dontdieGame = dontdieGame;
 
        player1 = new Player1(100,100);
    }
 
    Player1 getPlayer1() {
        return player1;
    }
}