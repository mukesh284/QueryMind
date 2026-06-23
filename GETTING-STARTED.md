# QueryMind - Getting Started (No Build Tools Required)

## 📋 Prerequisites

You need to install **ONE** of the following:
1. **Docker Desktop** (Easiest - recommended)
2. **Maven** (If you prefer Java development)
3. **Java 11+** (To run pre-built JAR)

---

## ✅ Option 1: Docker Desktop (Recommended - Easiest)

### Step 1: Install Docker Desktop
- Download: https://www.docker.com/products/docker-desktop
- Install and restart your computer
- Verify: Open Command Prompt and run `docker --version`

### Step 2: Build & Run
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
docker build -t querymind:1.0.0 .
docker run -p 8080:8080 -e AI_API_KEY=sk-your-key-here querymind:1.0.0
```

### Step 3: Access Application
```
http://localhost:8080/querymind/api/v1/health
```

---

## ✅ Option 2: Maven Installation

### Step 1: Install Maven
1. Go to https://maven.apache.org/download.cgi
2. Download `apache-maven-3.9.6-bin.zip`
3. Extract to `C:\tools\apache-maven-3.9.6`
4. Add to Windows PATH:
   - Search for "Environment Variables" in Windows
   - Click "Edit the system environment variables"
   - Click "Environment Variables" button
   - Under "System variables", click "New"
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\tools\apache-maven-3.9.6`
   - Find "Path" in System variables, click Edit
   - Add: `C:\tools\apache-maven-3.9.6\bin`
   - Click OK and restart Command Prompt

### Step 2: Verify Installation
```bash
mvn --version
```

### Step 3: Build & Run
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
mvn clean install -DskipTests
mvn spring-boot:run
```

### Step 4: Access Application
```
http://localhost:8080/querymind/api/v1/health
```

---

## ✅ Option 3: Using Pre-built Application

If you can't install Maven or Docker immediately, we can provide a pre-built JAR file.

### Requirements
- Java 11 or higher installed
- Verify: `java -version`

### Run JAR
```bash
# Download the pre-built JAR (coming soon)
java -Dquerymind.ai-api-key=sk-your-key \
     -jar querymind-ai-assistant-1.0.0.jar
```

---

## 🚀 Quick Test (After Starting Application)

```bash
# Test API is working
curl http://localhost:8080/querymind/api/v1/health

# Generate SQL Query
curl -X POST http://localhost:8080/querymind/api/v1/queries/sql ^
  -H "Content-Type: application/json" ^
  -d "{\"query\": \"Show all customers\", \"outputFormat\": \"SQL\"}"
```

---

## 📚 Full Documentation

After getting started:
- **Quick Start**: Read `QUICKSTART.md`
- **API Examples**: Check `examples/API-EXAMPLES.md`
- **Deployment**: See `DEPLOYMENT.md`
- **Contributing**: Read `CONTRIBUTING.md`

---

## 🆘 Common Issues

### Issue: "Maven/Docker not found"
**Solution**: Install one of the options above

### Issue: Java not installed
**Solution**: Download from https://www.oracle.com/java/technologies/downloads/

### Issue: Port 8080 already in use
**Solution**: Change port in `application.properties`:
```properties
server.port=9090
```

### Issue: API Key error
**Solution**: Set your OpenAI API key from https://platform.openai.com/api-keys

---

## 📞 Need Help?

1. Check `INSTALLATION.md` for detailed setup
2. Read `QUICKSTART.md` for first steps
3. Review `README.md` for full documentation
4. Check `examples/API-EXAMPLES.md` for API usage

---

**Recommended**: Install Docker Desktop for the smoothest experience! 🐳

