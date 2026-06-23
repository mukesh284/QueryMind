package com.querymind.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO for schema upload responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchemaUploadResponse {
    private Long schemaId;
    private String fileName;
    private String schemaType;
    private int tableCount;
    private int columnCount;
    private String databaseType;
    private String uploadedAt;
    private String message;
    private boolean success;
}

