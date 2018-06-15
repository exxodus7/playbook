package com.schroetech.game;

import java.util.ArrayList;

/**
 * Facilitates a game session. Specifically, drives the interactions between the
 * players and the game from setup through the end of the game.
 */
public class TabletopSession {

    private IGame game = null;
    private ArrayList<IPlayer> players = new ArrayList<>();

    /**
     * Sets up and plays through the specified game with the given players.S
     */
    public void playGame() {

        // check to see whether the game was set.
        if (game == null) {
            System.out.println("Please set game type.");
            return;
        }

        // check to make sure there are an accurate number of players.
        if (players.size() < game.getMinPlayers() || players.size() > game.getMaxPlayers()) {
            System.out.println("Please add the correct number of players.");
            return;
        }

        game.setup(players);

        while (!game.isGameOver()) {
            for (IPlayer player : players) {
                if (game.isGameOver()) {
                    break;
                }

                player.takeTurn(game);
                game.printBoardState();
            }
        }

        game.printResults();
    }

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
}
