package com.schroetech.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a game where the players take turns.
 */
public abstract class AbstractSimpleTurnGame extends AbstractGame {
    
    protected boolean display = false;

    @Override
    public boolean play(boolean display) {

        this.display = display;
        this.setup();

        // Check for a correct number of players.
        if (this.getNumPlayers() < this.getMinNumPlayers()
                || this.getNumPlayers() > this.getMaxNumPlayers()) {
            if (this.getMinNumPlayers() == this.getMaxNumPlayers()) {
                System.out.println("Please add exactly " + this.getMinNumPlayers() + " players to the game.");
            } else {
                System.out.println("Please add between " + this.getMinNumPlayers() + " and " + this.getMaxNumPlayers() + " players to the game.");
            }
            return false;
        }

        // Set turn order.
        List<String> playerIds = new ArrayList(this.getPlayers().keySet());
        Collections.shuffle(playerIds);
        
        while (!this.isGameOver()) {
            for (String playerId : playerIds) {
                setCurrentPlayerId(playerId);
                if (!playerTurn()) {
                    System.out.println(this.getPlayers().get(playerId).getName() + " (" + playerId + ") ran into a problem executing their turn.");
                    displayGameStateToConsole();
                    return false;
                }

                if (display) {
                    displayGameStateToConsole();
                }

                if (this.isGameOver()) {
                    break;
                }
            }
        }

        if (display) {
            displayFinalResultsToConsole();
        }

        return true;
    }

    /**
     * Sets up the game.
     */
    protected abstract void setup();

    /**
     * Sets up a player's turn and calls that player to take their turn.
     *
     * @return true if the turn was successful. False otherwise.
     */
    protected abstract boolean playerTurn();

}
