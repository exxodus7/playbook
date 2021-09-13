package com.crucible.playbook.common.game;

import com.crucible.playbook.common.game.AbstractSimpleTurnGame;
import com.crucible.playbook.common.persistence.AbstractGameData;
import com.crucible.playbook.common.game.player.AbstractPlayer;
import com.crucible.playbook.common.persistence.AbstractMoveData;
import java.util.Collection;
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

        @Override
        public AbstractGameData retrieveGameData() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Collection<AbstractMoveData> retrieveMoveData() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    public class AbstractPlayerImpl extends AbstractPlayer {

        @Override
        public String getTypeString() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
