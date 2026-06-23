package com.querymind.controller;

import com.querymind.dto.SchemaUploadResponse;
import com.querymind.entity.SchemaUpload;
import com.querymind.service.SchemaUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * REST API Controller for schema upload operations
 */
@RestController
@RequestMapping("/api/v1/schemas")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class SchemaController {
    private final SchemaUploadService schemaUploadService;

    public SchemaController(SchemaUploadService schemaUploadService) {
        this.schemaUploadService = schemaUploadService;
    }

    /**
     * Upload a schema file
     */
    @PostMapping("/upload")
    public ResponseEntity<SchemaUploadResponse> uploadSchema(@RequestParam("file") MultipartFile file) {
        log.info("Uploading schema file: {}", file.getOriginalFilename());
        SchemaUploadResponse response = schemaUploadService.uploadSchema(file);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Get all uploaded schemas
     */
    @GetMapping
    public ResponseEntity<List<SchemaUpload>> getAllSchemas() {
        List<SchemaUpload> schemas = schemaUploadService.getAllSchemas();
        return ResponseEntity.ok(schemas);
    }

    /**
     * Get schema by ID
     */
    @GetMapping("/{schemaId}")
    public ResponseEntity<SchemaUpload> getSchema(@PathVariable Long schemaId) {
        SchemaUpload schema = schemaUploadService.getSchema(schemaId);
        return ResponseEntity.ok(schema);
    }

    /**
     * Delete schema
     */
    @DeleteMapping("/{schemaId}")
    public ResponseEntity<Void> deleteSchema(@PathVariable Long schemaId) {
        schemaUploadService.deleteSchema(schemaId);
        return ResponseEntity.noContent().build();
    }
}

