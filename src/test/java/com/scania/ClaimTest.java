# Complete JUnit Test Cases for Claim Entity

```java
package com.scania.warranty.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Claim Entity Tests")
class ClaimTest {

    private Claim claim;

    @BeforeEach
    void setUp() {
        claim = new Claim();
    }

    @Test
    @DisplayName("Should create Claim with default constructor")
    void testDefaultConstructor() {
        assertNotNull(claim);
        assertNull(claim.getId());
        assertNull(claim.getCompanyCode());
        assertNull(claim.getInvoiceNumber());
        assertNull(claim.getClaimNumber());
    }

    @Test
    @DisplayName("Should set and get Id")
    void testIdGetterSetter() {
        Long expectedId = 12345L;
        claim.setId(expectedId);
        assertEquals(expectedId, claim.getId());
    }

    @Test
    @DisplayName("Should set and get CompanyCode")
    void testCompanyCodeGetterSetter() {
        String expectedCode = "ABC";
        claim.setCompanyCode(expectedCode);
        assertEquals(expectedCode, claim.getCompanyCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABC", "XYZ", "123"})
    @DisplayName("Should handle various CompanyCode values")
    void testCompanyCodeWithMultipleValues(String code) {
        claim.setCompanyCode(code);
        assertEquals(code, claim.getCompanyCode());
    }

    @Test
    @DisplayName("Should set and get InvoiceNumber")
    void testInvoiceNumberGetterSetter() {
        String expectedInvoice = "12345";
        claim.setInvoiceNumber(expectedInvoice);
        assertEquals(expectedInvoice, claim.getInvoiceNumber());
    }

    @Test
    @DisplayName("Should set and get InvoiceDate")
    void testInvoiceDateGetterSetter() {
        String expectedDate = "20240115";
        claim.setInvoiceDate(expectedDate);
        assertEquals(expectedDate, claim.getInvoiceDate());
    }

    @Test
    @DisplayName("Should set and get JobNumber")
    void testJobNumberGetterSetter() {
        String expectedJobNumber = "JOB01";
        claim.setJobNumber(expectedJobNumber);
        assertEquals(expectedJobNumber, claim.getJobNumber());
    }

    @Test
    @DisplayName("Should set and get WorkshopType")
    void testWorkshopTypeGetterSetter() {
        String expectedType = "A";
        claim.setWorkshopType(expectedType);
        assertEquals(expectedType, claim.getWorkshopType());
    }

    @Test
    @DisplayName("Should set and get ClaimNumber")
    void testClaimNumberGetterSetter() {
        String expectedClaimNumber = "CLM12345";
        claim.setClaimNumber(expectedClaimNumber);
        assertEquals(expectedClaimNumber, claim.getClaimNumber());
    }

    @Test
    @DisplayName("Should set and get ChassisNumber")
    void testChassisNumberGetterSetter() {
        String expectedChassis = "1234567";
        claim.setChassisNumber(expectedChassis);
        assertEquals(expectedChassis, claim.getChassisNumber());
    }

    @Test
    @DisplayName("Should set and get LicensePlate")
    void testLicensePlateGetterSetter() {
        String expectedPlate = "ABC-1234";
        claim.setLicensePlate(expectedPlate);
        assertEquals(expectedPlate, claim.getLicensePlate());
    }

    @Test
    @DisplayName("Should set and get RegistrationDate")
    void testRegistrationDateGetterSetter() {
        Integer expectedDate = 20240115;
        claim.setRegistrationDate(expectedDate);
        assertEquals(expectedDate, claim.getRegistrationDate());
    }

    @ParameterizedTest
    @ValueSource(ints = {20240115, 20230101, 20221231})
    @DisplayName("Should handle various RegistrationDate values")
    void testRegistrationDateWithMultipleValues(Integer date) {
        claim.setRegistrationDate(date);
        assertEquals(date, claim.getRegistrationDate());
    }

    @Test
    @DisplayName("Should set and get RepairDate")
    void testRepairDateGetterSetter() {
        Integer expectedDate = 20240120;
        claim.setRepairDate(expectedDate);
        assertEquals(expectedDate, claim.getRepairDate());
    }

    @Test
    @DisplayName("Should set and get Mileage")
    void testMileageGetterSetter() {
        Integer expectedMileage = 150;
        claim.setMileage(expectedMileage);
        assertEquals(expectedMileage, claim.getMileage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100, 500, 999})
    @DisplayName("Should handle various Mileage values")
    void testMileageWithMultipleValues(Integer mileage) {
        claim.setMileage(mileage);
        assertEquals(mileage, claim.getMileage());
    }

    @Test
    @DisplayName("Should set and get ProductType")
    void testProductTypeGetterSetter() {
        Integer expectedType = 1;
        claim.setProductType(expectedType);
        assertEquals(expectedType, claim.getProductType());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 9})
    @DisplayName("Should handle various ProductType values")
    void testProductTypeWithMultipleValues(Integer type) {
        claim.setProductType(type);
        assertEquals(type, claim.getProductType());
    }

    @Test
    @DisplayName("Should set and get Attachment")
    void testAttachmentGetterSetter() {
        String expectedAttachment = "Y";
        claim.setAttachment(expectedAttachment);
        assertEquals(expectedAttachment, claim.getAttachment());
    }

    @Test
    @DisplayName("Should set and get Foreigner")
    void testForeignerGetterSetter() {
        String expectedForeigner = "N";
        claim.setForeigner(expectedForeigner);
        assertEquals(expectedForeigner, claim.getForeigner());
    }

    @Test
    @DisplayName("Should set and get CustomerNumber")
    void testCustomerNumberGetterSetter() {
        String expectedCustomerNumber = "CUST01";
        claim.setCustomerNumber(expectedCustomerNumber);
        assertEquals(expectedCustomerNumber, claim.getCustomerNumber());
    }

    @Test
    @DisplayName("Should set and get CustomerName")
    void testCustomerNameGetterSetter() {
        String expectedName = "John Doe Workshop";
        claim.setCustomerName(expectedName);
        assertEquals(expectedName, claim.getCustomerName());
    }

    @Test
    @DisplayName("Should set and get ClaimNumberSde")
    void testClaimNumberSdeGetterSetter() {
        String expectedClaimNumberSde = "SDE12345";
        claim.setClaimNumberSde(expectedClaimNumberSde);
        assertEquals(expectedClaimNumberSde, claim.getClaimNumberSde());
    }

    @Test
    @DisplayName("Should set and get StatusCodeSde")
    void testStatusCodeSdeGetterSetter() {
        Integer expectedStatus = 10;
        claim.setStatusCodeSde(expectedStatus);
        assertEquals(expectedStatus, claim.getStatusCodeSde());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 20, 99})
    @DisplayName("Should handle various StatusCodeSde values")
    void testStatusCodeSdeWithMultipleValues(Integer status) {
        claim.setStatusCodeSde(status);
        assertEquals(status, claim.getStatusCodeSde());
    }

    @Test
    @DisplayName("Should set and get NumberOfFailures")
    void testNumberOfFailuresGetterSetter() {
        Integer expectedFailures = 5;
        claim.setNumberOfFailures(expectedFailures);
        assertEquals(expectedFailures, claim.getNumberOfFailures());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 5, 9})
    @DisplayName("Should handle various NumberOfFailures values (max 9)")
    void testNumberOfFailuresWithMultipleValues(Integer failures) {
        claim.setNumberOfFailures(failures);
        assertEquals(failures, claim.getNumberOfFailures());
    }

    @Test
    @DisplayName("Should set and get Area")
    void testAreaGetterSetter() {
        String expectedArea = "A";
        claim.setArea(expectedArea);
        assertEquals(expectedArea, claim.getArea());
    }

    @Test
    @DisplayName("Should set and get JobNumberSdps")
    void testJobNumberSdpsGetterSetter() {
        String expectedJobNumberSdps = "SDPS123456";
        claim.setJobNumberSdps(expectedJobNumberSdps);
        assertEquals(expectedJobNumberSdps, claim.getJobNumberSdps());
    }

    @Test
    @DisplayName("Should handle null values for all String fields")
    void testNullStringValues() {
        claim.setCompanyCode(null);
        claim.setInvoiceNumber(null);
        claim.setClaimNumber(null);
        claim.setCustomerName(null);
        
        assertNull(claim.getCompanyCode());
        assertNull(claim.getInvoiceNumber());
        assertNull(claim.getClaimNumber());
        assertNull(claim.getCustomerName());
    }

    @Test
    @DisplayName("Should handle null values for all Integer fields")
    void testNullIntegerValues() {
        claim.setRegistrationDate(null);
        claim.setRepairDate(null);
        claim.setMileage(null);
        claim.setStatusCodeSde(null);
        
        assertNull(claim.getRegistrationDate());
        assertNull(claim.getRepairDate());
        assertNull(claim.getMileage());
        assertNull(claim.getStatusCodeSde());
    }

    @Test
    @DisplayName("Should create fully populated Claim object")
    void testFullyPopulatedClaim() {
        claim.setId(1L);
        claim.setCompanyCode("ABC");
        claim.setInvoiceNumber("12345");
        claim.setInvoiceDate("20240115");
        claim.setJobNumber("JOB01");
        claim.setWorkshopType("A");
        claim.setClaimNumber("CLM12345");
        claim.setChassisNumber("1234567");
        claim.setLicensePlate("ABC-1234");
        claim.setRegistrationDate(20240115);
        claim.setRepairDate(20240120);
        claim.setMileage(150);
        claim.setProductType(1);
        claim.setAttachment("Y");
        claim.setForeigner("N");
        claim.setCustomerNumber("CUST01");
        claim.setCustomerName("John Doe Workshop");
        claim.setClaimNumberSde("SDE12345");
        claim.setStatusCodeSde(10);
        claim.setNumberOfFailures(5);
        claim.setArea("A");
        claim.setJobNumberSdps("SDPS123456");

        assertAll("Fully populated claim",
            () -> assertEquals(1L, claim.getId()),
            () -> assertEquals("ABC", claim.getCompanyCode()),
            () -> assertEquals("12345", claim.getInvoiceNumber()),
            () -> assertEquals("20240115", claim.getInvoiceDate()),
            () -> assertEquals("JOB01", claim.getJobNumber()),
            () -> assertEquals("A", claim.getWorkshopType()),
            () -> assertEquals("CLM12345", claim.getClaimNumber()),
            () -> assertEquals("1234567", claim.getChassisNumber()),
            () -> assertEquals("ABC-1234", claim.getLicensePlate()),
            () -> assertEquals(20240115, claim.getRegistrationDate()),
            () -> assertEquals(20240120, claim.getRepairDate()),
            () -> assertEquals(150, claim.getMileage()),
            () -> assertEquals(1, claim.getProductType()),
            () -> assertEquals("Y", claim.getAttachment()),
            () -> assertEquals("N", claim.getForeigner()),
            () -> assertEquals("CUST01", claim.getCustomerNumber()),
            () -> assertEquals("John Doe Workshop", claim.getCustomerName()),
            () -> assertEquals("SDE12345", claim.getClaimNumberSde()),
            () -> assertEquals(10, claim.getStatusCodeSde()),
            () -> assertEquals(5, claim.getNumberOfFailures()),
            () -> assertEquals("A", claim.getArea()),
            () -> assertEquals("SDPS123456", claim.getJobNumberSdps())
        );
    }

    @Test
    @DisplayName("Should handle empty strings for String fields")
    void testEmptyStringValues() {
        claim.setCompanyCode("");
        claim.setInvoiceNumber("");
        claim.setCustomerName("");
        
        assertEquals("", claim.getCompanyCode());
        assertEquals("", claim.getInvoiceNumber());
        assertEquals("", claim.getCustomerName());
    }

    @Test
    @DisplayName("Should handle boundary values for Integer fields")
    void testBoundaryIntegerValues() {
        claim.setMileage(0);
        claim.setNumberOfFailures(9);
        claim.setStatusCodeSde(99);
        
        assertEquals(0, claim.getMileage());
        assertEquals(9, claim.getNumberOfFailures());
        assertEquals(99, claim.getStatusCodeSde());
    }

    @Test
    @DisplayName("Should maintain data integrity after multiple updates")
    void testMultipleUpdates() {
        claim.setClaimNumber("CLM001");
        assertEquals("CLM001", claim.getClaimNumber());
        
        claim.setClaimNumber("CLM002");
        assertEquals("CLM002", claim.getClaimNumber());
        
        claim.setClaimNumber("CLM003");
        assertEquals("CLM003", claim.getClaimNumber());
    }

    @Test
    @DisplayName("Should handle special characters in String fields")
    void testSpecialCharactersInStrings() {
        claim.setCustomerName("Müller & Söhne GmbH");
        claim.setLicensePlate("AB-CD 1234");
        
        assertEquals("Müller & Söhne GmbH", claim.getCustomerName());
        assertEquals("AB-CD 1234", claim.getLicensePlate());
    }
}
```

## Additional Integration Test for JPA

```java
package com.scania.warranty.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClaimJpaTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testPersistAndRetrieveClaim() {
        // Given
        Claim claim = new Claim