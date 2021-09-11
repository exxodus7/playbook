/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.playbook.model.tictactoe.persistence;

import com.schroetech.playbook.model.common.persistence.AbstractGameData;
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
 * @author lauren
 */
@Entity
@Table(name = "GAMEDATA_TICTACTOE", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TicTacToeGameData.findAll", query = "SELECT t FROM TicTacToeGameData t"),
    @NamedQuery(name = "TicTacToeGameData.findByGameId", query = "SELECT t FROM TicTacToeGameData t WHERE t.gameId = :gameId"),
    @NamedQuery(name = "TicTacToeGameData.findBySessionId", query = "SELECT t FROM TicTacToeGameData t WHERE t.sessionId = :sessionId"),
    @NamedQuery(name = "TicTacToeGameData.findByPlayer1Id", query = "SELECT t FROM TicTacToeGameData t WHERE t.player1Id = :player1Id"),
    @NamedQuery(name = "TicTacToeGameData.findByPlayer2Id", query = "SELECT t FROM TicTacToeGameData t WHERE t.player2Id = :player2Id"),
    @NamedQuery(name = "TicTacToeGameData.findByPlayer1Place", query = "SELECT t FROM TicTacToeGameData t WHERE t.player1Place = :player1Place"),
    @NamedQuery(name = "TicTacToeGameData.findByPlayer2Place", query = "SELECT t FROM TicTacToeGameData t WHERE t.player2Place = :player2Place")})
public class TicTacToeGameData extends AbstractGameData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GAME_ID")
    private String gameId;
    @Basic(optional = false)
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Basic(optional = false)
    @Column(name = "PLAYER1_ID")
    private String player1Id;
    @Basic(optional = false)
    @Column(name = "PLAYER2_ID")
    private String player2Id;
    @Basic(optional = false)
    @Column(name = "PLAYER1_PLACE")
    private int player1Place;
    @Basic(optional = false)
    @Column(name = "PLAYER2_PLACE")
    private int player2Place;

    public TicTacToeGameData() {
    }

    public TicTacToeGameData(String gameId) {
        this.gameId = gameId;
    }

    public TicTacToeGameData(String gameId, String sessionId, String player1Id, String player2Id, int player1Place, int player2Place) {
        this.gameId = gameId;
        this.sessionId = sessionId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Place = player1Place;
        this.player2Place = player2Place;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public int getPlayer1Place() {
        return player1Place;
    }

    public void setPlayer1Place(int player1Place) {
        this.player1Place = player1Place;
    }

    public int getPlayer2Place() {
        return player2Place;
    }

    public void setPlayer2Place(int player2Place) {
        this.player2Place = player2Place;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameId != null ? gameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicTacToeGameData)) {
            return false;
        }
        TicTacToeGameData other = (TicTacToeGameData) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schroetech.playbook.model.tictactoe.persistence.TicTacToeGameData[ gameId=" + gameId + " ]";
    }

}
