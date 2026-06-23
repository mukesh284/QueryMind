package com.querymind.controller;

import com.querymind.dto.QueryRequest;
import com.querymind.dto.QueryResponse;
import com.querymind.entity.QueryHistory;
import com.querymind.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST API Controller for query operations
 */
@RestController
@RequestMapping("/api/v1/queries")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class QueryController {
    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Process a user query and generate code
     */
    @PostMapping("/generate")
    public ResponseEntity<QueryResponse> generateCode(@RequestBody QueryRequest request) {
        log.info("Received query: {}", request.getQuery());
        QueryResponse response = queryService.processQuery(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get query history
     */
    @GetMapping("/history")
    public ResponseEntity<List<QueryHistory>> getHistory(@RequestParam(required = false) Long schemaId) {
        List<QueryHistory> history = queryService.getQueryHistory(schemaId);
        return ResponseEntity.ok(history);
    }

    /**
     * Get specific query by ID
     */
    @GetMapping("/{queryId}")
    public ResponseEntity<QueryHistory> getQuery(@PathVariable Long queryId) {
        QueryHistory query = queryService.getQuery(queryId);
        return ResponseEntity.ok(query);
    }

    /**
     * Generate SQL from natural language
     */
    @PostMapping("/sql")
    public ResponseEntity<QueryResponse> generateSQL(@RequestBody QueryRequest request) {
        request.setOutputFormat("SQL");
        QueryResponse response = queryService.processQuery(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Generate Excel formula from natural language
     */
    @PostMapping("/excel/formula")
    public ResponseEntity<QueryResponse> generateExcelFormula(@RequestBody QueryRequest request) {
        request.setOutputFormat("FORMULA");
        QueryResponse response = queryService.processQuery(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Generate XLOOKUP formula
     */
    @PostMapping("/excel/xlookup")
    public ResponseEntity<QueryResponse> generateXLOOKUP(@RequestBody QueryRequest request) {
        request.setOutputFormat("XLOOKUP");
        QueryResponse response = queryService.processQuery(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Generate Power BI DAX
     */
    @PostMapping("/powerbi/dax")
    public ResponseEntity<QueryResponse> generateDAX(@RequestBody QueryRequest request) {
        request.setOutputFormat("DAX");
        QueryResponse response = queryService.processQuery(request);
        return ResponseEntity.ok(response);
    }
}

