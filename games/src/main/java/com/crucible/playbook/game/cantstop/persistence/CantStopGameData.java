/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucible.playbook.game.cantstop.persistence;

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
@Table(name = "CANTSTOP_GAMEDATA", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CantStopGameData.findAll", query = "SELECT c FROM CantStopGameData c"),
    @NamedQuery(name = "CantStopGameData.findByGameId", query = "SELECT c FROM CantStopGameData c WHERE c.gameId = :gameId"),
    @NamedQuery(name = "CantStopGameData.findBySessionId", query = "SELECT c FROM CantStopGameData c WHERE c.sessionId = :sessionId"),
    @NamedQuery(name = "CantStopGameData.findByPlayer1Id", query = "SELECT c FROM CantStopGameData c WHERE c.player1Id = :player1Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer1Place", query = "SELECT c FROM CantStopGameData c WHERE c.player1Place = :player1Place"),
    @NamedQuery(name = "CantStopGameData.findByPlayer1Score", query = "SELECT c FROM CantStopGameData c WHERE c.player1Score = :player1Score"),
    @NamedQuery(name = "CantStopGameData.findByPlayer2Id", query = "SELECT c FROM CantStopGameData c WHERE c.player2Id = :player2Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer2Place", query = "SELECT c FROM CantStopGameData c WHERE c.player2Place = :player2Place"),
    @NamedQuery(name = "CantStopGameData.findByPlayer2Score", query = "SELECT c FROM CantStopGameData c WHERE c.player2Score = :player2Score"),
    @NamedQuery(name = "CantStopGameData.findByPlayer3Id", query = "SELECT c FROM CantStopGameData c WHERE c.player3Id = :player3Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer3Place", query = "SELECT c FROM CantStopGameData c WHERE c.player3Place = :player3Place"),
    @NamedQuery(name = "CantStopGameData.findByPlayer3Score", query = "SELECT c FROM CantStopGameData c WHERE c.player3Score = :player3Score"),
    @NamedQuery(name = "CantStopGameData.findByPlayer4Id", query = "SELECT c FROM CantStopGameData c WHERE c.player4Id = :player4Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer4Place", query = "SELECT c FROM CantStopGameData c WHERE c.player4Place = :player4Place"),
@NamedQuery(name = "CantStopGameData.findByPlayer4Score", query = "SELECT c FROM CantStopGameData c WHERE c.player4Score = :player4Score")})
public class CantStopGameData extends AbstractGameData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GAME_ID")
    private String gameId;
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Column(name = "PLAYER1_ID")
    private String player1Id;
    @Column(name = "PLAYER1_PLACE")
    private Integer player1Place;
    @Column(name = "PLAYER1_SCORE")
    private Integer player1Score;
    @Column(name = "PLAYER2_ID")
    private String player2Id;
    @Column(name = "PLAYER2_PLACE")
    private Integer player2Place;
    @Column(name = "PLAYER2_SCORE")
    private Integer player2Score;
    @Column(name = "PLAYER3_ID")
    private String player3Id;
    @Column(name = "PLAYER3_PLACE")
    private Integer player3Place;
    @Column(name = "PLAYER3_SCORE")
    private Integer player3Score;
    @Column(name = "PLAYER4_ID")
    private String player4Id;
    @Column(name = "PLAYER4_PLACE")
    private Integer player4Place;
    @Column(name = "PLAYER4_SCORE")
    private Integer player4Score;

    public CantStopGameData() {
    }

    public CantStopGameData(String gameId) {
        this.gameId = gameId;
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

    public Integer getPlayer1Place() {
        return player1Place;
    }

    public void setPlayer1Place(Integer player1Place) {
        this.player1Place = player1Place;
    }
    
    public Integer getPlayer1Score() {
        return player1Score;
    }
    
    public void setPlayer1Score(Integer player1Score) {
        this.player1Score = player1Score;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public Integer getPlayer2Place() {
        return player2Place;
    }

    public void setPlayer2Place(Integer player2Place) {
        this.player2Place = player2Place;
    }
    
    public Integer getPlayer2Score() {
        return player2Score;
    }
    
    public void setPlayer2Score(Integer player2Score) {
        this.player2Score = player2Score;
    }

    public String getPlayer3Id() {
        return player3Id;
    }

    public void setPlayer3Id(String player3Id) {
        this.player3Id = player3Id;
    }

    public Integer getPlayer3Place() {
        return player3Place;
    }
    
    public Integer getPlayer3Score() {
        return player3Score;
    }
    
    public void setPlayer3Score(Integer player3Score) {
        this.player3Score = player3Score;
    }

    public void setPlayer3Place(Integer player3Place) {
        this.player3Place = player3Place;
    }

    public String getPlayer4Id() {
        return player4Id;
    }

    public void setPlayer4Id(String player4Id) {
        this.player4Id = player4Id;
    }

    public Integer getPlayer4Place() {
        return player4Place;
    }

    public void setPlayer4Place(Integer player4Place) {
        this.player4Place = player4Place;
    }
    
    public Integer getPlayer4Score() {
        return player4Score;
    }
    
    public void setPlayer4Score(Integer player4Score) {
        this.player4Score = player4Score;
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
        if (!(object instanceof CantStopGameData)) {
            return false;
        }
        CantStopGameData other = (CantStopGameData) object;
        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crucible.playbook.game.cantstop.persistence.CantStopGameData[ gameId=" + gameId + " ]";
    }
    
}
