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
import javax.persistence.Table;

/**
 *
 * @author aardvocate
 */
@Entity
@Table(name="oa_adresunit")
public class AdResUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String resImageLink;
    private String advertiserURL;

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
        if (!(object instanceof AdResUnit)) {
            return false;
        }
        AdResUnit other = (AdResUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.outeractive.ads.entities.AdResUnit[ id=" + id + " ]";
    }

    public String getResImageLink() {
        return resImageLink;
    }

    public void setResImageLink(String resImageLink) {
        this.resImageLink = resImageLink;
    }

    public String getAdvertiserURL() {
        return advertiserURL;
    }

    public void setAdvertiserURL(String advertiserURL) {
        this.advertiserURL = advertiserURL;
    }    
}
