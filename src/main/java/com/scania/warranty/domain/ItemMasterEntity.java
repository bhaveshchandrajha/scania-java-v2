/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;

/**
 * JPA entity for item master data (ITLSMF3) - minimal mapping for part validation.
 */
@Entity
@Table(name = "ITLSMF3")
public class ItemMasterEntity {

    @Id
    @Column(name = "ITEM_NUMBER", length = 18)
    private String itemNumber;

    @Column(name = "SFRAN", length = 2)
    private String range;

    public ItemMasterEntity() {
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}