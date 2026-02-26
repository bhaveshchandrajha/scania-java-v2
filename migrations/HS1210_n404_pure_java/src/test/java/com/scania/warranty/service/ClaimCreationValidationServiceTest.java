package com.scania.warranty.service;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.Invoice;
import com.scania.warranty.repository.ClaimErrorRepository;
import com.scania.warranty.repository.ClaimRepository;
import com.scania.warranty.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for ClaimCreationValidationService.
 * Validates business rules per: Validation workflow for creating/transmitting warranty claim from workorder.
 *
 * Business Preconditions:
 * 1. Valid Workorder - workorder must exist
 * 2. Workorder Structure - Invoice Number, Date, Order Number, Split, Area, Type must match
 * 3. Repair Date Rule - workorder creation date must not be older than 19 days
 * 4. Duplicate Prevention - no claim already exists for this workorder
 */
@DataJpaTest
@Import(ClaimCreationValidationService.class)
@ActiveProfiles("test")
class ClaimCreationValidationServiceTest {

    @Autowired
    private ClaimCreationValidationService validationService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimErrorRepository claimErrorRepository;

    private static final String PAKZ = "001";
    private static final String INVOICE_NUMBER = "12345";
    private static final String ORDER_NUMBER = "001";
    private static final String AREA = "A";
    private static final String TYPE = "1";
    private static final String SPLIT = "04";

    private static String recentDate() {
        return LocalDate.now().minusDays(5).format(DateTimeFormatter.BASIC_ISO_DATE);
    }

