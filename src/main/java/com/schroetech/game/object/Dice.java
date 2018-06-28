package com.schroetech.game.object;

import java.util.Random;

/**
 *
 * @author lauren
 */
public class Dice {

    private final int sides;
    private int value;

    public Dice(int newSides) {
        sides = newSides;
    }

    public int roll() {
        Random random = new Random();
        value = random.nextInt(sides) + 1;

        return value;
    }

    public int getValue() {
        if (value == 0) {
            return roll();
        } else {
            return value;
        }
    }

    public int getNumSides() {
        return sides;
    }
}
