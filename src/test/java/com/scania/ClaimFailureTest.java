# Complete JUnit Test Cases for ClaimFailure Entity

```java
package com.scania.warranty.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimFailure Entity Tests")
class ClaimFailureTest {

    private ClaimFailure claimFailure;

    @BeforeEach
    void setUp() {
        claimFailure = new ClaimFailure();
    }

    @Test
    @DisplayName("Should create ClaimFailure with default constructor")
    void testDefaultConstructor() {
        assertNotNull(claimFailure);
        assertNull(claimFailure.getId());
        assertNull(claimFailure.getCompanyCode());
        assertNull(claimFailure.getClaimNumber());
    }

    @Test
    @DisplayName("Should set and get ID")
    void testIdGetterSetter() {
        Long expectedId = 12345L;
        claimFailure.setId(expectedId);
        assertEquals(expectedId, claimFailure.getId());
    }

    @Test
    @DisplayName("Should set and get company code")
    void testCompanyCodeGetterSetter() {
        String expectedCode = "ABC";
        claimFailure.setCompanyCode(expectedCode);
        assertEquals(expectedCode, claimFailure.getCompanyCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "A", "AB", "ABC"})
    @DisplayName("Should handle various company code lengths")
    void testCompanyCodeVariousLengths(String code) {
        claimFailure.setCompanyCode(code);
        assertEquals(code, claimFailure.getCompanyCode());
    }

    @Test
    @DisplayName("Should set and get invoice number")
    void testInvoiceNumberGetterSetter() {
        String expectedInvoice = "12345";
        claimFailure.setInvoiceNumber(expectedInvoice);
        assertEquals(expectedInvoice, claimFailure.getInvoiceNumber());
    }

    @Test
    @DisplayName("Should set and get invoice date")
    void testInvoiceDateGetterSetter() {
        String expectedDate = "20240115";
        claimFailure.setInvoiceDate(expectedDate);
        assertEquals(expectedDate, claimFailure.getInvoiceDate());
    }

    @Test
    @DisplayName("Should set and get job number")
    void testJobNumberGetterSetter() {
        String expectedJobNumber = "JOB01";
        claimFailure.setJobNumber(expectedJobNumber);
        assertEquals(expectedJobNumber, claimFailure.getJobNumber());
    }

    @Test
    @DisplayName("Should set and get area")
    void testAreaGetterSetter() {
        String expectedArea = "A";
        claimFailure.setArea(expectedArea);
        assertEquals(expectedArea, claimFailure.getArea());
    }

    @Test
    @DisplayName("Should set and get claim number")
    void testClaimNumberGetterSetter() {
        String expectedClaimNumber = "CLM12345";
        claimFailure.setClaimNumber(expectedClaimNumber);
        assertEquals(expectedClaimNumber, claimFailure.getClaimNumber());
    }

    @Test
    @DisplayName("Should set and get failure number")
    void testFailureNumberGetterSetter() {
        String expectedFailureNumber = "01";
        claimFailure.setFailureNumber(expectedFailureNumber);
        assertEquals(expectedFailureNumber, claimFailure.getFailureNumber());
    }

    @Test
    @DisplayName("Should set and get sequence number")
    void testSequenceNumberGetterSetter() {
        String expectedSequenceNumber = "02";
        claimFailure.setSequenceNumber(expectedSequenceNumber);
        assertEquals(expectedSequenceNumber, claimFailure.getSequenceNumber());
    }

    @Test
    @DisplayName("Should set and get status code")
    void testStatusCodeGetterSetter() {
        Integer expectedStatusCode = 10;
        claimFailure.setStatusCode(expectedStatusCode);
        assertEquals(expectedStatusCode, claimFailure.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 99})
    @DisplayName("Should handle various status codes")
    void testStatusCodeVariousValues(Integer statusCode) {
        claimFailure.setStatusCode(statusCode);
        assertEquals(statusCode, claimFailure.getStatusCode());
    }

    @Test
    @DisplayName("Should handle null values for string fields")
    void testNullStringFields() {
        claimFailure.setCompanyCode(null);
        claimFailure.setInvoiceNumber(null);
        claimFailure.setClaimNumber(null);
        
        assertNull(claimFailure.getCompanyCode());
        assertNull(claimFailure.getInvoiceNumber());
        assertNull(claimFailure.getClaimNumber());
    }

    @Test
    @DisplayName("Should handle null values for integer fields")
    void testNullIntegerFields() {
        claimFailure.setStatusCode(null);
        claimFailure.setAssessmentCode2(null);
        
        assertNull(claimFailure.getStatusCode());
        assertNull(claimFailure.getAssessmentCode2());
    }

    @Test
    @DisplayName("Should create fully populated ClaimFailure object")
    void testFullyPopulatedObject() {
        // Arrange & Act
        claimFailure.setId(1L);
        claimFailure.setCompanyCode("SCN");
        claimFailure.setInvoiceNumber("INV01");
        claimFailure.setInvoiceDate("20240115");
        claimFailure.setJobNumber("JOB01");
        claimFailure.setArea("A");
        claimFailure.setClaimNumber("CLM00001");
        claimFailure.setFailureNumber("01");
        claimFailure.setSequenceNumber("01");
        claimFailure.setStatusCode(10);

        // Assert
        assertAll("Verify all fields are set correctly",
            () -> assertEquals(1L, claimFailure.getId()),
            () -> assertEquals("SCN", claimFailure.getCompanyCode()),
            () -> assertEquals("INV01", claimFailure.getInvoiceNumber()),
            () -> assertEquals("20240115", claimFailure.getInvoiceDate()),
            () -> assertEquals("JOB01", claimFailure.getJobNumber()),
            () -> assertEquals("A", claimFailure.getArea()),
            () -> assertEquals("CLM00001", claimFailure.getClaimNumber()),
            () -> assertEquals("01", claimFailure.getFailureNumber()),
            () -> assertEquals("01", claimFailure.getSequenceNumber()),
            () -> assertEquals(10, claimFailure.getStatusCode())
        );
    }

    @Test
    @DisplayName("Should verify entity annotations are present")
    void testEntityAnnotations() {
        assertTrue(ClaimFailure.class.isAnnotationPresent(Entity.class));
        assertTrue(ClaimFailure.class.isAnnotationPresent(Table.class));
        
        Table tableAnnotation = ClaimFailure.class.getAnnotation(Table.class);
        assertEquals("HSG73PF", tableAnnotation.name());
    }

    @Test
    @DisplayName("Should handle empty strings for string fields")
    void testEmptyStringFields() {
        claimFailure.setCompanyCode("");
        claimFailure.setInvoiceNumber("");
        claimFailure.setArea("");
        
        assertEquals("", claimFailure.getCompanyCode());
        assertEquals("", claimFailure.getInvoiceNumber());
        assertEquals("", claimFailure.getArea());
    }

    @Test
    @DisplayName("Should maintain field independence")
    void testFieldIndependence() {
        claimFailure.setCompanyCode("ABC");
        claimFailure.setInvoiceNumber("12345");
        
        claimFailure.setCompanyCode("XYZ");
        
        assertEquals("XYZ", claimFailure.getCompanyCode());
        assertEquals("12345", claimFailure.getInvoiceNumber());
    }

    @Test
    @DisplayName("Should handle boundary values for invoice date")
    void testInvoiceDateBoundaryValues() {
        String minDate = "19000101";
        String maxDate = "99991231";
        
        claimFailure.setInvoiceDate(minDate);
        assertEquals(minDate, claimFailure.getInvoiceDate());
        
        claimFailure.setInvoiceDate(maxDate);
        assertEquals(maxDate, claimFailure.getInvoiceDate());
    }

    @Test
    @DisplayName("Should handle special characters in text fields")
    void testSpecialCharactersInTextFields() {
        String specialText = "Test@#$%^&*()";
        claimFailure.setCompanyCode(specialText.substring(0, 3));
        
        assertNotNull(claimFailure.getCompanyCode());
        assertEquals(3, claimFailure.getCompanyCode().length());
    }

    @Test
    @DisplayName("Should verify claim number format")
    void testClaimNumberFormat() {
        String validClaimNumber = "CLM12345";
        claimFailure.setClaimNumber(validClaimNumber);
        
        assertEquals(validClaimNumber, claimFailure.getClaimNumber());
        assertEquals(8, claimFailure.getClaimNumber().length());
    }

    @Test
    @DisplayName("Should handle consecutive setter calls")
    void testConsecutiveSetterCalls() {
        claimFailure.setStatusCode(0);
        claimFailure.setStatusCode(10);
        claimFailure.setStatusCode(99);
        
        assertEquals(99, claimFailure.getStatusCode());
    }

    @Test
    @DisplayName("Should verify failure number is two characters")
    void testFailureNumberLength() {
        String failureNumber = "01";
        claimFailure.setFailureNumber(failureNumber);
        
        assertEquals(2, claimFailure.getFailureNumber().length());
    }

    @Test
    @DisplayName("Should verify sequence number is two characters")
    void testSequenceNumberLength() {
        String sequenceNumber = "99";
        claimFailure.setSequenceNumber(sequenceNumber);
        
        assertEquals(2, claimFailure.getSequenceNumber().length());
    }

    @Test
    @DisplayName("Should handle area as single character")
    void testAreaSingleCharacter() {
        claimFailure.setArea("A");
        assertEquals(1, claimFailure.getArea().length());
        
        claimFailure.setArea("Z");
        assertEquals(1, claimFailure.getArea().length());
    }

    @Test
    @DisplayName("Should verify job number format")
    void testJobNumberFormat() {
        String jobNumber = "12345";
        claimFailure.setJobNumber(jobNumber);
        
        assertEquals(jobNumber, claimFailure.getJobNumber());
        assertEquals(5, claimFailure.getJobNumber().length());
    }

    @Test
    @DisplayName("Should handle multiple field updates")
    void testMultipleFieldUpdates() {
        // First update
        claimFailure.setCompanyCode("ABC");
        claimFailure.setStatusCode(0);
        
        // Second update
        claimFailure.setCompanyCode("XYZ");
        claimFailure.setStatusCode(10);
        
        // Verify latest values
        assertEquals("XYZ", claimFailure.getCompanyCode());
        assertEquals(10, claimFailure.getStatusCode());
    }

    @Test
    @DisplayName("Should verify object state after creation")
    void testInitialObjectState() {
        ClaimFailure newFailure = new ClaimFailure();
        
        assertAll("Verify initial state",
            () -> assertNull(newFailure.getId()),
            () -> assertNull(newFailure.getCompanyCode()),
            () -> assertNull(newFailure.getInvoiceNumber()),
            () -> assertNull(newFailure.getInvoiceDate()),
            () -> assertNull(newFailure.getJobNumber()),
            () -> assertNull(newFailure.getArea()),
            () -> assertNull(newFailure.getClaimNumber()),
            () -> assertNull(newFailure.getFailureNumber()),
            () -> assertNull(newFailure.getSequenceNumber()),
            () -> assertNull(newFailure.getStatusCode())
        );
    }

    @Test
    @DisplayName("Should handle claim number with leading zeros")
    void testClaimNumberWithLeadingZeros() {
        String claimNumber = "00000123";
        claimFailure.setClaimNumber(claimNumber);
        
        assertEquals(claimNumber, claimFailure.getClaimNumber());
        assertTrue(claimFailure.getClaimNumber().startsWith("0"));
    }

    @Test
    @DisplayName("Should verify invoice date format YYYYMMDD")
    void testInvoiceDateFormat() {
        String validDate = "20240115";
        claimFailure.setInvoiceDate(validDate);
        
        assertEquals(8, claimFailure.getInvoiceDate().length());
        assertTrue(claimFailure.getInvoiceDate().matches("\\d{8}"));
    }

    @Test
    @DisplayName("Should handle status code zero")
    void testStatusCodeZero() {
        claimFailure.setStatusCode(0);
        assertEquals(0, claimFailure.getStatusCode());
    }

    @Test
    @DisplayName("Should handle status code maximum value")
    void testStatusCodeMaxValue() {
        claimFailure.setStatusCode(99);
        assertEquals(99, claimFailure.getStatusCode());
    }
}
```

```java
package com.scania.warranty.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimFailure Entity Metadata Tests")
class ClaimFailureMetadataTest {

    @Nested
    @DisplayName("Column Annotation Tests")
    class ColumnAnnotationTests {

        @Test
        @DisplayName("Should verify companyCode column name is PAKZ")
        void testCompanyCodeColumnName() throws NoSuchFieldException {
            Field field = ClaimFailure.class.getDeclaredField("companyCode");
            Column column = field.getAnnotation(Column.class);
            
            assertNotNull(column);
            assertEquals("PAKZ", column.name());
            assertEquals(3, column.length());
        }

        @Test
        @DisplayName("Should verify invoiceNumber column name")
        void testInvoiceNumberColumnName() throws NoSuchFieldException {
            Field field = ClaimFailure.class.getDeclaredField("invoiceNumber");
            Column column = field.getAnnotation(Column.class);
            
            assertNotNull(column);
            assertEquals("RECH.-NR.", column.name());
            assertEquals(5, column.length());
        }

        @Test
        @DisplayName("Should verify claimNumber column name")
        void testClaimNumberColumnName() throws NoSuchFieldException {
            