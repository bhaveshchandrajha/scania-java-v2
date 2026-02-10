```java
package com.warranty.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Service class for warranty validation and claim history logic.
 * Migrated from RPG program HS1210.
 */
@Service
public class WarrantyValidationService {

    private static final Logger logger = LoggerFactory.getLogger(WarrantyValidationService.class);

    // Status code constants
    private static final String STATUS_COMPENSATED = "61";
    private static final int STATUS_COMPLETED = 30;
    private static final int STATUS_INITIAL = 0;

    // Standard warranty period in days
    private static final int WARRANTY_PERIOD_DAYS = 1095; // 3 years

    private final ClaimRepository claimRepository;
    private final WarrantyScopeService warrantyScopeService;
    private final WorkProcessService workProcessService;

    public WarrantyValidationService(ClaimRepository claimRepository,
                                      WarrantyScopeService warrantyScopeService,
                                      WorkProcessService workProcessService) {
        this.claimRepository = claimRepository;
        this.warrantyScopeService = warrantyScopeService;
        this.workProcessService = workProcessService;
    }

    /**
     * Validates a claim and processes it through the workflow if valid.
     * 
     * @param claimContext The claim context containing all relevant claim data
     * @return ValidationResult indicating whether the claim was processed successfully
     */
    @Transactional
    public ValidationResult validateAndProcessClaim(ClaimContext claimContext) {
        logger.debug("Starting claim validation for claim ID: {}", claimContext.getClaimId());

        // Check if the item falls within warranty scope
        if (!isWarrantyScope(claimContext.getWarrantyCode())) {
            logger.info("Claim {} is outside warranty scope", claimContext.getClaimId());
            return ValidationResult.outsideWarrantyScope();
        }

        // Perform claim validation
        if (checkClaim(claimContext)) {
            // Invoke work process routine with claim parameters
            callWorkProcess(claimContext);
            
            // Mark that changes have been made to the record
            claimContext.setRecordModified(true);
            
            logger.info("Claim {} validated and processed successfully", claimContext.getClaimId());
            return ValidationResult.success();
        }

        logger.warn("Claim {} failed validation", claimContext.getClaimId());
        return ValidationResult.validationFailed();
    }

    /**
     * Validates claim processing based on status code and compensation values.
     * Implements business rule for detecting discrepancies between requested and compensated amounts.
     * 
     * @param claimContext The claim context
     * @return true if there is a discrepancy requiring special handling
     */
    public boolean validateClaimCompensation(ClaimContext claimContext) {
        String statusCode = claimContext.getStatusCode();
        
        // If status is not '61', flag the condition immediately
        if (!STATUS_COMPENSATED.equals(statusCode)) {
            logger.debug("Status code {} is not '{}', flagging condition", statusCode, STATUS_COMPENSATED);
            return true;
        }

        // For status '61', check if requested and compensated values differ
        BigDecimal requestedAmount = claimContext.getRequestedClaimAmount();
        BigDecimal compensatedAmount = claimContext.getCompensatedClaimAmount();
        
        boolean hasDiscrepancy = !requestedAmount.equals(compensatedAmount);
        
        if (hasDiscrepancy) {
            logger.info("Claim {} has discrepancy: requested={}, compensated={}", 
                       claimContext.getClaimId(), requestedAmount, compensatedAmount);
        }
        
        return hasDiscrepancy;
    }

    /**
     * Evaluates the status indicator to determine processing path.
     * Status 30 = Completed, Status 0 = Initial/Unprocessed
     * 
     * @param statusIndicator The status indicator value
     * @return true if status requires special processing (completed or initial)
     */
    public boolean requiresSpecialProcessing(int statusIndicator) {
        return statusIndicator == STATUS_COMPLETED || statusIndicator == STATUS_INITIAL;
    }

    /**
     * Checks if the given warranty code falls within warranty scope.
     * 
     * @param warrantyCode The warranty code to check
     * @return true if within warranty scope
     */
    public boolean isWarrantyScope(String warrantyCode) {
        return warrantyScopeService.isWithinWarrantyScope(warrantyCode);
    }

    /**
     * Validates the claim based on business rules.
     * 
     * @param claimContext The claim context
     * @return true if the claim passes validation
     */
    private boolean checkClaim(ClaimContext claimContext) {
        // Validate claim is not null and has required fields
        if (claimContext == null || claimContext.getClaimId() == null) {
            return false;
        }

        // Check warranty expiration (1095 days = 3 years)
        if (isWarrantyExpired(claimContext.getPurchaseDate())) {
            logger.info("Warranty expired for claim {}", claimContext.getClaimId());
            return false;
        }

        // Verify claim exists in repository
        Optional<Claim> existingClaim = claimRepository.findById(claimContext.getClaimId());
        if (existingClaim.isEmpty()) {
            logger.warn("Claim {} not found in repository", claimContext.getClaimId());
            return false;
        }

        // Additional validation: check status code handling
        if (!STATUS_COMPENSATED.equals(claimContext.getStatusCode())) {
            // Non-61 status requires additional validation
            return validateNonCompensatedClaim(claimContext);
        }

        return true;
    }

    /**
     * Checks if the warranty has expired based on purchase date.
     * Warranty expires after 1095 days (3 years).
     * 
     * @param purchaseDate The original purchase date
     * @return true if warranty has expired
     */
    private boolean isWarrantyExpired(LocalDate purchaseDate) {
        if (purchaseDate == null) {
            return true;
        }
        
        LocalDate expirationDate = purchaseDate.plusDays(WARRANTY_PERIOD_DAYS);
        return LocalDate.now().isAfter(expirationDate);
    }

    /**
     * Validates claims that are not in compensated status.
     * 
     * @param claimContext The claim context
     * @return true if claim passes validation
     */
    private boolean validateNonCompensatedClaim(ClaimContext claimContext) {
        // TODO: Implement specific validation rules for non-compensated claims
        // Based on RPG logic, status != '61' triggers different handling
        logger.debug("Validating non-compensated claim: {}", claimContext.getClaimId());
        return true;
    }

    /**
     * Invokes the work process routine with claim parameters.
     * 
     * @param claimContext The claim context containing parameters
     */
    private void callWorkProcess(ClaimContext claimContext) {
        WorkProcessRequest request = WorkProcessRequest.builder()
            .claimId(claimContext.getClaimId())
            .warrantyCode(claimContext.getWarrantyCode())
            .statusCode(claimContext.getStatusCode())
            .requestedAmount(claimContext.getRequestedClaimAmount())
            .build();
        
        workProcessService.processWorkItem(request);
    }

    /**
     * Retrieves claim history for validation purposes.
     * 
     * @param claimId The claim identifier
     * @return ClaimHistory containing historical claim data
     */
    public ClaimHistory getClaimHistory(String claimId) {
        return claimRepository.findClaimHistory(claimId)
            .orElseThrow(() -> new ClaimNotFoundException("Claim history not found: " + claimId));
    }

    // ==================== Inner Classes / DTOs ====================

    /**
     * Context object holding all claim-related data for processing.
     */
    public static class ClaimContext {
        private String claimId;
        private String warrantyCode;      // G73140 - warranty scope code
        private String statusCode;         // G73420 - status code
        private int statusIndicator;       // G73290 - status/type indicator
        private BigDecimal requestedClaimAmount;
        private BigDecimal compensatedClaimAmount;
        private LocalDate purchaseDate;
        private boolean recordModified;

        // Getters and Setters
        public String getClaimId() { return claimId; }
        public void setClaimId(String claimId) { this.claimId = claimId; }
        
        public String getWarrantyCode() { return warrantyCode; }
        public void setWarrantyCode(String warrantyCode) { this.warrantyCode = warrantyCode; }
        
        public String getStatusCode() { return statusCode; }
        public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
        
        public int getStatusIndicator() { return statusIndicator; }
        public void setStatusIndicator(int statusIndicator) { this.statusIndicator = statusIndicator; }
        
        public BigDecimal getRequestedClaimAmount() { return requestedClaimAmount; }
        public void setRequestedClaimAmount(BigDecimal requestedClaimAmount) { this.requestedClaimAmount = requestedClaimAmount; }
        
        public BigDecimal getCompensatedClaimAmount() { return compensatedClaimAmount; }
        public void setCompensatedClaimAmount(BigDecimal compensatedClaimAmount) { this.compensatedClaimAmount = compensatedClaimAmount; }
        
        public LocalDate getPurchaseDate() { return purchaseDate; }
        public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
        
        public boolean isRecordModified() { return recordModified; }
        public void setRecordModified(boolean recordModified) { this.recordModified = recordModified; }
    }

    /**
     * Result object for validation operations.
     */
    public static class ValidationResult {
        private final boolean success;
        private final String message;
        private final ValidationStatus status;

        private ValidationResult(boolean success, String message, ValidationStatus status) {
            this.success = success;
            this.message = message;
            this.status = status;
        }

        public static ValidationResult success() {
            return new ValidationResult(true, "Validation successful", ValidationStatus.SUCCESS);
        }

        public static ValidationResult validationFailed() {
            return new ValidationResult(false, "Claim validation failed", ValidationStatus.VALIDATION_FAILED);
        }

        public static ValidationResult outsideWarrantyScope() {
            return new ValidationResult(false, "Claim is outside warranty scope", ValidationStatus.OUTSIDE_WARRANTY_SCOPE);
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public ValidationStatus getStatus() { return status; }
    }

    public enum ValidationStatus {
        SUCCESS,
        VALIDATION_FAILED,
        OUTSIDE_WARRANTY_SCOPE,
        WARRANTY_EXPIRED
    }

    /**
     * Historical claim data for audit and validation.
     */
    public static class ClaimHistory {
        private String claimId;
        private LocalDate claimDate;
        private String previousStatus;
        private BigDecimal previousAmount;
        // TODO: Add additional history fields as needed

        public String getClaimId() { return claimId; }
        public void setClaimId(String claimId) { this.claimId = claimId; }
        
        public LocalDate getClaimDate() { return claimDate; }
        public void setClaimDate(LocalDate claimDate) { this.claimDate = claimDate; }
        
        public String getPreviousStatus() { return previousStatus; }
        public void setPreviousStatus(String previousStatus) { this.previousStatus = previousStatus; }
        
        public BigDecimal getPreviousAmount() { return previousAmount; }
        public void setPreviousAmount(BigDecimal previousAmount) { this.previousAmount = previousAmount; }
    }

    /**
     * Work process request DTO.
     */
    public static class WorkProcessRequest {
        private String claimId;
        private String warrantyCode;
        private String statusCode;
        private BigDecimal requestedAmount;

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private final WorkProcessRequest request = new WorkProcessRequest();

            public Builder claimId(String claimId) { request.claimId = claimId; return this; }
            public Builder warrantyCode(String warrantyCode) { request.warrantyCode = warrantyCode; return this; }
            public Builder statusCode(String statusCode) { request.statusCode = statusCode; return this; }
            public Builder requestedAmount(BigDecimal requestedAmount) { request.requestedAmount = requestedAmount; return this; }
            public WorkProcessRequest build() { return request; }
        }

        public String getClaimId() { return claimId; }
        public String getWarrantyCode() { return warrantyCode; }
        public String getStatusCode() { return statusCode; }
        public BigDecimal getRequestedAmount() { return requestedAmount; }
    }

    /**
     * Custom exception for claim not found scenarios.
     */
    public static class ClaimNotFoundException extends RuntimeException {
        public ClaimNotFoundException(String message) {
            super(message);
        }
    }
}

// ==================== Repository Interface ====================

interface ClaimRepository {
    Optional<Claim> findById(String claimId);
    Optional<WarrantyValidationService.ClaimHistory> findClaimHistory(String claimId);
}

// ==================== Service Interfaces ====================

interface WarrantyScopeService {
    boolean isWithinWarrantyScope(String warrantyCode);
}

interface WorkProcessService {
    void processWorkItem(WarrantyValidationService.WorkProcessRequest request);
}

// ==================== Domain Entity ====================

class Claim {
    private String claimId;
    private String statusCode;
    private BigDecimal amount;
    // TODO: Add additional claim fields based on HSAHKPF structure

    public String getClaimId() { return claimId; }
    public void setClaimId(String claimId) { this.claimId = claimId; }
    
    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
```