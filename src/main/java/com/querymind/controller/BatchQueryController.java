package com.querymind.controller;

import com.querymind.dto.BatchQueryRequest;
import com.querymind.dto.BatchQueryResponse;
import com.querymind.service.BatchQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for batch query operations
 */
@RestController
@RequestMapping("/api/v1/queries/batch")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class BatchQueryController {
    private final BatchQueryService batchQueryService;

    public BatchQueryController(BatchQueryService batchQueryService) {
        this.batchQueryService = batchQueryService;
    }

    /**
     * Process batch queries
     */
    @PostMapping("/process")
    public ResponseEntity<BatchQueryResponse> processBatch(@RequestBody BatchQueryRequest request) {
        log.info("Received batch query with {} queries", request.getQueries().length);
        BatchQueryResponse response = batchQueryService.processBatchQueries(request);
        return ResponseEntity.ok(response);
    }
}

