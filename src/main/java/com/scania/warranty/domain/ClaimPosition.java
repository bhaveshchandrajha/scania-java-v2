/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * JPA entity for claim position/line items (HSGPSLF3).
 */
@Entity
@Table(name = "HSGPSLF3")
@IdClass(ClaimPositionId.class)
public class ClaimPosition {

    @Id
    @Column(name = "KUERZEL", length = 3)
    private String abbreviation;

    @Id
    @Column(name = "CLAIM-NR.", length = 8)
    private String claimNumber;

    @Id
    @Column(name = "FEHLER-NR.", length = 2)
    private String errorNumber;

    @Id
    @Column(name = "FOLGE-NR.", length = 2)
    private String sequenceNumber;

    @Id
    @Column(name = "ZEILE", precision = 3, scale = 0)
    private BigDecimal lineNumber;

    @Column(name = "AUFTEILUNG", precision = 3, scale = 0)
    private BigDecimal distribution;

    @Column(name = "SATZART", length = 3)
    private String recordType;

    @Column(name = "NR.", length = 18)
    private String number;

    @Column(name = "MENGE", precision = 5, scale = 0)
    private BigDecimal quantity;

    @Column(name = "WERT", precision = 11, scale = 2)
    private BigDecimal value;

    @Column(name = "STEUER-CODE", length = 40)
    private String taxCode;

    @Column(name = "HAUPTGRUPPE", length = 2)
    private String mainGroup;

    @Column(name = "ZEIT", precision = 3, scale = 1)
    private BigDecimal time;

    @Column(name = "GRUND", precision = 4, scale = 0)
    private BigDecimal reason;

    @Column(name = "VERG�TUNG", precision = 3, scale = 0)
    private BigDecimal compensation;

    @Column(name = "MANUELL", length = 1)
    private String manual;

    @Column(name = "KAMPAGNE", precision = 8, scale = 0)
    private BigDecimal campaign;

    @Column(name = "POS.-NR.", precision = 3, scale = 0)
    private BigDecimal positionNumber;

    @Column(name = "RESULTCODE", length = 2)
    private String resultCode;

    @Column(name = "CODE TYPE", length = 2)
    private String codeType;

    @Column(name = "CODE ID", length = 5)
    private String codeId;

    @Column(name = "COMPFAC.", precision = 3, scale = 0)
    private BigDecimal compensationFactor;

    @Column(name = "GROSSPRICE", precision = 13, scale = 2)
    private BigDecimal grossPrice;

    @Column(name = "DISCOUNT", precision = 5, scale = 2)
    private BigDecimal discount;

    @Column(name = "COMPAMOUNT", precision = 15, scale = 2)
    private BigDecimal compensationAmount;

    @Column(name = "COMPQTY", precision = 7, scale = 0)
    private BigDecimal compensationQuantity;

    @Column(name = "TYPE", length = 5)
    private String type;

    @Column(name = "MIA STATUS", length = 1)
    private String miaStatus;

    @Column(name = "CATEGORYS", length = 2)
    private String categories;

    @Column(name = "TEXT", length = 2000)
    private String text;

    // Constructors
    public ClaimPosition() {
    }

    // Getters and Setters
    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(String claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public BigDecimal getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(BigDecimal lineNumber) {
        this.lineNumber = lineNumber;
    }

    public BigDecimal getDistribution() {
        return distribution;
    }

    public void setDistribution(BigDecimal distribution) {
        this.distribution = distribution;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getMainGroup() {
        return mainGroup;
    }

    public void setMainGroup(String mainGroup) {
        this.mainGroup = mainGroup;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal time) {
        this.time = time;
    }

    public BigDecimal getReason() {
        return reason;
    }

    public void setReason(BigDecimal reason) {
        this.reason = reason;
    }

    public BigDecimal getCompensation() {
        return compensation;
    }

    public void setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public BigDecimal getCampaign() {
        return campaign;
    }

    public void setCampaign(BigDecimal campaign) {
        this.campaign = campaign;
    }

    public BigDecimal getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(BigDecimal positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
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

    public BigDecimal getCompensationFactor() {
        return compensationFactor;
    }

    public void setCompensationFactor(BigDecimal compensationFactor) {
        this.compensationFactor = compensationFactor;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(BigDecimal compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public BigDecimal getCompensationQuantity() {
        return compensationQuantity;
    }

    public void setCompensationQuantity(BigDecimal compensationQuantity) {
        this.compensationQuantity = compensationQuantity;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}