# Complete JUnit Test Cases for MigrationDemoController

```java
package com.scania.warranty.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Comprehensive JUnit test suite for MigrationDemoController.
 * Tests both direct method invocation and HTTP endpoint behavior.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MigrationDemoController Tests")
class MigrationDemoControllerTest {

    @InjectMocks
    private MigrationDemoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    // ========== Direct Method Invocation Tests ==========

    @Test
    @DisplayName("Should return ResponseEntity with OK status")
    void testRunMigratedQueries_ReturnsOkStatus() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return non-null response body")
    void testRunMigratedQueries_ReturnsNonNullBody() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();

        // Then
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should return map with correct title")
    void testRunMigratedQueries_ContainsCorrectTitle() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertTrue(body.containsKey("title"));
        assertEquals("Migration demo – migrated read SQLs", body.get("title"));
    }

    @Test
    @DisplayName("Should return map with correct summary")
    void testRunMigratedQueries_ContainsCorrectSummary() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertTrue(body.containsKey("summary"));
        String expectedSummary = "This app (warranty_demo) does not include HS1210_n1 migrated read SQLs. "
                + "For the full demo with DealerConfigurationService and SystemConfiguration queries, "
                + "run the HS1210_n1 Pure Java app that includes the full MigrationDemoController.";
        assertEquals(expectedSummary, body.get("summary"));
    }

    @Test
    @DisplayName("Should return empty queries list")
    void testRunMigratedQueries_ContainsEmptyQueriesList() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertTrue(body.containsKey("queries"));
        Object queries = body.get("queries");
        assertInstanceOf(List.class, queries);
        assertTrue(((List<?>) queries).isEmpty());
    }

    @Test
    @DisplayName("Should return successCount as 0")
    void testRunMigratedQueries_ContainsZeroSuccessCount() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertTrue(body.containsKey("successCount"));
        assertEquals(0L, body.get("successCount"));
    }

    @Test
    @DisplayName("Should return totalCount as 0")
    void testRunMigratedQueries_ContainsZeroTotalCount() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertTrue(body.containsKey("totalCount"));
        assertEquals(0, body.get("totalCount"));
    }

    @Test
    @DisplayName("Should return migrationSuccessful as true")
    void testRunMigratedQueries_ContainsMigrationSuccessfulTrue() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertTrue(body.containsKey("migrationSuccessful"));
        assertEquals(true, body.get("migrationSuccessful"));
    }

    @Test
    @DisplayName("Should return map with all required keys")
    void testRunMigratedQueries_ContainsAllRequiredKeys() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertThat(body).containsKeys(
                "title",
                "summary",
                "queries",
                "successCount",
                "totalCount",
                "migrationSuccessful"
        );
    }

    @Test
    @DisplayName("Should return map with exactly 6 entries")
    void testRunMigratedQueries_ContainsExactlySixEntries() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertEquals(6, body.size());
    }

    // ========== MockMvc HTTP Endpoint Tests ==========

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return 200 OK")
    void testGetMigratedQueries_Returns200() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return JSON content type")
    void testGetMigratedQueries_ReturnsJsonContentType() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return correct title in JSON")
    void testGetMigratedQueries_ReturnsCorrectTitleInJson() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Migration demo – migrated read SQLs"));
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return correct summary in JSON")
    void testGetMigratedQueries_ReturnsCorrectSummaryInJson() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.summary").value(
                        "This app (warranty_demo) does not include HS1210_n1 migrated read SQLs. " +
                        "For the full demo with DealerConfigurationService and SystemConfiguration queries, " +
                        "run the HS1210_n1 Pure Java app that includes the full MigrationDemoController."
                ));
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return empty queries array in JSON")
    void testGetMigratedQueries_ReturnsEmptyQueriesArrayInJson() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.queries").isArray())
                .andExpect(jsonPath("$.queries").isEmpty());
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return successCount 0 in JSON")
    void testGetMigratedQueries_ReturnsSuccessCountZeroInJson() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.successCount").value(0));
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return totalCount 0 in JSON")
    void testGetMigratedQueries_ReturnsTotalCountZeroInJson() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(0));
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return migrationSuccessful true in JSON")
    void testGetMigratedQueries_ReturnsMigrationSuccessfulTrueInJson() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.migrationSuccessful").value(true));
    }

    @Test
    @DisplayName("GET /api/demo/migrated-queries should return all expected JSON fields")
    void testGetMigratedQueries_ReturnsAllExpectedJsonFields() throws Exception {
        mockMvc.perform(get("/api/demo/migrated-queries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.summary").exists())
                .andExpect(jsonPath("$.queries").exists())
                .andExpect(jsonPath("$.successCount").exists())
                .andExpect(jsonPath("$.totalCount").exists())
                .andExpect(jsonPath("$.migrationSuccessful").exists());
    }

    // ========== Edge Case and Consistency Tests ==========

    @Test
    @DisplayName("Multiple invocations should return consistent results")
    void testRunMigratedQueries_ConsistentResults() {
        // When
        ResponseEntity<Map<String, Object>> response1 = controller.runMigratedQueries();
        ResponseEntity<Map<String, Object>> response2 = controller.runMigratedQueries();

        // Then
        assertNotNull(response1.getBody());
        assertNotNull(response2.getBody());
        assertEquals(response1.getBody().get("title"), response2.getBody().get("title"));
        assertEquals(response1.getBody().get("summary"), response2.getBody().get("summary"));
        assertEquals(response1.getBody().get("successCount"), response2.getBody().get("successCount"));
        assertEquals(response1.getBody().get("totalCount"), response2.getBody().get("totalCount"));
        assertEquals(response1.getBody().get("migrationSuccessful"), response2.getBody().get("migrationSuccessful"));
    }

    @Test
    @DisplayName("Response body should be immutable-safe (no side effects)")
    void testRunMigratedQueries_NoSideEffects() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        int originalSize = body.size();
        
        // Verify the map structure is as expected
        assertEquals(6, originalSize);
        
        // Subsequent calls should return fresh instances
        ResponseEntity<Map<String, Object>> response2 = controller.runMigratedQueries();
        assertNotNull(response2.getBody());
        assertEquals(originalSize, response2.getBody().size());
    }

    @Test
    @DisplayName("Should handle concurrent requests safely")
    void testRunMigratedQueries_ThreadSafe() throws InterruptedException {
        // Given
        final int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        final boolean[] results = new boolean[threadCount];

        // When
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
                results[index] = response.getStatusCode() == HttpStatus.OK 
                        && response.getBody() != null
                        && response.getBody().size() == 6;
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        // Then
        for (boolean result : results) {
            assertTrue(result, "All concurrent requests should succeed");
        }
    }

    @Test
    @DisplayName("Queries list should be type-safe")
    void testRunMigratedQueries_QueriesListTypeSafety() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        Object queries = body.get("queries");
        assertInstanceOf(List.class, queries);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> queriesList = (List<Map<String, Object>>) queries;
        assertEquals(0, queriesList.size());
    }

    @Test
    @DisplayName("Numeric fields should have correct types")
    void testRunMigratedQueries_NumericFieldTypes() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertInstanceOf(Long.class, body.get("successCount"));
        assertInstanceOf(Integer.class, body.get("totalCount"));
    }

    @Test
    @DisplayName("Boolean field should have correct type")
    void testRunMigratedQueries_BooleanFieldType() {
        // When
        ResponseEntity<Map<String, Object>> response = controller.runMigratedQueries();
        Map<String, Object> body = response.getBody();

        // Then
        assertNotNull(body);
        assertInstanceOf(Boolean.class, body.get("migrationSuccessful"));
    