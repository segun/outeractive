/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.sessions;


import com.outeractive.ads.entities.AdUserProfile;
import com.outeractive.ads.entities.PremiumAd;
import com.trinisoft.libraries.Utils;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author trinisoftinc
 */
@Stateless
public class PremiumAdFacade extends AbstractFacade<PremiumAd> {

    @PersistenceContext(unitName = "OuterActiveServerPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PremiumAdFacade() {
        super(PremiumAd.class);
    }

    public List<PremiumAd> findAllActive() {
        Query q = em.createQuery("select pa from PremiumAd pa where pa.isActive = true");
        return q.getResultList();
    }

    public List<PremiumAd> findByUsernameAndPassword(String username, String password) {
        Query q = em.createQuery("select pa from PremiumAd pa where pa.advertiser.email = :username and pa.advertiser.password = :password order by pa.dateAdded")
                .setParameter("username", username)
                .setParameter("password", Utils.encryptPassword(password, "SHA"));
        return q.getResultList();
    }

    public PremiumAd findNextAd(AdUserProfile searchUserProfile) {
        if (findAllActive().isEmpty()) {
            return null;
        }
        String query = "Select pa from PremiumAd pa where "
                + "pa.shown < pa.priority and "
                + "pa.isActive = true and "
                + "pa.startDate <= :nowStart and "
                + "pa.endDate >= :nowEnd "
                + "order by pa.shown asc";
        Query q = em.createQuery(query)
                .setParameter("nowStart", new Date())
                .setParameter("nowEnd", new Date());
        //System.out.println(q.toString());
        List<PremiumAd> premiumAds = q.getResultList();
        if (premiumAds.isEmpty()) {
            q = em.createQuery("update PremiumAd set shown = 0");
            q.executeUpdate();
            return null;
        } else {
            boolean found = false;
            int count = 0;
            int size = premiumAds.size();
            PremiumAd foundPremiumAd = null;

            while (!found && (count < size)) {
                PremiumAd pa = premiumAds.get(count++);
                AdUserProfile adUserProfile = pa.getUserProfile();

                if (adUserProfile == null) {
                    found = true;
                    foundPremiumAd = pa;
                    break;
                }

                //everyone
                if ((adUserProfile.getAge() == null || adUserProfile.getAge().length() == 0)
                        && (adUserProfile.getLocation() == null || adUserProfile.getLocation().length() == 0)
                        && (adUserProfile.getSex() == null || adUserProfile.getSex().length() == 0)) {
                    System.out.println("isEvery One");
                    found = true;
                    foundPremiumAd = pa;
                    break;
                }

                //only artists or not
                if (searchUserProfile.isIsArtist() && adUserProfile.isIsArtist()) {
                    System.out.println("only artists or not");
                    found = true;
                    foundPremiumAd = pa;
                    break;
                }

                //users in a certain age
                if (adUserProfile.getAge().contains(searchUserProfile.getAge()) && adUserProfile.getAge() != null && adUserProfile.getAge().length() > 0) {
                    System.out.println("same age group");
                    found = true;
                    foundPremiumAd = pa;
                    break;
                }

                //users in a certain location
                if (adUserProfile.getLocation().contains(searchUserProfile.getLocation()) && adUserProfile.getLocation() != null && adUserProfile.getLocation().length() > 0) {
                    System.out.println("same location");
                    found = true;
                    foundPremiumAd = pa;
                    break;
                }

                //users of a certain sex
                if (adUserProfile.getSex().contains(searchUserProfile.getSex()) && adUserProfile.getSex() != null && adUserProfile.getSex().length() > 0) {
                    System.out.println("same sex");
                    found = true;
                    foundPremiumAd = pa;
                    break;
                }
            }
            if (found) {
                if (foundPremiumAd.getShown() >= foundPremiumAd.getPriority()) {
                    foundPremiumAd.setShown(0);
                }
                foundPremiumAd.setShown(foundPremiumAd.getShown() + 1);
                foundPremiumAd.setCount(foundPremiumAd.getCount() + 1);
                this.edit(foundPremiumAd);
            }
            return foundPremiumAd;
        }
    }
}
