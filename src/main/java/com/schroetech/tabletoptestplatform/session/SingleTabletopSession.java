package com.schroetech.tabletoptestplatform.session;

import com.schroetech.game.IGame;
import com.schroetech.game.IPlayer;
import com.schroetech.game.tictactoe.TicTacToe;
import java.util.ArrayList;

/**
 * Facilitates a game session for one game being played. Specifically, drives
 * the interactions between the players and the game from setup through the end
 * of the game.
 */
public class SingleTabletopSession extends AbstractTabletopSession {

    /**
     * Sets up and plays through the specified game with the given players.
     *
     * @param display Whether to display the game as it is being played.
     * @return The game winner, or null if there was a draw.
     */
    public IPlayer playGame(boolean display) {

        IGame game = this.getGame();
        ArrayList<IPlayer> players = this.getPlayers();

        // check to see whether the game was set.
        if (game == null) {
            throw new IllegalArgumentException("Please set game type.");
        }

        // check to make sure there are an accurate number of players.
        if (players.size() < game.getMinPlayers() || players.size() > game.getMaxPlayers()) {
            throw new IllegalArgumentException("Please add the correct number of players.");
        }

        game.setup(players);

        while (!game.isGameOver()) {
            for (IPlayer player : players) {
                if (game.isGameOver()) {
                    break;
                }

                player.takeTurn(game);
                if (display) {
                    game.printBoardState();
                }
            }
        }

        IPlayer winner = game.getWinner(players);
        if (display) {
            game.printResults();
        }

        return winner;
    }
}
