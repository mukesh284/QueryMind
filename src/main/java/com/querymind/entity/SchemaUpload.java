package com.querymind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entity representing a database schema upload
 */
@Entity
@Table(name = "schema_uploads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchemaUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(columnDefinition = "LONGTEXT")
    private String schemaContent;

    @Column(columnDefinition = "LONGTEXT")
    private String parsedSchema;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private String databaseType; // MySQL, PostgreSQL, SQL Server, etc.

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    private LocalDateTime lastUsedAt;

    private int queryCount = 0;

    public enum FileType {
        SQL_SCHEMA, EXCEL, CSV, JSON
    }
}

