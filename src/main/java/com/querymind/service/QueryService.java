package com.querymind.service;

import com.querymind.dto.QueryRequest;
import com.querymind.dto.QueryResponse;
import com.querymind.entity.QueryHistory;
import com.querymind.entity.SchemaUpload;
import com.querymind.repository.QueryHistoryRepository;
import com.querymind.repository.SchemaUploadRepository;
import com.querymind.service.ai.AIService;
import com.querymind.service.validation.CodeValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Main service for handling user queries and generating AI responses
 */
@Service
@Slf4j
public class QueryService {
    private final AIService aiService;
    private final SchemaUploadRepository schemaUploadRepository;
    private final QueryHistoryRepository queryHistoryRepository;
    private final CodeValidationService codeValidationService;

    public QueryService(AIService aiService,
                       SchemaUploadRepository schemaUploadRepository,
                       QueryHistoryRepository queryHistoryRepository,
                       CodeValidationService codeValidationService) {
        this.aiService = aiService;
        this.schemaUploadRepository = schemaUploadRepository;
        this.queryHistoryRepository = queryHistoryRepository;
        this.codeValidationService = codeValidationService;
    }

    /**
     * Process user query and generate appropriate response
     */
    public QueryResponse processQuery(QueryRequest request) {
        long startTime = System.currentTimeMillis();
        QueryResponse response = new QueryResponse();

        try {
            // Get schema if provided
            SchemaUpload schema = null;
            if (request.getSchemaUploadId() != null) {
                schema = schemaUploadRepository.findById(request.getSchemaUploadId())
                    .orElseThrow(() -> new RuntimeException("Schema not found"));
                schema.setLastUsedAt(LocalDateTime.now());
                schema.setQueryCount(schema.getQueryCount() + 1);
                schemaUploadRepository.save(schema);
            }

            String generatedCode = "";
            String codeType = "UNKNOWN";

            // Generate code based on output format
            String outputFormat = request.getOutputFormat().toUpperCase();
            switch (outputFormat) {
                case "SQL":
                    generatedCode = aiService.generateSQL(request.getQuery(),
                        schema != null ? schema.getParsedSchema() : "");
                    codeType = "SQL";
                    break;
                case "FORMULA":
                    generatedCode = aiService.generateExcelFormula(request.getQuery(),
                        schema != null ? schema.getParsedSchema() : request.getContext());
                    codeType = "FORMULA";
                    break;
                case "XLOOKUP":
                    generatedCode = aiService.generateXLOOKUP(request.getQuery(),
                        schema != null ? schema.getParsedSchema() : request.getContext());
                    codeType = "XLOOKUP";
                    break;
                case "DAX":
                    generatedCode = aiService.generateDAX(request.getQuery(),
                        schema != null ? schema.getParsedSchema() : "");
                    codeType = "DAX";
                    break;
                default:
                    generatedCode = aiService.generateSQL(request.getQuery(),
                        schema != null ? schema.getParsedSchema() : "");
                    codeType = "SQL";
            }

            // Validate generated code
            boolean isValid = codeValidationService.validateCode(generatedCode, codeType);
            double confidence = isValid ? 0.95 : 0.70;

            // Generate explanation if requested
            String explanation = "";
            if (request.isExplainSolution()) {
                explanation = aiService.explainCode(generatedCode, codeType);
            }

            // Get suggestions for improvement
            List<String> suggestions = codeValidationService.getSuggestions(generatedCode, codeType);

            // Save query history
            QueryHistory history = new QueryHistory();
            history.setUserQuery(request.getQuery());
            history.setGeneratedCode(generatedCode);
            history.setQueryType(QueryHistory.QueryType.valueOf(codeType));
            history.setOutputFormat(QueryHistory.OutputFormat.valueOf(codeType));
            history.setSchemaUploadId(request.getSchemaUploadId());
            history.setConfidence(confidence);
            history.setCreatedAt(LocalDateTime.now());
            history.setSuccessful(isValid);

            QueryHistory savedHistory = queryHistoryRepository.save(history);

            // Build response
            response.setQueryId(savedHistory.getId());
            response.setGeneratedCode(generatedCode);
            response.setCodeType(codeType);
            response.setExplanation(explanation);
            response.setConfidence(confidence);
            response.setSuggestedImprovements(suggestions);
            response.setSuccess(isValid);

            long processingTime = System.currentTimeMillis() - startTime;
            response.setProcessingTimeMs(processingTime);

            log.info("Query processed successfully in {}ms", processingTime);

        } catch (Exception e) {
            log.error("Error processing query", e);
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setProcessingTimeMs(System.currentTimeMillis() - startTime);
        }

        return response;
    }

    /**
     * Get query history
     */
    public List<QueryHistory> getQueryHistory(Long schemaUploadId) {
        if (schemaUploadId != null) {
            return queryHistoryRepository.findBySchemaUploadIdOrderByCreatedAtDesc(schemaUploadId);
        }
        return queryHistoryRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Get specific query by ID
     */
    public QueryHistory getQuery(Long queryId) {
        return queryHistoryRepository.findById(queryId)
            .orElseThrow(() -> new RuntimeException("Query not found"));
    }
}

