# JUnit Test Cases for ClaimCreationRequestDto

```java
package com.scania.warranty.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimCreationRequestDto Tests")
class ClaimCreationRequestDtoTest {

    @Test
    @DisplayName("Should create ClaimCreationRequestDto with all valid fields")
    void shouldCreateClaimCreationRequestDtoWithAllValidFields() {
        // Arrange
        String companyCode = "001";
        String invoiceNumber = "12345";
        String invoiceDate = "20240115";
        String jobNumber = "JOB01";
        String workshopType = "A";

        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            companyCode,
            invoiceNumber,
            invoiceDate,
            jobNumber,
            workshopType
        );

        // Assert
        assertNotNull(dto);
        assertEquals(companyCode, dto.companyCode());
        assertEquals(invoiceNumber, dto.invoiceNumber());
        assertEquals(invoiceDate, dto.invoiceDate());
        assertEquals(jobNumber, dto.jobNumber());
        assertEquals(workshopType, dto.workshopType());
    }

    @Test
    @DisplayName("Should create ClaimCreationRequestDto with null values")
    void shouldCreateClaimCreationRequestDtoWithNullValues() {
        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            null,
            null,
            null,
            null,
            null
        );

        // Assert
        assertNotNull(dto);
        assertNull(dto.companyCode());
        assertNull(dto.invoiceNumber());
        assertNull(dto.invoiceDate());
        assertNull(dto.jobNumber());
        assertNull(dto.workshopType());
    }

    @Test
    @DisplayName("Should create ClaimCreationRequestDto with empty strings")
    void shouldCreateClaimCreationRequestDtoWithEmptyStrings() {
        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "",
            "",
            "",
            "",
            ""
        );

        // Assert
        assertNotNull(dto);
        assertEquals("", dto.companyCode());
        assertEquals("", dto.invoiceNumber());
        assertEquals("", dto.invoiceDate());
        assertEquals("", dto.jobNumber());
        assertEquals("", dto.workshopType());
    }

    @Test
    @DisplayName("Should maintain equality for records with same values")
    void shouldMaintainEqualityForRecordsWithSameValues() {
        // Arrange
        ClaimCreationRequestDto dto1 = new ClaimCreationRequestDto(
            "001",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        ClaimCreationRequestDto dto2 = new ClaimCreationRequestDto(
            "001",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        // Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    @DisplayName("Should not be equal for records with different values")
    void shouldNotBeEqualForRecordsWithDifferentValues() {
        // Arrange
        ClaimCreationRequestDto dto1 = new ClaimCreationRequestDto(
            "001",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        ClaimCreationRequestDto dto2 = new ClaimCreationRequestDto(
            "002",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        // Assert
        assertNotEquals(dto1, dto2);
    }

    @Test
    @DisplayName("Should generate correct toString representation")
    void shouldGenerateCorrectToStringRepresentation() {
        // Arrange
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "001",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        // Act
        String result = dto.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("companyCode=001"));
        assertTrue(result.contains("invoiceNumber=12345"));
        assertTrue(result.contains("invoiceDate=20240115"));
        assertTrue(result.contains("jobNumber=JOB01"));
        assertTrue(result.contains("workshopType=A"));
    }

    @Test
    @DisplayName("Should handle maximum length strings")
    void shouldHandleMaximumLengthStrings() {
        // Arrange
        String longString = "A".repeat(1000);

        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            longString,
            longString,
            longString,
            longString,
            longString
        );

        // Assert
        assertNotNull(dto);
        assertEquals(longString, dto.companyCode());
        assertEquals(1000, dto.companyCode().length());
    }

    @Test
    @DisplayName("Should handle special characters in fields")
    void shouldHandleSpecialCharactersInFields() {
        // Arrange
        String specialChars = "!@#$%^&*()_+-=[]{}|;:',.<>?/~`";

        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            specialChars,
            specialChars,
            specialChars,
            specialChars,
            specialChars
        );

        // Assert
        assertNotNull(dto);
        assertEquals(specialChars, dto.companyCode());
        assertEquals(specialChars, dto.invoiceNumber());
    }

    @Test
    @DisplayName("Should handle whitespace in fields")
    void shouldHandleWhitespaceInFields() {
        // Arrange
        String whitespace = "   ";

        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            whitespace,
            whitespace,
            whitespace,
            whitespace,
            whitespace
        );

        // Assert
        assertNotNull(dto);
        assertEquals(whitespace, dto.companyCode());
        assertEquals(3, dto.companyCode().length());
    }

    @Test
    @DisplayName("Should create record with mixed null and non-null values")
    void shouldCreateRecordWithMixedNullAndNonNullValues() {
        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "001",
            null,
            "20240115",
            null,
            "A"
        );

        // Assert
        assertNotNull(dto);
        assertEquals("001", dto.companyCode());
        assertNull(dto.invoiceNumber());
        assertEquals("20240115", dto.invoiceDate());
        assertNull(dto.jobNumber());
        assertEquals("A", dto.workshopType());
    }

    @Test
    @DisplayName("Should handle numeric strings in all fields")
    void shouldHandleNumericStringsInAllFields() {
        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "123",
            "45678",
            "20240115",
            "90123",
            "1"
        );

        // Assert
        assertNotNull(dto);
        assertEquals("123", dto.companyCode());
        assertEquals("45678", dto.invoiceNumber());
        assertEquals("20240115", dto.invoiceDate());
        assertEquals("90123", dto.jobNumber());
        assertEquals("1", dto.workshopType());
    }

    @Test
    @DisplayName("Should handle alphanumeric strings")
    void shouldHandleAlphanumericStrings() {
        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "ABC123",
            "INV456",
            "2024-01-15",
            "JOB789",
            "T1"
        );

        // Assert
        assertNotNull(dto);
        assertEquals("ABC123", dto.companyCode());
        assertEquals("INV456", dto.invoiceNumber());
        assertEquals("2024-01-15", dto.invoiceDate());
        assertEquals("JOB789", dto.jobNumber());
        assertEquals("T1", dto.workshopType());
    }

    @Test
    @DisplayName("Should not be equal to null")
    void shouldNotBeEqualToNull() {
        // Arrange
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "001",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        // Assert
        assertNotEquals(null, dto);
    }

    @Test
    @DisplayName("Should be equal to itself")
    void shouldBeEqualToItself() {
        // Arrange
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "001",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        // Assert
        assertEquals(dto, dto);
    }

    @Test
    @DisplayName("Should handle Unicode characters")
    void shouldHandleUnicodeCharacters() {
        // Arrange
        String unicode = "测试数据";

        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            unicode,
            unicode,
            unicode,
            unicode,
            unicode
        );

        // Assert
        assertNotNull(dto);
        assertEquals(unicode, dto.companyCode());
    }

    @Test
    @DisplayName("Should create record matching business preconditions format")
    void shouldCreateRecordMatchingBusinessPreconditionsFormat() {
        // Arrange - Based on DB2 table structure
        String companyCode = "001";      // AHK000 - CHAR(3)
        String invoiceNumber = "12345";  // AHK010 - CHAR(5)
        String invoiceDate = "20240115"; // AHK020 - CHAR(8)
        String jobNumber = "JOB01";      // AHK040 - CHAR(5)
        String workshopType = "A";       // AHK060 - CHAR(1)

        // Act
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            companyCode,
            invoiceNumber,
            invoiceDate,
            jobNumber,
            workshopType
        );

        // Assert
        assertNotNull(dto);
        assertEquals(3, dto.companyCode().length());
        assertEquals(5, dto.invoiceNumber().length());
        assertEquals(8, dto.invoiceDate().length());
        assertEquals(5, dto.jobNumber().length());
        assertEquals(1, dto.workshopType().length());
    }

    @Test
    @DisplayName("Should handle date format variations")
    void shouldHandleDateFormatVariations() {
        // Act
        ClaimCreationRequestDto dto1 = new ClaimCreationRequestDto(
            "001", "12345", "20240115", "JOB01", "A"
        );
        
        ClaimCreationRequestDto dto2 = new ClaimCreationRequestDto(
            "001", "12345", "2024-01-15", "JOB01", "A"
        );

        // Assert
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertNotEquals(dto1, dto2); // Different date formats
    }

    @Test
    @DisplayName("Should maintain immutability")
    void shouldMaintainImmutability() {
        // Arrange
        String originalCompanyCode = "001";
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            originalCompanyCode,
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        // Act
        String retrievedCompanyCode = dto.companyCode();

        // Assert
        assertEquals(originalCompanyCode, retrievedCompanyCode);
        // Records are immutable by design - no setters exist
    }
}
```

## Additional Test Configuration (Optional)

```java
package com.scania.warranty.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimCreationRequestDto JSON Serialization Tests")
class ClaimCreationRequestDtoJsonTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Should serialize to JSON correctly")
    void shouldSerializeToJsonCorrectly() throws Exception {
        // Arrange
        ClaimCreationRequestDto dto = new ClaimCreationRequestDto(
            "001",
            "12345",
            "20240115",
            "JOB01",
            "A"
        );

        // Act
        String json = objectMapper.writeValueAsString(dto);

        // Assert
        assertNotNull(json);
        assertTrue(json.contains("\"companyCode\":\"001\""));
        assertTrue(json.contains("\"invoiceNumber\":\"12345\""));
        assertTrue(json.contains("\"invoiceDate\":\"20240115\""));
        assertTrue(json.contains("\"jobNumber\":\"JOB01\""));
        assertTrue(json.contains("\"workshopType\":\"A\""));
    }

    @Test
    @DisplayName("Should deserialize from JSON correctly")
    void shouldDeserializeFromJsonCorrectly() throws Exception {
        // Arrange
        String json = """
            {
                "companyCode": "001",
                "invoiceNumber": "12345",
                "invoiceDate": "20240115",
                "jobNumber": "JOB01",
                "workshopType": "A"
            }
            """;

        // Act
        ClaimCreationRequestDto dto = objectMapper.readValue(json, ClaimCreationRequestDto.class);

        // Assert
        assertNotNull(dto);
        assertEquals("001", dto.companyCode());
        assertEquals("12345", dto.invoiceNumber());
        assertEquals("20240115", dto.invoiceDate());
        assertEquals("JOB01", dto.jobNumber());
        assertEquals("A", dto.workshopType());
    }
}
```

These test cases cover:
- ✅ Valid object creation
- ✅ Null value handling
- ✅ Empty string handling
- ✅ Equality and hashCode
- ✅ toString() method
- ✅ Edge cases (max length, special characters, whitespace)
- ✅ Business domain validation
- ✅ Immutability
- ✅ JSON serialization/deserialization (optional)