package com.schroetech.playbook.ui.simulator;

import com.schroetech.playbook.model.common.object.IGame;
import com.schroetech.playbook.model.common.player.IPlayer;
import com.schroetech.playbook.persistence.GamingSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A session of one type of game. Could involve multiple play-throughs of that
 * game. Controls the execution of those games and aggregates data
 * appropriately.
 */
public class Simulator {

    private final Map<String, IPlayer> players = new HashMap();
    private boolean displayOn = false;
    private boolean persistData = false;
    private GamingSession session;

    public Simulator() {
        session = new GamingSession();
    }

    /**
     * Executes 1-many iterations of a given game with the given players.
     *
     * @return true if the session was run without error, false otherwise.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     */
    public boolean startSession() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        long startTime = System.currentTimeMillis();

        Map<String, Integer> numberOfWins = new HashMap();
        this.getPlayers().keySet().forEach((playerId) -> {
            numberOfWins.put(playerId, 0);
        });

        int numberOfDraws = session.getNumberOfPlays();

        for (int i = 0; i < session.getNumberOfPlays(); i++) {
            IGame game = (IGame) Class.forName(session.getGameName()).newInstance();
            game.setPlayers(this.getPlayers());
            if (!game.play(displayOn)) {
                return false;
            }

            String winningPlayerId = game.getWinningPlayerId();
            if (winningPlayerId != null) {
                numberOfWins.put(winningPlayerId, numberOfWins.get(winningPlayerId) + 1);
                numberOfDraws--;
            }

            System.out.print("\r");
            int percentageComplete = (i + 1) * 100 / (session.getNumberOfPlays());
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
        System.out.println("Out of " + session.getNumberOfPlays() + " games: ");
        getPlayers().keySet().forEach((playerId) -> {
            String playerName = getPlayers().get(playerId).getName();
            int numWins = numberOfWins.get(playerId);
            System.out.printf(" " + playerName + " won " + numWins + " times (%.2f%%),\n", resultPercent(numWins));
        });
        System.out.printf(" And the game was a draw " + numberOfDraws + " times (%.2f%%).\n", resultPercent(numberOfDraws));

        long endTime = System.currentTimeMillis();
        System.out.println("Session completed in " + ((endTime - startTime) / 1000.0) + "ms.");

        if (persistData) {
            GamingSession.persist(session);
            // just for test
            for (GamingSession session : GamingSession.findAll()) {
                System.out.println("Persisted, then queried for: ");
                System.out.println("  " + session.getSessionId());
                System.out.println("  " + session.getGameName());
                System.out.println("  " + session.getNumberOfPlays());
            }
        }

        return true;
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

    public void setSession(GamingSession newGamingSession) {
        session = newGamingSession;
    }

    public GamingSession getGamingSession() {
        return session;
    }

    public void turnDisplayOn() {
        displayOn = true;
    }

    public void turnPersistDataOn() {
        persistData = true;
    }

    /**
     * Gets the percentage of time that a certain outcome happened.
     *
     * @param numerator integer representing the number of outcomes you want to
     * find the percentage of.
     * @return the percentage.
     */
    private double resultPercent(int numerator) {
        return (numerator * 100.0) / session.getNumberOfPlays();
    }
}
