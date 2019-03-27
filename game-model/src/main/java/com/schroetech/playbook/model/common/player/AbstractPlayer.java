package com.schroetech.playbook.model.common.player;

import java.util.UUID;

/**
 * Provides common functionality for any Player object.
 */
public abstract class AbstractPlayer implements IPlayer {

    private String name = "";
    private final String id;

    /**
     * Constructor for AbstractPlayer. Creates a unique ID for that player.
     */
    public AbstractPlayer() {
        id = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        if (!name.isEmpty()) {
            return name;
        } else {
            return id;
        }
    }

    @Override
    public void setName(String newName) {
        name = newName;
    }

    @Override
    public String getId() {
        return id;
    }
}
