package com.schroetech.game.tictactoe;

import com.schroetech.game.tictactoe.object.TicTacToePlayerMarker;
import com.schroetech.game.AbstractGame;
import com.schroetech.game.IPlayer;
import com.schroetech.game.tictactoe.object.TicTacToeSpace;
import com.schroetech.game.tictactoe.player.AbstractTicTacToePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Implemetation of the game Tic-Tac-Toe.
 */
public class TicTacToe extends AbstractGame {

    // Private variables
    private static final int NUM_PLAYERS = 2;
    private int moveCount = 0;
    private boolean gameOver = false;
    private String winningPlayerID, currentPlayerID;
    private final TicTacToePlayerMarker[][] board = new TicTacToePlayerMarker[3][3];
    private Map<String, TicTacToePlayerMarker> playerAssignment;

    /**
     * Sets up the game. Assigns player markers and determines turn order.
     */
    public void setup() {

        playerAssignment = new HashMap();

        // randomly assigns players to either X's or O's.
        List<String> playerIds = new ArrayList<>(this.getPlayers().keySet());
        Random randomNumberGenerator = new Random();
        int firstPlayer = randomNumberGenerator.nextInt(2);
        playerAssignment.put(playerIds.get(firstPlayer), TicTacToePlayerMarker.X);
        playerAssignment.put(playerIds.get(1 - firstPlayer), TicTacToePlayerMarker.O);
    }

    @Override
    public void play(boolean display) {

        this.setup();

        if (NUM_PLAYERS != this.getNumPlayers()) {
            System.out.println(this.getNumPlayers());
            throw new IllegalArgumentException("Please add exactly " + NUM_PLAYERS + " players to the game.");
        }

        while (!this.isGameOver()) {
            for (String playerID : this.getPlayers().keySet()) {
                currentPlayerID = playerID;
                TicTacToeSpace moveLocation = ((AbstractTicTacToePlayer) this.getPlayers().get(playerID)).takeTurn(board, playerAssignment.get(playerID));
                move(moveLocation);

                if (display) {
                    printBoardState();
                }

                if (this.isGameOver()) {
                    break;
                }
            }
        }

        if (display) {
            printResults();
        }
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
     * the board. Puts the mark of the current player.
     *
     * @param moveLocation The TicTacToeSpace to put the mark in.
     * @return true if the move was legal, false otherwise.
     */
    public boolean move(TicTacToeSpace moveLocation) {
        if (isValidMove(moveLocation)) {
            board[moveLocation.getRow()][moveLocation.getColumn()] = playerAssignment.get(currentPlayerID);
            moveCount++;
            victoryCheck(moveLocation);
            return true;
        }

        return false;
    }

    /**
     * Checks if the move resulted in victory. If it did, game state is updated
     * to reflect that.
     *
     * @param moveLocation The location to check.
     * @param playerMarker The associated player's mark.
     */
    private void victoryCheck(TicTacToeSpace moveLocation) {

        int row = moveLocation.getRow();
        int col = moveLocation.getColumn();
        TicTacToePlayerMarker playerMarker = playerAssignment.get(currentPlayerID);

        // check column
        if (isWinningCombo(board[row][0], board[row][1], board[row][2], playerMarker)) {
            gameOver = true;
            setWinningPlayerId(currentPlayerID);
        }

        // check row
        if (isWinningCombo(board[0][col], board[1][col], board[2][col], playerMarker)) {
            gameOver = true;
            setWinningPlayerId(currentPlayerID);
        }

        // check diagonal
        if (row == col) {
            if (isWinningCombo(board[0][0], board[1][1], board[2][2], playerMarker)) {
                gameOver = true;
                setWinningPlayerId(currentPlayerID);
            }
        }

        // check reverse diagonal
        if (row + col == 2) {
            if (isWinningCombo(board[0][2], board[1][1], board[2][0], playerMarker)) {
                gameOver = true;
                setWinningPlayerId(currentPlayerID);
            }
        }

        // check for a draw
        if (!isGameOver() && moveCount == 9) {
            gameOver = true;
            setWinningPlayerId(null);
        }
    }

    // --- Getters and Setters ---
    /**
     * Sets the winner of the game.
     *
     * @param playerID String representing the ID of the winning player.
     */
    private void setWinningPlayerId(String playerID) {
        winningPlayerID = playerID;
    }

    @Override
    public String getWinningPlayerId() {
        return winningPlayerID;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getMinNumPlayers() {
        return NUM_PLAYERS;
    }

    @Override
    public int getMaxNumPlayers() {
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
    private boolean isValidMove(TicTacToeSpace moveLocation) {
        return moveLocation.getRow() < 3 && moveLocation.getColumn() < 3
                && board[moveLocation.getRow()][moveLocation.getColumn()] == null;
    }

    // --- Methods printing to the console ---
    @Override
    public void printResults() {

        if (!isGameOver()) {
            return;
        }

        if (getWinningPlayerId() != null) {
            IPlayer winningPlayer = this.getPlayers().get(getWinningPlayerId());
            System.out.println(winningPlayer.getName() + " wins the game!");
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
}
