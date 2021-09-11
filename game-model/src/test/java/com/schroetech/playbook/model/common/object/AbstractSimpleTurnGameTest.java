package com.schroetech.playbook.model.common.object;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for AbstrastSimpleTurnGame
 */
public class AbstractSimpleTurnGameTest {

    private static final int MIN_NUM_PLAYERS = 2;
    private static final int MAX_NUM_PLAYERS = 3;

    /**
     * Test of play method, of class AbstractSimpleTurnGame.
     */
    @Test
    public void testPlay() {
        System.out.println("Testing play");
        // Test num players
        AbstractSimpleTurnGameImpl impl = new AbstractSimpleTurnGameImpl();
        impl.addPlayer(new AbstractPlayerImpl());
        assertFalse(impl.play());
        impl.addPlayer(new AbstractPlayerImpl());
        assertTrue(impl.play());
        impl.addPlayer(new AbstractPlayerImpl());
        assertTrue(impl.play());
        impl.addPlayer(new AbstractPlayerImpl());
        assertFalse(impl.play());
    }

    public class AbstractSimpleTurnGameImpl extends AbstractSimpleTurnGame {

        @Override
        public void setup() {
        }

        @Override
        public boolean playerTurn() {
            this.gameOver();
            return true;
        }

        @Override
        public int getMinNumPlayers() {
            return MIN_NUM_PLAYERS;
        }

        @Override
        public int getMaxNumPlayers() {
            return MAX_NUM_PLAYERS;
        }

        @Override
        public void displayGameStateToConsole() {
        }

        @Override
        public String getName() {
            return "";
        }
    }

    public class AbstractPlayerImpl extends AbstractPlayer {
    }
}
