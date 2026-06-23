# QueryMind API Examples

## 1. Upload a Database Schema

### Request
```bash
curl -X POST http://localhost:8080/querymind/api/v1/schemas/upload \
  -F "file=@sample-schema.sql"
```

### Response
```json
{
  "schemaId": 1,
  "fileName": "sample-schema.sql",
  "schemaType": "SQL_SCHEMA",
  "tableCount": 4,
  "columnCount": 12,
  "databaseType": "MySQL",
  "uploadedAt": "2024-01-15 10:30:45",
  "message": "Schema uploaded successfully",
  "success": true
}
```

## 2. Generate SQL Query from Natural Language

### Request
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Show top 10 customers by total revenue in the last year",
    "schemaUploadId": 1,
    "explainSolution": true
  }'
```

### Response
```json
{
  "queryId": 101,
  "generatedCode": "SELECT c.customer_id, c.customer_name, SUM(o.total_amount) as total_revenue FROM customers c INNER JOIN orders o ON c.customer_id = o.customer_id WHERE o.order_date >= DATE_SUB(CURDATE(), INTERVAL 1 YEAR) GROUP BY c.customer_id, c.customer_name ORDER BY total_revenue DESC LIMIT 10",
  "codeType": "SQL",
  "explanation": "This query joins the customers and orders tables, filters orders from the last year, groups by customer, calculates total revenue, and returns the top 10 customers sorted by revenue in descending order.",
  "confidence": 0.95,
  "suggestedImprovements": [
    "Consider adding an index on order_date for better performance",
    "Add error handling for NULL values in total_amount"
  ],
  "processingTimeMs": 1250,
  "success": true
}
```

## 3. Generate Excel Formula

### Request
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/excel/formula \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Calculate sum of sales by region and quarter",
    "context": "Sales data in columns A-D: Region (A), Amount (B), Date (C), Product (D)",
    "explainSolution": true
  }'
```

### Response
```json
{
  "queryId": 102,
  "generatedCode": "=SUMIFS($B:$B, $A:$A, $A2, $C:$C, \">=\"&DATE(YEAR($C2),QUARTER($C2)*3-2,1), $C:$C, \"<\"&DATE(YEAR($C2),QUARTER($C2)*3+1,1))",
  "codeType": "FORMULA",
  "explanation": "This formula uses SUMIFS to sum amounts where region matches and date falls within the specified quarter.",
  "confidence": 0.88,
  "suggestedImprovements": [
    "Consider using absolute references ($) for ranges to avoid issues when copying",
    "Consider breaking into multiple helper columns for better readability"
  ],
  "processingTimeMs": 890,
  "success": true
}
```

## 4. Generate XLOOKUP Formula

### Request
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/excel/xlookup \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Find product price based on product ID from lookup table",
    "context": "Lookup table in Sheet2, Product IDs in column A, Prices in column B, search value in A2"
  }'
```

### Response
```json
{
  "queryId": 103,
  "generatedCode": "=XLOOKUP(A2, Sheet2!$A:$A, Sheet2!$B:$B, \"Not Found\", 0, 1)",
  "codeType": "XLOOKUP",
  "explanation": "This XLOOKUP searches for the product ID in A2 within Sheet2's column A and returns the corresponding price from column B. Returns 'Not Found' if no match exists.",
  "confidence": 0.92,
  "suggestedImprovements": [
    "XLOOKUP requires Excel 365 or Excel 2021+",
    "Use absolute references for the lookup ranges"
  ],
  "processingTimeMs": 750,
  "success": true
}
```

## 5. Generate Power BI DAX

### Request
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/powerbi/dax \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Calculate year-over-year revenue growth percentage",
    "schemaUploadId": 1
  }'
```

### Response
```json
{
  "queryId": 104,
  "generatedCode": "YOY_Revenue_Growth = VAR CurrentYear = CALCULATE(SUM(Sales[Amount]), FILTER(ALL(Calendar), YEAR(Calendar[Date]) = YEAR(TODAY()))) VAR PreviousYear = CALCULATE(SUM(Sales[Amount]), FILTER(ALL(Calendar), YEAR(Calendar[Date]) = YEAR(TODAY()) - 1)) RETURN DIVIDE(CurrentYear - PreviousYear, PreviousYear)",
  "codeType": "DAX",
  "explanation": "This DAX measure calculates the year-over-year revenue growth by comparing current year sales to previous year sales and computing the percentage change.",
  "confidence": 0.90,
  "suggestedImprovements": [
    "Consider using CALCULATE with date filters instead of FILTER for better performance",
    "Add error handling for division by zero"
  ],
  "processingTimeMs": 1100,
  "success": true
}
```

## 6. Health Check

### Request
```bash
curl http://localhost:8080/querymind/api/v1/health
```

### Response
```json
{
  "status": "UP",
  "application": "QueryMind AI Assistant",
  "version": "1.0.0"
}
```

---

For more examples, see the full documentation in README.md

