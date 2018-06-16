/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.tabletoptestplatform;

import com.schroetech.game.IPlayer;
import com.schroetech.game.tictactoe.TicTacToe;
import com.schroetech.game.tictactoe.player.JustinAITicTacToePlayer;
import com.schroetech.game.tictactoe.player.RandomTicTacToePlayer;
import com.schroetech.tabletoptestplatform.session.TabletopSession;

/**
 * Platform for running tabletop game sessions, usually where at least one of
 * the players is controlled by artificial intelligence.
 */
public class TabletopTestPlatform {

    /**
     * Creates sessions of games, creates players, and runs them.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Tabletop Test Platform!");
        System.out.println("Today we will be playing Tic Tac Toe, whether you like it or not.");
        
        IPlayer playerA = new RandomTicTacToePlayer();
        playerA.setName("Bob");
        IPlayer playerB = new RandomTicTacToePlayer();
        playerB.setName("Justin");

        TabletopSession session = new TabletopSession();
        session.setGameType(TicTacToe.class.getName());
        session.addPlayer(playerA);
        session.addPlayer(playerB);
        session.setNumberOfGames(1000);
        
        session.startSession();
    }

}
