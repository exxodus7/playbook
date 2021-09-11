package com.schroetech.game.tictactoe.player;

import com.schroetech.game.tictactoe.object.TicTacToePlayerMarker;
import com.schroetech.game.tictactoe.object.TicTacToeSpace;
import com.schroetech.playbook.model.common.player.AbstractPlayer;

/**
 * Abstract class representing a player who plays Tic-Tac-Toe.
 */
public abstract class AbstractTicTacToePlayer extends AbstractPlayer {

    /**
     * Takes a turn at Tic-Tac-Toe.
     *
     * @param board A 2D representation of the board state.
     * @param myMarker The marker type of this player.
     * @return A TicTacToeSpace representing the coordinates that the player
     * would like to select for their move.
     */
    public abstract TicTacToeSpace takeTurn(TicTacToePlayerMarker[][] board, TicTacToePlayerMarker myMarker);
}
