package com.querymind.service;

import com.querymind.dto.QueryRequest;
import com.querymind.dto.QueryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for QueryService
 */
@SpringBootTest
@TestPropertySource(properties = {
    "querymind.ai-api-key=test-key",
    "querymind.ai-model=gpt-4"
})
public class QueryServiceTest {

    private QueryService queryService;

    @BeforeEach
    public void setUp() {
        // Initialize test fixtures
    }

    @Test
    public void testProcessQuery_WithValidInput_ReturnsSuccess() {
        // Arrange
        QueryRequest request = new QueryRequest();
        request.setQuery("Show all customers");
        request.setOutputFormat("SQL");

        // Act
        QueryResponse response = queryService.processQuery(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getGeneratedCode());
    }

    @Test
    public void testProcessQuery_WithEmptyQuery_ReturnsFailed() {
        // Arrange
        QueryRequest request = new QueryRequest();
        request.setQuery("");
        request.setOutputFormat("SQL");

        // Act
        QueryResponse response = queryService.processQuery(request);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
    }
}

