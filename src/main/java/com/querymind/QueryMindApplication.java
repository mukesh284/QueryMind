package com.querymind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * QueryMind AI-Powered SQL & Excel Assistant Application
 * Main entry point for the application
 */
@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"com.querymind"})
public class QueryMindApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryMindApplication.class, args);
    }
}

