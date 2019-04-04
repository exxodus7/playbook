package com.schroetech.playbook.model.common.persistence;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lauren
 */
@Entity
@Table(name = "GAMING_SESSION", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GamingSession.findAll", query = "SELECT s FROM GamingSession s"),
    @NamedQuery(name = "GamingSession.findBySessionId", query = "SELECT s FROM GamingSession s WHERE s.sessionId = :sessionId"),
    @NamedQuery(name = "GamingSession.findByGameName", query = "SELECT s FROM GamingSession s WHERE s.gameName = :gameName"),
    @NamedQuery(name = "GamingSession.findByNumberOfPlays", query = "SELECT s FROM GamingSession s WHERE s.numberOfPlays = :numberOfPlays")})
public class GamingSession implements Serializable {

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

    public GamingSession() {
        this.sessionId = UUID.randomUUID().toString();
    }

    public GamingSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public GamingSession(String sessionId, String gameName, int numberOfPlays) {
        this.sessionId = sessionId;
        this.gameName = gameName;
        this.numberOfPlays = numberOfPlays;
    }

    public static List<GamingSession> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emfactory.createEntityManager();

        List<GamingSession> results = em.createNamedQuery("GamingSession.findAll")
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
        if (!(object instanceof GamingSession)) {
            return false;
        }
        GamingSession other = (GamingSession) object;
        if ((this.sessionId == null && other.sessionId != null) || (this.sessionId != null && !this.sessionId.equals(other.sessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schroetech.playbook.persistence.GamingSession[ sessionId=" + sessionId + " ]";
    }

    public void setNumberOfPlays(Integer numberOfPlays) {
        this.numberOfPlays = numberOfPlays;
    }

}
