/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * JPA entity for claim header (HSG71LF2).
 */
@Entity
@Table(name = "HSG71LF2")
@IdClass(ClaimId.class)
public class Claim {

    @Id
    @Column(name = "PAKZ", length = 3, nullable = false)
    private String pakz; // @rpg-trace: schema

    @Id
    @Column(name = "RECH.-NR.", length = 5, nullable = false)
    private String rechNr; // @rpg-trace: schema

    @Column(name = "RECH.-DATUM", length = 8, nullable = false)
    private String rechDatum; // @rpg-trace: schema

    @Id
    @Column(name = "AUFTRAGS-NR.", length = 5, nullable = false)
    private String auftragsNr; // @rpg-trace: schema

    @Id
    @Column(name = "WETE", length = 1, nullable = false)
    private String wete; // @rpg-trace: schema

    @Id
    @Column(name = "CLAIM-NR.", length = 8, nullable = false)
    private String claimNr; // @rpg-trace: schema

    @Column(name = "CHASSIS-NR.", length = 7, nullable = false)
    private String chassisNr; // @rpg-trace: schema

    @Column(name = "KENNZEICHEN", length = 10, nullable = false)
    private String kennzeichen; // @rpg-trace: schema

    @Column(name = "ZUL.-DATUM", precision = 8, scale = 0, nullable = false)
    private Integer zulDatum; // @rpg-trace: schema

    @Column(name = "REP.-DATUM", precision = 8, scale = 0, nullable = false)
    private Integer repDatum; // @rpg-trace: schema

    @Column(name = "KM-STAND", precision = 3, scale = 0, nullable = false)
    private Integer kmStand; // @rpg-trace: schema

    @Column(name = "PRODUKT-TYP", precision = 1, scale = 0, nullable = false)
    private Integer produktTyp; // @rpg-trace: schema

    @Column(name = "ANHANG", length = 1, nullable = false)
    private String anhang; // @rpg-trace: schema

    @Column(name = "AUSL#NDER", length = 1, nullable = false)
    private String auslaender; // @rpg-trace: schema

    @Column(name = "KD-NR.", length = 6, nullable = false)
    private String kdNr; // @rpg-trace: schema

    @Column(name = "KD-NAME", length = 30, nullable = false)
    private String kdName; // @rpg-trace: schema

    @Column(name = "CLAIM-NR. SDE", length = 8, nullable = false)
    private String claimNrSde; // @rpg-trace: schema

    @Column(name = "STATUS CODE SDE", precision = 2, scale = 0, nullable = false)
    private Integer statusCodeSde; // @rpg-trace: schema

    @Column(name = "ANZ. FEHLER", precision = 2, scale = 0, nullable = false)
    private Integer anzFehler; // @rpg-trace: schema

    @Column(name = "BEREICH", length = 1, nullable = false)
    private String bereich; // @rpg-trace: schema

    @Column(name = "AUF.NR.", length = 10, nullable = false)
    private String aufNr; // @rpg-trace: schema

    // Constructors
    public Claim() {
    }

    // Getters and Setters
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

    public Integer getZulDatum() {
        return zulDatum;
    }

    public void setZulDatum(Integer zulDatum) {
        this.zulDatum = zulDatum;
    }

    public Integer getRepDatum() {
        return repDatum;
    }

    public void setRepDatum(Integer repDatum) {
        this.repDatum = repDatum;
    }

    public Integer getKmStand() {
        return kmStand;
    }

    public void setKmStand(Integer kmStand) {
        this.kmStand = kmStand;
    }

    public Integer getProduktTyp() {
        return produktTyp;
    }

    public void setProduktTyp(Integer produktTyp) {
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

    public Integer getStatusCodeSde() {
        return statusCodeSde;
    }

    public void setStatusCodeSde(Integer statusCodeSde) {
        this.statusCodeSde = statusCodeSde;
    }

    public Integer getAnzFehler() {
        return anzFehler;
    }

    public void setAnzFehler(Integer anzFehler) {
        this.anzFehler = anzFehler;
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

    // Convenience methods for ClaimSearchService and ClaimManagementService
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

    public String getChassisNumber() {
        return chassisNr;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNr = chassisNumber;
    }

    public String getLicensePlate() {
        return kennzeichen;
    }

    public void setLicensePlate(String licensePlate) {
        this.kennzeichen = licensePlate;
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

    public String getRepairDate() {
        return repDatum != null ? String.format("%08d", repDatum) : "";
    }

    public void setRepairDate(Integer repairDate) {
        this.repDatum = repairDate;
    }

    public String getArea() {
        return bereich;
    }

    public void setArea(String area) {
        this.bereich = area;
    }

    public void setRegistrationDate(Integer registrationDate) {
        this.zulDatum = registrationDate;
    }

    public void setMileage(Integer mileage) {
        this.kmStand = mileage;
    }

    public void setProductType(Integer productType) {
        this.produktTyp = productType;
    }

    public void setAttachment(String attachment) {
        this.anhang = attachment;
    }

    public void setForeigner(String foreigner) {
        this.auslaender = foreigner;
    }

    public void setErrorCount(Integer errorCount) {
        this.anzFehler = errorCount;
    }

    public void setDepartment(String department) {
        // Department maps to bereich
        this.bereich = department;
    }

    public void setWorkshopTheke(String workshopTheke) {
        // WorkshopTheke maps to wete
        this.wete = workshopTheke;
    }

    public void setSdpsOrderNumber(String sdpsOrderNumber) {
        this.aufNr = sdpsOrderNumber;
    }

    public String getWorkshopType() {
        return wete;
    }

    public void setWorkshopType(String workshopType) {
        this.wete = workshopType;
    }
}