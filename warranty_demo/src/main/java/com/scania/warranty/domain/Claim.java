/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * JPA entity for claim header (HSG71PF).
 */
@Entity
@Table(name = "HSG71PF")
@IdClass(ClaimId.class)
public class Claim {
    
    @Id
    @Column(name = "PAKZ", length = 3, nullable = false)
    private String pakz;
    
    @Id
    @Column(name = "RECH.-NR.", length = 5, nullable = false)
    private String rechNr;
    
    @Column(name = "RECH.-DATUM", length = 8, nullable = false)
    private String rechDatum;
    
    @Column(name = "AUFTRAGS-NR.", length = 5, nullable = false)
    private String auftragsNr;
    
    @Column(name = "WETE", length = 1, nullable = false)
    private String wete;
    
    @Column(name = "CLAIM-NR.", length = 8, nullable = false)
    private String claimNr;
    
    @Column(name = "CHASSIS-NR.", length = 7, nullable = false)
    private String chassisNr;
    
    @Column(name = "KENNZEICHEN", length = 10, nullable = false)
    private String kennzeichen;
    
    @Column(name = "ZUL.-DATUM", precision = 8, scale = 0, nullable = false)
    private BigDecimal zulDatum;
    
    @Column(name = "REP.-DATUM", precision = 8, scale = 0, nullable = false)
    private BigDecimal repDatum;
    
    @Column(name = "KM-STAND", precision = 3, scale = 0, nullable = false)
    private BigDecimal kmStand;
    
    @Column(name = "PRODUKT-TYP", precision = 1, scale = 0, nullable = false)
    private BigDecimal produktTyp;
    
    @Column(name = "ANHANG", length = 1, nullable = false)
    private String anhang;
    
    @Column(name = "AUSL#NDER", length = 1, nullable = false)
    private String auslaender;
    
    @Column(name = "KD-NR.", length = 6, nullable = false)
    private String kdNr;
    
    @Column(name = "KD-NAME", length = 30, nullable = false)
    private String kdName;
    
    @Column(name = "CLAIM-NR. SDE", length = 8, nullable = false)
    private String claimNrSde;
    
    @Column(name = "STATUS CODE SDE", precision = 2, scale = 0, nullable = false)
    private BigDecimal statusCodeSde;
    
    @Column(name = "ANZ. FEHLER", precision = 2, scale = 0, nullable = false)
    private BigDecimal anzFehler;
    
    @Column(name = "BEREICH", length = 1, nullable = false)
    private String bereich;
    
    @Column(name = "AUF.NR.", length = 10, nullable = false)
    private String aufNr;

    public Claim() {
    }

    public String getPakz() {
        return pakz;
    }

    public void setPakz(String pakz) {
        this.pakz = pakz;
    }

    public String getRechNr() {
        return rechNr;
    }

    public void setRechNr(String rechNr) {
        this.rechNr = rechNr;
    }

    public String getRechDatum() {
        return rechDatum;
    }

    public void setRechDatum(String rechDatum) {
        this.rechDatum = rechDatum;
    }

    public String getAuftragsNr() {
        return auftragsNr;
    }

    public void setAuftragsNr(String auftragsNr) {
        this.auftragsNr = auftragsNr;
    }

    public String getWete() {
        return wete;
    }

    public void setWete(String wete) {
        this.wete = wete;
    }

    public String getClaimNr() {
        return claimNr;
    }

    public void setClaimNr(String claimNr) {
        this.claimNr = claimNr;
    }

    public String getChassisNr() {
        return chassisNr;
    }

