/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.schroetech.game.tictactoe.player;

import com.schroetech.game.IPlayer;
import com.schroetech.game.tictactoe.object.PlayerMarker;

/**
 *
 * @author lauren
 */
public abstract class AbstractTicTacToePlayer implements IPlayer {
    
    private PlayerMarker myMarker = null;
    
    public void setPlayerMarker(PlayerMarker newMarker) {
        myMarker = newMarker;
    }
    
    public PlayerMarker getPlayerMarker() {
        return myMarker;
    }
}
