/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.tabletoptestplatform.session;

import com.schroetech.game.IPlayer;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lauren
 */
public class MultiTabletopSession extends AbstractTabletopSession {

    private int numberOfGames = 0;

    public void playGames() {

        Map<String, Integer> numWins = new HashMap();
        for (IPlayer player : this.getPlayers()) {
            numWins.put(player.getName(), 0);
        }

        for (int i = 0; i < getNumberOfGames(); i++) {
            getGame().addPlayers(getPlayers());
            getGame().play();
            
            IPlayer winner = getGame().getWinner();
            if (winner != null) {
                numWins.put(winner.getName(), numWins.get(winner.getName()) + 1);
            }
        }
        
        System.out.println("Played " + getNumberOfGames() + " games.");
        int numDraws = getNumberOfGames();
        for (String name : numWins.keySet()) {
            int numberOfWins = numWins.get(name);
            System.out.println(name + " won " + numberOfWins + " times... " + getPercentage(numberOfWins) + "%");
            numDraws -= numberOfWins;
        }
        
        System.out.println("Draw " + numDraws + "... " + getPercentage(numDraws) + "%");
    }

    public void setNumberOfGames(int newNumberOfGames) {
        numberOfGames = newNumberOfGames;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }
    
    private double getPercentage(int numWins) {
        return (double) numWins / getNumberOfGames() * 100;
    }
}
