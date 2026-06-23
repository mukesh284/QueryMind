# QueryMind - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### 1. Prerequisites
- Java 11+
- Maven 3.6+
- OpenAI API key from https://platform.openai.com/api-keys

### 2. Setup

```bash
# Clone the repository
git clone https://github.com/yourusername/QueryMind.git
cd QueryMind

# Set your OpenAI API key
export AI_API_KEY=sk-your-key-here

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 3. Access the Application
- API Base URL: http://localhost:8080/querymind
- Health Check: http://localhost:8080/querymind/api/v1/health
- H2 Database Console: http://localhost:8080/querymind/h2-console

### 4. First Query

Open a terminal and run:

```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d '{
    "query": "Show all customers with their total orders",
    "outputFormat": "SQL",
    "explainSolution": true
  }'
```

Response will include:
- `generatedCode`: The SQL query
- `explanation`: How the query works
- `confidence`: How confident the AI is
- `suggestedImprovements`: Optimization tips

### 5. Upload a Schema

```bash
curl -X POST http://localhost:8080/querymind/api/v1/schemas/upload \
  -F "file=@examples/sample-schema.sql"
```

Then use the returned `schemaId` in future queries for context.

## 📊 Supported Query Types

### SQL Queries
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d '{"query": "Top 10 customers by revenue"}'
```

### Excel Formulas
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/excel/formula \
  -H "Content-Type: application/json" \
  -d '{"query": "Sum sales by region", "context": "Sales in column B, Region in column A"}'
```

### XLOOKUP
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/excel/xlookup \
  -H "Content-Type: application/json" \
  -d '{"query": "Find product price by product ID"}'
```

### Power BI DAX
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/powerbi/dax \
  -H "Content-Type: application/json" \
  -d '{"query": "Calculate year-over-year growth"}'
```

## 🐳 Docker Quick Start

```bash
# Build Docker image
docker build -t querymind:1.0.0 .

# Run with Docker
docker run -p 8080:8080 -e AI_API_KEY=your-key querymind:1.0.0

# Or use Docker Compose
docker-compose up -d
```

## 📚 Example Queries

Try these natural language queries:

1. **"Show top 5 products by sales"** → SQL
2. **"Calculate average price per category"** → SQL
3. **"Create a lookup for customer names"** → XLOOKUP
4. **"Sum values by date and region"** → Excel Formula
5. **"Calculate customer lifetime value"** → DAX

## ✨ Key Features

✅ AI-powered code generation  
✅ Natural language input  
✅ Multi-format output (SQL, Excel, DAX)  
✅ Code validation & security checks  
✅ Optimization suggestions  
✅ Query history & analytics  
✅ Batch processing  
✅ RESTful API  

## 🔐 Production Deployment

See `DEPLOYMENT.md` for:
- AWS deployment (EC2, RDS, Elastic Beanstalk, ECS)
- GCP deployment (Cloud Run, App Engine)
- Azure deployment (App Service)
- Docker & Kubernetes setup
- SSL/TLS configuration
- Performance tuning

## 📖 Documentation

- `README.md` - Full documentation
- `DEPLOYMENT.md` - Deployment guide
- `examples/API-EXAMPLES.md` - API examples
- `examples/sample-schema.sql` - Sample database schema

## 🆘 Troubleshooting

**Issue: "AI API key not configured"**
```bash
export AI_API_KEY=your-key-here
```

**Issue: Database connection error**
- Check `application.properties` database configuration
- For production, update with your database credentials

**Issue: Port 8080 already in use**
```bash
# Use different port
java -Dserver.port=9090 -jar target/querymind-ai-assistant-1.0.0.jar
```

## 📞 Support

- Documentation: See README.md
- Issues: GitHub Issues
- Email: support@querymind.ai

---

**Ready to enhance your analytics workflow? Start now! 🎯**

