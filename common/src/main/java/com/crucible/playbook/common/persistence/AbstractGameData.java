package com.crucible.playbook.common.persistence;

/**
 *
 * @author lauren
 */
public abstract class AbstractGameData extends AbstractData {
    
    public static final String PERSISTENCE_UNIT = "GameDataPU";
    
    @Override
    public String getPersistenceUnit() {
        return PERSISTENCE_UNIT;
    }
}
