/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.schroetech.game;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author lauren
 */
public class TabletopSession {
    
    IGame game = null;
    Collection<IPlayer> players = new LinkedList<>();
    
    public void playGame() {
        if (game == null) {
            System.out.println("Please set game type.");
            return;
        }
        
        if (players.size() < game.getMinPlayers() || players.size() > game.getMaxPlayers()) {
            System.out.println("Please add the correct number of players.");
            return;
        }
   
        game.setup();
    }
    
    public void setGame(IGame newGame) {
        game = newGame;
    }
    
    public IGame getGame() {
        return game;
    }
    
    public void addPlayer(IPlayer newPlayer) {
        players.add(newPlayer);
    }
}
