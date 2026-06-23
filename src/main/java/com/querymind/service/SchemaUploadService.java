package com.querymind.service;

import com.querymind.dto.SchemaUploadResponse;
import com.querymind.entity.SchemaUpload;
import com.querymind.repository.SchemaUploadRepository;
import com.querymind.service.schema.SchemaParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service for handling schema uploads
 */
@Service
@Slf4j
public class SchemaUploadService {
    private final SchemaUploadRepository schemaUploadRepository;
    private final SchemaParserService schemaParserService;

    public SchemaUploadService(SchemaUploadRepository schemaUploadRepository,
                              SchemaParserService schemaParserService) {
        this.schemaUploadRepository = schemaUploadRepository;
        this.schemaParserService = schemaParserService;
    }

    /**
     * Upload and parse schema file
     */
    public SchemaUploadResponse uploadSchema(MultipartFile file) {
        SchemaUploadResponse response = new SchemaUploadResponse();

        try {
            String fileName = file.getOriginalFilename();
            String contentType = file.getContentType();

            // Validate file
            if (!isValidFile(fileName)) {
                response.setSuccess(false);
                response.setMessage("Invalid file format. Supported: .sql, .xlsx, .xls, .csv");
                return response;
            }

            SchemaUpload schemaUpload = new SchemaUpload();
            schemaUpload.setFileName(fileName);
            schemaUpload.setUploadedAt(LocalDateTime.now());

            String parsedSchema = "";
            String fileType = "";

            // Parse based on file type
            try (InputStream inputStream = file.getInputStream()) {
                if (fileName.endsWith(".sql")) {
                    parsedSchema = schemaParserService.parseSQLSchema(inputStream);
                    fileType = "SQL_SCHEMA";
                    schemaUpload.setDatabaseType(schemaParserService.inferDatabaseType(parsedSchema));
                } else if (fileName.endsWith(".xlsx") || fileName.endsWith(".xls")) {
                    parsedSchema = schemaParserService.parseExcelSchema(inputStream, fileName);
                    fileType = "EXCEL";
                } else if (fileName.endsWith(".csv")) {
                    parsedSchema = schemaParserService.parseCSVSchema(inputStream);
                    fileType = "CSV";
                }
            }

            schemaUpload.setFileType(SchemaUpload.FileType.valueOf(fileType));
            schemaUpload.setSchemaContent(parsedSchema);
            schemaUpload.setParsedSchema(parsedSchema);

            // Extract metadata
            Map<String, Object> metadata = schemaParserService.extractSchemaMetadata(parsedSchema);

            // Save schema
            SchemaUpload savedSchema = schemaUploadRepository.save(schemaUpload);

            // Build response
            response.setSchemaId(savedSchema.getId());
            response.setFileName(fileName);
            response.setSchemaType(fileType);
            response.setTableCount((int) metadata.getOrDefault("tableCount", 0));
            response.setColumnCount((int) metadata.getOrDefault("columnCount", 0));
            response.setDatabaseType(savedSchema.getDatabaseType());
            response.setUploadedAt(savedSchema.getUploadedAt().toString());
            response.setSuccess(true);
            response.setMessage("Schema uploaded successfully");

            log.info("Schema uploaded: {} with {} tables", fileName, response.getTableCount());

        } catch (Exception e) {
            log.error("Error uploading schema", e);
            response.setSuccess(false);
            response.setMessage("Error uploading schema: " + e.getMessage());
        }

        return response;
    }

    /**
     * Get all uploaded schemas
     */
    public List<SchemaUpload> getAllSchemas() {
        return schemaUploadRepository.findAllByOrderByUploadedAtDesc();
    }

    /**
     * Get schema by ID
     */
    public SchemaUpload getSchema(Long id) {
        return schemaUploadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Schema not found"));
    }

    /**
     * Delete schema
     */
    public void deleteSchema(Long id) {
        schemaUploadRepository.deleteById(id);
        log.info("Schema deleted: {}", id);
    }

    /**
     * Validate file format
     */
    private boolean isValidFile(String fileName) {
        if (fileName == null) return false;
        return fileName.endsWith(".sql") ||
               fileName.endsWith(".xlsx") ||
               fileName.endsWith(".xls") ||
               fileName.endsWith(".csv");
    }
}

