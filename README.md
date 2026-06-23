# QueryMind - AI-Powered SQL & Excel Assistant

**Version:** 1.0.0

## Overview

QueryMind is a production-ready, AI-powered assistant designed for Data Analysts, Business Analysts, and Excel users. It eliminates the need to spend hours writing SQL queries, Excel formulas, XLOOKUP functions, and Power BI DAX expressions by generating them from plain English requests.

## Key Features ⭐

### 1. **SQL Query Generation**
- Convert natural language descriptions to optimized SQL queries
- Support for multiple database types (MySQL, PostgreSQL, SQL Server, Oracle)
- Automatic validation and security checks
- Performance optimization suggestions

### 2. **Excel Formula Generation**
- Create complex Excel formulas from simple English descriptions
- Support for modern Excel 365 and legacy Excel functions
- Automatic cell reference management
- Formula validation and suggestions

### 3. **XLOOKUP Formula Generation**
- Generate advanced XLOOKUP formulas
- Handle missing values with error handling
- Optimize lookup performance
- Support for approximate and exact matches

### 4. **Power BI DAX Generation**
- Create DAX measures and queries
- Support for complex calculations and aggregations
- Best practices enforcement
- Performance optimization for Power BI models

### 5. **Data Analysis & Insights**
- Provide insights based on data structure and context
- Suggest relevant metrics and KPIs
- Recommend visualization approaches
- Generate analysis explanations

## Target Users

- **Data Analysts** - Spend less time coding, more time analyzing
- **Business Analysts** - Generate reports without deep technical knowledge
- **Excel Users** - Create advanced formulas without complex syntax
- **Power BI Developers** - Build DAX expressions faster

## Architecture

### Technology Stack
- **Backend:** Spring Boot 3.1.5 (Java 11+)
- **Database:** H2 (Development), MySQL/PostgreSQL (Production)
- **AI Integration:** OpenAI API (GPT-4)
- **File Processing:** Apache POI (Excel), Commons CSV
- **HTTP Client:** Java HTTP Client
- **ORM:** Spring Data JPA with Hibernate

### Core Components

1. **QueryMindApplication** - Main entry point
2. **AIService** - Handles AI API integration and prompt engineering
3. **SchemaParserService** - Parses SQL, Excel, and CSV files
4. **QueryService** - Orchestrates query generation and validation
5. **CodeValidationService** - Validates generated code
6. **SchemaUploadService** - Manages schema uploads
7. **REST Controllers** - API endpoints for client interaction

### Database Entities

- **SchemaUpload** - Stores uploaded schema files and parsed content
- **QueryHistory** - Maintains audit log of all generated queries and responses

## API Endpoints

### Schema Management
```
POST   /api/v1/schemas/upload          - Upload schema file
GET    /api/v1/schemas                 - Get all schemas
GET    /api/v1/schemas/{schemaId}      - Get specific schema
DELETE /api/v1/schemas/{schemaId}      - Delete schema
```

### Query Generation
```
POST   /api/v1/queries/generate        - Generate any type of code
POST   /api/v1/queries/sql             - Generate SQL
POST   /api/v1/queries/excel/formula   - Generate Excel formula
POST   /api/v1/queries/excel/xlookup   - Generate XLOOKUP
POST   /api/v1/queries/powerbi/dax     - Generate DAX
GET    /api/v1/queries/history         - Get query history
GET    /api/v1/queries/{queryId}       - Get specific query
```

### System Health
```
GET    /api/v1/health                  - Health check
GET    /api/v1/info                    - Application info
```

## Installation & Setup

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- OpenAI API key

### Build & Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/QueryMind.git
   cd QueryMind
   ```

2. **Set environment variable for AI API key**
   ```bash
   # Windows
   set AI_API_KEY=your-openai-api-key-here
   
   # Linux/Mac
   export AI_API_KEY=your-openai-api-key-here
   ```

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - API: http://localhost:8080/querymind
   - Health: http://localhost:8080/querymind/api/v1/health
   - H2 Console: http://localhost:8080/querymind/h2-console

## Usage Examples

### Example 1: Generate SQL Query
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Show top 10 customers by total revenue in the last year",
    "schemaUploadId": 1,
    "explainSolution": true
  }'
```

