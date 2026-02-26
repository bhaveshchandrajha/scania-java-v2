# Complete JUnit Test Cases for ExternalService Entity

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

@DisplayName("ExternalService Entity Tests")
class ExternalServiceTest {

    private ExternalService externalService;

    @BeforeEach
    void setUp() {
        externalService = new ExternalService();
    }

    @Test
    @DisplayName("Should create ExternalService with default constructor")
    void testDefaultConstructor() {
        assertNotNull(externalService);
        assertNull(externalService.getId());
        assertNull(externalService.getCompanyCode());
        assertNull(externalService.getOrderDate());
    }

    @Test
    @DisplayName("Should set and get ID")
    void testIdGetterSetter() {
        Long expectedId = 12345L;
        externalService.setId(expectedId);
        assertEquals(expectedId, externalService.getId());
    }

    @Test
    @DisplayName("Should set and get company code")
    void testCompanyCodeGetterSetter() {
        String expectedCode = "ABC";
        externalService.setCompanyCode(expectedCode);
        assertEquals(expectedCode, externalService.getCompanyCode());
    }

    @Test
    @DisplayName("Should handle null company code")
    void testCompanyCodeNull() {
        externalService.setCompanyCode(null);
        assertNull(externalService.getCompanyCode());
    }

    @Test
    @DisplayName("Should set and get order date")
    void testOrderDateGetterSetter() {
        String expectedDate = "20240115";
        externalService.setOrderDate(expectedDate);
        assertEquals(expectedDate, externalService.getOrderDate());
    }

    @Test
    @DisplayName("Should set and get order number")
    void testOrderNumberGetterSetter() {
        String expectedOrderNumber = "12345";
        externalService.setOrderNumber(expectedOrderNumber);
        assertEquals(expectedOrderNumber, externalService.getOrderNumber());
    }

    @Test
    @DisplayName("Should set and get line number external")
    void testLineNumberExternalGetterSetter() {
        Integer expectedLineNumber = 100;
        externalService.setLineNumberExternal(expectedLineNumber);
        assertEquals(expectedLineNumber, externalService.getLineNumberExternal());
    }

    @Test
    @DisplayName("Should set and get external service ID")
    void testExternalServiceIdGetterSetter() {
        String expectedId = "EXT12345";
        externalService.setExternalServiceId(expectedId);
        assertEquals(expectedId, externalService.getExternalServiceId());
    }

    @Test
    @DisplayName("Should set and get line number")
    void testLineNumberGetterSetter() {
        Integer expectedLineNumber = 50;
        externalService.setLineNumber(expectedLineNumber);
        assertEquals(expectedLineNumber, externalService.getLineNumber());
    }

    @Test
    @DisplayName("Should set and get description")
    void testDescriptionGetterSetter() {
        String expectedDescription = "External service description";
        externalService.setDescription(expectedDescription);
        assertEquals(expectedDescription, externalService.getDescription());
    }

    @Test
    @DisplayName("Should handle long description up to 40 characters")
    void testDescriptionMaxLength() {
        String longDescription = "A".repeat(40);
        externalService.setDescription(longDescription);
        assertEquals(40, externalService.getDescription().length());
    }

    @Test
    @DisplayName("Should set and get text lines")
    void testTextLinesGetterSetter() {
        Integer expectedTextLines = 5;
        externalService.setTextLines(expectedTextLines);
        assertEquals(expectedTextLines, externalService.getTextLines());
    }

    @Test
    @DisplayName("Should set and get purchase price")
    void testPurchasePriceGetterSetter() {
        BigDecimal expectedPrice = new BigDecimal("99999.99");
        externalService.setPurchasePrice(expectedPrice);
        assertEquals(expectedPrice, externalService.getPurchasePrice());
    }

    @Test
    @DisplayName("Should handle zero purchase price")
    void testPurchasePriceZero() {
        BigDecimal zeroPrice = BigDecimal.ZERO;
        externalService.setPurchasePrice(zeroPrice);
        assertEquals(zeroPrice, externalService.getPurchasePrice());
    }

    @Test
    @DisplayName("Should handle negative purchase price")
    void testPurchasePriceNegative() {
        BigDecimal negativePrice = new BigDecimal("-100.50");
        externalService.setPurchasePrice(negativePrice);
        assertEquals(negativePrice, externalService.getPurchasePrice());
    }

