# Complete JUnit Test Cases for ClaimSearchService

```java
package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.dto.ClaimListItemDto;
import com.scania.warranty.dto.ClaimSearchResultDto;
import com.scania.warranty.repository.ClaimFailureRepository;
import com.scania.warranty.repository.ClaimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClaimSearchService Tests")
class ClaimSearchServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private ClaimFailureRepository claimFailureRepository;

    @InjectMocks
    private ClaimSearchService claimSearchService;

    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String COMPANY_CODE = "001";
    private static final String INVOICE_NUMBER = "INV01";
    private static final String JOB_NUMBER = "JOB01";

    private Claim createTestClaim() {
        Claim claim = new Claim();
        claim.setCompanyCode(COMPANY_CODE);
        claim.setInvoiceNumber(INVOICE_NUMBER);
        claim.setInvoiceDate("20240115");
        claim.setJobNumber(JOB_NUMBER);
        claim.setWorkshopType("A");
        claim.setClaimNumber("CLM001");
        claim.setChassisNumber("CHASSIS123");
        claim.setLicensePlate("ABC123");
        claim.setRepairDate(Integer.parseInt(LocalDate.now().minusDays(5).format(ISO_DATE_FORMATTER)));
        claim.setMileage(50000);
        claim.setCustomerNumber("CUST001");
        claim.setCustomerName("Test Customer");
        claim.setClaimNumberSde("SDE001");
        claim.setStatusCodeSde(10);
        claim.setNumberOfFailures(2);
        return claim;
    }

    private ClaimFailure createTestFailure(String companyCode, String claimNumber, Integer statusCode) {
        ClaimFailure failure = new ClaimFailure();
        failure.setCompanyCode(companyCode);
        failure.setClaimNumber(claimNumber);
        failure.setStatusCode(statusCode);
        return failure;
    }

    private ClaimSearchCriteria createBasicCriteria() {
        return new ClaimSearchCriteria(
            COMPANY_CODE,
            null, null, null, null, null, null, null, null, null, null, null, false, false
        );
    }

    @Nested
    @DisplayName("Search Claims Tests")
    class SearchClaimsTests {

        @Test
        @DisplayName("Should return empty result when company code is null")
        void shouldReturnEmptyResultWhenCompanyCodeIsNull() {
            // Arrange
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, null, null, null, null, null, false, false
            );

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.claims()).isEmpty();
            assertThat(result.totalCount()).isZero();
            verify(claimRepository, never()).findByCompanyCodeOrderByClaimNumberAsc(anyString());
        }

        @Test
        @DisplayName("Should return empty result when company code is blank")
        void shouldReturnEmptyResultWhenCompanyCodeIsBlank() {
            // Arrange
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "   ", null, null, null, null, null, null, null, null, null, null, null, false, false
            );

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.claims()).isEmpty();
            assertThat(result.totalCount()).isZero();
        }

        @Test
        @DisplayName("Should return claims in ascending order")
        void shouldReturnClaimsInAscendingOrder() {
            // Arrange
            Claim claim1 = createTestClaim();
            Claim claim2 = createTestClaim();
            claim2.setClaimNumber("CLM002");
            
            List<Claim> claims = Arrays.asList(claim1, claim2);
            ClaimSearchCriteria criteria = createBasicCriteria();
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(claims);
            when(claimFailureRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                .thenReturn(List.of());

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result.claims()).hasSize(2);
            verify(claimRepository).findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE);
            verify(claimRepository, never()).findByCompanyCodeOrderByClaimNumberDesc(anyString());
        }

        @Test
        @DisplayName("Should return claims in descending order")
        void shouldReturnClaimsInDescendingOrder() {
            // Arrange
            Claim claim = createTestClaim();
            List<Claim> claims = List.of(claim);
            ClaimSearchCriteria criteria = createBasicCriteria();
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberDesc(COMPANY_CODE))
                .thenReturn(claims);
            when(claimFailureRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                .thenReturn(List.of());

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, false);

            // Assert
            assertThat(result.claims()).hasSize(1);
            verify(claimRepository).findByCompanyCodeOrderByClaimNumberDesc(COMPANY_CODE);
            verify(claimRepository, never()).findByCompanyCodeOrderByClaimNumberAsc(anyString());
        }

        @Test
        @DisplayName("Should handle null repository result")
        void shouldHandleNullRepositoryResult() {
            // Arrange
            ClaimSearchCriteria criteria = createBasicCriteria();
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(null);

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.claims()).isEmpty();
            assertThat(result.totalCount()).isZero();
        }

        @Test
        @DisplayName("Should handle repository exception")
        void shouldHandleRepositoryException() {
            // Arrange
            ClaimSearchCriteria criteria = createBasicCriteria();
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenThrow(new RuntimeException("Database error"));

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result).isNotNull();
            assertThat(result.claims()).isEmpty();
            assertThat(result.totalCount()).isZero();
        }
    }

    @Nested
    @DisplayName("Status Filter Tests")
    class StatusFilterTests {

        @ParameterizedTest
        @CsvSource({
            "10, =, 10, true",
            "10, =, 20, false",
            "15, >, 10, true",
            "5, >, 10, false",
            "5, <, 10, true",
            "15, <, 10, false",
            "10, *, 10, true",
            "10, invalid, 10, true"
        })
        @DisplayName("Should filter by status with different operators")
        void shouldFilterByStatusWithOperators(int claimStatus, String operator, int filterStatus, boolean expected) {
            // Arrange
            Claim claim = createTestClaim();
            claim.setStatusCodeSde(claimStatus);
            
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                COMPANY_CODE, String.valueOf(filterStatus), operator, null, null, null, null, null, null, null, null, null, false, false
            );
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(List.of(claim));
            when(claimFailureRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                .thenReturn(List.of());

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            if (expected) {
                assertThat(result.claims()).hasSize(1);
            } else {
                assertThat(result.claims()).isEmpty();
            }
        }

        @Test
        @DisplayName("Should exclude claims with null status when filtering")
        void shouldExcludeClaimsWithNullStatus() {
            // Arrange
            Claim claim = createTestClaim();
            claim.setStatusCodeSde(null);
            
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                COMPANY_CODE, "10", "=", null, null, null, null, null, null, null, null, null, false, false
            );
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(List.of(claim));

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result.claims()).isEmpty();
        }
    }

    @Nested
    @DisplayName("Claim Age Filter Tests")
    class ClaimAgeFilterTests {

        @Test
        @DisplayName("Should include claims within age limit")
        void shouldIncludeClaimsWithinAgeLimit() {
            // Arrange
            Claim claim = createTestClaim();
            claim.setRepairDate(Integer.parseInt(LocalDate.now().minusDays(5).format(ISO_DATE_FORMATTER)));
            
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                COMPANY_CODE, null, null, 10, null, null, null, null, null, null, null, null, false, false
            );
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(List.of(claim));
            when(claimFailureRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                .thenReturn(List.of());

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result.claims()).hasSize(1);
        }

        @Test
        @DisplayName("Should exclude claims exceeding age limit")
        void shouldExcludeClaimsExceedingAgeLimit() {
            // Arrange
            Claim claim = createTestClaim();
            claim.setRepairDate(Integer.parseInt(LocalDate.now().minusDays(25).format(ISO_DATE_FORMATTER)));
            
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                COMPANY_CODE, null, null, 20, null, null, null, null, null, null, null, null, false, false
            );
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(List.of(claim));

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result.claims()).isEmpty();
        }

        @Test
        @DisplayName("Should handle invalid repair date gracefully")
        void shouldHandleInvalidRepairDate() {
            // Arrange
            Claim claim = createTestClaim();
            claim.setRepairDate(99999999); // Invalid date
            
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                COMPANY_CODE, null, null, 10, null, null, null, null, null, null, null, null, false, false
            );
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(List.of(claim));
            when(claimFailureRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                .thenReturn(List.of());

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert - Should include claim when date parsing fails
            assertThat(result.claims()).hasSize(1);
        }

        @Test
        @DisplayName("Should skip age filter when claimAgeDays is null")
        void shouldSkipAgeFilterWhenNull() {
            // Arrange
            Claim claim = createTestClaim();
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                COMPANY_CODE, null, null, null, null, null, null, null, null, null, null, null, false, false
            );
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(List.of(claim));
            when(claimFailureRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                .thenReturn(List.of());

            // Act
            ClaimSearchResultDto result = claimSearchService.searchClaims(criteria, true);

            // Assert
            assertThat(result.claims()).hasSize(1);
        }

        @Test
        @DisplayName("Should skip age filter when claimAgeDays is zero")
        void shouldSkipAgeFilterWhenZero() {
            // Arrange
            Claim claim = createTestClaim();
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                COMPANY_CODE, null, null, 0, null, null, null, null, null, null, null, null, false, false
            );
            
            when(claimRepository.findByCompanyCodeOrderByClaimNumberAsc(COMPANY_CODE))
                .thenReturn(List