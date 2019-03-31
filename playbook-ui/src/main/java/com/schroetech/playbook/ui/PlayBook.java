package com.schroetech.playbook.ui;

import com.schroetech.playbook.ui.simulator.Simulator;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author lauren
 */
public class PlayBook {

    public static final String PROPERTIES_FILE = "config.properties";

    /**
     * Creates sessions of games, creates players, and runs them.
     *
     * @param args Program arguments.
     */
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            try {
                displayHeader();
                System.out.println("What would you like to do?");
                System.out.println("1) Game Simulation");
                System.out.println("2) Playtest Import");
                System.out.println("3) Analytics");
                System.out.println("4) Exit");

                int selection = in.nextInt();
                in.nextLine();
                switch (selection) {
                    case 1:
                        Simulator sim = new Simulator();
                        sim.run();
                        break;
                    case 2:
                        runPlaytestImport();
                        break;
                    case 3:
                        runAnalytics();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Please select a valid option.");
                        break;
                }

                if (!exit) {
                    System.out.println("Would you like to continue? (Y/N)");
                    String willContinue = in.nextLine();
                    if ("n".equals(willContinue) || "N".equals(willContinue)) {
                        exit = true;
                    }
                }
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
                System.out.println("Error setting up the session.");
                return;
            }
        }
    }

    private static void runPlaytestImport() {
        System.out.println("Sorry, this feature is not yet available.");
        System.out.println("Please select another option.");
    }

    private static void runAnalytics() {
        System.out.println("Sorry, this feature is not yet available.");
        System.out.println("Please select another option.");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void displayHeader() {
        clearScreen();
        System.out.println("       - PlayBook -");
    }
}
