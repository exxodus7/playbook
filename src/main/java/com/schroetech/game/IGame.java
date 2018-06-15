/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.schroetech.game;

import java.util.ArrayList;

/**
 *
 * @author lauren
 */
public interface IGame {
    
    public void setup(ArrayList<IPlayer> players);
    
    public int getMinPlayers();
    
    public int getMaxPlayers();
    
    public boolean isGameOver();
    
    public void printResults();
    
    public void printBoardState();
}
