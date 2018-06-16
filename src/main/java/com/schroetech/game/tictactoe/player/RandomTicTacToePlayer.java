package com.schroetech.game.tictactoe.player;

import com.schroetech.game.IGame;
import com.schroetech.game.tictactoe.TicTacToe;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Tic Tac Toe player who always makes a random move.
 */
public class RandomTicTacToePlayer extends AbstractTicTacToePlayer {

    @Override
    public void takeTurn(IGame game) {
        ArrayList<int[]> possibilities = new ArrayList();
        TicTacToe ticTacToe = (TicTacToe) game;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (ticTacToe.getPosition(i, j) == null) {
                    int possibility[] = {i, j};
                    possibilities.add(possibility);
                }
            }
        }
        
        Random randomNumberGenerator = new Random();
        int[] move = possibilities.get(randomNumberGenerator.nextInt(possibilities.size()));
        ticTacToe.move(move[0], move[1]);
    }

}
