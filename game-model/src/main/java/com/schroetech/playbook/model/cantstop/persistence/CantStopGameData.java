package com.schroetech.playbook.model.cantstop.persistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lauren
 */
@Entity
@Table(name = "CANTSTOP_GAMEDATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CantStopGameData.findAll", query = "SELECT c FROM CantStopGameData c"),
    @NamedQuery(name = "CantStopGameData.findByGameId", query = "SELECT c FROM CantStopGameData c WHERE c.gameId = :gameId"),
    @NamedQuery(name = "CantStopGameData.findBySessionId", query = "SELECT c FROM CantStopGameData c WHERE c.sessionId = :sessionId"),
    @NamedQuery(name = "CantStopGameData.findByPlayer1Id", query = "SELECT c FROM CantStopGameData c WHERE c.player1Id = :player1Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer2Id", query = "SELECT c FROM CantStopGameData c WHERE c.player2Id = :player2Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer3Id", query = "SELECT c FROM CantStopGameData c WHERE c.player3Id = :player3Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer4Id", query = "SELECT c FROM CantStopGameData c WHERE c.player4Id = :player4Id"),
    @NamedQuery(name = "CantStopGameData.findByPlayer1Won", query = "SELECT c FROM CantStopGameData c WHERE c.player1Won = :player1Won"),
    @NamedQuery(name = "CantStopGameData.findByPlayer2Won", query = "SELECT c FROM CantStopGameData c WHERE c.player2Won = :player2Won"),
    @NamedQuery(name = "CantStopGameData.findByPlayer3Won", query = "SELECT c FROM CantStopGameData c WHERE c.player3Won = :player3Won"),
    @NamedQuery(name = "CantStopGameData.findByPlayer4Won", query = "SELECT c FROM CantStopGameData c WHERE c.player4Won = :player4Won")})
public class CantStopGameData implements Serializable {

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
    @Column(name = "PLAYER1_WON")
    private Boolean player1Won;
    @Column(name = "PLAYER2_WON")
    private Boolean player2Won;
    @Column(name = "PLAYER3_WON")
    private Boolean player3Won;
    @Column(name = "PLAYER4_WON")
    private Boolean player4Won;

    private static String PERSISTENCE_UNIT = "GameDataPU";

    public CantStopGameData() {
    }

    public CantStopGameData(String gameId) {
        this.gameId = gameId;
    }

    public CantStopGameData(String gameId, String sessionId, String player1Id, String player2Id) {
        this.gameId = gameId;
        this.sessionId = sessionId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
    }

    public static void persist(CantStopGameData cantStopGameData) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emfactory.createEntityManager();

        em.getTransaction().begin();
        em.persist(cantStopGameData);
        em.getTransaction().commit();
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

    public Boolean getPlayer1Won() {
        return player1Won;
    }

    public void setPlayer1Won(Boolean player1Won) {
        this.player1Won = player1Won;
    }

    public Boolean getPlayer2Won() {
        return player2Won;
    }

    public void setPlayer2Won(Boolean player2Won) {
        this.player2Won = player2Won;
    }

    public Boolean getPlayer3Won() {
        return player3Won;
    }

    public void setPlayer3Won(Boolean player3Won) {
        this.player3Won = player3Won;
    }

    public Boolean getPlayer4Won() {
        return player4Won;
    }

    public void setPlayer4Won(Boolean player4Won) {
        this.player4Won = player4Won;
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
        return "com.schroetech.playbook.model.cantstop.persistence.CantStopGameData[ gameId=" + gameId + " ]";
    }

}