    @BeforeEach
    void setUp() {
        claimRepository.deleteAll();
        claimErrorRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

    @Nested
    @DisplayName("Phase 1 - Claim Creation Validation")
    class ClaimCreationValidation {

        @Test
        @DisplayName("Should fail when workorder does not exist")
        void shouldFailWhenWorkorderDoesNotExist() {
            var result = validationService.validateClaimCreation(
                    PAKZ, "99999", "20240101", ORDER_NUMBER, SPLIT, AREA, TYPE);

            assertThat(result.valid()).isFalse();
            assertThat(result.code()).isEqualTo("VALID_WORKORDER");
            assertThat(result.message()).contains("does not exist");
        }

        @Test
        @DisplayName("Should fail when workorder structure does not match")
        void shouldFailWhenWorkorderStructureDoesNotMatch() {
            createValidInvoice();
            var result = validationService.validateClaimCreation(
                    PAKZ, "wrong", "20240101", ORDER_NUMBER, SPLIT, AREA, TYPE);

            assertThat(result.valid()).isFalse();
            assertThat(result.code()).isEqualTo("VALID_WORKORDER");
        }

        @Test
        @DisplayName("Should fail when workorder is older than 19 days")
        void shouldFailWhenWorkorderOlderThan19Days() {
            String oldDate = LocalDate.now().minusDays(25).format(DateTimeFormatter.BASIC_ISO_DATE);
            createInvoice(PAKZ, INVOICE_NUMBER, oldDate, ORDER_NUMBER, AREA, TYPE, SPLIT);

            var result = validationService.validateClaimCreation(
                    PAKZ, INVOICE_NUMBER, oldDate, ORDER_NUMBER, SPLIT, AREA, TYPE);

            assertThat(result.valid()).isFalse();
            assertThat(result.code()).isEqualTo("REPAIR_DATE_RULE");
            assertThat(result.message()).contains("19 days");
        }

        @Test
        @DisplayName("Should succeed when workorder is within 19 days")
        void shouldSucceedWhenWorkorderWithin19Days() {
            String recentDate = LocalDate.now().minusDays(5).format(DateTimeFormatter.BASIC_ISO_DATE);
            createInvoice(PAKZ, INVOICE_NUMBER, recentDate, ORDER_NUMBER, AREA, TYPE, SPLIT);

            var result = validationService.validateClaimCreation(
                    PAKZ, INVOICE_NUMBER, recentDate, ORDER_NUMBER, SPLIT, AREA, TYPE);

            assertThat(result.valid()).isTrue();
        }

        @Test
        @DisplayName("Should fail when duplicate claim exists")
        void shouldFailWhenDuplicateClaimExists() {
            String date = recentDate();
            createInvoice(PAKZ, INVOICE_NUMBER, date, ORDER_NUMBER, AREA, TYPE, SPLIT);
            createExistingClaim(INVOICE_NUMBER, date, ClaimCreationValidationService.STATUS_OPEN);

            var result = validationService.validateClaimCreation(
                    PAKZ, INVOICE_NUMBER, date, ORDER_NUMBER, SPLIT, AREA, TYPE);

            assertThat(result.valid()).isFalse();
            assertThat(result.code()).isEqualTo("DUPLICATE_PREVENTION");
            assertThat(result.message()).contains("already exists");
        }

        @Test
        @DisplayName("Should succeed when workorder exists and meets all conditions")
        void shouldSucceedWhenWorkorderValidAndMeetsConditions() {
            String date = recentDate();
            createInvoice(PAKZ, INVOICE_NUMBER, date, ORDER_NUMBER, AREA, TYPE, SPLIT);
            var result = validationService.validateClaimCreation(
                    PAKZ, INVOICE_NUMBER, date, ORDER_NUMBER, SPLIT, AREA, TYPE);

            assertThat(result.valid()).isTrue();
        }
    }

    @Nested
    @DisplayName("Phase 3 - Transmission Validation")
    class TransmissionValidation {

        @Test
        @DisplayName("Should fail when claim has no failures")
        void shouldFailWhenClaimHasNoFailures() {
            createValidInvoice();
            Claim claim = createExistingClaim(INVOICE_NUMBER, "20240115", ClaimCreationValidationService.STATUS_OPEN);
            // No claim errors added

            var result = validationService.validateTransmission(PAKZ, claim.getClaimNr());

            assertThat(result.valid()).isFalse();
            assertThat(result.code()).isEqualTo("NO_FAILURE");
            assertThat(result.message()).contains("at least one failure");
        }

        @Test
        @DisplayName("Should fail when claim does not exist")
        void shouldFailWhenClaimDoesNotExist() {
            var result = validationService.validateTransmission(PAKZ, "99999999");
            assertThat(result.valid()).isFalse();
            assertThat(result.code()).isEqualTo("CLAIM_NOT_FOUND");
        }

        @Test
        @DisplayName("Should succeed when claim has at least one failure")
        void shouldSucceedWhenClaimHasAtLeastOneFailure() {
            String date = recentDate();
            createInvoice(PAKZ, INVOICE_NUMBER, date, ORDER_NUMBER, AREA, TYPE, SPLIT);
            Claim claim = createExistingClaim(INVOICE_NUMBER, date, ClaimCreationValidationService.STATUS_OPEN);
            createClaimError(claim);

            var result = validationService.validateTransmission(PAKZ, claim.getClaimNr());

            assertThat(result.valid()).isTrue();
        }
    }

    private void createValidInvoice() {
        createInvoice(PAKZ, INVOICE_NUMBER, "20240115", ORDER_NUMBER, AREA, TYPE, SPLIT);
    }

    private void createInvoice(String pakz, String rnr, String rdat, String anr, String berei, String wt, String splitt) {
        Invoice inv = new Invoice();
        inv.setPakz(pakz);
        inv.setRnr(rnr);
        inv.setRdat(rdat);
        inv.setAnr(anr);
        inv.setBerei(berei);
        inv.setWt(wt);
        inv.setSplitt(splitt);
        inv.setAdat(rdat);
        inv.setAnTag(rdat);
        inv.setRgsNetto(BigDecimal.TEN);
        inv.setKundenNr("001001");
        inv.setName("Test Customer");
        inv.setFahrgNr("FG1234567");
        invoiceRepository.save(inv);
    }

    private Claim createExistingClaim(String rechNr, String rechDatum, int statusCode) {
        Claim claim = new Claim();
        claim.setPakz(PAKZ);
        claim.setRechNr(rechNr);
        claim.setRechDatum(rechDatum);
        claim.setAuftragsNr(ORDER_NUMBER);
        claim.setBereich(AREA);
        claim.setClaimNr("00000001");
        claim.setStatusCodeSde(statusCode);
        claim.setAnzFehler(0);
        return claimRepository.save(claim);
    }

    private void createClaimError(Claim claim) {
        var error = new com.scania.warranty.domain.ClaimError();
        error.setPakz(claim.getPakz());
        error.setRechNr(claim.getRechNr());
        error.setRechDatum(claim.getRechDatum());
        error.setAuftragsNr(claim.getAuftragsNr());
        error.setBereich(claim.getBereich());
        error.setClaimNr(claim.getClaimNr());
        error.setFehlerNr("01");
        error.setFolgeNr("00");
        error.setFehlerTeil("ENGINE");
        error.setText1("Test failure");
        claimErrorRepository.save(error);
    }
}
