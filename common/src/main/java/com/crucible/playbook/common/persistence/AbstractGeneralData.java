/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucible.playbook.common.persistence;

/**
 *
 * @author exxod
 */
public class AbstractGeneralData extends AbstractData {
    
    public static final String PERSISTENCE_UNIT = "GeneralDataPU";
    
    @Override
    public String getPersistenceUnit() {
        return PERSISTENCE_UNIT;
    }
}
