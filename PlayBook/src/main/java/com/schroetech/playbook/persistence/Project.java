package com.schroetech.playbook.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lauren
 */
@Entity
@Table(name = "PROJECT", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
    , @NamedQuery(name = "Project.findByProjectId", query = "SELECT p FROM Project p WHERE p.projectId = :projectId")
    , @NamedQuery(name = "Project.findByLastActivity", query = "SELECT p FROM Project p WHERE p.lastActivity = :lastActivity")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "PROJECT_ID")
    private String projectId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 32700)
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Lob
    @Size(max = 32700)
    @Column(name = "MAIN_IMAGE_URL")
    private String mainImageUrl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LAST_ACTIVITY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivity;

    public Project() {
    }

    public Project(String projectId) {
        this.projectId = projectId;
    }

    public Project(String projectId, String projectName, Date lastActivity) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.lastActivity = lastActivity;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public static List<Project> findAll() {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ProjectPU");
        EntityManager em = emfactory.createEntityManager();

        List<Project> results = em.createNamedQuery("Project.findAll")
                .getResultList();

        em.close();
        emfactory.close();

        return results;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schroetech.playbook.persistence.Project[ projectId=" + projectId + " ]";
    }

}
