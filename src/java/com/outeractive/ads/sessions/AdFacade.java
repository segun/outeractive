/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.sessions;

import com.outeractive.ads.entities.Ad;
import com.outeractive.ads.entities.AdUserProfile;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public List<Ad> findAllActive() {
        Query q = em.createQuery("select ad from Ad ad where ad.isActive = true");
        return q.getResultList();
    }

    public Ad findNextAd(AdUserProfile searchUserProfile) {
        if (findAllActive().isEmpty()) {
            return null;
        }
        String query = "Select ad from Ad ad where "
                + "ad.shown < ad.priority and "
                + "ad.isActive = true and "
                + "ad.startDate <= :nowStart and "
                + "ad.endDate >= :nowEnd "
                + "order by ad.shown asc";
        Query q = em.createQuery(query)
                .setParameter("nowStart", new Date())
                .setParameter("nowEnd", new Date());
        List<Ad> ads = q.getResultList();
        if (ads.isEmpty()) {
            q = em.createQuery("update Ad set shown = 0");
            q.executeUpdate();
            return null;
        } else {
            boolean found = false;
            int count = 0;
            int size = ads.size();
            Ad foundAd = null;

            while (!found && (count < size)) {
                Ad ad = ads.get(count++);
                AdUserProfile adUserProfile = ad.getUserProfile();

                if (adUserProfile == null) {
                    found = true;
                    foundAd = ad;
                    break;
                }

                //everyone
                if ((adUserProfile.getAge() == null || adUserProfile.getAge().length() == 0)
                        && (adUserProfile.getLocation() == null || adUserProfile.getLocation().length() == 0)
                        && (adUserProfile.getSex() == null || adUserProfile.getSex().length() == 0)) {
                    System.out.println("isEvery One");
                    found = true;
                    foundAd = ad;
                    break;
                }

                //only artists or not
                if (searchUserProfile.isIsArtist() && adUserProfile.isIsArtist()) {
                    System.out.println("only artists or not");
                    found = true;
                    foundAd = ad;
                    break;
                }

                //users in a certain age
                if (adUserProfile.getAge().contains(searchUserProfile.getAge()) && adUserProfile.getAge() != null && adUserProfile.getAge().length() > 0) {
                    System.out.println("same age group");
                    found = true;
                    foundAd = ad;
                    break;
                }

                //users in a certain location
                if (adUserProfile.getLocation().contains(searchUserProfile.getLocation()) && adUserProfile.getLocation() != null && adUserProfile.getLocation().length() > 0) {
                    System.out.println("same location");
                    found = true;
                    foundAd = ad;
                    break;
                }

                //users of a certain sex
                if (adUserProfile.getSex().contains(searchUserProfile.getSex()) && adUserProfile.getSex() != null && adUserProfile.getSex().length() > 0) {
                    System.out.println("same sex");
                    found = true;
                    foundAd = ad;
                    break;
                }
            }

            if (found) {
                if (foundAd.getShown() >= foundAd.getPriority()) {
                    foundAd.setShown(0);
                }
                foundAd.setShown(foundAd.getShown() + 1);
                foundAd.setCount(foundAd.getCount() + 1);
                this.edit(foundAd);
            }
            return foundAd;
        }
    }
}
