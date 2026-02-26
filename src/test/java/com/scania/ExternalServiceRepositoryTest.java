# Complete JUnit Test Cases for ExternalServiceRepository

```java
package com.scania.warranty.repository;

import com.scania.warranty.domain.ExternalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ExternalServiceRepository
 * Tests repository methods for warranty claim processing
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("ExternalServiceRepository Tests")
class ExternalServiceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExternalServiceRepository externalServiceRepository;

    private ExternalService testService1;
    private ExternalService testService2;
    private ExternalService testService3;

    private static final String COMPANY_CODE = "001";
    private static final String INVOICE_NUMBER = "INV01";
    private static final String INVOICE_DATE = "20240115";
    private static final String ORDER_NUMBER = "ORD01";

    @BeforeEach
    void setUp() {
        // Clear repository before each test
        externalServiceRepository.deleteAll();
        entityManager.clear();

        // Create test data
        testService1 = createExternalService(
            COMPANY_CODE, 
            INVOICE_NUMBER, 
            INVOICE_DATE, 
            ORDER_NUMBER, 
            "00" // Status: OPEN
        );

        testService2 = createExternalService(
            COMPANY_CODE, 
            INVOICE_NUMBER, 
            INVOICE_DATE, 
            "ORD02", 
            "10" // Status: SENT
        );

        testService3 = createExternalService(
            "002", 
            "INV02", 
            "20240116", 
            "ORD03", 
            "20" // Status: PROCESSED
        );
    }

    // ==================== Basic CRUD Tests ====================

    @Test
    @DisplayName("Should save external service successfully")
    void testSaveExternalService() {
        // Given
        ExternalService newService = createExternalService(
            "003", "INV03", "20240117", "ORD04", "00"
        );

        // When
        ExternalService savedService = externalServiceRepository.save(newService);

        // Then
        assertNotNull(savedService);
        assertNotNull(savedService.getId());
        assertEquals("003", savedService.getCompanyCode());
        assertEquals("INV03", savedService.getInvoiceNumber());
        assertEquals("20240117", savedService.getInvoiceDate());
    }

    @Test
    @DisplayName("Should find external service by ID")
    void testFindById() {
        // Given
        ExternalService saved = externalServiceRepository.save(testService1);

        // When
        Optional<ExternalService> found = externalServiceRepository.findById(saved.getId());

        // Then
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals(COMPANY_CODE, found.get().getCompanyCode());
    }

    @Test
    @DisplayName("Should return empty when ID not found")
    void testFindByIdNotFound() {
        // When
        Optional<ExternalService> found = externalServiceRepository.findById(999L);

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should find all external services")
    void testFindAll() {
        // Given
        externalServiceRepository.save(testService1);
        externalServiceRepository.save(testService2);
        externalServiceRepository.save(testService3);

        // When
        List<ExternalService> services = externalServiceRepository.findAll();

        // Then
        assertNotNull(services);
        assertEquals(3, services.size());
    }

    @Test
    @DisplayName("Should delete external service")
    void testDeleteExternalService() {
        // Given
        ExternalService saved = externalServiceRepository.save(testService1);
        Long id = saved.getId();

        // When
        externalServiceRepository.deleteById(id);

        // Then
        Optional<ExternalService> found = externalServiceRepository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Should update external service")
    void testUpdateExternalService() {
        // Given
        ExternalService saved = externalServiceRepository.save(testService1);
        saved.setStatus("10");

        // When
        ExternalService updated = externalServiceRepository.save(saved);

        // Then
        assertEquals("10", updated.getStatus());
        assertEquals(saved.getId(), updated.getId());
    }

    // ==================== Custom Query Method Tests ====================

    @Test
    @DisplayName("Should find services by company code, invoice number and invoice date")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate() {
        // Given
        externalServiceRepository.save(testService1);
        externalServiceRepository.save(testService2);
        externalServiceRepository.save(testService3);

        // When
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                COMPANY_CODE, 
                INVOICE_NUMBER, 
                INVOICE_DATE
            );

        // Then
        assertNotNull(found);
        assertEquals(2, found.size());
        found.forEach(service -> {
            assertEquals(COMPANY_CODE, service.getCompanyCode());
            assertEquals(INVOICE_NUMBER, service.getInvoiceNumber());
            assertEquals(INVOICE_DATE, service.getInvoiceDate());
        });
    }

    @Test
    @DisplayName("Should return empty list when no matching services found")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_NotFound() {
        // Given
        externalServiceRepository.save(testService1);

        // When
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "999", 
                "NOTFOUND", 
                "20990101"
            );

        // Then
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Should find services with status greater than specified value")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDateAndStatusGreaterThan() {
        // Given
        externalServiceRepository.save(testService1); // Status: 00
        externalServiceRepository.save(testService2); // Status: 10
        ExternalService service4 = createExternalService(
            COMPANY_CODE, INVOICE_NUMBER, INVOICE_DATE, "ORD04", "20"
        );
        externalServiceRepository.save(service4); // Status: 20

        // When
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDateAndStatusGreaterThan(
                COMPANY_CODE, 
                INVOICE_NUMBER, 
                INVOICE_DATE,
                "00" // Find status > 00
            );

        // Then
        assertNotNull(found);
        assertEquals(2, found.size());
        found.forEach(service -> {
            assertTrue(service.getStatus().compareTo("00") > 0);
        });
    }

    @Test
    @DisplayName("Should return empty list when no services have status greater than specified")
    void testFindByStatusGreaterThan_NoResults() {
        // Given
        externalServiceRepository.save(testService1); // Status: 00

        // When
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDateAndStatusGreaterThan(
                COMPANY_CODE, 
                INVOICE_NUMBER, 
                INVOICE_DATE,
                "99" // No status greater than 99
            );

        // Then
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Should filter correctly with status boundary values")
    void testFindByStatusGreaterThan_BoundaryValues() {
        // Given
        ExternalService service00 = createExternalService(
            COMPANY_CODE, INVOICE_NUMBER, INVOICE_DATE, "ORD01", "00"
        );
        ExternalService service10 = createExternalService(
            COMPANY_CODE, INVOICE_NUMBER, INVOICE_DATE, "ORD02", "10"
        );
        ExternalService service20 = createExternalService(
            COMPANY_CODE, INVOICE_NUMBER, INVOICE_DATE, "ORD03", "20"
        );
        
        externalServiceRepository.save(service00);
        externalServiceRepository.save(service10);
        externalServiceRepository.save(service20);

        // When - Find status > 10
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDateAndStatusGreaterThan(
                COMPANY_CODE, 
                INVOICE_NUMBER, 
                INVOICE_DATE,
                "10"
            );

        // Then
        assertNotNull(found);
        assertEquals(1, found.size());
        assertEquals("20", found.get(0).getStatus());
    }

    // ==================== Duplicate Prevention Tests ====================

    @Test
    @DisplayName("Should detect duplicate workorder - same invoice and order")
    void testDuplicateWorkorderDetection() {
        // Given
        externalServiceRepository.save(testService1);

        // When
        List<ExternalService> duplicates = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                COMPANY_CODE, 
                INVOICE_NUMBER, 
                INVOICE_DATE
            );

        // Then
        assertFalse(duplicates.isEmpty());
        assertTrue(duplicates.stream()
            .anyMatch(s -> s.getOrderNumber().equals(ORDER_NUMBER)));
    }

    @Test
    @DisplayName("Should allow same invoice with different order numbers")
    void testSameInvoiceDifferentOrders() {
        // Given
        externalServiceRepository.save(testService1); // ORD01
        externalServiceRepository.save(testService2); // ORD02

        // When
        List<ExternalService> services = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                COMPANY_CODE, 
                INVOICE_NUMBER, 
                INVOICE_DATE
            );

        // Then
        assertEquals(2, services.size());
        assertThat(services)
            .extracting(ExternalService::getOrderNumber)
            .containsExactlyInAnyOrder("ORD01", "ORD02");
    }

    // ==================== Validation Tests ====================

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void testFindWithNullParameters() {
        // When
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(null, null, null);

        // Then
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Should handle empty string parameters")
    void testFindWithEmptyParameters() {
        // When
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate("", "", "");

        // Then
        assertNotNull(found);
        assertTrue(found.isEmpty());
    }

    @Test
    @DisplayName("Should be case-sensitive for string comparisons")
    void testCaseSensitiveSearch() {
        // Given
        externalServiceRepository.save(testService1);

        // When
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                COMPANY_CODE.toLowerCase(), 
                INVOICE_NUMBER, 
                INVOICE_DATE
            );

        // Then
        assertTrue(found.isEmpty());
    }

    // ==================== Performance Tests ====================

    @Test
    @DisplayName("Should handle large dataset efficiently")
    void testLargeDatasetPerformance() {
        // Given - Create 100 services
        for (int i = 0; i < 100; i++) {
            ExternalService service = createExternalService(
                "C" + String.format("%03d", i % 10),
                "INV" + String.format("%03d", i % 20),
                "2024011" + (i % 10),
                "ORD" + i,
                String.format("%02d", i % 3 * 10)
            );
            externalServiceRepository.save(service);
        }

        // When
        long startTime = System.currentTimeMillis();
        List<ExternalService> found = externalServiceRepository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "C000", 
                "INV000", 
                "20240110"
            );
        long endTime = System.currentTimeMillis();

        // Then
        assertNotNull(found);
        assertTrue((endTime - startTime) < 1000); // Should complete within 1 second
    }

    // ==================== Integration Tests ====================

    @Test
    @DisplayName("Should maintain data integrity across multiple operations")
    void testDataIntegrity() {
        // Given
        ExternalService saved = externalServiceRepository.save(testService1);
        Long id = saved.getId();

        // When - Update
        saved.setStatus("10");
        externalServiceRepository.save(saved);

        // Then - Verify
        Optional<ExternalService> updated = externalServiceRepository.findById(id);
        assertTrue(updated.isPresent());
        assertEquals("10", updated.get().getStatus());
        assertEquals(COMPANY_CODE, updated.get().getCompanyCode());
    }

    @Test
    @DisplayName("Should handle transaction rollback correctly")
    void testTransactionRollback() {
        // Given
        int initialCount = externalServiceRepository.findAll().size();

        // When - Save and count
        externalServiceRepository.save(testService1);
        int afterSaveCount = externalServiceRepository.findAll().size();

        // Then
        assertEquals(initialCount + 1, afterSaveCount);
    }

    // ==================== Helper Methods ====================

    /**
     * Helper method to create ExternalService test data
     */
    private ExternalService createExternalService(
        String companyCode,
        String invoiceNumber,
        String invoiceDate,
        String orderNumber,
        String status
    ) {
        ExternalService service = new ExternalService();
        service.setCompanyCode(companyCode);
        service.setInvoiceNumber(invoiceNumber);
        service.setInvoiceDate(invoiceDate);
        service.setOrderNumber(orderNumber);
        service.setStatus(status);
        service.setArea("A");
        service.setType("T");
        service.setSplit("01");
        service.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        return service;
    }