package com.crucible.playbook.game.cantstop.player;

import com.crucible.playbook.common.game.player.AbstractPlayer;
import com.crucible.playbook.game.cantstop.object.CantStopBoard;
import java.util.Collection;

/**
 * Common functionality for a Can't Stop player.
 */
public abstract class AbstractCantStopPlayer extends AbstractPlayer {

    /**
     * Takes a turn at the game Can't Stop.
     *
     * @param board The state of the board. Where all of every player's markers
     * are, including temporary markers.
     * @param possibleMoves A collection of all of the possible moves.
     * @return Which columns to advance on. Should be one of the possible moves
     * from the possibleMoves Collection.
     */
    public abstract int[] takeTurn(CantStopBoard board, Collection<int[]> possibleMoves);

    /**
     * Whether to continue and take another turn or stop and upgrade the player
     * markers. Stopping ends the turn.
     *
     * @param board The state of the board. Where all of every player's markers
     * are, including temporary markers.
     * @return Whether or not to continue playing.
     */
    public abstract boolean continueOrStop(CantStopBoard board);
}
