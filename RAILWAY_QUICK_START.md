# 🚂 Railway Quick Start Guide - EduFinAI

## ⚡ Quick Start (5 phút)

### Bước 1: Đăng ký Railway
1. Truy cập: https://railway.app
2. Sign up với GitHub
3. Authorize Railway

### Bước 2: Tạo Project
1. Click **"New Project"**
2. Chọn **"Deploy from GitHub repo"**
3. Chọn repository: `Edufinai-cloud`

### Bước 3: Deploy Eureka (Phải deploy đầu tiên)
1. Click **"+ New"** → **"GitHub Repo"**
2. **Root Directory:** `edufinai/eureka`
3. **Service Name:** `eureka`
4. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   PORT=8761
   ```
5. **Settings** → **Networking** → Bật **Public Port** → Set port = `8761`
6. Click **"Deploy"**

### Bước 4: Tạo Databases
Với mỗi database:
1. Click **"+ New"** → **"Database"** → **"Add MySQL"**
2. Đặt tên và tạo:
   - `mysql-auth`
   - `mysql-finance`
   - `mysql-learning`
   - `mysql-gamification`
   - `mysql-ai`
   - `mysql-firebase`
3. Tạo Redis: **"+ New"** → **"Database"** → **"Add Redis"** → `redis`

### Bước 5: Deploy Services

#### Auth Service
1. **"+ New"** → **"GitHub Repo"**
2. **Root Directory:** `edufinai/auth-service`
3. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   MYSQL_URL=<copy_from_mysql-auth>
   ```

#### Finance Service
1. **Root Directory:** `edufinai/finance-service`
2. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   MYSQL_URL=<copy_from_mysql-finance>
   ```

#### Learning Service
1. **Root Directory:** `edufinai/learning-service`
2. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   MYSQL_URL=<copy_from_mysql-learning>
   ```

#### Gamification Service
1. **Root Directory:** `edufinai/gamification-service`
2. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   MYSQL_URL=<copy_from_mysql-gamification>
   REDIS_URL=<copy_from_redis>
   ```

#### AI Service
1. **Root Directory:** `edufinai/ai-service`
2. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   MYSQL_URL=<copy_from_mysql-ai>
   GEMINI_API_KEY=<your_key>
   ```

#### Firebase Notification Service
1. **Root Directory:** `edufinai/firebase-notification`
2. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   MYSQL_URL=<copy_from_mysql-firebase>
   ```

#### Gateway (QUAN TRỌNG)
1. **Root Directory:** `edufinai/gateway`
2. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   PORT=8080
   CORS_ALLOWED_ORIGINS=https://<frontend-url>
   ```
3. **Settings** → **Networking** → Bật **Public Port** → Set port = `8080`

#### Frontend
1. **Root Directory:** `edufinai-frontend`
2. **Environment Variables:**
   ```
   REACT_APP_API_URL=https://<gateway-url>
   NODE_ENV=production
   ```
3. **Settings** → **Networking** → Bật **Public Port**

### Bước 6: Kiểm Tra
1. Mở Eureka: `https://<eureka-url>`
2. Kiểm tra services đã đăng ký
3. Mở Frontend: `https://<frontend-url>`
4. Test API calls

---

## 📝 Lưu Ý Quan Trọng

1. **Thứ tự deploy:** Eureka → Databases → Services → Gateway → Frontend
2. **Eureka URL:** Copy từ Eureka service's public URL
3. **Database URLs:** Copy từ mỗi database service's connection string
4. **CORS:** Update `CORS_ALLOWED_ORIGINS` trong Gateway với frontend URL

---

## 🔗 Xem Hướng Dẫn Chi Tiết

Xem file `RAILWAY_DEPLOYMENT_GUIDE.md` để biết hướng dẫn đầy đủ và troubleshooting.
