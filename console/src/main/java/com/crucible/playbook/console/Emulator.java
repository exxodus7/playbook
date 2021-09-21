package com.crucible.playbook.console;

import com.crucible.playbook.common.game.IGame;
import com.crucible.playbook.common.game.player.IPlayer;
import com.crucible.playbook.common.persistence.AbstractData;
import com.crucible.playbook.common.persistence.AbstractGameData;
import com.crucible.playbook.common.persistence.GameSession;
import com.crucible.playbook.common.util.PersistLevel;
import com.crucible.playbook.common.util.PersistenceUtils;
import com.crucible.playbook.game.cantstop.CantStop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Runs simulations of games. Sets up the game session using user command line
 * input.
 */
public class Emulator {

    private GameSession session;
    private Properties properties;
    private boolean displayOn = false;
    private PersistLevel persistLevel = PersistLevel.NONE;
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
        session = new GameSession();
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
        System.out.println("What level of data would you like to save? (none/session/game/move): ");
        String saveDataResponse = in.nextLine();

        switch (saveDataResponse) {
            case "session":
                persistLevel = PersistLevel.SESSION;
                break;
            case "game":
                persistLevel = PersistLevel.GAME;
                break;
            case "move":
                persistLevel = PersistLevel.MOVE;
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
        long sessionStart = System.currentTimeMillis();

        Map<String, Integer> numberOfWins = new HashMap();
        players.keySet().forEach((playerId) -> {
            numberOfWins.put(playerId, 0);
        });

        int numberOfDraws = session.getNumberOfPlays();
        Collection<AbstractGameData> gameData = new LinkedList<>();

        for (int i = 0; i < session.getNumberOfPlays(); i++) {
            IGame game = (IGame) Class.forName(session.getGameName()).newInstance();
            game.setSessionId(session.getSessionId());
            game.setPlayers(players);
            game.setPersistLevel(persistLevel);
            if (!game.play(displayOn)) {
                return false;
            }

            String winningPlayerId = game.getWinningPlayerId();
            if (winningPlayerId != null) {
                numberOfWins.put(winningPlayerId, numberOfWins.get(winningPlayerId) + 1);
                numberOfDraws--;
            }
            if (PersistLevel.GAME.equals(persistLevel) || PersistLevel.MOVE.equals(persistLevel)) {
                gameData.add(game.retrieveGameData());
            }

            if (PersistLevel.MOVE.equals(persistLevel)) {
                gameData.addAll(game.retrieveMoveData());
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

        long sessionEnd = System.currentTimeMillis();
        System.out.println("Session completed in " + ((sessionEnd - sessionStart) / 1000.0) + "s.");

        if (persistLevel != PersistLevel.NONE) {
            long persistStart = System.currentTimeMillis();
            System.out.print("Saving game data... ");
            PersistenceUtils.persist(session);
            if (persistLevel != PersistLevel.SESSION) {
                int sessionsPersisted = 0;
                Collection<Collection<AbstractData>> splitGameData = splitGameData(gameData);
                for (Collection<AbstractData> gameDataSet : splitGameData) {
                    PersistenceUtils.persistAll(gameDataSet, AbstractGameData.PERSISTENCE_UNIT);
                    sessionsPersisted++;
                    System.out.print("\r");
                    int percentageComplete = 100 / splitGameData.size() * sessionsPersisted; 
                    for (int j = 0; j < 10; j++) {
                        if (j <= percentageComplete / 10) {
                            System.out.print("*");
                        } else {
                            System.out.print(" ");
                        }
                    }
                    System.out.print(" (" + percentageComplete + "%)"); //}
                }
            }
            System.out.println();
            System.out.println("saved");
            long persistEnd = System.currentTimeMillis();
            System.out.println("Persist completed in " + ((persistEnd - persistStart) / 1000.0) + " s");
        }

        return true;
    }

    private Collection<Collection<AbstractData>> splitGameData(Collection<AbstractGameData> gameData) {

        Map<Integer, Collection<AbstractData>> splitGameData = new HashMap();

        Iterator<AbstractGameData> iterator = gameData.iterator();

        int element = 0;
        while (iterator.hasNext()) {
            int subCollectionIndex = element % 20;
            if (splitGameData.get(subCollectionIndex) == null) {
                splitGameData.put(subCollectionIndex, new LinkedList());
            }
            splitGameData.get(subCollectionIndex).add(iterator.next());
            element++;
        }

        return splitGameData.values();
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
