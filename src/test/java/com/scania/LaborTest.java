# Complete JUnit Test Cases for Labor Entity

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

@DisplayName("Labor Entity Test Suite")
class LaborTest {

    private Labor labor;

    @BeforeEach
    void setUp() {
        labor = new Labor();
    }

    @Test
    @DisplayName("Should create Labor instance with default constructor")
    void testDefaultConstructor() {
        assertNotNull(labor);
        assertNull(labor.getId());
        assertNull(labor.getCompanyCode());
    }

    @Test
    @DisplayName("Should set and get ID")
    void testIdGetterSetter() {
        Long expectedId = 1L;
        labor.setId(expectedId);
        assertEquals(expectedId, labor.getId());
    }

    @Test
    @DisplayName("Should set and get company code")
    void testCompanyCodeGetterSetter() {
        String expectedCode = "ABC";
        labor.setCompanyCode(expectedCode);
        assertEquals(expectedCode, labor.getCompanyCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "ABC12", ""})
    @DisplayName("Should handle various invoice numbers")
    void testInvoiceNumberVariations(String invoiceNumber) {
        labor.setInvoiceNumber(invoiceNumber);
        assertEquals(invoiceNumber, labor.getInvoiceNumber());
    }

    @Test
    @DisplayName("Should set and get invoice number 10")
    void testInvoiceNumber10GetterSetter() {
        String expected = "1234567890";
        labor.setInvoiceNumber10(expected);
        assertEquals(expected, labor.getInvoiceNumber10());
    }

    @Test
    @DisplayName("Should set and get invoice date")
    void testInvoiceDateGetterSetter() {
        String expectedDate = "20240115";
        labor.setInvoiceDate(expectedDate);
        assertEquals(expectedDate, labor.getInvoiceDate());
    }

    @Test
    @DisplayName("Should set and get cancellation flag")
    void testCancellationFlagGetterSetter() {
        String expectedFlag = "Y";
        labor.setCancellationFlag(expectedFlag);
        assertEquals(expectedFlag, labor.getCancellationFlag());
    }

    @Test
    @DisplayName("Should set and get job number")
    void testJobNumberGetterSetter() {
        String expectedJobNumber = "JOB01";
        labor.setJobNumber(expectedJobNumber);
        assertEquals(expectedJobNumber, labor.getJobNumber());
    }

    @Test
    @DisplayName("Should set and get area")
    void testAreaGetterSetter() {
        String expectedArea = "A";
        labor.setArea(expectedArea);
        assertEquals(expectedArea, labor.getArea());
    }

    @Test
    @DisplayName("Should set and get workshop type")
    void testWorkshopTypeGetterSetter() {
        String expectedType = "W";
        labor.setWorkshopType(expectedType);
        assertEquals(expectedType, labor.getWorkshopType());
    }

    @Test
    @DisplayName("Should set and get split")
    void testSplitGetterSetter() {
        String expectedSplit = "01";
        labor.setSplit(expectedSplit);
        assertEquals(expectedSplit, labor.getSplit());
    }

    @Test
    @DisplayName("Should set and get position")
    void testPositionGetterSetter() {
        Integer expectedPosition = 100;
        labor.setPosition(expectedPosition);
        assertEquals(expectedPosition, labor.getPosition());
    }

    @Test
    @DisplayName("Should set and get entry code")
    void testEntryCodeGetterSetter() {
        String expectedCode = "EC";
        labor.setEntryCode(expectedCode);
        assertEquals(expectedCode, labor.getEntryCode());
    }

    @Test
    @DisplayName("Should set and get line number package")
    void testLineNumberPackageGetterSetter() {
        Integer expected = 123;
        labor.setLineNumberPackage(expected);
        assertEquals(expected, labor.getLineNumberPackage());
    }

    @Test
    @DisplayName("Should set and get package number")
    void testPackageNumberGetterSetter() {
        String expected = "PKG12345";
        labor.setPackageNumber(expected);
        assertEquals(expected, labor.getPackageNumber());
    }

    @Test
    @DisplayName("Should set and get sort sequence")
    void testSortSequenceGetterSetter() {
        Integer expected = 999;
        labor.setSortSequence(expected);
        assertEquals(expected, labor.getSortSequence());
    }

    @Test
    @DisplayName("Should set and get line number sequence")
    void testLineNumberSequenceGetterSetter() {
        Integer expected = 456;
        labor.setLineNumberSequence(expected);
        assertEquals(expected, labor.getLineNumberSequence());
    }

