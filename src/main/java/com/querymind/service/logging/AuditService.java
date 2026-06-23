package com.querymind.service.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service for audit logging and tracking
 */
@Service
@Slf4j
public class AuditService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Log query execution
     */
    public void logQueryExecution(Long queryId, String userQuery, String generatedCode,
                                   boolean success, long executionTimeMs) {
        String timestamp = LocalDateTime.now().format(formatter);
        String queryPreview = userQuery.length() > 100 ? userQuery.substring(0, 100) + "..." : userQuery;

        log.info("[QUERY_EXECUTION] ID: {} | Time: {} | Duration: {}ms | Success: {} | Query: {}",
            queryId, timestamp, executionTimeMs, success, queryPreview);
    }

    /**
     * Log schema upload
     */
    public void logSchemaUpload(Long schemaId, String fileName, String fileType, int tableCount) {
        String timestamp = LocalDateTime.now().format(formatter);
        log.info("[SCHEMA_UPLOAD] ID: {} | Time: {} | File: {} | Type: {} | Tables: {}",
            schemaId, timestamp, fileName, fileType, tableCount);
    }

    /**
     * Log API request
     */
    public void logAPIRequest(String endpoint, String method, String ipAddress) {
        String timestamp = LocalDateTime.now().format(formatter);
        log.info("[API_REQUEST] Time: {} | Method: {} | Endpoint: {} | IP: {}",
            timestamp, method, endpoint, ipAddress);
    }

    /**
     * Log error
     */
    public void logError(String component, String errorMessage, Exception exception) {
        log.error("[ERROR] Component: {} | Message: {} | Exception: {}",
            component, errorMessage, exception != null ? exception.getMessage() : "N/A", exception);
    }

    /**
     * Log performance metric
     */
    public void logPerformanceMetric(String metricName, long value, String unit) {
        log.info("[PERFORMANCE] Metric: {} | Value: {} {} | Time: {}",
            metricName, value, unit, LocalDateTime.now().format(formatter));
    }
}

