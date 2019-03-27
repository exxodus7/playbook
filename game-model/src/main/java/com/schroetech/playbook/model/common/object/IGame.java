package com.schroetech.playbook.model.common.object;

import com.schroetech.playbook.model.common.player.IPlayer;
import java.util.ArrayList;
import java.util.Map;

/**
 * Common interface for all game objects.
 */
public interface IGame {

    /**
     * Play through one complete iteration of the game. Will not display ongoing
     * game state to the console.
     *
     * @return boolean representing whether the game finished successfully.
     */
    public boolean play();

    /**
     * Play through one complete iteration of the game.
     *
     * @param display Whether or not to display ongoing game state to the
     * console.
     * @return boolean representing whether the game finished successfully.
     */
    public boolean play(boolean display);

    /**
     * Get the ID of the winning player.
     *
     * @return String representing the ID of the winning player, or null if
     * there was a draw.
     */
    public String getWinningPlayerId();

    /**
     * Add a player to the game.
     *
     * @param newPlayer The player to add.
     */
    public void addPlayer(IPlayer newPlayer);

    /**
     * Add multiple players to the game.
     *
     * @param newPlayers The players to add.
     */
    public void addPlayers(ArrayList<IPlayer> newPlayers);

    /**
     * Sets the players that will play the game. Erases any previously set
     * players.
     *
     * @param newPlayers The players to set.
     */
    public void setPlayers(Map<String, IPlayer> newPlayers);

    /**
     * Gets the players who are a part of the game.
     *
     * @return Map representing the players who are a part of the game. The key
     * to the map is the ID of the associated value, the player.
     */
    public Map<String, IPlayer> getPlayers();

    /**
     * Gets the number of players who are playing the game.
     *
     * @return Integer representing the current number of players.
     */
    public int getNumPlayers();

    /**
     * Gets the minimum number of players needed to play the game.
     *
     * @return Minimum number of players.
     */
    public int getMinNumPlayers();

    /**
     * Gets the maximum number of players possible to play the game.
     *
     * @return Maximum number of players.
     */
    public int getMaxNumPlayers();

    /**
     * Returns a boolean representing whether the game has ended.
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver();

    /**
     * Prints the results of the game to the console.
     */
    public void displayFinalResultsToConsole();

    /**
     * Prints the current state of the board to the console.
     */
    public void displayGameStateToConsole();
    
    public String getName();
}
