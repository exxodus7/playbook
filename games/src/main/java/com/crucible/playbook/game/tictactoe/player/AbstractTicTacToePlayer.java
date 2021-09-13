package com.crucible.playbook.game.tictactoe.player;

import com.crucible.playbook.common.game.player.AbstractPlayer;
import com.crucible.playbook.game.tictactoe.object.TicTacToePlayerMarker;
import com.crucible.playbook.game.tictactoe.object.TicTacToeSpace;

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
