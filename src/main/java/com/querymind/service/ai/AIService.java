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
 * AI Service supporting multiple free LLM providers
 * Supports: Groq (FREE!), Ollama (LOCAL), Hugging Face, Together AI, OpenAI
 */
@Service
@Slf4j
public class AIService {
    private final AppConfig appConfig;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final String GROQ_API = "https://api.groq.com/openai/v1/chat/completions";
    private static final String OLLAMA_API = "http://localhost:11434/api/generate";
    private static final String HUGGINGFACE_API = "https://api-inference.huggingface.co/models/";
    private static final String TOGETHER_API = "https://api.together.xyz/v1/chat/completions";
    private static final String OPENAI_API = "https://api.openai.com/v1/chat/completions";

    public AIService(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Detect which LLM provider to use
     */
    private String detectProvider() {
        if (System.getenv("GROQ_API_KEY") != null) {
            return "groq";
        } else if (System.getenv("OLLAMA_URL") != null) {
            return "ollama";
        } else if (System.getenv("HUGGINGFACE_API_KEY") != null) {
            return "huggingface";
        } else if (System.getenv("TOGETHER_API_KEY") != null) {
            return "together";
        } else if (System.getenv("OPENAI_API_KEY") != null) {
            return "openai";
        }

        // Default to Groq if no key set (will fail with helpful message)
        log.warn("No LLM API key detected. Set GROQ_API_KEY, OLLAMA_URL, or OPENAI_API_KEY");
        return "groq";
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
     * Call LLM API (supports multiple providers)
     */
    private String callAI(String prompt, String type) {
        String provider = detectProvider();
        log.info("Using LLM provider: {}", provider);

        try {
            switch (provider) {
                case "groq":
                    return callGroq(prompt);
                case "ollama":
                    return callOllama(prompt);
                case "huggingface":
                    return callHuggingFace(prompt);
                case "together":
                    return callTogether(prompt);
                case "openai":
                default:
                    return callOpenAI(prompt);
            }
        } catch (Exception e) {
            log.error("Error calling AI service with provider: {}", provider, e);
            throw new RuntimeException("Failed to call AI service: " + e.getMessage(), e);
        }
    }

    /**
     * Call Groq API (FREE!)
     */
    private String callGroq(String prompt) throws IOException, InterruptedException {
        String apiKey = System.getenv("GROQ_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("GROQ_API_KEY not set. Get free key at https://groq.com");
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "mixtral-8x7b-32768"); // Free model
        requestBody.put("messages", new Object[]{
            Map.of(
                "role", "user",
                "content", prompt
            )
        });
        requestBody.put("max_tokens", 2000);
        requestBody.put("temperature", 0.7);

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(GROQ_API))
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
            log.info("Groq response received");
            return content;
        } else {
            log.error("Groq API Error: {} - {}", response.statusCode(), response.body());
            throw new RuntimeException("Groq API failed: " + response.body());
        }
    }

    /**
     * Call Ollama API (LOCAL, 100% FREE)
     */
    private String callOllama(String prompt) throws IOException, InterruptedException {
        String ollamaUrl = System.getenv("OLLAMA_URL");
        String model = System.getenv("OLLAMA_MODEL");

        if (ollamaUrl == null) {
            ollamaUrl = "http://localhost:11434";
        }
        if (model == null) {
            model = "mistral";
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("prompt", prompt);
        requestBody.put("stream", false);

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(ollamaUrl + "/api/generate"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String content = jsonResponse.get("response").asText();
            log.info("Ollama response received from model: {}", model);
            return content;
        } else {
            log.error("Ollama API Error: {} - {}", response.statusCode(), response.body());
            throw new RuntimeException("Ollama not running. Install from https://ollama.ai");
        }
    }

    /**
     * Call Hugging Face API
     */
    private String callHuggingFace(String prompt) throws IOException, InterruptedException {
        String apiKey = System.getenv("HUGGINGFACE_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("HUGGINGFACE_API_KEY not set. Get free key at https://huggingface.co");
        }

        String model = "mistralai/Mistral-7B-Instruct-v0.1";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("inputs", prompt);

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(HUGGINGFACE_API + model))
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            String content = jsonResponse.get(0).get("generated_text").asText();
            log.info("Hugging Face response received");
            return content;
        } else {
            log.error("Hugging Face API Error: {} - {}", response.statusCode(), response.body());
            throw new RuntimeException("Hugging Face API failed: " + response.body());
        }
    }

    /**
     * Call Together AI API
     */
    private String callTogether(String prompt) throws IOException, InterruptedException {
        String apiKey = System.getenv("TOGETHER_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("TOGETHER_API_KEY not set. Get free key at https://www.together.ai");
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "mistralai/Mixtral-8x7B-Instruct-v0.1");
        requestBody.put("messages", new Object[]{
            Map.of(
                "role", "user",
                "content", prompt
            )
        });
        requestBody.put("max_tokens", 2000);

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(TOGETHER_API))
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
            log.info("Together AI response received");
            return content;
        } else {
            log.error("Together AI Error: {} - {}", response.statusCode(), response.body());
            throw new RuntimeException("Together AI API failed: " + response.body());
        }
    }

    /**
     * Call OpenAI API (optional, if you have a key)
     */
    private String callOpenAI(String prompt) throws IOException, InterruptedException {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("No LLM configured. Set one of: GROQ_API_KEY, OLLAMA_URL, HUGGINGFACE_API_KEY, TOGETHER_API_KEY, or OPENAI_API_KEY");
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
            Map.of(
                "role", "user",
                "content", prompt
            )
        });
        requestBody.put("max_tokens", 2000);
        requestBody.put("temperature", 0.7);

        String jsonBody = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(OPENAI_API))
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
            log.info("OpenAI response received");
            return content;
        } else {
            log.error("OpenAI API Error: {} - {}", response.statusCode(), response.body());
            throw new RuntimeException("OpenAI API failed: " + response.body());
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
