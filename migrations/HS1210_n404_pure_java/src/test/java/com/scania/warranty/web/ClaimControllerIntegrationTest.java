package com.scania.warranty.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.Invoice;
import com.scania.warranty.dto.ClaimCreationRequestDto;
import com.scania.warranty.repository.ClaimErrorRepository;
import com.scania.warranty.repository.ClaimRepository;
import com.scania.warranty.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for Claim API.
 * Covers claim lifecycle: OPEN → Failure Added → SEND → SENT
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClaimControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    private String invoiceDate; // Set by createValidWorkorder to match created invoice

    @BeforeEach
    void setUp() {
        claimErrorRepository.deleteAll();
        claimRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

    @Nested
    @DisplayName("Phase 1 - Claim Creation (F6 CREATE)")
    class ClaimCreation {

        @Test
        @DisplayName("POST /api/claims - should create claim when workorder is valid")
        void shouldCreateClaimWhenWorkorderValid() throws Exception {
            createValidWorkorder();

            ClaimCreationRequestDto request = new ClaimCreationRequestDto(
                    PAKZ, INVOICE_NUMBER, invoiceDate, ORDER_NUMBER, AREA);

            mockMvc.perform(post("/api/claims")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.pakz").value(PAKZ))
                    .andExpect(jsonPath("$.invoiceNumber").value(INVOICE_NUMBER))
                    .andExpect(jsonPath("$.claimNumber").value(not(emptyOrNullString())))
                    .andExpect(jsonPath("$.statusCodeSde").value(0)); // OPEN
        }

        @Test
        @DisplayName("POST /api/claims/create - should create claim via query params")
        void shouldCreateClaimViaQueryParams() throws Exception {
            createValidWorkorder();

            mockMvc.perform(post("/api/claims/create")
                            .param("pakz", PAKZ)
                            .param("invoiceNumber", INVOICE_NUMBER)
                            .param("invoiceDate", invoiceDate)
                            .param("orderNumber", ORDER_NUMBER)
                            .param("area", AREA))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusCodeSde").value(0));
        }

        @Test
        @DisplayName("Should return 400 when workorder does not exist")
        void shouldReturn400WhenWorkorderNotFound() throws Exception {
            ClaimCreationRequestDto request = new ClaimCreationRequestDto(
                    PAKZ, "99999", "20240101", ORDER_NUMBER, AREA);

            mockMvc.perform(post("/api/claims")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error", containsString("Workorder")));
        }
    }

    @Nested
    @DisplayName("Phase 2 - Failure Assignment")
    class FailureAssignment {

        @Test
        @DisplayName("Claim can have failures (ClaimError) associated")
        void claimCanHaveFailures() throws Exception {
            createValidWorkorder();
            Claim claim = createClaimViaApiAndGet();
            createClaimError(claim);

            mockMvc.perform(get("/api/claims/{pakz}/{claimNumber}", PAKZ, claim.getClaimNr()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.claimNumber").value(claim.getClaimNr()));
        }
    }

    @Nested
    @DisplayName("Phase 3 - Transmission (Option 10 SEND)")
    class Transmission {

        @Test
        @DisplayName("PUT status to 10 - should update claim status to SENT")
        void shouldUpdateStatusToSent() throws Exception {
            createValidWorkorder();
            Claim claim = createClaimViaApiAndGet();
            createClaimError(claim);

            mockMvc.perform(put("/api/claims/{pakz}/{claimNumber}/status", PAKZ, claim.getClaimNr())
                            .param("newStatus", "10"))
                    .andExpect(status().isOk());

            Claim updated = claimRepository.findByPakzAndClaimNr(PAKZ, claim.getClaimNr()).orElseThrow();
            assert updated.getStatusCodeSde() == 10;
        }
    }

    @Nested
    @DisplayName("Claim Lifecycle")
    class ClaimLifecycle {

        @Test
        @DisplayName("Full lifecycle: OPEN → Failure Added → SEND → SENT")
        void fullClaimLifecycle() throws Exception {
            createValidWorkorder();
            ClaimCreationRequestDto createReq = new ClaimCreationRequestDto(
                    PAKZ, INVOICE_NUMBER, invoiceDate, ORDER_NUMBER, AREA);

            var createResult = mockMvc.perform(post("/api/claims")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(createReq)))
                    .andExpect(status().isOk())
                    .andReturn();

            String claimNumber = objectMapper.readTree(createResult.getResponse().getContentAsString())
                    .path("claimNumber").asText();

            createClaimErrorForClaimNumber(claimNumber);

            mockMvc.perform(put("/api/claims/{pakz}/{claimNumber}/status", PAKZ, claimNumber)
                            .param("newStatus", "10"))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/api/claims/{pakz}/{claimNumber}", PAKZ, claimNumber))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusCodeSde").value(10));
        }
    }

    private void createValidWorkorder() {
        invoiceDate = LocalDate.now().minusDays(5).format(DateTimeFormatter.BASIC_ISO_DATE);
        Invoice inv = new Invoice();
        inv.setPakz(PAKZ);
        inv.setRnr(INVOICE_NUMBER);
        inv.setRdat(invoiceDate);
        inv.setAnr(ORDER_NUMBER);
        inv.setBerei(AREA);
        inv.setWt("1");
        inv.setSplitt("04");
        inv.setAdat(invoiceDate);
        inv.setAnTag(invoiceDate);
        inv.setRgsNetto(BigDecimal.TEN);
        inv.setKundenNr("001001");
        inv.setName("Test");
        inv.setFahrgNr("FG1234567");
        invoiceRepository.save(inv);
    }

    private Claim createClaimViaApiAndGet() throws Exception {
        var result = mockMvc.perform(post("/api/claims/create")
                        .param("pakz", PAKZ)
                        .param("invoiceNumber", INVOICE_NUMBER)
                        .param("invoiceDate", invoiceDate)
                        .param("orderNumber", ORDER_NUMBER)
                        .param("area", AREA))
                .andExpect(status().isOk())
                .andReturn();
        String claimNumber = objectMapper.readTree(result.getResponse().getContentAsString())
                .path("claimNumber").asText();
        return claimRepository.findByPakzAndClaimNr(PAKZ, claimNumber).orElseThrow();
    }

    private void createClaimError(Claim claim) {
        var err = new com.scania.warranty.domain.ClaimError();
        err.setPakz(claim.getPakz());
        err.setRechNr(claim.getRechNr());
        err.setRechDatum(claim.getRechDatum());
        err.setAuftragsNr(claim.getAuftragsNr());
        err.setBereich(claim.getBereich());
        err.setClaimNr(claim.getClaimNr());
        err.setFehlerNr("01");
        err.setFolgeNr("00");
        err.setFehlerTeil("ENGINE");
        err.setText1("Failed part");
        claimErrorRepository.save(err);
    }

    private void createClaimErrorForClaimNumber(String claimNumber) {
        Claim claim = claimRepository.findByPakzAndClaimNr(PAKZ, claimNumber).orElseThrow();
        createClaimError(claim);
    }
}
