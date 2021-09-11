package com.schroetech.playbook.model.tictactoe.player;

import com.schroetech.playbook.model.tictactoe.object.TicTacToePlayerMarker;
import com.schroetech.playbook.model.tictactoe.object.TicTacToeSpace;

/**
 * Tic-Tac-Toe player controlled by Justin's AI.
 */
public class JustinAITicTacToePlayer extends AbstractTicTacToePlayer {
    
    public static final String TYPE_STRING = "Justin's AI";

    @Override
    public TicTacToeSpace takeTurn(TicTacToePlayerMarker[][] board, TicTacToePlayerMarker myMarker) {

        TicTacToePlayerMarker otherGuy;
        if (myMarker == TicTacToePlayerMarker.X) {
            otherGuy = TicTacToePlayerMarker.O;
        } else {
            otherGuy = TicTacToePlayerMarker.X;
        }

        //set up winnable formations
        int[][][] formations = {{{0, 0}, {0, 1}, {0, 2}}, {{1, 0}, {1, 1}, {1, 2}}, {{2, 0}, {2, 1}, {2, 2}},
        {{0, 0}, {1, 0}, {2, 0}}, {{0, 1}, {1, 1}, {2, 1}}, {{0, 2}, {1, 2}, {2, 2}},
        {{0, 0}, {1, 1}, {2, 2}}, {{0, 2}, {1, 1}, {2, 0}}};

        //check to see if win possible
        for (int f = 0; f < 8; f++) {
            int firstMove = isWinnable(board, formations[f], myMarker, otherGuy);
            if (firstMove > -1) {
                return new TicTacToeSpace(formations[f][firstMove][0], formations[f][firstMove][1]);
            }
        }

        //check to see if opponent win possible
        for (int f = 0; f < 8; f++) {
            int secondMove = isWinnable(board, formations[f], otherGuy, myMarker);
            if (secondMove > -1) {
                return new TicTacToeSpace(formations[f][secondMove][0], formations[f][secondMove][1]);
            }
        }

        //check to see if middle is open (and at least one win possible)
        if (board[1][1] == null) {
            int[] middle = {1, 1};
            if (numUnblocked(middle, board, formations, otherGuy) > 0) {
                return new TicTacToeSpace(1, 1);
            }
        }

        //check to see if corner is open (choose max win possible)
        int[][] corners = {{0, 0}, {0, 2}, {2, 2}, {2, 0}};
        int[] numOpen = {0, 0, 0, 0};
        int maxOpen = 0;
        for (int c = 0; c < 4; c++) {
            if (board[corners[c][0]][corners[c][1]] == null) {
                int openings = numUnblocked(corners[c], board, formations, otherGuy);
                if (openings > maxOpen) {
                    maxOpen = openings;
                }
                numOpen[c] = openings;
            }
        }
        if (maxOpen > 0) {
            for (int c = 0; c < 4; c++) {
                if (numOpen[c] == maxOpen) {
                    return new TicTacToeSpace(corners[c][0], corners[c][1]);
                }
            }
        }

        //choose side square with max win possible
        int[][] sides = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
        int[] numOpenSides = {0, 0, 0, 0};
        int maxOpenSides = 0;
        for (int c = 0; c < 4; c++) {
            if (board[sides[c][0]][sides[c][1]] == null) {
                int openings = numUnblocked(sides[c], board, formations, otherGuy);
                if (openings > maxOpenSides) {
                    maxOpenSides = openings;
                }
                numOpenSides[c] = openings;
            }
        }
        if (maxOpenSides > 0) {
            for (int c = 0; c < 4; c++) {
                if (numOpenSides[c] == maxOpenSides) {
                    return new TicTacToeSpace(sides[c][0], sides[c][1]);
                }
            }
        }

        //choose any open square
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == null) {
                    return new TicTacToeSpace(r, c);
                }
            }
        }
        
        return null;
    }

    //method to check if a formation is near winning
    private int isWinnable(TicTacToePlayerMarker[][] board, int[][] formation, TicTacToePlayerMarker thisMarker, TicTacToePlayerMarker otherMarker) {
        int thisCount = 0;
        int otherCount = 0;
        int winningCell = -1;
        for (int c = 0; c < 3; c++) {
            if (board[formation[c][0]][formation[c][1]] == thisMarker) {
                thisCount++;
            } else if (board[formation[c][0]][formation[c][1]] == otherMarker) {
                otherCount++;
            } else {
                winningCell = c;
            }
        }
        if (thisCount == 2 & otherCount == 0) {
            return winningCell;
        } else {
            return -1;
        }
    }

    //method to check if a cell can be part of a winning formation
    private int numUnblocked(int[] cell, TicTacToePlayerMarker[][] board, int[][][] formations, TicTacToePlayerMarker otherMarker) {
        int numOpenings = 0;
        for (int f = 0; f < 8; f++) {
            boolean cellIncluded = false;
            boolean noOtherGuy = true;
            for (int c = 0; c < 3; c++) {
                if (formations[f][c][0] == cell[0] && formations[f][c][1] == cell[1]) {
                    cellIncluded = true;
                }
                if (board[formations[f][c][0]][formations[f][c][1]] == otherMarker) {
                    noOtherGuy = false;
                }
            }

            if (cellIncluded && noOtherGuy) {
                numOpenings++;
            }
        }

        return numOpenings;
    }

    @Override
    public String getTypeString() {
        return TYPE_STRING;
    }
}
