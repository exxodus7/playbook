package com.schroetech.game.tictactoe;

import com.schroetech.game.tictactoe.object.PlayerMarker;
import com.schroetech.game.AbstractGame;
import com.schroetech.game.IPlayer;
import com.schroetech.game.tictactoe.player.AbstractTicTacToePlayer;
import java.util.ArrayList;

/**
 *
 * @author lauren
 */
public class TicTacToe extends AbstractGame {

    private int moveCount = 0;
    private static final int NUM_PLAYERS = 2;
    private boolean gameOver = false;
    private PlayerMarker winner = null;
    private PlayerMarker[][] board = null;

    @Override
    public void setup(ArrayList<IPlayer> players) {

        ((AbstractTicTacToePlayer) players.get(0)).setPlayerMarker(PlayerMarker.X);
        ((AbstractTicTacToePlayer) players.get(1)).setPlayerMarker(PlayerMarker.O);

        board = new PlayerMarker[3][3];
    }

    public PlayerMarker getPosition(int row, int col) {
        return board[row][col];
    }

    public PlayerMarker[][] getBoard() {
        return board;
    }

    public boolean move(int row, int col, PlayerMarker playerMarker) {
        if (isValidMove(row, col)) {
            board[row][col] = playerMarker;
            moveCount++;
            victoryCheck(row, col, playerMarker);
            return true;
        }

        return false;
    }

    private void victoryCheck(int row, int col, PlayerMarker playerMarker) {

        // check column
        if (isWinningCombo(board[row][0], board[row][1], board[row][2], playerMarker)) {
            setWinner(playerMarker);
        }

        // check row
        if (isWinningCombo(board[0][col], board[1][col], board[2][col], playerMarker)) {
            setWinner(playerMarker);
        }

        // check diagonal
        if (row == col) {
            if (isWinningCombo(board[0][0], board[1][1], board[2][2], playerMarker)) {
                setWinner(playerMarker);
            }
        }

        // check reverse diagonal
        if (row + col == 2) {
            if (isWinningCombo(board[0][2], board[1][1], board[2][0], playerMarker)) {
                setWinner(playerMarker);
            }
        }

        //check draw
        if (!gameOver && moveCount == 9) {
            setWinner(null);
        }
    }

    private void setWinner(PlayerMarker player) {
        gameOver = true;
        winner = player;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    public PlayerMarker getWinner() {
        return winner;
    }

    private boolean isWinningCombo(PlayerMarker marker1, PlayerMarker marker2, PlayerMarker marker3, PlayerMarker curPlayer) {
        if (curPlayer.equals(marker1) && curPlayer.equals(marker2) && curPlayer.equals(marker3)) {
            return true;
        }

        return false;
    }

    private boolean isValidMove(int row, int col) {
        return row < 3 && col < 3 && board[row][col] == null;
    }

    @Override
    public void printResults() {
        if (getWinner() != null) {
            System.out.println(getWinner().toString() + "'s win the game!");
        } else {
            System.out.println("The game ended in a draw.");
        }
        System.out.println("Final board setup:");
        printBoardState();
    }

    @Override
    public void printBoardState() {
        System.out.println(printPosition(0, 0) + "|" + printPosition(0, 1) + "|" + printPosition(0, 2));
        System.out.println("-----");
        System.out.println(printPosition(1, 0) + "|" + printPosition(1, 1) + "|" + printPosition(1, 2));
        System.out.println("-----");
        System.out.println(printPosition(2, 0) + "|" + printPosition(2, 1) + "|" + printPosition(2, 2));
        System.out.println();
    }

    private String printPosition(int row, int col) {
        PlayerMarker position = getPosition(row, col);
        if (position == null) {
            return " ";
        } else {
            return position.toString();
        }
    }

    @Override
    public int getMinPlayers() {
        return NUM_PLAYERS;
    }

    @Override
    public int getMaxPlayers() {
        return NUM_PLAYERS;
    }
}
