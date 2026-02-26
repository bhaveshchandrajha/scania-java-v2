# JUnit Test Cases for SubmissionDeadlineReleaseRepository

```java
package com.scania.warranty.repository;

import com.scania.warranty.domain.SubmissionDeadlineRelease;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SubmissionDeadlineReleaseRepository
 * Tests repository operations for warranty claim deadline releases
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("SubmissionDeadlineRelease Repository Tests")
class SubmissionDeadlineReleaseRepositoryTest {

    @Autowired
    private SubmissionDeadlineReleaseRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private SubmissionDeadlineRelease testRelease1;
    private SubmissionDeadlineRelease testRelease2;
    private SubmissionDeadlineRelease testRelease3;

    @BeforeEach
    void setUp() {
        // Clear repository before each test
        repository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Create test data
        testRelease1 = createSubmissionDeadlineRelease(
            "001",
            "INV01",
            "20240101",
            "12345",
            "1",
            "A",
            "01"
        );

        testRelease2 = createSubmissionDeadlineRelease(
            "001",
            "INV02",
            "20240102",
            "12346",
            "2",
            "B",
            "02"
        );

        testRelease3 = createSubmissionDeadlineRelease(
            "002",
            "INV01",
            "20240101",
            "12347",
            "1",
            "A",
            "01"
        );
    }

    // ==================== Custom Query Method Tests ====================

    @Test
    @DisplayName("Should find release by company code, invoice number and invoice date")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_Success() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "001",
                "INV01",
                "20240101"
            );

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getCompanyCode()).isEqualTo("001");
        assertThat(result.get().getInvoiceNumber()).isEqualTo("INV01");
        assertThat(result.get().getInvoiceDate()).isEqualTo("20240101");
    }

    @Test
    @DisplayName("Should return empty when no matching release found")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_NotFound() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "999",
                "NONEXISTENT",
                "20240101"
            );

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should distinguish between different company codes")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_DifferentCompanyCodes() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.persist(testRelease3);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result1 = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "001",
                "INV01",
                "20240101"
            );

        Optional<SubmissionDeadlineRelease> result2 = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "002",
                "INV01",
                "20240101"
            );

        // Then
        assertThat(result1).isPresent();
        assertThat(result2).isPresent();
        assertThat(result1.get().getCompanyCode()).isEqualTo("001");
        assertThat(result2.get().getCompanyCode()).isEqualTo("002");
        assertThat(result1.get().getId()).isNotEqualTo(result2.get().getId());
    }

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_NullParameters() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.flush();

        // When & Then
        Optional<SubmissionDeadlineRelease> result = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                null,
                null,
                null
            );

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should handle empty string parameters")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_EmptyStrings() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "",
                "",
                ""
            );

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should be case-sensitive for string parameters")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_CaseSensitive() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "001",
                "inv01", // lowercase
                "20240101"
            );

        // Then
        assertThat(result).isEmpty();
    }

    // ==================== JpaRepository Standard Method Tests ====================

    @Test
    @DisplayName("Should save a new submission deadline release")
    void testSave_NewEntity() {
        // When
        SubmissionDeadlineRelease saved = repository.save(testRelease1);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCompanyCode()).isEqualTo("001");
        assertThat(saved.getInvoiceNumber()).isEqualTo("INV01");
    }

    @Test
    @DisplayName("Should update an existing submission deadline release")
    void testSave_UpdateEntity() {
        // Given
        SubmissionDeadlineRelease saved = entityManager.persist(testRelease1);
        entityManager.flush();
        Long id = saved.getId();

        // When
        saved.setOrderNumber("99999");
        SubmissionDeadlineRelease updated = repository.save(saved);
        entityManager.flush();

        // Then
        assertThat(updated.getId()).isEqualTo(id);
        assertThat(updated.getOrderNumber()).isEqualTo("99999");
    }

    @Test
    @DisplayName("Should find release by ID")
    void testFindById_Success() {
        // Given
        SubmissionDeadlineRelease saved = entityManager.persist(testRelease1);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result = repository.findById(saved.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("Should return empty when ID not found")
    void testFindById_NotFound() {
        // When
        Optional<SubmissionDeadlineRelease> result = repository.findById(99999L);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find all releases")
    void testFindAll() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.persist(testRelease2);
        entityManager.persist(testRelease3);
        entityManager.flush();

        // When
        List<SubmissionDeadlineRelease> results = repository.findAll();

        // Then
        assertThat(results).hasSize(3);
    }

    @Test
    @DisplayName("Should return empty list when no releases exist")
    void testFindAll_Empty() {
        // When
        List<SubmissionDeadlineRelease> results = repository.findAll();

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should delete release by ID")
    void testDeleteById() {
        // Given
        SubmissionDeadlineRelease saved = entityManager.persist(testRelease1);
        entityManager.flush();
        Long id = saved.getId();

        // When
        repository.deleteById(id);
        entityManager.flush();

        // Then
        Optional<SubmissionDeadlineRelease> result = repository.findById(id);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should delete release entity")
    void testDelete() {
        // Given
        SubmissionDeadlineRelease saved = entityManager.persist(testRelease1);
        entityManager.flush();
        Long id = saved.getId();

        // When
        repository.delete(saved);
        entityManager.flush();

        // Then
        Optional<SubmissionDeadlineRelease> result = repository.findById(id);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should delete all releases")
    void testDeleteAll() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.persist(testRelease2);
        entityManager.flush();

        // When
        repository.deleteAll();
        entityManager.flush();

        // Then
        List<SubmissionDeadlineRelease> results = repository.findAll();
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should check if release exists by ID")
    void testExistsById() {
        // Given
        SubmissionDeadlineRelease saved = entityManager.persist(testRelease1);
        entityManager.flush();

        // When & Then
        assertThat(repository.existsById(saved.getId())).isTrue();
        assertThat(repository.existsById(99999L)).isFalse();
    }

    @Test
    @DisplayName("Should count all releases")
    void testCount() {
        // Given
        entityManager.persist(testRelease1);
        entityManager.persist(testRelease2);
        entityManager.flush();

        // When
        long count = repository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    // ==================== Edge Cases and Business Logic Tests ====================

    @Test
    @DisplayName("Should handle multiple releases with same invoice but different dates")
    void testMultipleReleasesWithSameInvoice() {
        // Given
        SubmissionDeadlineRelease release1 = createSubmissionDeadlineRelease(
            "001", "INV01", "20240101", "12345", "1", "A", "01"
        );
        SubmissionDeadlineRelease release2 = createSubmissionDeadlineRelease(
            "001", "INV01", "20240102", "12346", "1", "A", "01"
        );

        entityManager.persist(release1);
        entityManager.persist(release2);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result1 = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate("001", "INV01", "20240101");
        Optional<SubmissionDeadlineRelease> result2 = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate("001", "INV01", "20240102");

        // Then
        assertThat(result1).isPresent();
        assertThat(result2).isPresent();
        assertThat(result1.get().getId()).isNotEqualTo(result2.get().getId());
    }

    @Test
    @DisplayName("Should handle special characters in invoice number")
    void testSpecialCharactersInInvoiceNumber() {
        // Given
        testRelease1.setInvoiceNumber("INV-01/2024");
        entityManager.persist(testRelease1);
        entityManager.flush();

        // When
        Optional<SubmissionDeadlineRelease> result = repository
            .findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                "001",
                "INV-01/2024",
                "20240101"
            );

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getInvoiceNumber()).isEqualTo("INV-01/2024");
    }

    @Test
    @DisplayName("Should persist all workorder related fields correctly")
    void testWorkorderFieldsPersistence() {
        // Given
        testRelease1.setOrderNumber("12345");
        testRelease1.setSplit("01");
        testRelease1.setArea("A");
        testRelease1.setType("W");

        // When
        SubmissionDeadlineRelease saved = repository.save(testRelease1);
        entityManager.flush();
        entityManager.clear();

        // Then
        Optional<SubmissionDeadlineRelease> result = repository.findById(saved.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getOrderNumber()).isEqualTo("12345");
        assertThat(result.get().getSplit()).isEqualTo("01");
        assertThat(result.get().getArea()).isEqualTo("A");
        assertThat(result.get().getType()).isEqualTo("W");
    }

    @Test
    @DisplayName("Should handle transaction rollback correctly")
    void testTransactionRollback() {
        // Given
        long initialCount = repository.count();

        // When - simulate transaction that would rollback
        try {
            repository.save(testRelease1);
            // Simulate error before flush
            throw new RuntimeException("Simulated error");
        } catch (RuntimeException e) {
            // Expected
        }

        // Then
        long finalCount = repository.count();
        assertThat(finalCount).isEqualTo(initialCount);
    }

    // ==================== Helper Methods ====================

    private SubmissionDeadlineRelease createSubmissionDeadlineRelease(
        String companyCode,
        String invoiceNumber,
        String invoiceDate,
        String orderNumber,
        String area,
        String type,
        String split
    ) {
        SubmissionDeadlineRelease release = new SubmissionDeadlineRelease