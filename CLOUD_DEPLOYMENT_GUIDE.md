# ☁️ Hướng Dẫn Deploy EduFinAI Lên Cloud (Miễn Phí/Edu)

## 📋 Tổng Quan Kiến Trúc Cần Deploy

Dự án của bạn bao gồm:
- **7 Spring Boot microservices** (auth, finance, learning, gamification, ai, notification, gateway)
- **1 React frontend**
- **6 MySQL databases**
- **1 Redis**
- **1 Eureka service discovery**

---

## 🎯 Top Recommendations (Ưu Tiên)

### 1. 🚂 Railway.app ⭐⭐⭐⭐⭐ (Khuyến Nghị Nhất)

**Ưu điểm:**
- ✅ **Free tier**: $5 credit/tháng (đủ cho dự án nhỏ)
- ✅ Hỗ trợ Docker Compose natively
- ✅ Tự động deploy từ GitHub
- ✅ Built-in MySQL, PostgreSQL, Redis
- ✅ Dễ sử dụng, UI đẹp
- ✅ Hỗ trợ environment variables
- ✅ Free SSL certificates

**Giới hạn:**
- $5 credit/tháng (khoảng 500 giờ runtime)
- Có thể cần upgrade nếu dùng nhiều resources

**Cách deploy:**
1. Đăng ký tại: https://railway.app
2. Connect GitHub repository
3. Tạo project mới
4. Add service từ `docker-compose.yml`
5. Railway tự động detect và deploy

**Link**: https://railway.app

---

### 2. 🎨 Render.com ⭐⭐⭐⭐

**Ưu điểm:**
- ✅ **Free tier**: 750 giờ/tháng cho mỗi service
- ✅ Hỗ trợ Docker
- ✅ Free PostgreSQL database
- ✅ Free SSL
- ✅ Auto-deploy từ GitHub
- ✅ Có thể chạy nhiều services

**Giới hạn:**
- Free tier services sleep sau 15 phút không dùng
- Cần upgrade để có persistent storage tốt hơn

**Cách deploy:**
1. Đăng ký tại: https://render.com
2. Tạo Web Service từ Docker
3. Tạo PostgreSQL database (free)
4. Setup environment variables
5. Deploy

**Link**: https://render.com

---

### 3. 🚀 Fly.io ⭐⭐⭐⭐

**Ưu điểm:**
- ✅ **Free tier**: 3 shared-cpu VMs
- ✅ Tốt cho containers/Docker
- ✅ Global edge network
- ✅ Free SSL
- ✅ Hỗ trợ volumes

**Giới hạn:**
- Free tier giới hạn resources
- Cần migrate MySQL sang PostgreSQL hoặc dùng managed database

**Cách deploy:**
1. Cài đặt Fly CLI: `curl -L https://fly.io/install.sh | sh`
2. Login: `fly auth login`
3. Deploy: `fly launch`
4. Setup databases riêng

**Link**: https://fly.io

---

### 4. ☁️ Google Cloud Platform (GCP) ⭐⭐⭐⭐⭐

**Ưu điểm:**
- ✅ **$300 free credit** (90 ngày)
- ✅ **Edu credits**: $50-100 cho sinh viên
- ✅ Cloud Run (serverless containers) - free tier tốt
- ✅ Cloud SQL (MySQL managed)
- ✅ Cloud Memorystore (Redis)
- ✅ Always Free tier cho một số services

**Always Free Tier:**
- Cloud Run: 2 triệu requests/tháng
- Cloud SQL: MySQL instance nhỏ
- Compute Engine: 1 f1-micro instance

**Cách deploy:**
1. Đăng ký tại: https://cloud.google.com
2. Activate free trial ($300)
3. Hoặc apply cho edu credits
4. Sử dụng Cloud Run cho services
5. Cloud SQL cho databases

**Link**: https://cloud.google.com/free

---

### 5. 🟠 Oracle Cloud Infrastructure (OCI) ⭐⭐⭐⭐⭐

**Ưu điểm:**
- ✅ **Free tier vĩnh viễn** (không hết hạn!)
- ✅ 2 VMs với 1GB RAM mỗi VM
- ✅ MySQL Database Service (free tier)
- ✅ Object Storage
- ✅ Load Balancer

**Giới hạn:**
- Cần credit card để verify (không charge)
- Có thể cần nhiều VMs cho microservices

**Cách deploy:**
1. Đăng ký tại: https://www.oracle.com/cloud/free
2. Tạo VMs
3. Setup Docker trên VMs
4. Deploy docker-compose
5. Setup MySQL Database Service

**Link**: https://www.oracle.com/cloud/free

---

### 6. 🟦 Microsoft Azure ⭐⭐⭐⭐

