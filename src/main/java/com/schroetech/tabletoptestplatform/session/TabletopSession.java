package com.schroetech.tabletoptestplatform.session;

import com.schroetech.game.IGame;
import com.schroetech.game.IPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A session of one type of game. Could involve multiple play-throughs of that
 * game. Controls the execution of those games and aggregates data
 * appropriately.
 */
public class TabletopSession {

    private final Map<String, IPlayer> players = new HashMap();
    private String gameType;
    private int numberOfGames;
    private boolean displayOn = false;

    /**
     * Executes 1-many iterations of a given game with the given players.
     *
     * @return true if the session was run without error, false otherwise.
     */
    public boolean startSession() {
        Map<String, Integer> numberOfWins = new HashMap();
        for (String playerId : this.getPlayers().keySet()) {
            numberOfWins.put(playerId, 0);
        }

        int numberOfDraws = getNumberOfGames();

        try {
            for (int i = 0; i < numberOfGames; i++) {
                Class cls = Class.forName(gameType);
                IGame game = (IGame) cls.newInstance();
                game.setPlayers(this.getPlayers());
                game.play(displayOn);
                String winningPlayerId = game.getWinningPlayerId();
                if (winningPlayerId != null) {
                    numberOfWins.put(winningPlayerId, numberOfWins.get(winningPlayerId) + 1);
                    numberOfDraws--;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println("Error instantiating new game of type " + gameType);
            return false;
        }

        // print results
        System.out.println("Out of " + getNumberOfGames() + " games: ");
        for (String playerId : getPlayers().keySet()) {
            String playerName = getPlayers().get(playerId).getName();
            int numWins = numberOfWins.get(playerId);
            System.out.println(" " + playerName + " won " + numWins + " times (" + percent(numWins, getNumberOfGames()) + "%),");
        }
        System.out.println(" And the game was a draw " + numberOfDraws + " times (" + percent(numberOfDraws, getNumberOfGames()) + "%),");

        return true;
    }

    /**
     * Gets the percentage that one number is of the other.
     *
     * @param numerator integer representing the numerator.
     * @param denominator integer representing the denominator.
     * @return the percentage.
     */
    private double percent(int numerator, int denominator) {
        return (numerator * 100.0) / denominator;
    }

    /**
     * Add a player to the game.
     *
     * @param newPlayer The player to add.
     */
    public void addPlayer(IPlayer newPlayer) {
        players.put(newPlayer.getId(), newPlayer);
    }

    /**
     * Add multiple players to the game.
     *
     * @param newPlayers The players to add.
     */
    public void addPlayers(ArrayList<IPlayer> newPlayers) {
        for (IPlayer newPlayer : newPlayers) {
            players.put(newPlayer.getId(), newPlayer);
        }
    }

    /**
     * Gets the players who are a part of the game.
     *
     * @return Map representing the players who are a part of the game. The key
     * to the map is the ID of the associated value, the player.
     */
    public Map<String, IPlayer> getPlayers() {
        return players;
    }

    public void setGameType(String newGameType) {
        gameType = newGameType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setNumberOfGames(int newNumberOfGames) {
        numberOfGames = newNumberOfGames;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void turnDisplayOn() {
        displayOn = true;
    }
}
