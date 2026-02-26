# Complete JUnit Test Cases for ClaimFailureRepository

```java
package com.scania.warranty.repository;

import com.scania.warranty.domain.ClaimFailure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for ClaimFailureRepository
 * Tests repository methods against an in-memory database
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("ClaimFailureRepository Integration Tests")
class ClaimFailureRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClaimFailureRepository claimFailureRepository;

    private ClaimFailure testFailure1;
    private ClaimFailure testFailure2;
    private ClaimFailure testFailure3;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        claimFailureRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Create test data
        testFailure1 = createClaimFailure("001", "12345678", "01", 0);
        testFailure2 = createClaimFailure("001", "12345678", "02", 0);
        testFailure3 = createClaimFailure("001", "87654321", "01", 10);
    }

    // ==================== Helper Methods ====================

    private ClaimFailure createClaimFailure(String companyCode, String claimNumber, 
                                           String failureNumber, Integer statusCode) {
        ClaimFailure failure = new ClaimFailure();
        failure.setCompanyCode(companyCode);
        failure.setClaimNumber(claimNumber);
        failure.setFailureNumber(failureNumber);
        failure.setStatusCode(statusCode);
        failure.setFailedPart("PART-" + failureNumber);
        failure.setDemandCode("DC01");
        failure.setFailureDescription("Test failure description");
        failure.setLaborHours(new BigDecimal("2.5"));
        return failure;
    }

    // ==================== JpaRepository Standard Methods Tests ====================

    @Test
    @DisplayName("Should save a new claim failure")
    void testSave_NewClaimFailure_Success() {
        // Arrange
        ClaimFailure newFailure = createClaimFailure("002", "11111111", "01", 0);

        // Act
        ClaimFailure savedFailure = claimFailureRepository.save(newFailure);
        entityManager.flush();

        // Assert
        assertNotNull(savedFailure);
        assertNotNull(savedFailure.getId());
        assertEquals("002", savedFailure.getCompanyCode());
        assertEquals("11111111", savedFailure.getClaimNumber());
        assertEquals("01", savedFailure.getFailureNumber());
    }

    @Test
    @DisplayName("Should update an existing claim failure")
    void testSave_UpdateExistingClaimFailure_Success() {
        // Arrange
        ClaimFailure saved = entityManager.persistAndFlush(testFailure1);
        Long id = saved.getId();

        // Act
        saved.setStatusCode(10);
        saved.setFailureDescription("Updated description");
        ClaimFailure updated = claimFailureRepository.save(saved);
        entityManager.flush();
        entityManager.clear();

        // Assert
        ClaimFailure found = claimFailureRepository.findById(id).orElse(null);
        assertNotNull(found);
        assertEquals(10, found.getStatusCode());
        assertEquals("Updated description", found.getFailureDescription());
    }

    @Test
    @DisplayName("Should find claim failure by ID")
    void testFindById_ExistingId_ReturnsClaimFailure() {
        // Arrange
        ClaimFailure saved = entityManager.persistAndFlush(testFailure1);
        Long id = saved.getId();
        entityManager.clear();

        // Act
        Optional<ClaimFailure> result = claimFailureRepository.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("001", result.get().getCompanyCode());
        assertEquals("12345678", result.get().getClaimNumber());
    }

    @Test
    @DisplayName("Should return empty when finding by non-existent ID")
    void testFindById_NonExistentId_ReturnsEmpty() {
        // Act
        Optional<ClaimFailure> result = claimFailureRepository.findById(99999L);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should find all claim failures")
    void testFindAll_ReturnsAllClaimFailures() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.persist(testFailure2);
        entityManager.persist(testFailure3);
        entityManager.flush();

        // Act
        List<ClaimFailure> result = claimFailureRepository.findAll();

        // Assert
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("Should delete claim failure by ID")
    void testDeleteById_ExistingId_DeletesSuccessfully() {
        // Arrange
        ClaimFailure saved = entityManager.persistAndFlush(testFailure1);
        Long id = saved.getId();
        entityManager.clear();

        // Act
        claimFailureRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<ClaimFailure> result = claimFailureRepository.findById(id);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should delete claim failure entity")
    void testDelete_ExistingEntity_DeletesSuccessfully() {
        // Arrange
        ClaimFailure saved = entityManager.persistAndFlush(testFailure1);
        Long id = saved.getId();
        entityManager.clear();

        // Act
        claimFailureRepository.delete(saved);
        entityManager.flush();

        // Assert
        Optional<ClaimFailure> result = claimFailureRepository.findById(id);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should check if claim failure exists by ID")
    void testExistsById_ExistingId_ReturnsTrue() {
        // Arrange
        ClaimFailure saved = entityManager.persistAndFlush(testFailure1);
        Long id = saved.getId();

        // Act
        boolean exists = claimFailureRepository.existsById(id);

        // Assert
        assertTrue(exists);
    }

    @Test
    @DisplayName("Should return false when checking non-existent ID")
    void testExistsById_NonExistentId_ReturnsFalse() {
        // Act
        boolean exists = claimFailureRepository.existsById(99999L);

        // Assert
        assertFalse(exists);
    }

    @Test
    @DisplayName("Should count all claim failures")
    void testCount_ReturnsCorrectCount() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.persist(testFailure2);
        entityManager.persist(testFailure3);
        entityManager.flush();

        // Act
        long count = claimFailureRepository.count();

        // Assert
        assertEquals(3, count);
    }

    // ==================== Custom Query Methods Tests ====================

    @Test
    @DisplayName("Should find failures by company code and claim number")
    void testFindByCompanyCodeAndClaimNumber_ExistingClaim_ReturnsFailures() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.persist(testFailure2);
        entityManager.persist(testFailure3);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumber("001", "12345678");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result)
            .extracting(ClaimFailure::getFailureNumber)
            .containsExactlyInAnyOrder("01", "02");
        assertThat(result)
            .allMatch(f -> f.getCompanyCode().equals("001"))
            .allMatch(f -> f.getClaimNumber().equals("12345678"));
    }

    @Test
    @DisplayName("Should return empty list when no failures found for claim")
    void testFindByCompanyCodeAndClaimNumber_NonExistentClaim_ReturnsEmptyList() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.flush();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumber("999", "99999999");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find failures by company code, claim number and status code")
    void testFindByCompanyCodeAndClaimNumberAndStatusCode_ExistingClaim_ReturnsFilteredFailures() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.persist(testFailure2);
        entityManager.persist(testFailure3);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumberAndStatusCode("001", "12345678", 0);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result)
            .allMatch(f -> f.getCompanyCode().equals("001"))
            .allMatch(f -> f.getClaimNumber().equals("12345678"))
            .allMatch(f -> f.getStatusCode().equals(0));
    }

    @Test
    @DisplayName("Should return empty list when no failures match status code")
    void testFindByCompanyCodeAndClaimNumberAndStatusCode_NoMatchingStatus_ReturnsEmptyList() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.persist(testFailure2);
        entityManager.flush();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumberAndStatusCode("001", "12345678", 99);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find single failure with specific status code")
    void testFindByCompanyCodeAndClaimNumberAndStatusCode_SingleMatch_ReturnsSingleFailure() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.persist(testFailure2);
        entityManager.persist(testFailure3);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumberAndStatusCode("001", "87654321", 10);

        // Assert
        assertThat(result).hasSize(1);
        ClaimFailure failure = result.get(0);
        assertEquals("001", failure.getCompanyCode());
        assertEquals("87654321", failure.getClaimNumber());
        assertEquals(10, failure.getStatusCode());
    }

    // ==================== Edge Cases and Boundary Tests ====================

    @Test
    @DisplayName("Should handle null company code gracefully")
    void testFindByCompanyCodeAndClaimNumber_NullCompanyCode_ReturnsEmptyList() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.flush();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumber(null, "12345678");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should handle null claim number gracefully")
    void testFindByCompanyCodeAndClaimNumber_NullClaimNumber_ReturnsEmptyList() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.flush();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumber("001", null);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should handle null status code gracefully")
    void testFindByCompanyCodeAndClaimNumberAndStatusCode_NullStatusCode_ReturnsEmptyList() {
        // Arrange
        entityManager.persist(testFailure1);
        entityManager.flush();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumberAndStatusCode("001", "12345678", null);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should save and retrieve failure with maximum allowed failures (9)")
    void testSave_MaximumFailures_Success() {
        // Arrange - Create 9 failures for same claim
        for (int i = 1; i <= 9; i++) {
            ClaimFailure failure = createClaimFailure(
                "001", 
                "12345678", 
                String.format("%02d", i), 
                0
            );
            entityManager.persist(failure);
        }
        entityManager.flush();
        entityManager.clear();

        // Act
        List<ClaimFailure> result = claimFailureRepository
            .findByCompanyCodeAndClaimNumber("001", "12345678");

        // Assert
        assertThat(result).hasSize(9);
    }

    @Test
    @DisplayName("Should handle empty database")
    void testFindAll_EmptyDatabase_ReturnsEmptyList() {
        // Act
        List<ClaimFailure> result = claimFailureRepository.findAll();

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should save failure with all mandatory fields")
    void testSave_AllMandatoryFields_Success() {
        // Arrange
        ClaimFailure failure = new ClaimFailure();
        failure.setCompanyCode("001");
        failure.setClaimNumber("12345678");
        failure.setFailureNumber("01");
        failure.setStatusCode(0);
        failure.setFailedPart("PART001");
        failure.setDemandCode("DC01");
        failure.setFailureDescription("Complete failure description");
        failure.setLaborHours(new BigDecimal("5.00"));
        failure.setMaterialCost(new BigDecimal("150.00"));
        failure.setLaborCost(new BigDecimal("250.00"));

        // Act
        ClaimFailure saved = claimFailureRepository.save(failure);
        entityManager.flush();
        entityManager.clear();

        // Assert
        ClaimFailure found = claimFailureRepository.findById(saved.getId()).