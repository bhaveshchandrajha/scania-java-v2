# Complete JUnit Test Cases for ClaimStatus Enum

```java
package com.scania.warranty.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimStatus Enum Tests")
class ClaimStatusTest {

    @Test
    @DisplayName("Should return correct code for CREATED status")
    void testCreatedStatusCode() {
        assertEquals(0, ClaimStatus.CREATED.getCode());
    }

    @Test
    @DisplayName("Should return correct code for PENDING status")
    void testPendingStatusCode() {
        assertEquals(2, ClaimStatus.PENDING.getCode());
    }

    @Test
    @DisplayName("Should return correct code for SUBMITTED status")
    void testSubmittedStatusCode() {
        assertEquals(3, ClaimStatus.SUBMITTED.getCode());
    }

    @Test
    @DisplayName("Should return correct code for MINIMUM_REQUEST status")
    void testMinimumRequestStatusCode() {
        assertEquals(5, ClaimStatus.MINIMUM_REQUEST.getCode());
    }

    @Test
    @DisplayName("Should return correct code for APPROVED status")
    void testApprovedStatusCode() {
        assertEquals(10, ClaimStatus.APPROVED.getCode());
    }

    @Test
    @DisplayName("Should return correct code for REJECTED status")
    void testRejectedStatusCode() {
        assertEquals(11, ClaimStatus.REJECTED.getCode());
    }

    @Test
    @DisplayName("Should return correct code for ERROR status")
    void testErrorStatusCode() {
        assertEquals(16, ClaimStatus.ERROR.getCode());
    }

    @Test
    @DisplayName("Should return correct code for MINIMUM_POSTED status")
    void testMinimumPostedStatusCode() {
        assertEquals(20, ClaimStatus.MINIMUM_POSTED.getCode());
    }

    @Test
    @DisplayName("Should return correct code for PENDING_MANUAL status")
    void testPendingManualStatusCode() {
        assertEquals(30, ClaimStatus.PENDING_MANUAL.getCode());
    }

    @Test
    @DisplayName("Should return correct code for EXCLUDED status")
    void testExcludedStatusCode() {
        assertEquals(99, ClaimStatus.EXCLUDED.getCode());
    }

    @Test
    @DisplayName("Should return correct description for CREATED status")
    void testCreatedStatusDescription() {
        assertEquals("Created", ClaimStatus.CREATED.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for PENDING status")
    void testPendingStatusDescription() {
        assertEquals("Pending", ClaimStatus.PENDING.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for SUBMITTED status")
    void testSubmittedStatusDescription() {
        assertEquals("Submitted", ClaimStatus.SUBMITTED.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for MINIMUM_REQUEST status")
    void testMinimumRequestStatusDescription() {
        assertEquals("Minimum Request", ClaimStatus.MINIMUM_REQUEST.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for APPROVED status")
    void testApprovedStatusDescription() {
        assertEquals("Approved", ClaimStatus.APPROVED.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for REJECTED status")
    void testRejectedStatusDescription() {
        assertEquals("Rejected", ClaimStatus.REJECTED.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for ERROR status")
    void testErrorStatusDescription() {
        assertEquals("Error", ClaimStatus.ERROR.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for MINIMUM_POSTED status")
    void testMinimumPostedStatusDescription() {
        assertEquals("Minimum Posted", ClaimStatus.MINIMUM_POSTED.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for PENDING_MANUAL status")
    void testPendingManualStatusDescription() {
        assertEquals("Pending Manual Processing", ClaimStatus.PENDING_MANUAL.getDescription());
    }

    @Test
    @DisplayName("Should return correct description for EXCLUDED status")
    void testExcludedStatusDescription() {
        assertEquals("Excluded", ClaimStatus.EXCLUDED.getDescription());
    }

    @ParameterizedTest
    @CsvSource({
        "0, CREATED",
        "2, PENDING",
        "3, SUBMITTED",
        "5, MINIMUM_REQUEST",
        "10, APPROVED",
        "11, REJECTED",
        "16, ERROR",
        "20, MINIMUM_POSTED",
        "30, PENDING_MANUAL",
        "99, EXCLUDED"
    })
    @DisplayName("Should return correct ClaimStatus from valid code")
    void testFromCodeWithValidCodes(int code, ClaimStatus expectedStatus) {
        ClaimStatus result = ClaimStatus.fromCode(code);
        assertEquals(expectedStatus, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 1, 4, 6, 7, 8, 9, 12, 13, 14, 15, 17, 18, 19, 21, 50, 100, 999})
    @DisplayName("Should return null for invalid codes")
    void testFromCodeWithInvalidCodes(int invalidCode) {
        ClaimStatus result = ClaimStatus.fromCode(invalidCode);
        assertNull(result);
    }

    @Test
    @DisplayName("Should return null for negative code")
    void testFromCodeWithNegativeCode() {
        assertNull(ClaimStatus.fromCode(-1));
    }

    @Test
    @DisplayName("Should return null for very large code")
    void testFromCodeWithVeryLargeCode() {
        assertNull(ClaimStatus.fromCode(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("Should have exactly 10 enum values")
    void testEnumValuesCount() {
        assertEquals(10, ClaimStatus.values().length);
    }

    @Test
    @DisplayName("Should contain all expected enum constants")
    void testAllEnumConstantsExist() {
        ClaimStatus[] values = ClaimStatus.values();
        
        assertTrue(containsStatus(values, ClaimStatus.CREATED));
        assertTrue(containsStatus(values, ClaimStatus.PENDING));
        assertTrue(containsStatus(values, ClaimStatus.SUBMITTED));
        assertTrue(containsStatus(values, ClaimStatus.MINIMUM_REQUEST));
        assertTrue(containsStatus(values, ClaimStatus.APPROVED));
        assertTrue(containsStatus(values, ClaimStatus.REJECTED));
        assertTrue(containsStatus(values, ClaimStatus.ERROR));
        assertTrue(containsStatus(values, ClaimStatus.MINIMUM_POSTED));
        assertTrue(containsStatus(values, ClaimStatus.PENDING_MANUAL));
        assertTrue(containsStatus(values, ClaimStatus.EXCLUDED));
    }

    @Test
    @DisplayName("Should have unique codes for all statuses")
    void testUniqueCodesForAllStatuses() {
        ClaimStatus[] values = ClaimStatus.values();
        
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                assertNotEquals(values[i].getCode(), values[j].getCode(),
                    String.format("Duplicate code found: %s and %s both have code %d",
                        values[i], values[j], values[i].getCode()));
            }
        }
    }

    @Test
    @DisplayName("Should convert enum to string correctly")
    void testEnumToString() {
        assertEquals("CREATED", ClaimStatus.CREATED.toString());
        assertEquals("APPROVED", ClaimStatus.APPROVED.toString());
        assertEquals("EXCLUDED", ClaimStatus.EXCLUDED.toString());
    }

    @Test
    @DisplayName("Should support valueOf method")
    void testValueOf() {
        assertEquals(ClaimStatus.CREATED, ClaimStatus.valueOf("CREATED"));
        assertEquals(ClaimStatus.APPROVED, ClaimStatus.valueOf("APPROVED"));
        assertEquals(ClaimStatus.EXCLUDED, ClaimStatus.valueOf("EXCLUDED"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException for invalid valueOf")
    void testValueOfWithInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            ClaimStatus.valueOf("INVALID_STATUS");
        });
    }

    @Test
    @DisplayName("Should throw NullPointerException for null valueOf")
    void testValueOfWithNull() {
        assertThrows(NullPointerException.class, () -> {
            ClaimStatus.valueOf(null);
        });
    }

    @Test
    @DisplayName("Should maintain enum equality")
    void testEnumEquality() {
        ClaimStatus status1 = ClaimStatus.CREATED;
        ClaimStatus status2 = ClaimStatus.CREATED;
        ClaimStatus status3 = ClaimStatus.APPROVED;
        
        assertSame(status1, status2);
        assertNotSame(status1, status3);
        assertEquals(status1, status2);
        assertNotEquals(status1, status3);
    }

    @Test
    @DisplayName("Should support switch statements")
    void testSwitchStatement() {
        String result = switch (ClaimStatus.APPROVED) {
            case CREATED -> "New";
            case APPROVED -> "Success";
            case REJECTED -> "Failed";
            default -> "Other";
        };
        
        assertEquals("Success", result);
    }

    @Test
    @DisplayName("Should return consistent hashCode")
    void testHashCode() {
        ClaimStatus status = ClaimStatus.CREATED;
        int hashCode1 = status.hashCode();
        int hashCode2 = status.hashCode();
        
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("Should handle fromCode with boundary values")
    void testFromCodeBoundaryValues() {
        assertNull(ClaimStatus.fromCode(Integer.MIN_VALUE));
        assertNull(ClaimStatus.fromCode(Integer.MAX_VALUE));
        assertNotNull(ClaimStatus.fromCode(0));
        assertNotNull(ClaimStatus.fromCode(99));
    }

    @ParameterizedTest
    @CsvSource({
        "CREATED, 0, Created",
        "PENDING, 2, Pending",
        "SUBMITTED, 3, Submitted",
        "MINIMUM_REQUEST, 5, Minimum Request",
        "APPROVED, 10, Approved",
        "REJECTED, 11, Rejected",
        "ERROR, 16, Error",
        "MINIMUM_POSTED, 20, Minimum Posted",
        "PENDING_MANUAL, 30, Pending Manual Processing",
        "EXCLUDED, 99, Excluded"
    })
    @DisplayName("Should have correct code and description mapping")
    void testCodeAndDescriptionMapping(String statusName, int expectedCode, String expectedDescription) {
        ClaimStatus status = ClaimStatus.valueOf(statusName);
        
        assertEquals(expectedCode, status.getCode());
        assertEquals(expectedDescription, status.getDescription());
    }

    // Helper method
    private boolean containsStatus(ClaimStatus[] values, ClaimStatus status) {
        for (ClaimStatus value : values) {
            if (value == status) {
                return true;
            }
        }
        return false;
    }
}
```

## Test Coverage Summary

These test cases provide comprehensive coverage for the `ClaimStatus` enum:

1. **Individual Status Tests**: Tests for each enum constant's code and description
2. **fromCode() Method Tests**: 
   - Valid codes
   - Invalid codes
   - Boundary values
   - Null handling
3. **Enum Behavior Tests**:
   - values() method
   - valueOf() method
   - toString() method
   - Equality and hashCode
4. **Business Logic Tests**:
   - Unique codes validation
   - Complete enum set verification
   - Code-description mapping
5. **Edge Cases**:
   - Negative values
   - Large values
   - Null inputs
   - Invalid enum names

The tests use JUnit 5 features including parameterized tests for efficient testing of multiple scenarios.