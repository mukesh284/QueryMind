package com.querymind.service;

import com.querymind.dto.BatchQueryRequest;
import com.querymind.dto.BatchQueryResponse;
import com.querymind.dto.QueryRequest;
import com.querymind.dto.QueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;

/**
 * Service for batch query processing
 */
@Service
@Slf4j
public class BatchQueryService {
    private final QueryService queryService;
    private final ExecutorService executorService;

    public BatchQueryService(QueryService queryService) {
        this.queryService = queryService;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    /**
     * Process batch queries
     */
    public BatchQueryResponse processBatchQueries(BatchQueryRequest request) {
        long startTime = System.currentTimeMillis();
        List<QueryResponse> results = Collections.synchronizedList(new ArrayList<>());
        List<Future<?>> futures = new ArrayList<>();

        for (String query : request.getQueries()) {
            QueryRequest singleRequest = new QueryRequest();
            singleRequest.setQuery(query);
            singleRequest.setSchemaUploadId(request.getSchemaUploadId());
            singleRequest.setOutputFormat(request.getOutputFormat());
            singleRequest.setExplainSolution(request.isExplainSolutions());

            Future<?> future = executorService.submit(() -> {
                try {
                    QueryResponse response = queryService.processQuery(singleRequest);
                    results.add(response);
                    log.info("Processed query: {}", query.substring(0, Math.min(50, query.length())));
                } catch (Exception e) {
                    log.error("Error processing query: {}", query, e);
                    QueryResponse errorResponse = new QueryResponse();
                    errorResponse.setSuccess(false);
                    errorResponse.setErrorMessage(e.getMessage());
                    results.add(errorResponse);
                }
            });
            futures.add(future);
        }

        // Wait for all tasks to complete
        for (Future<?> future : futures) {
            try {
                future.get(5, TimeUnit.MINUTES);
            } catch (TimeoutException | InterruptedException | ExecutionException e) {
                log.error("Error waiting for query completion", e);
            }
        }

        int successCount = (int) results.stream().filter(QueryResponse::isSuccess).count();
        int failureCount = results.size() - successCount;
        long processingTime = System.currentTimeMillis() - startTime;

        BatchQueryResponse response = new BatchQueryResponse();
        response.setResults(results);
        response.setTotalProcessed(results.size());
        response.setSuccessCount(successCount);
        response.setFailureCount(failureCount);
        response.setTotalProcessingTimeMs(processingTime);

        log.info("Batch processing completed: {} total, {} success, {} failed in {}ms",
            results.size(), successCount, failureCount, processingTime);

        return response;
    }

    /**
     * Shutdown executor service
     */
    public void shutdown() {
        executorService.shutdown();
    }
}

