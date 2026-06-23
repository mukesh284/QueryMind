package com.querymind.service;

import com.querymind.entity.QueryHistory;
import com.querymind.repository.QueryHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service for analytics and metrics
 */
@Service
@Slf4j
public class AnalyticsService {
    private final QueryHistoryRepository queryHistoryRepository;

    public AnalyticsService(QueryHistoryRepository queryHistoryRepository) {
        this.queryHistoryRepository = queryHistoryRepository;
    }

    /**
     * Get analytics dashboard metrics
     */
    public Map<String, Object> getDashboardMetrics() {
        Map<String, Object> metrics = new HashMap<>();

        List<QueryHistory> allQueries = queryHistoryRepository.findAllByOrderByCreatedAtDesc();

        // Total queries
        metrics.put("totalQueries", allQueries.size());

        // Success rate
        long successCount = allQueries.stream().filter(QueryHistory::isSuccessful).count();
        double successRate = allQueries.isEmpty() ? 0 : (double) successCount / allQueries.size() * 100;
        metrics.put("successRate", String.format("%.2f%%", successRate));

        // Average confidence
        double avgConfidence = allQueries.stream()
            .mapToDouble(QueryHistory::getConfidence)
            .average()
            .orElse(0.0);
        metrics.put("averageConfidence", String.format("%.2f", avgConfidence));

        // Query type breakdown
        Map<String, Long> queryTypeBreakdown = new HashMap<>();
        allQueries.forEach(q -> {
            String type = q.getQueryType().toString();
            queryTypeBreakdown.put(type, queryTypeBreakdown.getOrDefault(type, 0L) + 1);
        });
        metrics.put("queryTypeBreakdown", queryTypeBreakdown);

        // Average response time
        double avgResponseTime = allQueries.stream()
            .filter(q -> q.getExecutionTime() != null)
            .mapToLong(q -> Long.parseLong(q.getExecutionTime().replaceAll("[^0-9]", "")))
            .average()
            .orElse(0.0);
        metrics.put("averageResponseTimeMs", String.format("%.0f", avgResponseTime));

        return metrics;
    }

    /**
     * Get top performing queries
     */
    public List<QueryHistory> getTopPerformingQueries(int limit) {
        List<QueryHistory> allQueries = queryHistoryRepository.findAllByOrderByCreatedAtDesc();
        return allQueries.stream()
            .filter(QueryHistory::isSuccessful)
            .limit(limit)
            .toList();
    }

    /**
     * Get error statistics
     */
    public Map<String, Object> getErrorStatistics() {
        Map<String, Object> stats = new HashMap<>();

        List<QueryHistory> allQueries = queryHistoryRepository.findAllByOrderByCreatedAtDesc();
        List<QueryHistory> failedQueries = allQueries.stream()
            .filter(q -> !q.isSuccessful())
            .toList();

        stats.put("totalErrors", failedQueries.size());

        Map<String, Long> errorsByType = new HashMap<>();
        failedQueries.forEach(q -> {
            String type = q.getQueryType().toString();
            errorsByType.put(type, errorsByType.getOrDefault(type, 0L) + 1);
        });
        stats.put("errorsByType", errorsByType);

        return stats;
    }
}

