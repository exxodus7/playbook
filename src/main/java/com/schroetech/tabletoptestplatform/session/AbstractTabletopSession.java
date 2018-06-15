package com.schroetech.tabletoptestplatform.session;

import com.schroetech.game.IGame;
import com.schroetech.game.IPlayer;
import java.util.ArrayList;

/**
 * Provides common functionality for facilitating a game session. Specifically,
 * drives the interactions between the players and the game from setup through
 * the end of the game.
 */
public abstract class AbstractTabletopSession {

    private IGame game = null;
    private final ArrayList<IPlayer> players = new ArrayList<>();

    /**
     * Sets the game to be played.
     *
     * @param newGame New instance of the desired game.
     */
    public void setGame(IGame newGame) {
        game = newGame;
    }

    /**
     * Gets the game being played.
     *
     * @return the instance of the game being played.
     */
    public IGame getGame() {
        return game;
    }

    /**
     * Adds a player to the game.
     *
     * @param newPlayer Player who will play the game.S
     */
    public void addPlayer(IPlayer newPlayer) {
        players.add(newPlayer);
    }

    /**
     * Adds players to the game.
     *
     * @param newPlayers Players who will play the game.
     */
    public void addPlayers(ArrayList<IPlayer> newPlayers) {
        players.addAll(newPlayers);
    }

    /**
     * Gets the players who are playing the game.
     *
     * @return List of players.
     */
    public ArrayList<IPlayer> getPlayers() {
        return players;
    }
}