    @Test
    @DisplayName("Should set and get quantity")
    void testQuantityGetterSetter() {
        Integer expectedQuantity = 10;
        externalService.setQuantity(expectedQuantity);
        assertEquals(expectedQuantity, externalService.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 100, 99999})
    @DisplayName("Should handle various quantity values")
    void testQuantityVariousValues(Integer quantity) {
        externalService.setQuantity(quantity);
        assertEquals(quantity, externalService.getQuantity());
    }

    @Test
    @DisplayName("Should set and get purchase invoice number")
    void testPurchaseInvoiceNumberGetterSetter() {
        String expectedInvoiceNumber = "INV-123456";
        externalService.setPurchaseInvoiceNumber(expectedInvoiceNumber);
        assertEquals(expectedInvoiceNumber, externalService.getPurchaseInvoiceNumber());
    }

    @Test
    @DisplayName("Should set and get purchase invoice date")
    void testPurchaseInvoiceDateGetterSetter() {
        String expectedDate = "20240115";
        externalService.setPurchaseInvoiceDate(expectedDate);
        assertEquals(expectedDate, externalService.getPurchaseInvoiceDate());
    }

    @Test
    @DisplayName("Should set and get purchase remarks 1")
    void testPurchaseRemarks1GetterSetter() {
        String expectedRemarks = "First purchase remark";
        externalService.setPurchaseRemarks1(expectedRemarks);
        assertEquals(expectedRemarks, externalService.getPurchaseRemarks1());
    }

    @Test
    @DisplayName("Should set and get purchase remarks 2")
    void testPurchaseRemarks2GetterSetter() {
        String expectedRemarks = "Second purchase remark";
        externalService.setPurchaseRemarks2(expectedRemarks);
        assertEquals(expectedRemarks, externalService.getPurchaseRemarks2());
    }

    @Test
    @DisplayName("Should handle long purchase remarks up to 60 characters")
    void testPurchaseRemarksMaxLength() {
        String longRemarks = "A".repeat(60);
        externalService.setPurchaseRemarks1(longRemarks);
        assertEquals(60, externalService.getPurchaseRemarks1().length());
    }

    @Test
    @DisplayName("Should set and get purchase value")
    void testPurchaseValueGetterSetter() {
        BigDecimal expectedValue = new BigDecimal("9999999.99");
        externalService.setPurchaseValue(expectedValue);
        assertEquals(expectedValue, externalService.getPurchaseValue());
    }

    @Test
    @DisplayName("Should set and get surcharge percent")
    void testSurchargePercentGetterSetter() {
        String expectedPercent = "15%";
        externalService.setSurchargePercent(expectedPercent);
        assertEquals(expectedPercent, externalService.getSurchargePercent());
    }

    @Test
    @DisplayName("Should set and get sales value")
    void testSalesValueGetterSetter() {
        BigDecimal expectedValue = new BigDecimal("12500.75");
        externalService.setSalesValue(expectedValue);
        assertEquals(expectedValue, externalService.getSalesValue());
    }

    @Test
    @DisplayName("Should set and get job number")
    void testJobNumberGetterSetter() {
        String expectedJobNumber = "JOB01";
        externalService.setJobNumber(expectedJobNumber);
        assertEquals(expectedJobNumber, externalService.getJobNumber());
    }

    @Test
    @DisplayName("Should set and get area")
    void testAreaGetterSetter() {
        String expectedArea = "A";
        externalService.setArea(expectedArea);
        assertEquals(expectedArea, externalService.getArea());
    }

    @Test
    @DisplayName("Should set and get workshop type")
    void testWorkshopTypeGetterSetter() {
        String expectedType = "W";
        externalService.setWorkshopType(expectedType);
        assertEquals(expectedType, externalService.getWorkshopType());
    }

    @Test
    @DisplayName("Should set and get split")
    void testSplitGetterSetter() {
        String expectedSplit = "01";
        externalService.setSplit(expectedSplit);
        assertEquals(expectedSplit, externalService.getSplit());
    }

    @Test
    @DisplayName("Should set and get job date")
    void testJobDateGetterSetter() {
        String expectedDate = "20240115";
        externalService.setJobDate(expectedDate);
        assertEquals(expectedDate, externalService.getJobDate());
    }

    @Test
    @DisplayName("Should set and get position")
    void testPositionGetterSetter() {
        Integer expectedPosition = 1;
        externalService.setPosition(expectedPosition);
        assertEquals(expectedPosition, externalService.getPosition());
    }

    @Test
    @DisplayName("Should set and get revenue group")
    void testRevenueGroupGetterSetter() {
        String expectedGroup = "RG";
        externalService.setRevenueGroup(expectedGroup);
        assertEquals(expectedGroup, externalService.getRevenueGroup());
    }

