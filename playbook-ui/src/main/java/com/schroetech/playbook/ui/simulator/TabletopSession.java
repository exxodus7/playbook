package com.schroetech.playbook.ui.simulator;

import com.schroetech.playbook.model.common.object.IGame;
import com.schroetech.playbook.model.common.player.IPlayer;
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
    private Class gameType;
    private int numberOfGames;
    private boolean displayOn = false;

    /**
     * Executes 1-many iterations of a given game with the given players.
     *
     * @return true if the session was run without error, false otherwise.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public boolean startSession() throws InstantiationException, IllegalAccessException {
        long startTime = System.currentTimeMillis();

        Map<String, Integer> numberOfWins = new HashMap();
        this.getPlayers().keySet().forEach((playerId) -> {
            numberOfWins.put(playerId, 0);
        });

        int numberOfDraws = getNumberOfGames();

        for (int i = 0; i < numberOfGames; i++) {
            IGame game = (IGame) gameType.newInstance();
            game.setPlayers(this.getPlayers());
            if (!game.play(displayOn)) {
                return false;
            }

            String winningPlayerId = game.getWinningPlayerId();
            if (winningPlayerId != null) {
                numberOfWins.put(winningPlayerId, numberOfWins.get(winningPlayerId) + 1);
                numberOfDraws--;
            }
            //if (i % (numberOfGames / 10) == 0) {
            System.out.print("\r");
            int percentageComplete = (i + 1) * 100 / (numberOfGames);
            for (int j = 0; j < 10; j++) {
                if (j <= percentageComplete / 10) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print(" (" + percentageComplete + "%)"); //}
        }

        // print results
        System.out.println();
        System.out.println("Out of " + getNumberOfGames() + " games: ");
        getPlayers().keySet().forEach((playerId) -> {
            String playerName = getPlayers().get(playerId).getName();
            int numWins = numberOfWins.get(playerId);
            System.out.printf(" " + playerName + " won " + numWins + " times (%.2f%%),\n", resultPercent(numWins));
        });
        System.out.printf(" And the game was a draw " + numberOfDraws + " times (%.2f%%).\n", resultPercent(numberOfDraws));

        long endTime = System.currentTimeMillis();
        System.out.println("Session completed in " + ((endTime - startTime) / 1000.0) + "ms.");

        return true;
    }

    /**
     * Gets the percentage of time that a certain outcome happened.
     *
     * @param numerator integer representing the number of outcomes you want to
     * find the percentage of.
     * @return the percentage.
     */
    private double resultPercent(int numerator) {
        return (numerator * 100.0) / getNumberOfGames();
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
        newPlayers.forEach((newPlayer) -> {
            players.put(newPlayer.getId(), newPlayer);
        });
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

    public void setGameType(Class newGameType) {
        gameType = newGameType;
    }

    public Class getGameType() {
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
