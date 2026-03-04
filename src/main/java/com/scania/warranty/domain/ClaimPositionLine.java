/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * JPA entity for claim position lines from GPS system (HSGPSLF3).
 */
@Entity
@Table(name = "HSGPSLF3")
@IdClass(ClaimPositionLineId.class)
public class ClaimPositionLine {
    
    @Id
    @Column(name = "KUERZEL", length = 3, nullable = false)
    private String kuerzel;
    
    @Id
    @Column(name = "CLAIM-NR.", length = 8, nullable = false)
    private String claimNr;
    
    @Id
    @Column(name = "FEHLER-NR.", length = 2, nullable = false)
    private String fehlerNr;
    
    @Id
    @Column(name = "FOLGE-NR.", length = 2, nullable = false)
    private String folgeNr;
    
    @Id
    @Column(name = "ZEILE", precision = 3, scale = 0, nullable = false)
    private BigDecimal zeile;
    
    @Column(name = "AUFTEILUNG", precision = 3, scale = 0, nullable = false)
    private BigDecimal aufteilung;
    
    @Column(name = "SATZART", length = 3, nullable = false)
    private String satzart;
    
    @Column(name = "NR.", length = 18, nullable = false)
    private String nr;
    
    @Column(name = "MENGE", precision = 5, scale = 0, nullable = false)
    private BigDecimal menge;
    
    @Column(name = "WERT", precision = 11, scale = 2, nullable = false)
    private BigDecimal wert;
    
    @Column(name = "STEUER-CODE", length = 40, nullable = false)
    private String steuerCode;
    
    @Column(name = "HAUPTGRUPPE", length = 2, nullable = false)
    private String hauptgruppe;
    
    @Column(name = "ZEIT", precision = 3, scale = 1, nullable = false)
    private BigDecimal zeit;
    
    @Column(name = "GRUND", precision = 4, scale = 0, nullable = false)
    private BigDecimal grund;
    
    @Column(name = "VERGÜTUNG", precision = 3, scale = 0, nullable = false)
    private BigDecimal verguetung;
    
    @Column(name = "MANUELL", length = 1, nullable = false)
    private String manuell;
    
    @Column(name = "KAMPAGNE", precision = 8, scale = 0, nullable = false)
    private BigDecimal kampagne;
    
    @Column(name = "POS.-NR.", precision = 3, scale = 0, nullable = false)
    private BigDecimal posNr;
    
    @Column(name = "RESULTCODE", length = 2, nullable = false)
    private String resultcode;
    
    @Column(name = "CODE TYPE", length = 2, nullable = false)
    private String codeType;
    
    @Column(name = "CODE ID", length = 5, nullable = false)
    private String codeId;
    
    @Column(name = "COMPFAC.", precision = 3, scale = 0, nullable = false)
    private BigDecimal compfac;
    
    @Column(name = "GROSSPRICE", precision = 13, scale = 2, nullable = false)
    private BigDecimal grossprice;
    
    @Column(name = "DISCOUNT", precision = 5, scale = 2, nullable = false)
    private BigDecimal discount;
    
    @Column(name = "COMPAMOUNT", precision = 15, scale = 2, nullable = false)
    private BigDecimal compamount;
    
    @Column(name = "COMPQTY", precision = 7, scale = 0, nullable = false)
    private BigDecimal compqty;
    
    @Column(name = "TYPE", length = 5, nullable = false)
    private String type;
    
    @Column(name = "MIA STATUS", length = 1, nullable = false)
    private String miaStatus;
    
    @Column(name = "CATEGORYS", length = 2, nullable = false)
    private String categorys;
    
    @Column(name = "TEXT", length = 2000, nullable = false)
    private String text;

    public ClaimPositionLine() {
    }

    public String getKuerzel() {
        return kuerzel;
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    public String getClaimNr() {
        return claimNr;
    }

    public void setClaimNr(String claimNr) {
        this.claimNr = claimNr;
    }

    public String getFehlerNr() {
        return fehlerNr;
    }

    public void setFehlerNr(String fehlerNr) {
        this.fehlerNr = fehlerNr;
    }

    public String getFolgeNr() {
        return folgeNr;
    }

    public void setFolgeNr(String folgeNr) {
        this.folgeNr = folgeNr;
    }

    public BigDecimal getZeile() {
        return zeile;
    }

    public void setZeile(BigDecimal zeile) {
        this.zeile = zeile;
    }

    public BigDecimal getAufteilung() {
        return aufteilung;
    }

    public void setAufteilung(BigDecimal aufteilung) {
        this.aufteilung = aufteilung;
    }

    public String getSatzart() {
        return satzart;
    }

    public void setSatzart(String satzart) {
        this.satzart = satzart;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public BigDecimal getMenge() {
        return menge;
    }

    public void setMenge(BigDecimal menge) {
        this.menge = menge;
    }

    public BigDecimal getWert() {
        return wert;
    }

    public void setWert(BigDecimal wert) {
        this.wert = wert;
    }

    public String getSteuerCode() {
        return steuerCode;
    }

    public void setSteuerCode(String steuerCode) {
        this.steuerCode = steuerCode;
    }

    public String getHauptgruppe() {
        return hauptgruppe;
    }

    public void setHauptgruppe(String hauptgruppe) {
        this.hauptgruppe = hauptgruppe;
    }

    public BigDecimal getZeit() {
        return zeit;
    }

    public void setZeit(BigDecimal zeit) {
        this.zeit = zeit;
    }

    public BigDecimal getGrund() {
        return grund;
    }

    public void setGrund(BigDecimal grund) {
        this.grund = grund;
    }

    public BigDecimal getVerguetung() {
        return verguetung;
    }

    public void setVerguetung(BigDecimal verguetung) {
        this.verguetung = verguetung;
    }

    public String getManuell() {
        return manuell;
    }

    public void setManuell(String manuell) {
        this.manuell = manuell;
    }

    public BigDecimal getKampagne() {
        return kampagne;
    }

    public void setKampagne(BigDecimal kampagne) {
        this.kampagne = kampagne;
    }

    public BigDecimal getPosNr() {
        return posNr;
    }

    public void setPosNr(BigDecimal posNr) {
        this.posNr = posNr;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public BigDecimal getCompfac() {
        return compfac;
    }

    public void setCompfac(BigDecimal compfac) {
        this.compfac = compfac;
    }

    public BigDecimal getGrossprice() {
        return grossprice;
    }

    public void setGrossprice(BigDecimal grossprice) {
        this.grossprice = grossprice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getCompamount() {
        return compamount;
    }

    public void setCompamount(BigDecimal compamount) {
        this.compamount = compamount;
    }

    public BigDecimal getCompqty() {
        return compqty;
    }

    public void setCompqty(BigDecimal compqty) {
        this.compqty = compqty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMiaStatus() {
        return miaStatus;
    }

    public void setMiaStatus(String miaStatus) {
        this.miaStatus = miaStatus;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}