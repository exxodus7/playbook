package com.schroetech.game;

/**
 * Common interface for all player objects.
 */
public interface IPlayer {

    /**
     * Takes a turn of the game.
     *
     * @param game The current state of the game being played.SS
     */
    public void takeTurn(IGame game);
}
