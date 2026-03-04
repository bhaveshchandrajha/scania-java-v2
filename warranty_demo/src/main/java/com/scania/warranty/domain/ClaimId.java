package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link Claim} (HSG71PF).
 * Field names must match the @Id property names in Claim.
 */
public class ClaimId implements Serializable {

    private String pakz;
    private String rechNr;

    public ClaimId() {
    }

    public ClaimId(String pakz, String rechNr) {
        this.pakz = pakz;
        this.rechNr = rechNr;
    }

    public String getPakz() { return pakz; }
    public void setPakz(String pakz) { this.pakz = pakz; }
    public String getRechNr() { return rechNr; }
    public void setRechNr(String rechNr) { this.rechNr = rechNr; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimId that = (ClaimId) o;
        return Objects.equals(pakz, that.pakz) && Objects.equals(rechNr, that.rechNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pakz, rechNr);
    }
}
