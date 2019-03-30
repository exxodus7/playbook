package com.schroetech.playbook.ui.simulator;

import com.schroetech.playbook.model.cantstop.CantStop;
import com.schroetech.playbook.model.common.object.IGame;
import com.schroetech.playbook.model.common.player.IPlayer;
import com.schroetech.playbook.ui.PlayBook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Runs simulations of games. Sets up the game session using user command line
 * input.
 */
public class Simulator {

    private TabletopSession session;
    private Properties properties;
    private static final String GAMES_KEY = "games";
    private static final String DELIMETER = ",";
    private static final String PLAYER_TYPE_KEY_PREFIX = "playerTypes_";
    private static ArrayList<Class> availableGames;
    private static Map<String, ArrayList<Class>> availablePlayers;

    /**
     * Asks the user about their session preferences.
     *
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void setup() throws IOException, InstantiationException, IllegalAccessException {
        // ceate players
        loadProperties();

        Scanner in = new Scanner(System.in);
        session = new TabletopSession();

        PlayBook.displayHeader();
        System.out.println("Which game would you like to simulate?");
        availableGames.forEach((game) -> {
            int index = availableGames.indexOf(game) + 1;
            System.out.println(index + ") " + game.getSimpleName());
        });
        int selection = in.nextInt();
        in.nextLine();

        if (selection > 0 && selection <= availableGames.size()) {
            session.setGameType(availableGames.get(selection - 1));
            displayGameInfo();
            setupPlayers();
        } else {
            System.out.println("Not a valid option");
            return;
        }

        System.out.print("Enter number of iterations: ");
        session.setNumberOfGames(in.nextInt());
        in.nextLine();

        if (session.getNumberOfGames() != 1) {
            System.out.println("Warning: You have a high number of iterations.");
            System.out.println("Turn display on with caution.");
        }

        System.out.println("Would you like to turn game display on? (Y/N): ");
        String displayOn = in.nextLine();

        switch (displayOn) {
            case "y":
            case "Y":
                session.turnDisplayOn();
                break;
        }
    }

    public void run() throws InstantiationException, IllegalAccessException {
        session.startSession();
    }

    private void setupPlayers() throws InstantiationException, IllegalAccessException {
        Scanner in = new Scanner(System.in);

        IGame game = (IGame) session.getGameType().newInstance();

        for (int i = 0; i < game.getMaxNumPlayers(); i++) {
            displayGameInfo();
            System.out.println("Select a player type.");
            ArrayList<Class> playerTypes = availablePlayers.get(session.getGameType().toString());

            playerTypes.forEach((playerType) -> {
                int index = playerTypes.indexOf(playerType) + 1;
                System.out.println(index + ") " + playerType.getSimpleName());
            });
            if (i >= CantStop.MIN_PLAYERS) {
                System.out.println((playerTypes.size() + 1) + ") Done");
            }
            int selection = in.nextInt();
            in.nextLine();
            IPlayer player = null;

            if (selection > 0 && selection <= playerTypes.size()) {
                player = (IPlayer) playerTypes.get(selection - 1).newInstance();
                System.out.print("Enter a name for your player: ");
                player.setName(in.nextLine());
                session.addPlayer(player);
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
        for (String gameName : gameNames) {
            try {
                Class gameClass = Class.forName(gameName);
                availableGames.add(gameClass);
            } catch (ClassNotFoundException ex) {
                System.err.println("Couldn't load game " + gameName + ", ignoring.");
            }
        }

        // load available players
        availablePlayers = new HashMap();
        availableGames.forEach((game) -> {
            ArrayList<Class> playerTypes = new ArrayList();
            String key = PLAYER_TYPE_KEY_PREFIX + game.getSimpleName();
            String[] playerTypeNames = properties.getProperty(key).split(DELIMETER);
            for (String playerTypeName : playerTypeNames) {
                try {
                    Class playerTypeClass = Class.forName(playerTypeName);
                    playerTypes.add(playerTypeClass);
                } catch (ClassNotFoundException ex) {
                    System.err.println("Couldn't load player type " + playerTypeName + " for game " + game.getName() + ", ignoring.");
                }
            }
            availablePlayers.put(game.toString(), playerTypes);
        });
    }

    private void displayGameInfo() {
        PlayBook.displayHeader();
        System.out.println("Game: " + session.getGameType().getSimpleName());
        System.out.println("Players:");
        session.getPlayers().values().forEach((player) -> {
            System.out.println(player.getTypeString() + " (" + player.getName() + ")");
        });
        System.out.println("Iterations: " + session.getNumberOfGames());
    }
}
