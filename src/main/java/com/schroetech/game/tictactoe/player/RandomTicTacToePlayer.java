/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.game.tictactoe.player;

import com.schroetech.game.IGame;
import com.schroetech.game.tictactoe.TicTacToe;
import com.schroetech.game.tictactoe.object.PlayerMarker;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author lauren
 */
public class RandomTicTacToePlayer extends AbstractTicTacToePlayer {

    @Override
    public void takeTurn(IGame game) {
        ArrayList<int[]> possibilities = new ArrayList();
        TicTacToe ticTacToe = (TicTacToe) game;
        PlayerMarker[][] board = ticTacToe.getBoard();
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
        ticTacToe.move(move[0], move[1], this.getPlayerMarker());
    }

}
