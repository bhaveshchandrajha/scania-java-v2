/**
 * Application service implementing warranty claim business logic.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.service;

import com.scania.warranty.domain.ClaimError;
import com.scania.warranty.dto.FailureCreationRequest;
import com.scania.warranty.repository.ClaimErrorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for creating claim failures/errors.
 */
@Service
public class ClaimFailureService {

    private final ClaimErrorRepository claimErrorRepository;

    public ClaimFailureService(ClaimErrorRepository claimErrorRepository) {
        this.claimErrorRepository = claimErrorRepository;
    }

    @Transactional
    public void createFailure(String companyCode, String claimNumber, FailureCreationRequest request) {
        ClaimError error = new ClaimError();

        error.setCompanyCode(companyCode);
        error.setClaimNumber(claimNumber);
        error.setErrorNumber(formatErrorNumber(request.failureNumber()));
        error.setSequenceNumber("00");

        String mainGroup = extractMainGroup(request.groups());
        String subGroup = extractSubGroup(request.groups());
        error.setMainGroup(mainGroup);
        error.setSubGroup(subGroup);

        error.setErrorPart(request.partNumber() != null ? request.partNumber() : "");

        List<String> textLines = request.textLines();
        error.setText1(textLines.size() > 0 ? textLines.get(0) : "");
        error.setText2(textLines.size() > 1 ? textLines.get(1) : "");
        error.setText3(textLines.size() > 2 ? textLines.get(2) : "");
        error.setText4(textLines.size() > 3 ? textLines.get(3) : "");

        error.setRequestedMaterial(request.valueMaterial() != null ? request.valueMaterial() : BigDecimal.ZERO);
        error.setRequestedLabor(request.valueLabor() != null ? request.valueLabor() : BigDecimal.ZERO);
        error.setRequestedSpecial(request.valueSpecial() != null ? request.valueSpecial() : BigDecimal.ZERO);

        error.setInvoiceNumber("");
        error.setInvoiceDate("");
        error.setOrderNumber("");
        error.setArea("");
        error.setDamageCode1("");
        error.setDamageCode2("");
        error.setControlCode("");
        error.setAssessmentCode1("");
        error.setAssessmentCode2(0);
        error.setAssessmentDate(0);
        error.setCompensatedMaterial(0);
        error.setCompensatedLabor(0);
        error.setCompensatedSpecial(0);
        error.setClaimType(0);
        error.setPreviousRepairDate(0);
        error.setPreviousMileage(0);
        error.setFieldTestNumber(0);
        error.setCampaignNumber("");
        error.setEps("");
        error.setStatusCode(0);
        error.setVariantCode(0);
        error.setActionCode(0);
        error.setErrorNumberSde("");
        error.setAttachment("");
        error.setSource("");
        error.setComplain("");
        error.setSymptom("");
        error.setFailure("");
        error.setLocation("");
        error.setRepair("");
        error.setResultCode("");
        error.setResult1("");
        error.setResult2("");
        error.setFault1("");
        error.setFault2("");
        error.setReply1("");
        error.setReply2("");
        error.setExplanation1("");
        error.setExplanation2("");

        // @origin HS1210 L2877-2877 (WRITE)
        claimErrorRepository.save(error);
    }

    private String formatErrorNumber(Integer failureNumber) {
        // @origin HS1210 L2818-2831 (IF)
        if (failureNumber == null || failureNumber == 0) {
            return "00";
        }
        return String.format("%02d", failureNumber);
    }

    private String extractMainGroup(String groups) {
        if (groups == null || groups.length() < 2) {
            return "";
        }
        return groups.substring(0, 2);
    }

    private String extractSubGroup(String groups) {
        if (groups == null || groups.length() < 4) {
            return "";
        }
        return groups.substring(2, 4);
    }
}