**Ưu điểm:**
- ✅ **$200 free credit** (30 ngày)
- ✅ **Azure for Students**: $100 credit (không cần credit card)
- ✅ Azure Container Instances
- ✅ Azure Database for MySQL
- ✅ Azure Cache for Redis

**Azure for Students:**
- $100 credit miễn phí
- Không cần credit card
- Cần email .edu

**Cách deploy:**
1. Đăng ký tại: https://azure.microsoft.com/free/students
2. Tạo Container Instances
3. Setup Azure Database for MySQL
4. Deploy containers

**Link**: https://azure.microsoft.com/free/students

---

### 7. 🟧 AWS (Amazon Web Services) ⭐⭐⭐⭐

**Ưu điểm:**
- ✅ **Free tier 12 tháng**
- ✅ **AWS Educate**: Credits cho sinh viên
- ✅ ECS/Fargate cho containers
- ✅ RDS MySQL (free tier)
- ✅ ElastiCache Redis (free tier)

**Free Tier:**
- EC2: 750 giờ/tháng (t1.micro)
- RDS: 750 giờ/tháng
- S3: 5GB storage

**Cách deploy:**
1. Đăng ký tại: https://aws.amazon.com/free
2. Hoặc AWS Educate: https://aws.amazon.com/education/awseducate
3. Setup ECS/Fargate
4. Deploy containers

**Link**: https://aws.amazon.com/free

---

## 📊 So Sánh Nhanh

| Platform | Free Tier | Edu Credits | Docker Support | MySQL | Dễ Sử Dụng |
|----------|-----------|------------|----------------|-------|------------|
| **Railway** | $5/tháng | ❌ | ✅✅✅ | ✅ | ⭐⭐⭐⭐⭐ |
| **Render** | 750h/tháng | ❌ | ✅✅ | ✅ | ⭐⭐⭐⭐ |
| **Fly.io** | 3 VMs | ❌ | ✅✅✅ | ⚠️ | ⭐⭐⭐ |
| **GCP** | $300/90 ngày | $50-100 | ✅✅✅ | ✅ | ⭐⭐⭐⭐ |
| **Oracle** | Vĩnh viễn | ❌ | ✅✅ | ✅ | ⭐⭐⭐ |
| **Azure** | $200/30 ngày | $100 | ✅✅✅ | ✅ | ⭐⭐⭐⭐ |
| **AWS** | 12 tháng | Có | ✅✅✅ | ✅ | ⭐⭐⭐ |

---

## 🎓 Các Nền Tảng Có Edu Credits

### 1. GitHub Student Pack
**Link**: https://education.github.com/pack

Bao gồm:
- **DigitalOcean**: $200 credit
- **Heroku**: Free dyno
- **Azure**: $100 credit
- **AWS**: $75-200 credit
- Và nhiều services khác

**Cách đăng ký:**
1. Truy cập: https://education.github.com/pack
2. Verify email .edu
3. Upload student ID
4. Nhận credits

---

### 2. Microsoft Azure for Students
**Link**: https://azure.microsoft.com/free/students

- $100 credit miễn phí
- Không cần credit card
- Email .edu

---

### 3. AWS Educate
**Link**: https://aws.amazon.com/education/awseducate

- Credits cho sinh viên
- Free training
- Access đến AWS services

---

### 4. Google Cloud for Education
**Link**: https://edu.google.com/programs/credits/

- Credits cho institutions
- Free training resources

---

## 🚀 Khuyến Nghị Cho Dự Án Của Bạn

### Option 1: Railway.app (Dễ Nhất) ⭐

**Lý do:**
- Hỗ trợ Docker Compose tốt nhất
- Deploy nhanh từ GitHub
- Built-in databases
- UI đơn giản

**Cách làm:**
1. Push code lên GitHub
2. Connect Railway với GitHub
3. Railway tự động detect docker-compose.yml
4. Deploy!

---

### Option 2: GCP Cloud Run + Cloud SQL (Linh Hoạt Nhất)

**Lý do:**
- Free tier tốt
- Có edu credits
- Scalable
- Professional

**Cách làm:**
1. Build Docker images
2. Push lên Google Container Registry
3. Deploy lên Cloud Run
4. Setup Cloud SQL cho MySQL
5. Setup Cloud Memorystore cho Redis

---

### Option 3: Oracle Cloud (Free Vĩnh Viễn)

**Lý do:**
- Free tier không hết hạn
- Đủ resources cho dự án nhỏ
- MySQL managed service

**Cách làm:**
1. Tạo 2 VMs
2. Setup Docker trên VMs
3. Deploy docker-compose
4. Setup MySQL Database Service

---

## 📝 Checklist Trước Khi Deploy

