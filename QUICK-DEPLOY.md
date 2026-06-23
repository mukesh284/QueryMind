# 🎯 DEPLOY & RUN - QUICK REFERENCE CARD

## 🚀 FASTEST PATH (15 Minutes to Live App)

### Step-by-Step Commands

```bash
# Step 1: Go to GitHub and create account
# https://github.com/signup

# Step 2: Install Git (if not already installed)
# Download from https://git-scm.com/download/win

# Step 3: Push your code to GitHub
cd C:\Users\mukes\IdeaProjects\QueryMind
git init
git add .
git commit -m "QueryMind deployment"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/QueryMind.git
git push -u origin main

# Step 4: Deploy to Railway
# 1. Go to https://railway.app
# 2. Click "Start a New Project"
# 3. Click "Deploy from GitHub"
# 4. Select your QueryMind repository
# 5. Wait 2-3 minutes for build

# Step 5: Add API Key in Railway Dashboard
# Variables → New Variable
# Key: AI_API_KEY
# Value: sk-your-openai-api-key-here
# Click Redeploy

# Step 6: Test your live app
curl https://querymind-xxxx.railway.app/querymind/api/v1/health
```

---

## 🏠 RUN LOCALLY (5-10 Minutes)

### Option A: Docker Compose (EASIEST)
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Create .env file with:
# AI_API_KEY=sk-your-openai-api-key-here

# Run:
docker-compose up -d

# Access at:
# http://localhost:8080/querymind/api/v1/health
```

### Option B: Docker
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
set AI_API_KEY=sk-your-openai-api-key-here
docker build -t querymind:1.0.0 .
docker run -p 8080:8080 -e AI_API_KEY=%AI_API_KEY% querymind:1.0.0
```

### Option C: Maven
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
set AI_API_KEY=sk-your-openai-api-key-here
mvn clean install -DskipTests
mvn spring-boot:run
```

---

## ✅ TEST YOUR APP

### Health Check (copy & paste)
```bash
curl http://localhost:8080/querymind/api/v1/health
```

### Generate SQL Query (copy & paste)
```bash
curl -X POST http://localhost:8080/querymind/api/v1/queries/sql ^
  -H "Content-Type: application/json" ^
  -d "{\"query\": \"Show all customers\", \"outputFormat\": \"SQL\"}"
```

### Upload Schema (copy & paste)
```bash
curl -X POST http://localhost:8080/querymind/api/v1/schemas/upload ^
  -F "file=@examples/sample-schema.sql"
```

---

## 📊 API ENDPOINTS (After Deployment)

```
Health: http://localhost:8080/querymind/api/v1/health
SQL: http://localhost:8080/querymind/api/v1/queries/sql
Formula: http://localhost:8080/querymind/api/v1/queries/excel/formula
XLOOKUP: http://localhost:8080/querymind/api/v1/queries/excel/xlookup
DAX: http://localhost:8080/querymind/api/v1/queries/powerbi/dax
Upload: http://localhost:8080/querymind/api/v1/schemas/upload
History: http://localhost:8080/querymind/api/v1/queries/history
```

---

## 🆘 COMMON ISSUES & FIXES

| Issue | Fix |
|-------|-----|
| `git: command not found` | Install Git from https://git-scm.com |
| `docker: command not found` | Install Docker from https://docker.com |
| `Maven not found` | Download from https://maven.apache.org |
| `API key error` | Get key from https://platform.openai.com/api-keys |
| `Port 8080 in use` | Change port or kill process using it |
| `Build failed` | Check internet, try `mvn clean install` locally |

---

## 🎯 WHICH PATH TO CHOOSE?

```
Do you have Docker installed?
├─ YES → Use Docker Compose (5 min)
└─ NO  → Do you have Maven installed?
         ├─ YES → Use Maven (15 min)
         └─ NO  → Use Railway Cloud (15 min, easiest!)
```

---

## 📱 For Railway Cloud Deployment

**Before starting:**
- GitHub account created ✅
- OpenAI API key ready ✅
- Git installed ✅

**Steps:**
1. `git init` + `git push -u origin main`
2. Railway connects to GitHub
3. Railway auto-builds in 2-3 min
4. Add API key in variables
5. Get live URL
6. **DONE!** 🎉

---

## 🔑 YOUR CREDENTIALS

**Never share these:**
```bash
AI_API_KEY=sk-your-openai-api-key-here
```

**Keep this secret!**
- Don't commit to GitHub
- Don't put in code comments
- Only set as environment variable
- Cloud platforms handle this securely

---

## 📝 AFTER DEPLOYMENT

Your app URLs will be:

**If using Railway:**
```
https://querymind-xxxx.railway.app/querymind/api/v1/health
```

**If using Docker locally:**
```
http://localhost:8080/querymind/api/v1/health
```

**If using Google Cloud Run:**
```
https://querymind-xxxxx.run.app/querymind/api/v1/health
```

---

## ✨ SUCCESS INDICATORS

Your deployment works when:
- ✅ Health endpoint returns `"status": "UP"`
- ✅ You can generate SQL queries
- ✅ You can upload schemas
- ✅ Response times are fast
- ✅ No error messages in logs

---

## 🚀 NEXT: Try These Queries

Once running, test with:

1. **SQL Generation:**
   ```
   "Show top 10 customers by revenue"
   ```

2. **Excel Formula:**
   ```
   "Sum sales by region"
   ```

3. **XLOOKUP:**
   ```
   "Find product price by ID"
   ```

4. **DAX:**
   ```
   "Calculate year-over-year growth"
   ```

---

## 📞 NEED HELP?

1. Check `HOW-TO-DEPLOY-AND-RUN.md` for detailed steps
2. Check `examples/API-EXAMPLES.md` for API usage
3. Check Railway docs: https://docs.railway.app
4. Check Docker docs: https://docs.docker.com

---

## 🎉 YOU'RE READY!

Pick one option above and follow the commands.

**Recommended:** Railway (easiest, most beginner-friendly) ⭐

**Let's deploy!** 🚀

