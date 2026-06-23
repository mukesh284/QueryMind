# 🚀 COMPLETE DEPLOYMENT & RUN GUIDE

## 🎯 Choose Your Path

### Path 1: ⭐ **EASIEST** - Cloud Deployment (Railway) - FREE
**Time: 15 minutes | Cost: FREE | Setup: 1-click**

### Path 2: 🏠 **LOCAL** - Run on Your Computer
**Time: 5 minutes | Cost: FREE | Setup: Docker**

### Path 3: 💻 **ADVANCED** - Production Setup (AWS/GCP)
**Time: 30 minutes | Cost: FREE tier available | Setup: More complex**

---

# 🌐 PATH 1: DEPLOY TO CLOUD (RECOMMENDED - EASIEST)

## Step 1️⃣: Create Free GitHub Account
**Time: 2 minutes**

1. Go to: https://github.com/signup
2. Enter email and create password
3. Verify your email
4. ✅ Done!

---

## Step 2️⃣: Install Git on Your Computer
**Time: 5 minutes**

### Windows:
1. Download: https://git-scm.com/download/win
2. Run installer (use default settings)
3. Restart your computer

### Mac:
```bash
brew install git
```

### Linux:
```bash
sudo apt-get install git
```

Verify:
```bash
git --version
```

---

## Step 3️⃣: Push Your Code to GitHub
**Time: 3 minutes**

Open Command Prompt or PowerShell:

```bash
# Navigate to your project
cd C:\Users\mukes\IdeaProjects\QueryMind

# Initialize git
git init

# Add all files
git add .

# Create first commit
git commit -m "QueryMind - AI-Powered SQL & Excel Assistant"

# Rename branch to main (if needed)
git branch -M main

# Add remote (replace YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/QueryMind.git

# Push to GitHub
git push -u origin main
```

✅ Your code is now on GitHub!

---

## Step 4️⃣: Deploy to Railway (ONE CLICK!)
**Time: 3 minutes**

1. Go to: https://railway.app
2. Click "Start a New Project"
3. Click "Deploy from GitHub"
4. Search for "QueryMind"
5. Select your repository
6. Click "Deploy"

**Railway will automatically:**
- ✅ Detect Java/Maven project
- ✅ Download all dependencies
- ✅ Build Docker image
- ✅ Deploy to cloud
- ✅ Give you a live URL

Wait 2-3 minutes for build to complete...

---

## Step 5️⃣: Add Your OpenAI API Key
**Time: 2 minutes**

1. Get API key: https://platform.openai.com/api-keys
2. Go to your Railway dashboard
3. Select your service
4. Click "Variables"
5. Click "New Variable"
6. Enter:
   - **Key**: `AI_API_KEY`
   - **Value**: `sk-your-openai-api-key-here`
7. Click "Redeploy"

---

## Step 6️⃣: TEST YOUR LIVE APP! 🎉
**Time: 1 minute**

Railway shows you your live URL:
```
https://querymind-xxxx.railway.app
```

### Test it:
```bash
# Health check
curl https://querymind-xxxx.railway.app/querymind/api/v1/health

# Try generating SQL
curl -X POST https://querymind-xxxx.railway.app/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d "{\"query\": \"Show all customers\", \"outputFormat\": \"SQL\"}"
```

### Expected Response:
```json
{
  "status": "UP",
  "application": "QueryMind AI Assistant"
}
```

✅ **YOUR APP IS LIVE!** 🚀

---

## 📊 YOUR LIVE ENDPOINTS

| Endpoint | URL |
|----------|-----|
| Health Check | `https://querymind-xxxx.railway.app/querymind/api/v1/health` |
| Generate SQL | `https://querymind-xxxx.railway.app/querymind/api/v1/queries/sql` |
| Upload Schema | `https://querymind-xxxx.railway.app/querymind/api/v1/schemas/upload` |
| Generate Formula | `https://querymind-xxxx.railway.app/querymind/api/v1/queries/excel/formula` |
| Generate XLOOKUP | `https://querymind-xxxx.railway.app/querymind/api/v1/queries/excel/xlookup` |
| Generate DAX | `https://querymind-xxxx.railway.app/querymind/api/v1/queries/powerbi/dax` |
| View History | `https://querymind-xxxx.railway.app/querymind/api/v1/queries/history` |

---

---

# 🏠 PATH 2: RUN LOCALLY (ON YOUR COMPUTER)

## Option A: Using Docker (Easiest Local)
**Time: 10 minutes**

### Step 1: Install Docker Desktop
1. Download: https://www.docker.com/products/docker-desktop
2. Install and restart your computer
3. Verify: `docker --version`

