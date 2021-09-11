package com.schroetech.playbook.model.cantstop.object;

import com.schroetech.playbook.model.cantstop.player.RandomCantStopPlayer;
import com.schroetech.playbook.model.common.player.IPlayer;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Test class for CantStopBoard.
 */
public class CantStopBoardTest {

    private CantStopBoard board;
    private final Map<String, IPlayer> players = new HashMap();
    private String playerAId;

    @Before
    public void setUp() {
        RandomCantStopPlayer playerA = new RandomCantStopPlayer();
        RandomCantStopPlayer playerB = new RandomCantStopPlayer();

        players.put(playerA.getId(), playerA);
        players.put(playerB.getId(), playerB);

        board = new CantStopBoard(players.values());
        playerAId = playerA.getId();
    }

    /**
     * Test of advanceTempMarkers method, of class CantStopBoard.
     */
    @Test
    public void testAdvanceTempMarkersAndGetTempMarkerPosition() {
        System.out.println("Testing advanceTempMarkers and getTempMarkerPosition");
        assertEquals(0, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(0, board.getTempMarkerPosition(4));
        board.advanceTempMarkers(playerAId, new int[]{2, 4});
        assertEquals(1, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(1, board.getTempMarkerPosition(4));
        board.advanceTempMarkers(playerAId, new int[]{2, 4});
        board.advanceTempMarkers(playerAId, new int[]{2, 4});
        assertEquals(3, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(3, board.getTempMarkerPosition(4));
        // Make sure full column stops advancing.
        board.advanceTempMarkers(playerAId, new int[]{2, 4});
        assertEquals(3, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(4, board.getTempMarkerPosition(4));

        // Test where one marker is left.
        board.advanceTempMarkers(playerAId, new int[]{7, 12});
        assertEquals(3, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(4, board.getTempMarkerPosition(4));
        assertEquals(1, board.getTempMarkerPosition(7));
        assertEquals(0, board.getTempMarkerPosition(12));

        // Test double incrementing one column.
        board.advanceTempMarkers(playerAId, new int[]{7, 7});
        assertEquals(3, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(4, board.getTempMarkerPosition(4));
        assertEquals(3, board.getTempMarkerPosition(7));

        // Passing a single column to increment.
        board.advanceTempMarkers(playerAId, new int[]{4});
        assertEquals(5, board.getTempMarkerPosition(4));

        // Passing no columns to increment.
        board.advanceTempMarkers(playerAId, new int[]{});
        assertEquals(5, board.getTempMarkerPosition(4));
    }

    /**
     * Test of resetTempMarkers method, of class CantStopBoard.
     */
    @Test
    public void testResetTempMarkers() {
        System.out.println("Testing resetTempMarkers");
        int[] columns = {2, 4};
        board.advanceTempMarkers(playerAId, columns);
        board.resetTempMarkers();
        assertEquals(0, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(4));
    }

    /**
     * Test of updatePlayerMarkers method, of class CantStopBoard.
     */
    @Test
    public void testUpdatePlayerMarkersAndGetPlayerMarkers() {
        System.out.println("Testing updatePlayerMarkers and getPlayerMarkers");
        int[] columns = {2, 4};
        assertEquals(0, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(0, board.getTempMarkerPosition(4));
        board.advanceTempMarkers(playerAId, columns);
        board.advanceTempMarkers(playerAId, columns);
        board.advanceTempMarkers(playerAId, columns);
        assertEquals(3, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(3, board.getTempMarkerPosition(4));
        assertEquals(0, board.getPlayerMarkerPosition(playerAId, 2));
        assertEquals(0, board.getPlayerMarkerPosition(playerAId, 3));
        assertEquals(0, board.getPlayerMarkerPosition(playerAId, 4));
        board.updatePlayerMarkers(playerAId);
        assertEquals(0, board.getTempMarkerPosition(2));
        assertEquals(0, board.getTempMarkerPosition(3));
        assertEquals(0, board.getTempMarkerPosition(4));
        assertEquals(3, board.getPlayerMarkerPosition(playerAId, 2));
        assertEquals(0, board.getPlayerMarkerPosition(playerAId, 3));
        assertEquals(3, board.getPlayerMarkerPosition(playerAId, 4));
    }

    /**
     * Test of isOpenColumn method, of class CantStopBoard.
     */
    @Test
    public void testIsOpenColumn() {
        System.out.println("Testing isOpenColumn");
        assertTrue(board.isOpenColumn(2));
        assertTrue(board.isOpenColumn(3));
        assertTrue(board.isOpenColumn(4));
        int[] columns = {2, 3};
        board.advanceTempMarkers(playerAId, columns);
        board.advanceTempMarkers(playerAId, columns);
        assertTrue(board.isOpenColumn(2));
        board.advanceTempMarkers(playerAId, columns);
        assertFalse(board.isOpenColumn(2));
        assertTrue(board.isOpenColumn(3));
        assertTrue(board.isOpenColumn(4));
        board.advanceTempMarkers(playerAId, columns);
        board.advanceTempMarkers(playerAId, columns);
        assertFalse(board.isOpenColumn(2));
        assertFalse(board.isOpenColumn(3));
        assertTrue(board.isOpenColumn(4));
        int[] singleColumn = {4};
        // Test false when three other columns have temp markers.
        board.advanceTempMarkers(playerAId, singleColumn);
        assertTrue(board.isOpenColumn(4));
        assertFalse(board.isOpenColumn(5));
        // Completed columns shouldn't be open.
        board.updatePlayerMarkers(playerAId);
        assertFalse(board.isOpenColumn(2));
        assertFalse(board.isOpenColumn(3));
        assertTrue(board.isOpenColumn(4));
        assertTrue(board.isOpenColumn(5));
    }

    /**
     * Test of getNumTempMarkers method, of class CantStopBoard.
     */
    @Test
    public void testGetNumTempMarkers() {
        System.out.println("Testing getNumTempMarkers");
        int[] columns = {2, 3};
        assertEquals(0, board.getNumTempMarkers());
        board.advanceTempMarkers(playerAId, columns);
        assertEquals(2, board.getNumTempMarkers());
        board.advanceTempMarkers(playerAId, columns);
        assertEquals(2, board.getNumTempMarkers());
        int[] moreColumns = {5, 7};
        board.advanceTempMarkers(playerAId, moreColumns);
        assertEquals(3, board.getNumTempMarkers());
        board.resetTempMarkers();
        assertEquals(0, board.getNumTempMarkers());
    }

    /**
     * Test of getNumAscendedColumnsByPlayerId method, of class CantStopBoard.
     */
    @Test
    public void testGetNumAscendedColumnsByPlayerId() {
        System.out.println("Testing getNumAscendedColumnsByPlayerId");
        assertEquals(0, board.getNumAscendedColumnsByPlayerId(playerAId));
        int[] columns = {2, 3};
        board.advanceTempMarkers(playerAId, columns);
        board.advanceTempMarkers(playerAId, columns);
        board.advanceTempMarkers(playerAId, columns);
        // Column 2 advanced.
        assertEquals(0, board.getNumAscendedColumnsByPlayerId(playerAId));
        board.updatePlayerMarkers(playerAId);
        assertEquals(1, board.getNumAscendedColumnsByPlayerId(playerAId));
        int[] moreColumns = {3, 12};
        board.advanceTempMarkers(playerAId, moreColumns);
        board.advanceTempMarkers(playerAId, moreColumns);
        board.advanceTempMarkers(playerAId, moreColumns);
        // Columns 2, 3 and 4 advanced.
        assertEquals(1, board.getNumAscendedColumnsByPlayerId(playerAId));
        board.updatePlayerMarkers(playerAId);
        assertEquals(3, board.getNumAscendedColumnsByPlayerId(playerAId));
    }
}
