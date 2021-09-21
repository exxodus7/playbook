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
//@Table(name = "CANTSTOP_GAMEDATA")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "CantStopGameData.findAll", query = "SELECT c FROM CantStopGameData c"),
//    @NamedQuery(name = "CantStopGameData.findByGameId", query = "SELECT c FROM CantStopGameData c WHERE c.gameId = :gameId"),
//    @NamedQuery(name = "CantStopGameData.findByGameSession", query = "SELECT c FROM CantStopGameData c WHERE c.gameSession = :gameSession"),
//    @NamedQuery(name = "CantStopGameData.findByPlayerId", query = "SELECT c FROM CantStopGameData c WHERE c.playerId = :playerId"),
//    @NamedQuery(name = "CantStopGameData.findByBoardSetup", query = "SELECT c FROM CantStopGameData c WHERE c.space1 = :space1")})
//public class CantStopGameData implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @Basic(optional = false)
//    @Column(name = "GAME_ID")
//    private String gameId;
//    @Column(name = "GAME_SESSION")
//    private String gameSession;
//    @Column(name = "PLAYER_ID")
//    private String playerId;
//    @Column(name = "BOARD_SETUP")
//    private String boardSetup;
//    
//    public CantStopGameData() {
//    }
//
//    public CantStopGameData(String gameId) {
//        this.gameId = gameId;
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
//    public String getGameSession() {
//        return gameSession;
//    }
//
//    public void setGameSession(String gameSession) {
//        this.gameSession = gameSession;
//    }
//
//    public String getPlayerId() {
//        return playerId;
//    }
//
//    public void setPlayerId(String playerId) {
//        this.playerId = playerId;
//    }
//
//    public String getBoardSetup() {
//        return boardSetup;
//    }
//
//    public void setBoardSetup(String boardSetup) {
//        this.boardSetup = boardSetup;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (gameId != null ? gameId.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof CantStopGameData)) {
//            return false;
//        }
//        CantStopGameData other = (CantStopGameData) object;
//        if ((this.gameId == null && other.gameId != null) || (this.gameId != null && !this.gameId.equals(other.gameId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.crucible.playbook.game.cantstop.persistence.CantStopGamedata[ gameId=" + gameId + " ]";
//    }
//    
//}