- [ ] Code đã push lên GitHub/GitLab
- [ ] Environment variables đã được tách ra (không hardcode)
- [ ] Database credentials đã được config qua env vars
- [ ] CORS settings đã được cấu hình đúng
- [ ] API Gateway URLs đã được update
- [ ] Frontend API URLs đã được config
- [ ] SSL certificates (nếu cần)
- [ ] Health checks đã được setup
- [ ] Logging đã được config

---

## 🔧 Chuẩn Bị Code Trước Khi Deploy

### 1. Tách Environment Variables

Tạo file `.env.example` cho mỗi service:

```bash
# .env.example
SPRING_PROFILES_ACTIVE=production
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka:8761/eureka/
SPRING_DATASOURCE_URL=jdbc:mysql://mysql-finance:3306/finance
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
GEMINI_API_KEY=${GEMINI_API_KEY}
```

### 2. Update Frontend API URLs

Trong `edufinai-frontend/src/services/`:

```javascript
// Thay vì localhost
const GATEWAY_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
```

### 3. Update CORS Settings

Trong Gateway và các services:

```yaml
app:
  cors:
    allowed-origins: ${FRONTEND_URL:http://localhost:3000}
```

---

## 🎯 Hướng Dẫn Deploy Chi Tiết

### Railway.app - Step by Step

1. **Đăng ký tài khoản**
   - Truy cập: https://railway.app
   - Sign up với GitHub

2. **Tạo Project**
   - Click "New Project"
   - Chọn "Deploy from GitHub repo"
   - Chọn repository của bạn

3. **Setup Services**
   - Railway sẽ detect docker-compose.yml
   - Hoặc add từng service thủ công

4. **Setup Databases**
   - Add MySQL service cho mỗi database
   - Railway tự động tạo connection strings

5. **Environment Variables**
   - Add các biến môi trường cần thiết
   - Database URLs, API keys, etc.

6. **Deploy**
   - Railway tự động deploy khi push code
   - Hoặc manual deploy từ dashboard

---

### GCP Cloud Run - Step by Step

1. **Setup GCP Project**
   ```bash
   gcloud init
   gcloud config set project YOUR_PROJECT_ID
   ```

2. **Enable APIs**
   ```bash
   gcloud services enable run.googleapis.com
   gcloud services enable sqladmin.googleapis.com
   ```

3. **Build và Push Images**
   ```bash
   # Build images
   docker build -t gcr.io/PROJECT_ID/auth-service ./auth-service
   
   # Push to Container Registry
   docker push gcr.io/PROJECT_ID/auth-service
   ```

4. **Deploy to Cloud Run**
   ```bash
   gcloud run deploy auth-service \
     --image gcr.io/PROJECT_ID/auth-service \
     --platform managed \
     --region us-central1 \
     --allow-unauthenticated
   ```

5. **Setup Cloud SQL**
   ```bash
   gcloud sql instances create mysql-instance \
     --database-version=MYSQL_8_0 \
     --tier=db-f1-micro \
     --region=us-central1
   ```

---

## 💰 Ước Tính Chi Phí (Free Tier)

### Railway.app
- Free: $5 credit/tháng
- Đủ cho: 5-7 services nhỏ
- Database: Included

### GCP
- Free: $300 credit (90 ngày)
- Cloud Run: Free tier tốt
- Cloud SQL: Free tier 1 instance

### Oracle Cloud
- Free: Vĩnh viễn
- 2 VMs: 1GB RAM mỗi VM
- MySQL: Free tier

---

## ⚠️ Lưu Ý Quan Trọng

1. **Database Migration**
   - Một số platforms chỉ có PostgreSQL free
   - Có thể cần migrate MySQL → PostgreSQL
   - Hoặc dùng managed MySQL (có thể tốn phí)

2. **Service Discovery**
   - Eureka có thể không cần thiết trên cloud
   - Có thể dùng DNS hoặc load balancer

3. **Scaling**
   - Free tier thường giới hạn resources
   - Có thể cần optimize code

4. **Monitoring**
   - Setup logging và monitoring
   - Sử dụng free tiers của monitoring services

---

## 🔗 Links Hữu Ích

- **Railway**: https://railway.app
- **Render**: https://render.com
- **Fly.io**: https://fly.io
- **GCP Free Tier**: https://cloud.google.com/free
- **Oracle Free Tier**: https://www.oracle.com/cloud/free
- **Azure for Students**: https://azure.microsoft.com/free/students
- **AWS Free Tier**: https://aws.amazon.com/free
- **GitHub Student Pack**: https://education.github.com/pack

---

## 📞 Hỗ Trợ

Nếu cần hỗ trợ deploy, hãy:
1. Chọn platform bạn muốn dùng
2. Đọc documentation của platform đó
3. Hoặc hỏi tôi để được hướng dẫn chi tiết hơn!

---

**Chúc bạn deploy thành công! 🚀**
