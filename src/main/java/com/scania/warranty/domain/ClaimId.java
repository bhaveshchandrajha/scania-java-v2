package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link Claim} (HSG71LF2).
 * Field names must match the @Id property names in Claim.
 */
public class ClaimId implements Serializable {

    private String pakz;
    private String rechNr;
    private String auftragsNr;
    private String wete;
    private String claimNr;

    public ClaimId() {
    }

    public ClaimId(String pakz, String rechNr, String auftragsNr, String wete, String claimNr) {
        this.pakz = pakz;
        this.rechNr = rechNr;
        this.auftragsNr = auftragsNr;
        this.wete = wete;
        this.claimNr = claimNr;
    }

    public String getPakz() { return pakz; }
    public void setPakz(String pakz) { this.pakz = pakz; }
    public String getRechNr() { return rechNr; }
    public void setRechNr(String rechNr) { this.rechNr = rechNr; }
    public String getAuftragsNr() { return auftragsNr; }
    public void setAuftragsNr(String auftragsNr) { this.auftragsNr = auftragsNr; }
    public String getWete() { return wete; }
    public void setWete(String wete) { this.wete = wete; }
    public String getClaimNr() { return claimNr; }
    public void setClaimNr(String claimNr) { this.claimNr = claimNr; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimId that = (ClaimId) o;
        return Objects.equals(pakz, that.pakz) && Objects.equals(rechNr, that.rechNr)
                && Objects.equals(auftragsNr, that.auftragsNr) && Objects.equals(wete, that.wete)
                && Objects.equals(claimNr, that.claimNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pakz, rechNr, auftragsNr, wete, claimNr);
    }
}
