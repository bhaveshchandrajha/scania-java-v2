/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for creating claims from GPS position lines.
 */
@Service
public class ClaimCreationService {
    
    private final ClaimPositionLineRepository claimPositionLineRepository;
    private final ClaimErrorRepository claimErrorRepository;
    private final ClaimRepository claimRepository;
    private final PartMasterRepository partMasterRepository;
    
    @Autowired
    public ClaimCreationService(ClaimPositionLineRepository claimPositionLineRepository,
                                ClaimErrorRepository claimErrorRepository,
                                ClaimRepository claimRepository,
                                PartMasterRepository partMasterRepository) {
        this.claimPositionLineRepository = claimPositionLineRepository;
        this.claimErrorRepository = claimErrorRepository;
        this.claimRepository = claimRepository;
        this.partMasterRepository = partMasterRepository;
    }
    
    @Transactional
    public void createClaim(String kuerzel, String claimNr, String pakz, String rechNr, String rechDatum) {
        int failureNo = 1;
        // @origin HS1210 L941-941 (CHAIN)
        List<ClaimPositionLine> lines = claimPositionLineRepository.findByKuerzelAndClaimNrOrderByFehlerNrAndFolgeNrAndZeile(kuerzel, claimNr);
        
        boolean create = false;
        boolean maintenance = false;
        String groups = "";
        String partNo = "";
        BigDecimal partValue = BigDecimal.ZERO;
        List<String> text = new ArrayList<>();
        int lineNo = 0;
        BigDecimal valueMat = BigDecimal.ZERO;
        BigDecimal valueLab = BigDecimal.ZERO;
        BigDecimal valueSpe = BigDecimal.ZERO;
        
        // @origin HS1210 L884-1012 (DOW)
        for (ClaimPositionLine line : lines) {
            int currentFailureNo = line.getFehlerNr() != null ? Integer.parseInt(line.getFehlerNr()) : 0;
            
            // @origin HS1210 L830-833 (IF)
            if (currentFailureNo > failureNo) {
                createFailure(pakz, rechNr, rechDatum, failureNo, groups, partNo, maintenance, text, valueMat, valueLab, valueSpe);
                failureNo = currentFailureNo;
                maintenance = false;
                text.clear();
                lineNo = 0;
                valueMat = BigDecimal.ZERO;
                valueLab = BigDecimal.ZERO;
                valueSpe = BigDecimal.ZERO;
                groups = "";
                partNo = "";
                partValue = BigDecimal.ZERO;
            }
            
            if (currentFailureNo > 0) {
                create = true;
                String fehlerNr = String.format("%02d", currentFailureNo);
                String folgeNr = "00";
                
                if ("MAT".equals(line.getSatzart())) {
                    // @origin HS1210 L1027-1027 (CHAIN)
                    Optional<PartMaster> partMaster = partMasterRepository.findByPartNo(line.getNr());
                    // @origin HS1210 L864-883 (IF)
                    if (partMaster.isPresent() && "SC".equals(partMaster.get().getSfran())) {
                        if (line.getWert().compareTo(partValue) > 0) {
                            partNo = line.getNr();
                            partValue = line.getWert();
                        }
                    }
                }
                
                String textUpper = claimPositionLineRepository.upperCase(line.getText());
                if ("ARB".equals(line.getSatzart()) && textUpper.contains("WARTUNG")) {
                    maintenance = true;
                    groups = line.getNr().substring(0, Math.min(4, line.getNr().length()));
                }
                
                if ("ARB".equals(line.getSatzart()) && groups.isEmpty()) {
                    groups = line.getNr().substring(0, Math.min(4, line.getNr().length()));
                }
                
                if ("TXT".equals(line.getSatzart()) && !line.getText().startsWith("+") && lineNo < 4) {
                    lineNo++;
                    text.add(line.getText());
                }
                
                if ("ARB".equals(line.getSatzart())) {
                    valueLab = valueLab.add(line.getMenge().multiply(line.getWert()));
                } else if ("MAT".equals(line.getSatzart())) {
                    valueMat = valueMat.add(line.getMenge().multiply(line.getWert()));
                } else if (!"TXT".equals(line.getSatzart())) {
                    valueSpe = valueSpe.add(line.getWert());
                }
            }
        }
        
        if (create) {
            createFailure(pakz, rechNr, rechDatum, failureNo, groups, partNo, maintenance, text, valueMat, valueLab, valueSpe);
            // @origin HS1210 L1035-1035 (CHAIN)
            Optional<Claim> claim = claimRepository.findByPakzAndClaimNrAndRechDatum(pakz, claimNr, rechDatum);
            // @origin HS1210 L909-911 (IF)
            if (claim.isPresent()) {
                // @origin HS1210 L887-887 (EVAL)
                claim.get().setStatusCodeSde(3);
                // @origin HS1210 L860-860 (WRITE)
                claimRepository.save(claim.get());
            }
        }
    }
    
    private void createFailure(String pakz, String rechNr, String rechDatum, int failureNo, String groups, String partNo, boolean maintenance, List<String> text, BigDecimal valueMat, BigDecimal valueLab, BigDecimal valueSpe) {
        ClaimError error = new ClaimError();
        // @origin HS1210 L890-890 (EVAL)
        error.setPakz(pakz);
        error.setRechNr(rechNr);
        error.setRechDatum(rechDatum);
        error.setFehlerNr(String.format("%02d", failureNo));
        error.setFolgeNr("00");
        
        // @origin HS1210 L914-916 (IF)
        if (groups.length() >= 2) {
            // @origin HS1210 L915-915 (EVAL)
            error.setHauptgruppe(groups.substring(0, 2));
        }
        // @origin HS1210 L919-996 (IF)
        if (groups.length() >= 4) {
            // @origin HS1210 L939-939 (EVAL)
            error.setNebengruppe(groups.substring(2, 4));
        }
        
        error.setFehlerTeil(partNo);
        error.setBeantrMat(valueMat);
        error.setBeantrArb(valueLab);
        error.setBeantrSpez(valueSpe);
        
        // @origin HS1210 L944-953 (IF)
        if (text.size() > 0) {
            // @origin HS1210 L1047-1047 (EVAL)
            error.setText1(text.get(0));
        }
        // @origin HS1210 L956-958 (IF)
        if (text.size() > 1) {
            // @origin HS1210 L1048-1048 (EVAL)
            error.setText2(text.get(1));
        }
        // @origin HS1210 L959-970 (IF)
        if (text.size() > 2) {
            // @origin HS1210 L1049-1049 (EVAL)
            error.setText3(text.get(2));
        }
        // @origin HS1210 L960-963 (IF)
        if (text.size() > 3) {
            // @origin HS1210 L1050-1050 (EVAL)
            error.setText4(text.get(3));
        }
        
        // @origin HS1210 L861-861 (WRITE)
        claimErrorRepository.save(error);
    }
}