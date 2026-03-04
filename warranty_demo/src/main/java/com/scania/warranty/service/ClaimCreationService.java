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
    // @origin HS1210 L2798-2887 (PROC)
    public void createClaim(String kuerzel, String claimNr, String pakz, String rechNr, String rechDatum) {
        // @origin HS1210 L2814-2814 (EVAL)
        int failureNo = 1;
        // @origin HS1210 L2815-2815 (SETLL)
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
        
        // @origin HS1210 L2817-2870 (DOW)
        for (ClaimPositionLine line : lines) {
            int currentFailureNo = line.getFehlerNr() != null ? Integer.parseInt(line.getFehlerNr()) : 0;
            
            // @origin HS1210 L2818-2818 (COND)
            if (currentFailureNo > failureNo) {
                // @origin HS1210 L2818-2831 (IF)
                createFailure(pakz, rechNr, rechDatum, failureNo, groups, partNo, maintenance, text, valueMat, valueLab, valueSpe);
                // @origin HS1210 L2821-2821 (EVAL)
                failureNo = currentFailureNo;
                // @origin HS1210 L2822-2822 (EVAL)
                maintenance = false;
                // @origin HS1210 L2824-2824 (EVAL)
                text.clear();
                // @origin HS1210 L2824-2824 (EVAL)
                lineNo = 0;
                // @origin HS1210 L2825-2825 (EVAL)
                valueMat = BigDecimal.ZERO;
                // @origin HS1210 L2826-2826 (EVAL)
                valueLab = BigDecimal.ZERO;
                // @origin HS1210 L2827-2827 (EVAL)
                valueSpe = BigDecimal.ZERO;
                // @origin HS1210 L2828-2828 (EVAL)
                groups = "";
                // @origin HS1210 L2829-2829 (EVAL)
                partNo = "";
                // @origin HS1210 L2830-2830 (EVAL)
                partValue = BigDecimal.ZERO;
            }
            
            // @origin HS1210 L2832-2832 (COND)
            if (currentFailureNo > 0) {
                // @origin HS1210 L2833-2833 (EVAL)
                create = true;
                // @origin HS1210 L2834-2834 (EVAL)
                String fehlerNr = String.format("%02d", currentFailureNo);
                // @origin HS1210 L2835-2835 (EVAL)
                String folgeNr = "00";
                
                // @origin HS1210 L2837-2837 (COND)
                if ("MAT".equals(line.getSatzart())) {
                    // @origin HS1210 L2838-2838 (CHAIN)
                    Optional<PartMaster> partMaster = partMasterRepository.findByPartNo(line.getNr());
                    // @origin HS1210 L2839-2839 (COND)
                    if (partMaster.isPresent() && "SC".equals(partMaster.get().getSfran())) {
                        // @origin HS1210 L2840-2840 (COND)
                        if (line.getWert().compareTo(partValue) > 0) {
                            // @origin HS1210 L2841-2841 (EVAL)
                            partNo = line.getNr();
                            // @origin HS1210 L2842-2842 (EVAL)
                            partValue = line.getWert();
                        }
                    }
                }
                
                // @origin HS1210 L2834-2834 (EVAL)
                String textUpper = claimPositionLineRepository.upperCase(line.getText());
                // @origin HS1210 L2837-2837 (COND)
                if ("ARB".equals(line.getSatzart()) && textUpper.contains("WARTUNG")) {
                    maintenance = true;
                    groups = line.getNr().substring(0, Math.min(4, line.getNr().length()));
                }
                
                // @origin HS1210 L2837-2837 (COND)
                if ("ARB".equals(line.getSatzart()) && groups.isEmpty()) {
                    groups = line.getNr().substring(0, Math.min(4, line.getNr().length()));
                }
                
                // @origin HS1210 L2837-2837 (COND)
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
        
        // @origin HS1210 L2871-2871 (COND)
        if (create) {
            // @origin HS1210 L2871-2882 (IF)
            createFailure(pakz, rechNr, rechDatum, failureNo, groups, partNo, maintenance, text, valueMat, valueLab, valueSpe);
            // @origin HS1210 L2874-2874 (CHAIN)
            Optional<Claim> claim = claimRepository.findByPakzAndClaimNrAndRechDatum(pakz, claimNr, rechDatum);
            // @origin HS1210 L2875-2875 (COND)
            if (claim.isPresent()) {
                // @origin HS1210 L2876-2876 (EVAL)
                claim.get().setStatusCodeSde(new BigDecimal(3));
                // @origin HS1210 L2877-2877 (UPDATE)
                claimRepository.save(claim.get());
            }
        }
    }
    
    private void createFailure(String pakz, String rechNr, String rechDatum, int failureNo, String groups, String partNo, boolean maintenance, List<String> text, BigDecimal valueMat, BigDecimal valueLab, BigDecimal valueSpe) {
        ClaimError error = new ClaimError();
        error.setPakz(pakz);
        error.setRechNr(rechNr);
        error.setRechDatum(rechDatum);
        error.setFehlerNr(String.format("%02d", failureNo));
        error.setFolgeNr("00");
        
        if (groups.length() >= 2) {
            error.setHauptgruppe(groups.substring(0, 2));
        }
        if (groups.length() >= 4) {
            error.setNebengruppe(groups.substring(2, 4));
        }
        
        error.setFehlerTeil(partNo);
        error.setBeantrMat(valueMat);
        error.setBeantrArb(valueLab);
        error.setBeantrSpez(valueSpe);
        
        if (text.size() > 0) {
            error.setText1(text.get(0));
        }
        if (text.size() > 1) {
            error.setText2(text.get(1));
        }
        if (text.size() > 2) {
            error.setText3(text.get(2));
        }
        if (text.size() > 3) {
            error.setText4(text.get(3));
        }
        
        claimErrorRepository.save(error);
    }
}