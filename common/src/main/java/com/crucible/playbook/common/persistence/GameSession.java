package com.crucible.playbook.common.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lauren
 */
@Entity
@Table(name = "GAME_SESSION", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameSession.findAll", query = "SELECT s FROM GameSession s"),
    @NamedQuery(name = "GameSession.findBySessionId", query = "SELECT s FROM GameSession s WHERE s.sessionId = :sessionId"),
    @NamedQuery(name = "GameSession.findByGameName", query = "SELECT s FROM GameSession s WHERE s.gameName = :gameName"),
    @NamedQuery(name = "GameSession.findByNumberOfPlays", query = "SELECT s FROM GameSession s WHERE s.numberOfPlays = :numberOfPlays")})
public class GameSession implements Serializable {

    @Column(name = "NUMBER_OF_PLAYS")
    private Integer numberOfPlays;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Basic(optional = false)
    @Column(name = "GAME_NAME")
    private String gameName;
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    private static final String PERSISTENCE_UNIT = "GameDataPU";

    public GameSession() {
        this.sessionId = UUID.randomUUID().toString();
    }

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public GameSession(String sessionId, String gameName, int numberOfPlays) {
        this.sessionId = sessionId;
        this.gameName = gameName;
        this.numberOfPlays = numberOfPlays;
    }

    public static List<GameSession> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emfactory.createEntityManager();

        List<GameSession> results = em.createNamedQuery("GameSession.findAll")
                .getResultList();

        em.close();
        emfactory.close();

        return results;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getSimpleGameName() {
        return gameName.substring(gameName.lastIndexOf('.') + 1);
    }

    public int getNumberOfPlays() {
        if (numberOfPlays == null) {
            return 0;
        }

        return numberOfPlays;
    }

    public void setNumberOfPlays(int numberOfPlays) {
        this.numberOfPlays = numberOfPlays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameSession)) {
            return false;
        }
        GameSession other = (GameSession) object;
        if ((this.sessionId == null && other.sessionId != null) || (this.sessionId != null && !this.sessionId.equals(other.sessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schroetech.playbook.persistence.GameSession[ sessionId=" + sessionId + " ]";
    }

    public void setNumberOfPlays(Integer numberOfPlays) {
        this.numberOfPlays = numberOfPlays;
    }

}
