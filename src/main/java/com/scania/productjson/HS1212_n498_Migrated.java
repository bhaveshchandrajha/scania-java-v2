package com.scania.productjson;

import com.example.warranty.entity.*;
import com.example.warranty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class ClaimSubroutineService {

    @Autowired
    private HSG71Repository hsg71Repository;
    
    @Autowired
    private HSG73Repository hsg73Repository;
    
    @Autowired
    private HSGPSRepository hsgpsRepository;
    
    @Autowired
    private FARSTLRepository farstlRepository;
    
    @Autowired
    private AUFWSKRepository aufwskRepository;
    
    @Autowired
    private S3F004Repository s3f004Repository;
    
    @Autowired
    private S3F103Repository s3f103Repository;
    
    @Autowired
    private S3F104Repository s3f104Repository;
    
    @Autowired
    private S3F105Repository s3f105Repository;
    
    @Autowired
    private S3F106Repository s3f106Repository;

    // Working variables
    private String mark11;
    private String mark12;
    private Integer zl1;
    private Integer zl4;
    private String sr102f;
    private String returnValue;
    private String aktion;
    private String art;
    private String auto;
    private String bereich;
    private String wete;
    private String splitt;
    private BigDecimal cnr;
    private String fgnr17;
    private String fn;
    private BigDecimal fnn;
    private String hival;
    private BigDecimal s3fCno;
    private BigDecimal s3fFno;
    private BigDecimal s3fDno;
    private String tnr;
    private String tnra;
    private BigDecimal x;
    private BigDecimal zwrec2;
    private BigDecimal zlw2;
    private BigDecimal rec2;
    private BigDecimal pag2;
    private BigDecimal err;
    private String punkt1;
    private String punkt2;
    private BigDecimal y;

    // Subroutine SB10N - Clear indicators and handle mark selection
    public void sb10n() {
        zl4 = 0;
        // Clear indicators 50-52, 53-55, 56-58, 59, 63, 74, 76
        
        if (mark12 == null || mark12.trim().isEmpty()) {
            mark12 = mark11;
            mark11 = " ";
        }
    }

    // Subroutine MARK - Adjust mark selection
    public void mark() {
        if (mark12 == null || mark12.trim().isEmpty()) {
            mark12 = mark11;
            mark11 = " ";
        }
    }

    // Subroutine SB100 - Build subfile with claim failures
    public List<SubfileRecord> sb100(String g71000, String g71010, String g71020, String g71030, String g71040) {
        List<SubfileRecord> subfileRecords = new ArrayList<>();
        zl1 = 0;

        List<HSG73Entity> failures = hsg73Repository.findByKeyG71(g71000, g71010, g71020, g71030, g71040);
        
        for (HSG73Entity failure : failures) {
            SubfileRecord record = new SubfileRecord();
            record.mark1 = "";
            record.sub060 = failure.fehlerNr;
            record.sub065 = failure.folgeNr;
            record.sub070 = failure.fehlerTeil;
            
            if (failure.kampagnenNr != null && !failure.kampagnenNr.trim().isEmpty() 
                && !failure.kampagnenNr.equals("0") && !failure.kampagnenNr.equals("00000000")) {
                record.sub070 = "KAMPAGNE" + failure.kampagnenNr;
            }
            
            record.sub080 = failure.hauptgruppe;
            record.sub100 = failure.schadC1;
            record.sub110 = failure.schadC2;
            record.sub140 = failure.steuerCode;
            record.sub180 = failure.vergMat;
            record.sub190 = failure.vergArb;
            record.sub200 = failure.vergSpez;
            record.sub240 = failure.claimArt;
            record.sub280 = failure.kampagnenNr;
            record.substa = String.format("%02d", failure.statusCode);
            
            srwert(failure);
            srrot(failure);
            srgut(failure);
            
            zl1++;
            subfileRecords.add(record);
        }
        
        if (zl1 == 0) {
            SubfileRecord emptyRecord = new SubfileRecord();
            emptyRecord.mark1 = "";
            zl1 = 1;
            subfileRecords.add(emptyRecord);
        }
        
        return subfileRecords;
    }

    // Subroutine SR102 - Edit/Change claim failure
    public void sr102(String g71000, String g71050, String sub060) {
        sr102f = "Y";
        
        Optional<HSG73Entity> failureOpt = hsg73Repository.findByKey(g71000, g71050, sub060);
        
        if (failureOpt.isPresent()) {
            HSG73Entity failure = failureOpt.get();
            
            // If status 30 or 16, reset status and delete errors
            if (failure.statusCode == 30 || failure.statusCode == 16) {
                failure.statusCode = 0;
                hsg73Repository.save(failure);
                
                // Delete errors from SDE001
                // Implementation depends on SDE001 structure
            }
            
            // Check if all failures have status 0, reset claim status to 02
            List<HSG73Entity> allFailures = hsg73Repository.findByKeyG71(
                g71000, failure.rechNr, failure.rechDatum, failure.auftragsNr, failure.bereich);
            
            boolean allZero = true;
            for (HSG73Entity f : allFailures) {
                if (f.statusCode > 0) {
                    allZero = false;
                    break;
                }
            }
            
            if (allZero) {
                Optional<HSG71Entity> claimOpt = hsg71Repository.findByKey(
                    g71000, failure.rechNr, failure.rechDatum, failure.auftragsNr, failure.bereich);
                if (claimOpt.isPresent()) {
                    HSG71Entity claim = claimOpt.get();
                    if (claim.claimNrSde == null || claim.claimNrSde.trim().isEmpty()) {
                        claim.statusCodeSde = 2;
                        hsg71Repository.save(claim);
                    }
                }
            }
        }
        
        // Load claim data
        loadClaim();
        sr06();
        sr102f = "";
    }

    // Subroutine SR104 - Delete claim failure
    public void sr104(String g71000, String g71050, List<SubfileRecord> subfileRecords) {
        zl4 = 0;
        
        for (SubfileRecord record : subfileRecords) {
            mark();
            
            if (" 4".equals(record.mark1)) {
                zl4++;
                
                Optional<HSG73Entity> failureOpt = hsg73Repository.findByKey(g71000, g71050, record.sub060);
                if (failureOpt.isPresent()) {
                    HSG73Entity failure = failureOpt.get();
                    hsg73Repository.delete(failure);
                    
                    // Decrement failure count in HSG71
                    Optional<HSG71Entity> claimOpt = hsg71Repository.findByKey(
                        g71000, failure.rechNr, failure.rechDatum, failure.auftragsNr, failure.bereich);
                    if (claimOpt.isPresent()) {
                        HSG71Entity claim = claimOpt.get();
                        claim.anzFehler = claim.anzFehler - 1;
                        hsg71Repository.save(claim);
                    }
                    
                    // Delete positions
                    List<HSGPSEntity> positions = hsgpsRepository.findByClaimAndFailure(g71000, g71050, record.sub060);
                    for (HSGPSEntity pos : positions) {
                        if (pos.fehlerNr != null && !pos.fehlerNr.trim().isEmpty()) {
                            if (pos.manuell != null && !pos.manuell.trim().isEmpty()) {
                                hsgpsRepository.delete(pos);
                            } else {
                                // Aggregate quantities and values
                                pos.fehlerNr = "";
                                pos.folgeNr = "";
                                pos.aufteilung = 0;
                                hsgpsRepository.save(pos);
                            }
                        }
                    }
                }
            }
        }
    }

    // Subroutine SR105 - Display claim
    public void sr105(String g71000, String g71050, String sub060) {
        Optional<HSG73Entity> failureOpt = hsg73Repository.findByKey(g71000, g71050, sub060);
        if (failureOpt.isPresent()) {
            loadClaim();
            // Display claim details
        }
    }

    // Subroutine SR106 - Display positions
    public void sr106(String g71000, String g71010, String g71020, String g71030, String g71040, 
                      String g71050, String sub060, String substa) {
        mark();
        srrot(null);
        
        art = "5";
        if ("00".equals(substa) || "16".equals(substa)) {
            art = "2";
        }
        
        // Call HS1215 program
        callHS1215(art, g71000, g71010, g71020, g71030, g71040, g71050, sub060, aktion);
    }

    // Subroutine SR107 - Assign positions
    public void sr107(String g71000, String g71010, String g71020, String g71030, String g71040,
                      String g71050, String sub060) {
        mark();
        srrot(null);
        
        auto = "";
        callHS1217(g71000, g71010, g71020, g71030, g71040, g71050, sub060, aktion, auto);
    }

    // Subroutine SR108 - Attachments
    public void sr108(String g71000, String g71050, String sub060, String sub065, 
                      String g7111a, String g71060, String substa) {
        callHS1228(g71000, g71050, sub060, sub065, g7111a, g71060, substa);
    }

    // Subroutine SR109 - Print claim
    public void sr109(String g71000, String g71050, String sub060, String sub065) {
        printClaim(g71000, g71050, sub060, sub065, "");
    }

    // Subroutine SR110 - Send claim
    public void sr110(String g71000, String g71050, String sub060, String sub065) {
        sendClaim(g71000, g71050, sub060, sub065);
        
        if (!"ZZZ".equals(g71000)) {
            try {
                Optional<HSG73Entity> failureOpt = hsg73Repository.findByKey(g71000, g71050, sub060);
                if (failureOpt.isPresent()) {
                    HSG73Entity failure = failureOpt.get();
                    if (failure.claimArt == 9) {
                        // Create email notification
                    }
                }
            } catch (Exception e) {
                // Handle error
            }
        }
    }

    // Subroutine SR112 - Edit claim in SCAS
    public void sr112(String g71160, String sub060, BigDecimal key0011) {
        s3fCno = new BigDecimal(g71160);
        s3fFno = new BigDecimal(sub060);
        s3fDno = key0011;
        
        Optional<S3F004Entity> scasClaimOpt = s3f004Repository.findByKey(s3fCno, s3fFno, s3fDno);
        if (scasClaimOpt.isPresent()) {
            // Call HS1212C2 program
            callHS1212C2(g71160, sub060, key0011.toString());
        }
    }

    // Subroutine SR115 - Display claim in SCAS
    public void sr115(String g71000, String g71050, String sub060, String sub065) {
        Optional<HSG73Entity> failureOpt = hsg73Repository.findByKey(g71000, g71050, sub060);
        if (failureOpt.isPresent()) {
            HSG73Entity failure = failureOpt.get();
            
            if (isWarrScope(failure.steuerCode)) {
                // Display warranty scope claim details
                String restxt = getCwpMsg("", failure.result1, failure.result2);
                String flttxt = getCwpMsg("", failure.fault1, failure.fault2);
                String rpytxt = getCwpMsg("", failure.reply1, failure.reply2);
                String exptxt = getCwpMsg("", failure.explanation1, failure.explanation2);
                
                // Get claim values
                BigDecimal claimReq = getClaimValues("Requested", g71000, g71050, sub060, sub065);
                BigDecimal claimCmp = getClaimValues("Compensated", g71000, g71050, sub060, sub065);
                
                // Display claim window
            } else {
                // Display SCAS claim
                if (failure.fehlerNrSde != null && !failure.fehlerNrSde.trim().isEmpty()) {
                    callHS1212C1(failure.claimNrSde, failure.fehlerNrSde);
                } else {
                    callHS1212C1(failure.claimNrSde, sub060);
                }
            }
        }
    }

    // Subroutine SR117 - Display credit note
    public void sr117(String subgut, String sub140) {
        if (subgut != null && !subgut.trim().isEmpty()) {
            String scope = getScope(sub140);
            String gutart;
            
            if (scope.startsWith("A")) {
                gutart = "A";
                callHs1212C3(gutart, subgut);
            } else if (scope.startsWith("R")) {
                gutart = "R";
                callHs1212C3(gutart, subgut);
            } else {
                gutart = "D";
                callHs1295(gutart, subgut, "");
            }
        }
    }

    // Subroutine SR120 - Follow-up claim
    public void sr120(String g71000, String g71050, String sub060, String sub140, LocalDate aktdat, int tage) {
        LocalDate vgldat2 = aktdat.minusDays(tage);
        
        if (isWarrScope(sub140)) {
            // Check warranty scope follow-up
            sr120a(vgldat2);
        } else {
            // Check SCAS follow-up
            BigDecimal clano8 = new BigDecimal(g71050);
            
            if (clano8.compareTo(new BigDecimal("999999")) < 0) {
                // Legacy claim number format
            } else {
                Optional<S3F004Entity> scasClaimOpt = s3f004Repository.findByKey(clano8, new BigDecimal(sub060), null);
                if (scasClaimOpt.isPresent()) {
                    S3F004Entity scasClaim = scasClaimOpt.get();
                    if (scasClaim.crRptDate != null && scasClaim.crRptDate != 0) {
                        LocalDate vgldat1 = parseDate(scasClaim.crRptDate.toString());
                        sr120a(vgldat1);
                    }
                }
            }
        }
    }

    // Subroutine SR120A - Create follow-up claim
    public void sr120a(LocalDate vgldat1) {
        // Implementation for creating follow-up claim
    }

    // Subroutine SR04 - User guidance
    public void sr04(String flag04, String flagx4) {
        // Display help/guidance based on field
    }

    // Subroutine SR06 - Create new claim failure
    public void sr06(String g71000, String g71010, String g71020, String g71030, String g71040, String g71050) {
        if (sr102f == null || sr102f.trim().isEmpty()) {
            // Initialize fields
            initializeClaimFields();
        }
        
        // Determine next failure number
        Integer fnr = 0;
        List<HSG73Entity> failures = hsg73Repository.findByKeyG71(g71000, g71010, g71020, g71030, g71040);
        
        for (HSG73Entity failure : failures) {
            Integer currentFnr = Integer.parseInt(failure.fehlerNr);
            if (currentFnr > fnr) {
                fnr = currentFnr;
            }
        }
        fnr++;
        
        if (fnr > 9) {
            // Create new claim if more than 9 failures
            srneu(g71000, g71010, g71020, g71030, g71040, g71050);
            return;
        }
        
        // Check for existing campaign
        srKampagnen(g71000, g71010, g71020, g71030, bereich, g71040, "", g71050, String.format("%02d", fnr), "00");
        
        if (returnValue != null && !returnValue.trim().isEmpty()) {
            return;
        }
        
        // Create new failure record
        HSG73Entity newFailure = new HSG73Entity();
        newFailure.pakz = g71000;
        newFailure.rechNr = g71010;
        newFailure.rechDatum = g71020;
        newFailure.auftragsNr = g71030;
        newFailure.bereich = g71040;
        newFailure.claimNr = g71050;
        newFailure.fehlerNr = String.format("%02d", fnr);
        newFailure.statusCode = 0;
        
        hsg73Repository.save(newFailure);
        
        // Automatically assign positions
        auto = "J";
        callHS1217(g71000, g71010, g71020, g71030, g71040, g71050, newFailure.fehlerNr, aktion, auto);
        
        // Update GPS with new percentages
        srUpdgps(g71000, g71050);
        
        // Save text
        saveText();
    }

    // Subroutine SR08 - Display warranty info
    public void sr08(String g71060) {
        fgnr17 = g71060;
        callHS0069C(fgnr17);
    }

    // Subroutine SR16 - New claim
    public void sr16() {
        // Create new claim
        srneu(null, null, null, null, null, null);
    }

    // Subroutine SR18 - Edit header data
    public void sr18(String g71000, String g71050) {
        Optional<HSG71Entity> claimOpt = hsg71Repository.findByKey(g71000, null, null, null, null);
        if (claimOpt.isPresent()) {
            HSG71Entity claim = claimOpt.get();
            
            // Edit header fields
            srchk1(claim);
            
            // Save changes
            hsg71Repository.save(claim);
        }
    }

    // Subroutine SRCHK1 - Validate header data
    public void srchk1(HSG71Entity claim) {
        boolean error = false;
        
        // Validate product type
        if (claim.produktTyp < 1 || claim.produktTyp > 5) {
            error = true;
        }
        
        // Validate chassis number
        if (claim.chassisNr != null && !claim.chassisNr.trim().isEmpty()) {
            // Check in master file
        }
        
        // Validate repair date
        if (claim.repDatum == null || claim.repDatum == 0) {
            error = true;
        }
        
        // Validate mileage
        if (claim.kmStand == null || claim.kmStand == 0) {
            if (claim.chassisNr != null && !claim.chassisNr.trim().isEmpty()) {
                error = true;
            }
        }
    }

    // Subroutine SRDAT - Format date
    public String srdat(String datitt, String datimm, String datijj) {
        punkt1 = ".";
        punkt2 = ".";
        
        if ("0000".equals(datijj)) {
            return "";
        }
        
        return datitt + punkt1 + datimm + punkt2 + datijj;
    }

    // Subroutine SRDATN - Format numeric date
    public void srdatn(String datnij, String datnim, String datnit) {
        // Convert numeric date format
    }

    // Subroutine SRTNR - Format part number
    public String srtnr(String g7307a) {
        if (g7307a == null || g7307a.length() < 8) {
            return g7307a;
        }
        
        // Right-align part number
        String[] tnr = new String[7];
        String[] tnra = new String[7];
        
        for (int i = 0; i < 7; i++) {
            tnr[i] = g7307a.substring(i, i + 1);
            tnra[i] = "0";
        }
        
        y = 7;
        for (int i = 0; i < 7; i++) {
            if (!" ".equals(tnr[i])) {
                tnra[y.intValue() - 1] = tnr[i];
                y--;
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            result.append(tnra[i]);
        }
        
        return result.toString();
    }

    // Subroutine SRERR1 - Error validation
    public void srerr1(List<SubfileRecord> subfileRecords) {
        err = 0;
        
        for (SubfileRecord record : subfileRecords) {
            mark();
            
            // Validate selection
            if (record.mark1 != null && !record.mark1.trim().isEmpty()) {
                if (!"  ".equals(record.mark1) && !" 2".equals(record.mark1) && !" 4".equals(record.mark1)
                    && !" 5".equals(record.mark1) && !" 6".equals(record.mark1) && !" 7".equals(record.mark1)
                    && !" 8".equals(record.mark1) && !" 9".equals(record.mark1) && !"10".equals(record.mark1)
                    && !"12".equals(record.mark1) && !"15".equals(record.mark1) && !"17".equals(record.mark1)
                    && !"20".equals(record.mark1)) {
                    err++;
                }
            }
            
            // Validate follow-up claim
            if ("20".equals(record.mark1) && Integer.parseInt(record.substa) < 4) {
                err++;
            }
            
            // Validate SCAS edit
            if ("12".equals(record.mark1) && !"01".equals(record.substa)) {
                err++;
            }
            
            // Validate edit/delete
            if ((Integer.parseInt(record.substa) > 0 && Integer.parseInt(record.substa) < 30 
                 && Integer.parseInt(record.substa) != 16)
                && (" 2".equals(record.mark1) || " 4".equals(record.mark1))) {
                err++;
            }
        }
    }

    // Subroutine SRWERT - Calculate claim value
    public void srwert(HSG73Entity failure) {
        BigDecimal subwert = BigDecimal.ZERO;
        
        List<HSGPSEntity> positions = hsgpsRepository.findByClaimAndFailure(
            failure.pakz, failure.claimNr, failure.fehlerNr);
        
        for (HSGPSEntity pos : positions) {
            subwert = subwert.add(pos.wert);
        }
        
        // Store calculated value
    }

    // Subroutine SRROT - Mark rejected claims in red
    public void srrot(HSG73Entity failure) {
        if (failure != null && (failure.statusCode == 30 || failure.statusCode == 16)) {
            // Set indicator 83 on
        }
    }

    // Subroutine SRGUT - Get credit note number
    public void srgut(HSG73Entity failure) {
        // Implementation to retrieve credit note number
    }

    // Subroutine SR20 - Copy claim
    public void sr20(String g71000, String g71050) {
        // Implementation for copying claim
    }

    // Subroutine SRNEU - Create new claim
    public void srneu(String g71000, String g71010, String g71020, String g71030, String g71040, String g71050) {
        // Determine next claim number
        String cn = g71050;
        fn = "0";
        
        List<HSG71Entity> claims = hsg71Repository.findByClaimNumber(cn);
        
        for (HSG71Entity claim : claims) {
            if (claim.wete.compareTo(fn) > 0) {
                fn = claim.wete;
            }
        }
        
        fnn = new BigDecimal(fn).add(BigDecimal.ONE);
        fn = fnn.toString();
        
        // Check if number is available
        Optional<HSG71Entity> existingClaim = hsg71Repository.findByKey(g71000, null, null, null, fn);
        if (existingClaim.isPresent()) {
            return;
        }
        
        // Get highest claim number
        Optional<HSG71Entity> lastClaim = hsg71Repository.findLastClaim();
        if (lastClaim.isPresent()) {
            cnr = new BigDecimal(lastClaim.get().claimNr).add(BigDecimal.ONE);
        } else {
            cnr = BigDecimal.ONE;
        }
        
        // Create new claim record
        HSG71Entity newClaim = new HSG71Entity();
        newClaim.pakz = g71000;
        newClaim.wete = fn;
        newClaim.claimNr = cnr.toString();
        newClaim.claimNrSde = "";
        newClaim.statusCodeSde = 0;
        newClaim.anzFehler = 0;
        
        hsg71Repository.save(newClaim);
        
        // Copy positions
        srZeile(g71000, cnr.toString());
    }

    // Subroutine SR_ZEILE - Get next line number
    public Integer srZeile(String g71000, String g71050) {
        Integer zeile = 0;
        
        List<HSGPSEntity> positions = hsgpsRepository.findByClaim(g71000, g71050);
        
        for (HSGPSEntity pos : positions) {
            if (pos.zeile > zeile) {
                zeile = pos.zeile;
            }
        }
        
        return zeile;
    }

    // Subroutine SR_MODEL - Create model claim
    public void srModel(String g71000, String g71050, String g7306a) {
        // Implementation for model claim creation
    }

    // Subroutine SR_UPDGPS - Update GPS percentages
    public void srUpdgps(String g71000, String g71050) {
        List<HSGPSEntity> positions = hsgpsRepository.findByClaim(g71000, g71050);
        
        for (HSGPSEntity pos : positions) {
            if (pos.fehlerNr != null && !pos.fehlerNr.trim().isEmpty()) {
                // Update percentages based on position type
                hsgpsRepository.save(pos);
            }
        }
    }

    // Subroutine SR_KAMPAGNEN - Create campaign positions
    public void srKampagnen(String g71000, String g71010, String g71020, String g71030, 
                            String bereich, String g71040, String g71060, String g71050, 
                            String g7306a, String g73065) {
        returnValue = "";
        callHS1213(g71000, g71010, g71020, g71030, bereich, g71040, g71060, g71050, g7306a, g73065, returnValue);
    }

    // Subroutine SR_TEXT - Edit claim text
    public void srText() {
        // Implementation for text editing
    }

    // Subroutine SR_PRUEFP - Check if positions exist
    public boolean srPruefp(String g71000, String g71050) {
        List<HSGPSEntity> positions = hsgpsRepository.findByClaim(g71000, g71050);
        
        int anzpos = 0;
        for (HSGPSEntity pos : positions) {
            if (pos.fehlerNr == null || pos.fehlerNr.trim().isEmpty()) {
                if (!"TXT".equals(pos.satzart)) {
                    anzpos++;
                }
            }
        }
        
        return anzpos > 0;
    }

    // Helper methods
    private void loadClaim() {
        // Load claim data
    }

    private void initializeClaimFields() {
        // Initialize all claim fields to blank/zero
    }

    private void saveText() {
        // Save text positions
    }

    private boolean isWarrScope(String steuerCode) {
        // Check if warranty scope
        return false;
    }

    private String getCwpMsg(String locale, String type, String id) {
        // Get CWP message
        // Get
        return "";
    }

    private BigDecimal getClaimValues(String type, String g71000, String g71050, String sub060, String sub065) {
        // Get claim values
        return BigDecimal.ZERO;
    }

    private String getScope(String steuerCode) {
        // Get scope from steering code
        return "";
    }

    private LocalDate parseDate(String dateStr) {
        // Parse date string
        return LocalDate.now();
    }

    private void callHS1215(String art, String g71000, String g71010, String g71020, 
                           String g71030, String g71040, String g71050, String sub060, String aktion) {
        // Call HS1215 program
    }

    private void callHS1217(String g71000, String g71010, String g71020, String g71030, 
                           String g71040, String g71050, String sub060, String aktion, String auto) {
        // Call HS1217 program
    }

    private void callHS1228(String g71000, String g71050, String sub060, String sub065,
                           String g7111a, String g71060, String substa) {
        // Call HS1228 program
    }

    private void printClaim(String g71000, String g71050, String sub060, String sub065, String type) {
        // Print claim
    }

    private void sendClaim(String g71000, String g71050, String sub060, String sub065) {
        // Send claim
    }

    private void callHS1212C1(String claimNr, String fehlerNr) {
        // Call HS1212C1 program
    }

    private void callHS1212C2(String claimNr, String fehlerNr, String dealerNo) {
        // Call HS1212C2 program
    }

    private void callHs1212C3(String gutart, String subgut) {
        // Call HS1212C3 program
    }

    private void callHs1295(String gutart, String subgut, String reprint) {
        // Call HS1295 program
    }

    private void callHS0069C(String fgnr17) {
        // Call HS0069C program
    }

    private void callHS1213(String g71000, String g71010, String g71020, String g71030,
                           String bereich, String g71040, String g71060, String g71050,
                           String g7306a, String g73065, String returnValue) {
        // Call HS1213 program
    }

    // Inner class for subfile records
    public static class SubfileRecord {
        public String mark1;
        public String sub060;
        public String sub065;
        public String sub070;
        public String sub080;
        public String sub100;
        public String sub110;
        public String sub140;
        public Integer sub180;
        public Integer sub190;
        public Integer sub200;
        public Integer sub240;
        public String sub280;
        public String substa;
    }
}

// JPA Entity for AUFWSKF
@Entity
@Table(name = "AUFWSKF")
class AUFWSKEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "AUFNR", length = 5)
    private String aufnr;
    
    @Column(name = "FAB", length = 1)
    private String fab;
    
    @Column(name = "BER", length = 1)
    private String ber;
    
    @Column(name = "SPL", length = 2)
    private String spl;
    
    @Column(name = "AUFDAT", length = 8)
    private String aufdat;
    
    @Column(name = "POS.", precision = 3, scale = 0)
    private Integer pos;
    
    @Column(name = "LFD.", precision = 3, scale = 0)
    private Integer lfd;
    
    @Column(name = "TYPE", length = 2)
    private String type;
    
    @Column(name = "ID", length = 5)
    private String id;
    
    @Column(name = "TEXT")
    private String text;
}

