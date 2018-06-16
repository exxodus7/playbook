package com.schroetech.game;

import java.util.ArrayList;
import java.util.Map;

/**
 * Common interface for all game objects.
 */
public interface IGame {
    
    public void play();
    
    public String getWinningPlayerId();
    
    public void addPlayer(IPlayer newPlayer);
    
    public void addPlayers(ArrayList<IPlayer> newPlayers);
    
    public void setPlayers(Map<String, IPlayer> newPlayers);
    
    public Map<String, IPlayer> getPlayers();

    /**
     * Gets the minimum number of players needed to play the game.
     *
     * @return Minimum number of players.
     */
    public int getMinPlayers();

    /**
     * Gets the maximum number of players possible to play the game.
     *
     * @return Maximum number of players.
     */
    public int getMaxPlayers();

    /**
     * Returns a boolean representing whether the game has ended.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver();

    /**
     * Prints the results of the game to the console.
     */
    public void printResults();

    /**
     * Prints the current state of the board to the console.
     */
    public void printBoardState();
}
