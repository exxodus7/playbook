package com.schroetech.game;

/**
 * Common interface for all player objects.
 */
public interface IPlayer {

    /**
     * Returns the name of the player.
     *
     * @return String representing the name of the player.
     */
    public String getName();
    
    /**
     * Sets the player's name;
     *
     * @param newName String representing the player's name.
     */
    public void setName(String newName);
    
    public String getId();
}
