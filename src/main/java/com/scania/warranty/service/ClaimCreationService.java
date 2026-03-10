/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClaimCreationService {
    
    private final GpsLineRepository gpsLineRepository;
    private final ClaimFailureRepository claimFailureRepository;
    private final ClaimRepository claimRepository;
    private final StandardMaterialRepository standardMaterialRepository;

    public ClaimCreationService(
            GpsLineRepository gpsLineRepository,
            ClaimFailureRepository claimFailureRepository,
            ClaimRepository claimRepository,
            StandardMaterialRepository standardMaterialRepository) {
        this.gpsLineRepository = gpsLineRepository;
        this.claimFailureRepository = claimFailureRepository;
        this.claimRepository = claimRepository;
        this.standardMaterialRepository = standardMaterialRepository;
    }

    @Transactional
    public String generateClaimNumber(String companyCode, String invoiceNumber, String invoiceDate, String orderNumber, String area) {
        List<Claim> existing = claimRepository.findByPakzOrderByClaimNrDescAll(companyCode);
        int next = 1;
        for (Claim c : existing) {
            try {
                int n = Integer.parseInt(c.getClaimNr().trim());
                if (n >= next) next = n + 1;
            } catch (NumberFormatException ignored) { }
        }
        return String.format("%08d", next);
    }

    @Transactional
    public void createClaim(String kuerzel, String claimNr, String pakz, String rechNr, String rechDatum) {
        ClaimCreationContext context = new ClaimCreationContext(); // @rpg-trace: n1783
        context.setCurrentFailureNo(1); // @rpg-trace: n1783
        
        List<GpsLine> gpsLines = gpsLineRepository.findByKuerzelAndClaimNrOrderByFehlerNrAndFolgeNr(kuerzel, claimNr); // @rpg-trace: n1784, n1785
        
        for (GpsLine gpsLine : gpsLines) { // @rpg-trace: n1785
            int currentLineFailureNo = Integer.parseInt(gpsLine.getFehlerNr()); // @rpg-trace: n1789
            
            if (currentLineFailureNo > context.getCurrentFailureNo()) { // @rpg-trace: n1789
                createFailure(context, pakz, rechNr, rechDatum, claimNr); // @rpg-trace: n1788
                context.resetForNewFailure(currentLineFailureNo); // @rpg-trace: n1790
            }
            
            if (currentLineFailureNo > 0) { // @rpg-trace: n1801
                context.setCreateFlag(true); // @rpg-trace: n1802
                
                String fehlerNrFormatted = String.format("%02d", currentLineFailureNo); // @rpg-trace: n1803
                String folgeNr = "00"; // @rpg-trace: n1806
                
                if ("MAT".equals(gpsLine.getSatzart())) { // @rpg-trace: n1809
                    Optional<StandardMaterial> standardMaterial = standardMaterialRepository.findByPartNumber(gpsLine.getNr()); // @rpg-trace: n1810
                    
                    if (standardMaterial.isPresent() && "SC".equals(standardMaterial.get().getCategory())) { // @rpg-trace: n1812
                        if (gpsLine.getWert().compareTo(context.getPartValue()) > 0) { // @rpg-trace: n1815
                            context.setPartNo(gpsLine.getNr()); // @rpg-trace: n1816
                            context.setPartValue(gpsLine.getWert()); // @rpg-trace: n1817
                        }
                    }
                }
                
                String gpsTextUpper = gpsLine.getText().toUpperCase(); // @rpg-trace: n1803 (SQL UPPER)
                
                if ("ARB".equals(gpsLine.getSatzart()) && gpsTextUpper.contains("WARTUNG")) { // @rpg-trace: n1809
                    context.setMaintenanceFlag(true); // @rpg-trace: n1791
                    context.setGroups(gpsLine.getNr().substring(0, Math.min(4, gpsLine.getNr().length()))); // @rpg-trace: n1796
                }
                
                if ("ARB".equals(gpsLine.getSatzart()) && context.getGroups().isEmpty()) { // @rpg-trace: n1809
                    context.setGroups(gpsLine.getNr().substring(0, Math.min(4, gpsLine.getNr().length()))); // @rpg-trace: n1796
                }
                
                if ("TXT".equals(gpsLine.getSatzart()) && !gpsLine.getText().startsWith("+") && context.getLineNo() < 4) { // @rpg-trace: n1809
                    context.addTextLine(gpsLine.getText()); // @rpg-trace: n1792
                }
                
                if ("ARB".equals(gpsLine.getSatzart())) { // @rpg-trace: n1809
                    BigDecimal laborValue = BigDecimal.valueOf(gpsLine.getMenge()).multiply(gpsLine.getWert()); // @rpg-trace: n1794
                    context.setValueLab(context.getValueLab().add(laborValue)); // @rpg-trace: n1794
                } else if ("MAT".equals(gpsLine.getSatzart())) { // @rpg-trace: n1809
                    BigDecimal materialValue = BigDecimal.valueOf(gpsLine.getMenge()).multiply(gpsLine.getWert()); // @rpg-trace: n1793
                    context.setValueMat(context.getValueMat().add(materialValue)); // @rpg-trace: n1793
                } else if (!"TXT".equals(gpsLine.getSatzart())) { // @rpg-trace: n1809
                    context.setValueSpe(context.getValueSpe().add(gpsLine.getWert())); // @rpg-trace: n1795
                }
            }
        }
        
        if (context.isCreateFlag()) { // @rpg-trace: n1823
            createFailure(context, pakz, rechNr, rechDatum, claimNr); // @rpg-trace: n1822
            
            Optional<Claim> claim = claimRepository.findByPakzAndRechNrAndRechDatum(pakz, rechNr, rechDatum); // @rpg-trace: n1824
            
            if (claim.isPresent()) { // @rpg-trace: n1826
                claimRepository.updateStatusCode(pakz, rechNr, rechDatum, 3); // @rpg-trace: n1828, n1829
            }
            
            if ("SNF".equals(pakz)) { // @rpg-trace: n1832
                // sendClaim implementation would go here
            }
        }
    }

    private void createFailure(ClaimCreationContext context, String pakz, String rechNr, String rechDatum, String claimNr) {
        ClaimFailure failure = new ClaimFailure(); // @rpg-trace: n1788
        
        ClaimFailureId failureId = new ClaimFailureId(); // @rpg-trace: n1788
        failureId.setPakz(pakz); // @rpg-trace: n1788
        failureId.setRechNr(rechNr); // @rpg-trace: n1788
        failureId.setRechDatum(rechDatum); // @rpg-trace: n1788
        failureId.setClaimNr(claimNr); // @rpg-trace: n1788
        failureId.setFehlerNr(String.format("%02d", context.getCurrentFailureNo())); // @rpg-trace: n1788
        
        failure.setPakz(pakz); // @rpg-trace: n1788
        failure.setRechNr(rechNr); // @rpg-trace: n1788
        failure.setRechDatum(rechDatum); // @rpg-trace: n1788
        failure.setClaimNr(claimNr); // @rpg-trace: n1788
        failure.setFehlerNr(String.format("%02d", context.getCurrentFailureNo())); // @rpg-trace: n1788
        failure.setFolgeNr("00"); // @rpg-trace: n1788
        
        if (!context.getGroups().isEmpty() && context.getGroups().length() >= 2) { // @rpg-trace: n1788
            failure.setHauptgruppe(context.getGroups().substring(0, 2)); // @rpg-trace: n1788
            if (context.getGroups().length() >= 4) { // @rpg-trace: n1788
                failure.setNebengruppe(context.getGroups().substring(2, 4)); // @rpg-trace: n1788
            } else {
                failure.setNebengruppe(""); // @rpg-trace: n1788
            }
        } else {
            failure.setHauptgruppe(""); // @rpg-trace: n1788
            failure.setNebengruppe(""); // @rpg-trace: n1788
        }
        
        failure.setFehlerTeil(context.getPartNo()); // @rpg-trace: n1788
        
        if (context.getTextLines().size() > 0) { // @rpg-trace: n1788
            failure.setText1(context.getTextLines().get(0)); // @rpg-trace: n1788
        } else {
            failure.setText1(""); // @rpg-trace: n1788
        }
        
        if (context.getTextLines().size() > 1) { // @rpg-trace: n1788
            failure.setText2(context.getTextLines().get(1)); // @rpg-trace: n1788
        } else {
            failure.setText2(""); // @rpg-trace: n1788
        }
        
        if (context.getTextLines().size() > 2) { // @rpg-trace: n1788
            failure.setText3(context.getTextLines().get(2)); // @rpg-trace: n1788
        } else {
            failure.setText3(""); // @rpg-trace: n1788
        }
        
        if (context.getTextLines().size() > 3) { // @rpg-trace: n1788
            failure.setText4(context.getTextLines().get(3)); // @rpg-trace: n1788
        } else {
            failure.setText4(""); // @rpg-trace: n1788
        }
        
        failure.setBeantrMat(context.getValueMat()); // @rpg-trace: n1788
        failure.setBeantrArb(context.getValueLab()); // @rpg-trace: n1788
        failure.setBeantrSpez(context.getValueSpe()); // @rpg-trace: n1788
        
        failure.setBereich(""); // @rpg-trace: n1788
        failure.setSchadC1(""); // @rpg-trace: n1788
        failure.setSchadC2(""); // @rpg-trace: n1788
        failure.setSteuerCode(""); // @rpg-trace: n1788
        failure.setBewCode1(""); // @rpg-trace: n1788
        failure.setBewCode2(0); // @rpg-trace: n1788
        failure.setBewDatum(0); // @rpg-trace: n1788
        failure.setVergMat(0); // @rpg-trace: n1788
        failure.setVergArb(0); // @rpg-trace: n1788
        failure.setVergSpez(0); // @rpg-trace: n1788
        failure.setClaimArt(0); // @rpg-trace: n1788
        failure.setvRepDatum(0); // @rpg-trace: n1788
        failure.setvKmStand(0); // @rpg-trace: n1788
        failure.setFeldtestNr(0); // @rpg-trace: n1788
        failure.setKampagnenNr(""); // @rpg-trace: n1788
        failure.setEps(""); // @rpg-trace: n1788
        failure.setStatusCode(0); // @rpg-trace: n1788
        failure.setVariantCode(0); // @rpg-trace: n1788
        failure.setActionCode(0); // @rpg-trace: n1788
        failure.setFehlerNrSde(""); // @rpg-trace: n1788
        failure.setAnhang(""); // @rpg-trace: n1788
        failure.setSource(""); // @rpg-trace: n1788
        failure.setComplain(""); // @rpg-trace: n1788
        failure.setSymptom(""); // @rpg-trace: n1788
        failure.setFailure(""); // @rpg-trace: n1788
        failure.setLocation(""); // @rpg-trace: n1788
        failure.setRepair(""); // @rpg-trace: n1788
        failure.setErgCode(""); // @rpg-trace: n1788
        failure.setResult1(""); // @rpg-trace: n1788
        failure.setResult2(""); // @rpg-trace: n1788
        failure.setFault1(""); // @rpg-trace: n1788
        failure.setFault2(""); // @rpg-trace: n1788
        failure.setReply1(""); // @rpg-trace: n1788
        failure.setReply2(""); // @rpg-trace: n1788
        failure.setExplanation1(""); // @rpg-trace: n1788
        failure.setExplanation2(""); // @rpg-trace: n1788
        
        claimFailureRepository.save(failure); // @rpg-trace: n1788
    }
}