// JPA Entity for FARSTLF4
@Entity
@Table(name = "FARSTLF4")
class FARSTLEntity {
    @Id
    @Column(name = "FAHRG.-NR.", length = 17)
    private String fahrgNr;
    
    @Column(name = "PAKZ", length = 3)
    private String pakz;
    
    @Column(name = "AMTL.KZ", length = 12)
    private String amtlKz;
    
    @Column(name = "TYP", length = 30)
    private String typ;
    
    @Column(name = "BAUJ", length = 4)
    private String bauj;
    
    @Column(name = "ZUL-DAT", length = 8)
    private String zulDat;
    
    @Column(name = "AU", length = 6)
    private String au;
    
    @Column(name = "GA", length = 8)
    private String ga;
    
    @Column(name = "SP", length = 6)
    private String sp;
    
    @Column(name = "KM-STAND", length = 8)
    private String kmStand;
    
    @Column(name = "V-NR SVS", length = 4)
    private String vNrSvs;
    
    @Column(name = "RESERVE", length = 2)
    private String reserve;
    
    @Column(name = "MOTOR-NR", length = 10)
    private String motorNr;
    
    @Column(name = "MOTOR-TYP", length = 20)
    private String motorTyp;
    
    @Column(name = "BETR.WS", length = 3)
    private String betrWs;
    
