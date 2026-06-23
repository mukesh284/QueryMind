package com.querymind.service.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Service for validating generated code
 */
@Service
@Slf4j
public class CodeValidationService {

    /**
     * Validate generated code
     */
    public boolean validateCode(String code, String codeType) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }

        switch (codeType.toUpperCase()) {
            case "SQL":
                return validateSQL(code);
            case "FORMULA":
                return validateExcelFormula(code);
            case "XLOOKUP":
                return validateXLOOKUP(code);
            case "DAX":
                return validateDAX(code);
            default:
                return true;
        }
    }

    /**
     * Validate SQL code
     */
    private boolean validateSQL(String sql) {
        String upperSql = sql.toUpperCase().trim();

        // Check for dangerous operations
        if (upperSql.contains("DROP ") || upperSql.contains("DELETE ") ||
            upperSql.contains("TRUNCATE") || upperSql.contains("ALTER ")) {
            log.warn("Dangerous SQL operation detected");
            return false;
        }

        // Check for basic SQL structure
        boolean hasSelect = upperSql.startsWith("SELECT") || upperSql.startsWith("WITH");
        boolean hasFrom = upperSql.contains("FROM");

        return hasSelect && hasFrom;
    }

    /**
     * Validate Excel formula
     */
    private boolean validateExcelFormula(String formula) {
        if (!formula.startsWith("=")) {
            return false;
        }

        // Check for balanced parentheses
        int openParen = 0;
        for (char c : formula.toCharArray()) {
            if (c == '(') openParen++;
            if (c == ')') openParen--;
            if (openParen < 0) return false;
        }

        return openParen == 0;
    }

    /**
     * Validate XLOOKUP formula
     */
    private boolean validateXLOOKUP(String formula) {
        String upperFormula = formula.toUpperCase();

        // Must contain XLOOKUP function
        if (!upperFormula.contains("XLOOKUP")) {
            return false;
        }

        // Check for balanced parentheses
        return validateExcelFormula(formula);
    }

    /**
     * Validate DAX code
     */
    private boolean validateDAX(String dax) {
        String upperDax = dax.toUpperCase();

        // Must contain measure or query syntax
        boolean hasMeasure = upperDax.contains("MEASURE") ||
                           upperDax.contains("EVALUATE") ||
                           upperDax.contains("RETURN");

        // Check for balanced parentheses and brackets
        int openParen = 0;
        int openBracket = 0;
        for (char c : dax.toCharArray()) {
            if (c == '(') openParen++;
            if (c == ')') openParen--;
            if (c == '[') openBracket++;
            if (c == ']') openBracket--;
        }

        return hasMeasure && openParen == 0 && openBracket == 0;
    }

    /**
     * Get improvement suggestions for code
     */
    public List<String> getSuggestions(String code, String codeType) {
        List<String> suggestions = new ArrayList<>();

        switch (codeType.toUpperCase()) {
            case "SQL":
                suggestions.addAll(getSQLSuggestions(code));
                break;
            case "FORMULA":
                suggestions.addAll(getFormulaSuggestions(code));
                break;
            case "XLOOKUP":
                suggestions.addAll(getXLOOKUPSuggestions(code));
                break;
            case "DAX":
                suggestions.addAll(getDAXSuggestions(code));
                break;
        }

        return suggestions;
    }

    /**
     * Get SQL optimization suggestions
     */
    private List<String> getSQLSuggestions(String sql) {
        List<String> suggestions = new ArrayList<>();
        String upperSql = sql.toUpperCase();

        if (upperSql.contains("SELECT *")) {
            suggestions.add("Consider specifying exact columns instead of SELECT * for better performance");
        }

        if (!upperSql.contains("ORDER BY") && upperSql.contains("LIMIT")) {
            suggestions.add("Add ORDER BY clause for consistent pagination results");
        }

        if (upperSql.contains("OR") && !upperSql.contains("INDEX")) {
            suggestions.add("Consider using IN clause instead of multiple OR conditions");
        }

        if (!upperSql.contains("WHERE")) {
            suggestions.add("Consider adding WHERE clause to filter unnecessary data");
        }

        if (upperSql.contains("JOIN") && upperSql.split("JOIN").length > 3) {
            suggestions.add("Query has many joins - consider materialized views for better performance");
        }

        return suggestions;
    }

    /**
     * Get Excel formula suggestions
     */
    private List<String> getFormulaSuggestions(String formula) {
        List<String> suggestions = new ArrayList<>();

        if (formula.toUpperCase().contains("VLOOKUP")) {
            suggestions.add("Consider using XLOOKUP for modern Excel - it's more flexible than VLOOKUP");
        }

        if (!formula.contains("$")) {
            suggestions.add("Consider using absolute references ($) if copying formula to other cells");
        }

        if (formula.length() > 500) {
            suggestions.add("Formula is quite complex - consider breaking it into multiple helper columns");
        }

        if (formula.toUpperCase().contains("IF") &&
            formula.toUpperCase().split("IF").length > 5) {
            suggestions.add("Multiple nested IFs detected - consider using SWITCH or IFS function");
        }

        return suggestions;
    }

    /**
     * Get XLOOKUP suggestions
     */
    private List<String> getXLOOKUPSuggestions(String formula) {
        List<String> suggestions = new ArrayList<>();

        if (!formula.contains(",")) {
            suggestions.add("Ensure XLOOKUP has all required parameters separated by commas");
        }

        if (!formula.toUpperCase().contains("IFERROR") && !formula.contains("-1")) {
            suggestions.add("Consider adding error handling for missing values");
        }

        suggestions.add("XLOOKUP is available in Excel 365 and Excel 2021 or later");

        return suggestions;
    }

    /**
     * Get DAX optimization suggestions
     */
    private List<String> getDAXSuggestions(String dax) {
        List<String> suggestions = new ArrayList<>();

        if (dax.toUpperCase().contains("CALCULATETABLE")) {
            suggestions.add("Use CALCULATE with appropriate filter context instead of CALCULATETABLE when possible");
        }

        if (dax.toUpperCase().contains("SUMX") || dax.toUpperCase().contains("MAXX")) {
            suggestions.add("X functions are slower - consider using aggregation functions when possible");
        }

        if (!dax.toUpperCase().contains("VAR") && dax.length() > 300) {
            suggestions.add("Consider using variables (VAR) to improve readability and performance");
        }

        if (dax.toUpperCase().contains("ALLEXCEPT")) {
            suggestions.add("ALLEXCEPT can be slower - consider using ALL with specific columns");
        }

        return suggestions;
    }
}

