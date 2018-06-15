package com.schroetech.game;

import java.util.ArrayList;

/**
 * Common interface for all game objects.
 */
public interface IGame {

    /**
     * Sets up a game, initializing both the game state and the players.
     *
     * @param players ArrayList of all of the players taking part in this
     * particular game instance.
     */
    public void setup(ArrayList<IPlayer> players);

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
