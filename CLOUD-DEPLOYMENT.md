# 🚀 One-Click Cloud Deployment Guide

## ⭐ BEST OPTION: Railway (Recommended for Beginners)

Railway is the **easiest and fastest** way to deploy QueryMind with one click!

### Step 1: Prepare Your Code
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
git init
git add .
git commit -m "Initial commit"
```

### Step 2: Push to GitHub
1. Create a GitHub account: https://github.com/signup
2. Create a new repository named `QueryMind`
3. Push your code:
```bash
git remote add origin https://github.com/YOUR_USERNAME/QueryMind.git
git branch -M main
git push -u origin main
```

### Step 3: Deploy to Railway (One Click!)
1. Go to: https://railway.app
2. Click "Start a New Project"
3. Select "Deploy from GitHub"
4. Choose your `QueryMind` repository
5. Railway will automatically detect it's a Java/Maven project
6. Click "Deploy"

### Step 4: Set Environment Variables
In Railway Dashboard:
1. Go to your deployed service
2. Click "Variables"
3. Add: `AI_API_KEY=sk-your-openai-api-key-here`
4. Click "Deploy" again

### Step 5: Access Your App
```
Your app will be live at: https://your-app-name.railway.app
```

**Test it:**
```bash
curl https://your-app-name.railway.app/querymind/api/v1/health
```

---

## 🟢 OPTION 2: Render

Render is another excellent free option with generous free tier.

### One-Click Deploy Button
Click here to deploy directly (if you set up GitHub first):
https://render.com/deploy

### Manual Steps:
1. Go to: https://render.com
2. Click "New +"
3. Select "Web Service"
4. Connect your GitHub repository
5. Configure:
   - **Name**: `querymind`
   - **Runtime**: `Java`
   - **Build Command**: `mvn clean install -DskipTests`
   - **Start Command**: `java -Dserver.port=$PORT -Dspring.profiles.active=prod -Dquerymind.ai-api-key=$AI_API_KEY -jar target/querymind-ai-assistant-1.0.0.jar`
6. Add Environment Variable:
   - `AI_API_KEY=sk-your-openai-api-key-here`
7. Click "Create Web Service"

**Your app will be at**: `https://querymind.onrender.com`

---

## 🟠 OPTION 3: Heroku (Still Has Free Options)

Heroku has limited free tier now, but still works for testing.

### Deploy Steps:
1. Sign up at: https://www.heroku.com/
2. Install Heroku CLI from: https://devcenter.heroku.com/articles/heroku-cli
3. Run these commands:
```bash
heroku login
heroku create querymind
git push heroku main
heroku config:set AI_API_KEY=sk-your-openai-api-key-here
```

**Your app will be at**: `https://querymind.herokuapp.com`

---

## 🔵 OPTION 4: Google Cloud Run (Free Tier!)

Google Cloud Run has excellent free tier (2 million requests/month free).

### Step 1: Set Up Google Cloud Account
1. Go to: https://cloud.google.com/run
2. Click "Try for Free"
3. Create a project: `querymind`

### Step 2: Enable Services
```bash
gcloud services enable run.googleapis.com
gcloud services enable cloudbuild.googleapis.com
```

### Step 3: Deploy
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
gcloud run deploy querymind \
  --source . \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated \
  --set-env-vars AI_API_KEY=sk-your-key-here \
  --memory 512Mi
```

**Your app will be at**: `https://querymind-xxxxx.run.app`

---

## 📊 Comparison Table

| Service | Cost | Setup Time | Ease | Free Tier |
|---------|------|-----------|------|-----------|
| **Railway** ⭐ | Free first $5/mo | 5 min | ⭐⭐⭐⭐⭐ | Generous |
| **Render** | Free | 10 min | ⭐⭐⭐⭐ | Very Good |
| **Heroku** | Limited free | 10 min | ⭐⭐⭐ | Limited |
| **Google Cloud Run** | Free tier | 15 min | ⭐⭐⭐ | Excellent |

**Recommendation**: Start with **Railway** - it's the fastest!

---

## ✅ Quick Start with Railway (5 Minutes)

### Prerequisites
- GitHub account (free)
- OpenAI API key
- This QueryMind repository

### The 5 Steps:
1. **GitHub**: Create repo and push code
   ```bash
   git init
   git add .
   git commit -m "QueryMind"
   git push -u origin main
   ```

2. **Railway**: Connect GitHub
   - Go to https://railway.app
   - Click "Start New Project"
   - Select "Deploy from GitHub"
   - Choose QueryMind repo

3. **Wait**: Railway builds automatically (2-3 minutes)

4. **Set API Key**: In Railway dashboard → Variables
   ```
   AI_API_KEY = sk-your-openai-api-key-here
   ```

5. **Deploy**: Click "Deploy" and get your live URL!

---

## 🧪 Test Your Deployed App

After deployment, test with:

```bash
# Health check
curl https://your-app-name.railway.app/querymind/api/v1/health

# Generate SQL
curl -X POST https://your-app-name.railway.app/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d "{\"query\": \"Show all customers\", \"outputFormat\": \"SQL\"}"
```

---

## 🔐 Security Best Practices

1. **Keep API Key Secret**
   - Never commit your API key to GitHub
   - Use environment variables (Railway does this automatically)

2. **Use Private Repository**
   - Make your GitHub repo private if possible

3. **Monitor Costs**
   - All these services have free tiers
   - Set spending alerts

---

## 📈 Scaling Your Deployment

### If You Need More Power:
- **Railway**: Pay only for what you use (~$5-10/mo for small app)
- **Render**: Starter plan ($7/mo)
- **Google Cloud Run**: Only pay for requests (very cheap)

### For Production:
- Add SSL certificate (automatic on all platforms)
- Set up custom domain
- Add monitoring and logging
- Upgrade to paid tier if needed

---

## 🆘 Troubleshooting

### "Build Failed"
- Check Maven is installed locally
- Verify pom.xml is correct
- Check build logs in platform dashboard

### "Port Already in Use"
- Cloud platforms automatically assign PORT environment variable
- Our Dockerfile handles this automatically

### "API Key Not Working"
- Verify key at https://platform.openai.com/api-keys
- Make sure it's set as environment variable
- Check it's not accidentally committed to GitHub

### "Timeout Error"
- Increase container timeout in platform settings
- First build might take 3-5 minutes

---

## 📞 Support

- **Railway Help**: https://docs.railway.app
- **Render Help**: https://render.com/docs
- **Google Cloud Help**: https://cloud.google.com/docs
- **Heroku Help**: https://devcenter.heroku.com

---

## 🎉 You're Ready!

Your QueryMind app is now:
- ✅ Deployed to the cloud
- ✅ Publicly accessible
- ✅ Running with AI integration
- ✅ Scalable and maintainable
- ✅ Completely free to start

**Next**: Share your public URL and start using the API! 🚀

