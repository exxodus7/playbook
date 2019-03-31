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
public class Simulator {

    private GamingSession session;
    private Properties properties;
    private boolean displayOn = false;
    private boolean saveData = false;
    private static final String GAMES_KEY = "games";
    private static final String DELIMETER = ",";
    private static final String PLAYER_TYPE_KEY_PREFIX = "playerTypes_";
    private static ArrayList<String> availableGames;
    private static Map<String, ArrayList<String>> availablePlayerTypes;
    private Map<String, IPlayer> players;

    /**
     * Asks the user about their session preferences.
     *
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     */
    public void run() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Scanner in = new Scanner(System.in);
        session = new GamingSession();
        players = new HashMap<>();

        loadProperties();
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
        if (selection > 0 && selection <= availableGames.size()) {
            session.setGameName(availableGames.get(selection - 1));
            displayGameInfo();
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
        String displayOnResponse = in.nextLine();

        switch (displayOnResponse) {
            case "y":
            case "Y":
                displayOn = true;
                break;
        }

        // setup persistence
        System.out.println("Would you like to save the session data? (Y/N): ");
        String saveDataResponse = in.nextLine();

        switch (saveDataResponse) {
            case "y":
            case "Y":
                saveData = true;
                break;
        }

        // run the simulation
        startSession();
    }

    /**
     * Executes 1-many iterations of a given game with the given players.
     *
     * @return true if the session was run without error, false otherwise.
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.ClassNotFoundException
     */
    public boolean startSession() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        long startTime = System.currentTimeMillis();

        Map<String, Integer> numberOfWins = new HashMap();
        players.keySet().forEach((playerId) -> {
            numberOfWins.put(playerId, 0);
        });

        int numberOfDraws = session.getNumberOfPlays();

        for (int i = 0; i < session.getNumberOfPlays(); i++) {
            IGame game = (IGame) Class.forName(session.getGameName()).newInstance();
            game.setPlayers(players);
            if (!game.play(displayOn)) {
                return false;
            }

            String winningPlayerId = game.getWinningPlayerId();
            if (winningPlayerId != null) {
                numberOfWins.put(winningPlayerId, numberOfWins.get(winningPlayerId) + 1);
                numberOfDraws--;
            }

            System.out.print("\r");
            int percentageComplete = (i + 1) * 100 / (session.getNumberOfPlays());
            for (int j = 0; j < 10; j++) {
                if (j <= percentageComplete / 10) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print(" (" + percentageComplete + "%)"); //}
        }

        // print results
        System.out.println();
        System.out.println("Out of " + session.getNumberOfPlays() + " games: ");
        players.keySet().forEach((playerId) -> {
            String playerName = players.get(playerId).getName();
            int numWins = numberOfWins.get(playerId);
            System.out.printf(" " + playerName + " won " + numWins + " times (%.2f%%),\n", resultPercent(numWins));
        });
        System.out.printf(" And the game was a draw " + numberOfDraws + " times (%.2f%%).\n", resultPercent(numberOfDraws));

        long endTime = System.currentTimeMillis();
        System.out.println("Session completed in " + ((endTime - startTime) / 1000.0) + "ms.");

        if (saveData) {
            System.out.println("Saving game data...");
            GamingSession.persist(session);
        }

        return true;
    }

    private void setupPlayers() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);

        IGame game = (IGame) Class.forName(session.getGameName()).newInstance();

        for (int i = 0; i < game.getMaxNumPlayers(); i++) {
            displayGameInfo();
            System.out.println("Select a player type.");
            ArrayList<String> playerTypes = availablePlayerTypes.get(session.getGameName());

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
                players.put(player.getId(), player);
            } else if (i >= CantStop.MIN_PLAYERS && selection == playerTypes.size() + 1) {
                return;
            } else {
                System.out.println("Not a valid option.");
                i--;
            }
        }
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
        availablePlayerTypes = new HashMap();
        availableGames.forEach((game) -> {
            ArrayList<String> playerTypes = new ArrayList();
            String key = PLAYER_TYPE_KEY_PREFIX + getSimpleClassName(game);
            String propertyValue = properties.getProperty(key);
            if (propertyValue == null) {
                System.err.println("Property " + key + " isn't configured.");
            } else {
                String[] playerTypeNames = properties.getProperty(key).split(DELIMETER);
                playerTypes.addAll(Arrays.asList(playerTypeNames));
                availablePlayerTypes.put(game, playerTypes);
            }
        });
    }

    private void displayGameInfo() {
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

    /**
     * Gets the percentage of time that a certain outcome happened.
     *
     * @param numerator integer representing the number of outcomes you want to
     * find the percentage of.
     * @return the percentage.
     */
    private double resultPercent(int numerator) {
        return (numerator * 100.0) / session.getNumberOfPlays();
    }
}