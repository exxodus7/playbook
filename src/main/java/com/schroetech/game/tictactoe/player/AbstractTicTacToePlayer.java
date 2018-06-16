package com.schroetech.game.tictactoe.player;

import com.schroetech.game.AbstractPlayer;
import com.schroetech.game.tictactoe.object.TicTacToeSpace;

/**
 * Abstract class representing a player who plays TicTacToe.
 */
public abstract class AbstractTicTacToePlayer extends AbstractPlayer {

    public abstract TicTacToeSpace takeTurn();
}
