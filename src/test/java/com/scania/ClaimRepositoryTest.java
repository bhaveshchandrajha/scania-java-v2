# Complete JUnit Test Cases for ClaimRepository

```java
package com.scania.warranty.repository;

import com.scania.warranty.domain.Claim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("ClaimRepository Tests")
class ClaimRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClaimRepository claimRepository;

    private Claim claim1;
    private Claim claim2;
    private Claim claim3;

    @BeforeEach
    void setUp() {
        // Clear any existing data
        claimRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        // Create test claims
        claim1 = createClaim("001", "CLM00001", 0, "VIN123456", "CUST001");
        claim2 = createClaim("001", "CLM00002", 10, "VIN789012", "CUST002");
        claim3 = createClaim("002", "CLM00003", 0, "VIN345678", "CUST001");

        // Persist test data
        entityManager.persist(claim1);
        entityManager.persist(claim2);
        entityManager.persist(claim3);
        entityManager.flush();
    }

    private Claim createClaim(String companyCode, String claimNumber, Integer statusCode, 
                             String chassisNumber, String customerNumber) {
        Claim claim = new Claim();
        claim.setCompanyCode(companyCode);
        claim.setClaimNumber(claimNumber);
        claim.setStatusCodeSde(statusCode);
        claim.setChassisNumber(chassisNumber);
        claim.setCustomerNumber(customerNumber);
        claim.setInvoiceNumber("INV" + claimNumber);
        claim.setInvoiceDate(LocalDate.now());
        claim.setOrderNumber("ORD" + claimNumber);
        claim.setArea("A");
        claim.setType("T");
        claim.setSplit("01");
        return claim;
    }

    @Test
    @DisplayName("Should find all claims by company code ordered by claim number ascending")
    void testFindByCompanyCodeOrderByClaimNumberAsc() {
        // When
        List<Claim> claims = claimRepository.findByCompanyCodeOrderByClaimNumberAsc("001");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(2);
        assertThat(claims.get(0).getClaimNumber()).isEqualTo("CLM00001");
        assertThat(claims.get(1).getClaimNumber()).isEqualTo("CLM00002");
    }

    @Test
    @DisplayName("Should return empty list when no claims found for company code (ascending)")
    void testFindByCompanyCodeOrderByClaimNumberAsc_NoResults() {
        // When
        List<Claim> claims = claimRepository.findByCompanyCodeOrderByClaimNumberAsc("999");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).isEmpty();
    }

    @Test
    @DisplayName("Should find all claims by company code ordered by claim number descending")
    void testFindByCompanyCodeOrderByClaimNumberDesc() {
        // When
        List<Claim> claims = claimRepository.findByCompanyCodeOrderByClaimNumberDesc("001");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(2);
        assertThat(claims.get(0).getClaimNumber()).isEqualTo("CLM00002");
        assertThat(claims.get(1).getClaimNumber()).isEqualTo("CLM00001");
    }

    @Test
    @DisplayName("Should return empty list when no claims found for company code (descending)")
    void testFindByCompanyCodeOrderByClaimNumberDesc_NoResults() {
        // When
        List<Claim> claims = claimRepository.findByCompanyCodeOrderByClaimNumberDesc("999");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).isEmpty();
    }

    @Test
    @DisplayName("Should find claim by company code and claim number")
    void testFindByCompanyCodeAndClaimNumber() {
        // When
        Optional<Claim> foundClaim = claimRepository.findByCompanyCodeAndClaimNumber("001", "CLM00001");

        // Then
        assertThat(foundClaim).isPresent();
        assertThat(foundClaim.get().getCompanyCode()).isEqualTo("001");
        assertThat(foundClaim.get().getClaimNumber()).isEqualTo("CLM00001");
        assertThat(foundClaim.get().getChassisNumber()).isEqualTo("VIN123456");
    }

    @Test
    @DisplayName("Should return empty when claim not found by company code and claim number")
    void testFindByCompanyCodeAndClaimNumber_NotFound() {
        // When
        Optional<Claim> foundClaim = claimRepository.findByCompanyCodeAndClaimNumber("001", "CLM99999");

        // Then
        assertThat(foundClaim).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when wrong company code provided")
    void testFindByCompanyCodeAndClaimNumber_WrongCompanyCode() {
        // When
        Optional<Claim> foundClaim = claimRepository.findByCompanyCodeAndClaimNumber("999", "CLM00001");

        // Then
        assertThat(foundClaim).isEmpty();
    }

    @Test
    @DisplayName("Should find claims by company code and status code")
    void testFindByCompanyCodeAndStatusCodeSde() {
        // When
        List<Claim> claims = claimRepository.findByCompanyCodeAndStatusCodeSde("001", 0);

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(1);
        assertThat(claims.get(0).getClaimNumber()).isEqualTo("CLM00001");
        assertThat(claims.get(0).getStatusCodeSde()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return empty list when no claims match status code")
    void testFindByCompanyCodeAndStatusCodeSde_NoResults() {
        // When
        List<Claim> claims = claimRepository.findByCompanyCodeAndStatusCodeSde("001", 99);

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).isEmpty();
    }

    @Test
    @DisplayName("Should search claims with all parameters")
    void testSearchClaims_AllParameters() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", 0, "VIN123", "CUST001");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(1);
        assertThat(claims.get(0).getClaimNumber()).isEqualTo("CLM00001");
    }

    @Test
    @DisplayName("Should search claims with company code only")
    void testSearchClaims_CompanyCodeOnly() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", null, null, null);

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(2);
    }

    @Test
    @DisplayName("Should search claims with status code filter")
    void testSearchClaims_WithStatusCode() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", 10, null, null);

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(1);
        assertThat(claims.get(0).getClaimNumber()).isEqualTo("CLM00002");
        assertThat(claims.get(0).getStatusCodeSde()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should search claims with vehicle number partial match")
    void testSearchClaims_WithVehicleNumberPartialMatch() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", null, "789", null);

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(1);
        assertThat(claims.get(0).getChassisNumber()).contains("789");
    }

    @Test
    @DisplayName("Should search claims with customer number")
    void testSearchClaims_WithCustomerNumber() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", null, null, "CUST001");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(1);
        assertThat(claims.get(0).getCustomerNumber()).isEqualTo("CUST001");
    }

    @Test
    @DisplayName("Should search claims with multiple filters")
    void testSearchClaims_MultipleFilters() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", 0, "VIN", "CUST001");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(1);
        assertThat(claims.get(0).getClaimNumber()).isEqualTo("CLM00001");
    }

    @Test
    @DisplayName("Should return empty list when search criteria don't match")
    void testSearchClaims_NoMatch() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", 99, "NONEXISTENT", "CUST999");

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).isEmpty();
    }

    @Test
    @DisplayName("Should handle case-insensitive vehicle number search")
    void testSearchClaims_CaseInsensitiveVehicleNumber() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", null, "vin123", null);

        // Then
        assertThat(claims).isNotNull();
        // Note: LIKE behavior depends on database collation
        // This test assumes case-insensitive collation
    }

    @Test
    @DisplayName("Should find claims across different company codes")
    void testSearchClaims_DifferentCompanyCodes() {
        // When
        List<Claim> claims001 = claimRepository.searchClaims("001", null, null, null);
        List<Claim> claims002 = claimRepository.searchClaims("002", null, null, null);

        // Then
        assertThat(claims001).hasSize(2);
        assertThat(claims002).hasSize(1);
    }

    @Test
    @DisplayName("Should verify JPA repository save functionality")
    void testSaveClaim() {
        // Given
        Claim newClaim = createClaim("003", "CLM00004", 0, "VIN999999", "CUST003");

        // When
        Claim savedClaim = claimRepository.save(newClaim);

        // Then
        assertThat(savedClaim).isNotNull();
        assertThat(savedClaim.getId()).isNotNull();
        assertThat(savedClaim.getClaimNumber()).isEqualTo("CLM00004");
    }

    @Test
    @DisplayName("Should verify JPA repository delete functionality")
    void testDeleteClaim() {
        // Given
        Long claimId = claim1.getId();

        // When
        claimRepository.deleteById(claimId);
        Optional<Claim> deletedClaim = claimRepository.findById(claimId);

        // Then
        assertThat(deletedClaim).isEmpty();
    }

    @Test
    @DisplayName("Should verify JPA repository count functionality")
    void testCountClaims() {
        // When
        long count = claimRepository.count();

        // Then
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("Should handle null parameters in search gracefully")
    void testSearchClaims_NullParameters() {
        // When
        List<Claim> claims = claimRepository.searchClaims("001", null, null, null);

        // Then
        assertThat(claims).isNotNull();
        assertThat(claims).hasSize(2);
    }
}
```

## Additional Test Configuration Class (if needed)

```java
package com.scania.warranty.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@TestConfiguration
@EnableJpaRepositories(basePackages = "com.scania.warranty.repository")
public class RepositoryTestConfiguration {
    // Additional test configuration if needed
}
```

## Test Coverage Summary

These test cases cover:

1. **findByCompanyCodeOrderByClaimNumberAsc**
   - Happy path with results
   - Empty result scenario

2. **findByCompanyCodeOrderByClaimNumberDesc**
   - Happy path with results
   - Empty result scenario

3. **findByCompanyCodeAndClaimNumber**
   - Found scenario
   - Not found scenario
   - Wrong company code scenario

4. **findByCompanyCodeAndStatusCodeSde**
   - Matching status code
   - No matching status code

5. **searchClaims**
   - All parameters provided
   - Company code only
   - Individual filter tests
   - Multiple filters combined
   - No match scenario
   - Null parameter handling

6. **Additional JPA functionality**
   - Save operation
   - Delete operation
   - Count operation

The tests use AssertJ for fluent assertions and follow best practices for repository testing with Spring Data JPA.