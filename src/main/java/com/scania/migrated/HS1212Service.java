package com.scania.warranty.claim;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// JPA Entity for HSG71PF
@Entity
@Table(name = "HSG71PF")
class Hsg71pfEntity {
    @Id
    @Column(name = "PAKZ", length = 3)
    private String pakz;

    @Column(name = "RECH.-NR.", length = 5)
    private String rechNr;

    @Column(name = "RECH.-DATUM", length = 8)
    private String rechDatum;

    @Column(name = "AUFTRAGS-NR.", length = 5)
    private String auftragsNr;

    @Column(name = "WETE", length = 1)
    private String wete;

    @Column(name = "CLAIM-NR.", length = 8)
    private String claimNr;

    @Column(name = "CHASSIS-NR.", length = 7)
    private String chassisNr;

    @Column(name = "KENNZEICHEN", length = 10)
    private String kennzeichen;

    @Column(name = "ZUL.-DATUM", precision = 8, scale = 0)
    private BigDecimal zulDatum;

    @Column(name = "REP.-DATUM", precision = 8, scale = 0)
    private BigDecimal repDatum;

    @Column(name = "KM-STAND", precision = 3, scale = 0)
    private BigDecimal kmStand;

    @Column(name = "PRODUKT-TYP", precision = 1, scale = 0)
    private BigDecimal produktTyp;

    @Column(name = "ANHANG", length = 1)
    private String anhang;

    @Column(name = "AUSL#NDER", length = 1)
    private String auslaender;

    @Column(name = "KD-NR.", length = 6)
    private String kdNr;

    @Column(name = "KD-NAME", length = 30)
    private String kdName;

    @Column(name = "CLAIM-NR. SDE", length = 8)
    private String claimNrSde;

    @Column(name = "STATUS CODE SDE", precision = 2, scale = 0)
    private BigDecimal statusCodeSde;

    @Column(name = "ANZ. FEHLER", precision = 2, scale = 0)
    private BigDecimal anzFehler;

    @Column(name = "BEREICH", length = 1)
    private String bereich;

    @Column(name = "AUF.NR.", length = 10)
    private String aufNr;

    // Getters and setters
    public String getPakz() { return pakz; }
    public void setPakz(String pakz) { this.pakz = pakz; }
    public String getRechNr() { return rechNr; }
    public void setRechNr(String rechNr) { this.rechNr = rechNr; }
    public String getRechDatum() { return rechDatum; }
    public void setRechDatum(String rechDatum) { this.rechDatum = rechDatum; }
    public String getAuftragsNr() { return auftragsNr; }
    public void setAuftragsNr(String auftragsNr) { this.auftragsNr = auftragsNr; }
    public String getWete() { return wete; }
    public void setWete(String wete) { this.wete = wete; }
    public String getClaimNr() { return claimNr; }
    public void setClaimNr(String claimNr) { this.claimNr = claimNr; }
    public String getChassisNr() { return chassisNr; }
    public void setChassisNr(String chassisNr) { this.chassisNr = chassisNr; }
    public String getKennzeichen() { return kennzeichen; }
    public void setKennzeichen(String kennzeichen) { this.kennzeichen = kennzeichen; }
    public BigDecimal getZulDatum() { return zulDatum; }
    public void setZulDatum(BigDecimal zulDatum) { this.zulDatum = zulDatum; }
    public BigDecimal getRepDatum() { return repDatum; }
    public void setRepDatum(BigDecimal repDatum) { this.repDatum = repDatum; }
    public BigDecimal getKmStand() { return kmStand; }
    public void setKmStand(BigDecimal kmStand) { this.kmStand = kmStand; }
    public BigDecimal getProduktTyp() { return produktTyp; }
    public void setProduktTyp(BigDecimal produktTyp) { this.produktTyp = produktTyp; }
    public String getAnhang() { return anhang; }
    public void setAnhang(String anhang) { this.anhang = anhang; }
    public String getAuslaender() { return auslaender; }
    public void setAuslaender(String auslaender) { this.auslaender = auslaender; }
    public String getKdNr() { return kdNr; }
    public void setKdNr(String kdNr) { this.kdNr = kdNr; }
    public String getKdName() { return kdName; }
    public void setKdName(String kdName) { this.kdName = kdName; }
    public String getClaimNrSde() { return claimNrSde; }
    public void setClaimNrSde(String claimNrSde) { this.claimNrSde = claimNrSde; }
    public BigDecimal getStatusCodeSde() { return statusCodeSde; }
    public void setStatusCodeSde(BigDecimal statusCodeSde) { this.statusCodeSde = statusCodeSde; }
    public BigDecimal getAnzFehler() { return anzFehler; }
    public void setAnzFehler(BigDecimal anzFehler) { this.anzFehler = anzFehler; }
    public String getBereich() { return bereich; }
    public void setBereich(String bereich) { this.bereich = bereich; }
    public String getAufNr() { return aufNr; }
    public void setAufNr(String aufNr) { this.aufNr = aufNr; }
}

