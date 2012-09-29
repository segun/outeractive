/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.sessions;

import com.outeractive.ads.entities.Advertiser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
