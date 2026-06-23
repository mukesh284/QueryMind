package com.querymind.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Health check and info endpoints
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("application", "QueryMind AI Assistant");
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "QueryMind - AI-Powered SQL & Excel Assistant");
        info.put("description", "AI Assistant for Analysts");
        info.put("features", new String[]{
            "SQL Query Generation",
            "Excel Formula Generation",
            "XLOOKUP Formula Generation",
            "Power BI DAX Generation",
            "Data Analysis & Insights"
        });
        info.put("targetUsers", new String[]{
            "Data Analysts",
            "Business Analysts",
            "Excel Users",
            "Power BI Developers"
        });
        return ResponseEntity.ok(info);
    }
}

