package com.querymind.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for user queries
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRequest {
    private String query;
    private Long schemaUploadId;
    private String outputFormat; // SQL, FORMULA, DAX, EXPLANATION
    private String context; // Optional context about the data
    private boolean explainSolution;
}

