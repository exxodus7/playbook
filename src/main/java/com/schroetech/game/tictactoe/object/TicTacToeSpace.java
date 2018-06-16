/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.game.tictactoe.object;

/**
 *
 * @author justin
 */
public class TicTacToeSpace {
    private int row;
    private int column;
    
    public TicTacToeSpace(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
    }
    
    public void setRow(int newRow) {
        row = newRow;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setColumn(int newColumn) {
        column = newColumn;
    }
    
    public int getColumn() {
        return column;
    }
}