    @Test
    @DisplayName("Should set and get operation code")
    void testOperationCodeGetterSetter() {
        String expected = "OP123456";
        labor.setOperationCode(expected);
        assertEquals(expected, labor.getOperationCode());
    }

    @Test
    @DisplayName("Should set and get line number")
    void testLineNumberGetterSetter() {
        String expected = "001";
        labor.setLineNumber(expected);
        assertEquals(expected, labor.getLineNumber());
    }

    @Test
    @DisplayName("Should set and get description")
    void testDescriptionGetterSetter() {
        String expected = "Test labor description with special chars: äöü";
        labor.setDescription(expected);
        assertEquals(expected, labor.getDescription());
    }

    @Test
    @DisplayName("Should handle long description")
    void testLongDescription() {
        String longDescription = "A".repeat(40);
        labor.setDescription(longDescription);
        assertEquals(longDescription, labor.getDescription());
    }

    @Test
    @DisplayName("Should set and get factory time")
    void testFactoryTimeGetterSetter() {
        BigDecimal expected = new BigDecimal("12.50");
        labor.setFactoryTime(expected);
        assertEquals(expected, labor.getFactoryTime());
    }

    @Test
    @DisplayName("Should set and get actual hours")
    void testActualHoursGetterSetter() {
        BigDecimal expected = new BigDecimal("8.75");
        labor.setActualHours(expected);
        assertEquals(expected, labor.getActualHours());
    }

    @Test
    @DisplayName("Should set and get time units")
    void testTimeUnitsGetterSetter() {
        Integer expected = 480;
        labor.setTimeUnits(expected);
        assertEquals(expected, labor.getTimeUnits());
    }

    @Test
    @DisplayName("Should set and get price units")
    void testPriceUnitsGetterSetter() {
        Integer expected = 1000;
        labor.setPriceUnits(expected);
        assertEquals(expected, labor.getPriceUnits());
    }

    @Test
    @DisplayName("Should set and get rate price unit")
    void testRatePriceUnitGetterSetter() {
        BigDecimal expected = new BigDecimal("125.50");
        labor.setRatePriceUnit(expected);
        assertEquals(expected, labor.getRatePriceUnit());
    }

    @Test
    @DisplayName("Should set and get weighted time")
    void testWeightedTimeGetterSetter() {
        String expected = "Y";
        labor.setWeightedTime(expected);
        assertEquals(expected, labor.getWeightedTime());
    }

    @Test
    @DisplayName("Should set and get price")
    void testPriceGetterSetter() {
        BigDecimal expected = new BigDecimal("1500.99");
        labor.setPrice(expected);
        assertEquals(expected, labor.getPrice());
    }

    @Test
    @DisplayName("Should handle large price values")
    void testLargePriceValue() {
        BigDecimal largePrice = new BigDecimal("9999999.99");
        labor.setPrice(largePrice);
        assertEquals(largePrice, labor.getPrice());
    }

    @Test
    @DisplayName("Should set and get mechanic")
    void testMechanicGetterSetter() {
        String expected = "M01";
        labor.setMechanic(expected);
        assertEquals(expected, labor.getMechanic());
    }

    @Test
    @DisplayName("Should set and get business code")
    void testBusinessCodeGetterSetter() {
        String expected = "BC";
        labor.setBusinessCode(expected);
        assertEquals(expected, labor.getBusinessCode());
    }

    @Test
    @DisplayName("Should set and get calculation rate")
    void testCalculationRateGetterSetter() {
        BigDecimal expected = new BigDecimal("95.75");
        labor.setCalculationRate(expected);
        assertEquals(expected, labor.getCalculationRate());
    }

    @Test
    @DisplayName("Should set and get mechanic hours")
    void testMechanicHoursGetterSetter() {
        BigDecimal expected = new BigDecimal("10.25");
        labor.setMechanicHours(expected);
        assertEquals(expected, labor.getMechanicHours());
    }

    @Test
    @DisplayName("Should set and get calculation net")
    void testCalculationNetGetterSetter() {
        BigDecimal expected = new BigDecimal("2500.00");
        labor.setCalculationNet(expected);
        assertEquals(expected, labor.getCalculationNet());
    }

    @Test
    @DisplayName("Should set and get calculation gross")
    void testCalculationGrossGetterSetter() {
        BigDecimal expected = new BigDecimal("2975.00");
        labor.setCalculationGross(expected);
        assertEquals(expected, labor.getCalculationGross());
    }

    @Test
    @DisplayName("Should set and get calculation hours")
    void testCalculationHoursGetterSetter() {
        BigDecimal expected = new BigDecimal("15.50");
        labor.setCalculationHours(expected);
        assertEquals(expected, labor.getCalculationHours());
    }

