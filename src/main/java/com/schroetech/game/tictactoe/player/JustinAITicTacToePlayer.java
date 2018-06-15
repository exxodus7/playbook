/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.schroetech.game.tictactoe.player;

import com.schroetech.game.IGame;
import com.schroetech.game.tictactoe.TicTacToe;
import com.schroetech.game.tictactoe.object.PlayerMarker;

/**
 *
 * @author lauren
 */
public class JustinAITicTacToePlayer extends AbstractTicTacToePlayer {

    @Override
    public void takeTurn(IGame game) {
        TicTacToe ticTacToe = (TicTacToe) game;
        PlayerMarker[][] board = ticTacToe.getBoard();
        
        //set playerMarkers (X or O)
        PlayerMarker myGuy = this.getPlayerMarker();
        PlayerMarker otherGuy;
        if(myGuy==PlayerMarker.X)
            otherGuy = PlayerMarker.O;
        else
            otherGuy = PlayerMarker.X;
        
        //set up winnable formations
        int[][][] formations = { { {0,0},{0,1},{0,2} }, { {1,0},{1,1},{1,2} }, { {2,0},{2,1},{2,2} },
                                 { {0,0},{1,0},{2,0} }, { {0,1},{1,1},{2,1} }, { {0,2},{1,2},{2,2} },
                                 { {0,0},{1,1},{2,2} }, { {0,2},{1,1},{2,0} } };
            
        //check to see if win possible
        for(int f=0; f<8; f++)
        {
            int firstMove = isWinnable(board,formations[f],myGuy,otherGuy);
            if(firstMove>-1)
            {
                ticTacToe.move(formations[f][firstMove][0], formations[f][firstMove][1], myGuy);
                return;
            }
        }

        //check to see if opponent win possible
        for(int f=0; f<8; f++)
        {
            int secondMove = isWinnable(board,formations[f],otherGuy,myGuy);
            if(secondMove>-1)
            {
                ticTacToe.move(formations[f][secondMove][0], formations[f][secondMove][1], myGuy);
                return;
            }
        }

        //check to see if middle is open (and at least one win possible)
        if(board[1][1]==null)
        {
            int[] middle = {1,1};
            if(numUnblocked(middle,board,formations,otherGuy)>0)
            {
                ticTacToe.move(1, 1, myGuy);
                return;
            }
        }

        //check to see if corner is open (choose max win possible)
        int[][] corners = { {0,0}, {0,2}, {2,2}, {2,0} };
        int[] numOpen = {0,0,0,0};
        int maxOpen = 0;
        for(int c=0; c<4; c++)
        {
            if(board[corners[c][0]][corners[c][1]]==null)
            {
                int openings = numUnblocked(corners[c],board,formations,otherGuy);
                if(openings>maxOpen)
                    maxOpen=openings;
                numOpen[c]=openings;
            }
        }
        if(maxOpen>0)
        {
            for(int c=0; c<4; c++)
            {
                if(numOpen[c]==maxOpen)
                {
                    ticTacToe.move(corners[c][0], corners[c][1], myGuy);
                    return;
                }
            }
        }

        //choose side square with max win possible
        int[][] sides = { {0,1}, {1,0}, {1,2}, {2,1} };
        int[] numOpenSides = {0,0,0,0};
        int maxOpenSides = 0;
        for(int c=0; c<4; c++)
        {
            if(board[sides[c][0]][sides[c][1]]==null)
            {
                int openings = numUnblocked(sides[c],board,formations,otherGuy);
                if(openings>maxOpenSides)
                    maxOpenSides=openings;
                numOpenSides[c]=openings;
            }
        }
        if(maxOpenSides>0)
        {
            for(int c=0; c<4; c++)
            {
                if(numOpenSides[c]==maxOpenSides)
                {
                    ticTacToe.move(sides[c][0], sides[c][1], myGuy);
                    return;
                }
            }
        }
        
        //choose any open square
        for(int r=0; r<3; r++)
            for(int c=0; c<3; c++)
                if(board[r][c]==null)
                {
                    ticTacToe.move(r, c, myGuy);
                    return;
                }
    }
    
    //method to check if a formation is near winning
    private int isWinnable(PlayerMarker[][] board, int[][] formation, PlayerMarker thisMarker, PlayerMarker otherMarker)
    {
        int thisCount=0;
        int otherCount=0;
        int winningCell=-1;
        for(int c=0; c<3; c++)
        {
            if(board[formation[c][0]][formation[c][1]]==thisMarker)
                thisCount++;
            else if(board[formation[c][0]][formation[c][1]]==otherMarker)
                otherCount++;
            else
                winningCell=c;
        }
        if(thisCount==2 & otherCount==0)
            return winningCell;
        else
            return -1;
    }
    
    //method to check if a cell can be part of a winning formation
    private int numUnblocked(int[] cell, PlayerMarker[][] board, int[][][] formations, PlayerMarker otherMarker)
    {
        int numOpenings=0;
        for(int f=0; f<8; f++)
        {
            boolean cellIncluded=false;
            boolean noOtherGuy=true;
            for(int c=0; c<3; c++)
            {
                if(formations[f][c]==cell)
                    cellIncluded=true;
                if(board[formations[f][c][0]][formations[f][c][1]]==otherMarker)
                    noOtherGuy=false;
            }
            if(cellIncluded & noOtherGuy)
                numOpenings++;
        }
        
        return numOpenings;
    }
}
