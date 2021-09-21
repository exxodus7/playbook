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
@Table(name = "CANTSTOP_MOVEDATA", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CantStopMoveData.findAll", query = "SELECT c FROM CantStopMoveData c"),
    @NamedQuery(name = "CantStopMoveData.findByGameId", query = "SELECT c FROM CantStopMoveData c WHERE c.gameId = :gameId"),
    @NamedQuery(name = "CantStopMoveData.findByMoveId", query = "SELECT c FROM CantStopMoveData c WHERE c.moveId = :moveId"),
    @NamedQuery(name = "CantStopMoveData.findByMoveNumber", query = "SELECT c FROM CantStopMoveData c WHERE c.moveNumber = :moveNumber"),
    @NamedQuery(name = "CantStopMoveData.findByTurnNumber", query = "SELECT c FROM CantStopMoveData c WHERE c.turnNumber = :turnNumber"),
    @NamedQuery(name = "CantStopMoveData.findByDie1Value", query = "SELECT c FROM CantStopMoveData c WHERE c.die1Value = :die1Value"),
    @NamedQuery(name = "CantStopMoveData.findByDie2Value", query = "SELECT c FROM CantStopMoveData c WHERE c.die2Value = :die2Value"),
    @NamedQuery(name = "CantStopMoveData.findByDie3Value", query = "SELECT c FROM CantStopMoveData c WHERE c.die3Value = :die3Value"),
    @NamedQuery(name = "CantStopMoveData.findByDie4Value", query = "SELECT c FROM CantStopMoveData c WHERE c.die4Value = :die4Value"),
    @NamedQuery(name = "CantStopMoveData.findByAdvancedColumnA", query = "SELECT c FROM CantStopMoveData c WHERE c.advancedColumnA = :advancedColumnA"),
    @NamedQuery(name = "CantStopMoveData.findByAdvancedColumnB", query = "SELECT c FROM CantStopMoveData c WHERE c.advancedColumnB = :advancedColumnB"),
    @NamedQuery(name = "CantStopMoveData.findByWillContinue", query = "SELECT c FROM CantStopMoveData c WHERE c.willContinue = :willContinue")})
public class CantStopMoveData extends AbstractGameData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "GAME_ID")
    private String gameId;
    @Id
    @Basic(optional = false)
    @Column(name = "MOVE_ID")
    private String moveId;
    @Column(name = "MOVE_NUMBER")
    private Integer moveNumber;
    @Column(name = "TURN_NUMBER")
    private Integer turnNumber;
    @Column(name = "DIE1_VALUE")
    private Integer die1Value;
    @Column(name = "DIE2_VALUE")
    private Integer die2Value;
    @Column(name = "DIE3_VALUE")
    private Integer die3Value;
    @Column(name = "DIE4_VALUE")
    private Integer die4Value;
    @Column(name = "ADVANCED_COLUMNA")
    private Integer advancedColumnA;
    @Column(name = "ADVANCED_COLUMNB")
    private Integer advancedColumnB;
    @Column(name = "WILL_CONTINUE")
    private Boolean willContinue;

    public CantStopMoveData() {
    }

    public CantStopMoveData(String moveId) {
        this.moveId = moveId;
    }

    public CantStopMoveData(String moveId, String gameId) {
        this.moveId = moveId;
        this.gameId = gameId;
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

    public Integer getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
        this.turnNumber = turnNumber;
    }

    public Integer getDie1Value() {
        return die1Value;
    }

    public void setDie1Value(Integer die1Value) {
        this.die1Value = die1Value;
    }

    public Integer getDie2Value() {
        return die2Value;
    }

    public void setDie2Value(Integer die2Value) {
        this.die2Value = die2Value;
    }

    public Integer getDie3Value() {
        return die3Value;
    }

    public void setDie3Value(Integer die3Value) {
        this.die3Value = die3Value;
    }

    public Integer getDie4Value() {
        return die4Value;
    }

    public void setDie4Value(Integer die4Value) {
        this.die4Value = die4Value;
    }

    public Integer getAdvancedColumnA() {
        return advancedColumnA;
    }

    public void setAdvancedColumnA(Integer advancedColumnA) {
        this.advancedColumnA= advancedColumnA;
    }

    public Integer getAdvancedColumnB() {
        return advancedColumnB;
    }

    public void setAdvancedColumnB(Integer advancedColumnB) {
        this.advancedColumnB = advancedColumnB;
    }

    public Boolean getWillContinue() {
        return willContinue;
    }

    public void setWillContinue(Boolean willContinue) {
        this.willContinue = willContinue;
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
        if (!(object instanceof CantStopMoveData)) {
            return false;
        }
        CantStopMoveData other = (CantStopMoveData) object;
        if ((this.moveId == null && other.moveId != null) || (this.moveId != null && !this.moveId.equals(other.moveId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.crucible.playbook.game.cantstop.persistence.CantStopMoveData[ moveId=" + moveId + " ]";
    }
    
}
