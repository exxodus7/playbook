/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schroetech.playbook.model.util;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lauren
 */
public class PersistenceUtils {

    public static final String PERSISTENCE_UNIT = "GameDataPU";

    public static void persist(Object object) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emfactory.createEntityManager();

        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    public static void persistAll(Collection<Object> objects) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager em = emfactory.createEntityManager();

        em.getTransaction().begin();
        objects.forEach((object) -> {
            em.persist(object);
        });
        em.getTransaction().commit();
    }
}
