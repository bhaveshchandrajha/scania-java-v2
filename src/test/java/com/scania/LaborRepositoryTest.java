# Complete JUnit Test Cases for LaborRepository

```java
package com.scania.warranty.repository;

import com.scania.warranty.domain.Labor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LaborRepository
 * Tests all JpaRepository methods and custom finder methods
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Labor Repository Tests")
class LaborRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LaborRepository laborRepository;

    private Labor labor1;
    private Labor labor2;
    private Labor labor3;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        laborRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Setup test data
        labor1 = createLabor("001", "INV001", "20240101", "Order1", "A", "1", "01");
        labor2 = createLabor("001", "INV001", "20240101", "Order2", "B", "2", "02");
        labor3 = createLabor("002", "INV002", "20240102", "Order3", "C", "3", "03");
    }

    // ==================== Custom Finder Method Tests ====================

    @Test
    @DisplayName("Should find labors by company code, invoice number and invoice date")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_Success() {
        // Given
        entityManager.persist(labor1);
        entityManager.persist(labor2);
        entityManager.persist(labor3);
        entityManager.flush();

        // When
        List<Labor> result = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV001", "20240101"
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Labor::getCompanyCode).containsOnly("001");
        assertThat(result).extracting(Labor::getInvoiceNumber).containsOnly("INV001");
        assertThat(result).extracting(Labor::getInvoiceDate).containsOnly("20240101");
    }

    @Test
    @DisplayName("Should return empty list when no matching labors found")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_NotFound() {
        // Given
        entityManager.persist(labor1);
        entityManager.flush();

        // When
        List<Labor> result = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "999", "INVXXX", "20991231"
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty list when database is empty")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_EmptyDatabase() {
        // When
        List<Labor> result = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV001", "20240101"
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_NullParameters() {
        // Given
        entityManager.persist(labor1);
        entityManager.flush();

        // When
        List<Labor> result = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            null, null, null
        );

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should be case sensitive for string parameters")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_CaseSensitive() {
        // Given
        entityManager.persist(labor1);
        entityManager.flush();

        // When
        List<Labor> result = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "inv001", "20240101" // lowercase invoice number
        );

        // Then
        assertThat(result).isEmpty();
    }

    // ==================== JpaRepository Standard Method Tests ====================

    @Test
    @DisplayName("Should save labor successfully")
    void testSave_Success() {
        // When
        Labor saved = laborRepository.save(labor1);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCompanyCode()).isEqualTo("001");
        assertThat(saved.getInvoiceNumber()).isEqualTo("INV001");
    }

    @Test
    @DisplayName("Should save and flush labor successfully")
    void testSaveAndFlush_Success() {
        // When
        Labor saved = laborRepository.saveAndFlush(labor1);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        
        // Verify it's actually in the database
        Labor found = entityManager.find(Labor.class, saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getCompanyCode()).isEqualTo("001");
    }

    @Test
    @DisplayName("Should find labor by id")
    void testFindById_Success() {
        // Given
        Labor saved = entityManager.persist(labor1);
        entityManager.flush();

        // When
        Optional<Labor> result = laborRepository.findById(saved.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getCompanyCode()).isEqualTo("001");
        assertThat(result.get().getInvoiceNumber()).isEqualTo("INV001");
    }

    @Test
    @DisplayName("Should return empty optional when labor not found by id")
    void testFindById_NotFound() {
        // When
        Optional<Labor> result = laborRepository.findById(999L);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find all labors")
    void testFindAll_Success() {
        // Given
        entityManager.persist(labor1);
        entityManager.persist(labor2);
        entityManager.persist(labor3);
        entityManager.flush();

        // When
        List<Labor> result = laborRepository.findAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("Should return empty list when no labors exist")
    void testFindAll_EmptyDatabase() {
        // When
        List<Labor> result = laborRepository.findAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should check if labor exists by id")
    void testExistsById_True() {
        // Given
        Labor saved = entityManager.persist(labor1);
        entityManager.flush();

        // When
        boolean exists = laborRepository.existsById(saved.getId());

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when labor does not exist")
    void testExistsById_False() {
        // When
        boolean exists = laborRepository.existsById(999L);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should count labors correctly")
    void testCount_Success() {
        // Given
        entityManager.persist(labor1);
        entityManager.persist(labor2);
        entityManager.flush();

        // When
        long count = laborRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should return zero count for empty database")
    void testCount_EmptyDatabase() {
        // When
        long count = laborRepository.count();

        // Then
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("Should delete labor by id")
    void testDeleteById_Success() {
        // Given
        Labor saved = entityManager.persist(labor1);
        entityManager.flush();
        Long id = saved.getId();

        // When
        laborRepository.deleteById(id);
        entityManager.flush();

        // Then
        Optional<Labor> result = laborRepository.findById(id);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should delete labor entity")
    void testDelete_Success() {
        // Given
        Labor saved = entityManager.persist(labor1);
        entityManager.flush();
        Long id = saved.getId();

        // When
        laborRepository.delete(saved);
        entityManager.flush();

        // Then
        Optional<Labor> result = laborRepository.findById(id);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should delete all labors")
    void testDeleteAll_Success() {
        // Given
        entityManager.persist(labor1);
        entityManager.persist(labor2);
        entityManager.flush();

        // When
        laborRepository.deleteAll();
        entityManager.flush();

        // Then
        long count = laborRepository.count();
        assertThat(count).isZero();
    }

    @Test
    @DisplayName("Should save all labors in batch")
    void testSaveAll_Success() {
        // Given
        List<Labor> labors = List.of(labor1, labor2, labor3);

        // When
        List<Labor> saved = laborRepository.saveAll(labors);

        // Then
        assertThat(saved).hasSize(3);
        assertThat(saved).allMatch(labor -> labor.getId() != null);
        assertThat(laborRepository.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("Should find all labors by ids")
    void testFindAllById_Success() {
        // Given
        Labor saved1 = entityManager.persist(labor1);
        Labor saved2 = entityManager.persist(labor2);
        entityManager.persist(labor3);
        entityManager.flush();

        List<Long> ids = List.of(saved1.getId(), saved2.getId());

        // When
        List<Labor> result = laborRepository.findAllById(ids);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Labor::getId).containsExactlyInAnyOrder(
            saved1.getId(), saved2.getId()
        );
    }

    @Test
    @DisplayName("Should update existing labor")
    void testUpdate_Success() {
        // Given
        Labor saved = entityManager.persist(labor1);
        entityManager.flush();
        entityManager.clear();

        // When
        saved.setInvoiceNumber("INV999");
        Labor updated = laborRepository.save(saved);
        entityManager.flush();

        // Then
        Labor found = laborRepository.findById(updated.getId()).orElseThrow();
        assertThat(found.getInvoiceNumber()).isEqualTo("INV999");
    }

    // ==================== Edge Cases and Integration Tests ====================

    @Test
    @DisplayName("Should handle multiple labors with same company code but different invoice")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_MultipleInvoices() {
        // Given
        Labor labor4 = createLabor("001", "INV003", "20240101", "Order4", "D", "4", "04");
        entityManager.persist(labor1);
        entityManager.persist(labor2);
        entityManager.persist(labor4);
        entityManager.flush();

        // When
        List<Labor> result1 = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV001", "20240101"
        );
        List<Labor> result2 = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV003", "20240101"
        );

        // Then
        assertThat(result1).hasSize(2);
        assertThat(result2).hasSize(1);
    }

    @Test
    @DisplayName("Should handle special characters in search parameters")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_SpecialCharacters() {
        // Given
        Labor specialLabor = createLabor("001", "INV-001/A", "20240101", "Order1", "A", "1", "01");
        entityManager.persist(specialLabor);
        entityManager.flush();

        // When
        List<Labor> result = laborRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV-001/A", "20240101"
        );

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getInvoiceNumber()).isEqualTo("INV-001/A");
    }

    @Test
    @DisplayName("Should maintain data integrity after multiple operations")
    void testDataIntegrity_MultipleOperations() {
        // Given
        Labor saved = laborRepository.save(labor1);
        Long id = saved.getId();

        // When - Multiple operations
        saved.setInvoiceNumber("UPDATED");
        laborRepository.save(saved);
        
        Labor found = laborRepository.findById(id).orElseThrow();
        found.setInvoiceDate("20240201");
        laborRepository.save(found);

        // Then
        Labor final_labor = laborRepository.findById(id).orElseThrow();
        assertThat(final_labor.getInvoiceNumber()).isEqualTo("UPDATED");
        assertThat(final_labor.getInvoiceDate()).isEqualTo("20240201");
        assertThat(final_labor.getCompanyCode()).isEqualTo("001"); // Unchanged field
    }

    // ==================== Helper Methods ====================

    private Labor createLabor(String companyCode, String invoiceNumber, String invoiceDate,
                             String orderNumber, String area, String type, String split) {
        Labor labor = new Labor();
        labor.setCompanyCode(companyCode);
        labor.setInvoiceNumber(invoiceNumber);
        labor.setInvoiceDate(invoiceDate);
        labor.setOrderNumber(orderNumber);
        labor.setArea(area);
        labor.setType(type);
        labor.setSplit(split);
        // Set other required fields based on your Labor entity
        return labor;
    }
}
```

## Additional Test Configuration Files

### application-test.properties
```properties
# H2 In-Memory Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org