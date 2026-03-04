package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link WarrantyRelease} (HSG70F).
 * Field names must match the @Id property names in WarrantyRelease.
 */
public class WarrantyReleaseId implements Serializable {

    private String kzl;
    private String rNr;
    private String rDat;

    public WarrantyReleaseId() {
    }

    public WarrantyReleaseId(String kzl, String rNr, String rDat) {
        this.kzl = kzl;
        this.rNr = rNr;
        this.rDat = rDat;
    }

    public String getKzl() { return kzl; }
    public void setKzl(String kzl) { this.kzl = kzl; }
    public String getRNr() { return rNr; }
    public void setRNr(String rNr) { this.rNr = rNr; }
    public String getRDat() { return rDat; }
    public void setRDat(String rDat) { this.rDat = rDat; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarrantyReleaseId that = (WarrantyReleaseId) o;
        return Objects.equals(kzl, that.kzl) && Objects.equals(rNr, that.rNr) && Objects.equals(rDat, that.rDat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kzl, rNr, rDat);
    }
}
