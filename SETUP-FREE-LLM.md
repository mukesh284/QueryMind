# 🚀 SETUP QUERYMIND WITH FREE LLM - Quick Guide

## ✅ What I Updated

Your QueryMind application now supports **5 FREE LLM providers**:

1. **Groq** ⭐ (RECOMMENDED - Fastest & Easiest)
2. **Ollama** (LOCAL - 100% Private & Free)
3. **Hugging Face** (Community Models)
4. **Together AI** (Free Tier)
5. **OpenAI** (Optional - if you have key)

**No OpenAI key needed!** Just pick ONE and set an environment variable.

---

## 🎯 FASTEST SETUP: Groq (2 Minutes)

### Step 1: Get Free Groq API Key
1. Go to: https://groq.com
2. Click "Sign In" → "Sign Up"
3. Enter your email and create account
4. Go to API keys section
5. Create new API key
6. Copy the key (looks like: `gsk_Abc123XyZ...`)

### Step 2: Set Environment Variable
```bash
# Windows Command Prompt or PowerShell
set GROQ_API_KEY=gsk_your-actual-key-here
```

### Step 3: Deploy
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Push to GitHub
git add .
git commit -m "Add free LLM support"
git push origin main
```

### Step 4: Deploy to Railway
1. Go to https://railway.app
2. Create new project from GitHub
3. In Variables, add:
   - Key: `GROQ_API_KEY`
   - Value: `gsk_your-key-here`
4. Redeploy

✅ **Your app is now using FREE Groq!** 🎉

---

## 🏠 ALTERNATIVE: Ollama (LOCAL - 100% Free & Private)

### Step 1: Install Ollama
1. Download: https://ollama.ai
2. Install and run
3. Restart your computer

### Step 2: Download a Model
```bash
ollama pull mistral
# Wait 5-10 minutes for download
# Or try: ollama pull llama2
```

### Step 3: Set Environment Variables
```bash
set OLLAMA_URL=http://localhost:11434
set OLLAMA_MODEL=mistral
```

### Step 4: Run QueryMind
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind
docker-compose up -d
```

### Step 5: Access
```
http://localhost:8080/querymind/api/v1/health
```

✅ **Your app runs 100% locally with Ollama!** 🎉

---

## 📊 Comparison: Which Should You Use?

| Provider | Cost | Speed | Setup | Best For |
|----------|------|-------|-------|----------|
| **Groq** ⭐ | FREE | ⚡⚡⚡ | 2 min | Cloud deployment |
| **Ollama** ⭐ | FREE | ⚡⚡ | 10 min | Local/private |
| **Hugging Face** | FREE | ⚡ | 5 min | Testing |
| **Together AI** | FREE | ⚡⚡ | 5 min | Alternative |
| **OpenAI** | Paid | ⚡⚡ | 2 min | If you have key |

**Recommendation: Start with Groq** - it's the fastest to set up and completely free! ⭐

---

## ✨ HOW IT WORKS

Your app automatically detects which LLM to use:

```
Does GROQ_API_KEY exist? → Use Groq
Else Does OLLAMA_URL exist? → Use Ollama (local)
Else Does HUGGINGFACE_API_KEY exist? → Use Hugging Face
Else Does TOGETHER_API_KEY exist? → Use Together AI
Else Does OPENAI_API_KEY exist? → Use OpenAI
Else → Error: "No LLM configured"
```

**Just set ONE environment variable and you're done!**

---

## 🎯 STEP-BY-STEP: Deploy with Groq

### Step 1: Get Groq Key
```
Go to https://groq.com
Sign up → Create API key → Copy key
```

### Step 2: Add to Your Code
```bash
cd C:\Users\mukes\IdeaProjects\QueryMind

# Update and push
git add .
git commit -m "Support free LLMs"
git push origin main
```

### Step 3: Deploy to Railway
```
1. Go to https://railway.app
2. New Project → Deploy from GitHub
3. Select QueryMind repo
4. Wait for build (3-5 min)
5. Add Variable: GROQ_API_KEY=gsk_...
6. Redeploy
7. Your app is LIVE! ✅
```

### Step 4: Test It
```bash
curl https://your-app.railway.app/querymind/api/v1/health
```

✅ **Success! Your QueryMind app is using FREE Groq!**

---

