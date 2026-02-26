# Complete JUnit Test Cases for ClaimListItemDto

```java
package com.scania.warranty.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimListItemDto Tests")
class ClaimListItemDtoTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create ClaimListItemDto with all valid fields")
        void shouldCreateDtoWithAllValidFields() {
            // Arrange & Act
            ClaimListItemDto dto = new ClaimListItemDto(
                "001",
                "12345",
                "20240115",
                "JOB01",
                "W",
                "CLM00001",
                "YS2R4X20005399401",
                "ABC123",
                "20240115",
                50000,
                "CUST1",
                "Customer Name",
                "SDE12345",
                10,
                "SENT",
                3,
                "G"
            );

            // Assert
            assertNotNull(dto);
            assertEquals("001", dto.companyCode());
            assertEquals("12345", dto.invoiceNumber());
            assertEquals("20240115", dto.invoiceDate());
            assertEquals("JOB01", dto.jobNumber());
            assertEquals("W", dto.workshopType());
            assertEquals("CLM00001", dto.claimNumber());
            assertEquals("YS2R4X20005399401", dto.chassisNumber());
            assertEquals("ABC123", dto.licensePlate());
            assertEquals("20240115", dto.repairDate());
            assertEquals(50000, dto.mileage());
            assertEquals("CUST1", dto.customerNumber());
            assertEquals("Customer Name", dto.customerName());
            assertEquals("SDE12345", dto.claimNumberSde());
            assertEquals(10, dto.statusCode());
            assertEquals("SENT", dto.statusDescription());
            assertEquals(3, dto.numberOfFailures());
            assertEquals("G", dto.colorIndicator());
        }

        @Test
        @DisplayName("Should create ClaimListItemDto with null values")
        void shouldCreateDtoWithNullValues() {
            // Arrange & Act
            ClaimListItemDto dto = new ClaimListItemDto(
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null
            );

            // Assert
            assertNotNull(dto);
            assertNull(dto.companyCode());
            assertNull(dto.invoiceNumber());
            assertNull(dto.invoiceDate());
            assertNull(dto.jobNumber());
            assertNull(dto.workshopType());
            assertNull(dto.claimNumber());
            assertNull(dto.chassisNumber());
            assertNull(dto.licensePlate());
            assertNull(dto.repairDate());
            assertNull(dto.mileage());
            assertNull(dto.customerNumber());
            assertNull(dto.customerName());
            assertNull(dto.claimNumberSde());
            assertNull(dto.statusCode());
            assertNull(dto.statusDescription());
            assertNull(dto.numberOfFailures());
            assertNull(dto.colorIndicator());
        }

        @Test
        @DisplayName("Should create ClaimListItemDto with empty strings")
        void shouldCreateDtoWithEmptyStrings() {
            // Arrange & Act
            ClaimListItemDto dto = new ClaimListItemDto(
                "", "", "", "", "", "", "", "",
                "", 0, "", "", "", 0, "", 0, ""
            );

            // Assert
            assertNotNull(dto);
            assertEquals("", dto.companyCode());
            assertEquals("", dto.invoiceNumber());
            assertEquals(0, dto.mileage());
            assertEquals(0, dto.statusCode());
            assertEquals(0, dto.numberOfFailures());
        }

        @Test
        @DisplayName("Should create ClaimListItemDto with minimum integer values")
        void shouldCreateDtoWithMinimumIntegerValues() {
            // Arrange & Act
            ClaimListItemDto dto = new ClaimListItemDto(
                "001", "12345", "20240115", "JOB01", "W", "CLM00001",
                "YS2R4X20005399401", "ABC123", "20240115",
                Integer.MIN_VALUE,
                "CUST1", "Customer Name", "SDE12345",
                Integer.MIN_VALUE,
                "SENT",
                Integer.MIN_VALUE,
                "G"
            );

            // Assert
            assertEquals(Integer.MIN_VALUE, dto.mileage());
            assertEquals(Integer.MIN_VALUE, dto.statusCode());
            assertEquals(Integer.MIN_VALUE, dto.numberOfFailures());
        }

        @Test
        @DisplayName("Should create ClaimListItemDto with maximum integer values")
        void shouldCreateDtoWithMaximumIntegerValues() {
            // Arrange & Act
            ClaimListItemDto dto = new ClaimListItemDto(
                "001", "12345", "20240115", "JOB01", "W", "CLM00001",
                "YS2R4X20005399401", "ABC123", "20240115",
                Integer.MAX_VALUE,
                "CUST1", "Customer Name", "SDE12345",
                Integer.MAX_VALUE,
                "SENT",
                Integer.MAX_VALUE,
                "G"
            );

            // Assert
            assertEquals(Integer.MAX_VALUE, dto.mileage());
            assertEquals(Integer.MAX_VALUE, dto.statusCode());
            assertEquals(Integer.MAX_VALUE, dto.numberOfFailures());
        }
    }

    @Nested
    @DisplayName("Getter Tests")
    class GetterTests {

        @Test
        @DisplayName("Should return correct companyCode")
        void shouldReturnCorrectCompanyCode() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("001", dto.companyCode());
        }

        @Test
        @DisplayName("Should return correct invoiceNumber")
        void shouldReturnCorrectInvoiceNumber() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("12345", dto.invoiceNumber());
        }

        @Test
        @DisplayName("Should return correct invoiceDate")
        void shouldReturnCorrectInvoiceDate() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("20240115", dto.invoiceDate());
        }

        @Test
        @DisplayName("Should return correct jobNumber")
        void shouldReturnCorrectJobNumber() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("JOB01", dto.jobNumber());
        }

        @Test
        @DisplayName("Should return correct workshopType")
        void shouldReturnCorrectWorkshopType() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("W", dto.workshopType());
        }

        @Test
        @DisplayName("Should return correct claimNumber")
        void shouldReturnCorrectClaimNumber() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("CLM00001", dto.claimNumber());
        }

        @Test
        @DisplayName("Should return correct chassisNumber")
        void shouldReturnCorrectChassisNumber() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("YS2R4X20005399401", dto.chassisNumber());
        }

        @Test
        @DisplayName("Should return correct licensePlate")
        void shouldReturnCorrectLicensePlate() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("ABC123", dto.licensePlate());
        }

        @Test
        @DisplayName("Should return correct repairDate")
        void shouldReturnCorrectRepairDate() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("20240115", dto.repairDate());
        }

        @Test
        @DisplayName("Should return correct mileage")
        void shouldReturnCorrectMileage() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals(50000, dto.mileage());
        }

        @Test
        @DisplayName("Should return correct customerNumber")
        void shouldReturnCorrectCustomerNumber() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("CUST1", dto.customerNumber());
        }

        @Test
        @DisplayName("Should return correct customerName")
        void shouldReturnCorrectCustomerName() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("Customer Name", dto.customerName());
        }

        @Test
        @DisplayName("Should return correct claimNumberSde")
        void shouldReturnCorrectClaimNumberSde() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("SDE12345", dto.claimNumberSde());
        }

        @Test
        @DisplayName("Should return correct statusCode")
        void shouldReturnCorrectStatusCode() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals(10, dto.statusCode());
        }

        @Test
        @DisplayName("Should return correct statusDescription")
        void shouldReturnCorrectStatusDescription() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("SENT", dto.statusDescription());
        }

        @Test
        @DisplayName("Should return correct numberOfFailures")
        void shouldReturnCorrectNumberOfFailures() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals(3, dto.numberOfFailures());
        }

        @Test
        @DisplayName("Should return correct colorIndicator")
        void shouldReturnCorrectColorIndicator() {
            ClaimListItemDto dto = createSampleDto();
            assertEquals("G", dto.colorIndicator());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should be equal when all fields are the same")
        void shouldBeEqualWhenAllFieldsAreTheSame() {
            // Arrange
            ClaimListItemDto dto1 = createSampleDto();
            ClaimListItemDto dto2 = createSampleDto();

            // Assert
            assertEquals(dto1, dto2);
            assertEquals(dto1.hashCode(), dto2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when companyCode differs")
        void shouldNotBeEqualWhenCompanyCodeDiffers() {
            // Arrange
            ClaimListItemDto dto1 = createSampleDto();
            ClaimListItemDto dto2 = new ClaimListItemDto(
                "002", "12345", "20240115", "JOB01", "W", "CLM00001",
                "YS2R4X20005399401", "ABC123", "20240115", 50000,
                "CUST1", "Customer Name", "SDE12345", 10, "SENT", 3, "G"
            );

            // Assert
            assertNotEquals(dto1, dto2);
            assertNotEquals(dto1.hashCode(), dto2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when invoiceNumber differs")
        void shouldNotBeEqualWhenInvoiceNumberDiffers() {
            // Arrange
            ClaimListItemDto dto1 = createSampleDto();
            ClaimListItemDto dto2 = new ClaimListItemDto(
                "001", "54321", "20240115", "JOB01", "W", "CLM00001",
                "YS2R4X20005399401", "ABC123", "20240115", 50000,
                "CUST1", "Customer Name", "SDE12345", 10, "SENT", 3, "G"
            );

            // Assert
            assertNotEquals(dto1, dto2);
        }

        @Test
        @DisplayName("Should not be equal when mileage differs")
        void shouldNotBeEqualWhenMileageDiffers() {
            // Arrange
            ClaimListItemDto dto1 = createSampleDto();
            ClaimListItemDto dto2 = new ClaimListItemDto(
                "001", "12345", "20240115", "JOB01", "W", "CLM00001",
                "YS2R4X20005399401", "ABC123", "20240115", 60000,
                "CUST1", "Customer Name", "SDE12345", 10, "SENT", 3, "G"
            );

            // Assert
            assertNotEquals(dto1, dto2);
        }

        @Test
        @DisplayName("Should not be equal when statusCode differs")
        void shouldNotBeEqualWhenStatusCodeDiffers() {
            // Arrange
            ClaimListItemDto dto1 = createSampleDto();
            ClaimListItemDto dto2 = new ClaimListItemDto(
                "001", "12345", "20240115", "JOB01", "W", "CLM00001",
                "YS2R4X20005399401", "ABC123", "20240115", 50000,
                "CUST1", "Customer Name", "SDE12345", 20, "SENT", 3, "G"
            );

            // Assert
            assertNotEquals(dto1, dto2);
        }

        @Test
        @DisplayName("Should not be equal when numberOfFailures differs")
        void shouldNotBeEqualWhenNumberOfFailuresDiffers() {
            // Arrange
            ClaimListItemDto dto1 = createSampleDto();
            ClaimListItemDto dto2 = new ClaimListItemDto(
                "001", "12345", "20240115", "JOB01", "W", "CLM00001",
                "YS2R4X20005399401", "ABC123", "20240115", 50000,
                "CUST1", "Customer Name", "SDE12345", 10, "SENT", 5, "G"
            );

            // Assert
            assertNotEquals(dto1, dto2);
        }

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            // Arrange
            ClaimListItemDto dto = createSampleDto();

            // Assert
            assertEquals(dto, dto);
            assertEquals(dto.hashCode(), dto.hashCode());
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            // Arrange
            ClaimListItemDto dto = createSampleDto();

            // Assert
            assertNotEquals(null, dto);
        }

        @Test
        @DisplayName("Should not be equal to different class")
        void shouldNotBeEqualToDifferentClass() {
            // Arrange
            ClaimListItemDto dto = createSampleDto();
            String differentObject = "Not a ClaimListItemDto";

            // Assert
            assertNotEquals(dto, differentObject);
        }

        @Test
        @DisplayName("Should handle null fields in equals")
        void shouldHandleNullFieldsInEquals() {
            // Arrange
            ClaimListItemDto dto1 = new ClaimListItemDto(
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null
            );
            ClaimListItemDto dto2 = new ClaimListItemDto(
                null, null, null, null, null, null, null, null,
                null, null, null