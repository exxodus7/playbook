/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucible.playbook.game.cantstop;

import com.crucible.playbook.common.game.AbstractSimpleTurnGame;
import com.crucible.playbook.common.game.object.Dice;
import com.crucible.playbook.common.persistence.AbstractGameData;
import com.crucible.playbook.common.persistence.AbstractMoveData;
import com.crucible.playbook.common.util.GameUtils;
import com.crucible.playbook.game.cantstop.player.AbstractCantStopPlayer;
import com.crucible.playbook.game.cantstop.object.CantStopBoard;
import com.crucible.playbook.game.cantstop.persistence.CantStopGameData;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author exxod
 */
public class CantStop extends AbstractSimpleTurnGame {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
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
                CantStop.clearScreen();
                System.out.println("Dice Rolled: " + GameUtils.getDiceAsString(dice));
                displayGameStateToConsole();
                System.out.println("Continue? " + continueTurn);

                try {
                    Thread.sleep(200);
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
            if (possibleMoves.stream().anyMatch((possibleMove) -> (Arrays.equals(possibleMove, columns)))) {
                return true;
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
            switch (numTempMarkers) {
                case 0:
                case 1:
                    addOpenMove(moves, possibility);
                    break;
                case 2:
                    // If at least one column already has a temp marker...
                    if ((board.getTempMarkerPosition(possibility[0]) > 0)
                            || (board.getTempMarkerPosition(possibility[1]) > 0)) {
                        addOpenMove(moves, possibility);
                    } else {
                        addOpenMove(moves, Arrays.copyOfRange(possibility, 0, 1));
                        addOpenMove(moves, Arrays.copyOfRange(possibility, 1, 2));
                    }
                    break;
                default:
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
                    break;
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
    public AbstractGameData retrieveGameData() {
        CantStopGameData gameData = new CantStopGameData();
        gameData.setSessionId(sessionId);
        gameData.setGameId(gameId);
        Map<String, Integer> placeResults = getPlaceResults();
        // set player 1 info
        String player1Id = this.playerOrder.get(0);
        gameData.setPlayer1Id(player1Id);
        gameData.setPlayer1Place(placeResults.get(player1Id));
        gameData.setPlayer1Score(board.getNumAscendedColumnsByPlayerId(player1Id));
        // set player 2 info
        String player2Id = this.playerOrder.get(1);
        gameData.setPlayer2Id(player2Id);
        gameData.setPlayer2Place(placeResults.get(player2Id));
        gameData.setPlayer2Score(board.getNumAscendedColumnsByPlayerId(player2Id));
        // set player 3 info
        if (this.getNumPlayers() >= 3) {
            String player3Id = this.playerOrder.get(2);
            gameData.setPlayer3Id(player3Id);
            gameData.setPlayer3Place(placeResults.get(player3Id));
            gameData.setPlayer3Score(board.getNumAscendedColumnsByPlayerId(player3Id));
        }
        // set player 4 info
        if (this.getNumPlayers() == 4) {
            String player4Id = this.playerOrder.get(3);
            gameData.setPlayer4Id(player4Id);
            gameData.setPlayer4Place(placeResults.get(player4Id));
            gameData.setPlayer4Score(board.getNumAscendedColumnsByPlayerId(player4Id));
        }

        return gameData;
    }

    private Map<String, Integer> getPlaceResults() {
        Map<String, Integer> placeResults = new HashMap<>();

        // 1st group
        int place = 1;
        int playersWhoPlaced = 0;
        for (String player : this.playerOrder) {
            if (board.getNumAscendedColumnsByPlayerId(player) >= 3) {
                placeResults.put(player, place);
                playersWhoPlaced++;
            }
        }

        // 2nd group
        place = playersWhoPlaced + 1;
        for (String player : this.playerOrder) {
            if (board.getNumAscendedColumnsByPlayerId(player) == 2) {
                placeResults.put(player, place);
                playersWhoPlaced++;
            }
        }

        // 3rd group
        place = playersWhoPlaced + 1;
        for (String player : this.playerOrder) {
            if (board.getNumAscendedColumnsByPlayerId(player) == 1) {
                placeResults.put(player, place);
                playersWhoPlaced++;
            }
        }

        // 4th group
        place = playersWhoPlaced + 1;
        for (String player : this.playerOrder) {
            if (board.getNumAscendedColumnsByPlayerId(player) == 0) {
                placeResults.put(player, place);
                playersWhoPlaced++;
            }
        }

        return placeResults;
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

    @Override
    public String getName() {
        return "Can't Stop";
    }

    @Override
    public Collection<AbstractMoveData> retrieveMoveData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