    @Column(name = "HU", length = 6)
    private String hu;
    
    @Column(name = "FG-NR-MULTI", length = 7)
    private String fgNrMulti;
    
    @Column(name = "FG-NR-SVAS", precision = 9, scale = 0)
    private Integer fgNrSvas;
    
    @Column(name = "SCHLIESSUNGS-NR.", length = 10)
    private String schliessungsNr;
    
    @Column(name = "TACHO-PR.", length = 6)
    private String tachoPr;
    
    @Column(name = "FARBE", length = 20)
    private String farbe;
    
    @Column(name = "MOBIL-NR.", length = 20)
    private String mobilNr;
    
    @Column(name = "FAX-NR.", length = 20)
    private String faxNr;
    
    @Column(name = "IND-NR.", length = 40)
    private String indNr;
    
    @Column(name = "KBA-NR.1", length = 10)
    private String kbaNr1;
    
    @Column(name = "KBA-NR.2", length = 10)
    private String kbaNr2;
    
    @Column(name = "KBA-NR.3", length = 10)
    private String kbaNr3;
    
    @Column(name = "HERSTELLER ALT", length = 10)
    private String herstellerAlt;
    
    @Column(name = "HERSTELLER NEU", length = 20)
    private String herstellerNeu;
    
    @Column(name = "FAHRZEUG-ART ALT", length = 1)
    private String fahrzeugArtAlt;
    
