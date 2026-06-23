# Installation & Deployment Guide

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- OpenAI API key (or alternative AI provider)
- Docker & Docker Compose (for containerized deployment)
- MySQL/PostgreSQL (for production database)

## Local Development Setup

### 1. Clone Repository
```bash
git clone https://github.com/yourusername/QueryMind.git
cd QueryMind
```

### 2. Set Environment Variables
```bash
# Windows
set AI_API_KEY=sk-your-openai-api-key-here

# Linux/Mac
export AI_API_KEY=sk-your-openai-api-key-here
```

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run Locally
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/querymind`

## Docker Deployment

### Build Docker Image
```bash
docker build -t querymind:1.0.0 .
```

### Run Single Container
```bash
docker run -p 8080:8080 \
  -e AI_API_KEY=your-key-here \
  querymind:1.0.0
```

### Run with Docker Compose
```bash
# Copy environment template
cp .env.example .env

# Edit .env with your configuration
nano .env

# Start services
docker-compose up -d

# View logs
docker-compose logs -f querymind-app

# Stop services
docker-compose down
```

## Production Deployment

### AWS Deployment

#### Option 1: EC2 + RDS
```bash
# 1. Launch EC2 instance with Java
# 2. Create RDS MySQL database
# 3. Configure application.properties
# 4. Deploy JAR file
# 5. Start service with systemd
```

#### Option 2: Elastic Beanstalk
```bash
eb init -p "Java with Tomcat"
eb create querymind-env
eb deploy
```

#### Option 3: ECS with Fargate
```bash
# Push image to ECR
aws ecr create-repository --repository-name querymind
docker tag querymind:1.0.0 {account-id}.dkr.ecr.{region}.amazonaws.com/querymind:1.0.0
docker push {account-id}.dkr.ecr.{region}.amazonaws.com/querymind:1.0.0

# Create ECS task and service
```

### GCP Deployment

#### Cloud Run
```bash
gcloud run deploy querymind \
  --image gcr.io/your-project/querymind:1.0.0 \
  --platform managed \
  --region us-central1 \
  --set-env-vars AI_API_KEY=your-key-here
```

#### App Engine
```bash
gcloud app deploy
```

### Azure Deployment

#### App Service
```bash
az webapp create --resource-group QueryMind \
  --plan QueryMindPlan \
  --name querymind-app \
  --runtime "JAVA|11"

az webapp config set --resource-group QueryMind \
  --name querymind-app \
  --startup-file "java -jar querymind.jar"
```

## Production Configuration

### application-prod.properties
```properties
# Server
server.port=8080
server.servlet.context-path=/querymind

# Database - MySQL
spring.datasource.url=jdbc:mysql://prod-db-host:3306/querymind?useSSL=true
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# AI Configuration
querymind.ai-api-key=${AI_API_KEY}
querymind.ai-provider=openai
querymind.ai-model=gpt-4
querymind.max-tokens=2000

# Connection Pooling
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000

# Logging
logging.level.root=INFO
logging.level.com.querymind=INFO
logging.file.name=logs/querymind.log
logging.file.max-size=10MB
logging.file.max-history=30
```

### Run with Production Profile
```bash
java -Dspring.profiles.active=prod \
     -jar querymind-ai-assistant-1.0.0.jar
```

## Performance Tuning

### JVM Options
```bash
java -Xmx2g \
     -Xms1g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+ParallelRefProcEnabled \
     -jar querymind-ai-assistant-1.0.0.jar
```

### Database Optimization
```sql
-- Add indexes for frequently searched columns
CREATE INDEX idx_query_created_at ON query_history(created_at DESC);
CREATE INDEX idx_schema_uploaded_at ON schema_uploads(uploaded_at DESC);
CREATE INDEX idx_query_schema_id ON query_history(schema_upload_id);

-- Enable query result caching
SET SESSION query_cache_type=ON;
SET SESSION query_cache_size=268435456;
```

## Monitoring & Logging

### Application Health Monitoring
```bash
# Check health endpoint
curl http://localhost:8080/querymind/api/v1/health
```

### Log Files
```bash
# View application logs
tail -f logs/querymind.log

# View error logs
grep ERROR logs/querymind.log
```

### Database Monitoring
```sql
-- Check slow queries
SELECT * FROM mysql.slow_log;

-- Check database size
SELECT table_schema AS database_name,
       ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS size_mb
FROM information_schema.tables
GROUP BY table_schema;
```

## Backup & Recovery

### Database Backup
```bash
# MySQL backup
mysqldump -u root -p querymind > querymind_backup_$(date +%Y%m%d_%H%M%S).sql

# Restore from backup
mysql -u root -p querymind < querymind_backup_2024_01_15.sql
```

### File Backup
```bash
# Backup uploaded schemas
tar -czf schemas_backup_$(date +%Y%m%d_%H%M%S).tar.gz /app/schemas/
```

## SSL/TLS Configuration

### Enable HTTPS
```properties
# application-prod.properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
```

### Generate Self-Signed Certificate
```bash
keytool -genkeypair -alias tomcat \
        -keyalg RSA -keysize 2048 \
        -keystore keystore.p12 \
        -keypass password \
        -storepass password \
        -storetype PKCS12
```

## Reverse Proxy Configuration

### Nginx Configuration
```nginx
server {
    listen 443 ssl http2;
    server_name querymind.example.com;

    ssl_certificate /etc/ssl/certs/querymind.crt;
    ssl_certificate_key /etc/ssl/private/querymind.key;

    location / {
        proxy_pass http://localhost:8080/querymind;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

## Troubleshooting

### Common Issues

#### Issue: AI API Key Not Found
```bash
# Solution: Set environment variable
export AI_API_KEY=your-key-here
```

#### Issue: Database Connection Failed
```bash
# Check database connectivity
java -jar querymind-ai-assistant-1.0.0.jar --debug

# Verify connection string in application.properties
```

#### Issue: OutOfMemory Exception
```bash
# Increase JVM heap size
java -Xmx4g -jar querymind-ai-assistant-1.0.0.jar
```

#### Issue: Slow Response Time
```bash
# Check database indexes
# Increase thread pool size
# Optimize AI prompt templates
```

## Support

For deployment issues or questions:
- Check logs: `logs/querymind.log`
- Review documentation: `README.md`
- Open issue on GitHub
- Contact: support@querymind.ai

---

**Happy Deploying! 🚀**

