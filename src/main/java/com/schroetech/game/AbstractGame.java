package com.schroetech.game;

import java.util.ArrayList;

/**
 * Abstract class that holds common functionality for all game objects.
 */
public abstract class AbstractGame implements IGame {
    
    private final ArrayList<IPlayer> players = new ArrayList();
    
    public void addPlayer(IPlayer newPlayer) {
        players.add(newPlayer);
    }
    
    public void addPlayers(ArrayList<IPlayer> newPlayers) {
        players.addAll(newPlayers);
    }
    
    public ArrayList<IPlayer> getPlayers() {
        return players;
    }
    
    public int getNumPlayers() {
        return players.size();
    }
}
