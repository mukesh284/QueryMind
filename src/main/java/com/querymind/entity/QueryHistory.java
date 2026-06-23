package com.querymind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entity representing user queries and AI responses
 */
@Entity
@Table(name = "query_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String userQuery;

    @Column(columnDefinition = "LONGTEXT")
    private String generatedCode;

    @Enumerated(EnumType.STRING)
    private QueryType queryType;

    @Enumerated(EnumType.STRING)
    private OutputFormat outputFormat;

    private Long schemaUploadId;

    private double confidence;

    private String executionTime;

    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime executedAt;

    private boolean successful;

    public enum QueryType {
        SQL, EXCEL_FORMULA, XLOOKUP, POWER_BI_DAX, ANALYSIS
    }

    public enum OutputFormat {
        SQL, FORMULA, DAX, EXPLANATION
    }
}

