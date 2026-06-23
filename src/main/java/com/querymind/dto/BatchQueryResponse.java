package com.querymind.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO for batch query response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchQueryResponse {
    private List<QueryResponse> results;
    private int totalProcessed;
    private int successCount;
    private int failureCount;
    private long totalProcessingTimeMs;
}

