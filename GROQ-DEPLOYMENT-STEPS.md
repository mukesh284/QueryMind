# 🚀 GROQ DEPLOYMENT - Complete Step-by-Step

## ✅ STEP 1: Get Your FREE Groq API Key (2 minutes)

1. **Go to Groq**: https://groq.com
2. **Click "Sign In"** (top right)
3. **Click "Sign Up"** 
4. **Enter your email** and create a password
5. **Verify your email** (check inbox)
6. **Go to API Console**: https://console.groq.com/
7. **Create API Key**:
   - Click "API Keys" in left menu
   - Click "Create New API Key"
   - Copy the key (looks like: `gsk_Abc123XyZ...`)
   - **SAVE THIS KEY!** You'll need it

---

## ✅ STEP 2: Set Environment Variable

Open Command Prompt and run:

```bash
set GROQ_API_KEY=gsk_your-actual-key-from-step-1-here
```

**Example** (if your key is `gsk_ABC123xyz`):
```bash
set GROQ_API_KEY=gsk_ABC123xyz
```

---

## ✅ STEP 3: Push Code to GitHub

```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Check what's changed
git status

# Add all changes
git add .

# Commit
git commit -m "Add Groq LLM support - completely free!"

# Push to GitHub
git push origin main
```

---

## ✅ STEP 4: Deploy to Railway (ONE CLICK!)

1. **Go to Railway**: https://railway.app
2. **Sign In** (or create account with GitHub)
3. **Click "Start a New Project"**
4. **Click "Deploy from GitHub"**
5. **Search for "QueryMind"** and select your repository
6. **Railway auto-detects and starts building** (2-3 minutes)

---

## ✅ STEP 5: Add Groq API Key to Railway

1. **In Railway Dashboard**, find your `querymind` service
2. **Click on it** to open service details
3. **Click "Variables"** tab
4. **Click "New Variable"**
5. **Enter:**
   - Key: `GROQ_API_KEY`
   - Value: `gsk_your-actual-key-here` (paste your Groq key)
6. **Click "Add"**
7. **Click "Redeploy"** button

Railway will rebuild with your API key. Wait 2-3 minutes...

---

## ✅ STEP 6: Get Your Live URL

Once deployed, Railway shows your live URL:
```
https://querymind-xxxx.railway.app
```

---

## ✅ STEP 7: Test It Works!

In a Command Prompt, run:

```bash
curl https://querymind-xxxx.railway.app/querymind/api/v1/health
```

**Expected Response:**
```json
{
  "status": "UP",
  "application": "QueryMind AI Assistant",
  "version": "1.0.0"
}
```

✅ **If you see this, your app is LIVE!** 🎉

---

## 🧪 Test Generating SQL with Groq

```bash
curl -X POST https://querymind-xxxx.railway.app/querymind/api/v1/queries/sql ^
  -H "Content-Type: application/json" ^
  -d "{\"query\": \"Show top 10 customers\", \"outputFormat\": \"SQL\"}"
```

You should get back a generated SQL query from Groq!

---

## 📊 Your App Features (NOW USING GROQ!)

✅ SQL generation from English  
✅ Excel formula generation  
✅ XLOOKUP creation  
✅ Power BI DAX generation  
✅ Data analysis  
✅ Batch processing  
✅ **COMPLETELY FREE** with Groq!  

---

## 🎯 What's Next?

Your QueryMind app is now:
- 🌐 **Live on the internet** at your Railway URL
- ⚡ **Using Groq** (fastest free LLM)
- 💾 **Auto-scaling** for traffic
- 🔒 **With HTTPS** security
- 📊 **Production-ready**

---

## 💡 IMPORTANT NOTES

### Groq API Key Safety:
- ✅ Your key is safe in Railway's secrets
- ✅ Never commit to GitHub
- ✅ Only set as environment variable
- ✅ Railway encrypts it

### Groq Speed:
- ⚡ **Super fast** responses (milliseconds)
- 💰 **Completely FREE** (no limits yet!)
- 🎯 **Perfect for this app**

### If You Hit Rate Limits (rare):
- Switch to Ollama (local)
- Set `OLLAMA_URL=http://localhost:11434`
- Or try Together AI
- Or Hugging Face

---

## ✨ SUCCESS CHECKLIST

- [ ] Got Groq API key from https://groq.com
- [ ] Set `GROQ_API_KEY` environment variable
- [ ] Pushed code to GitHub
- [ ] Deployed to Railway
- [ ] Added `GROQ_API_KEY` variable in Railway
- [ ] Redeployed in Railway
- [ ] Got live URL (https://querymind-xxxx.railway.app)
- [ ] Tested health endpoint ✅
- [ ] Got "status": "UP" response ✅
- [ ] Tested SQL generation ✅

---

## 📞 SUPPORT

- **Groq Console**: https://console.groq.com/
- **Railway Docs**: https://docs.railway.app
- **Groq API Models**: https://console.groq.com/docs/models

---

## 🎉 YOU'RE DONE!

Your QueryMind app is now:
- Running in the **cloud** ☁️
- Using **FREE Groq** 🆓
- **Production-ready** ✅
- **Scalable** 📈
- **Fast** ⚡

**Congratulations!** 🚀

Your AI-Powered SQL & Excel Assistant is LIVE!

