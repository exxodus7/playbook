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
 * Implemetation of the game Tic Tac Toe.
 */
public class TicTacToe extends AbstractGame {

    // Private variables
    private static final int NUM_PLAYERS = 2;
    private int moveCount = 0;
    private boolean gameOver = false;
    private String winningPlayerId;
    private final TicTacToePlayerMarker[][] board = new TicTacToePlayerMarker[3][3];
    private Map<String, TicTacToePlayerMarker> playerAssignment;
    private String currentPlayerId;

    public void setup() {

        playerAssignment = new HashMap();

        List<String> playerIds = new ArrayList<>(this.getPlayers().keySet());
        Random randomNumberGenerator = new Random();
        int firstPlayer = randomNumberGenerator.nextInt(2);
        playerAssignment.put(playerIds.get(firstPlayer), TicTacToePlayerMarker.X);
        playerAssignment.put(playerIds.get(1 - firstPlayer), TicTacToePlayerMarker.O);
    }

    @Override
    public void play() {
        
        this.setup();
        
        if (NUM_PLAYERS != this.getNumPlayers()) {
            System.out.println(this.getNumPlayers());
            throw new IllegalArgumentException("Please add exactly " + NUM_PLAYERS + " players to the game.");
        }

        while (!this.isGameOver()) {
            for (String playerId : this.getPlayers().keySet()) {
                currentPlayerId = playerId;
                TicTacToeSpace moveLocation = ((AbstractTicTacToePlayer) this.getPlayers().get(playerId)).takeTurn(board, playerAssignment.get(playerId));
                move(moveLocation);
                
                if (this.isGameOver())
                    break;
            }
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
     * the board.
     *
     * @param row Row of the move.
     * @param col Column of the move.
     * @param playerMarker The associated player's mark.
     * @return true if the move was legal, false otherwise.
     */
    public boolean move(TicTacToeSpace moveLocation) {
        if (isValidMove(moveLocation)) {
            board[moveLocation.getRow()][moveLocation.getColumn()] = playerAssignment.get(currentPlayerId);
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
     * @param row Row of the move.
     * @param col Column of the move.
     * @param playerMarker The associated player's mark.
     */
    private void victoryCheck(TicTacToeSpace moveLocation) {

        int row = moveLocation.getRow();
        int col = moveLocation.getColumn();
        TicTacToePlayerMarker playerMarker = playerAssignment.get(currentPlayerId);

        // check column
        if (isWinningCombo(board[row][0], board[row][1], board[row][2], playerMarker)) {
            setWinningPlayerId(currentPlayerId);
        }

        // check row
        if (isWinningCombo(board[0][col], board[1][col], board[2][col], playerMarker)) {
            setWinningPlayerId(currentPlayerId);
        }

        // check diagonal
        if (row == col) {
            if (isWinningCombo(board[0][0], board[1][1], board[2][2], playerMarker)) {
                setWinningPlayerId(currentPlayerId);
            }
        }

        // check reverse diagonal
        if (row + col == 2) {
            if (isWinningCombo(board[0][2], board[1][1], board[2][0], playerMarker)) {
                setWinningPlayerId(currentPlayerId);
            }
        }

        //check draw
        if (!gameOver && moveCount == 9) {
            setWinningPlayerId(null);
        }
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

    // -- Getters and Setters ---
    /**
     * Sets the winner of the game.
     *
     * @param player PlayerMarker of the winning player.S
     */
    private void setWinningPlayerId(String playerId) {
        gameOver = true;
        winningPlayerId = playerId;
    }

    @Override
    public String getWinningPlayerId() {
        return winningPlayerId;
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
    private boolean isValidMove(TicTacToeSpace moveLocation) {
        return moveLocation.getRow() < 3 && moveLocation.getColumn() < 3
                && board[moveLocation.getRow()][moveLocation.getColumn()] == null;
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
