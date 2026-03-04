package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link ReleaseRequest} (HSG70F).
 * Field names must match the @Id property names in ReleaseRequest.
 */
public class ReleaseRequestId implements Serializable {

    private String kzl;
    private String rNr;
    private String rDat;

    public ReleaseRequestId() {
    }

    public ReleaseRequestId(String kzl, String rNr, String rDat) {
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
        ReleaseRequestId that = (ReleaseRequestId) o;
        return Objects.equals(kzl, that.kzl) && Objects.equals(rNr, that.rNr)
                && Objects.equals(rDat, that.rDat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kzl, rNr, rDat);
    }
}
