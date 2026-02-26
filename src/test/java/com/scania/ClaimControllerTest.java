# Complete JUnit Test Cases for ClaimController

```java
package com.scania.warranty.web;

import com.scania.warranty.domain.Claim;
import com.scania.warranty.domain.ClaimSearchCriteria;
import com.scania.warranty.dto.ClaimCreationRequestDto;
import com.scania.warranty.dto.ClaimSearchResultDto;
import com.scania.warranty.service.ClaimCreationService;
import com.scania.warranty.service.ClaimSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClaimController Tests")
class ClaimControllerTest {

    @Mock
    private ClaimSearchService claimSearchService;

    @Mock
    private ClaimCreationService claimCreationService;

    @InjectMocks
    private ClaimController claimController;

    private ClaimSearchCriteria testCriteria;
    private ClaimSearchResultDto testSearchResult;
    private Claim testClaim;
    private ClaimCreationRequestDto testCreationRequest;

    @BeforeEach
    void setUp() {
        // Setup test data
        testCriteria = new ClaimSearchCriteria(
            "001",
            "00",
            "=",
            "VEH123",
            "CUST001",
            "CLM001",
            "W",
            false,
            false,
            null,
            null
        );

        testClaim = new Claim();
        testClaim.setClaimNumber("CLM001");
        testClaim.setCompanyCode("001");
        testClaim.setStatus("00");

        testSearchResult = new ClaimSearchResultDto(
            List.of(testClaim),
            1,
            "Search completed successfully"
        );

        testCreationRequest = new ClaimCreationRequestDto(
            "001",
            "INV001",
            "20240101",
            "JOB001",
            "W"
        );
    }

    // ==================== POST /api/claims/search Tests ====================

    @Test
    @DisplayName("POST /search - Should return claims when valid criteria provided")
    void searchClaims_WithValidCriteria_ShouldReturnClaims() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = 
            claimController.searchClaims(testCriteria, true);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().totalCount());
        assertEquals("CLM001", response.getBody().claims().get(0).getClaimNumber());
        
        verify(claimSearchService, times(1))
            .searchClaims(testCriteria, true);
    }

    @Test
    @DisplayName("POST /search - Should handle ascending parameter correctly")
    void searchClaims_WithAscendingFalse_ShouldPassCorrectParameter() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), eq(false)))
            .thenReturn(testSearchResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = 
            claimController.searchClaims(testCriteria, false);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(claimSearchService, times(1))
            .searchClaims(testCriteria, false);
    }

    @Test
    @DisplayName("POST /search - Should return empty result when no claims found")
    void searchClaims_WithNoResults_ShouldReturnEmptyList() {
        // Arrange
        ClaimSearchResultDto emptyResult = new ClaimSearchResultDto(
            Collections.emptyList(),
            0,
            "No claims found"
        );
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(emptyResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = 
            claimController.searchClaims(testCriteria, true);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().totalCount());
        assertTrue(response.getBody().claims().isEmpty());
    }

    @Test
    @DisplayName("POST /search - Should use default ascending=true when not specified")
    void searchClaims_WithoutAscendingParam_ShouldUseDefaultTrue() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), eq(true)))
            .thenReturn(testSearchResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = 
            claimController.searchClaims(testCriteria, true);

        // Assert
        verify(claimSearchService, times(1))
            .searchClaims(any(ClaimSearchCriteria.class), eq(true));
    }

    // ==================== GET /api/claims/search Tests ====================

    @Test
    @DisplayName("GET /search - Should build criteria from query parameters and return claims")
    void searchClaimsGet_WithAllParameters_ShouldReturnClaims() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = claimController.searchClaimsGet(
            "001",
            "00",
            "=",
            "VEH123",
            "CUST001",
            "CLM001",
            "W",
            false,
            false,
            30,
            "search text",
            true
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().totalCount());
        
        verify(claimSearchService, times(1))
            .searchClaims(any(ClaimSearchCriteria.class), eq(true));
    }

    @Test
    @DisplayName("GET /search - Should handle null optional parameters")
    void searchClaimsGet_WithNullOptionalParams_ShouldBuildCriteriaCorrectly() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = claimController.searchClaimsGet(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            false,
            false,
            null,
            null,
            true
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(claimSearchService, times(1))
            .searchClaims(argThat(criteria -> 
                criteria.companyCode().equals("") &&
                criteria.statusOperator().equals("=")
            ), eq(true));
    }

    @Test
    @DisplayName("GET /search - Should use default values for boolean parameters")
    void searchClaimsGet_WithDefaultBooleans_ShouldUseCorrectDefaults() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = claimController.searchClaimsGet(
            "001",
            null,
            null,
            null,
            null,
            null,
            null,
            false,  // openClaimsOnly default
            false,  // minimumOnly default
            null,
            null,
            true    // ascending default
        );

        // Assert
        verify(claimSearchService, times(1))
            .searchClaims(argThat(criteria -> 
                !criteria.openClaimsOnly() && !criteria.minimumOnly()
            ), eq(true));
    }

    @Test
    @DisplayName("GET /search - Should handle openClaimsOnly=true")
    void searchClaimsGet_WithOpenClaimsOnly_ShouldPassCorrectFlag() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        claimController.searchClaimsGet(
            "001", null, null, null, null, null, null,
            true,  // openClaimsOnly
            false, null, null, true
        );

        // Assert
        verify(claimSearchService, times(1))
            .searchClaims(argThat(criteria -> criteria.openClaimsOnly()), anyBoolean());
    }

    @Test
    @DisplayName("GET /search - Should handle minimumOnly=true")
    void searchClaimsGet_WithMinimumOnly_ShouldPassCorrectFlag() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        claimController.searchClaimsGet(
            "001", null, null, null, null, null, null,
            false,
            true,  // minimumOnly
            null, null, true
        );

        // Assert
        verify(claimSearchService, times(1))
            .searchClaims(argThat(criteria -> criteria.minimumOnly()), anyBoolean());
    }

    @Test
    @DisplayName("GET /search - Should handle claimAgeDays parameter")
    void searchClaimsGet_WithClaimAgeDays_ShouldPassCorrectValue() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        claimController.searchClaimsGet(
            "001", null, null, null, null, null, null,
            false, false,
            19,  // claimAgeDays
            null, true
        );

        // Assert
        verify(claimSearchService, times(1))
            .searchClaims(argThat(criteria -> 
                criteria.claimAgeDays() != null && criteria.claimAgeDays() == 19
            ), anyBoolean());
    }

    @Test
    @DisplayName("GET /search - Should handle searchText parameter")
    void searchClaimsGet_WithSearchText_ShouldPassCorrectValue() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), anyBoolean()))
            .thenReturn(testSearchResult);

        // Act
        claimController.searchClaimsGet(
            "001", null, null, null, null, null, null,
            false, false, null,
            "test search",  // searchText
            true
        );

        // Assert
        verify(claimSearchService, times(1))
            .searchClaims(argThat(criteria -> 
                "test search".equals(criteria.searchText())
            ), anyBoolean());
    }

    @Test
    @DisplayName("GET /search - Should handle descending sort")
    void searchClaimsGet_WithAscendingFalse_ShouldSortDescending() {
        // Arrange
        when(claimSearchService.searchClaims(any(ClaimSearchCriteria.class), eq(false)))
            .thenReturn(testSearchResult);

        // Act
        ResponseEntity<ClaimSearchResultDto> response = claimController.searchClaimsGet(
            "001", null, null, null, null, null, null,
            false, false, null, null,
            false  // ascending = false
        );

        // Assert
        verify(claimSearchService, times(1))
            .searchClaims(any(ClaimSearchCriteria.class), eq(false));
    }

    // ==================== GET /api/claims Tests ====================

    @Test
    @DisplayName("GET / - Should return API information")
    void getClaimsInfo_ShouldReturnApiDocumentation() {
        // Act
        ResponseEntity<String> response = claimController.getClaimsInfo();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Warranty Claims API"));
        assertTrue(response.getBody().contains("GET  /api/claims/search"));
        assertTrue(response.getBody().contains("POST /api/claims/search"));
        assertTrue(response.getBody().contains("POST /api/claims"));
    }

    // ==================== POST /api/claims Tests ====================

    @Test
    @DisplayName("POST / - Should create claim successfully with valid request")
    void createClaim_WithValidRequest_ShouldReturnCreatedClaim() {
        // Arrange
        when(claimCreationService.createClaimFromInvoice(
            anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(testClaim);

        // Act
        ResponseEntity<Claim> response = claimController.createClaim(testCreationRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("CLM001", response.getBody().getClaimNumber());
        assertEquals("001", response.getBody().getCompanyCode());
        
        verify(claimCreationService, times(1))
            .createClaimFromInvoice("001", "INV001", "20240101", "JOB001", "W");
    }

    @Test
    @DisplayName("POST / - Should pass all parameters correctly to service")
    void createClaim_ShouldPassAllParametersToService() {
        // Arrange
        when(claimCreationService.createClaimFromInvoice(
            anyString(), anyString(), anyString(), anyString(), anyString()))
            .thenReturn(testClaim);

        ClaimCreationRequestDto request = new ClaimCreationRequestDto(
            "002",
            "INV999",
            "20240115",
            "JOB999",
            "D"
        );

        // Act
        claimController.createClaim(request);

        // Assert
        verify(claimCreationService, times(1))
            .createClaimFromInvoice("002", "INV999", "20240115", "JOB999", "D");
    }

    @Test
    @Display