package com.schroetech.game.cantstop.player;

import com.schroetech.game.cantstop.object.CantStopBoard;
import java.util.Collection;
import java.util.Random;

/**
 * A player who makes random moves at Can't Stop.
 */
public class RandomCantStopPlayer extends AbstractCantStopPlayer {

    @Override
    public int[] takeTurn(CantStopBoard board, Collection<int[]> possibleMoves) {

        if (possibleMoves.isEmpty()) {
            return null;
        } else {
            Random randomNumberGenerator = new Random();
            return (int[]) possibleMoves.toArray()[randomNumberGenerator.nextInt(possibleMoves.size())];
        }
    }

    @Override
    public boolean continueOrStop(CantStopBoard board) {
        // 25% chance the player will stop.
        Random randomNumberGenerator = new Random();
        return (randomNumberGenerator.nextInt(4) != 0);
    }
}
