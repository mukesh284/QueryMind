package com.querymind.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for batch query processing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchQueryRequest {
    private String[] queries;
    private Long schemaUploadId;
    private String outputFormat;
    private boolean explainSolutions;
}

