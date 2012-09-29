/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.sessions;

import com.outeractive.ads.entities.Ad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aardvocate
 */
@Stateless
public class AdFacade extends AbstractFacade<Ad> {
    @PersistenceContext(unitName = "OuterActiveServerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdFacade() {
        super(Ad.class);
    }
    
}
