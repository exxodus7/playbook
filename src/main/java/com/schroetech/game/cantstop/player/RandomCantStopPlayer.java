/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.game.cantstop.player;

import com.schroetech.game.cantstop.CantStop;
import com.schroetech.game.object.Dice;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author lauren
 */
public class RandomCantStopPlayer extends AbstractCantStopPlayer {

    @Override
    public int[] takeTurn(Map<String, int[]> board, Collection<Dice> dice) {
        List<int[]> possibilities = CantStop.getPossibleMoves(board, dice);

        if (possibilities.isEmpty()) {
            return null;
        } else {
            Random randomNumberGenerator = new Random();
            return possibilities.get(randomNumberGenerator.nextInt(possibilities.size()));
        }
    }

    @Override
    public boolean continueOrStop(Map<String, int[]> board) {
        // 25% chance the player will stop.
        Random randomNumberGenerator = new Random();
        return (randomNumberGenerator.nextInt(4) != 0);
    }
}