// JPA Entity for S3F003
@Entity
@Table(name = "S3F003")
class S3f003Entity {
    @Id
    @Column(name = "Dist Wrnty Cust No", length = 48)
    private String distWrntyCustNo;

    @Column(name = "G/A Cust. Number", length = 10)
    private String gaCustNumber;

    @Column(name = "Dist Name", length = 30)
    private String distName;

    @Column(name = "Dist Short Name 1", length = 15)
    private String distShortName1;

    @Column(name = "Dist Short Name 2", length = 6)
    private String distShortName2;

    @Column(name = "Dist Locn /Town", length = 15)
    private String distLocnTown;

    @Column(name = "Parts Dist. Number", length = 10)
    private String partsDistNumber;

    @Column(name = "Curr Claim Rg Start No", precision = 8, scale = 0)
    private BigDecimal currClaimRgStartNo;

    @Column(name = "Curr Claim Rg End No", precision = 8, scale = 0)
    private BigDecimal currClaimRgEndNo;

    @Column(name = "Prev Claim Rg Start No", precision = 8, scale = 0)
    private BigDecimal prevClaimRgStartNo;

    @Column(name = "Prev Claim Rg End No", precision = 8, scale = 0)
    private BigDecimal prevClaimRgEndNo;

    @Column(name = "Stnd Labour Rt/Hr", precision = 8, scale = 2)
    private BigDecimal stndLabourRtHr;

    @Column(name = "Labour Rt/Hr 2", precision = 8, scale = 2)
    private BigDecimal labourRtHr2;

    @Column(name = "Labour Rt/Hr 3", precision = 8, scale = 2)
    private BigDecimal labourRtHr3;

    @Column(name = "Eff Per Start Date 1", precision = 8, scale = 0)
    private BigDecimal effPerStartDate1;

    @Column(name = "Eff Per End Date 1", precision = 8, scale = 0)
    private BigDecimal effPerEndDate1;

    @Column(name = "Eff Per Start Date 2", precision = 8, scale = 0)
    private BigDecimal effPerStartDate2;

    @Column(name = "Eff Per End Date 2", precision = 8, scale = 0)
    private BigDecimal effPerEndDate2;

    @Column(name = "Eff Per Start Date 3", precision = 8, scale = 0)
    private BigDecimal effPerStartDate3;

    @Column(name = "Eff Per End Date 3", precision = 8, scale = 0)
    private BigDecimal effPerEndDate3;

    @Column(name = "Dist Lab Uplift", precision = 5, scale = 4)
    private BigDecimal distLabUplift;

    @Column(name = "Purch Split S/Order", precision = 3, scale = 0)
    private BigDecimal purchSplitSOrder;

    @Column(name = "Purch Split VOR", precision = 3, scale = 0)
    private BigDecimal purchSplitVor;

    @Column(name = "L/Val Comp Uplift %", precision = 5, scale = 4)
    private BigDecimal lValCompUpliftPct;

    @Column(name = "BML/ Hand. Uplift %", precision = 5, scale = 4)
    private BigDecimal bmlHandUpliftPct;

    @Column(name = "Dist. Labour Uplift Factor 2", precision = 5, scale = 4)
    private BigDecimal distLabourUpliftFactor2;

