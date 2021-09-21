///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.crucible.playbook.game.cantstop.persistence;
//
//import java.io.Serializable;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.xml.bind.annotation.XmlRootElement;
//
///**
// *
// * @author exxod
// */
//@Entity
//@Table(name = "CANTSTOP_MOVEDATA")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "CantStopMoveData.findAll", query = "SELECT c FROM CantStopMoveData c"),
//    @NamedQuery(name = "CantStopMoveData.findByGameId", query = "SELECT c FROM CantStopMoveData c WHERE c.gameId = :gameId"),
//    @NamedQuery(name = "CantStopMoveData.findByRound", query = "SELECT c FROM CantStopMoveData c WHERE c.round = :round"),
//    @NamedQuery(name = "CantStopMoveData.findByDieType", query = "SELECT c FROM CantStopMoveData c WHERE c.dieType = :dieType"),
//    @NamedQuery(name = "CantStopMoveData.findByDieValue", query = "SELECT c FROM CantStopMoveData c WHERE c.dieValue = :dieValue"),
//    @NamedQuery(name = "CantStopMoveData.findByModifier", query = "SELECT c FROM CantStopMoveData c WHERE c.modifier = :modifier"),
//    @NamedQuery(name = "CantStopMoveData.findByDevelopment", query = "SELECT c FROM CantStopMoveData c WHERE c.development = :development"),
//    @NamedQuery(name = "CantStopMoveData.findByBoardRow", query = "SELECT c FROM CantStopMoveData c WHERE c.boardRow = :boardRow"),
//    @NamedQuery(name = "CantStopMoveData.findByBoardColumn", query = "SELECT c FROM CantStopMoveData c WHERE c.boardColumn = :boardColumn"),
//    @NamedQuery(name = "CantStopMoveData.findByMoveNumber", query = "SELECT c FROM CantStopMoveData c WHERE c.moveNumber = :moveNumber"),
//    @NamedQuery(name = "CantStopMoveData.findByMoveId", query = "SELECT c FROM CantStopMoveData c WHERE c.moveId = :moveId")})
//public class CantStopMoveData implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Basic(optional = false)
//    @Column(name = "GAME_ID")
//    private String gameId;
//    @Basic(optional = false)
//    @Column(name = "ROUND")
//    private int round;
//    @Basic(optional = false)
//    @Column(name = "DIE_TYPE")
//    private String dieType;
//    @Basic(optional = false)
//    @Column(name = "DIE_VALUE")
//    private int dieValue;
//    @Column(name = "MODIFIER")
//    private Integer modifier;
//    @Basic(optional = false)
//    @Column(name = "DEVELOPMENT")
//    private String development;
//    @Basic(optional = false)
//    @Column(name = "BOARD_ROW")
//    private int boardRow;
//    @Basic(optional = false)
//    @Column(name = "BOARD_COLUMN")
//    private int boardColumn;
//    @Column(name = "MOVE_NUMBER")
//    private Integer moveNumber;
//    @Id
//    @Basic(optional = false)
//    @Column(name = "MOVE_ID")
//    private String moveId;
//
//    public CantStopMoveData() {
//    }
//
//    public CantStopMoveData(String moveId) {
//        this.moveId = moveId;
//    }
//
//    public CantStopMoveData(String moveId, String gameId, int round, String dieType, int dieValue, String development, int boardRow, int boardColumn) {
//        this.moveId = moveId;
//        this.gameId = gameId;
//        this.round = round;
//        this.dieType = dieType;
//        this.dieValue = dieValue;
//        this.development = development;
//        this.boardRow = boardRow;
//        this.boardColumn = boardColumn;
//    }
//
//    public String getGameId() {
//        return gameId;
//    }
//
//    public void setGameId(String gameId) {
//        this.gameId = gameId;
//    }
//
//    public int getRound() {
//        return round;
//    }
//
//    public void setRound(int round) {
//        this.round = round;
//    }
//
//    public String getDieType() {
//        return dieType;
//    }
//
//    public void setDieType(String dieType) {
//        this.dieType = dieType;
//    }
//
//    public int getDieValue() {
//        return dieValue;
//    }
//
//    public void setDieValue(int dieValue) {
//        this.dieValue = dieValue;
//    }
//
//    public Integer getModifier() {
//        return modifier;
//    }
//
//    public void setModifier(Integer modifier) {
//        this.modifier = modifier;
//    }
//
//    public String getDevelopment() {
//        return development;
//    }
//
//    public void setDevelopment(String development) {
//        this.development = development;
//    }
//
//    public int getBoardRow() {
//        return boardRow;
//    }
//
//    public void setBoardRow(int boardRow) {
//        this.boardRow = boardRow;
//    }
//
//    public int getBoardColumn() {
//        return boardColumn;
//    }
//
//    public void setBoardColumn(int boardColumn) {
//        this.boardColumn = boardColumn;
//    }
//
//    public Integer getMoveNumber() {
//        return moveNumber;
//    }
//
//    public void setMoveNumber(Integer moveNumber) {
//        this.moveNumber = moveNumber;
//    }
//
//    public String getMoveId() {
//        return moveId;
//    }
//
//    public void setMoveId(String moveId) {
//        this.moveId = moveId;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (moveId != null ? moveId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof CantStopMoveData)) {
//            return false;
//        }
//        CantStopMoveData other = (CantStopMoveData) object;
//        if ((this.moveId == null && other.moveId != null) || (this.moveId != null && !this.moveId.equals(other.moveId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.crucible.playbook.game.cantstop.persistence.CantstopMovedata[ moveId=" + moveId + " ]";
//    }
//    
//}
