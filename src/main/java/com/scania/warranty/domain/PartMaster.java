/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;

/**
 * JPA entity for part master data (ITLSMF3).
 */
@Entity
@Table(name = "ITLSMF3")
public class PartMaster {
    
    @Id
    @Column(name = "PART_NO", length = 18, nullable = false)
    private String partNo;
    
    @Column(name = "SFRAN", length = 2, nullable = false)
    private String sfran;

    public PartMaster() {
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getSfran() {
        return sfran;
    }

    public void setSfran(String sfran) {
        this.sfran = sfran;
    }
}