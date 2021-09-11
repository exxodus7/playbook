package com.schroetech.playbook.model.tictactoe.player;

import com.schroetech.playbook.model.tictactoe.object.TicTacToePlayerMarker;
import com.schroetech.playbook.model.tictactoe.object.TicTacToeSpace;
import java.util.Scanner;

/**
 * A Tic-Tac-Toe player controlled by a human from the console.
 */
public class HumanTicTacToePlayer extends AbstractTicTacToePlayer {
    
    public static final String TYPE_STRING = "Human";

    @Override
    public TicTacToeSpace takeTurn(TicTacToePlayerMarker[][] board, TicTacToePlayerMarker myMarker) {
        System.out.println(this.getName() + ", where would you like to go?: ");

        boolean madeValidMove = false;
        int row = -1, column = -1;
        Scanner in = new Scanner(System.in);
        while (!madeValidMove) {
            if (in.hasNextInt()) {
                row = in.nextInt();
            }

            if (in.hasNextInt()) {
                column = in.nextInt();
            }

            if (checkValidInput(row) && checkValidInput(column)) {
                if (board[row][column] == null) {
                    return new TicTacToeSpace(row, column);
                }
            }

            // clear scanner
            in.nextLine();

            System.out.println("That is an illegal move. Please try again: ");
        }

        return null;
    }

    private boolean checkValidInput(int input) {
        return input >= 0 && input <= 2;
    }

    @Override
    public String getTypeString() {
        return TYPE_STRING;
    }
}
