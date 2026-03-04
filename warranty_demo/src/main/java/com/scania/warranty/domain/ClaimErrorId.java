package com.scania.warranty.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite primary key for {@link ClaimError} (HSG73PF).
 * Field names must match the @Id property names in ClaimError.
 */
public class ClaimErrorId implements Serializable {

    private String pakz;
    private String rechNr;
    private String bereich;
    private String fehlerNr;
    private String folgeNr;

    public ClaimErrorId() {
    }

    public ClaimErrorId(String pakz, String rechNr, String bereich, String fehlerNr, String folgeNr) {
        this.pakz = pakz;
        this.rechNr = rechNr;
        this.bereich = bereich;
        this.fehlerNr = fehlerNr;
        this.folgeNr = folgeNr;
    }

    public String getPakz() { return pakz; }
    public void setPakz(String pakz) { this.pakz = pakz; }
    public String getRechNr() { return rechNr; }
    public void setRechNr(String rechNr) { this.rechNr = rechNr; }
    public String getBereich() { return bereich; }
    public void setBereich(String bereich) { this.bereich = bereich; }
    public String getFehlerNr() { return fehlerNr; }
    public void setFehlerNr(String fehlerNr) { this.fehlerNr = fehlerNr; }
    public String getFolgeNr() { return folgeNr; }
    public void setFolgeNr(String folgeNr) { this.folgeNr = folgeNr; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClaimErrorId that = (ClaimErrorId) o;
        return Objects.equals(pakz, that.pakz) && Objects.equals(rechNr, that.rechNr)
                && Objects.equals(bereich, that.bereich) && Objects.equals(fehlerNr, that.fehlerNr)
                && Objects.equals(folgeNr, that.folgeNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pakz, rechNr, bereich, fehlerNr, folgeNr);
    }
}
