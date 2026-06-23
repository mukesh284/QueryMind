package com.querymind.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Response DTO for query results
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResponse {
    private Long queryId;
    private String generatedCode;
    private String codeType; // SQL, FORMULA, DAX
    private String explanation;
    private double confidence;
    private List<String> suggestedImprovements;
    private String executionExample;
    private long processingTimeMs;
    private boolean success;
    private String errorMessage;
}

