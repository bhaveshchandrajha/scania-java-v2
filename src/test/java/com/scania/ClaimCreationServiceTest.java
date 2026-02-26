Here are comprehensive JUnit test cases for the `ClaimCreationService` class:

```java
package com.scania.warranty.service;

import com.scania.warranty.domain.*;
import com.scania.warranty.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClaimCreationService Tests")
class ClaimCreationServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private LaborRepository laborRepository;

    @Mock
    private ExternalServiceRepository externalServiceRepository;

    @InjectMocks
    private ClaimCreationService claimCreationService;

    private Invoice testInvoice;
    private String companyCode;
    private String invoiceNumber;
    private String invoiceDate;
    private String jobNumber;
    private String workshopType;

    @BeforeEach
    void setUp() {
        companyCode = "001";
        invoiceNumber = "12345";
        invoiceDate = "20240115";
        jobNumber = "JOB001";
        workshopType = "1";

        testInvoice = new Invoice();
        testInvoice.setCompanyCode(companyCode);
        testInvoice.setInvoiceNumber(invoiceNumber);
        testInvoice.setInvoiceDate(invoiceDate);
        testInvoice.setVehicleNumber("ABC1234567");
        testInvoice.setLicensePlate("XYZ123");
        testInvoice.setRegistrationDate("20230101");
        testInvoice.setAcceptanceDate("20240110");
        testInvoice.setJobDate("20240112");
        testInvoice.setKilometers("50000");
        testInvoice.setCustomerNumber("CUST001");
        testInvoice.setName("Test Customer");
        testInvoice.setArea("A");
        testInvoice.setSplit("01");
        testInvoice.setWorkshopType("1");
    }

    @Nested
    @DisplayName("createClaimFromInvoice - Success Scenarios")
    class SuccessScenarios {

        @Test
        @DisplayName("Should successfully create claim when invoice exists and no duplicate claim")
        void shouldCreateClaimSuccessfully() {
            // Arrange
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            
            Claim savedClaim = new Claim();
            savedClaim.setClaimNumber("00000001");
            when(claimRepository.save(any(Claim.class))).thenReturn(savedClaim);

            // Act
            Claim result = claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType);

            // Assert
            assertNotNull(result);
            assertEquals("00000001", result.getClaimNumber());
            verify(invoiceRepository).findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate);
            verify(claimRepository).save(any(Claim.class));
        }

        @Test
        @DisplayName("Should set correct claim properties from invoice")
        void shouldSetCorrectClaimProperties() {
            // Arrange
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            
            when(claimRepository.save(any(Claim.class))).thenAnswer(invocation -> {
                Claim claim = invocation.getArgument(0);
                
                // Verify all properties are set correctly
                assertEquals(companyCode, claim.getCompanyCode());
                assertEquals(invoiceNumber, claim.getInvoiceNumber());
                assertEquals(invoiceDate, claim.getInvoiceDate());
                assertEquals(jobNumber, claim.getJobNumber());
                assertEquals(workshopType, claim.getWorkshopType());
                assertEquals("1234567", claim.getChassisNumber());
                assertEquals("XYZ123", claim.getLicensePlate());
                assertEquals(20230101, claim.getRegistrationDate());
                assertEquals(50, claim.getMileage());
                assertEquals("CUST001", claim.getCustomerNumber());
                assertEquals("Test Customer", claim.getCustomerName());
                assertEquals("", claim.getClaimNumberSde());
                assertEquals(ClaimStatus.CREATED.getCode(), claim.getStatusCodeSde());
                assertEquals(0, claim.getNumberOfFailures());
                assertEquals("A", claim.getArea());
                assertEquals("JOB0011A01", claim.getJobNumberSdps());
                
                return claim;
            });

            // Act
            claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType);

            // Assert
            verify(claimRepository).save(any(Claim.class));
        }

        @Test
        @DisplayName("Should use acceptance date for workshop type 1")
        void shouldUseAcceptanceDateForWorkshopType1() {
            // Arrange
            testInvoice.setWorkshopType("1");
            testInvoice.setAcceptanceDate("20240110");
            testInvoice.setJobDate("20240112");
            
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            
            when(claimRepository.save(any(Claim.class))).thenAnswer(invocation -> {
                Claim claim = invocation.getArgument(0);
                assertEquals(20240110, claim.getRepairDate());
                return claim;
            });

            // Act
            claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, "1");

            // Assert
            verify(claimRepository).save(any(Claim.class));
        }

        @Test
        @DisplayName("Should use job date for workshop type other than 1")
        void shouldUseJobDateForOtherWorkshopTypes() {
            // Arrange
            testInvoice.setWorkshopType("2");
            testInvoice.setAcceptanceDate("20240110");
            testInvoice.setJobDate("20240112");
            
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            
            when(claimRepository.save(any(Claim.class))).thenAnswer(invocation -> {
                Claim claim = invocation.getArgument(0);
                assertEquals(20240112, claim.getRepairDate());
                return claim;
            });

            // Act
            claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, "2");

            // Assert
            verify(claimRepository).save(any(Claim.class));
        }
    }

    @Nested
    @DisplayName("createClaimFromInvoice - Failure Scenarios")
    class FailureScenarios {

        @Test
        @DisplayName("Should throw IllegalArgumentException when invoice not found")
        void shouldThrowExceptionWhenInvoiceNotFound() {
            // Arrange
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.empty());

            // Act & Assert
            IllegalArgumentException exception = assertThrows(
                    IllegalArgumentException.class,
                    () -> claimCreationService.createClaimFromInvoice(
                            companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType)
            );

            assertEquals("Invoice not found", exception.getMessage());
            verify(invoiceRepository).findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate);
            verify(claimRepository, never()).save(any(Claim.class));
        }

        @Test
        @DisplayName("Should throw IllegalStateException when claim already exists")
        void shouldThrowExceptionWhenClaimAlreadyExists() {
            // Arrange
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            Claim existingClaim = new Claim();
            existingClaim.setStatusCodeSde(ClaimStatus.CREATED.getCode());
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.of(existingClaim));

            // Act & Assert
            IllegalStateException exception = assertThrows(
                    IllegalStateException.class,
                    () -> claimCreationService.createClaimFromInvoice(
                            companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType)
            );

            assertEquals("Claim already exists for this invoice", exception.getMessage());
            verify(claimRepository, never()).save(any(Claim.class));
        }

        @Test
        @DisplayName("Should allow creation when existing claim is excluded")
        void shouldAllowCreationWhenExistingClaimIsExcluded() {
            // Arrange
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            Claim existingClaim = new Claim();
            existingClaim.setStatusCodeSde(ClaimStatus.EXCLUDED.getCode());
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.of(existingClaim));
            
            Claim savedClaim = new Claim();
            when(claimRepository.save(any(Claim.class))).thenReturn(savedClaim);

            // Act
            Claim result = claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType);

            // Assert
            assertNotNull(result);
            verify(claimRepository).save(any(Claim.class));
        }
    }

    @Nested
    @DisplayName("Helper Methods - extractChassisNumber")
    class ExtractChassisNumberTests {

        @Test
        @DisplayName("Should extract last 7 characters from vehicle number")
        void shouldExtractLast7Characters() {
            // Arrange
            testInvoice.setVehicleNumber("ABC1234567");
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            
            when(claimRepository.save(any(Claim.class))).thenAnswer(invocation -> {
                Claim claim = invocation.getArgument(0);
                assertEquals("1234567", claim.getChassisNumber());
                return claim;
            });

            // Act
            claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType);

            // Assert
            verify(claimRepository).save(any(Claim.class));
        }

        @Test
        @DisplayName("Should return empty string when vehicle number is null")
        void shouldReturnEmptyStringWhenVehicleNumberIsNull() {
            // Arrange
            testInvoice.setVehicleNumber(null);
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            
            when(claimRepository.save(any(Claim.class))).thenAnswer(invocation -> {
                Claim claim = invocation.getArgument(0);
                assertEquals("", claim.getChassisNumber());
                return claim;
            });

            // Act
            claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType);

            // Assert
            verify(claimRepository).save(any(Claim.class));
        }

        @Test
        @DisplayName("Should return empty string when vehicle number is too short")
        void shouldReturnEmptyStringWhenVehicleNumberIsTooShort() {
            // Arrange
            testInvoice.setVehicleNumber("ABC12");
            when(invoiceRepository.findByCompanyCodeAndInvoiceNumberAndInvoiceDate(
                    companyCode, invoiceNumber, invoiceDate))
                    .thenReturn(Optional.of(testInvoice));
            
            when(claimRepository.findByCompanyCodeAndClaimNumber(anyString(), anyString()))
                    .thenReturn(Optional.empty());
            
            when(claimRepository.save(any(Claim.class))).thenAnswer(invocation -> {
                Claim claim = invocation.getArgument(0);
                assertEquals("", claim.getChassisNumber());
                return claim;
            });

            // Act
            claimCreationService.createClaimFromInvoice(
                    companyCode, invoiceNumber, invoiceDate, jobNumber, workshopType);

            // Assert
            verify(claimRepository).save(any(Claim.class));
        }
    }

    @Nested
    @DisplayName("Helper Methods - parseDate")
    class ParseDateTests {

        @Test
        @DisplayName("Should parse valid date string to integer")
        void shouldParseValidDateString() {
            // Arrange
            testInvoice.setRegistrationDate("20230101");
            when(invoiceRepository.findByCompanyCodeAndIn