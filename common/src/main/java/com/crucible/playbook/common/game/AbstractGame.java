package com.crucible.playbook.common.game;

import com.crucible.playbook.common.game.player.IPlayer;
import com.crucible.playbook.common.util.PersistLevel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Abstract class that holds common functionality for all game objects.
 */
public abstract class AbstractGame implements IGame {

    private final Map<String, IPlayer> players = new HashMap();
    private String winningPlayerId, currentPlayerId;
    private boolean gameOver = false;
    protected String gameId;
    protected List<String> playerOrder;
    protected boolean display = false;
    protected String sessionId;
    private PersistLevel persistLevel;

    public AbstractGame() {
        gameId = UUID.randomUUID().toString();
    }

    @Override
    public boolean play(boolean display) {
        this.display = display;
        determineTurnOrder();
        this.setup();
        return this.start();
    }

    @Override
    public boolean play() {
        return play(false);
    }

    /**
     * Sets up the game.
     */
    protected abstract void setup();

    protected abstract boolean start();

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
     * @param playerId String representing the ID of the winning player.
     */
    protected void setWinningPlayerId(String playerId) {
        winningPlayerId = playerId;
    }

    @Override
    public String getWinningPlayerId() {
        return winningPlayerId;
    }

    /**
     * Sets the current player.
     *
     * @param playerId String representing the ID of the current player.
     */
    protected void setCurrentPlayerId(String playerId) {
        currentPlayerId = playerId;
    }

    /**
     * Gets the current player.
     *
     * @return String representing the ID of the current player.
     */
    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    @Override
    public void setSessionId(String newSessionId) {
        sessionId = newSessionId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
    
    @Override
    public void setPersistLevel(PersistLevel newPersistLevel) {
        persistLevel = newPersistLevel;
    }

    @Override
    public PersistLevel getPersistLevel() {
        return persistLevel;
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
        setWinningPlayerId(winnerID);
        gameOver();
    }

    @Override
    public void displayFinalResultsToConsole() {
        if (!isGameOver()) {
            return;
        }

        if (getWinningPlayerId() != null) {
            IPlayer winningPlayer = this.getPlayers().get(getWinningPlayerId());
            System.out.println(winningPlayer.getName() + " wins the game!");
        } else {
            System.out.println("The game ended in a draw.");
        }

        System.out.println("Final board setup:");
        displayGameStateToConsole();
    }

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected void determineTurnOrder() {
        // Set turn order.
        playerOrder = new ArrayList(this.getPlayers().keySet());
        Collections.shuffle(playerOrder);
    }
    
}
