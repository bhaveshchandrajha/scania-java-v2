/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * JPA entity for claim error/failure details (HSG73PF).
 */
@Entity
@Table(name = "HSG73PF")
@IdClass(ClaimErrorId.class)
public class ClaimError {
    
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
    
    @Id
    @Column(name = "BEREICH", length = 1, nullable = false)
    private String bereich;
    
    @Column(name = "CLAIM-NR.", length = 8, nullable = false)
    private String claimNr;
    
    @Id
    @Column(name = "FEHLER-NR.", length = 2, nullable = false)
    private String fehlerNr;
    
    @Id
    @Column(name = "FOLGE-NR.", length = 2, nullable = false)
    private String folgeNr;
    
    @Column(name = "FEHLER-TEIL", length = 18, nullable = false)
    private String fehlerTeil;
    
    @Column(name = "HAUPTGRUPPE", length = 2, nullable = false)
    private String hauptgruppe;
    
    @Column(name = "NEBENGRUPPE", length = 2, nullable = false)
    private String nebengruppe;
    
    @Column(name = "SCHAD.-C1", length = 2, nullable = false)
    private String schadC1;
    
    @Column(name = "SCHAD.-C2", length = 2, nullable = false)
    private String schadC2;
    
    @Column(name = "TEXT1", length = 65, nullable = false)
    private String text1;
    
    @Column(name = "TEXT2", length = 65, nullable = false)
    private String text2;
    
    @Column(name = "STEUER CODE", length = 2, nullable = false)
    private String steuerCode;
    
    @Column(name = "BEW. CODE1", length = 2, nullable = false)
    private String bewCode1;
    
    @Column(name = "BEW. CODE2", precision = 4, scale = 0, nullable = false)
    private BigDecimal bewCode2;
    
    @Column(name = "BEW. DATUM", precision = 8, scale = 0, nullable = false)
    private BigDecimal bewDatum;
    
    @Column(name = "VERG. MAT.", precision = 3, scale = 0, nullable = false)
    private BigDecimal vergMat;
    
    @Column(name = "VERG. ARB.", precision = 3, scale = 0, nullable = false)
    private BigDecimal vergArb;
    
    @Column(name = "VERG. SPEZ.", precision = 3, scale = 0, nullable = false)
    private BigDecimal vergSpez;
    
    @Column(name = "BEANTR. MAT.", precision = 11, scale = 2, nullable = false)
    private BigDecimal beantrMat;
    
    @Column(name = "BEANTRG. ARB.", precision = 11, scale = 2, nullable = false)
    private BigDecimal beantrArb;
    
    @Column(name = "BEANTRG. SPEZ.", precision = 11, scale = 2, nullable = false)
    private BigDecimal beantrSpez;
    
    @Column(name = "CLAIM-ART", precision = 1, scale = 0, nullable = false)
    private BigDecimal claimArt;
    
    @Column(name = "V.-REP.-DATUM", precision = 8, scale = 0, nullable = false)
    private BigDecimal vRepDatum;
    
    @Column(name = "V.-KM-STAND", precision = 3, scale = 0, nullable = false)
    private BigDecimal vKmStand;
    
    @Column(name = "FELDTEST-NR.", precision = 6, scale = 0, nullable = false)
    private BigDecimal feldtestNr;
    
    @Column(name = "KAMPAGNEN-NR.", length = 8, nullable = false)
    private String kampagnenNr;
    
    @Column(name = "EPS", length = 20, nullable = false)
    private String eps;
    
    @Column(name = "STATUS CODE", precision = 2, scale = 0, nullable = false)
    private BigDecimal statusCode;
    
    @Column(name = "VARIANT CODE", precision = 2, scale = 0, nullable = false)
    private BigDecimal variantCode;
    
    @Column(name = "ACTION CODE", precision = 2, scale = 0, nullable = false)
    private BigDecimal actionCode;
    
    @Column(name = "TEXT3", length = 65, nullable = false)
    private String text3;
    
    @Column(name = "TEXT4", length = 65, nullable = false)
    private String text4;
    
    @Column(name = "FEHLER-NR. SDE", length = 2, nullable = false)
    private String fehlerNrSde;
    
