package com.schroetech.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that holds common functionality for all game objects.
 */
public abstract class AbstractGame implements IGame {

    private final Map<String, IPlayer> players = new HashMap();
    private String winningPlayerID, currentPlayerID;
    private boolean gameOver = false;

    @Override
    public boolean play() {
        return play(false);
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

    /**
     * Sets the winner of the game.
     *
     * @param playerID String representing the ID of the winning player.
     */
    protected void setWinningPlayerID(String playerID) {
        winningPlayerID = playerID;
    }

    @Override
    public String getWinningPlayerID() {
        return winningPlayerID;
    }

    /**
     * Sets the current player.
     *
     * @param playerID String representing the ID of the current player.
     */
    protected void setCurrentPlayerID(String playerID) {
        currentPlayerID = playerID;
    }

    /**
     * Gets the current player.
     *
     * @return String representing the ID of the current player.
     */
    public String getCurrentPlayerID() {
        return currentPlayerID;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Marks the game as over.
     */
    protected void gameOver() {
        gameOver = true;
    }

    /**
     * Marks the game as over and sets a winner ID.
     *
     * @param winnerID
     */
    protected void gameOver(String winnerID) {
        gameOver = true;
        setWinningPlayerID(winnerID);
    }

    @Override
    public void printResults() {
        if (!isGameOver()) {
            return;
        }

        if (getWinningPlayerID() != null) {
            IPlayer winningPlayer = this.getPlayers().get(getWinningPlayerID());
            System.out.println(winningPlayer.getName() + " wins the game!");
        } else {
            System.out.println("The game ended in a draw.");
        }

        System.out.println("Final board setup:");
        printBoardState();
    }
}