    @Test
    @DisplayName("Should set and get surcharge")
    void testSurchargeGetterSetter() {
        BigDecimal expected = new BigDecimal("10.00");
        labor.setSurcharge(expected);
        assertEquals(expected, labor.getSurcharge());
    }

    @Test
    @DisplayName("Should set and get discount")
    void testDiscountGetterSetter() {
        BigDecimal expected = new BigDecimal("5.50");
        labor.setDiscount(expected);
        assertEquals(expected, labor.getDiscount());
    }

    @Test
    @DisplayName("Should set and get flag standard actual")
    void testFlagStandardActualGetterSetter() {
        String expected = "S";
        labor.setFlagStandardActual(expected);
        assertEquals(expected, labor.getFlagStandardActual());
    }

    @Test
    @DisplayName("Should set and get VAT flag")
    void testVatFlagGetterSetter() {
        String expected = "1";
        labor.setVatFlag(expected);
        assertEquals(expected, labor.getVatFlag());
    }

    @Test
    @DisplayName("Should set and get consolidate")
    void testConsolidateGetterSetter() {
        String expected = "Y";
        labor.setConsolidate(expected);
        assertEquals(expected, labor.getConsolidate());
    }

    @Test
    @DisplayName("Should set and get text key")
    void testTextKeyGetterSetter() {
        String expected = "TXT";
        labor.setTextKey(expected);
        assertEquals(expected, labor.getTextKey());
    }

    @Test
    @DisplayName("Should set and get invoice gross")
    void testInvoiceGrossGetterSetter() {
        BigDecimal expected = new BigDecimal("3500.00");
        labor.setInvoiceGross(expected);
        assertEquals(expected, labor.getInvoiceGross());
    }

    @Test
    @DisplayName("Should set and get invoice discount")
    void testInvoiceDiscountGetterSetter() {
        BigDecimal expected = new BigDecimal("175.00");
        labor.setInvoiceDiscount(expected);
        assertEquals(expected, labor.getInvoiceDiscount());
    }

    @Test
    @DisplayName("Should set and get invoice net")
    void testInvoiceNetGetterSetter() {
        BigDecimal expected = new BigDecimal("3325.00");
        labor.setInvoiceNet(expected);
        assertEquals(expected, labor.getInvoiceNet());
    }

    @Test
    @DisplayName("Should set and get identifier invoice to sum")
    void testIdentifierInvoiceToSumGetterSetter() {
        String expected = "Y";
        labor.setIdentifierInvoiceToSum(expected);
        assertEquals(expected, labor.getIdentifierInvoiceToSum());
    }

    @Test
    @DisplayName("Should set and get original factor hour mechanic")
    void testOriginalFactorHourMechanicGetterSetter() {
        BigDecimal expected = new BigDecimal("1.25");
        labor.setOriginalFactorHourMechanic(expected);
        assertEquals(expected, labor.getOriginalFactorHourMechanic());
    }

    @Test
    @DisplayName("Should set and get original net mechanic")
    void testOriginalNetMechanicGetterSetter() {
        BigDecimal expected = new BigDecimal("2000.00");
        labor.setOriginalNetMechanic(expected);
        assertEquals(expected, labor.getOriginalNetMechanic());
    }

    @Test
    @DisplayName("Should set and get cost price")
    void testCostPriceGetterSetter() {
        BigDecimal expected = new BigDecimal("1800.00");
        labor.setCostPrice(expected);
        assertEquals(expected, labor.getCostPrice());
    }

    @Test
    @DisplayName("Should set and get EPS name")
    void testEpsNameGetterSetter() {
        String expected = "EPS System Name";
        labor.setEpsName(expected);
        assertEquals(expected, labor.getEpsName());
    }

    @Test
    @DisplayName("Should set and get EPS reduction percent")
    void testEpsReductionPercentGetterSetter() {
        BigDecimal expected = new BigDecimal("15.50");
        labor.setEpsReductionPercent(expected);
        assertEquals(expected, labor.getEpsReductionPercent());
    }

    @Test
    @DisplayName("Should set and get variant")
    void testVariantGetterSetter() {
        String expected = "Variant description with 500 characters max";
        labor.setVariant(expected);
        assertEquals(expected, labor.getVariant());
    }

    @Test
    @DisplayName("Should handle long variant text")
    void testLongVariant() {
        String longVariant = "V".repeat(500);
        labor.setVariant(longVariant);
        assertEquals(longVariant, labor.getVariant());
    }

    @Test
    @DisplayName("Should set and get work description")
    void