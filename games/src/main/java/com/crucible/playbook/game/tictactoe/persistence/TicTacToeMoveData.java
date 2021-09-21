/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucible.playbook.game.tictactoe.persistence;

import com.crucible.playbook.common.persistence.AbstractGameData;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author exxod
 */
@Entity
@Table(name = "TICTACTOE_MOVEDATA", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicTacToeMoveData.findAll", query = "SELECT m FROM TicTacToeMoveData m"),
    @NamedQuery(name = "TicTacToeMoveData.findByGameId", query = "SELECT m FROM TicTacToeMoveData m WHERE m.gameId = :gameId"),
    @NamedQuery(name = "TicTacToeMoveData.findByMoveNumber", query = "SELECT m FROM TicTacToeMoveData m WHERE m.moveNumber = :moveNumber"),
    @NamedQuery(name = "TicTacToeMoveData.findByMoveId", query = "SELECT m FROM TicTacToeMoveData m WHERE m.moveId = :moveId"),
    @NamedQuery(name = "TicTacToeMoveData.findByPlayerId", query = "SELECT m FROM TicTacToeMoveData m WHERE m.playerId = :playerId"),
    @NamedQuery(name = "TicTacToeMoveData.findByBoardColumn", query = "SELECT m FROM TicTacToeMoveData m WHERE m.boardColumn = :boardColumn"),
    @NamedQuery(name = "TicTacToeMoveData.findByBoardRow", query = "SELECT m FROM TicTacToeMoveData m WHERE m.boardRow = :boardRow")})
public class TicTacToeMoveData extends AbstractGameData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "GAME_ID")
    private String gameId;
    @Basic(optional = false)
    @Column(name = "MOVE_NUMBER")
    private int moveNumber;
    @Id
    @Basic(optional = false)
    @Column(name = "MOVE_ID")
    private String moveId;
    @Basic(optional = false)
    @Column(name = "PLAYER_ID")
    private String playerId;
    @Basic(optional = false)
    @Column(name = "BOARD_COLUMN")
    private int boardColumn;
    @Basic(optional = false)
    @Column(name = "BOARD_ROW")
    private int boardRow;

    public TicTacToeMoveData() {
    }

    public TicTacToeMoveData(String moveId) {
        this.moveId = moveId;
    }

    public TicTacToeMoveData(String moveId, String gameId, String playerId, int boardColumn, int boardRow) {
        this.moveId = moveId;
        this.gameId = gameId;
        this.playerId = playerId;
        this.boardColumn = boardColumn;
        this.boardRow = boardRow;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    
    public int getMoveNumber() {
        return moveNumber;
    }
    
    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
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

    public int getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(int boardColumn) {
        this.boardColumn = boardColumn;
    }

    public int getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(int boardRow) {
        this.boardRow = boardRow;
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
