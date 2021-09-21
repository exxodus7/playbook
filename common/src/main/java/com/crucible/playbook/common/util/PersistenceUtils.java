/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crucible.playbook.common.util;

import com.crucible.playbook.common.persistence.AbstractData;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lauren
 */
public class PersistenceUtils {

    public static void persist(AbstractData object) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(object.getPersistenceUnit());
        EntityManager em = emfactory.createEntityManager();

        em.getTransaction().begin();
        em.persist(object);
        em.flush();
        em.getTransaction().commit();
    }
    
    public static void persistAll(Collection<AbstractData> objects, String persistenceUnit) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(persistenceUnit);
        EntityManager em = emfactory.createEntityManager();

        em.getTransaction().begin();
        objects.forEach((object) -> {
            em.persist(object);
            em.flush();
        });
        em.getTransaction().commit();
    }

    public static void persistAll(Collection<AbstractData> objects) {
        
        objects.forEach((object) -> {
            persist(object);
        });
    }
}