    @Column(name = "Dist. Labour Uplift Factor 3", precision = 5, scale = 4)
    private BigDecimal distLabourUpliftFactor3;

    @Column(name = "Dist. compensation forcore uplift.", length = 1)
    private String distCompensationForcoreUplift;

    @Column(name = "Online dlr access all claims", length = 1)
    private String onlineDlrAccessAllClaims;

    @Column(name = "VAT code in Leg System", length = 10)
    private String vatCodeInLegSystem;

    @Column(name = "VAT %", precision = 5, scale = 3)
    private BigDecimal vatPct;

    // Getters and setters
    public String getDistWrntyCustNo() { return distWrntyCustNo; }
    public void setDistWrntyCustNo(String distWrntyCustNo) { this.distWrntyCustNo = distWrntyCustNo; }
    public String getGaCustNumber() { return gaCustNumber; }
    public void setGaCustNumber(String gaCustNumber) { this.gaCustNumber = gaCustNumber; }
    public String getDistName() { return distName; }
    public void setDistName(String distName) { this.distName = distName; }
    public String getDistShortName1() { return distShortName1; }
    public void setDistShortName1(String distShortName1) { this.distShortName1 = distShortName1; }
    public String getDistShortName2() { return distShortName2; }
    public void setDistShortName2(String distShortName2) { this.distShortName2 = distShortName2; }
    public String getDistLocnTown() { return distLocnTown; }
    public void setDistLocnTown(String distLocnTown) { this.distLocnTown = distLocnTown; }
    public String getPartsDistNumber() { return partsDistNumber; }
    public void setPartsDistNumber(String partsDistNumber) { this.partsDistNumber = partsDistNumber; }
    public BigDecimal getCurrClaimRgStartNo() { return currClaimRgStartNo; }
    public void setCurrClaimRgStartNo(BigDecimal currClaimRgStartNo) { this.currClaimRgStartNo = currClaimRgStartNo; }
    public BigDecimal getCurrClaimRgEndNo() { return currClaimRgEndNo; }
    public void setCurrClaimRgEndNo(BigDecimal currClaimRgEndNo) { this.currClaimRgEndNo = currClaimRgEndNo; }
    public BigDecimal getPrevClaimRgStartNo() { return prevClaimRgStartNo; }
    public void setPrevClaimRgStartNo(BigDecimal prevClaimRgStartNo) { this.prevClaimRgStartNo = prevClaimRgStartNo; }
    public BigDecimal getPrevClaimRgEndNo() { return prevClaimRgEndNo; }
    public void setPrevClaimRgEndNo(BigDecimal prevClaimRgEndNo) { this.prevClaimRgEndNo = prevClaimRgEndNo; }
    public BigDecimal getStndLabourRtHr() { return stndLabourRtHr; }
    public void setStndLabourRtHr(BigDecimal stndLabourRtHr) { this.stndLabourRtHr = stndLabourRtHr; }
    public BigDecimal getLabourRtHr2() { return labourRtHr2; }
    public void setLabourRtHr2(BigDecimal labourRtHr2) { this.labourRtHr2 = labourRtHr2; }
    public BigDecimal getLabourRtHr3() { return labourRtHr3; }
    public void setLabourRtHr3(BigDecimal labourRtHr3) { this.labourRtHr3 = labourRtHr3; }
    public BigDecimal getEffPerStartDate1() { return effPerStartDate1; }
    public void setEffPerStartDate1(BigDecimal effPerStartDate1) { this.effPerStartDate1 = effPerStartDate1; }
    public BigDecimal getEffPerEndDate1() { return effPerEndDate1; }
    public void setEffPerEndDate1(BigDecimal effPerEndDate1) { this.effPerEndDate1 = effPerEndDate1; }
    public BigDecimal getEffPerStartDate2() { return effPerStartDate2; }
    public void setEffPerStartDate2(BigDecimal effPerStartDate2) { this.effPerStartDate2 = effPerStartDate2; }
    public BigDecimal getEffPerEndDate2() { return effPerEndDate2; }
    public void setEffPerEndDate2(BigDecimal effPerEndDate2) { this.effPerEndDate2 = effPerEndDate2; }
    public BigDecimal getEffPerStartDate3() { return effPerStartDate3; }
    public void setEffPerStartDate3(BigDecimal effPerStartDate3) { this.effPerStartDate3 = effPerStartDate3; }
    public BigDecimal getEffPerEndDate3() { return effPerEndDate3; }
    public void setEffPerEndDate3(BigDecimal effPerEndDate3) { this.effPerEndDate3 = effPerEndDate3; }
    public BigDecimal getDistLabUplift() { return distLabUplift; }
    public void setDistLabUplift(BigDecimal distLabUplift) { this.distLabUplift = distLabUplift; }
    public BigDecimal getPurchSplitSOrder() { return purchSplitSOrder; }
    public void setPurchSplitSOrder(BigDecimal purchSplitSOrder) { this.purchSplitSOrder = purchSplitSOrder; }
    public BigDecimal getPurchSplitVor() { return purchSplitVor; }
    public void setPurchSplitVor(BigDecimal purchSplitVor) { this.purchSplitVor = purchSplitVor; }
    public BigDecimal getlValCompUpliftPct() { return lValCompUpliftPct; }
    public void setlValCompUpliftPct(BigDecimal lValCompUpliftPct) { this.lValCompUpliftPct = lValCompUpliftPct; }
    public BigDecimal getBmlHandUpliftPct() { return bmlHandUpliftPct; }
    public void setBmlHandUpliftPct(BigDecimal bmlHandUpliftPct) { this.bmlHandUpliftPct = bmlHandUpliftPct; }
    public BigDecimal getDistLabourUpliftFactor2() { return distLabourUpliftFactor2; }
    public void setDistLabourUpliftFactor2(BigDecimal distLabourUpliftFactor2) { this.distLabourUpliftFactor2 = distLabourUpliftFactor2; }
    public BigDecimal getDistLabourUpliftFactor3() { return distLabourUpliftFactor3; }
    public void setDistLabourUpliftFactor3(BigDecimal distLabourUpliftFactor3) { this.distLabourUpliftFactor3 = distLabourUpliftFactor3; }
    public String getDistCompensationForcoreUplift() { return distCompensationForcoreUplift; }
    public void setDistCompensationForcoreUplift(String distCompensationForcoreUplift) { this.distCompensationForcoreUplift = distCompensationForcoreUplift; }
    public String getOnlineDlrAccessAllClaims() { return onlineDlrAccessAllClaims; }
    public void setOnlineDlrAccessAllClaims(String onlineDlrAccessAllClaims) { this.onlineDlrAccessAllClaims = onlineDlrAccessAllClaims; }
    public String getVatCodeInLegSystem() { return vatCodeInLegSystem; }
    public void setVatCodeInLegSystem(String vatCodeInLegSystem) { this.vatCodeInLegSystem = vatCodeInLegSystem; }
    public BigDecimal getVatPct() { return vatPct; }
    public void setVatPct(BigDecimal vatPct) { this.vatPct = vatPct; }
}