### Step 2: Build & Run
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Set your API key
set AI_API_KEY=sk-your-openai-api-key-here

# Build Docker image
docker build -t querymind:1.0.0 .

# Run container
docker run -p 8080:8080 -e AI_API_KEY=%AI_API_KEY% querymind:1.0.0
```

### Step 3: Access Locally
```
http://localhost:8080/querymind/api/v1/health
```

---

## Option B: Using Docker Compose (Easiest with Database)
**Time: 5 minutes**

### Step 1: Create .env file
In your project folder, create `.env`:
```bash
AI_API_KEY=sk-your-openai-api-key-here
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=querymind123
```

### Step 2: Run Docker Compose
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
docker-compose up -d
```

### Step 3: Check Status
```bash
# View logs
docker-compose logs -f querymind-app

# Check services
docker-compose ps

# Stop services
docker-compose down
```

### Step 4: Access
```
http://localhost:8080/querymind/api/v1/health
```

---

## Option C: Using Maven (If Java Installed)
**Time: 15 minutes**

### Step 1: Install Maven
- Download: https://maven.apache.org/download.cgi
- Extract to `C:\tools\apache-maven-3.9.6`
- Add to PATH environment variable
- Verify: `mvn --version`

### Step 2: Build & Run
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Set API key
set AI_API_KEY=sk-your-openai-api-key-here

# Build
mvn clean install -DskipTests

# Run
mvn spring-boot:run
```

### Step 3: Access
```
http://localhost:8080/querymind/api/v1/health
```

---

---

# 💻 PATH 3: ADVANCED CLOUD DEPLOYMENT

## Option A: Google Cloud Run (Free Tier!)
**Time: 20 minutes | Cost: FREE (2M requests/month)**

### Prerequisites:
- Google Cloud Account: https://cloud.google.com/run

### Deploy:
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Login to Google Cloud
gcloud auth login

# Create project
gcloud projects create querymind

# Enable services
gcloud services enable run.googleapis.com cloudbuild.googleapis.com

# Deploy
gcloud run deploy querymind \
  --source . \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated \
  --set-env-vars AI_API_KEY=sk-your-key-here \
  --memory 512Mi
```

### Get URL:
```
Your app is deployed at: https://querymind-xxxxx.run.app
```

---

## Option B: Heroku (Legacy - Limited Free Tier)
**Time: 10 minutes**

### Prerequisites:
- Heroku Account: https://www.heroku.com
- Heroku CLI: https://devcenter.heroku.com/articles/heroku-cli

### Deploy:
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Login
heroku login

# Create app
heroku create querymind

# Set API key
heroku config:set AI_API_KEY=sk-your-openai-api-key-here

# Deploy
git push heroku main
```

### Get URL:
```
Your app is deployed at: https://querymind.herokuapp.com
```

---

## Option C: AWS (More Complex - But Most Powerful)
**Time: 30 minutes**

### Using EC2:
1. Create AWS Account: https://aws.amazon.com
2. Launch EC2 instance (t3.micro - free tier)
3. Install Java 11 and Maven
4. Clone your repository
5. Run: `mvn clean install && mvn spring-boot:run`

### Using Elastic Beanstalk:
```bash
# Install EB CLI
pip install awsebcli

# Initialize
eb init -p "Java with Tomcat"

# Create and deploy
eb create querymind-env
eb deploy
```

---

---

# 📋 QUICK COMPARISON TABLE

| Method | Time | Cost | Ease | Best For |
|--------|------|------|------|----------|
| **Railway Cloud** ⭐ | 15 min | FREE | ⭐⭐⭐⭐⭐ | Beginners |
| **Docker Local** | 10 min | FREE | ⭐⭐⭐⭐ | Developers |
| **Docker Compose** | 5 min | FREE | ⭐⭐⭐⭐ | Full setup |
| **Maven Local** | 15 min | FREE | ⭐⭐⭐ | Java devs |
| **Google Cloud Run** | 20 min | FREE | ⭐⭐⭐ | Scale |
| **AWS** | 30 min | FREE tier | ⭐⭐ | Enterprise |

---

# ✅ VERIFICATION CHECKLIST

After deployment/running, verify:

### 1. Health Check
```bash
curl https://your-url/querymind/api/v1/health
```

Response should be:
```json
{
  "status": "UP",
  "application": "QueryMind AI Assistant",
  "version": "1.0.0"
}
```

### 2. Generate SQL Query
```bash
curl -X POST https://your-url/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d '{"query": "Show all customers", "outputFormat": "SQL"}'
```

Response should include `generatedCode` with SQL query.

### 3. Upload Schema
```bash
curl -X POST https://your-url/querymind/api/v1/schemas/upload \
  -F "file=@examples/sample-schema.sql"
