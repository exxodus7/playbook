package com.schroetech.game.cantstop;

import com.schroetech.game.AbstractSimpleTurnGame;
import com.schroetech.game.cantstop.object.CantStopBoard;
import com.schroetech.game.cantstop.player.AbstractCantStopPlayer;
import com.schroetech.game.object.Dice;
import com.schroetech.game.util.GameUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the game Can't Stop.
 */
public class CantStop extends AbstractSimpleTurnGame {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    protected CantStopBoard board;

    @Override
    protected void setup() {
        board = new CantStopBoard(this.getPlayers().values());
    }

    @Override
    public boolean playerTurn() {

        AbstractCantStopPlayer currentPlayer = (AbstractCantStopPlayer) this.getPlayers().get(this.getCurrentPlayerId());
        Collection<Dice> dice = GameUtils.getDice(4, 6);

        boolean continueTurn = true;
        while (continueTurn) {

            GameUtils.rollDice(dice);

            Collection<int[]> possibleMoves = getPossibleMoves(dice);
            System.out.println();

            if (possibleMoves.isEmpty()) {
                board.resetTempMarkers();
                continueTurn = false;
            } else {
                int[] columns = currentPlayer.takeTurn(board, possibleMoves);

                if (!isValidMove(possibleMoves, columns)) {
                    return false;
                }

                board.advanceTempMarkers(currentPlayer.getId(), columns);
                continueTurn = currentPlayer.continueOrStop(board);
            }

            if (this.display) {
                System.out.println("Dice Rolled: " + GameUtils.getDiceAsString(dice));
                displayGameStateToConsole();
                System.out.println("Continue? " + continueTurn);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CantStop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        board.updatePlayerMarkers(currentPlayer.getId());
        victoryCheck();

        return true;
    }

    /**
     * Checks whether the current player has claimed at least three columns. If
     * yes, the game ends and the current player is the victor.
     */
    private void victoryCheck() {

        if (board.getNumAscendedColumnsByPlayerId(this.getCurrentPlayerId()) >= 3) {
            gameOver(this.getCurrentPlayerId());
        }
    }

    /**
     * Checks to see whether the given columns are a valid choice based on the
     * dice roll and board state.
     *
     * @param possibleMoves
     * @param columns
     * @return
     */
    public static boolean isValidMove(Collection<int[]> possibleMoves, int[] columns) {

        Arrays.sort(columns);
        if (columns.length <= 2) {
            for (int[] possibleMove : possibleMoves) {
                if (Arrays.equals(possibleMove, columns)) {
                    return true;
                }
            }
        } 

        return false;
    }

    protected Collection<int[]> getPossibleMoves(Collection<Dice> dice) {

        Collection<int[]> moves = new LinkedList();
        int[] diceValues = GameUtils.getDiceValues(dice);

        // Add all non-duplicate dice combinationations.
        Collection<int[]> possibilities = new LinkedList();
        int[] possibility1 = {diceValues[0] + diceValues[1], diceValues[2] + diceValues[3]};
        int[] possibility2 = {diceValues[0] + diceValues[2], diceValues[1] + diceValues[3]};
        int[] possibility3 = {diceValues[0] + diceValues[3], diceValues[1] + diceValues[2]};

        Arrays.sort(possibility1);
        Arrays.sort(possibility2);
        Arrays.sort(possibility3);

        possibilities.add(possibility1);
        if (!Arrays.equals(possibility1, possibility2)) {
            possibilities.add(possibility2);
        }
        if (!Arrays.equals(possibility1, possibility3)
                && !Arrays.equals(possibility2, possibility3)) {
            possibilities.add(possibility3);
        }

        // Find out how many temp markers there are.
        int numTempMarkers = board.getNumTempMarkers();

        // Add possibilities based on availiable temp markers and open columns.
        for (int[] possibility : possibilities) {
            if (numTempMarkers == 0 || numTempMarkers == 1) {
                addOpenMove(moves, possibility);
            } else if (numTempMarkers == 2) {
                // If at least one column already has a temp marker...
                if ((board.getTempMarkerPosition(possibility[0]) > 0)
                        || (board.getTempMarkerPosition(possibility[1]) > 0)) {
                    addOpenMove(moves, possibility);
                } else {
                    addOpenMove(moves, Arrays.copyOfRange(possibility, 0, 1));
                    addOpenMove(moves, Arrays.copyOfRange(possibility, 1, 2));
                }
            } else {
                if ((board.getTempMarkerPosition(possibility[0]) > 0)
                        && (board.getTempMarkerPosition(possibility[1]) > 0)) {
                    addOpenMove(moves, possibility);
                }
                if (board.getTempMarkerPosition(possibility[0]) > 0) {
                    addOpenMove(moves, Arrays.copyOfRange(possibility, 0, 1));
                }
                if (board.getTempMarkerPosition(possibility[1]) > 0) {
                    addOpenMove(moves, Arrays.copyOfRange(possibility, 1, 2));
                }
            }
        }

        return moves;
    }

    private void addOpenMove(Collection<int[]> moves, int[] possibility) {

        if (possibility.length == 1 && board.isOpenColumn(possibility[0])) {
            moves.add(possibility);
        } else if (possibility.length == 2) {
            boolean col0Open = board.isOpenColumn(possibility[0]);
            boolean col1Open = board.isOpenColumn(possibility[1]);
            if (col0Open && col1Open) {
                moves.add(possibility);
            } else if (col0Open) {
                moves.add(Arrays.copyOfRange(possibility, 0, 1));
            } else if (col1Open) {
                moves.add(Arrays.copyOfRange(possibility, 1, 2));
            }
        }
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
    public void displayFinalResultsToConsole() {

    }

    @Override
    public void displayGameStateToConsole() {

        board.printBoardState();
    }
}
