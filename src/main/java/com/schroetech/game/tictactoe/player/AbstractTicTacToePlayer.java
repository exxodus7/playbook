package com.schroetech.game.tictactoe.player;

import com.schroetech.game.AbstractPlayer;
import com.schroetech.game.tictactoe.object.TicTacToePlayerMarker;

/**
 * Abstract class representing a player who plays TicTacToe.
 */
public abstract class AbstractTicTacToePlayer extends AbstractPlayer {

    private TicTacToePlayerMarker myMarker = null;

    /**
     * Sets the marker that this player will use.
     *
     * @param newMarker The marker that this player will use.
     */
    public void setPlayerMarker(TicTacToePlayerMarker newMarker) {
        myMarker = newMarker;
    }

    /**
     * Gets the marker that this player will use.
     *
     * @return The marker that this player will use.
     */
    public TicTacToePlayerMarker getPlayerMarker() {
        return myMarker;
    }
}