```

Response should include `schemaId`.

### 4. View API Info
```bash
curl https://your-url/querymind/api/v1/info
```

---

# 🆘 TROUBLESHOOTING

## Cloud Deployment Issues

### "Build Failed"
- Check if Maven compiles locally: `mvn clean install`
- Check pom.xml for syntax errors
- View build logs in cloud platform

### "API Key Not Working"
- Verify key: https://platform.openai.com/api-keys
- Make sure you set it in platform variables
- Redeploy after adding API key

### "Connection Timeout"
- First deploy takes 2-3 minutes
- Check internet connection
- Verify API key is correct

### "Port Error"
- Our app handles PORT automatically
- Don't need to change anything

---

## Local Deployment Issues

### "Docker Not Found"
- Install Docker Desktop
- Restart your computer
- Run: `docker --version`

### "Port 8080 Already in Use"
```bash
# Change port in application.properties:
server.port=9090

# Or in Docker:
docker run -p 9090:8080 querymind:1.0.0
```

### "Maven Not Installed"
- Download from: https://maven.apache.org/download.cgi
- Extract to `C:\tools`
- Add to PATH
- Restart terminal

### "API Key Error"
```bash
# Verify it's set:
echo %AI_API_KEY%

# Or set it:
set AI_API_KEY=sk-your-key-here
```

---

# 🎯 NEXT STEPS

## After Successful Deployment:

1. **Test All Endpoints**
   - See examples in `examples/API-EXAMPLES.md`

2. **Upload Sample Schema**
   - Use `examples/sample-schema.sql`

3. **Generate Different Query Types**
   - SQL queries
   - Excel formulas
   - XLOOKUP functions
   - DAX measures

4. **Monitor Performance**
   - Check logs in your platform dashboard
   - View API response times
   - Monitor usage

5. **Share Your App**
   - Share live URL with others
   - Get feedback
   - Improve features

---

# 📚 DOCUMENTATION

| Document | Purpose |
|----------|---------|
| `README.md` | Full feature documentation |
| `QUICKSTART.md` | 5-minute quick start |
| `examples/API-EXAMPLES.md` | Real API usage examples |
| `CLOUD-DEPLOYMENT.md` | Detailed cloud options |
| `examples/sample-schema.sql` | Database schema |

---

# 🎉 SUCCESS CRITERIA

Your deployment is successful when:

✅ Health check returns `UP`  
✅ Can generate SQL queries  
✅ Can upload schemas  
✅ Can generate Excel formulas  
✅ API endpoints are accessible  
✅ Response times are reasonable  

---

# 💡 PRO TIPS

1. **Use Railway for Production**
   - Best balance of ease and reliability
   - $5 free credit per month

2. **Use Docker Locally**
   - Consistent with production
   - Easy to test before deploying

3. **Keep API Key Secure**
   - Never commit to GitHub
   - Use environment variables
   - Use cloud platform's secret management

4. **Monitor Your Costs**
   - All platforms have free tiers
   - Set spending alerts
   - Check regularly

5. **Scale When Needed**
   - Start free
   - Upgrade only if needed
   - Most platforms have good free tiers

---

# 🚀 RECOMMENDED PATH FOR YOU

### If You Want the Fastest:
1. Create GitHub account (2 min)
2. Push code to GitHub (3 min)
3. Deploy to Railway (3 min)
4. Add API key (2 min)
5. **DONE!** 🎉

### If You Want to Test Locally First:
1. Install Docker Desktop (5 min)
2. Create `.env` file
3. Run: `docker-compose up -d`
4. Test at `localhost:8080`
5. Then deploy to Railway

### If You Want Production Setup:
1. Use Google Cloud Run
2. Or Render.com
3. Or AWS with Elastic Beanstalk

---

# 📞 SUPPORT RESOURCES

- **Railway Docs**: https://docs.railway.app
- **Docker Docs**: https://docs.docker.com
- **Maven Docs**: https://maven.apache.org/guides/
- **Google Cloud**: https://cloud.google.com/docs
- **GitHub Docs**: https://docs.github.com

---

## 🎯 Ready to Deploy?

**Choose your path above and follow the steps!**

- ⭐ **Easiest**: Railway (recommended)
- 🏠 **Local**: Docker
- 💻 **Advanced**: Google Cloud Run

**Questions?** Check the troubleshooting section or review the specific path documentation.

**Let's go! 🚀**

