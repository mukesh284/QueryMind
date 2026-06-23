# Installation Guide - Maven Not Required

## Quick Start (Docker - Recommended)

If you don't have Maven installed, use Docker to build and run QueryMind:

### Prerequisites
- Docker Desktop (https://www.docker.com/products/docker-desktop)

### Build with Docker
```bash
# On Windows (PowerShell)
cd C:\Users\mukes\IdeaProjects\QueryMind
docker build -t querymind:1.0.0 .

# On Mac/Linux
cd QueryMind
docker build -t querymind:1.0.0 .
```

### Run with Docker
```bash
docker run -p 8080:8080 \
  -e AI_API_KEY=sk-your-openai-api-key-here \
  querymind:1.0.0
```

### Run with Docker Compose
```bash
# Set up environment
cp .env.example .env
# Edit .env with your API key

# Start services
docker-compose up -d

# View logs
docker-compose logs -f querymind-app

# Stop services
docker-compose down
```

---

## Install Maven (If Needed)

### Windows
1. Download Maven from https://maven.apache.org/download.cgi
2. Extract to `C:\tools\apache-maven-3.9.0`
3. Add to PATH:
   - Right-click "This PC" → Properties
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - Add `C:\tools\apache-maven-3.9.0\bin` to PATH
4. Verify: Open new Command Prompt and run `mvn --version`

### Mac
```bash
brew install maven
mvn --version
```

### Linux
```bash
sudo apt-get install maven
mvn --version
```

---

## Build with Maven (After Installation)

```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Build
mvn clean install -DskipTests

# Run
mvn spring-boot:run
```

---

## Alternative: Run Pre-built JAR

1. Build locally with Docker first to generate JAR
2. Then run the JAR directly:

```bash
java -Dspring.profiles.active=prod \
     -Dserver.port=8080 \
     -Dquerymind.ai-api-key=sk-your-key \
     -jar target/querymind-ai-assistant-1.0.0.jar
```

---

## Recommended: Docker Compose (Easiest)

This is the **fastest and easiest way** to get started:

1. Install Docker Desktop
2. Navigate to project: `cd C:\Users\mukes\IdeaProjects\QueryMind`
3. Run setup script:
   - **Windows**: `setup.bat`
   - **Mac/Linux**: `./setup.sh`
4. Or manually:
   ```bash
   docker-compose up -d
   ```

---

## Access the Application

After starting with Docker or Maven:

- **API**: http://localhost:8080/querymind/api/v1
- **Health Check**: http://localhost:8080/querymind/api/v1/health
- **Info**: http://localhost:8080/querymind/api/v1/info

---

## Troubleshooting

### Docker Build Fails
- Ensure Docker Desktop is running
- Check Docker is installed: `docker --version`

### Port 8080 Already in Use
```bash
# Change port when running
docker run -p 9090:8080 querymind:1.0.0
```

### API Key Not Working
- Verify you set `AI_API_KEY` environment variable
- Check OpenAI API key is valid at https://platform.openai.com/api-keys

### Database Connection Error
- For Docker: Uses embedded H2 database automatically
- For production: Update `application-prod.properties` with your database

---

## Next Steps

1. **Make First Query**: See `QUICKSTART.md`
2. **Explore API**: Check `examples/API-EXAMPLES.md`
3. **Deploy to Cloud**: See `DEPLOYMENT.md`
4. **Contribute**: Read `CONTRIBUTING.md`

