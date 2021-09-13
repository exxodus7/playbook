package com.crucible.playbook.game.tictactoe.player;

import com.crucible.playbook.game.tictactoe.object.TicTacToePlayerMarker;
import com.crucible.playbook.game.tictactoe.object.TicTacToeSpace;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Tic-Tac-Toe player who always makes a random move.
 */
public class RandomTicTacToePlayer extends AbstractTicTacToePlayer {
    
    public static final String TYPE_STRING = "Random AI";

    @Override
    public TicTacToeSpace takeTurn(TicTacToePlayerMarker[][] board, TicTacToePlayerMarker myMarker) {
        ArrayList<TicTacToeSpace> possibilities = new ArrayList();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    possibilities.add(new TicTacToeSpace(i, j));
                }
            }
        }

        Random randomNumberGenerator = new Random();
        return possibilities.get(randomNumberGenerator.nextInt(possibilities.size()));
    }

    @Override
    public String getTypeString() {
        return TYPE_STRING;
    }

}
