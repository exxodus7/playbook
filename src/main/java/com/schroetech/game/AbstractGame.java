package com.schroetech.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that holds common functionality for all game objects.
 */
public abstract class AbstractGame implements IGame {

    private final Map<String, IPlayer> players = new HashMap();
    
    @Override
    public void play() {
        play(false);
    }

    @Override
    public void addPlayer(IPlayer newPlayer) {
        players.put(newPlayer.getId(), newPlayer);
    }

    @Override
    public void addPlayers(ArrayList<IPlayer> newPlayers) {
        for (IPlayer newPlayer : newPlayers) {
            players.put(newPlayer.getId(), newPlayer);
        }
    }

    @Override
    public void setPlayers(Map<String, IPlayer> newPlayers) {
        players.putAll(newPlayers);
    }

    @Override
    public Map<String, IPlayer> getPlayers() {
        return players;
    }

    @Override
    public int getNumPlayers() {
        return players.size();
    }
}
