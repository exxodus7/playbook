package com.crucible.playbook.game.cantstop.object;

import com.crucible.playbook.common.game.player.IPlayer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

/**
 * The board in a Can't Stop game. Holds all information about where players'
 * markers are as well as any temporary markers. Also contains useful methods
 * for getting information about the board state.
 */
public class CantStopBoard {

    private static final String TEMP_MARKER_KEY = "temp-marker";
    private static final String TEMP_MARKER_ICON = "t";
    public static final int[] COLUMN_SIZES = {3, 5, 7, 9, 11, 13, 11, 9, 7, 5, 3};
    private final Map<String, int[]> board = new HashMap();
    private final Map<String, IPlayer> players = new HashMap();

    /**
     * Constructor which takes a map of the players' IDs to the player objects
     *
     * @param newPlayers
     */
    public CantStopBoard(Collection<IPlayer> newPlayers) {

        board.put(TEMP_MARKER_KEY, new int[11]);
        for (IPlayer newPlayer : newPlayers) {
            board.put(newPlayer.getId(), new int[11]);
            players.put(newPlayer.getId(), newPlayer);
        }
    }

    /**
     * Sets the Temp Markers to 0.
     */
    public void resetTempMarkers() {
        Arrays.fill(board.get(TEMP_MARKER_KEY), 0);
    }

    /**
     * Increases the temp markers based on the chosen columns, if possible. If
     * only one can be chosen, preference is given to the first column.
     *
     * @param playerId ID of the current player.
     * @param columns integer array which represents which two columns to
     * increment the temp markers.
     */
    public void advanceTempMarkers(String playerId, int[] columns) {
        int[] tempMarkerState = board.get(TEMP_MARKER_KEY);
        int[] playerState = board.get(playerId);

        for (int i = 0; i < columns.length; i++) {
            if (isOpenColumn(columns[i])) {
                int columnIndex = columns[i] - 2;
                if (tempMarkerState[columnIndex] > 0) {
                    tempMarkerState[columnIndex]++;
                } else {
                    tempMarkerState[columnIndex] = playerState[columnIndex] + 1;
                }
            }
        }

        // TODO: Do I need this?
        board.put(TEMP_MARKER_KEY, tempMarkerState);
    }

    /**
     * Updates the current players markers to the positions of the temp markers.
     * Temp markers are reset.
     *
     * @param playerToUpdate ID of the player whose markers will be updated to
     * the temp marker positions.
     */
    public void updatePlayerMarkers(String playerToUpdate) {
        int[] tempMarkerState = board.get(TEMP_MARKER_KEY);
        int[] playerState = board.get(playerToUpdate);

        for (int i = 0; i < tempMarkerState.length; i++) {
            if (tempMarkerState[i] > 0) {
                playerState[i] = tempMarkerState[i];
            }

            // If column ascended, set opponents' markers to 0 in that column.
            if (tempMarkerState[i] == COLUMN_SIZES[i]) {
                for (String playerId : board.keySet()) {
                    if (!playerToUpdate.equals(playerId)) {
                        board.get(playerId)[i] = 0;
                    }
                }
            }
        }

        resetTempMarkers();
    }

    /**
     * Returns true of the given column is available. Is considered not
     * available if it has been ascended by any of the players or a temp marker.
     *
     * @param column Column to check.
     * @return Whether the column is open.
     */
    public boolean isOpenColumn(int column) {

        int columnIndex = column - 2;

        // Check to see if there is a free temp marker
        if (getNumTempMarkers() >= 3) {
            if (this.getTempMarkerPosition(column) == 0) {
                return false;
            }
        }

        // Check for completed columns
        int totalColumnSpaces = COLUMN_SIZES[columnIndex];
        for (int[] playerState : board.values()) {
            if (playerState.length >= columnIndex && playerState[columnIndex] >= totalColumnSpaces) {
                return false;
            }
        }

        return true;
    }

    /**
     * How many temp markers, of three possible, are currently on the board.
     *
     * @return An int between 0 and 3, representing the number of temp markers.
     */
    public int getNumTempMarkers() {
        int numTempMarkers = 0;
        for (int tempMarker : board.get(TEMP_MARKER_KEY)) {
            if (tempMarker > 0) {
                numTempMarkers++;
            }
        }

        return numTempMarkers;
    }

    /**
     * How many columns the given player has ascended.
     *
     * @param playerId ID of the player to be checked.
     * @return Number of ascended columns.
     */
    public int getNumAscendedColumnsByPlayerId(String playerId) {

        int numAscendedColumns = 0;
        if (board.keySet().contains(playerId)) {
            int[] playerMarkerPositions = board.get(playerId);
            for (int i = 0; i < COLUMN_SIZES.length; i++) {
                if (COLUMN_SIZES[i] == playerMarkerPositions[i]) {
                    numAscendedColumns++;
                }
            }
        }

        return numAscendedColumns;
    }

    /**
     * Returns the temp marker position on a given column.
     *
     * @param column The column to check.
     * @return The position that the temp marker is on in that column.
     */
    public int getTempMarkerPosition(int column) {
        return getPlayerMarkerPosition(TEMP_MARKER_KEY, column);
    }

    /**
     * Returns the player's marker position on a given column.
     *
     * @param playerId The ID of the player's whose position will be checked.
     * @param column The column to check.
     * @return The position that the player's marker is on in that column.
     */
    public int getPlayerMarkerPosition(String playerId, int column) {
        return board.get(playerId)[column - 2];
    }

    /**
     * Prints a picture representing the state of the board to the console.
     */
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
            char[] blankChars = new char[colSize[i - 1] + 1];
            Arrays.fill(blankChars, ' ');
            leadingWhitespace[i] = leadingWhitespace[i - 1] + new String(blankChars);
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

    /**
     * Returns a one-letter representation of the player at the given position
     * on the board. If there are multiple players, returns both players'
     * letters. Fills in the remaining spaces of the column, if any, with "^" if
     * the row is the top-most in the column or "*" otherwise.
     *
     * @param column The column of the marker.
     * @param row The row of the marker.
     * @param colSize Size of the column.
     * @return The player(s) marker(s), or other symbols as dictated above.
     */
    private String getMarker(int column, int row, int[] colSize) {

        String markers = "";

        for (String playerId : board.keySet()) {
            int[] playerState = board.get(playerId);
            // If column completed, fill in all rows with players marker.
            if (playerState[column] == COLUMN_SIZES[column] && !playerId.equals(TEMP_MARKER_KEY)) {
                return " " + this.players.get(playerId).getName().toUpperCase().substring(0, 1);
            } else if (playerState[column] == row) {
                if (playerId.equals(TEMP_MARKER_KEY)) {
                    markers += TEMP_MARKER_ICON;
                } else {
                    markers += this.players.get(playerId).getName().toUpperCase().substring(0, 1);
                }
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
}
