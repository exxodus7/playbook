package com.schroetech.game.cantstop;

import com.schroetech.game.AbstractSimpleTurnGame;
import com.schroetech.game.cantstop.player.AbstractCantStopPlayer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the game Can't Stop.
 */
public class CantStop extends AbstractSimpleTurnGame {

    private static final String TEMP_MARKER_KEY = "temp-marker";
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
        rollDice();
        AbstractCantStopPlayer currentPlayer = (AbstractCantStopPlayer) this.getPlayers().get(this.getCurrentPlayerID());
        int[] dice = rollDice();
        int[] columns = currentPlayer.takeTurn(board, dice);
        if (columns.length != 2 || !isValidMove(dice, columns[0], columns[1])) {
            return false;
        }

        // TODO: Update board, declare winner etc.
        // TODO: Prompt player to continue.
        return true;
    }

    private int[] rollDice() {

        int[] dice = new int[4];
        Random randomNumberGenerator = new Random();
        dice[0] = randomNumberGenerator.nextInt(6);
        dice[1] = randomNumberGenerator.nextInt(6);
        dice[2] = randomNumberGenerator.nextInt(6);
        dice[3] = randomNumberGenerator.nextInt(6);

        return dice;
    }

    private boolean isValidMove(int[] dice, int columnA, int columnB) {

        // TODO: Make sure at least one column is available.
        if ((dice[0] + dice[1] == columnA) && (dice[2] + dice[3] == columnB)) {
            return true;
        } else if (((dice[0] + dice[2]) == columnA) && ((dice[1] + dice[3]) == columnB)) {
            return true;
        } else if (((dice[0] + dice[3]) == columnA) && ((dice[1] + dice[2]) == columnB)) {
            return true;
        }

        return false;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void printBoardState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
