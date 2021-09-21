package com.crucible.playbook.game.tictactoe;

import com.crucible.playbook.common.game.AbstractSimpleTurnGame;
import com.crucible.playbook.common.persistence.AbstractGameData;
import com.crucible.playbook.game.tictactoe.object.TicTacToePlayerMarker;
import com.crucible.playbook.game.tictactoe.object.TicTacToeSpace;
import com.crucible.playbook.game.tictactoe.persistence.TicTacToeGameData;
import com.crucible.playbook.game.tictactoe.persistence.TicTacToeMoveData;
import com.crucible.playbook.game.tictactoe.player.AbstractTicTacToePlayer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Implementation of the game Tic-Tac-Toe.
 */
public class TicTacToe extends AbstractSimpleTurnGame {

    // Private variables
    private static final int NUM_PLAYERS = 2;
    public static final int MAX_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;
    private int moveCount = 0;
    private final TicTacToePlayerMarker[][] board = new TicTacToePlayerMarker[3][3];
    private Map<String, TicTacToePlayerMarker> playerAssignment;
    private Collection<AbstractGameData> moveData;

    /**
     * Sets up the game. Assigns player markers and determines turn order.
     */
    @Override
    public void setup() {

        playerAssignment = new HashMap();

        // randomly assigns players to either X's or O's.
        List<String> playerIds = new ArrayList<>(this.getPlayers().keySet());
        Random randomNumberGenerator = new Random();
        int firstPlayer = randomNumberGenerator.nextInt(2);
        this.setCurrentPlayerId(playerIds.get(firstPlayer));
        playerAssignment.put(playerIds.get(firstPlayer), TicTacToePlayerMarker.X);
        playerAssignment.put(playerIds.get(1 - firstPlayer), TicTacToePlayerMarker.O);
        moveData = new LinkedList();
    }

    @Override
    public boolean playerTurn() {

        TicTacToeSpace moveLocation = ((AbstractTicTacToePlayer) getPlayers().get(getCurrentPlayerId())).takeTurn(board, playerAssignment.get(getCurrentPlayerId()));

        if (!isValidMove(moveLocation)) {
            return false;
        }

        board[moveLocation.getRow()][moveLocation.getColumn()] = playerAssignment.get(getCurrentPlayerId());
        moveCount++;
        victoryCheck(moveLocation);
        addMoveData(moveLocation);
        return true;
    }
    
    private void addMoveData(TicTacToeSpace moveLocation) {
        TicTacToeMoveData move = new TicTacToeMoveData();
        move.setGameId(gameId);
        move.setMoveId(UUID.randomUUID().toString());
        move.setMoveNumber(moveCount);
        move.setPlayerId(getCurrentPlayerId());
        move.setBoardRow(moveLocation.getRow());
        move.setBoardColumn(moveLocation.getColumn());
        
        moveData.add(move);
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
     * Checks if the move resulted in victory. If it did, game state is updated
     * to reflect that.
     *
     * @param moveLocation The location to check.
     * @param playerMarker The associated player's mark.
     */
    private void victoryCheck(TicTacToeSpace moveLocation) {

        int row = moveLocation.getRow();
        int col = moveLocation.getColumn();
        TicTacToePlayerMarker playerMarker = playerAssignment.get(getCurrentPlayerId());

        // check column
        if (isWinningCombo(board[row][0], board[row][1], board[row][2], playerMarker)) {
            gameOver(getCurrentPlayerId());
        }

        // check row
        if (isWinningCombo(board[0][col], board[1][col], board[2][col], playerMarker)) {
            gameOver(getCurrentPlayerId());
        }

        // check diagonal
        if (row == col) {
            if (isWinningCombo(board[0][0], board[1][1], board[2][2], playerMarker)) {
                gameOver(getCurrentPlayerId());
            }
        }

        // check reverse diagonal
        if (row + col == 2) {
            if (isWinningCombo(board[0][2], board[1][1], board[2][0], playerMarker)) {
                gameOver(getCurrentPlayerId());
            }
        }

        // check for a draw
        if (!isGameOver() && moveCount == 9) {
            gameOver(null);
        }
    }

    // --- Getters and Setters ---
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
    public void displayGameStateToConsole() {
        TicTacToe.clearScreen();
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

    @Override
    public String getName() {
        return "Tic Tac Toe";
    }

    @Override
    public AbstractGameData retrieveGameData() {
        TicTacToeGameData gameData = new TicTacToeGameData();
        gameData.setSessionId(sessionId);
        gameData.setGameId(gameId);
        // set player 1 info
        gameData.setPlayer1Id(this.playerOrder.get(0));
        if (gameData.getPlayer1Id().equals(this.getWinningPlayerId())) {
            gameData.setPlayer1Place(1);
            gameData.setPlayer2Place(2);
        }
        // set player 2 info
        gameData.setPlayer2Id(this.playerOrder.get(1));
        if (gameData.getPlayer2Id().equals(this.getWinningPlayerId())) {
            gameData.setPlayer1Place(2);
            gameData.setPlayer2Place(1);
        }

        return gameData;
    }
    
    @Override
    public Collection<AbstractGameData> retrieveMoveData() {
        return moveData;
    }
}
