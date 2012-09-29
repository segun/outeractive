/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author aardvocate
 */
@Entity
@Table(name = "oa_aduserprofile")
public class AdUserProfile implements Serializable {
    @OneToOne
    private Ad ad;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String age;
    String sex;
    String location;
    boolean isArtist;
    @OneToOne
    PremiumAd premiumAd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdUserProfile)) {
            return false;
        }
        AdUserProfile other = (AdUserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nlw.entities.UserProfile[ id=" + id + " ]";
    }

    public String getAge() {
        if (age != null) {
            return age;
        }

        return "7-19";
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        if (sex != null) {
            return sex.toUpperCase();
        }
        return "MALE";
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        if (location != null) {
            return location.toUpperCase();
        }

        return "LAGOS";
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isIsArtist() {
        return isArtist;
    }

    public void setIsArtist(boolean isArtist) {
        this.isArtist = isArtist;
    }

    public PremiumAd getPremiumAd() {
        return premiumAd;
    }

    public void setPremiumAd(PremiumAd premiumAd) {
        this.premiumAd = premiumAd;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
    
}
