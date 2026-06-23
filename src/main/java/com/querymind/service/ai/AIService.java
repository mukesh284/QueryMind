package com.querymind.service.ai;

import com.querymind.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;

/**
 * AI Service for interacting with OpenAI API
 */
@Service
@Slf4j
public class AIService {
    private final AppConfig appConfig;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AIService(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Generate SQL query from natural language
     */
    public String generateSQL(String userQuery, String schema) {
        String prompt = buildSQLPrompt(userQuery, schema);
        return callAI(prompt, "sql");
    }

    /**
     * Generate Excel formula from natural language
     */
    public String generateExcelFormula(String userQuery, String dataContext) {
        String prompt = buildFormulaPrompt(userQuery, dataContext);
        return callAI(prompt, "formula");
    }

    /**
     * Generate XLOOKUP formula
     */
    public String generateXLOOKUP(String userQuery, String dataContext) {
        String prompt = buildXLOOKUPPrompt(userQuery, dataContext);
        return callAI(prompt, "xlookup");
    }

    /**
     * Generate Power BI DAX query
     */
    public String generateDAX(String userQuery, String schema) {
        String prompt = buildDAXPrompt(userQuery, schema);
        return callAI(prompt, "dax");
    }

    /**
     * Analyze data and provide insights
     */
    public String analyzeData(String userQuery, String dataContext) {
        String prompt = buildAnalysisPrompt(userQuery, dataContext);
        return callAI(prompt, "analysis");
    }

    /**
     * Build SQL generation prompt
     */
    private String buildSQLPrompt(String userQuery, String schema) {
        return String.format(
            "You are an expert SQL developer. Generate a SQL query based on the user request.\n\n" +
            "Database Schema:\n%s\n\n" +
            "User Request: %s\n\n" +
            "Requirements:\n" +
            "1. Generate valid SQL that works with the provided schema\n" +
            "2. Include proper formatting and comments\n" +
            "3. Optimize for performance\n" +
            "4. Only return the SQL query, no explanation\n" +
            "5. Ensure the query is safe and doesn't include DROP/DELETE operations\n\n" +
            "SQL Query:",
            schema, userQuery
        );
    }

    /**
     * Build Excel formula generation prompt
     */
    private String buildFormulaPrompt(String userQuery, String dataContext) {
        return String.format(
            "You are an expert Excel formula developer. Generate an Excel formula based on the user request.\n\n" +
            "Data Context:\n%s\n\n" +
            "User Request: %s\n\n" +
            "Requirements:\n" +
            "1. Generate valid Excel formula\n" +
            "2. Support both modern (Excel 365) and older versions\n" +
            "3. Include cell references and absolute/relative reference notation\n" +
            "4. Only return the formula, no explanation\n\n" +
            "Excel Formula:",
            dataContext, userQuery
        );
    }

    /**
     * Build XLOOKUP prompt
     */
    private String buildXLOOKUPPrompt(String userQuery, String dataContext) {
        return String.format(
            "You are an expert Excel XLOOKUP specialist. Generate an XLOOKUP formula.\n\n" +
            "Data Context:\n%s\n\n" +
            "User Request: %s\n\n" +
            "Requirements:\n" +
            "1. Use XLOOKUP function (modern Excel)\n" +
            "2. Include proper parameters: lookup_value, lookup_array, return_array, etc.\n" +
            "3. Handle errors with appropriate error handling\n" +
            "4. Only return the XLOOKUP formula\n\n" +
            "XLOOKUP Formula:",
            dataContext, userQuery
        );
    }

    /**
     * Build DAX prompt
     */
    private String buildDAXPrompt(String userQuery, String schema) {
        return String.format(
            "You are an expert Power BI DAX developer. Generate a DAX measure or query.\n\n" +
            "Data Model:\n%s\n\n" +
            "User Request: %s\n\n" +
            "Requirements:\n" +
            "1. Generate valid DAX code\n" +
            "2. Follow Power BI best practices\n" +
            "3. Include proper table and column references\n" +
            "4. Only return the DAX code, no explanation\n\n" +
            "DAX Code:",
            schema, userQuery
        );
    }

    /**
     * Build analysis prompt
     */
    private String buildAnalysisPrompt(String userQuery, String dataContext) {
        return String.format(
            "You are a data analyst. Provide insights and recommendations.\n\n" +
            "Data Context:\n%s\n\n" +
            "Analysis Request: %s\n\n" +
            "Requirements:\n" +
            "1. Provide clear, actionable insights\n" +
            "2. Suggest relevant metrics and KPIs\n" +
            "3. Recommend data visualization approaches\n" +
            "4. Be concise and professional\n\n" +
            "Analysis:",
            dataContext, userQuery
        );
    }

    /**
     * Call OpenAI API
     */
    private String callAI(String prompt, String type) {
        try {
            String apiKey = appConfig.getAiApiKey();
            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalStateException("AI API key not configured");
            }

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", appConfig.getAiModel());
            requestBody.put("messages", new Object[]{
                Map.of(
                    "role", "user",
                    "content", prompt
                )
            });
            requestBody.put("max_tokens", appConfig.getMaxTokens());
            requestBody.put("temperature", appConfig.getTemperature());

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode jsonResponse = objectMapper.readTree(response.body());
                String content = jsonResponse.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();
                log.info("AI Response for {}: {}", type, content.substring(0, Math.min(100, content.length())));
                return content;
            } else {
                log.error("AI API Error: {} - {}", response.statusCode(), response.body());
                throw new RuntimeException("AI API call failed: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Error calling AI service", e);
            throw new RuntimeException("Failed to call AI service: " + e.getMessage(), e);
        }
    }

    /**
     * Validate and explain generated code
     */
    public String explainCode(String code, String codeType) {
        String prompt = String.format(
            "Explain this %s code in simple terms for a data analyst:\n\n%s\n\n" +
            "Provide:\n1. What it does\n2. Key components\n3. When to use it",
            codeType, code
        );
        return callAI(prompt, "explanation");
    }
}