## 📝 Environment Variable Options

Choose ONE and set it:

### For Groq (Recommended)
```
GROQ_API_KEY=gsk_your-key-here
```
Get free key at: https://groq.com

### For Ollama (Local)
```
OLLAMA_URL=http://localhost:11434
OLLAMA_MODEL=mistral
```
Download from: https://ollama.ai

### For Hugging Face
```
HUGGINGFACE_API_KEY=hf_your-token-here
```
Get free key at: https://huggingface.co

### For Together AI
```
TOGETHER_API_KEY=your-key-here
```
Get free key at: https://www.together.ai

### For OpenAI (Optional)
```
OPENAI_API_KEY=sk_your-key-here
```
Get key at: https://platform.openai.com/api-keys (paid)

---

## 🔄 How to Change Providers

If you want to switch providers later:

1. **Clear old variable**: `set GROQ_API_KEY=`
2. **Set new variable**: `set OLLAMA_URL=http://localhost:11434`
3. **Restart app**: `docker-compose restart`

The app auto-detects the new provider!

---

## ✅ VERIFY IT'S WORKING

After deployment, test with:

```bash
# Health check
curl https://your-app.railway.app/querymind/api/v1/health

# Generate SQL
curl -X POST https://your-app.railway.app/querymind/api/v1/queries/sql \
  -H "Content-Type: application/json" \
  -d "{\"query\": \"Show all customers\", \"outputFormat\": \"SQL\"}"
```

Should return generated SQL code! ✅

---

## 💡 PRO TIPS

### For Groq:
- ✅ Completely free (no limits yet!)
- ✅ Super fast responses
- ✅ Works from anywhere (cloud)
- ✅ Perfect for Railway deployment

### For Ollama:
- ✅ 100% private (data stays on your computer)
- ✅ No internet needed
- ✅ Great for testing
- ✅ Perfect for local development

### Switch Between Them:
- Set different env vars to try different providers
- App auto-detects which one to use
- Easy to experiment!

---

## 🆘 TROUBLESHOOTING

### "GROQ_API_KEY not set"
- Get key from https://groq.com
- Set: `set GROQ_API_KEY=gsk_your-key-here`

### "Ollama not running"
- Install from https://ollama.ai
- Run Ollama application
- Pull model: `ollama pull mistral`

### "No LLM configured" Error
- Make sure you set at least ONE environment variable
- Check spelling of variable name
- Restart the app after setting variable

### Slow Responses
- If using Ollama, first response takes longer
- Groq is much faster
- Try Groq if you want speed

---

## 🎉 SUMMARY

### Your QueryMind Now:
✅ Works with **5 FREE LLM providers**  
✅ Supports **Groq** (fastest & free)  
✅ Supports **Ollama** (local & private)  
✅ Supports **Hugging Face** (community)  
✅ Supports **Together AI** (free tier)  
✅ Still supports **OpenAI** (optional)  

### No OpenAI key needed!
Just pick Groq or Ollama and you're ready to go! 🚀

---

## 🚀 NEXT STEPS

### Option 1: Deploy to Cloud with Groq (RECOMMENDED)
1. Get free Groq key (2 min): https://groq.com
2. Deploy to Railway (1 click)
3. Add GROQ_API_KEY variable
4. Your app is LIVE! 🎉

### Option 2: Run Locally with Ollama
1. Install Ollama (5 min): https://ollama.ai
2. Download model (5-10 min): `ollama pull mistral`
3. Set OLLAMA_URL and OLLAMA_MODEL
4. Run locally: `docker-compose up -d`
5. Access at http://localhost:8080 🎉

### Option 3: Test Both
1. Try Groq first (fastest)
2. If you hit rate limits, use Ollama
3. Switch by changing environment variables

---

## 📞 SUPPORT

Free LLM Services:
- **Groq**: https://groq.com
- **Ollama**: https://ollama.ai
- **Hugging Face**: https://huggingface.co
- **Together AI**: https://www.together.ai

All of these are **100% FREE** to use! 🎁

---

## ⭐ YOU'RE ALL SET!

Your QueryMind app now works with FREE LLMs!

**Recommended Path:**
1. Get Groq key (free)
2. Deploy to Railway
3. Add GROQ_API_KEY
4. Your app is LIVE!

**Ready? Let's go!** 🚀

