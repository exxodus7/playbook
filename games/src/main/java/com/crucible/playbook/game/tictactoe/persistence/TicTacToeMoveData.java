/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucible.playbook.game.tictactoe.persistence;

import com.crucible.playbook.common.persistence.AbstractMoveData;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author exxod
 */
@Entity
@Table(name = "MOVEDATA_TICTACTOE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicTacToeMoveData.findAll", query = "SELECT m FROM TicTacToeMoveData m"),
    @NamedQuery(name = "TicTacToeMoveData.findByGameId", query = "SELECT m FROM TicTacToeMoveData m WHERE m.gameId = :gameId"),
    @NamedQuery(name = "TicTacToeMoveData.findByMoveId", query = "SELECT m FROM TicTacToeMoveData m WHERE m.moveId = :moveId"),
    @NamedQuery(name = "TicTacToeMoveData.findByPlayerId", query = "SELECT m FROM TicTacToeMoveData m WHERE m.playerId = :playerId"),
    @NamedQuery(name = "TicTacToeMoveData.findByCol", query = "SELECT m FROM TicTacToeMoveData m WHERE m.col = :col"),
    @NamedQuery(name = "TicTacToeMoveData.findByRow", query = "SELECT m FROM TicTacToeMoveData m WHERE m.row = :row")})
public class TicTacToeMoveData extends AbstractMoveData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "GAME_ID")
    private String gameId;
    @Id
    @Basic(optional = false)
    @Column(name = "MOVE_ID")
    private String moveId;
    @Basic(optional = false)
    @Column(name = "PLAYER_ID")
    private String playerId;
    @Basic(optional = false)
    @Column(name = "COL")
    private int col;
    @Basic(optional = false)
    @Column(name = "ROW")
    private int row;

    public TicTacToeMoveData() {
    }

    public TicTacToeMoveData(String moveId) {
        this.moveId = moveId;
    }

    public TicTacToeMoveData(String moveId, String gameId, String playerId, int col, int row) {
        this.moveId = moveId;
        this.gameId = gameId;
        this.playerId = playerId;
        this.col = col;
        this.row = row;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getMoveId() {
        return moveId;
    }

    public void setMoveId(String moveId) {
        this.moveId = moveId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (moveId != null ? moveId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicTacToeMoveData)) {
            return false;
        }
        TicTacToeMoveData other = (TicTacToeMoveData) object;
        if ((this.moveId == null && other.moveId != null) || (this.moveId != null && !this.moveId.equals(other.moveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crucible.playbook.game.tictactoe.persistence.TicTacToeMoveData[ moveId=" + moveId + " ]";
    }
    
}
