package com.schroetech.playbook.gamesession;

import com.schroetech.gamesimulator.common.player.IPlayer;
import com.schroetech.gamesimulator.game.cantstop.CantStop;
import com.schroetech.gamesimulator.game.cantstop.player.RandomCantStopPlayer;

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
        System.out.println("Today we will be playing Can't Stop, whether you like it or not.");

        // ceate players
        IPlayer playerA = new RandomCantStopPlayer();
        playerA.setName("Bob");
        IPlayer playerB = new RandomCantStopPlayer();
        playerB.setName("Jack");

        // create a tabletop session
        TabletopSession session = new TabletopSession();
        session.setGameType(CantStop.class.getName());
        session.addPlayer(playerA);
        session.addPlayer(playerB);
        session.setNumberOfGames(10000);

        session.startSession();
    }

}
