package com.schroetech.playbook.game.model.common.object;

import java.util.Random;

/**
 * A Die object. Can have any number of faces.
 */
public class Dice {

    private final int numFaces;
    private int value;

    /**
     * Constructor which sets the number of faces that the dice has.
     *
     * @param newNumFaces The number of faces that the die has. They will start
     * with the value of 1 and have incrementally larger values on each face.
     */
    public Dice(int newNumFaces) {
        numFaces = newNumFaces;
    }

    /**
     * Simulates rolling the die. Sets the die's value to be that of a random
     * face.
     *
     * @return The new face's value.
     */
    public int roll() {
        Random random = new Random();
        value = random.nextInt(numFaces) + 1;

        return value;
    }

    /**
     * Returns the current value of the die. If no current value, the dice is
     * rolled and that value is returned.
     *
     * @return The current value of the die, or a random value if none set.
     */
    public int getValue() {
        if (value == 0) {
            return roll();
        } else {
            return value;
        }
    }

    /**
     * Sets the dice to a new value.
     *
     * @param newValue The new value to set. Must be less than or equal to the
     * number of faces that the die has.
     */
    public void setValue(int newValue) {
        if (newValue > 0 && newValue <= numFaces) {
            value = newValue;
        }
    }

    /**
     * Gets the number of faces that the die has.
     *
     * @return The number of faces of the die.
     */
    public int getNumFaces() {
        return numFaces;
    }
}
