package com.querymind.service.schema;

import com.querymind.entity.SchemaUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.*;
import java.util.*;

/**
 * Service for parsing various file formats (SQL, Excel, CSV)
 */
@Service
@Slf4j
public class SchemaParserService {

    /**
     * Parse SQL schema file
     */
    public String parseSQLSchema(InputStream inputStream) throws IOException {
        StringBuilder schema = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                schema.append(line).append("\n");
            }
        }
        return cleanAndValidateSQL(schema.toString());
    }

    /**
     * Parse Excel file and extract structure
     */
    public String parseExcelSchema(InputStream inputStream, String fileName) throws IOException {
        StringBuilder schema = new StringBuilder();
        schema.append("Excel File: ").append(fileName).append("\n\n");

        try (Workbook workbook = createWorkbook(inputStream, fileName)) {
            int sheetCount = workbook.getNumberOfSheets();
            schema.append("Total Sheets: ").append(sheetCount).append("\n\n");

            for (int i = 0; i < sheetCount; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                schema.append("Sheet: ").append(sheet.getSheetName()).append("\n");

                if (sheet.getPhysicalNumberOfRows() > 0) {
                    Row headerRow = sheet.getRow(0);
                    if (headerRow != null) {
                        schema.append("Columns: ");
                        List<String> columns = new ArrayList<>();
                        for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
                            Cell cell = headerRow.getCell(j);
                            if (cell != null) {
                                columns.add(cell.toString());
                            }
                        }
                        schema.append(String.join(", ", columns)).append("\n");
                        schema.append("Sample Data:\n");

                        int sampleRows = Math.min(3, sheet.getPhysicalNumberOfRows() - 1);
                        for (int row = 1; row <= sampleRows; row++) {
                            Row dataRow = sheet.getRow(row);
                            if (dataRow != null) {
                                for (int j = 0; j < dataRow.getPhysicalNumberOfCells(); j++) {
                                    schema.append(dataRow.getCell(j)).append(" | ");
                                }
                                schema.append("\n");
                            }
                        }
                    }
                }
                schema.append("\n");
            }
        }
        return schema.toString();
    }

    /**
     * Parse CSV file and extract structure
     */
    public String parseCSVSchema(InputStream inputStream) throws IOException {
        StringBuilder schema = new StringBuilder();
        schema.append("CSV File Structure:\n\n");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            Map<String, Integer> headers = csvParser.getHeaderMap();
            schema.append("Columns: ").append(String.join(", ", headers.keySet())).append("\n\n");

            schema.append("Sample Data:\n");
            int count = 0;
            for (CSVRecord record : csvParser) {
                if (count >= 3) break;
                for (String header : headers.keySet()) {
                    schema.append(record.get(header)).append(" | ");
                }
                schema.append("\n");
                count++;
            }
        }
        return schema.toString();
    }

    /**
     * Create workbook from input stream
     */
    private Workbook createWorkbook(InputStream inputStream, String fileName) throws IOException {
        if (fileName.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else if (fileName.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        }
        throw new IllegalArgumentException("Unsupported Excel format: " + fileName);
    }

    /**
     * Clean and validate SQL schema
     */
    private String cleanAndValidateSQL(String sql) {
        String cleaned = sql.trim()
            .replaceAll("(?i)^(USE|DATABASE|SCHEMA).*?;", "")
            .trim();
        log.info("Parsed SQL schema with {} characters", cleaned.length());
        return cleaned;
    }

    /**
     * Infer database type from schema
     */
    public String inferDatabaseType(String schema) {
        if (schema.contains("SERIAL") || schema.contains("BIGSERIAL")) {
            return "PostgreSQL";
        } else if (schema.contains("AUTO_INCREMENT")) {
            return "MySQL";
        } else if (schema.contains("IDENTITY")) {
            return "SQL Server";
        } else if (schema.contains("OracleSequence")) {
            return "Oracle";
        }
        return "Unknown";
    }

    /**
     * Extract table and column information
     */
    public Map<String, Object> extractSchemaMetadata(String schema) {
        Map<String, Object> metadata = new HashMap<>();

        // Count tables
        int tableCount = schema.split("(?i)CREATE TABLE").length - 1;
        metadata.put("tableCount", tableCount);

        // Count columns (approximate)
        int columnCount = schema.split("(?i),").length;
        metadata.put("columnCount", columnCount);

        // Extract table names
        List<String> tableNames = new ArrayList<>();
        String[] lines = schema.split("\n");
        for (String line : lines) {
            if (line.toLowerCase().contains("create table")) {
                String tableName = line.replaceAll("(?i)create table\\s+", "").split("[\\s(]")[0];
                tableNames.add(tableName);
            }
        }
        metadata.put("tableNames", tableNames);

        return metadata;
    }
}

