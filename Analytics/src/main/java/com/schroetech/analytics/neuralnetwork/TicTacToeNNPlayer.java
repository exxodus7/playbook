package com.schroetech.analytics.neuralnetwork;

import com.schroetech.gamesimulator.game.tictactoe.object.TicTacToePlayerMarker;
import com.schroetech.gamesimulator.game.tictactoe.object.TicTacToeSpace;
import com.schroetech.gamesimulator.game.tictactoe.player.AbstractTicTacToePlayer;

/**
 * 
 */
public class TicTacToeNNPlayer extends AbstractTicTacToePlayer {

    @Override
    public TicTacToeSpace takeTurn(TicTacToePlayerMarker[][] board, TicTacToePlayerMarker myMarker) {
        return new TicTacToeSpace(0,0);
    }   
}