    public void setChassisNr(String chassisNr) {
        this.chassisNr = chassisNr;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public BigDecimal getZulDatum() {
        return zulDatum;
    }

    public void setZulDatum(BigDecimal zulDatum) {
        this.zulDatum = zulDatum;
    }

    public BigDecimal getRepDatum() {
        return repDatum;
    }

    public void setRepDatum(BigDecimal repDatum) {
        this.repDatum = repDatum;
    }

    public BigDecimal getKmStand() {
        return kmStand;
    }

    public void setKmStand(BigDecimal kmStand) {
        this.kmStand = kmStand;
    }

    public BigDecimal getProduktTyp() {
        return produktTyp;
    }

    public void setProduktTyp(BigDecimal produktTyp) {
        this.produktTyp = produktTyp;
    }

    public String getAnhang() {
        return anhang;
    }

    public void setAnhang(String anhang) {
        this.anhang = anhang;
    }

    public String getAuslaender() {
        return auslaender;
    }

    public void setAuslaender(String auslaender) {
        this.auslaender = auslaender;
    }

    public String getKdNr() {
        return kdNr;
    }

    public void setKdNr(String kdNr) {
        this.kdNr = kdNr;
    }

    public String getKdName() {
        return kdName;
    }

    public void setKdName(String kdName) {
        this.kdName = kdName;
    }

    public String getClaimNrSde() {
        return claimNrSde;
    }

    public void setClaimNrSde(String claimNrSde) {
        this.claimNrSde = claimNrSde;
    }

    public BigDecimal getStatusCodeSde() {
        return statusCodeSde;
    }

    public void setStatusCodeSde(BigDecimal statusCodeSde) {
        this.statusCodeSde = statusCodeSde;
    }
    
    public void setStatusCodeSde(int statusCodeSde) {
        this.statusCodeSde = BigDecimal.valueOf(statusCodeSde);
    }

    public BigDecimal getAnzFehler() {
        return anzFehler;
    }

    public void setAnzFehler(BigDecimal anzFehler) {
        this.anzFehler = anzFehler;
    }
    
    public void setAnzFehler(int anzFehler) {
        this.anzFehler = BigDecimal.valueOf(anzFehler);
    }

    public String getBereich() {
        return bereich;
    }

    public void setBereich(String bereich) {
        this.bereich = bereich;
    }

    public String getAufNr() {
        return aufNr;
    }

    public void setAufNr(String aufNr) {
        this.aufNr = aufNr;
    }
    
    // Convenience getters for compatibility
    public String getCompanyCode() {
        return pakz;
    }
    
    public void setCompanyCode(String companyCode) {
        this.pakz = companyCode;
    }
    
    public String getClaimNumber() {
        return claimNr;
    }
    
    public void setClaimNumber(String claimNumber) {
        this.claimNr = claimNumber;
    }
    
    public String getChassisNumber() {
        return chassisNr;
    }
    
    public void setChassisNumber(String chassisNumber) {
        this.chassisNr = chassisNumber;
    }
    
    public String getCustomerNumber() {
        return kdNr;
    }
    
    public void setCustomerNumber(String customerNumber) {
        this.kdNr = customerNumber;
    }
    
    public String getCustomerName() {
        return kdName;
    }
    
    public void setCustomerName(String customerName) {
        this.kdName = customerName;
    }
    
    public String getClaimNumberSde() {
        return claimNrSde;
    }
    
    public void setClaimNumberSde(String claimNumberSde) {
        this.claimNrSde = claimNumberSde;
    }
    
    public String getInvoiceNumber() {
        return rechNr;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.rechNr = invoiceNumber;
    }
    
    public String getInvoiceDate() {
        return rechDatum;
    }
    
    public void setInvoiceDate(String invoiceDate) {
        this.rechDatum = invoiceDate;
    }
    
    public String getOrderNumber() {
        return auftragsNr;
    }
    
    public void setOrderNumber(String orderNumber) {
        this.auftragsNr = orderNumber;
    }
    
    public String getLicensePlate() {
        return kennzeichen;
    }
    
    public void setLicensePlate(String licensePlate) {
        this.kennzeichen = licensePlate;
    }
    
    public String getRepairDate() {
        return repDatum != null ? repDatum.toBigInteger().toString() : "0";
    }
    
    public void setRepairDate(int repairDate) {
        this.repDatum = BigDecimal.valueOf(repairDate);
    }
    
    public int getErrorCount() {
        return anzFehler != null ? anzFehler.intValue() : 0;
    }
    
    public void setErrorCount(int errorCount) {
        this.anzFehler = BigDecimal.valueOf(errorCount);
    }
    
    public String getWorkshopType() {
        return wete;
    }
    
    public void setWorkshopType(String workshopType) {
        this.wete = workshopType;
    }
    
    public void setRegistrationDate(Integer registrationDate) {
        this.zulDatum = registrationDate != null ? BigDecimal.valueOf(registrationDate) : BigDecimal.ZERO;
    }
    
    public void setMileage(Integer mileage) {
        this.kmStand = mileage != null ? BigDecimal.valueOf(mileage) : BigDecimal.ZERO;
    }
    
    public void setProductType(Integer productType) {
        this.produktTyp = productType != null ? BigDecimal.valueOf(productType) : BigDecimal.ONE;
    }
    
    public void setAttachment(String attachment) {
        this.anhang = attachment;
    }
    
    public void setForeigner(String foreigner) {
        this.auslaender = foreigner;
    }
    
    public String getArea() {
        return bereich;
    }
    
    public void setArea(String area) {
        this.bereich = area;
    }
    
    public void setDepartment(String department) {
        this.bereich = department;
    }
    
    public void setWorkshopTheke(String workshopTheke) {
        this.wete = workshopTheke;
    }
    
    public void setSdpsOrderNumber(String sdpsOrderNumber) {
        this.aufNr = sdpsOrderNumber;
    }
}