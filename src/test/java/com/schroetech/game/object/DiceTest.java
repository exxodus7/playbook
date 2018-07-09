package com.schroetech.game.object;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author lauren
 */
public class DiceTest {
    
    /**
     * Test of roll method, of class Dice.
     */
    @Test
    public void testRoll() {
        System.out.println("roll");
        Dice instance = null;
        int expResult = 0;
        int result = instance.roll();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValue and getValue methods, of class Dice.
     */
    @Test
    public void testSetValue() {
        System.out.println("Testing setValue and getValue");
        Dice die = new Dice(6);
        
    }

    /**
     * Test of getNumSides method, of class Dice.
     */
    @Test
    public void testGetNumSides() {
        System.out.println("getNumSides");
        Dice instance = null;
        int expResult = 0;
        int result = instance.getNumSides();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
