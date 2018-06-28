package com.schroetech.game.cantstop;

import com.schroetech.game.AbstractSimpleTurnGame;
import com.schroetech.game.cantstop.player.AbstractCantStopPlayer;
import com.schroetech.game.object.Dice;
import com.schroetech.game.util.GameUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Implementation of the game Can't Stop.
 */
public class CantStop extends AbstractSimpleTurnGame {

    public static final String TEMP_MARKER_KEY = "temp-marker";
    private static final String TEMP_MARKER_ICON = "t";
    public static final int[] COLUMN_SIZES = {3, 5, 7, 9, 11, 13, 11, 9, 7, 5, 3};
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private final Map<String, int[]> board = new HashMap();

    @Override
    protected void setup() {

        board.put(TEMP_MARKER_KEY, new int[11]);
        for (String playerID : this.getPlayers().keySet()) {
            board.put(playerID, new int[11]);
        }
    }

    @Override
    public boolean playerTurn() {

        AbstractCantStopPlayer currentPlayer = (AbstractCantStopPlayer) this.getPlayers().get(this.getCurrentPlayerID());
        Collection<Dice> dice = GameUtils.getDice(4, 6);
        System.out.println(currentPlayer.getName() + "'s turn");

        boolean continueTurn = true;
        while (continueTurn) {

            GameUtils.rollDice(dice);
            System.out.println("Dice: " + GameUtils.getDiceAsString(dice));

            if (CantStop.getPossibleMoves(board, dice).isEmpty()) {
                System.out.println("No possible Moves!");
                resetTempMarkers();
                continueTurn = false;
            } else {
                int[] columns = currentPlayer.takeTurn(board, dice);
                System.out.println("Columns: " + Arrays.toString(columns));

                if (columns.length != 2 || !isValidMove(board, dice, columns[0], columns[1])) {
                    System.out.println("Invalid move");
                    return false;
                }

                updateTempMarkers(columns[0], columns[1]);
                continueTurn = currentPlayer.continueOrStop(board);
                System.out.println("Continue? " + continueTurn);
            }

            printBoardState();
            try {
                Thread.sleep(750);
            } catch (InterruptedException ex) {
                Logger.getLogger(CantStop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        updatePlayerMarkers();
        victoryCheck();

        System.out.println("Turn Over");
        return true;
    }

    private void updatePlayerMarkers() {
        int[] tempMarkerState = board.get(TEMP_MARKER_KEY);
        int[] playerState = board.get(this.getCurrentPlayerID());

        for (int i = 0; i < tempMarkerState.length; i++) {
            if (tempMarkerState[i] > 0) {
                playerState[i] = tempMarkerState[i];
            }

            // If column ascended, set opponents' markers to 0 in that column.
            if (tempMarkerState[i] == COLUMN_SIZES[i]) {
                for (String playerId : board.keySet()) {
                    if (!this.getCurrentPlayerID().equals(playerId)) {
                        int[] opponentState = board.get(playerId);
                        opponentState[i] = 0;
                        board.put(playerId, opponentState);
                    }
                }
            }
        }

        board.put(this.getCurrentPlayerID(), playerState);
        resetTempMarkers();
    }

    private void resetTempMarkers() {
        Arrays.fill(board.get(TEMP_MARKER_KEY), 0);
    }

    private void updateTempMarkers(int columnA, int columnB) {
        int[] tempMarkerState = board.get(TEMP_MARKER_KEY);
        int[] playerState = board.get(this.getCurrentPlayerID());
        int columnAIndex = columnA - 2;
        int columnBIndex = columnB - 2;

        if (CantStop.isOpenColumn(board, columnA)) {
            if (tempMarkerState[columnAIndex] > 0) {
                tempMarkerState[columnAIndex]++;
            } else {
                tempMarkerState[columnAIndex] = playerState[columnAIndex] + 1;
            }
        }

        if (CantStop.isOpenColumn(board, columnB)) {
            if (tempMarkerState[columnBIndex] > 0) {
                tempMarkerState[columnBIndex]++;
            } else {
                tempMarkerState[columnBIndex] = playerState[columnBIndex] + 1;
            }
        }

        board.put(TEMP_MARKER_KEY, tempMarkerState);
    }

    private void victoryCheck() {
        int[] playerState = board.get(this.getCurrentPlayerID());
        int numColumnsAscended = 0;

        for (int i = 0; i < playerState.length; i++) {
            if (playerState[i] == COLUMN_SIZES[i]) {
                numColumnsAscended++;
            }
        }

        if (numColumnsAscended >= 3) {
            gameOver(this.getCurrentPlayerID());
        }
    }

    private int[] rollDice() {

        int[] dice = new int[4];
        Random randomNumberGenerator = new Random();
        dice[0] = randomNumberGenerator.nextInt(6) + 1;
        dice[1] = randomNumberGenerator.nextInt(6) + 1;
        dice[2] = randomNumberGenerator.nextInt(6) + 1;
        dice[3] = randomNumberGenerator.nextInt(6) + 1;

        return dice;
    }

    public static boolean isValidMove(Map<String, int[]> board, Collection<Dice> dice, int columnA, int columnB) {

        int[] diceValues = GameUtils.getDiceValues(dice);

        boolean isValid = false;
        if ((diceValues[0] + diceValues[1] == columnA) && (diceValues[2] + diceValues[3] == columnB)) {
            isValid = true;
        } else if (((diceValues[0] + diceValues[2]) == columnA) && ((diceValues[1] + diceValues[3]) == columnB)) {
            isValid = true;
        } else if (((diceValues[0] + diceValues[3]) == columnA) && ((diceValues[1] + diceValues[2]) == columnB)) {
            isValid = true;
        }

        if (isValid) {
            isValid = CantStop.isOpenColumn(board, columnA) || CantStop.isOpenColumn(board, columnB);
        }

        return isValid;
    }

    public static boolean isOpenColumn(Map<String, int[]> board, int column) {

        int columnIndex = column - 2;

        if (CantStop.COLUMN_SIZES.length <= columnIndex) {
            return false;
        }

        // Check to see if there is a free temp marker
        int[] tempMarkerState = board.get(TEMP_MARKER_KEY);
        if (tempMarkerState[columnIndex] == 0) {
            int numTempMarkers = 0;
            for (int markerPosition : tempMarkerState) {
                if (markerPosition > 0) {
                    numTempMarkers++;
                }
            }

            // Already three temp markers on different columns.
            if (numTempMarkers >= 3) {
                return false;
            }
        }

        // Check for completed columns
        int totalColumnSpaces = CantStop.COLUMN_SIZES[columnIndex];
        for (int[] playerState : board.values()) {
            if (playerState.length >= columnIndex && playerState[columnIndex] >= totalColumnSpaces) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getMinNumPlayers() {
        return MIN_PLAYERS;
    }

    @Override
    public int getMaxNumPlayers() {
        return MAX_PLAYERS;
    }

    @Override
    public void printResults() {

    }

    @Override
    public void printBoardState() {

        int[] colSize = new int[11];
        for (int column = 0; column < COLUMN_SIZES.length; column++) {
            int[] numInPosOnCol = new int[COLUMN_SIZES[column]];
            for (String playerId : board.keySet()) {
                int playersMarkerPos = board.get(playerId)[column];
                if (playersMarkerPos > 0) {
                    numInPosOnCol[playersMarkerPos - 1]++;
                }
            }

            List b = Arrays.asList(ArrayUtils.toObject(numInPosOnCol));
            colSize[column] = (Integer) Collections.max(b);
            // Add space for asterisk on empty column.
            if (colSize[column] == 0) {
                colSize[column] = 1;
            }
        }

        String[] leadingWhitespace = new String[6];
        leadingWhitespace[0] = "";

        for (int i = 1; i <= 5; i++) {
            leadingWhitespace[i] = leadingWhitespace[i - 1] + getWhitespace(colSize[i - 1] + 1);
        }

        System.out.println(leadingWhitespace[5] + getMarker(5, 13, colSize));
        System.out.println(leadingWhitespace[4] + getMarker(4, 11, colSize)
                + getMarker(5, 12, colSize) + getMarker(6, 11, colSize));
        System.out.println(leadingWhitespace[3] + getMarker(3, 9, colSize)
                + getMarker(4, 10, colSize) + getMarker(5, 11, colSize) + getMarker(6, 10, colSize)
                + getMarker(7, 9, colSize));
        System.out.println(leadingWhitespace[2] + getMarker(2, 7, colSize)
                + getMarker(3, 8, colSize) + getMarker(4, 9, colSize) + getMarker(5, 10, colSize)
                + getMarker(6, 9, colSize) + getMarker(7, 8, colSize) + getMarker(8, 7, colSize));
        System.out.println(leadingWhitespace[1] + getMarker(1, 5, colSize)
                + getMarker(2, 6, colSize) + getMarker(3, 7, colSize) + getMarker(4, 8, colSize)
                + getMarker(5, 9, colSize) + getMarker(6, 8, colSize) + getMarker(7, 7, colSize)
                + getMarker(8, 6, colSize) + getMarker(9, 5, colSize));
        System.out.println(leadingWhitespace[0] + getMarker(0, 3, colSize)
                + getMarker(1, 4, colSize) + getMarker(2, 5, colSize) + getMarker(3, 6, colSize)
                + getMarker(4, 7, colSize) + getMarker(5, 8, colSize) + getMarker(6, 7, colSize)
                + getMarker(7, 6, colSize) + getMarker(8, 5, colSize) + getMarker(9, 4, colSize)
                + getMarker(10, 3, colSize));
        System.out.println(leadingWhitespace[0] + getMarker(0, 2, colSize)
                + getMarker(1, 3, colSize) + getMarker(2, 4, colSize) + getMarker(3, 5, colSize)
                + getMarker(4, 6, colSize) + getMarker(5, 7, colSize) + getMarker(6, 6, colSize)
                + getMarker(7, 5, colSize) + getMarker(8, 4, colSize) + getMarker(9, 3, colSize)
                + getMarker(10, 2, colSize));
        System.out.println(leadingWhitespace[0] + getMarker(0, 1, colSize)
                + getMarker(1, 2, colSize) + getMarker(2, 3, colSize) + getMarker(3, 4, colSize)
                + getMarker(4, 5, colSize) + getMarker(5, 6, colSize) + getMarker(6, 5, colSize)
                + getMarker(7, 4, colSize) + getMarker(8, 3, colSize) + getMarker(9, 2, colSize)
                + getMarker(10, 1, colSize));
        System.out.println(leadingWhitespace[1] + getMarker(1, 1, colSize)
                + getMarker(2, 2, colSize) + getMarker(3, 3, colSize) + getMarker(4, 4, colSize)
                + getMarker(5, 5, colSize) + getMarker(6, 4, colSize) + getMarker(7, 3, colSize)
                + getMarker(8, 2, colSize) + getMarker(9, 1, colSize));
        System.out.println(leadingWhitespace[2] + getMarker(2, 1, colSize)
                + getMarker(3, 2, colSize) + getMarker(4, 3, colSize) + getMarker(5, 4, colSize)
                + getMarker(6, 3, colSize) + getMarker(7, 2, colSize) + getMarker(8, 1, colSize));
        System.out.println(leadingWhitespace[3] + getMarker(3, 1, colSize)
                + getMarker(4, 2, colSize) + getMarker(5, 3, colSize) + getMarker(6, 2, colSize)
                + getMarker(7, 1, colSize));
        System.out.println(leadingWhitespace[4] + getMarker(4, 1, colSize)
                + getMarker(5, 2, colSize) + getMarker(6, 1, colSize));
        System.out.println(leadingWhitespace[5] + getMarker(5, 1, colSize));
    }

    private String getMarker(int column, int row, int[] colSize) {

        String markers = "";

        for (String playerId : board.keySet()) {
            int[] playerState = board.get(playerId);
            // If column completed, fill in all rows with players marker.
            if (playerState[column] == COLUMN_SIZES[column] && !playerId.equals(TEMP_MARKER_KEY)) {
                return " " + getPlayerIcon(playerId);
            } else if (playerState[column] == row) {
                markers += getPlayerIcon(playerId);
            }
        }

        if (markers.isEmpty() && row == COLUMN_SIZES[column]) {
            markers = "^";
        }

        for (int i = markers.length(); i < colSize[column]; i++) {
            if (row == COLUMN_SIZES[column]) {
                markers += "^";
            } else {
                markers += "*";
            }
        }

        return " " + markers;
    }

    private String getPlayerIcon(String playerId) {
        if (playerId.equals(TEMP_MARKER_KEY)) {
            return TEMP_MARKER_ICON;
        } else {
            return this.getPlayers().get(playerId).getName().toUpperCase().substring(0, 1);
        }
    }

    private String getWhitespace(int length) {
        char[] blankChars = new char[length];
        Arrays.fill(blankChars, ' ');
        return new String(blankChars);
    }

    public static List<int[]> getPossibleMoves(Map<String, int[]> board, Collection<Dice> dice) {
        List<int[]> possibilities = new ArrayList();

        int[] diceValues = GameUtils.getDiceValues(dice);

        int colA = diceValues[0] + diceValues[1];
        int colB = diceValues[2] + diceValues[3];
        if (CantStop.isOpenColumn(board, colA) || CantStop.isOpenColumn(board, colB)) {
            possibilities.add(createPossibility(colA, colB));
        }

        colA = diceValues[0] + diceValues[2];
        colB = diceValues[1] + diceValues[3];
        if (CantStop.isOpenColumn(board, colA) || CantStop.isOpenColumn(board, colB)) {
            possibilities.add(createPossibility(colA, colB));
        }

        colA = diceValues[0] + diceValues[3];
        colB = diceValues[1] + diceValues[2];
        if (CantStop.isOpenColumn(board, colA) || CantStop.isOpenColumn(board, colB)) {
            possibilities.add(createPossibility(colA, colB));
        }

        return possibilities;
    }

    private static int[] createPossibility(int columnA, int columnB) {
        int[] possibility = new int[2];
        possibility[0] = columnA;
        possibility[1] = columnB;
        return possibility;
    }
}