    @Column(name = "FAHRZEUG-ART NEU", length = 20)
    private String fahrzeugArtNeu;
    
    @Column(name = "P.O.-SVAS", precision = 9, scale = 0)
    private Integer poSvas;
    
    @Column(name = "SA-USR", length = 10)
    private String saUsr;
    
    @Column(name = "SA-KZ/NAME", length = 10)
    private String saKzName;
    
    @Column(name = "SA-DAT", length = 8)
    private String saDat;
    
    @Column(name = "SA-TIM", length = 6)
    private String saTim;
    
    @Column(name = "#N-USR", length = 10)
    private String nUsr;
    
    @Column(name = "#N-KZ/NAME", length = 10)
    private String nKzName;
    
    @Column(name = "#N-DAT", length = 8)
    private String nDat;
    
    @Column(name = "#N-TIM", length = 6)
    private String nTim;
    
    @Column(name = "GEST-DAT", length = 8)
    private String gestDat;
    
    @Column(name = "GEM-KZ", length = 10)
    private String gemKz;
    
    @Column(name = "GEM-DAT", length = 8)
    private String gemDat;
    
    @Column(name = "GEM-TIM", length = 6)
    private String gemTim;
    
    @Column(name = "�57-�BERPR.", length = 6)
    private String ueberpr57;
    
