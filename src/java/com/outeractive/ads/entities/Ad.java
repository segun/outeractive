/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.outeractive.ads.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author aardvocate
 */
@Entity
@Table(name="oa_ad")
public class Ad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private AdResUnit vlowResImage;
    @OneToOne
    private AdResUnit lowResImage;
    @OneToOne
    private AdResUnit mediumResImage;
    @OneToOne
    private AdResUnit highResImage;
    @OneToOne
    private AdResUnit vhighResImage;
    @OneToOne
    private AdResUnit hdResImage;
    @ManyToOne
    private Advertiser advertiser;
    @OneToOne(mappedBy="ad")
    private AdUserProfile userProfile;
    
    private boolean isActive = true;
    private int priority;
    private int shown;
    private int count;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date endDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateAdded;
    
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
        if (!(object instanceof Ad)) {
            return false;
        }
        Ad other = (Ad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.outeractive.ads.entities.Ad[ id=" + id + " ]";
    }

    public AdResUnit getVlowResImage() {
        return vlowResImage;
    }

    public void setVlowResImage(AdResUnit vlowResImage) {
        this.vlowResImage = vlowResImage;
    }

    public AdResUnit getLowResImage() {
        return lowResImage;
    }

    public void setLowResImage(AdResUnit lowResImage) {
        this.lowResImage = lowResImage;
    }

    public AdResUnit getMediumResImage() {
        return mediumResImage;
    }

    public void setMediumResImage(AdResUnit mediumResImage) {
        this.mediumResImage = mediumResImage;
    }

    public AdResUnit getHighResImage() {
        return highResImage;
    }

    public void setHighResImage(AdResUnit highResImage) {
        this.highResImage = highResImage;
    }

    public AdResUnit getVhighResImage() {
        return vhighResImage;
    }

    public void setVhighResImage(AdResUnit vhighResImahe) {
        this.vhighResImage = vhighResImahe;
    }

    public AdResUnit getHdResImage() {
        return hdResImage;
    }

    public void setHdResImage(AdResUnit hdResImage) {
        this.hdResImage = hdResImage;
    }    

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getShown() {
        return shown;
    }

    public void setShown(int shown) {
        this.shown = shown;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }    

    public AdUserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(AdUserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
