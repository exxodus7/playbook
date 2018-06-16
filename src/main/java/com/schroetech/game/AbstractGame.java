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
public abstract class AbstractGame implements IGame {
    
    private ArrayList<IPlayer> players = new ArrayList();
    
    public void addPlayer(IPlayer newPlayer) {
        players.add(newPlayer);
    }
    
    public void addPlayers(ArrayList<IPlayer> newPlayers) {
        players.addAll(newPlayers);
    }
    
    public ArrayList<IPlayer> getPlayers() {
        return players;
    }
    
    public int getNumPlayers() {
        return players.size();
    }
}
