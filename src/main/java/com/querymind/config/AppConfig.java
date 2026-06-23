package com.querymind.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * Application configuration properties
 */
@Configuration
@ConfigurationProperties(prefix = "querymind")
@Data
public class AppConfig {
    private String aiApiKey;
    private String aiProvider; // openai, claude, gemini
    private String aiModel;
    private int maxTokens = 2000;
    private double temperature = 0.7;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;
    private String databaseDriver;
}