// Repository interfaces
@Repository
interface Hsg71pfRepository extends JpaRepository<Hsg71pfEntity, String> {
    Optional<Hsg71pfEntity> findByPakzAndRechNrAndRechDatumAndAuftragsNrAndWete(
        String pakz, String rechNr, String rechDatum, String auftragsNr, String wete);
}

@Repository
interface S3f003Repository extends JpaRepository<S3f003Entity, String> {
    Optional<S3f003Entity> findByDistWrntyCustNo(String distWrntyCustNo);
}

// Service class implementing the business logic
@Service
@Transactional
public class HS1212Service {

    @Autowired
    private Hsg71pfRepository hsg71pfRepository;

    @Autowired
    private S3f003Repository s3f003Repository;

    private LocalDate aktdat = LocalDate.now();
    private String aktion;
    private BigDecimal anzkam;
    private String art;
    private String auto = "J";
    private String bereich = "1";
    private String bereich1 = "1";
    private BigDecimal clano6;
    private BigDecimal clano8;
    private String cmd;
    private BigDecimal cmdLen;
    private BigDecimal cnr;
    private String cn;
    private String g71050;
    private BigDecimal count;
    private BigDecimal countZ;
    private String c1 = "3";
    private String c2 = "13";
    private String c3 = "1";
    private String c4 = "4";
    private String c5 = "6";
    private String c6 = "5";
    private String epsType;
    private boolean epsCheck;
    private String epsInfo = "";
    private String fgnr17;
    private String fn;
    private BigDecimal fnn;
    private String fabrikat = "1";
    private String faino;
    private String g7105a;
    private LocalDate g7108f;
    private LocalDate g7108a;
    private BigDecimal g71040n;
    private String g7117n;
    private String g7306l = "";
    private String g73060;
    private BigDecimal g7308n;
    private BigDecimal g7310n;
    private BigDecimal ganrn;
    private String gpsart;
    private BigDecimal gps120n;
    private BigDecimal gps120;
    private String gutart;
    private BigDecimal hersteller = BigDecimal.ONE;
    private String hival = "99999999";
    private String keyKzl;
    private String aprKzl;
    private String keySdpsr;
    private String locale;
    private BigDecimal locA;
    private BigDecimal locB;
    private String modules;
    private BigDecimal matcos;
    private String msgIdc;
    private String ok;
    private String pkz;
    private BigDecimal pos;
    private String reprint = "Y";
    private String resultCode;
    private String returnValue;
    private String rwParm;
    private String rueck;
    private BigDecimal s3fCno;
    private BigDecimal gclndd;
    private BigDecimal s3fFno;
    private BigDecimal pagedd;
    private BigDecimal s3fDno;
    private BigDecimal wcusdd;
    private String scope;
    private String[] stat = new String[4];
    private String storno = "";
    private String sub060a;
    private BigDecimal sub060n;
    private String svt;
    private String gps050;
    private BigDecimal svta;
    private BigDecimal gps070;
    private BigDecimal tage = new BigDecimal(14);
    private String[] typ = new String[6];
    private String[] tnr = new String[7];
    private String tnr0 = "000000000000000000";
    private String tnr14;
    private String[] tnra = new String[7];
    private String[] txt = new String[4];
    private boolean updateSwat = true;
    private LocalDate vgldat1;
    private LocalDate vgldat2;
    private String valDmc;
    private String valScope;
    private String valDealer;
    private String wete = "1";
    private BigDecimal x;
    private BigDecimal zeile;
    private BigDecimal gps030;
    private BigDecimal zwrec2;
    private BigDecimal zla;
    private BigDecimal zlt;
    private BigDecimal zlw2;
    private String umgebung;
    private String g7100p;
    private String g7101p;
    private String g7102p;
    private String g7103p;
    private String g7104p;

