package com.crucible.playbook.common.game;

/**
 * Represents a game where the players take turns.
 */
public abstract class AbstractSimpleTurnGame extends AbstractGame {

    private int turnNumber = 0;
    
    @Override
    public boolean start() {
                
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

        while (!this.isGameOver()) {
            for (String playerId : playerOrder) {
                setCurrentPlayerId(playerId);
                turnNumber++;
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
     * Sets up a player's turn and calls that player to take their turn.
     *
     * @return true if the turn was successful. False otherwise.
     */
    protected abstract boolean playerTurn();

    public int getTurnNumber() {
        return this.turnNumber;
    }
    
    public void setTurnNumber(Integer newTurnNumber) {
        this.turnNumber = newTurnNumber;
    }
}