### Example 2: Generate Excel Formula
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/excel/formula \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Calculate average sales per region and sum by quarter",
    "context": "Data in columns A-D, regions in A, sales in B, dates in C"
  }'
```

### Example 3: Upload Schema
```bash
curl -X POST http://localhost:8080/querymind/api/v1/schemas/upload \
  -F "file=@schema.sql"
```

### Example 4: Generate XLOOKUP
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/excel/xlookup \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Find product price based on product ID",
    "context": "Lookup table in Sheet2, columns A-B"
  }'
```

### Example 5: Generate DAX
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/powerbi/dax \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Calculate year-over-year growth percentage",
    "schemaUploadId": 1
  }'
```

## Configuration

Edit `application.properties` to customize:

```properties
# AI Configuration
querymind.ai-api-key=your-key-here
querymind.ai-model=gpt-4
querymind.max-tokens=2000
querymind.temperature=0.7

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/querymind
spring.datasource.username=root
spring.datasource.password=password

# Server
server.port=8080
```

## Request/Response Format

### QueryRequest
```json
{
  "query": "Your natural language request",
  "schemaUploadId": 1,
  "outputFormat": "SQL|FORMULA|XLOOKUP|DAX",
  "context": "Optional context about data",
  "explainSolution": true
}
```

### QueryResponse
```json
{
  "queryId": 123,
  "generatedCode": "SELECT * FROM ...",
  "codeType": "SQL",
  "explanation": "This query...",
  "confidence": 0.95,
  "suggestedImprovements": ["Consider adding WHERE clause"],
  "processingTimeMs": 1250,
  "success": true
}
```

## Supported File Formats

- **SQL:** `.sql` files (database schemas)
- **Excel:** `.xlsx`, `.xls` files (data structures)
- **CSV:** `.csv` files (tabular data)

## Security Features

- ✅ SQL injection prevention (validates generated SQL)
- ✅ Dangerous operation detection (DROP, DELETE, TRUNCATE)
- ✅ Input validation and sanitization
- ✅ API error handling with detailed logs
- ✅ CORS configuration for cross-origin requests
- ✅ File upload size limits (10MB default)

## Performance Features

- ✅ Async processing for long-running queries
- ✅ Query result caching
- ✅ Database indexing on frequently queried columns
- ✅ Pagination support for large result sets
- ✅ Connection pooling

## Code Quality

- ✅ Lombok for reduced boilerplate
- ✅ Comprehensive logging with SLF4J
- ✅ Global exception handling
- ✅ Input validation
- ✅ RESTful API design
- ✅ Separation of concerns (Controllers → Services → Repositories)

## Testing

Run tests with:
```bash
mvn test
```

## Deployment

### Docker Build
```bash
docker build -t querymind:1.0.0 .
docker run -p 8080:8080 -e AI_API_KEY=your-key querymind:1.0.0
```

### Cloud Deployment
- **AWS:** Deploy to EC2, RDS for database, API Gateway
- **GCP:** Deploy to Cloud Run, Cloud SQL
- **Azure:** Deploy to App Service, SQL Database

## Monitoring & Logging

- Health check endpoint: `/api/v1/health`
- Detailed logs in `logs/` directory
- Query execution metrics stored in database
- Performance monitoring via query execution time

## Future Enhancements

- [ ] Support for additional AI providers (Claude, Gemini)
- [ ] Advanced formula templates library
- [ ] Multi-language support
- [ ] Web UI dashboard
- [ ] Mobile app integration
- [ ] Batch query processing
- [ ] Query optimization recommendations
- [ ] Integration with BI tools (Power BI, Tableau)
- [ ] Machine learning-based quality scoring
- [ ] Natural language query federation

## Contributing

We welcome contributions! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Email: support@querymind.ai
- Documentation: https://querymind.ai/docs

## Credits

Built with ❤️ for Data Analysts and Business Analysts

---

**QueryMind** - Making data analysis faster, easier, and smarter! 🚀