    public void processClaimManagement(String art, String g7100p, String g7101p, 
                                      String g7102p, String g7103p, String g7104p) {
        this.art = art;
        this.g7100p = g7100p;
        this.g7101p = g7101p;
        this.g7102p = g7102p;
        this.g7103p = g7103p;
        this.g7104p = g7104p;

        initializeTypArray();
        
        Optional<Hsg71pfEntity> claimOpt = hsg71pfRepository.findByPakzAndRechNrAndRechDatumAndAuftragsNrAndWete(
            g7100p, g7101p, g7102p, g7103p, g7104p);
        
        if (!claimOpt.isPresent()) {
            return;
        }

        Hsg71pfEntity claim = claimOpt.get();
        
        determineBereiche(claim);
        
        BigDecimal ganrn = determinePartnerNumber(claim);
        if (ganrn == null) {
            return;
        }

        Optional<S3f003Entity> partnerOpt = s3f003Repository.findByDistWrntyCustNo(ganrn.toString());
        if (!partnerOpt.isPresent()) {
            return;
        }

        S3f003Entity partner = partnerOpt.get();
        calculateMaterialCost(claim, partner);

        if ("5".equals(art)) {
            // Display mode
            return;
        } else if ("6".equals(art)) {
            // External call mode
            return;
        } else if ("7".equals(art)) {
            // Process and continue
        } else if ("8".equals(art)) {
            // Reset art
            this.art = "";
        }

        if (claim.getStatusCodeSde().compareTo(new BigDecimal(20)) < 0) {
            validateClaimHeader(claim);
        }

        if (claim.getClaimNrSde() != null && claim.getClaimNrSde().equals("00000000")) {
            // Minimum claim processing
            return;
        }

        processClaimLoop(claim);
    }

