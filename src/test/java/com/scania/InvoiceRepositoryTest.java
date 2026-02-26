# Complete JUnit Test Cases for InvoiceRepository

```java
package com.scania.warranty.repository;

import com.scania.warranty.domain.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for InvoiceRepository
 * Tests repository methods against an in-memory database
 */
@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Invoice Repository Tests")
class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Invoice testInvoice1;
    private Invoice testInvoice2;
    private Invoice testInvoice3;

    @BeforeEach
    void setUp() {
        // Clear repository before each test
        invoiceRepository.deleteAll();
        entityManager.clear();

        // Create test data
        testInvoice1 = createInvoice("001", "INV001", "20240101", "01", "A", "W");
        testInvoice2 = createInvoice("001", "INV002", "20240102", "01", "B", "W");
        testInvoice3 = createInvoice("002", "INV003", "20240103", "02", "A", "W");
    }

    // ==================== findByCompanyCodeAndInvoiceNumberAndInvoiceDate Tests ====================

    @Test
    @DisplayName("Should find invoice by company code, invoice number and invoice date")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_Success() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        Optional<Invoice> result = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV001", "20240101"
        );

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getCompanyCode()).isEqualTo("001");
        assertThat(result.get().getInvoiceNumber()).isEqualTo("INV001");
        assertThat(result.get().getInvoiceDate()).isEqualTo("20240101");
    }

    @Test
    @DisplayName("Should return empty when invoice not found by composite key")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_NotFound() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        Optional<Invoice> result = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "999", "NONEXISTENT", "20240101"
        );

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when company code does not match")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_WrongCompanyCode() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        Optional<Invoice> result = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "002", "INV001", "20240101"
        );

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when invoice number does not match")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_WrongInvoiceNumber() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        Optional<Invoice> result = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV999", "20240101"
        );

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when invoice date does not match")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_WrongInvoiceDate() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        Optional<Invoice> result = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV001", "20240199"
        );

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should handle null parameters gracefully")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_NullParameters() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        Optional<Invoice> result = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            null, null, null
        );

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find correct invoice when multiple invoices exist")
    void testFindByCompanyCodeAndInvoiceNumberAndInvoiceDate_MultipleInvoices() {
        // Given
        entityManager.persist(testInvoice1);
        entityManager.persist(testInvoice2);
        entityManager.persist(testInvoice3);
        entityManager.flush();

        // When
        Optional<Invoice> result = invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
            "001", "INV002", "20240102"
        );

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getInvoiceNumber()).isEqualTo("INV002");
        assertThat(result.get().getSplit()).isEqualTo("01");
    }

    // ==================== findByCompanyCodeAndSplit Tests ====================

    @Test
    @DisplayName("Should find all invoices by company code and split")
    void testFindByCompanyCodeAndSplit_Success() {
        // Given
        Invoice invoice4 = createInvoice("001", "INV004", "20240104", "01", "C", "W");
        entityManager.persist(testInvoice1);
        entityManager.persist(testInvoice2);
        entityManager.persist(testInvoice3);
        entityManager.persist(invoice4);
        entityManager.flush();

        // When
        List<Invoice> results = invoiceRepository.findByCompanyCodeAndSplit("001", "01");

        // Then
        assertThat(results).hasSize(2);
        assertThat(results).extracting(Invoice::getCompanyCode).containsOnly("001");
        assertThat(results).extracting(Invoice::getSplit).containsOnly("01");
        assertThat(results).extracting(Invoice::getInvoiceNumber)
            .containsExactlyInAnyOrder("INV001", "INV002");
    }

    @Test
    @DisplayName("Should return empty list when no invoices match company code and split")
    void testFindByCompanyCodeAndSplit_NotFound() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        List<Invoice> results = invoiceRepository.findByCompanyCodeAndSplit("999", "99");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should return empty list when company code matches but split does not")
    void testFindByCompanyCodeAndSplit_WrongSplit() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        List<Invoice> results = invoiceRepository.findByCompanyCodeAndSplit("001", "99");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should return empty list when split matches but company code does not")
    void testFindByCompanyCodeAndSplit_WrongCompanyCode() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        List<Invoice> results = invoiceRepository.findByCompanyCodeAndSplit("999", "01");

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should return single invoice when only one matches")
    void testFindByCompanyCodeAndSplit_SingleResult() {
        // Given
        entityManager.persist(testInvoice1);
        entityManager.persist(testInvoice3);
        entityManager.flush();

        // When
        List<Invoice> results = invoiceRepository.findByCompanyCodeAndSplit("002", "02");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getInvoiceNumber()).isEqualTo("INV003");
    }

    @Test
    @DisplayName("Should handle null parameters in findByCompanyCodeAndSplit")
    void testFindByCompanyCodeAndSplit_NullParameters() {
        // Given
        entityManager.persistAndFlush(testInvoice1);

        // When
        List<Invoice> results = invoiceRepository.findByCompanyCodeAndSplit(null, null);

        // Then
        assertThat(results).isEmpty();
    }

    @Test
    @DisplayName("Should return multiple invoices with same company code and split")
    void testFindByCompanyCodeAndSplit_MultipleResults() {
        // Given
        Invoice invoice4 = createInvoice("001", "INV004", "20240104", "01", "D", "W");
        Invoice invoice5 = createInvoice("001", "INV005", "20240105", "01", "E", "W");
        
        entityManager.persist(testInvoice1);
        entityManager.persist(testInvoice2);
        entityManager.persist(invoice4);
        entityManager.persist(invoice5);
        entityManager.flush();

        // When
        List<Invoice> results = invoiceRepository.findByCompanyCodeAndSplit("001", "01");

        // Then
        assertThat(results).hasSize(3);
        assertThat(results).extracting(Invoice::getInvoiceNumber)
            .containsExactlyInAnyOrder("INV001", "INV002", "INV004");
    }

    // ==================== JpaRepository Standard Methods Tests ====================

    @Test
    @DisplayName("Should save invoice successfully")
    void testSave_Success() {
        // When
        Invoice saved = invoiceRepository.save(testInvoice1);

        // Then
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getInvoiceNumber()).isEqualTo("INV001");
    }

    @Test
    @DisplayName("Should find invoice by id")
    void testFindById_Success() {
        // Given
        Invoice saved = entityManager.persistAndFlush(testInvoice1);

        // When
        Optional<Invoice> result = invoiceRepository.findById(saved.getId());

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("Should return empty when finding by non-existent id")
    void testFindById_NotFound() {
        // When
        Optional<Invoice> result = invoiceRepository.findById(999L);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find all invoices")
    void testFindAll_Success() {
        // Given
        entityManager.persist(testInvoice1);
        entityManager.persist(testInvoice2);
        entityManager.persist(testInvoice3);
        entityManager.flush();

        // When
        List<Invoice> results = invoiceRepository.findAll();

        // Then
        assertThat(results).hasSize(3);
    }

    @Test
    @DisplayName("Should delete invoice successfully")
    void testDelete_Success() {
        // Given
        Invoice saved = entityManager.persistAndFlush(testInvoice1);
        Long id = saved.getId();

        // When
        invoiceRepository.delete(saved);
        entityManager.flush();

        // Then
        Optional<Invoice> result = invoiceRepository.findById(id);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should count invoices correctly")
    void testCount_Success() {
        // Given
        entityManager.persist(testInvoice1);
        entityManager.persist(testInvoice2);
        entityManager.flush();

        // When
        long count = invoiceRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Should check if invoice exists by id")
    void testExistsById_Success() {
        // Given
        Invoice saved = entityManager.persistAndFlush(testInvoice1);

        // When
        boolean exists = invoiceRepository.existsById(saved.getId());

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when invoice does not exist")
    void testExistsById_NotFound() {
        // When
        boolean exists = invoiceRepository.existsById(999L);

        // Then
        assertThat(exists).isFalse();
    }

    // ==================== Helper Methods ====================

    /**
     * Helper method to create test Invoice objects
     */
    private Invoice createInvoice(String companyCode, String invoiceNumber, 
                                  String invoiceDate, String split, 
                                  String area, String type) {
        Invoice invoice = new Invoice();
        invoice.setCompanyCode(companyCode);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setSplit(split);
        invoice.setArea(area);
        invoice.setType(type);
        invoice.setOrderNumber("ORD001");
        invoice.setCreationDate("20240101");
        return invoice;
    }
}
```

## Additional Test Configuration Files

### application-test.properties
```properties
# Test Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console (optional for debugging)
spring.h2.console.enabled=true
```

### pom.xml Dependencies
```xml
<dependencies>
    <!-- Spring Boot Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- H2 Database for Testing -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>test</scope>
    </dependency>
    
    <!-- AssertJ for flu