    @Column(name = "ANHANG", length = 1, nullable = false)
    private String anhang;
    
    @Column(name = "SOURCE", length = 5, nullable = false)
    private String source;
    
    @Column(name = "COMPLAIN", length = 5, nullable = false)
    private String complain;
    
    @Column(name = "SYMPTOM", length = 5, nullable = false)
    private String symptom;
    
    @Column(name = "FAILURE", length = 5, nullable = false)
    private String failure;
    
    @Column(name = "LOCATION", length = 5, nullable = false)
    private String location;
    
    @Column(name = "REPAIR", length = 5, nullable = false)
    private String repair;
    
    @Column(name = "ERG.CODE", length = 2, nullable = false)
    private String ergCode;
    
    @Column(name = "RESULT1", length = 2, nullable = false)
    private String result1;
    
    @Column(name = "RESULT2", length = 5, nullable = false)
    private String result2;
    
    @Column(name = "FAULT1", length = 2, nullable = false)
    private String fault1;
    
    @Column(name = "FAULT2", length = 5, nullable = false)
    private String fault2;
    
    @Column(name = "REPLY1", length = 2, nullable = false)
    private String reply1;
    
    @Column(name = "REPLY2", length = 5, nullable = false)
    private String reply2;
    
    @Column(name = "EXPLANATION1", length = 2, nullable = false)
    private String explanation1;
    
    @Column(name = "EXPLANATION2", length = 5, nullable = false)
    private String explanation2;

