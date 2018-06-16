package com.schroetech.game.tictactoe;

import com.schroetech.game.tictactoe.object.TicTacToePlayerMarker;
import com.schroetech.game.AbstractGame;
import com.schroetech.game.IPlayer;
import com.schroetech.game.tictactoe.player.AbstractTicTacToePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implemetation of the game Tic Tac Toe.S
 */
public class TicTacToe extends AbstractGame {

    // Private variables
    private static final int NUM_PLAYERS = 2;
    private int moveCount;
    private boolean gameOver;
    private AbstractTicTacToePlayer winner;
    private TicTacToePlayerMarker[][] board = null;
    private Map<TicTacToePlayerMarker, IPlayer> pieceAssignment;

    @Override
    public void setup(ArrayList<IPlayer> players) {

        pieceAssignment = new HashMap();
        // TODO: Make this random.
        Random randomNumberGenerator = new Random();
        int firstPlayer = randomNumberGenerator.nextInt(2);
        pieceAssignment.put(TicTacToePlayerMarker.X, this.getPlayers().get(firstPlayer));
        pieceAssignment.put(TicTacToePlayerMarker.O, this.getPlayers().get(1-firstPlayer));

        board = new TicTacToePlayerMarker[3][3];
        moveCount = 0;
        gameOver = false;
        winner = null;
    }

    /**
     * Return which PlayerMarker is at the given position.
     *
     * @param row The row of the desired position.
     * @param col The column of the desired position.
     * @return PlayerMarker representing who, X's or O's, has played at that
     * position, or null if no one has.
     */
    public TicTacToePlayerMarker getPosition(int row, int col) {
        return board[row][col];
    }

    /**
     * Gets the entire game board.
     *
     * @return Double array of PlayerMarkers representing the current state of
     * the entire game board.
     */
    public TicTacToePlayerMarker[][] getBoard() {
        return board;
    }

    /**
     * Called when a player takes their turn. Represents putting an X or an O on
     * the board.
     *
     * @param row Row of the move.
     * @param col Column of the move.
     * @param playerMarker The associated player's mark.
     * @return true if the move was legal, false otherwise.
     */
    public boolean move(int row, int col, TicTacToePlayerMarker playerMarker) {
        if (isValidMove(row, col)) {
            board[row][col] = playerMarker;
            moveCount++;
            victoryCheck(row, col, playerMarker);
            return true;
        }

        return false;
    }

    @Override
    public IPlayer getWinner(ArrayList<IPlayer> players) {
        return winner;
    }

    /**
     * Checks if the move resulted in victory. If it did, game state is updated
     * to reflect that.
     *
     * @param row Row of the move.
     * @param col Column of the move.
     * @param playerMarker The associated player's mark.
     */
    private void victoryCheck(int row, int col, AbstractTicTacToePlayer currPlayer) {

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

    // --- Methods printing to the console ---
    @Override
    public void printResults() {

        if (!isGameOver()) {
            return;
        }

        if (getWinner() != null) {
            System.out.println(getWinner().getName() + " wins the game!");
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

    // -- Getters and Setters ---
    /**
     * Sets the winner of the game.
     *
     * @param player PlayerMarker of the winning player.S
     */
    private void setWinner(AbstractTicTacToePlayer player) {
        gameOver = true;
        winner = player;
    }

    /**
     * Gets the PlayerMarker of the winning player.
     *
     * @return PlayerMarker of the winning player.
     */
    public AbstractTicTacToePlayer getWinner() {
        return winner;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getMinPlayers() {
        return NUM_PLAYERS;
    }

    @Override
    public int getMaxPlayers() {
        return NUM_PLAYERS;
    }

    // -- Helper functions ---
    /**
     * Determines whether three spaces all have the same marker as the given
     * player's marker.
     *
     * @param marker1 PlayerMarker in the first space to be checked.
     * @param marker2 PlayerMarker in the second space to be checked.
     * @param marker3 PlayerMarker in the third space to be checked.
     * @param curPlayer PlayerMarker of the player to check for winning combos.
     * @return true if all of the markers match curPlayer's marker, false
     * otherwise.
     */
    private boolean isWinningCombo(TicTacToePlayerMarker marker1, TicTacToePlayerMarker marker2, TicTacToePlayerMarker marker3, TicTacToePlayerMarker curPlayer) {
        return curPlayer.equals(marker1) && curPlayer.equals(marker2) && curPlayer.equals(marker3);
    }

    /**
     * Determines whether a given move is legal.
     *
     * @param row Row of the move.
     * @param col Column of the move.
     * @return true if it is a valid move, false otherwise.
     */
    private boolean isValidMove(int row, int col) {
        return row < 3 && col < 3 && board[row][col] == null;
    }

    /**
     * Returns the string form of the marker at the given position.
     *
     * @param row Row of the position to print.
     * @param col Column of the position to print.
     * @return String form of the marker at the given position. Represents null
     * as an empty string of one blank space.S
     */
    private String printPosition(int row, int col) {
        TicTacToePlayerMarker position = getPosition(row, col);
        if (position == null) {
            return " ";
        } else {
            return position.toString();
        }
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
