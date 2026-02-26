# JUnit Test Cases for ClaimSearchResultDto

```java
package com.scania.warranty.dto;

import com.scania.warranty.domain.ClaimSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimSearchResultDto Tests")
class ClaimSearchResultDtoTest {

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create ClaimSearchResultDto with valid parameters")
        void shouldCreateClaimSearchResultDtoWithValidParameters() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            int totalCount = 10;
            ClaimSearchCriteria criteria = createSampleCriteria();

            // Act
            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, totalCount, criteria);

            // Assert
            assertNotNull(result);
            assertEquals(claims, result.claims());
            assertEquals(totalCount, result.totalCount());
            assertEquals(criteria, result.criteria());
        }

        @Test
        @DisplayName("Should create ClaimSearchResultDto with empty claims list")
        void shouldCreateClaimSearchResultDtoWithEmptyClaimsList() {
            // Arrange
            List<ClaimListItemDto> claims = Collections.emptyList();
            int totalCount = 0;
            ClaimSearchCriteria criteria = createSampleCriteria();

            // Act
            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, totalCount, criteria);

            // Assert
            assertNotNull(result);
            assertTrue(result.claims().isEmpty());
            assertEquals(0, result.totalCount());
            assertEquals(criteria, result.criteria());
        }

        @Test
        @DisplayName("Should create ClaimSearchResultDto with null claims list")
        void shouldCreateClaimSearchResultDtoWithNullClaimsList() {
            // Arrange
            int totalCount = 0;
            ClaimSearchCriteria criteria = createSampleCriteria();

            // Act
            ClaimSearchResultDto result = new ClaimSearchResultDto(null, totalCount, criteria);

            // Assert
            assertNotNull(result);
            assertNull(result.claims());
            assertEquals(0, result.totalCount());
            assertEquals(criteria, result.criteria());
        }

        @Test
        @DisplayName("Should create ClaimSearchResultDto with null criteria")
        void shouldCreateClaimSearchResultDtoWithNullCriteria() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            int totalCount = 5;

            // Act
            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, totalCount, null);

            // Assert
            assertNotNull(result);
            assertEquals(claims, result.claims());
            assertEquals(totalCount, result.totalCount());
            assertNull(result.criteria());
        }

        @Test
        @DisplayName("Should create ClaimSearchResultDto with zero total count")
        void shouldCreateClaimSearchResultDtoWithZeroTotalCount() {
            // Arrange
            List<ClaimListItemDto> claims = Collections.emptyList();
            int totalCount = 0;
            ClaimSearchCriteria criteria = createSampleCriteria();

            // Act
            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, totalCount, criteria);

            // Assert
            assertNotNull(result);
            assertEquals(0, result.totalCount());
        }

        @Test
        @DisplayName("Should create ClaimSearchResultDto with negative total count")
        void shouldCreateClaimSearchResultDtoWithNegativeTotalCount() {
            // Arrange
            List<ClaimListItemDto> claims = Collections.emptyList();
            int totalCount = -1;
            ClaimSearchCriteria criteria = createSampleCriteria();

            // Act
            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, totalCount, criteria);

            // Assert
            assertNotNull(result);
            assertEquals(-1, result.totalCount());
        }
    }

    @Nested
    @DisplayName("Accessor Method Tests")
    class AccessorMethodTests {

        @Test
        @DisplayName("Should return correct claims list")
        void shouldReturnCorrectClaimsList() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, 5, createSampleCriteria());

            // Act
            List<ClaimListItemDto> retrievedClaims = result.claims();

            // Assert
            assertNotNull(retrievedClaims);
            assertEquals(claims.size(), retrievedClaims.size());
            assertEquals(claims, retrievedClaims);
        }

        @Test
        @DisplayName("Should return correct total count")
        void shouldReturnCorrectTotalCount() {
            // Arrange
            int expectedCount = 42;
            ClaimSearchResultDto result = new ClaimSearchResultDto(
                createSampleClaimList(), 
                expectedCount, 
                createSampleCriteria()
            );

            // Act
            int actualCount = result.totalCount();

            // Assert
            assertEquals(expectedCount, actualCount);
        }

        @Test
        @DisplayName("Should return correct criteria")
        void shouldReturnCorrectCriteria() {
            // Arrange
            ClaimSearchCriteria criteria = createSampleCriteria();
            ClaimSearchResultDto result = new ClaimSearchResultDto(
                createSampleClaimList(), 
                10, 
                criteria
            );

            // Act
            ClaimSearchCriteria retrievedCriteria = result.criteria();

            // Assert
            assertNotNull(retrievedCriteria);
            assertEquals(criteria, retrievedCriteria);
        }
    }

    @Nested
    @DisplayName("Equality and HashCode Tests")
    class EqualityAndHashCodeTests {

        @Test
        @DisplayName("Should be equal when all fields are the same")
        void shouldBeEqualWhenAllFieldsAreTheSame() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            int totalCount = 10;
            ClaimSearchCriteria criteria = createSampleCriteria();

            ClaimSearchResultDto result1 = new ClaimSearchResultDto(claims, totalCount, criteria);
            ClaimSearchResultDto result2 = new ClaimSearchResultDto(claims, totalCount, criteria);

            // Assert
            assertEquals(result1, result2);
            assertEquals(result1.hashCode(), result2.hashCode());
        }

        @Test
        @DisplayName("Should not be equal when claims differ")
        void shouldNotBeEqualWhenClaimsDiffer() {
            // Arrange
            List<ClaimListItemDto> claims1 = createSampleClaimList();
            List<ClaimListItemDto> claims2 = new ArrayList<>();
            ClaimSearchCriteria criteria = createSampleCriteria();

            ClaimSearchResultDto result1 = new ClaimSearchResultDto(claims1, 10, criteria);
            ClaimSearchResultDto result2 = new ClaimSearchResultDto(claims2, 10, criteria);

            // Assert
            assertNotEquals(result1, result2);
        }

        @Test
        @DisplayName("Should not be equal when total count differs")
        void shouldNotBeEqualWhenTotalCountDiffers() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            ClaimSearchCriteria criteria = createSampleCriteria();

            ClaimSearchResultDto result1 = new ClaimSearchResultDto(claims, 10, criteria);
            ClaimSearchResultDto result2 = new ClaimSearchResultDto(claims, 20, criteria);

            // Assert
            assertNotEquals(result1, result2);
        }

        @Test
        @DisplayName("Should not be equal when criteria differs")
        void shouldNotBeEqualWhenCriteriaDiffers() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            ClaimSearchCriteria criteria1 = createSampleCriteria();
            ClaimSearchCriteria criteria2 = new ClaimSearchCriteria();

            ClaimSearchResultDto result1 = new ClaimSearchResultDto(claims, 10, criteria1);
            ClaimSearchResultDto result2 = new ClaimSearchResultDto(claims, 10, criteria2);

            // Assert
            assertNotEquals(result1, result2);
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            // Arrange
            ClaimSearchResultDto result = new ClaimSearchResultDto(
                createSampleClaimList(), 
                10, 
                createSampleCriteria()
            );

            // Assert
            assertNotEquals(null, result);
        }

        @Test
        @DisplayName("Should not be equal to different type")
        void shouldNotBeEqualToDifferentType() {
            // Arrange
            ClaimSearchResultDto result = new ClaimSearchResultDto(
                createSampleClaimList(), 
                10, 
                createSampleCriteria()
            );

            // Assert
            assertNotEquals(result, "Not a ClaimSearchResultDto");
        }

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            // Arrange
            ClaimSearchResultDto result = new ClaimSearchResultDto(
                createSampleClaimList(), 
                10, 
                createSampleCriteria()
            );

            // Assert
            assertEquals(result, result);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should generate toString with all fields")
        void shouldGenerateToStringWithAllFields() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            int totalCount = 10;
            ClaimSearchCriteria criteria = createSampleCriteria();

            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, totalCount, criteria);

            // Act
            String toString = result.toString();

            // Assert
            assertNotNull(toString);
            assertTrue(toString.contains("ClaimSearchResultDto"));
            assertTrue(toString.contains("claims"));
            assertTrue(toString.contains("totalCount"));
            assertTrue(toString.contains("criteria"));
        }

        @Test
        @DisplayName("Should generate toString with null values")
        void shouldGenerateToStringWithNullValues() {
            // Arrange
            ClaimSearchResultDto result = new ClaimSearchResultDto(null, 0, null);

            // Act
            String toString = result.toString();

            // Assert
            assertNotNull(toString);
            assertTrue(toString.contains("null"));
        }
    }

    @Nested
    @DisplayName("Immutability Tests")
    class ImmutabilityTests {

        @Test
        @DisplayName("Should maintain immutability - modifying original list should not affect record")
        void shouldMaintainImmutabilityWhenOriginalListIsModified() {
            // Arrange
            List<ClaimListItemDto> claims = new ArrayList<>(createSampleClaimList());
            int originalSize = claims.size();
            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, 10, createSampleCriteria());

            // Act
            claims.clear();

            // Assert
            assertEquals(originalSize, result.claims().size());
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle large total count")
        void shouldHandleLargeTotalCount() {
            // Arrange
            int largeTotalCount = Integer.MAX_VALUE;
            ClaimSearchResultDto result = new ClaimSearchResultDto(
                createSampleClaimList(), 
                largeTotalCount, 
                createSampleCriteria()
            );

            // Assert
            assertEquals(largeTotalCount, result.totalCount());
        }

        @Test
        @DisplayName("Should handle large claims list")
        void shouldHandleLargeClaimsList() {
            // Arrange
            List<ClaimListItemDto> largeClaims = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                largeClaims.add(createSampleClaimListItem("CLAIM" + i));
            }

            ClaimSearchResultDto result = new ClaimSearchResultDto(
                largeClaims, 
                10000, 
                createSampleCriteria()
            );

            // Assert
            assertEquals(10000, result.claims().size());
            assertEquals(10000, result.totalCount());
        }

        @Test
        @DisplayName("Should handle mismatch between claims size and total count")
        void shouldHandleMismatchBetweenClaimsSizeAndTotalCount() {
            // Arrange
            List<ClaimListItemDto> claims = createSampleClaimList();
            int totalCount = 100; // Different from claims.size()

            ClaimSearchResultDto result = new ClaimSearchResultDto(claims, totalCount, createSampleCriteria());

            // Assert
            assertNotEquals(result.claims().size(), result.totalCount());
            assertEquals(claims.size(), result.claims().size());
            assertEquals(totalCount, result.totalCount());
        }
    }

    // Helper methods
    private List<ClaimListItemDto> createSampleClaimList() {
        List<ClaimListItemDto> claims = new ArrayList<>();
        claims.add(createSampleClaimListItem("CLAIM001"));
        claims.add(createSampleClaimListItem("CLAIM002"));
        claims.add(createSampleClaimListItem("CLAIM003"));
        return claims;
    }

    private ClaimListItemDto createSampleClaimListItem(String claimNumber) {
        // Mock implementation - adjust based on actual ClaimListItemDto structure
        return new ClaimListItemDto(
            claimNumber,
            "INV001",
            "20240101",
            "ORD001",
            "00",
            "A",
            "1",
            "01"
        );
    }

    private ClaimSearchCriteria createSampleCriteria() {
        // Mock implementation - adjust based on actual ClaimSearchCriteria structure
        ClaimSearchCriteria criteria = new ClaimSearchCriteria();
        // Set sample criteria fields if needed
        return criteria;
    }
}
```

## Additional Test Class for Integration Scenarios

```java
package com.scania.warranty.dto;

import com.scania.warranty.domain.ClaimSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ClaimSearchResultDto Integration Tests")
class ClaimSearchResultDtoIntegrationTest {

    @Test
    @DisplayName("Should correctly represent paginated search results")
    void shouldCorrectlyRepresentPaginatedSearchResults() {
        // Arrange - Simulate page 1 of 3 (10 items per page, 25 total)
        List<ClaimListItemDto> page1Claims = Arrays.asList(
            createClaimListItem("CLAIM001"),
            createClaimListItem("CLAIM002"),
            createClaimListItem("CLAIM003"),
            createClaimListItem("CLAIM004"),
            createClaimListItem("CLAIM005"),
            createClaimListItem("CLAIM006"),
            createClaimListItem("CLAIM007"),
            createClaimListItem("