    @Column(name = "TACHOPR. JJJJMMTT", length = 8)
    private String tachoprJjjjmmtt;
    
    @Column(name = "LARMARM", length = 8)
    private String larmarm;
    
    @Column(name = "LBW/KRAN", length = 6)
    private String lbwKran;
    
    @Column(name = "AUFBAUART", length = 20)
    private String aufbauart;
    
    @Column(name = "HERSTELLER AUFBAU", length = 20)
    private String herstellerAufbau;
    
    @Column(name = "ZUSATZAUSR�STUNG 1", length = 20)
    private String zusatzausruestung1;
    
    @Column(name = "HERSTELLER ZUSATZ 1", length = 20)
    private String herstellerZusatz1;
    
    @Column(name = "AGGREGAT-NR ZUSATZ 1", length = 12)
    private String aggregatNrZusatz1;
    
    @Column(name = "ZUSATZAUSR�STUNG 2", length = 20)
    private String zusatzausruestung2;
    
    @Column(name = "HERSTELLER ZUSATZ 2", length = 20)
    private String herstellerZusatz2;
    
    @Column(name = "AGGREGAT-NR ZUSATZ 2", length = 12)
    private String aggregatNrZusatz2;
    
    @Column(name = "ZUSATZAUSR�STUNG 3", length = 20)
    private String zusatzausruestung3;
    
    @Column(name = "HERSTELLER ZUSATZ 3", length = 20)
    private String herstellerZusatz3;
    
    @Column(name = "AGGREGAT-NR ZUSATZ 3", length = 12)
    private String aggregatNrZusatz3;
    
    @Column(name = "EINSATZART", length = 20)
    private String einsatzart;
    
    @Column(name = "EURO-NORM", length = 10)
    private String euroNorm;
    
    @Column(name = "PARTIKELFILTER", length = 1)
    private String partikelfilter;
    
    @Column(name = "IS-ART", length = 5)
    private String isArt;
}

// JPA Entity for HSG71PF
@Entity
@Table(name = "HSG71PF")
class HSG71Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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
    private Integer zulDatum;
    
    @Column(name = "REP.-DATUM", precision = 8, scale = 0)
    private Integer repDatum;
    
    @Column(name = "KM-STAND", precision = 3, scale = 0)
    private Integer kmStand;
    
    @Column(name = "PRODUKT-TYP", precision = 1, scale = 0)
    private Integer produktTyp;
    
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
    private Integer statusCodeSde;
    
    @Column(name = "ANZ. FEHLER", precision = 2, scale = 0)
    private Integer anzFehler;
    
    @Column(name = "BEREICH", length = 1)
    private String bereich;
    
    @Column(name = "AUF.NR.", length = 10)
    private String aufNr;
}

