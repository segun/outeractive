/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.sessions;

import com.outeractive.ads.entities.Advertiser;
import com.trinisoft.libraries.Utils;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author aardvocate
 */
@Stateless
public class AdvertiserFacade extends AbstractFacade<Advertiser> {
    @PersistenceContext(unitName = "OuterActiveServerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdvertiserFacade() {
        super(Advertiser.class);
    }
    
    public Advertiser findByEmailAndPassword(String email, String password) {
        Query q = em.createQuery("select a from Advertiser a where a.email = :email and a.password = :password")
                .setParameter("email", email)
                .setParameter("password", Utils.encryptPassword(password, "SHA"));
        try {
            return (Advertiser) q.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }
}
