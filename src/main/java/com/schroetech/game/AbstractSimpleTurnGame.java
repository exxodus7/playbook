package com.schroetech.game;

/**
 * Represents a game where the players take turns.
 */
public abstract class AbstractSimpleTurnGame extends AbstractGame {

    @Override
    public boolean play(boolean display) {

        this.setup();

        if (this.getNumPlayers() < this.getMinNumPlayers()
                || this.getNumPlayers() > this.getMaxNumPlayers()) {
            if (this.getMinNumPlayers() == this.getMaxNumPlayers()) {
                throw new IllegalArgumentException("Please add exactly " + this.getMinNumPlayers() + " players to the game.");
            } else {
                throw new IllegalArgumentException("Please add between " + this.getMinNumPlayers() + " and " + this.getMaxNumPlayers() + " to the game.");
            }
        }

        while (!this.isGameOver()) {
            for (String playerID : this.getPlayers().keySet()) {
                setCurrentPlayerID(playerID);
                if (!playerTurn()) {
                    System.out.println(this.getPlayers().get(playerID).getName() + " (" + playerID + ") ran into a problem executing their turn.");
                    System.out.println("Current board state: ");
                    printBoardState();
                    return false;
                }

                if (display) {
                    printBoardState();
                }

                if (this.isGameOver()) {
                    break;
                }
            }
        }

        if (display) {
            printResults();
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
