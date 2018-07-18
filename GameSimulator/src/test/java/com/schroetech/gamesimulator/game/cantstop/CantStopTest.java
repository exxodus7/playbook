package com.schroetech.gamesimulator.game.cantstop;

import com.schroetech.gamesimulator.game.cantstop.object.CantStopBoard;
import com.schroetech.gamesimulator.game.cantstop.player.AbstractCantStopPlayer;
import com.schroetech.gamesimulator.game.cantstop.player.RandomCantStopPlayer;
import com.schroetech.gamesimulator.common.object.Dice;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for CantStop.
 */
public class CantStopTest extends CantStop {

    private boolean tookTurn = false;

    @Before
    public void setUp() {
        AbstractCantStopPlayerImpl player = new AbstractCantStopPlayerImpl();
        this.addPlayer(player);
        this.addPlayer(new RandomCantStopPlayer());

        this.setup();
        this.setCurrentPlayerId(player.getId());
    }

    /**
     * Test of playerTurn method, of class CantStop.
     */
    @Test
    public void testPlayerTurn() {
        System.out.println("Testing playerTurn");
        this.playerTurn();
        assertTrue(tookTurn);
    }

    /**
     * Test of getPossibleMoves method, of class CantStop.
     */
    @Test
    public void testGetPossibleMoves() {
        Dice dieA = new Dice(6);
        Dice dieB = new Dice(6);
        Dice dieC = new Dice(6);
        Dice dieD = new Dice(6);

        dieA.setValue(1);
        dieB.setValue(1);
        dieC.setValue(3);
        dieD.setValue(4);

        Collection<Dice> dice = new LinkedList();
        dice.add(dieA);
        dice.add(dieB);
        dice.add(dieC);
        dice.add(dieD);

        Collection<int[]> moves = this.getPossibleMoves(dice);
        assertEquals(2, moves.size());
        assertTrue(contains(moves, new int[]{2, 7}));
        assertTrue(contains(moves, new int[]{4, 5}));

        dieC.setValue(1);
        dieD.setValue(1);
        moves = this.getPossibleMoves(dice);
        assertEquals(1, moves.size());
        assertTrue(contains(moves, new int[]{2, 2}));

        // Test when a column is ascended by a temp marker.
        this.board.advanceTempMarkers(this.getCurrentPlayerId(), new int[]{2, 2});
        this.board.advanceTempMarkers(this.getCurrentPlayerId(), new int[]{2, 2});
        moves = this.getPossibleMoves(dice);
        assertEquals(0, moves.size());

        dieC.setValue(3);
        dieD.setValue(4);
        moves = this.getPossibleMoves(dice);
        assertEquals(2, moves.size());
        assertTrue(contains(moves, new int[]{7}));
        assertTrue(contains(moves, new int[]{4, 5}));

        // Test when there are two temp markers.
        this.board.advanceTempMarkers(this.getCurrentPlayerId(), new int[]{9});
        moves = this.getPossibleMoves(dice);
        assertEquals(3, moves.size());
        assertTrue(contains(moves, new int[]{7}));
        assertTrue(contains(moves, new int[]{4}));
        assertTrue(contains(moves, new int[]{5}));

        this.board.updatePlayerMarkers(this.getCurrentPlayerId());
        moves = this.getPossibleMoves(dice);
        assertEquals(2, moves.size());
        assertTrue(contains(moves, new int[]{7}));
        assertTrue(contains(moves, new int[]{4, 5}));

        dieA.setValue(2);
        dieB.setValue(2);
        this.board.advanceTempMarkers(this.getCurrentPlayerId(), new int[]{4, 11});
        this.board.advanceTempMarkers(this.getCurrentPlayerId(), new int[]{12});
        moves = this.getPossibleMoves(dice);
        assertEquals(1, moves.size());
        assertTrue(contains(moves, new int[]{4}));

        // Test when there are no available moves
        dieA.setValue(3);
        dieB.setValue(4);
        moves = this.getPossibleMoves(dice);
        assertEquals(0, moves.size());
    }

    /**
     * Test of isValidMove method, of class CantStop.
     */
    @Test
    public void testIsValidMove() {
        Collection<int[]> possibleMoves = new LinkedList();
        possibleMoves.add(new int[]{2, 3});
        possibleMoves.add(new int[]{7});

        assertFalse(CantStop.isValidMove(possibleMoves, new int[]{2, 3, 4}));
        assertTrue(CantStop.isValidMove(possibleMoves, new int[]{2, 3}));
        assertTrue(CantStop.isValidMove(possibleMoves, new int[]{3, 2}));
        assertTrue(CantStop.isValidMove(possibleMoves, new int[]{7}));
        assertFalse(CantStop.isValidMove(possibleMoves, new int[]{7, 2}));
        assertFalse(CantStop.isValidMove(possibleMoves, new int[]{4, 6}));
    }

    public class AbstractCantStopPlayerImpl extends AbstractCantStopPlayer {

        @Override
        public int[] takeTurn(CantStopBoard board, Collection<int[]> possibleMoves) {
            tookTurn = true;
            return (int[]) possibleMoves.toArray()[0];
        }

        @Override
        public boolean continueOrStop(CantStopBoard board) {
            // Stop if player has had two turns.
            return false;
        }

    }

    private boolean contains(Collection<int[]> collection, int[] array) {
        for (int[] element : collection) {
            if (Arrays.equals(element, array)) {
                return true;
            }
        }

        return false;
    }

}
