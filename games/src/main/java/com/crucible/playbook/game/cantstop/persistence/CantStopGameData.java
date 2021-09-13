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
 * @author lauren
 */
@Entity
@Table(name = "GAMEDATA_CANTSTOP", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CantStopGameData.findAll", query = "SELECT g FROM CantStopGameData g"),
    @NamedQuery(name = "CantStopGameData.findByGameId", query = "SELECT g FROM CantStopGameData g WHERE g.gameId = :gameId"),
    @NamedQuery(name = "CantStopGameData.findBySessionId", query = "SELECT g FROM CantStopGameData g WHERE g.sessionId = :sessionId"),
    @NamedQuery(name = "CantStopGameData.findByPlayer1Id", query = "SELECT g FROM CantStopGameData g WHERE g.player1Id = :player1Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer2Id", query = "SELECT g FROM CantStopGameData g WHERE g.player2Id = :player2Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer3Id", query = "SELECT g FROM CantStopGameData g WHERE g.player3Id = :player3Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer4Id", query = "SELECT g FROM CantStopGameData g WHERE g.player4Id = :player4Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer1Place", query = "SELECT g FROM CantStopGameData g WHERE g.player1Place = :player1Place"),
    @NamedQuery(name = "CantStopGameData.findByPlayer2Place", query = "SELECT g FROM CantStopGameData g WHERE g.player2Place = :player2Place"),
    @NamedQuery(name = "CantStopGameData.findByPlayer3Place", query = "SELECT g FROM CantStopGameData g WHERE g.player3Place = :player3Place"),
    @NamedQuery(name = "CantStopGameData.findByPlayer4Place", query = "SELECT g FROM CantStopGameData g WHERE g.player4Place = :player4Place")})
public class CantStopGameData extends AbstractGameData implements Serializable {

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
    @Column(name = "PLAYER3_ID")
    private String player3Id;
    @Column(name = "PLAYER4_ID")
    private String player4Id;
    @Basic(optional = false)
    @Column(name = "PLAYER1_PLACE")
    private int player1Place;
    @Basic(optional = false)
    @Column(name = "PLAYER2_PLACE")
    private int player2Place;
    @Basic(optional = false)
    @Column(name = "PLAYER3_PLACE")
    private int player3Place;
    @Basic(optional = false)
    @Column(name = "PLAYER4_PLACE")
    private int player4Place;
    @Basic(optional = true)
    @Column(name = "PLAYER1_SCORE")
    private int player1Score;
    @Basic(optional = true)
    @Column(name = "PLAYER2_SCORE")
    private int player2Score;
    @Basic(optional = true)
    @Column(name = "PLAYER3_SCORE")
    private int player3Score;
    @Basic(optional = true)
    @Column(name = "PLAYER4_SCORE")
    private int player4Score;

    public CantStopGameData() {
    }

    public CantStopGameData(String gameId) {
        this.gameId = gameId;
    }

    public CantStopGameData(String gameId, String sessionId, String player1Id, String player2Id, int player1Place, int player2Place, int player3Place, int player4Place) {
        this.gameId = gameId;
        this.sessionId = sessionId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Place = player1Place;
        this.player2Place = player2Place;
        this.player3Place = player3Place;
        this.player4Place = player4Place;
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

    public String getPlayer3Id() {
        return player3Id;
    }

    public void setPlayer3Id(String player3Id) {
        this.player3Id = player3Id;
    }

    public String getPlayer4Id() {
        return player4Id;
    }

    public void setPlayer4Id(String player4Id) {
        this.player4Id = player4Id;
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

    public int getPlayer3Place() {
        return player3Place;
    }

    public void setPlayer3Place(int player3Place) {
        this.player3Place = player3Place;
    }

    public int getPlayer4Place() {
        return player4Place;
    }

    public void setPlayer4Place(int player4Place) {
        this.player4Place = player4Place;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public int getPlayer3Score() {
        return player3Score;
    }

    public void setPlayer3Score(int player3Score) {
        this.player3Score = player3Score;
    }

    public int getPlayer4Score() {
        return player4Score;
    }

    public void setPlayer4Score(int player4Score) {
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
        return "com.crucible.playbook.cantstop.persistence.CantStopGameData[ gameId=" + gameId + " ]";
    }

}
