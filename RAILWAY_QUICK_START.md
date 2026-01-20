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

**⚠️ QUAN TRỌNG:** Railway sẽ báo lỗi "could not determine how to build" - ĐÂY LÀ BÌNH THƯỜNG!

1. Click **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. Railway tạo service mới và báo lỗi - **KHÔNG SAO!**
3. Click vào service → **Settings** → Tìm **"Root Directory"**
4. Set **Root Directory:** `edufinai/eureka`
5. Railway sẽ tự động detect Dockerfile và build
6. **Service Name:** Đổi thành `eureka` (nếu muốn)
7. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   PORT=8761
   ```
8. **Settings** → **Networking** → Bật **Public Port** → Set port = `8761`
9. Railway sẽ tự động deploy

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
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/auth-service`
3. **Environment Variables:**
   ```
   SPRING_PROFILES_ACTIVE=railway
   EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://<eureka-url>/eureka/
   MYSQL_URL=<copy_from_mysql-auth>
   ```

#### Finance Service
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/finance-service`
3. **Environment Variables:** (giống như trên)

#### Learning Service
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/learning-service`
3. **Environment Variables:** (giống như trên)

#### Gamification Service
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/gamification-service`
3. **Environment Variables:** (giống như trên)

#### AI Service
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/ai-service`
3. **Environment Variables:** (giống như trên)

#### Firebase Notification Service
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/firebase-notification`
3. **Environment Variables:** (giống như trên)

#### Gateway (QUAN TRỌNG)
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/gateway`
3. **Environment Variables:** (giống như trên)
4. **Settings** → **Networking** → Bật **Public Port** → Set port = `8080`

#### Frontend
1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai-frontend`
3. **Environment Variables:** (giống như trên)
4. **Settings** → **Networking** → Bật **Public Port**

### Bước 6: Kiểm Tra
1. Mở Eureka: `https://<eureka-url>`
2. Kiểm tra services đã đăng ký
3. Mở Frontend: `https://<frontend-url>`
4. Test API calls

---

## 📝 Lưu Ý Quan Trọng

1. **Root Directory:** **PHẢI** set Root Directory cho mỗi service trong Settings!
2. **Thứ tự deploy:** Eureka → Databases → Services → Gateway → Frontend
3. **Eureka URL:** Copy từ Eureka service's public URL
4. **Database URLs:** Copy từ mỗi database service's connection string
5. **CORS:** Update `CORS_ALLOWED_ORIGINS` trong Gateway với frontend URL

## ⚠️ Nếu Gặp Lỗi "could not determine how to build"

Xem file `RAILWAY_FIX_ROOT_DIRECTORY.md` để biết cách fix chi tiết!

---

## 🔗 Xem Hướng Dẫn Chi Tiết

Xem file `RAILWAY_DEPLOYMENT_GUIDE.md` để biết hướng dẫn đầy đủ và troubleshooting.
