# Complete JUnit Test Cases for Invoice Entity

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

@DisplayName("Invoice Entity Tests")
class InvoiceTest {

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        invoice = new Invoice();
    }

    // Constructor Tests
    @Test
    @DisplayName("Should create Invoice with default constructor")
    void testDefaultConstructor() {
        Invoice newInvoice = new Invoice();
        assertNotNull(newInvoice);
        assertNull(newInvoice.getId());
    }

    // ID Tests
    @Test
    @DisplayName("Should set and get ID")
    void testSetAndGetId() {
        Long expectedId = 12345L;
        invoice.setId(expectedId);
        assertEquals(expectedId, invoice.getId());
    }

    @Test
    @DisplayName("Should handle null ID")
    void testNullId() {
        invoice.setId(null);
        assertNull(invoice.getId());
    }

    // Company Code Tests
    @Test
    @DisplayName("Should set and get company code")
    void testSetAndGetCompanyCode() {
        String expectedCode = "ABC";
        invoice.setCompanyCode(expectedCode);
        assertEquals(expectedCode, invoice.getCompanyCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "A", "AB", "ABC"})
    @DisplayName("Should handle various company code lengths")
    void testCompanyCodeLengths(String code) {
        invoice.setCompanyCode(code);
        assertEquals(code, invoice.getCompanyCode());
    }

    @Test
    @DisplayName("Should handle null company code")
    void testNullCompanyCode() {
        invoice.setCompanyCode(null);
        assertNull(invoice.getCompanyCode());
    }

    // Invoice Number Tests
    @Test
    @DisplayName("Should set and get invoice number")
    void testSetAndGetInvoiceNumber() {
        String expectedNumber = "12345";
        invoice.setInvoiceNumber(expectedNumber);
        assertEquals(expectedNumber, invoice.getInvoiceNumber());
    }

    @Test
    @DisplayName("Should set and get invoice number 10")
    void testSetAndGetInvoiceNumber10() {
        String expectedNumber = "1234567890";
        invoice.setInvoiceNumber10(expectedNumber);
        assertEquals(expectedNumber, invoice.getInvoiceNumber10());
    }

    // Invoice Date Tests
    @Test
    @DisplayName("Should set and get invoice date")
    void testSetAndGetInvoiceDate() {
        String expectedDate = "20240115";
        invoice.setInvoiceDate(expectedDate);
        assertEquals(expectedDate, invoice.getInvoiceDate());
    }

    @ParameterizedTest
    @ValueSource(strings = {"20240101", "20231231", "20250630"})
    @DisplayName("Should handle various date formats")
    void testVariousInvoiceDates(String date) {
        invoice.setInvoiceDate(date);
        assertEquals(date, invoice.getInvoiceDate());
    }

    // Cancellation Flag Tests
    @Test
    @DisplayName("Should set and get cancellation flag")
    void testSetAndGetCancellationFlag() {
        String expectedFlag = "Y";
        invoice.setCancellationFlag(expectedFlag);
        assertEquals(expectedFlag, invoice.getCancellationFlag());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Y", "N", "X"})
    @DisplayName("Should handle various cancellation flags")
    void testVariousCancellationFlags(String flag) {
        invoice.setCancellationFlag(flag);
        assertEquals(flag, invoice.getCancellationFlag());
    }

    // Job Number Tests
    @Test
    @DisplayName("Should set and get job number")
    void testSetAndGetJobNumber() {
        String expectedJobNumber = "JOB01";
        invoice.setJobNumber(expectedJobNumber);
        assertEquals(expectedJobNumber, invoice.getJobNumber());
    }

    // Area Tests
    @Test
    @DisplayName("Should set and get area")
    void testSetAndGetArea() {
        String expectedArea = "A";
        invoice.setArea(expectedArea);
        assertEquals(expectedArea, invoice.getArea());
    }

    // Workshop Type Tests
    @Test
    @DisplayName("Should set and get workshop type")
    void testSetAndGetWorkshopType() {
        String expectedType = "W";
        invoice.setWorkshopType(expectedType);
        assertEquals(expectedType, invoice.getWorkshopType());
    }

    // Split Tests
    @Test
    @DisplayName("Should set and get split")
    void testSetAndGetSplit() {
        String expectedSplit = "01";
        invoice.setSplit(expectedSplit);
        assertEquals(expectedSplit, invoice.getSplit());
    }

    @ParameterizedTest
    @ValueSource(strings = {"01", "02", "10", "99"})
    @DisplayName("Should handle various split values")
    void testVariousSplitValues(String split) {
        invoice.setSplit(split);
        assertEquals(split, invoice.getSplit());
    }

    // Job Date Tests
    @Test
    @DisplayName("Should set and get job date")
    void testSetAndGetJobDate() {
        String expectedDate = "20240115";
        invoice.setJobDate(expectedDate);
        assertEquals(expectedDate, invoice.getJobDate());
    }

    // Customer Number Tests
    @Test
    @DisplayName("Should set and get customer number")
    void testSetAndGetCustomerNumber() {
        String expectedCustomer = "123456";
        invoice.setCustomerNumber(expectedCustomer);
        assertEquals(expectedCustomer, invoice.getCustomerNumber());
    }

    // Name Tests
    @Test
    @DisplayName("Should set and get name")
    void testSetAndGetName() {
        String expectedName = "John Doe";
        invoice.setName(expectedName);
        assertEquals(expectedName, invoice.getName());
    }

    @Test
    @DisplayName("Should handle long names")
    void testLongName() {
        String longName = "A".repeat(30);
        invoice.setName(longName);
        assertEquals(longName, invoice.getName());
    }

    // Vehicle Number Tests
    @Test
    @DisplayName("Should set and get vehicle number")
    void testSetAndGetVehicleNumber() {
        String expectedVehicleNumber = "VIN1234567890ABCD";
        invoice.setVehicleNumber(expectedVehicleNumber);
        assertEquals(expectedVehicleNumber, invoice.getVehicleNumber());
    }

    // License Plate Tests
    @Test
    @DisplayName("Should set and get license plate")
    void testSetAndGetLicensePlate() {
        String expectedPlate = "ABC-123";
        invoice.setLicensePlate(expectedPlate);
        assertEquals(expectedPlate, invoice.getLicensePlate());
    }

    // Registration Date Tests
    @Test
    @DisplayName("Should set and get registration date")
    void testSetAndGetRegistrationDate() {
        String expectedDate = "20200101";
        invoice.setRegistrationDate(expectedDate);
        assertEquals(expectedDate, invoice.getRegistrationDate());
    }

    // Kilometers Tests
    @Test
    @DisplayName("Should set and get kilometers")
    void testSetAndGetKilometers() {
        String expectedKm = "150000";
        invoice.setKilometers(expectedKm);
        assertEquals(expectedKm, invoice.getKilometers());
    }

    // Acceptance Date Tests
    @Test
    @DisplayName("Should set and get acceptance date")
    void testSetAndGetAcceptanceDate() {
        String expectedDate = "20240115";
        invoice.setAcceptanceDate(expectedDate);
        assertEquals(expectedDate, invoice.getAcceptanceDate());
    }

    // BigDecimal Tests
    @Test
    @DisplayName("Should set and get invoice net amount")
    void testInvoiceNetAmount() {
        BigDecimal expectedAmount = new BigDecimal("1234.56");
        invoice.setInvoiceNet(expectedAmount);
        assertEquals(expectedAmount, invoice.getInvoiceNet());
    }

    @Test
    @DisplayName("Should set and get invoice VAT")
    void testInvoiceVat() {
        BigDecimal expectedVat = new BigDecimal("234.56");
        invoice.setInvoiceVat(expectedVat);
        assertEquals(expectedVat, invoice.getInvoiceVat());
    }

    @Test
    @DisplayName("Should set and get invoice total gross")
    void testInvoiceTotalGross() {
        BigDecimal expectedTotal = new BigDecimal("1469.12");
        invoice.setInvoiceTotalGross(expectedTotal);
        assertEquals(expectedTotal, invoice.getInvoiceTotalGross());
    }

    @Test
    @DisplayName("Should handle zero amounts")
    void testZeroAmounts() {
        BigDecimal zero = BigDecimal.ZERO;
        invoice.setInvoiceNet(zero);
        invoice.setInvoiceVat(zero);
        invoice.setInvoiceTotalGross(zero);
        
        assertEquals(zero, invoice.getInvoiceNet());
        assertEquals(zero, invoice.getInvoiceVat());
        assertEquals(zero, invoice.getInvoiceTotalGross());
    }

    @Test
    @DisplayName("Should handle negative amounts")
    void testNegativeAmounts() {
        BigDecimal negative = new BigDecimal("-100.00");
        invoice.setInvoiceNet(negative);
        assertEquals(negative, invoice.getInvoiceNet());
    }

    @Test
    @DisplayName("Should handle null amounts")
    void testNullAmounts() {
        invoice.setInvoiceNet(null);
        invoice.setInvoiceVat(null);
        assertNull(invoice.getInvoiceNet());
        assertNull(invoice.getInvoiceVat());
    }

    @Test
    @DisplayName("Should set and get VAT percent")
    void testVatPercent() {
        BigDecimal expectedPercent = new BigDecimal("19.00");
        invoice.setVatPercent(expectedPercent);
        assertEquals(expectedPercent, invoice.getVatPercent());
    }

    @Test
    @DisplayName("Should set and get VAT percent reduced")
    void testVatPercentReduced() {
        BigDecimal expectedPercent = new BigDecimal("7.00");
        invoice.setVatPercentReduced(expectedPercent);
        assertEquals(expectedPercent, invoice.getVatPercentReduced());
    }

    // Complex Object Tests
    @Test
    @DisplayName("Should create complete invoice object")
    void testCompleteInvoiceObject() {
        // Arrange
        invoice.setId(1L);
        invoice.setCompanyCode("SCA");
        invoice.setInvoiceNumber("12345");
        invoice.setInvoiceDate("20240115");
        invoice.setJobNumber("JOB01");
        invoice.setArea("A");
        invoice.setWorkshopType("W");
        invoice.setSplit("01");
        invoice.setCustomerNumber("CUST01");
        invoice.setName("Test Customer");
        invoice.setVehicleNumber("VIN123456");
        invoice.setLicensePlate("ABC-123");
        invoice.setKilometers("50000");
        invoice.setInvoiceNet(new BigDecimal("1000.00"));
        invoice.setInvoiceVat(new BigDecimal("190.00"));
        invoice.setInvoiceTotalGross(new BigDecimal("1190.00"));
        
        // Assert
        assertAll("Complete Invoice",
            () -> assertEquals(1L, invoice.getId()),
            () -> assertEquals("SCA", invoice.getCompanyCode()),
            () -> assertEquals("12345", invoice.getInvoiceNumber()),
            () -> assertEquals("20240115", invoice.getInvoiceDate()),
            () -> assertEquals("JOB01", invoice.getJobNumber()),
            () -> assertEquals("A", invoice.getArea()),
            () -> assertEquals("W", invoice.getWorkshopType()),
            () -> assertEquals("01", invoice.getSplit()),
            () -> assertEquals("CUST01", invoice.getCustomerNumber()),
            () -> assertEquals("Test Customer", invoice.getName()),
            () -> assertEquals("VIN123456", invoice.getVehicleNumber()),
            () -> assertEquals("ABC-123", invoice.getLicensePlate()),
            () -> assertEquals("50000", invoice.getKilometers()),
            () -> assertEquals(new BigDecimal("1000.00"), invoice.getInvoiceNet()),
            () -> assertEquals(new BigDecimal("190.00"), invoice.getInvoiceVat()),
            () -> assertEquals(new BigDecimal("1190.00"), invoice.getInvoiceTotalGross())
        );
    }

    // Null Safety Tests
    @Test
    @DisplayName("Should handle all null values")
    void testAllNullValues() {
        Invoice nullInvoice = new Invoice();
        
        assertAll("Null Invoice Fields",
            () -> assertNull(nullInvoice.getId()),
            () -> assertNull(nullInvoice.getCompanyCode()),
            () -> assertNull(nullInvoice.getInvoiceNumber()),
            () -> assertNull(nullInvoice.getInvoiceDate()),
            () -> assertNull(nullInvoice.getJobNumber()),
            () -> assertNull(nullInvoice.getArea()),
            () -> assertNull(nullInvoice.getWorkshopType()),
            () -> assertNull(nullInvoice.getSplit()),
            () -> assertNull(nullInvoice.getCustomerNumber()),
            () -> assertNull(nullInvoice.getName()),
            () -> assertNull(nullInvoice.getVehicleNumber()),
            () -> assertNull(nullInvoice.getLicensePlate()),
            () -> assertNull(nullInvoice.getKilometers()),
            () -> assertNull(nullInvoice.getInvoiceNet()),
            () -> assertNull(nullInvoice.getInvoiceVat())
        );
    }

    // Edge Case Tests
    @Test
    @DisplayName("Should handle empty strings")
    void testEmptyStrings() {
        invoice.setCompanyCode("");
        invoice.setInvoiceNumber("");
        invoice.setName("");
        
        assertEquals("", invoice.getCompanyCode());
        assertEquals("", invoice.getInvoiceNumber());
        assertEquals("", invoice.getName());
    }

    @Test
    @DisplayName("Should handle whitespace strings")
    void testWhitespaceStrings() {
        invoice.setCompanyCode("   ");
        invoice.setName("   ");
        
        assertEquals("   ", invoice.getCompanyCode());
        assertEquals("   ", invoice.getName());
    }

    // Business Logic Validation Tests
    @Test
    @DisplayName("Should validate invoice date format (8 characters)")
    void testInvoiceDateFormat() {
        String validDate = "20240115";
        invoice.setInvoiceDate(validDate);
        assertEquals(8, invoice.getInvoiceDate().length());
    }

    @Test
    @DisplayName("Should validate split format (2 characters)")
    void testSplitFormat() {
        String validSplit = "01";
        invoice.setSplit(validSplit);
        assertEquals(2, invoice.getSplit().length());