package com.schroetech.playbook.model.tictactoe;

import com.schroetech.playbook.model.tictactoe.object.TicTacToePlayerMarker;
import com.schroetech.playbook.model.tictactoe.object.TicTacToeSpace;
import com.schroetech.playbook.model.tictactoe.player.AbstractTicTacToePlayer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for the Tic-Tac-Toe game
 */
public class TicTacToeTest {

    private TicTacToe ticTacToe;
    private boolean tookTurn;

    @Before
    public void setUp() {
        ticTacToe = new TicTacToe();
        ticTacToe.addPlayer(new AbstractTicTacToePlayerImpl());
        ticTacToe.addPlayer(new AbstractTicTacToePlayerImpl());
        ticTacToe.setup();
        tookTurn = true;
    }

    /**
     * Test of playerTurn method, of class TicTacToe.
     */
    @Test
    public void testPlayerTurn() {
        assertTrue(ticTacToe.playerTurn());
        assertTrue(tookTurn);
    }

    /**
     * Test of getPosition method, of class TicTacToe.
     */
    @Test
    public void testGetPosition() {
        ticTacToe.playerTurn();
        TicTacToePlayerMarker marker = ticTacToe.getPosition(0, 0);
        assertTrue(TicTacToePlayerMarker.X.equals(marker)
                || TicTacToePlayerMarker.O.equals(marker));
        assertTrue(ticTacToe.getPosition(0, 1) == null);
    }

    /**
     * Test of getMinNumPlayers method, of class TicTacToe.
     */
    @Test
    public void testGetMinNumPlayers() {
        assertEquals(2, ticTacToe.getMinNumPlayers());
    }

    /**
     * Test of getMaxNumPlayers method, of class TicTacToe.
     */
    @Test
    public void testGetMaxNumPlayers() {
        assertEquals(2, ticTacToe.getMaxNumPlayers());
    }

    public class AbstractTicTacToePlayerImpl extends AbstractTicTacToePlayer {

        @Override
        public TicTacToeSpace takeTurn(TicTacToePlayerMarker[][] board, TicTacToePlayerMarker myMarker) {
            return new TicTacToeSpace(0, 0);
        }

        @Override
        public String getTypeString() {
            return "";
        }
    }

}