    public ClaimError() {
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

    public String getBereich() {
        return bereich;
    }

    public void setBereich(String bereich) {
        this.bereich = bereich;
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

    public String getFehlerTeil() {
        return fehlerTeil;
    }

    public void setFehlerTeil(String fehlerTeil) {
        this.fehlerTeil = fehlerTeil;
    }

    public String getHauptgruppe() {
        return hauptgruppe;
    }

    public void setHauptgruppe(String hauptgruppe) {
        this.hauptgruppe = hauptgruppe;
    }

    public String getNebengruppe() {
        return nebengruppe;
    }

    public void setNebengruppe(String nebengruppe) {
        this.nebengruppe = nebengruppe;
    }

    public String getSchadC1() {
        return schadC1;
    }

    public void setSchadC1(String schadC1) {
        this.schadC1 = schadC1;
    }

    public String getSchadC2() {
        return schadC2;
    }

    public void setSchadC2(String schadC2) {
        this.schadC2 = schadC2;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getSteuerCode() {
        return steuerCode;
    }

    public void setSteuerCode(String steuerCode) {
        this.steuerCode = steuerCode;
    }

    public String getBewCode1() {
        return bewCode1;
    }

    public void setBewCode1(String bewCode1) {
        this.bewCode1 = bewCode1;
    }

    public BigDecimal getBewCode2() {
        return bewCode2;
    }

    public void setBewCode2(BigDecimal bewCode2) {
        this.bewCode2 = bewCode2;
    }
    
    public void setBewCode2(int bewCode2) {
        this.bewCode2 = BigDecimal.valueOf(bewCode2);
    }

    public BigDecimal getBewDatum() {
        return bewDatum;
    }

    public void setBewDatum(BigDecimal bewDatum) {
        this.bewDatum = bewDatum;
    }
    
    public void setBewDatum(int bewDatum) {
        this.bewDatum = BigDecimal.valueOf(bewDatum);
    }

    public BigDecimal getVergMat() {
        return vergMat;
    }

    public void setVergMat(BigDecimal vergMat) {
        this.vergMat = vergMat;
    }
    
    public void setVergMat(int vergMat) {
        this.vergMat = BigDecimal.valueOf(vergMat);
    }

    public BigDecimal getVergArb() {
        return vergArb;
    }

    public void setVergArb(BigDecimal vergArb) {
        this.vergArb = vergArb;
    }
    
    public void setVergArb(int vergArb) {
        this.vergArb = BigDecimal.valueOf(vergArb);
    }

    public BigDecimal getVergSpez() {
        return vergSpez;
    }

    public void setVergSpez(BigDecimal vergSpez) {
        this.vergSpez = vergSpez;
    }
    
    public void setVergSpez(int vergSpez) {
        this.vergSpez = BigDecimal.valueOf(vergSpez);
    }

    public BigDecimal getBeantrMat() {
        return beantrMat;
    }

    public void setBeantrMat(BigDecimal beantrMat) {
        this.beantrMat = beantrMat;
    }

    public BigDecimal getBeantrArb() {
        return beantrArb;
    }

    public void setBeantrArb(BigDecimal beantrArb) {
        this.beantrArb = beantrArb;
    }

    public BigDecimal getBeantrSpez() {
        return beantrSpez;
    }

    public void setBeantrSpez(BigDecimal beantrSpez) {
        this.beantrSpez = beantrSpez;
    }

    public BigDecimal getClaimArt() {
        return claimArt;
    }

    public void setClaimArt(BigDecimal claimArt) {
        this.claimArt = claimArt;
    }
    
    public void setClaimArt(int claimArt) {
        this.claimArt = BigDecimal.valueOf(claimArt);
    }

    public BigDecimal getvRepDatum() {
        return vRepDatum;
    }

    public void setvRepDatum(BigDecimal vRepDatum) {
        this.vRepDatum = vRepDatum;
    }
    
    public void setvRepDatum(int vRepDatum) {
        this.vRepDatum = BigDecimal.valueOf(vRepDatum);
    }

    public BigDecimal getvKmStand() {
        return vKmStand;
    }

    public void setvKmStand(BigDecimal vKmStand) {
        this.vKmStand = vKmStand;
    }
    
    public void setvKmStand(int vKmStand) {
        this.vKmStand = BigDecimal.valueOf(vKmStand);
    }

    public BigDecimal getFeldtestNr() {
        return feldtestNr;
    }

    public void setFeldtestNr(BigDecimal feldtestNr) {
        this.feldtestNr = feldtestNr;
    }
    
    public void setFeldtestNr(int feldtestNr) {
        this.feldtestNr = BigDecimal.valueOf(feldtestNr);
    }

    public String getKampagnenNr() {
        return kampagnenNr;
    }

    public void setKampagnenNr(String kampagnenNr) {
        this.kampagnenNr = kampagnenNr;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public BigDecimal getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(BigDecimal statusCode) {
        this.statusCode = statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = BigDecimal.valueOf(statusCode);
    }

    public BigDecimal getVariantCode() {
        return variantCode;
    }

    public void setVariantCode(BigDecimal variantCode) {
        this.variantCode = variantCode;
    }
    
    public void setVariantCode(int variantCode) {
        this.variantCode = BigDecimal.valueOf(variantCode);
    }

    public BigDecimal getActionCode() {
        return actionCode;
    }

    public void setActionCode(BigDecimal actionCode) {
        this.actionCode = actionCode;
    }
    
    public void setActionCode(int actionCode) {
        this.actionCode = BigDecimal.valueOf(actionCode);
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getFehlerNrSde() {
        return fehlerNrSde;
    }

    public void setFehlerNrSde(String fehlerNrSde) {
        this.fehlerNrSde = fehlerNrSde;
    }

    public String getAnhang() {
        return anhang;
    }

    public void setAnhang(String anhang) {
        this.anhang = anhang;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String getErgCode() {
        return ergCode;
    }

    public void setErgCode(String ergCode) {
        this.ergCode = ergCode;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }

    public String getFault1() {
        return fault1;
    }

    public void setFault1(String fault1) {
        this.fault1 = fault1;
    }

    public String getFault2() {
        return fault2;
    }

    public void setFault2(String fault2) {
        this.fault2 = fault2;
    }

    public String getReply1() {
        return reply1;
    }

    public void setReply1(String reply1) {
        this.reply1 = reply1;
    }

    public String getReply2() {
        return reply2;
    }

    public void setReply2(String reply2) {
        this.reply2 = reply2;
    }

    public String getExplanation1() {
        return explanation1;
    }

    public void setExplanation1(String explanation1) {
        this.explanation1 = explanation1;
    }

    public String getExplanation2() {
        return explanation2;
    }

    public void setExplanation2(String explanation2) {
        this.explanation2 = explanation2;
    }
    
    // Convenience getters/setters for compatibility
    public String getMainGroup() {
        return hauptgruppe;
    }
    
    public void setMainGroup(String mainGroup) {
        this.hauptgruppe = mainGroup;
    }
    
    public String getSubGroup() {
        return nebengruppe;
    }
    
    public void setSubGroup(String subGroup) {
        this.nebengruppe = subGroup;
    }
    
    public String getControlCode() {
        return steuerCode;
    }
    
    public void setControlCode(String controlCode) {
        this.steuerCode = controlCode;
    }
    
    public Integer getClaimType() {
        return claimArt != null ? claimArt.intValue() : null;
    }
    
    public void setClaimType(Integer claimType) {
        this.claimArt = claimType != null ? BigDecimal.valueOf(claimType) : BigDecimal.ZERO;
    }
    
    public String getCompanyCode() {
        return pakz;
    }
    
    public void setCompanyCode(String companyCode) {
        this.pakz = companyCode;
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
    
    public String getArea() {
        return bereich;
    }
    
    public void setArea(String area) {
        this.bereich = area;
    }
    
    public String getClaimNumber() {
        return claimNr;
    }
    
    public void setClaimNumber(String claimNumber) {
        this.claimNr = claimNumber;
    }
    
    public String getErrorNumber() {
        return fehlerNr;
    }
    
    public void setErrorNumber(String errorNumber) {
        this.fehlerNr = errorNumber;
    }
    
    public String getSequenceNumber() {
        return folgeNr;
    }
    
    public void setSequenceNumber(String sequenceNumber) {
        this.folgeNr = sequenceNumber;
    }
    
    public String getErrorPart() {
        return fehlerTeil;
    }
    
    public void setErrorPart(String errorPart) {
        this.fehlerTeil = errorPart;
    }
    
    public String getDamageCode1() {
        return schadC1;
    }
    
    public void setDamageCode1(String damageCode1) {
        this.schadC1 = damageCode1;
    }
    
    public String getDamageCode2() {
        return schadC2;
    }
    
    public void setDamageCode2(String damageCode2) {
        this.schadC2 = damageCode2;
    }
    
    public String getAssessmentCode1() {
        return bewCode1;
    }
    
    public void setAssessmentCode1(String assessmentCode1) {
        this.bewCode1 = assessmentCode1;
    }
    
    public void setAssessmentCode2(int assessmentCode2) {
        this.bewCode2 = BigDecimal.valueOf(assessmentCode2);
    }
    
    public void setAssessmentDate(int assessmentDate) {
        this.bewDatum = BigDecimal.valueOf(assessmentDate);
    }
    
    public void setCompensatedMaterial(int compensatedMaterial) {
        this.vergMat = BigDecimal.valueOf(compensatedMaterial);
    }
    
    public void setCompensatedLabor(int compensatedLabor) {
        this.vergArb = BigDecimal.valueOf(compensatedLabor);
    }
    
    public void setCompensatedSpecial(int compensatedSpecial) {
        this.vergSpez = BigDecimal.valueOf(compensatedSpecial);
    }
    
    public void setRequestedMaterial(BigDecimal requestedMaterial) {
        this.beantrMat = requestedMaterial;
    }
    
    public void setRequestedLabor(BigDecimal requestedLabor) {
        this.beantrArb = requestedLabor;
    }
    
    public void setRequestedSpecial(BigDecimal requestedSpecial) {
        this.beantrSpez = requestedSpecial;
    }
    
    public void setPreviousRepairDate(int previousRepairDate) {
        this.vRepDatum = BigDecimal.valueOf(previousRepairDate);
    }
    
    public void setPreviousMileage(int previousMileage) {
        this.vKmStand = BigDecimal.valueOf(previousMileage);
    }
    
    public void setFieldTestNumber(int fieldTestNumber) {
        this.feldtestNr = BigDecimal.valueOf(fieldTestNumber);
    }
    
    public void setCampaignNumber(String campaignNumber) {
        this.kampagnenNr = campaignNumber;
    }
    
    public void setErrorNumberSde(String errorNumberSde) {
        this.fehlerNrSde = errorNumberSde;
    }
    
    public void setResultCode(String resultCode) {
        this.ergCode = resultCode;
    }
    
    public void setAttachment(String attachment) {
        this.anhang = attachment;
    }
}