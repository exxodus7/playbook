/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.tabletoptestplatform.session;

import com.schroetech.game.IGame;
import com.schroetech.game.IPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lauren
 */
public class TabletopSession {

    private final Map<String, IPlayer> players = new HashMap();
    private String gameType;
    private int numberOfGames;

    public boolean startSession() {
        Map<String, Integer> numberOfWins = new HashMap();
        for (String playerId : this.getPlayers().keySet())
            numberOfWins.put(playerId, 0);   
            
        int numberOfDraws = getNumberOfGames();

        try {
            for (int i = 0; i < numberOfGames; i++) {
                Class cls = Class.forName(gameType);
                IGame game = (IGame) cls.newInstance();
                game.setPlayers(this.getPlayers());
                game.play();
                String winningPlayerId = game.getWinningPlayerId();
                if (winningPlayerId != null) {
                    numberOfWins.put(winningPlayerId, numberOfWins.get(winningPlayerId) + 1);
                    numberOfDraws--;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            System.out.println("Error instantiating new game of type " + gameType);
            return false;
        }

        System.out.println("Out of " + getNumberOfGames() + " games: ");
        for (String playerId : getPlayers().keySet()) {
            String playerName = getPlayers().get(playerId).getName();
            int numWins = numberOfWins.get(playerId);
            System.out.println(" " + playerName + " won " + numWins + " times (" + percent(numWins, getNumberOfGames()) + "%),");
        }
        System.out.println(" And the game was a draw " + numberOfDraws + " times (" + percent(numberOfDraws, getNumberOfGames()) + "%),");

        return true;
    }

    private double percent(int numerator, int denominator) {
        return (numerator * 100.0) / denominator;
    }

    public void addPlayer(IPlayer newPlayer) {
        players.put(newPlayer.getId(), newPlayer);
    }

    public void addPlayers(ArrayList<IPlayer> newPlayers) {
        for (IPlayer newPlayer : newPlayers) {
            players.put(newPlayer.getId(), newPlayer);
        }
    }

    public Map<String, IPlayer> getPlayers() {
        return players;
    }

    public void setGameType(String newGameType) {
        gameType = newGameType;
    }

    public String getGameType() {
        return gameType;
    }

    public void setNumberOfGames(int newNumberOfGames) {
        numberOfGames = newNumberOfGames;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }
}
