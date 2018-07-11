package com.schroetech.gamesimulator.game.tictactoe.object;

/**
 * Represents a space on the Tic-Tac-Toe board.
 */
public class TicTacToeSpace {

    private int row;
    private int column;

    /**
     * Constructor which takes the row and column of the space.
     *
     * @param newRow Integer between 0 and 2 representing the row.
     * @param newColumn Integer between 0 and 2 representing the column.
     */
    public TicTacToeSpace(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
    }

    /**
     * Sets the row of the space.
     *
     * @param newRow Integer between 0 and 2 representing the row.
     */
    public void setRow(int newRow) {
        row = newRow;
    }

    /**
     * Gets the row of the space.
     *
     * @return Integer between 0 and 2 representing the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the column of the space.
     *
     * @param newColumn Integer between 0 and 2 representing the column.
     */
    public void setColumn(int newColumn) {
        column = newColumn;
    }

    /**
     * Gets the column of the space.
     *
     * @return Integer between 0 and 2 representing the column.
     */
    public int getColumn() {
        return column;
    }
}
