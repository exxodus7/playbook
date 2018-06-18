package com.schroetech.tabletoptestplatform;

import com.schroetech.game.IPlayer;
import com.schroetech.game.tictactoe.TicTacToe;
import com.schroetech.game.tictactoe.player.JustinAITicTacToePlayer;
import com.schroetech.tabletoptestplatform.session.TabletopSession;

/**
 * Platform for running tabletop game sessions, usually where at least one of
 * the players is controlled by artificial intelligence.
 */
public class TabletopTestPlatform {

    /**
     * Creates sessions of games, creates players, and runs them.
     *
     * @param args Program arguments.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to the Tabletop Test Platform!");
        System.out.println("Today we will be playing Tic-Tac-Toe, whether you like it or not.");

        // ceate players
        IPlayer playerA = new JustinAITicTacToePlayer();
        playerA.setName("Bob");
        IPlayer playerB = new JustinAITicTacToePlayer();
        playerB.setName("Lauren");

        // create a tabletop session
        TabletopSession session = new TabletopSession();
        session.setGameType(TicTacToe.class.getName());
        session.addPlayer(playerA);
        session.addPlayer(playerB);
        session.setNumberOfGames(100);

        session.startSession();
    }

}
