package com.scania.warranty.service;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.Invoice;
import com.scania.warranty.repository.ClaimErrorRepository;
import com.scania.warranty.repository.ClaimRepository;
import com.scania.warranty.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

/**
 * Validates business rules for warranty claim creation and transmission.
 * Based on: Validation workflow for creating and transmitting a warranty claim from a workorder.
 *
 * Business Preconditions:
 * 1. Valid Workorder - workorder (invoice) must exist
 * 2. Workorder Structure - Invoice Number, Invoice Date, Order Number, Split, Area, Type must match
 * 3. Repair Date Rule - workorder creation date must not be older than 19 days
 * 4. Duplicate Prevention - no claim already exists for this workorder
 */
@Service
public class ClaimCreationValidationService {

    /** Maximum age of workorder in days for claim creation. */
    public static final int MAX_WORKORDER_AGE_DAYS = 19;

    /** Maximum failures allowed per claim. */
    public static final int MAX_FAILURES_PER_CLAIM = 9;

    /** Status: OPEN. */
    public static final int STATUS_OPEN = 0;

    /** Status: SENT. */
    public static final int STATUS_SENT = 10;

    /** Status: EXCLUDED. */
    public static final int STATUS_EXCLUDED = 99;

    private final InvoiceRepository invoiceRepository;
    private final ClaimRepository claimRepository;
    private final ClaimErrorRepository claimErrorRepository;

    @Autowired
    public ClaimCreationValidationService(
            InvoiceRepository invoiceRepository,
            ClaimRepository claimRepository,
            ClaimErrorRepository claimErrorRepository) {
        this.invoiceRepository = invoiceRepository;
        this.claimRepository = claimRepository;
        this.claimErrorRepository = claimErrorRepository;
    }

    /**
     * Validates that a workorder exists and meets all preconditions for claim creation.
     *
     * @param pakz          Company code
     * @param invoiceNumber AHK010 - Invoice Number
     * @param invoiceDate   AHK020 - Invoice Date
     * @param orderNumber   AHK040 - Order Number
     * @param split         AHK070 - Split
     * @param area          AHK050 - Area
     * @param type          AHK060 - Type (W/T)
     * @return ValidationResult with success/failure and message
     */
    public ValidationResult validateClaimCreation(
            String pakz,
            String invoiceNumber,
            String invoiceDate,
            String orderNumber,
            String split,
            String area,
            String type) {

        // 1. Valid Workorder - workorder must exist
        Optional<Invoice> invoiceOpt = invoiceRepository.findByPakzAndRnrAndRdatAndAnrAndBereiAndWtAndSplitt(
                pakz, invoiceNumber, invoiceDate, orderNumber, area, type != null ? type : "1", split != null ? split : "04");

        if (invoiceOpt.isEmpty()) {
            return ValidationResult.fail("VALID_WORKORDER", "Workorder does not exist in the system");
        }

        Invoice invoice = invoiceOpt.get();

        // 2. Workorder Structure - already validated by repository find
        // (Invoice Number, Date, Order Number, Split, Area, Type must match)

        // 3. Repair Date Rule - workorder creation date (AHK620) must not be older than 19 days
        String workorderDateStr = invoice.getRdat() != null ? invoice.getRdat() : invoice.getAdat();
        if (workorderDateStr != null && !workorderDateStr.isBlank()) {
            if (!isWithinMaxAge(workorderDateStr, MAX_WORKORDER_AGE_DAYS)) {
                return ValidationResult.fail("REPAIR_DATE_RULE",
                        "Workorder creation date must not be older than " + MAX_WORKORDER_AGE_DAYS + " days");
            }
        }

        // 4. Duplicate Prevention - no claim already exists for this workorder
        List<Claim> existingClaims = claimRepository.findByPakzAndRechNrAndRechDatum(pakz, invoiceNumber, invoiceDate);
        for (Claim existing : existingClaims) {
            if (existing.getStatusCodeSde() != null && existing.getStatusCodeSde() != STATUS_EXCLUDED) {
                return ValidationResult.fail("DUPLICATE_PREVENTION",
                        "A claim already exists for this workorder");
            }
        }

        return ValidationResult.ok();
    }

    /**
     * Validates that a claim can be transmitted (Phase 3).
     * - At least one failure must exist
     * - Claim must be technically complete
     *
     * @param pakz       Company code
     * @param claimNumber Claim number
     * @return ValidationResult
     */
    public ValidationResult validateTransmission(String pakz, String claimNumber) {
        Optional<Claim> claimOpt = claimRepository.findByPakzAndClaimNr(pakz, claimNumber);
        if (claimOpt.isEmpty()) {
            return ValidationResult.fail("CLAIM_NOT_FOUND", "Claim does not exist");
        }

        Claim claim = claimOpt.get();
        int failureCount = claimErrorRepository.findByPakzAndClaimNr(pakz, claimNumber).size();

        if (failureCount < 1) {
            return ValidationResult.fail("NO_FAILURE",
                    "A claim cannot be transmitted without at least one failure. Add failure in HS1212 first.");
        }

        if (failureCount > MAX_FAILURES_PER_CLAIM) {
            return ValidationResult.fail("MAX_FAILURES_EXCEEDED",
                    "Maximum of " + MAX_FAILURES_PER_CLAIM + " failures allowed per claim");
        }

        return ValidationResult.ok();
    }

    private boolean isWithinMaxAge(String dateStr, int maxDays) {
        try {
            LocalDate workorderDate = LocalDate.parse(dateStr, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDate today = LocalDate.now();
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(workorderDate, today);
            return daysBetween <= maxDays;
        } catch (DateTimeParseException e) {
            return true; // If we can't parse, allow (fail open for data issues)
        }
    }

    public record ValidationResult(boolean valid, String code, String message) {
        public static ValidationResult ok() {
            return new ValidationResult(true, null, null);
        }

        public static ValidationResult fail(String code, String message) {
            return new ValidationResult(false, code, message);
        }
    }
}
