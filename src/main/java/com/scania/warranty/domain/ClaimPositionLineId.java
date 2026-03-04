package com.scania.warranty.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Composite primary key for {@link ClaimPositionLine} (HSGPSLF3).
 * Field names must match the @Id property names in ClaimPositionLine.
 */
public class ClaimPositionLineId implements Serializable {

    private String kuerzel;
    private String claimNr;
    private String fehlerNr;
    private String folgeNr;
    private BigDecimal zeile;

    public ClaimPositionLineId() {
    }

    public ClaimPositionLineId(String kuerzel, String claimNr, String fehlerNr, String folgeNr, BigDecimal zeile) {
        this.kuerzel = kuerzel;
        this.claimNr = claimNr;
        this.fehlerNr = fehlerNr;
        this.folgeNr = folgeNr;
        this.zeile = zeile;
    }

    public String getKuerzel() { return kuerzel; }
    public void setKuerzel(String kuerzel) { this.kuerzel = kuerzel; }
    public String getClaimNr() { return claimNr; }
    public void setClaimNr(String claimNr) { this.claimNr = claimNr; }
    public String getFehlerNr() { return fehlerNr; }
    public void setFehlerNr(String fehlerNr) { this.fehlerNr = fehlerNr; }
    public String getFolgeNr() { return folgeNr; }
    public void setFolgeNr(String folgeNr) { this.folgeNr = folgeNr; }
    public BigDecimal getZeile() { return zeile; }
    public void setZeile(BigDecimal zeile) { this.zeile = zeile; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimPositionLineId that = (ClaimPositionLineId) o;
        return Objects.equals(kuerzel, that.kuerzel) && Objects.equals(claimNr, that.claimNr)
                && Objects.equals(fehlerNr, that.fehlerNr) && Objects.equals(folgeNr, that.folgeNr)
                && Objects.equals(zeile, that.zeile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kuerzel, claimNr, fehlerNr, folgeNr, zeile);
    }
}