    private void initializeTypArray() {
        typ[0] = "1 = Original Mont.  ";
        typ[1] = "2 = Orig. teil mont.";
        typ[2] = "4 = Orig. ab Lager  ";
        typ[3] = "6 = IRM Vertrag     ";
        typ[4] = "8 = SP. Maintenance ";
        typ[5] = "9 = EPC             ";
    }

    private void determineBereiche(Hsg71pfEntity claim) {
        if (claim.getBereich() != null && !claim.getBereich().isEmpty()) {
            bereich = claim.getBereich();
        } else if (claim.getProduktTyp().compareTo(new BigDecimal(2)) == 0) {
            bereich = "3";
        } else if (claim.getProduktTyp().compareTo(new BigDecimal(3)) == 0) {
            bereich = "6";
        } else {
            bereich = "1";
        }
    }

    private BigDecimal determinePartnerNumber(Hsg71pfEntity claim) {
        // Logic to determine partner number from claim
        return new BigDecimal(claim.getKdNr());
    }

    private void calculateMaterialCost(Hsg71pfEntity claim, S3f003Entity partner) {
        BigDecimal repairDate = claim.getRepDatum();
        
        if (repairDate.compareTo(partner.getEffPerStartDate1()) >= 0 && 
            repairDate.compareTo(partner.getEffPerEndDate1()) <= 0) {
            matcos = partner.getStndLabourRtHr().multiply(partner.getDistLabUplift());
        } else if (repairDate.compareTo(partner.getEffPerStartDate2()) >= 0 && 
                   repairDate.compareTo(partner.getEffPerEndDate2()) <= 0) {
            matcos = partner.getLabourRtHr2().multiply(partner.getDistLabourUpliftFactor2());
        } else if (repairDate.compareTo(partner.getEffPerStartDate3()) >= 0 && 
                   repairDate.compareTo(partner.getEffPerEndDate3()) <= 0) {
            matcos = partner.getLabourRtHr3().multiply(partner.getDistLabourUpliftFactor3());
        }
    }

    private void validateClaimHeader(Hsg71pfEntity claim) {
        boolean hasError = false;

        if (claim.getProduktTyp().compareTo(BigDecimal.ONE) < 0 || 
            claim.getProduktTyp().compareTo(new BigDecimal(5)) > 0) {
            hasError = true;
        }

        if (claim.getChassisNr() != null && !claim.getChassisNr().isEmpty()) {
            // Validate chassis number
        }

        if (claim.getRepDatum() == null || claim.getRepDatum().compareTo(BigDecimal.ZERO) <= 0) {
            hasError = true;
        }

        if (claim.getKmStand() != null && claim.getKmStand().compareTo(BigDecimal.ZERO) <= 0 && 
            claim.getChassisNr() != null && !claim.getChassisNr().isEmpty()) {
            hasError = true;
        }

        if (claim.getKmStand() != null && claim.getKmStand().compareTo(BigDecimal.ZERO) != 0 && 
            (claim.getChassisNr() == null || claim.getChassisNr().isEmpty())) {
            hasError = true;
        }
    }

    private void processClaimLoop(Hsg71pfEntity claim) {
        // Main processing loop for claim management
        // This would contain the subfile processing logic from the RPG code
    }

    private void loadClaim() {
        // Load claim data into working variables
    }

    private boolean checkClaim() {
        // Validate claim data
        return true;
    }

    private void getClaimAge() {
        // Calculate claim age
    }

    private void getDescription() {
        // Retrieve descriptions for codes
    }

    private void getDates() {
        // Retrieve dates from database
    }

    private String getModules() {
        // Retrieve modules list
        return "";
    }

    private void readPositions(String dealerId, String claimNo) {
        // Read position data for claim
    }
}