// JPA Entity for HSG73LF1
@Entity
@Table(name = "HSG73LF1")
class HSG73Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "PAKZ", length = 3)
    private String pakz;
    
    @Column(name = "RECH.-NR.", length = 5)
    private String rechNr;
    
    @Column(name = "RECH.-DATUM", length = 8)
    private String rechDatum;
    
    @Column(name = "AUFTRAGS-NR.", length = 5)
    private String auftragsNr;
    
    @Column(name = "BEREICH", length = 1)
    private String bereich;
    
    @Column(name = "CLAIM-NR.", length = 8)
    private String claimNr;
    
    @Column(name = "FEHLER-NR.", length = 2)
    private String fehlerNr;
    
    @Column(name = "FOLGE-NR.", length = 2)
    private String folgeNr;
    
    @Column(name = "FEHLER-TEIL", length = 18)
    private String fehlerTeil;
    
    @Column(name = "HAUPTGRUPPE", length = 2)
    private String hauptgruppe;
    
    @Column(name = "NEBENGRUPPE", length = 2)
    private String nebengruppe;
    
    @Column(name = "SCHAD.-C1", length = 2)
    private String schadC1;
    
    @Column(name = "SCHAD.-C2", length = 2)
    private String schadC2;
    
    @Column(name = "TEXT1", length = 65)
    private String text1;
    
    @Column(name = "TEXT2", length = 65)
    private String text2;
    
    @Column(name = "STEUER CODE", length = 2)
    private String steuerCode;
    
    @Column(name = "BEW. CODE1", length = 2)
    private String bewCode1;
    
    @Column(name = "BEW. CODE2", precision = 4, scale = 0)
    private Integer bewCode2;
    
    @Column(name = "BEW. DATUM", precision = 8, scale = 0)
    private Integer bewDatum;
    
    @Column(name = "VERG. MAT.", precision = 3, scale = 0)
    private Integer vergMat;
    
    @Column(name = "VERG. ARB.", precision = 3, scale = 0)
    private Integer vergArb;
    
    @Column(name = "VERG. SPEZ.", precision = 3, scale = 0)
    private Integer vergSpez;
    
    @Column(name = "BEANTR. MAT.", precision = 11, scale = 2)
    private BigDecimal beantrMat;
    
    @Column(name = "BEANTRG. ARB.", precision = 11, scale = 2)
    private BigDecimal beantrgArb;
    
    @Column(name = "BEANTRG. SPEZ.", precision = 11, scale = 2)
    private BigDecimal beantrgSpez;
    
    @Column(name = "CLAIM-ART", precision = 1, scale = 0)
    private Integer claimArt;
    
    @Column(name = "V.-REP.-DATUM", precision = 8, scale = 0)
    private Integer vRepDatum;
    
    @Column(name = "V.-KM-STAND", precision = 3, scale = 0)
    private Integer vKmStand;
    
    @Column(name = "FELDTEST-NR.", precision = 6, scale = 0)
    private Integer feldtestNr;
    
    @Column(name = "KAMPAGNEN-NR.", length = 8)
    private String kampagnenNr;
    
    @Column(name = "EPS", length = 20)
    private String eps;
    
    @Column(name = "STATUS CODE", precision = 2, scale = 0)
    private Integer statusCode;
    
    @Column(name = "VARIANT CODE", precision = 2, scale = 0)
    private Integer variantCode;
    
    @Column(name = "ACTION CODE", precision = 2, scale = 0)
    private Integer actionCode;
    
    @Column(name = "TEXT3", length = 65)
    private String text3;
    
    @Column(name = "TEXT4", length = 65)
    private String text4;
    
    @Column(name = "FEHLER-NR. SDE", length = 2)
    private String fehlerNrSde;
    
    @Column(name = "ANHANG", length = 1)
    private String anhang;
    
    @Column(name = "SOURCE", length = 5)
    private String source;
    
    @Column(name = "COMPLAIN", length = 5)
    private String complain;
    
    @Column(name = "SYMPTOM", length = 5)
    private String symptom;
    
    @Column(name = "FAILURE", length = 5)
    private String failure;
    
    @Column(name = "LOCATION", length = 5)
    private String location;
    
    @Column(name = "REPAIR", length = 5)
    private String repair;
    
    @Column(name = "ERG.CODE", length = 2)
    private String ergCode;
    
    @Column(name = "RESULT1", length = 2)
    private String result1;
    
    @Column(name = "RESULT2", length = 5)
    private String result2;
    
    @Column(name = "FAULT1", length = 2)
    private String fault1;
    
    @Column(name = "FAULT2", length = 5)
    private String fault2;
    
    @Column(name = "REPLY1", length = 2)
    private String reply1;
    
    @Column(name = "REPLY2", length = 5)
    private String reply2;
    
    @Column(name = "EXPLANATION1", length = 2)
    private String explanation1;
    
    @Column(name = "EXPLANATION2", length = 5)
    private String explanation2;
}

// JPA Entity for HSGPSPF
@Entity
@Table(name = "HSGPSPF")
class HSGPSEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "KUERZEL", length = 3)
    private String kuerzel;
    
    @Column(name = "CLAIM-NR.", length = 8)
    private String claimNr;
    
    @Column(name = "FEHLER-NR.", length = 2)
    private String fehlerNr;
    
    @Column(name = "FOLGE-NR.", length = 2)
    private String folgeNr;
    
    @Column(name = "ZEILE", precision = 3, scale = 0)
    private Integer zeile;
    
    @Column(name = "AUFTEILUNG", precision = 3, scale = 0)
    private Integer aufteilung;
    
    @Column(name = "SATZART", length = 3)
    private String satzart;
    
    @Column(name = "NR.", length = 18)
    private String nr;
    
    @Column(name = "MENGE", precision = 5, scale = 0)
    private Integer menge;
    
    @Column(name = "WERT", precision = 11, scale = 2)
    private BigDecimal wert;
    
    @Column(name = "STEUER-CODE", length = 40)
    private String steuerCode;
    
    @Column(name = "HAUPTGRUPPE", length = 2)
    private String hauptgruppe;
    
    @Column(name = "ZEIT", precision = 3, scale = 1)
    private BigDecimal zeit;
    
    @Column(name = "GRUND", precision = 4, scale = 0)
    private Integer grund;
    
    @Column(name = "VERG�TUNG", precision = 3, scale = 0)
    private Integer verguetung;
    
    @Column(name = "MANUELL", length = 1)
    private String manuell;
    
    @Column(name = "KAMPAGNE", precision = 8, scale = 0)
    private Integer kampagne;
    
    @Column(name = "POS.-NR.", precision = 3, scale = 0)
    private Integer posNr;
    
    @Column(name = "RESULTCODE", length = 2)
    private String resultcode;
    
    @Column(name = "CODE TYPE", length = 2)
    private String codeType;
    
    @Column(name = "CODE ID", length = 5)
    private String codeId;
    
    @Column(name = "COMPFAC.", precision = 3, scale = 0)
    private Integer compfac;
    
    @Column(name = "GROSSPRICE", precision = 13, scale = 2)
    private BigDecimal grossprice;
    
    @Column(name = "DISCOUNT", precision = 5, scale = 2)
    private BigDecimal discount;
    
    @Column(name = "COMPAMOUNT", precision = 15, scale = 2)
    private BigDecimal compamount;
    
    @Column(name = "COMPQTY", precision = 7, scale = 0)
    private Integer compqty;
    
    @Column(name = "TYPE", length = 5)
    private String type;
    
    @Column(name = "MIA STATUS", length = 1)
    private String miaStatus;
    
    @Column(name = "CATEGORYS", length = 2)
    private String categorys;
    
    @Column(name = "TEXT", length = 2000)
    private String text;
}

