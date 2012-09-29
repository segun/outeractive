/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.sessions;

import com.outeractive.ads.entities.AdUserProfile;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author aardvocate
 */
@Stateless
public class AdUserProfileFacade extends AbstractFacade<AdUserProfile> {
    @PersistenceContext(unitName = "OuterActiveServerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdUserProfileFacade() {
        super(AdUserProfile.class);
    }
    
}
