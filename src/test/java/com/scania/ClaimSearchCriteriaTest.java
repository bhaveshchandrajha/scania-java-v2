# Complete JUnit Test Cases for ClaimSearchCriteria

```java
package com.scania.warranty.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimSearchCriteria Tests")
class ClaimSearchCriteriaTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create ClaimSearchCriteria with all valid parameters")
        void shouldCreateWithAllValidParameters() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001",
                "10",
                "=",
                "VEH123",
                "CUST456",
                "CLM789",
                "W",
                true,
                false,
                30,
                "search text"
            );

            // Assert
            assertNotNull(criteria);
            assertEquals("001", criteria.companyCode());
            assertEquals("10", criteria.statusFilter());
            assertEquals("=", criteria.statusOperator());
            assertEquals("VEH123", criteria.vehicleNumber());
            assertEquals("CUST456", criteria.customerNumber());
            assertEquals("CLM789", criteria.claimNumberSde());
            assertEquals("W", criteria.claimType());
            assertTrue(criteria.openClaimsOnly());
            assertFalse(criteria.minimumOnly());
            assertEquals(30, criteria.claimAgeDays());
            assertEquals("search text", criteria.searchText());
        }

        @Test
        @DisplayName("Should create ClaimSearchCriteria with null values")
        void shouldCreateWithNullValues() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
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
                null
            );

            // Assert
            assertNotNull(criteria);
            assertNull(criteria.companyCode());
            assertNull(criteria.statusFilter());
            assertEquals("=", criteria.statusOperator()); // Default value
            assertNull(criteria.vehicleNumber());
            assertNull(criteria.customerNumber());
            assertNull(criteria.claimNumberSde());
            assertNull(criteria.claimType());
            assertFalse(criteria.openClaimsOnly());
            assertFalse(criteria.minimumOnly());
            assertNull(criteria.claimAgeDays());
            assertNull(criteria.searchText());
        }

        @Test
        @DisplayName("Should create ClaimSearchCriteria with empty strings")
        void shouldCreateWithEmptyStrings() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                false,
                false,
                0,
                ""
            );

            // Assert
            assertNotNull(criteria);
            assertEquals("", criteria.companyCode());
            assertEquals("", criteria.statusFilter());
            assertEquals("=", criteria.statusOperator()); // Default value for blank
            assertEquals("", criteria.vehicleNumber());
            assertEquals("", criteria.customerNumber());
            assertEquals("", criteria.claimNumberSde());
            assertEquals("", criteria.claimType());
            assertFalse(criteria.openClaimsOnly());
            assertFalse(criteria.minimumOnly());
            assertEquals(0, criteria.claimAgeDays());
            assertEquals("", criteria.searchText());
        }
    }

    @Nested
    @DisplayName("StatusOperator Default Value Tests")
    class StatusOperatorDefaultTests {

        @Test
        @DisplayName("Should default statusOperator to '=' when null")
        void shouldDefaultToEqualsWhenNull() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001", "10", null, null, null, null, null, false, false, null, null
            );

            // Assert
            assertEquals("=", criteria.statusOperator());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @DisplayName("Should default statusOperator to '=' when blank")
        void shouldDefaultToEqualsWhenBlank(String blankValue) {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001", "10", blankValue, null, null, null, null, false, false, null, null
            );

            // Assert
            assertEquals("=", criteria.statusOperator());
        }

        @ParameterizedTest
        @ValueSource(strings = {"=", "!=", ">", "<", ">=", "<=", "LIKE"})
        @DisplayName("Should preserve valid statusOperator values")
        void shouldPreserveValidStatusOperator(String operator) {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001", "10", operator, null, null, null, null, false, false, null, null
            );

            // Assert
            assertEquals(operator, criteria.statusOperator());
        }
    }

    @Nested
    @DisplayName("Boolean Field Tests")
    class BooleanFieldTests {

        @Test
        @DisplayName("Should handle openClaimsOnly as true")
        void shouldHandleOpenClaimsOnlyTrue() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, true, false, null, null
            );

            // Assert
            assertTrue(criteria.openClaimsOnly());
        }

        @Test
        @DisplayName("Should handle openClaimsOnly as false")
        void shouldHandleOpenClaimsOnlyFalse() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, false, false, null, null
            );

            // Assert
            assertFalse(criteria.openClaimsOnly());
        }

        @Test
        @DisplayName("Should handle minimumOnly as true")
        void shouldHandleMinimumOnlyTrue() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, false, true, null, null
            );

            // Assert
            assertTrue(criteria.minimumOnly());
        }

        @Test
        @DisplayName("Should handle minimumOnly as false")
        void shouldHandleMinimumOnlyFalse() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, false, false, null, null
            );

            // Assert
            assertFalse(criteria.minimumOnly());
        }

        @Test
        @DisplayName("Should handle both boolean flags as true")
        void shouldHandleBothBooleanFlagsTrue() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, true, true, null, null
            );

            // Assert
            assertTrue(criteria.openClaimsOnly());
            assertTrue(criteria.minimumOnly());
        }
    }

    @Nested
    @DisplayName("ClaimAgeDays Tests")
    class ClaimAgeDaysTests {

        @Test
        @DisplayName("Should handle positive claimAgeDays")
        void shouldHandlePositiveClaimAgeDays() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, false, false, 19, null
            );

            // Assert
            assertEquals(19, criteria.claimAgeDays());
        }

        @Test
        @DisplayName("Should handle zero claimAgeDays")
        void shouldHandleZeroClaimAgeDays() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, false, false, 0, null
            );

            // Assert
            assertEquals(0, criteria.claimAgeDays());
        }

        @Test
        @DisplayName("Should handle null claimAgeDays")
        void shouldHandleNullClaimAgeDays() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, false, false, null, null
            );

            // Assert
            assertNull(criteria.claimAgeDays());
        }

        @Test
        @DisplayName("Should handle large claimAgeDays value")
        void shouldHandleLargeClaimAgeDays() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                null, null, null, null, null, null, null, false, false, 365, null
            );

            // Assert
            assertEquals(365, criteria.claimAgeDays());
        }
    }

    @Nested
    @DisplayName("Record Equality Tests")
    class RecordEqualityTests {

        @Test
        @DisplayName("Should be equal when all fields match")
        void shouldBeEqualWhenAllFieldsMatch() {
            // Arrange
            ClaimSearchCriteria criteria1 = new ClaimSearchCriteria(
                "001", "10", "=", "VEH123", "CUST456", "CLM789", "W", true, false, 30, "search"
            );
            ClaimSearchCriteria criteria2 = new ClaimSearchCriteria(
                "001", "10", "=", "VEH123", "CUST456", "CLM789", "W", true, false, 30, "search"
            );

            // Assert
            assertEquals(criteria1, criteria2);
            assertEquals(criteria1.hashCode(), criteria2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when fields differ")
        void shouldNotBeEqualWhenFieldsDiffer() {
            // Arrange
            ClaimSearchCriteria criteria1 = new ClaimSearchCriteria(
                "001", "10", "=", "VEH123", "CUST456", "CLM789", "W", true, false, 30, "search"
            );
            ClaimSearchCriteria criteria2 = new ClaimSearchCriteria(
                "002", "10", "=", "VEH123", "CUST456", "CLM789", "W", true, false, 30, "search"
            );

            // Assert
            assertNotEquals(criteria1, criteria2);
        }

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            // Arrange
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001", "10", "=", "VEH123", "CUST456", "CLM789", "W", true, false, 30, "search"
            );

            // Assert
            assertEquals(criteria, criteria);
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            // Arrange
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001", "10", "=", "VEH123", "CUST456", "CLM789", "W", true, false, 30, "search"
            );

            // Assert
            assertNotEquals(null, criteria);
        }
    }

    @Nested
    @DisplayName("Record toString Tests")
    class RecordToStringTests {

        @Test
        @DisplayName("Should generate toString with all fields")
        void shouldGenerateToStringWithAllFields() {
            // Arrange
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001", "10", "=", "VEH123", "CUST456", "CLM789", "W", true, false, 30, "search"
            );

            // Act
            String result = criteria.toString();

            // Assert
            assertNotNull(result);
            assertTrue(result.contains("ClaimSearchCriteria"));
            assertTrue(result.contains("companyCode=001"));
            assertTrue(result.contains("statusFilter=10"));
            assertTrue(result.contains("statusOperator=="));
            assertTrue(result.contains("vehicleNumber=VEH123"));
            assertTrue(result.contains("customerNumber=CUST456"));
            assertTrue(result.contains("claimNumberSde=CLM789"));
            assertTrue(result.contains("claimType=W"));
            assertTrue(result.contains("openClaimsOnly=true"));
            assertTrue(result.contains("minimumOnly=false"));
            assertTrue(result.contains("claimAgeDays=30"));
            assertTrue(result.contains("searchText=search"));
        }
    }

    @Nested
    @DisplayName("Business Scenario Tests")
    class BusinessScenarioTests {

        @Test
        @DisplayName("Should create criteria for warranty claim search within 19 days")
        void shouldCreateCriteriaForWarrantyClaimWithin19Days() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001",
                "00",
                "=",
                null,
                null,
                null,
                "W",
                true,
                false,
                19,
                null
            );

            // Assert
            assertEquals("001", criteria.companyCode());
            assertEquals("00", criteria.statusFilter());
            assertEquals("W", criteria.claimType());
            assertTrue(criteria.openClaimsOnly());
            assertEquals(19, criteria.claimAgeDays());
        }

        @Test
        @DisplayName("Should create criteria for sent claims search")
        void shouldCreateCriteriaForSentClaims() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001",
                "10",
                "=",
                null,
                null,
                null,
                null,
                false,
                false,
                null,
                null
            );

            // Assert
            assertEquals("001", criteria.companyCode());
            assertEquals("10", criteria.statusFilter());
            assertEquals("=", criteria.statusOperator());
            assertFalse(criteria.openClaimsOnly());
        }

        @Test
        @DisplayName("Should create criteria for vehicle-specific search")
        void shouldCreateCriteriaForVehicleSpecificSearch() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001",
                null,
                null,
                "VEH12345",
                null,
                null,
                null,
                false,
                false,
                null,
                null
            );

            // Assert
            assertEquals("001", criteria.companyCode());
            assertEquals("VEH12345", criteria.vehicleNumber());
            assertEquals("=", criteria.statusOperator()); // Default
        }

        @Test
        @DisplayName("Should create criteria for customer-specific search")
        void shouldCreateCriteriaForCustomerSpecificSearch() {
            // Arrange & Act
            ClaimSearchCriteria criteria = new ClaimSearchCriteria(
                "001",
                null,
                null,
                null,
                "CUST789",