// JPA Entity for S3F004
@Entity
@Table(name = "S3F004")
class S3F004Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "G/A Claim No", precision = 5, scale = 3)
    private BigDecimal gaClaimNo;
    
    @Column(name = "Page No", precision = 5, scale = 3)
    private BigDecimal pageNo;
    
    @Column(name = "Dist Wrnty Cust No", precision = 5, scale = 3)
    private BigDecimal distWrntyCustNo;
    
    @Column(name = "Chassis Number", precision = 5, scale = 3)
    private BigDecimal chassisNumber;
    
    @Column(name = "Engine Number", length = 7)
    private String engineNumber;
    
    @Column(name = "Repair Date", precision = 8, scale = 0)
    private Integer repairDate;
    
    @Column(name = "Mileage 000 Km", precision = 3, scale = 0)
    private Integer mileage000Km;
    
    @Column(name = "G/A No", precision = 5, scale = 0)
    private Integer gaNo;
    
    @Column(name = "G/A Claim Fail No", precision = 6, scale = 0)
    private Integer gaClaimFailNo;
    
    @Column(name = "Cab Type", length = 1)
    private String cabType;
    
    @Column(name = "Eng Type", length = 1)
    private String engType;
    
    @Column(name = "Chas Type", length = 1)
    private String chasType;
    
    @Column(name = "Adapt Type", length = 1)
    private String adaptType;
    
    @Column(name = "Wheel Plan", length = 3)
    private String wheelPlan;
    
    @Column(name = "Equip Type", length = 4)
    private String equipType;
    
    @Column(name = "Engine Type", length = 10)
    private String engineType;
    
    @Column(name = "Delivery Date", precision = 8, scale = 0)
    private Integer deliveryDate;
    
    @Column(name = "Job Ord No", length = 7)
    private String jobOrdNo;
    
    @Column(name = "Dist Claim No", precision = 6, scale = 0)
    private Integer distClaimNo;
    
    @Column(name = "Dist Claim Fail No", length = 5)
    private String distClaimFailNo;
    
    @Column(name = "Prod Type", length = 5)
    private String prodType;
    
    @Column(name = "Type Of Claim", length = 5)
    private String typeOfClaim;
    
    @Column(name = "Previous Repair Date", precision = 8, scale = 0)
    private Integer previousRepairDate;
    
    @Column(name = "Prev Mileage 000 Km", precision = 3, scale = 0)
    private Integer prevMileage000Km;
    
    @Column(name = "Encl", length = 1)
    private String encl;
    
    @Column(name = "Recon", length = 1)
    private String recon;
    
    @Column(name = "Foreign Veh", length = 1)
    private String foreignVeh;
    
    @Column(name = "Dist Name", length = 30)
    private String distName;
    
    @Column(name = "Dist Abbrev", length = 6)
    private String distAbbrev;
    
    @Column(name = "Owner", length = 25)
    private String owner;
    
    @Column(name = "Owner Address", length = 25)
    private String ownerAddress1;
    
    @Column(name = "Owner Address", length = 20)
    private String ownerAddress2;
    
    @Column(name = "Gross Veh Weight", precision = 5, scale = 0)
    private Integer grossVehWeight;
    
    @Column(name = "Type Of Operation", length = 20)
    private String typeOfOperation;
    
    @Column(name = "Veh Code", precision = 3, scale = 0)
    private Integer vehCode;
    
    @Column(name = "Field Test No.", precision = 6, scale = 0)
    private Integer fieldTestNo;
    
    @Column(name = "Campaign Number", precision = 6, scale = 0)
    private Integer campaignNumber;
    
    @Column(name = "Total Hours", precision = 3, scale = 1)
    private BigDecimal totalHours;
    
    @Column(name = "Dist Calc Labour", precision = 11, scale = 2)
    private BigDecimal distCalcLabour;
    
    @Column(name = "Dist Calc Matrl", precision = 11, scale = 2)
    private BigDecimal distCalcMatrl;
    
    @Column(name = "Dist Calc Spec.", precision = 11, scale = 2)
    private BigDecimal distCalcSpec;
    
    @Column(name = "Dist Calc Total", precision = 11, scale = 2)
    private BigDecimal distCalcTotal;
    
    @Column(name = "Dist Auth Labour", precision = 11, scale = 2)
    private BigDecimal distAuthLabour;
    
    @Column(name = "Dist Auth Matrl", precision = 11, scale = 2)
    private BigDecimal distAuthMatrl;
    
    @Column(name = "Dist Auth Spec.", precision = 11, scale = 2)
    private BigDecimal distAuthSpec;
    
    @Column(name = "Dist Auth Total", precision = 11, scale = 2)
    private BigDecimal distAuthTotal;
    
    @Column(name = "SSS Calc Labour", precision = 11, scale = 2)
    private BigDecimal sssCalcLabour;
    
    @Column(name = "SSS Calc Matl", precision = 11, scale = 2)
    private BigDecimal sssCalcMatl;
    
    @Column(name = "SSS Calc Spec", precision = 11, scale = 2)
    private BigDecimal sssCalcSpec;
    
    @Column(name = "SSS Calc Total", precision = 11, scale = 2)
    private BigDecimal sssCalcTotal;
    
    @Column(name = "SSS Auth Labour", precision = 11, scale = 2)
    private BigDecimal sssAuthLabour;
    
    @Column(name = "SSS Auth Matrl", precision = 11, scale = 2)
    private BigDecimal sssAuthMatrl;
    
    @Column(name = "SSS Auth Spec", precision = 11, scale = 2)
    private BigDecimal sssAuthSpec;
    
    @Column(name = "SSS Auth Total", precision = 11, scale = 2)
    private BigDecimal sssAuthTotal;
    
    @Column(name = "Material recive Date", precision = 8, scale = 0)
    private Integer materialReciveDate;
    
    @Column(name = "Entered/Batch date", precision = 8, scale = 0)
    private Integer enteredBatchDate;
    
    @Column(name = "User Profile", length = 10)
    private String userProfile;
    
    @Column(name = "Workstn", length = 10)
    private String workstn;
    
    @Column(name = "Rej ALL Fl", length = 1)
    private String rejAllFl;
    
    @Column(name = "Return Pack List No", precision = 6, scale = 0)
    private Integer returnPackListNo;
    
    @Column(name = "Box No.", precision = 6, scale = 0)
    private Integer boxNo;
    
    @Column(name = "Goodwill Value", precision = 11, scale = 2)
    private BigDecimal goodwillValue;
    
    @Column(name = "Goodwill No.", precision = 6, scale = 0)
    private Integer goodwillNo;
    
    @Column(name = "Goodwill Remarks", length = 30)
    private String goodwillRemarks;
    
    @Column(name = "Auth Init", length = 3)
    private String authInit;
    
    @Column(name = "Auth Sign Date", precision = 8, scale = 0)
    private Integer authSignDate;
    
    @Column(name = "Claim Status", precision = 8, scale = 0)
    private Integer claimStatus;
    
    @Column(name = "Claim Type", length = 1)
    private String claimType;
    
    @Column(name = "Cr Rpt Date", precision = 8, scale = 0)
    private Integer crRptDate;
    
    @Column(name = "Cr Week No", precision = 8, scale = 0)
    private Integer crWeekNo;
    
    @Column(name = "Cr Year", precision = 4, scale = 0)
    private Integer crYear;
    
    @Column(name = "Accts Mth", precision = 4, scale = 0)
    private Integer acctsMth;
    
    @Column(name = "Status Code", length = 1)
    private String statusCode;
    
    @Column(name = "Date 8 digits", precision = 8, scale = 0)
    private Integer date8Digits;
    
    @Column(name = "Time of Day", precision = 6, scale = 0)
    private Integer timeOfDay;
    
    @Column(name = "Extras", length = 20)
    private String extras;
}

