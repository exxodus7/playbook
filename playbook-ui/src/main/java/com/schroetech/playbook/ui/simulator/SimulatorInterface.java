package com.schroetech.playbook.ui.simulator;

import com.schroetech.playbook.model.cantstop.CantStop;
import com.schroetech.playbook.model.common.object.IGame;
import com.schroetech.playbook.model.common.player.IPlayer;
import com.schroetech.playbook.persistence.GamingSession;
import com.schroetech.playbook.ui.PlayBook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Runs simulations of games. Sets up the game session using user command line
 * input.
 */
public class SimulatorInterface {

    private Simulator simulator;
    private GamingSession session;
    private Properties properties;
    private static final String GAMES_KEY = "games";
    private static final String DELIMETER = ",";
    private static final String PLAYER_TYPE_KEY_PREFIX = "playerTypes_";
    private static ArrayList<String> availableGames;
    private static Map<String, ArrayList<String>> availablePlayers;

    /**
     * Asks the user about their session preferences.
     *
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void run() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        loadProperties();

        Scanner in = new Scanner(System.in);
        simulator = new Simulator();
        session = new GamingSession();

        PlayBook.displayHeader();
        // setup game type
        System.out.println("Which game would you like to simulate?");
        availableGames.forEach((game) -> {
            int index = availableGames.indexOf(game) + 1;
            System.out.println(index + ") " + getSimpleClassName(game));
        });
        int selection = in.nextInt();
        in.nextLine();

        // setup players
        Map<String, IPlayer> players = new HashMap<>();
        if (selection > 0 && selection <= availableGames.size()) {
            session.setGameName(availableGames.get(selection - 1));
            displayGameInfo(players);
            setupPlayers();
        } else {
            System.out.println("Not a valid option");
            return;
        }

        // setup iterations
        System.out.print("Enter number of iterations: ");
        session.setNumberOfPlays(in.nextInt());
        in.nextLine();

        if (session.getNumberOfPlays() != 1) {
            System.out.println("Warning: You have a high number of iterations.");
            System.out.println("Turn display on with caution.");
        }

        // setup display
        System.out.println("Would you like to turn game display on? (Y/N): ");
        String displayOn = in.nextLine();

        switch (displayOn) {
            case "y":
            case "Y":
                simulator.turnDisplayOn();
                break;
        }

        // setup persistence
        System.out.println("Would you like to persist session data? (Y/N): ");
        String persistData = in.nextLine();

        switch (persistData) {
            case "y":
            case "Y":
                simulator.turnPersistDataOn();
                break;
        }

        // run the simulation
        simulator.setSession(session);
        simulator.startSession();
    }

    private Map<String, IPlayer> setupPlayers() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);

        IGame game = (IGame) Class.forName(session.getGameName()).newInstance();
        Map<String, IPlayer> players = new HashMap<>();

        for (int i = 0; i < game.getMaxNumPlayers(); i++) {
            displayGameInfo(players);
            System.out.println("Select a player type.");
            ArrayList<String> playerTypes = availablePlayers.get(session.getGameName());

            playerTypes.forEach((playerType) -> {
                int index = playerTypes.indexOf(playerType) + 1;
                System.out.println(index + ") " + getSimpleClassName(playerType));
            });
            if (i >= CantStop.MIN_PLAYERS) {
                System.out.println((playerTypes.size() + 1) + ") Done");
            }
            int selection = in.nextInt();
            in.nextLine();
            IPlayer player;

            if (selection > 0 && selection <= playerTypes.size()) {
                player = (IPlayer) Class.forName(playerTypes.get(selection - 1)).newInstance();
                System.out.print("Enter a name for your player: ");
                player.setName(in.nextLine());
                simulator.addPlayer(player);
            } else if (i >= CantStop.MIN_PLAYERS && selection == playerTypes.size() + 1) {
                return null;
            } else {
                System.out.println("Not a valid option.");
                i--;
            }
        }

        return players;
    }

    private void loadProperties() throws FileNotFoundException, IOException {
        System.out.println("Loading Properties...");
        properties = new Properties();
        FileInputStream ip = new FileInputStream(PlayBook.PROPERTIES_FILE);
        properties.load(ip);

        // load available games
        availableGames = new ArrayList();
        String[] gameNames = properties.getProperty(GAMES_KEY).split(DELIMETER);
        availableGames.addAll(Arrays.asList(gameNames));

        // load available players
        availablePlayers = new HashMap();
        availableGames.forEach((game) -> {
            ArrayList<String> playerTypes = new ArrayList();
            String key = PLAYER_TYPE_KEY_PREFIX + getSimpleClassName(game);
            String propertyValue = properties.getProperty(key);
            if (propertyValue == null) {
                System.err.println("Property " + key + " isn't configured.");
            } else {
                String[] playerTypeNames = properties.getProperty(key).split(DELIMETER);
                playerTypes.addAll(Arrays.asList(playerTypeNames));
                availablePlayers.put(game, playerTypes);
            }
        });
    }

    private void displayGameInfo(Map<String, IPlayer> players) {
        PlayBook.displayHeader();
        System.out.println("Game: " + getSimpleClassName(session.getGameName()));
        System.out.println("Players:");
        players.values().forEach((player) -> {
            System.out.println(player.getTypeString() + " (" + player.getName() + ")");
        });
        System.out.println("Iterations: " + session.getNumberOfPlays());
    }

    private String getSimpleClassName(String className) {
        if (className == null) {
            return "";
        }

        return className.substring(className.lastIndexOf('.') + 1);
    }
}