    @Test
    @DisplayName("Should set and get invoice number")
    void testInvoiceNumberGetterSetter() {
        String expectedInvoiceNumber = "INV01";
        externalService.setInvoiceNumber(expectedInvoiceNumber);
        assertEquals(expectedInvoiceNumber, externalService.getInvoiceNumber());
    }

    @Test
    @DisplayName("Should set and get invoice date")
    void testInvoiceDateGetterSetter() {
        String expectedDate = "20240115";
        externalService.setInvoiceDate(expectedDate);
        assertEquals(expectedDate, externalService.getInvoiceDate());
    }

    @Test
    @DisplayName("Should set and get status")
    void testStatusGetterSetter() {
        String expectedStatus = "A";
        externalService.setStatus(expectedStatus);
        assertEquals(expectedStatus, externalService.getStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "I", "P", "C"})
    @DisplayName("Should handle various status values")
    void testStatusVariousValues(String status) {
        externalService.setStatus(status);
        assertEquals(status, externalService.getStatus());
    }

    @Test
    @DisplayName("Should set and get SDPS job UUID")
    void testSdpsJobUuidGetterSetter() {
        String expectedUuid = "550e8400-e29b-41d4-a716-446655440000";
        externalService.setSdpsJobUuid(expectedUuid);
        assertEquals(expectedUuid, externalService.getSdpsJobUuid());
    }

    @Test
    @DisplayName("Should set and get SDPS FLA UUID")
    void testSdpsFlaUuidGetterSetter() {
        String expectedUuid = "660e8400-e29b-41d4-a716-446655440001";
        externalService.setSdpsFlaUuid(expectedUuid);
        assertEquals(expectedUuid, externalService.getSdpsFlaUuid());
    }

    @Test
    @DisplayName("Should handle null UUID values")
    void testNullUuidValues() {
        externalService.setSdpsJobUuid(null);
        externalService.setSdpsFlaUuid(null);
        assertNull(externalService.getSdpsJobUuid());
        assertNull(externalService.getSdpsFlaUuid());
    }

    @Test
    @DisplayName("Should create fully populated ExternalService object")
    void testFullyPopulatedObject() {
        // Arrange & Act
        externalService.setId(1L);
        externalService.setCompanyCode("ABC");
        externalService.setOrderDate("20240115");
        externalService.setOrderNumber("12345");
        externalService.setLineNumberExternal(100);
        externalService.setExternalServiceId("EXT12345");
        externalService.setLineNumber(50);
        externalService.setDescription("Test Description");
        externalService.setTextLines(3);
        externalService.setPurchasePrice(new BigDecimal("100.50"));
        externalService.setQuantity(5);
        externalService.setPurchaseInvoiceNumber("INV-001");
        externalService.setPurchaseInvoiceDate("20240110");
        externalService.setPurchaseRemarks1("Remark 1");
        externalService.setPurchaseRemarks2("Remark 2");
        externalService.setPurchaseValue(new BigDecimal("502.50"));
        externalService.setSurchargePercent("10%");
        externalService.setSalesValue(new BigDecimal("552.75"));
        externalService.setJobNumber("JOB01");
        externalService.setArea("A");
        externalService.setWorkshopType("W");
        externalService.setSplit("01");
        externalService.setJobDate("20240115");
        externalService.setPosition(1);
        externalService.setRevenueGroup("RG");
        externalService.setInvoiceNumber("INV01");
        externalService.setInvoiceDate("20240115");
        externalService.setStatus("A");
        externalService.setSdpsJobUuid("550e8400-e29b-41d4-a716-446655440000");
        externalService.setSdpsFlaUuid("660e8400-e29b-41d4-a716-446655440001");

        // Assert
        assertAll("Fully populated ExternalService",
            () -> assertEquals(1L, externalService.getId()),
            () -> assertEquals("ABC", externalService.getCompanyCode()),
            () -> assertEquals("20240115", externalService.getOrderDate()),
            () -> assertEquals("12345", externalService.getOrderNumber()),
            () -> assertEquals(100, externalService.getLineNumberExternal()),
            () -> assertEquals("EXT12345", externalService.getExternalServiceId()),
            () -> assertEquals(50, externalService.getLineNumber()),
            () -> assertEquals("Test Description", externalService.getDescription()),
            () -> assertEquals(3, externalService.getTextLines()),
            () -> assertEquals(new BigDecimal("100.50"), externalService.getPurchasePrice()),
            () -> assertEquals(5, externalService.getQuantity()),
            () -> assertEquals("IN