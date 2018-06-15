/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.schroetech.game;

import com.schroetech.game.tictactoe.player.AbstractTicTacToePlayer;
import java.util.ArrayList;

/**
 *
 * @author lauren
 */
public class TabletopSession {
    
    IGame game = null;
    ArrayList<IPlayer> players = new ArrayList<>();
    
    public void playGame() {
        if (game == null) {
            System.out.println("Please set game type.");
            return;
        }
        
        if (players.size() < game.getMinPlayers() || players.size() > game.getMaxPlayers()) {
            System.out.println("Please add the correct number of players.");
            return;
        }
   
        game.setup(players);
        
        while(!game.isGameOver()) {
            for (IPlayer player : players) {
                if (game.isGameOver())
                        break;
                
                player.takeTurn(game);
                game.printBoardState();
            }
        }
        
        System.out.println("Game over!");
        game.printResults();
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
