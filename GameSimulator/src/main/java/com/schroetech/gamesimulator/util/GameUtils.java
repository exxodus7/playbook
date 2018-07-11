/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.gamesimulator.util;

import com.schroetech.gamesimulator.common.object.Dice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author lauren
 */
public class GameUtils {

    public static Collection<Dice> getDice(int number, int size) {
        Collection<Dice> dice = new LinkedList();
        for (int i = 0; i < number; i++) {
            dice.add(new Dice(size));
        }

        return dice;
    }

    public static void rollDice(Collection<Dice> dice) {
        for (Dice die : dice) {
            die.roll();
        }
    }
    
    public static String getDiceAsString(Collection<Dice> dice) {
        String diceDisplay = "";
        for(Dice die : dice) {
            diceDisplay += " " + die.getValue();
        }
        
        return diceDisplay;
    }
    
    public static int[] getDiceValues(Collection<Dice> dice) {
        int[] diceValues = new int[dice.size()];
        int i = 0;
        for (Dice die : dice) {
            diceValues[i] = die.getValue();
            i++;
        }
        
        return diceValues;
    }
}