// JPA Entity for S3F103
@Entity
@Table(name = "S3F103")
class S3F103Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Prod Type", precision = 6, scale = 0)
    private Integer prodType;
    
    @Column(name = "Failed Part", length = 18)
    private String failedPart;
    
    @Column(name = "Damage Code-1", length = 18)
    private String damageCode1;
    
    @Column(name = "Sequence Number", length = 1)
    private String sequenceNumber;
    
    @Column(name = "Part No", length = 18)
    private String partNo;
    
    @Column(name = "Qty", length = 18)
    private String qty;
    
    @Column(name = "Demand Code", length = 2)
    private String demandCode;
    
    @Column(name = "Dealer Material %", precision = 3, scale = 0)
    private Integer dealerMaterialPercent;
    
    @Column(name = "Supplier Material %", precision = 3, scale = 0)
    private Integer supplierMaterialPercent;
}

// JPA Entity for S3F104
@Entity
@Table(name = "S3F104")
class S3F104Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Prod Type", precision = 3, scale = 0)
    private Integer prodType;
    
    @Column(name = "Failed Part", length = 18)
    private String failedPart;
    
    @Column(name = "Damage Code-1", length = 18)
    private String damageCode1;
    
    @Column(name = "Sequence Number", length = 1)
    private String sequenceNumber;
    
    @Column(name = "Record Number", length = 1)
    private String recordNumber;
    
    @Column(name = "Operation Code", length = 1)
    private String operationCode;
    
    @Column(name = "Op. Code Qty", length = 1)
    private String opCodeQty;
    
    @Column(name = "Demand Code", length = 2)
    private String demandCode;
    
    @Column(name = "Dealer Labour %", precision = 3, scale = 0)
    private Integer dealerLabourPercent;
    
    @Column(name = "Supplier Labour %", precision = 3, scale = 0)
    private Integer supplierLabourPercent;
}

// JPA Entity for S3F105
@Entity
@Table(name = "S3F105")
class S3F105Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Prod Type", precision = 3, scale = 0)
    private Integer prodType;
    
    @Column(name = "Failed Part", length = 18)
    private String failedPart;
    
    @Column(name = "Damage Code-1", length = 18)
    private String damageCode1;
    
    @Column(name = "Sequence Number", length = 1)
    private String sequenceNumber;
    
    @Column(name = "Record Number", length = 1)
    private String recordNumber;
    
    @Column(name = "Special Cost Desc.", length = 25)
    private String specialCostDesc;
    
    @Column(name = "Special Costs Value", precision = 11, scale = 2)
    private BigDecimal specialCostsValue;
    
    @Column(name = "Demand Code", length = 2)
    private String demandCode;
    
    @Column(name = "Dealer Special Cost%", precision = 3, scale = 0)
    private Integer dealerSpecialCostPercent;
    
    @Column(name = "Supplier Special Cost%", precision = 3, scale = 0)
    private Integer supplierSpecialCostPercent;
}

// JPA Entity for S3F106
@Entity
@Table(name = "S3F106")
class S3F106Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "Prod Type", precision = 3, scale = 0)
    private Integer prodType;
    
    @Column(name = "Failed Part", length = 18)
    private String failedPart;
    
    @Column(name = "Damage Code-1", length = 18)
    private String damageCode1;
    
    @Column(name = "Sequence Number", length = 1)
    private String sequenceNumber;
    
    @Column(name = "Record Number", length = 1)
    private String recordNumber;
    
    @Column(name = "Unscheduled Description", length = 30)
    private String unscheduledDescription;
    
    @Column(name = "Unsched Time", length = 30)
    private String unschedTime;
    
    @Column(name = "Demand Code", length = 2)
    private String demandCode;
    
    @Column(name = "Dealer Labour %", precision = 3, scale = 0)
    private Integer dealerLabourPercent;
    
    @Column(name = "Supplier Labour %", precision = 3, scale = 0)
    private Integer supplierLabourPercent;
}

// JPA Repositories
interface HSG71Repository extends JpaRepository<HSG71Entity, Long> {
    Optional<HSG71Entity> findByKey(String pakz, String rechNr, String rechDatum, String auftragsNr, String wete);
    List<HSG71Entity> findByClaimNumber(String claimNr);
    Optional<HSG71Entity> findLastClaim();
}

interface HSG73Repository extends JpaRepository<HSG73Entity, Long> {
    Optional<HSG73Entity> findByKey(String pakz, String claimNr, String fehlerNr);
    List<HSG73Entity> findByKeyG71(String pakz, String rechNr, String rechDatum, String auftragsNr, String bereich);
}

interface HSGPSRepository extends JpaRepository<HSGPSEntity, Long> {
    List<HSGPSEntity> findByClaimAndFailure(String kuerzel, String claimNr, String fehlerNr);
    List<HSGPSEntity> findByClaim(String kuerzel, String claimNr);
}

interface FARSTLRepository extends JpaRepository<FARSTLEntity, String> {
}

interface AUFWSKRepository extends JpaRepository<AUFWSKEntity, Long> {
}

interface S3F004Repository extends JpaRepository<S3F004Entity, Long> {
    Optional<S3F004Entity> findByKey(BigDecimal gaClaimNo, BigDecimal pageNo, BigDecimal distWrntyCustNo);
}

interface S3F103Repository extends JpaRepository<S3F103Entity, Long> {
}

interface S3F104Repository extends JpaRepository<S3F104Entity, Long> {
}

interface S3F105Repository extends JpaRepository<S3F105Entity, Long> {
}

interface S3F106Repository extends JpaRepository<S3F106Entity, Long> {
}
