package com.schroetech.gamesimulator.game.cantstop.player;

import com.schroetech.gamesimulator.game.cantstop.object.CantStopBoard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * Human Can't Stop player.
 */
public class HumanCantStopPlayer extends AbstractCantStopPlayer {

    @Override
    public int[] takeTurn(CantStopBoard board, Collection<int[]> possibleMoves) {
        int[] selection = null;
        while (selection == null) {
            board.printBoardState();
            System.out.println("Choose from the possible moves:");
            ArrayList<int[]> moveList = new ArrayList(possibleMoves);
            int i = 1;
            for (int[] possibleMove : moveList) {
                System.out.print(i + ": " + Arrays.toString(possibleMove) + " ");
            }
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            if (moveList.size() >= input - 1) {
                selection = moveList.get(input - 1);
            }
        }
        return selection;
    }

    @Override
    public boolean continueOrStop(CantStopBoard board) {
        System.out.println("Continue?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        return input.contains("Y") || input.contains("y");
    }

}
