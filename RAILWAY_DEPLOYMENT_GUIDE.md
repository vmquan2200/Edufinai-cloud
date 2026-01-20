# 🚂 Hướng Dẫn Deploy EduFinAI Lên Railway

## 📋 Tổng Quan

Railway là một platform cloud hiện đại, dễ sử dụng với:
- ✅ **Free tier**: $5 credit/tháng (đủ cho dự án nhỏ)
- ✅ Hỗ trợ Docker natively
- ✅ Built-in MySQL, PostgreSQL, Redis
- ✅ Tự động deploy từ GitHub
- ✅ Free SSL certificates
- ✅ Environment variables management

---

## ⚠️ Lưu Ý Quan Trọng

### Kiến Trúc Microservices trên Railway

Railway hoạt động tốt nhất với **từng service riêng lẻ**. Với kiến trúc microservices của bạn:

**Có 2 cách deploy:**

1. **Cách 1: Deploy từng service riêng** (Khuyến nghị) ⭐
   - Mỗi service là một Railway service riêng
   - Dễ quản lý và scale
   - Tốt cho production

2. **Cách 2: Deploy Docker Compose** (Hạn chế)
   - Railway hỗ trợ Docker Compose nhưng có giới hạn
   - Phù hợp cho development/testing
   - Có thể gặp vấn đề với nhiều services

**Khuyến nghị:** Dùng **Cách 1** cho production.

---

## ✅ Checklist Trước Khi Deploy

- [x] Code đã push lên GitHub
- [x] Dockerfiles đã sẵn sàng
- [ ] Environment variables đã được tách ra
- [ ] Frontend API URLs đã được config qua env vars
- [ ] Database connection strings đã được config
- [ ] CORS settings đã được update

---

## 🚀 Bước 1: Chuẩn Bị Code

### 1.1. Update Frontend để dùng Environment Variables

Frontend đã được update để sử dụng `REACT_APP_API_URL`. 

**File đã được cập nhật:**
- `edufinai-frontend/src/services/authApi.js`
- `edufinai-frontend/src/services/financeApi.js`
- `edufinai-frontend/src/services/aiService.js`
- `edufinai-frontend/src/services/gamificationApi.js`
- `edufinai-frontend/src/services/learningService.js`
- `edufinai-frontend/src/services/notificationApi.js`

### 1.2. Application Configs cho Railway

Các file `application-railway.properties` và `application-railway.yml` đã được tạo cho tất cả services.

---

## 🚀 Bước 2: Đăng Ký và Setup Railway

### 2.1. Đăng Ký Tài Khoản

1. Truy cập: https://railway.app
2. Click **"Start a New Project"**
3. Chọn **"Deploy from GitHub repo"**
4. Authorize Railway với GitHub
5. Chọn repository của bạn: `Edufinai-cloud`

### 2.2. Tạo Project

Railway sẽ tự động tạo một project mới từ repository của bạn.

---

## 🚀 Bước 3: Setup Databases

Railway có built-in MySQL và Redis. Bạn cần tạo **6 MySQL databases** và **1 Redis**.

### 3.1. Tạo MySQL Databases

Với mỗi database, làm theo các bước sau:

1. Trong Railway project, click **"+ New"**
2. Chọn **"Database"** → **"Add MySQL"**
3. Đặt tên database (ví dụ: `mysql-auth`)
4. Railway tự động tạo:
   - Database name
   - Username
   - Password
   - Host
   - Port

**Lặp lại cho 6 databases:**
- `mysql-auth` (cho auth-service)
- `mysql-finance` (cho finance-service)
- `mysql-learning` (cho learning-service)
- `mysql-gamification` (cho gamification-service)
- `mysql-ai` (cho ai-service)
- `mysql-firebase` (cho firebase-notification)

### 3.2. Tạo Redis

1. Click **"+ New"**
2. Chọn **"Database"** → **"Add Redis"**
3. Đặt tên: `redis`

### 3.3. Lưu Connection Strings

Railway tự động tạo các environment variables cho mỗi database:
- `MYSQL_URL` (hoặc `DATABASE_URL`)
- `MYSQL_HOST`
- `MYSQL_PORT`
- `MYSQL_DATABASE`
- `MYSQL_USER`
- `MYSQL_PASSWORD`

**Lưu ý:** Railway sử dụng format connection string đặc biệt. Bạn sẽ cần parse nó trong application config.

---

## 🚀 Bước 4: Deploy Services

### ⚠️ QUAN TRỌNG: Set Root Directory

**Railway sẽ không tự động detect Dockerfile ở root!** Bạn **PHẢI** set Root Directory cho mỗi service.

### 4.1. Deploy Eureka Server (Phải deploy đầu tiên)

1. Click **"+ New"** → **"GitHub Repo"**
2. Chọn repository của bạn: `Edufinai-cloud`
3. Railway sẽ báo lỗi "could not determine how to build" - **ĐÂY LÀ BÌNH THƯỜNG!**
4. Vào **Settings** của service vừa tạo
5. Tìm mục **"Root Directory"** hoặc **"Source"**
6. Set **Root Directory:** `edufinai/eureka`
7. Railway sẽ tự động detect Dockerfile trong thư mục đó
8. **Service Name:** Đổi tên thành `eureka` (nếu muốn)
9. Click **"Deploy"** hoặc Railway sẽ tự động deploy

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
PORT=8761
```

**Public Port:** Bật public port và set port = `8761`

### 4.2. Deploy Auth Service

1. Click **"+ New"** → **"GitHub Repo"**
2. Chọn repository: `Edufinai-cloud`
3. Railway sẽ báo lỗi - **KHÔNG SAO**, tiếp tục bước sau
4. Vào **Settings** → **"Root Directory"**
5. Set **Root Directory:** `edufinai/auth-service`
6. Railway sẽ detect Dockerfile và tự động build
7. **Service Name:** Đổi thành `auth-service`
8. Railway sẽ tự động deploy

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
EUREKA_INSTANCE_HOSTNAME=auth-service
EUREKA_INSTANCE_IP=auth-service
```

**Database Connection:**
- Click vào `mysql-auth` database
- Copy connection string
- Add vào environment variables của `auth-service`:
  ```
  MYSQL_URL=<connection_string_from_railway>
  ```

**Public Port:** Không cần (chỉ expose qua Gateway)

### 4.3. Deploy Finance Service

Tương tự như auth-service:

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. Vào **Settings** → **"Root Directory"**
3. Set **Root Directory:** `edufinai/finance-service`
4. **Service Name:** `finance-service`

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
MYSQL_URL=<mysql-finance_connection_string>
```

### 4.4. Deploy Learning Service

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/learning-service`
3. **Service Name:** `learning-service`

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
MYSQL_URL=<mysql-learning_connection_string>
```

### 4.5. Deploy Gamification Service

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/gamification-service`
3. **Service Name:** `gamification-service`

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
MYSQL_URL=<mysql-gamification_connection_string>
REDIS_URL=<redis_connection_string>
```

### 4.6. Deploy AI Service

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/ai-service`
3. **Service Name:** `ai-service`

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
MYSQL_URL=<mysql-ai_connection_string>
GEMINI_API_KEY=<your_gemini_api_key>
```

### 4.7. Deploy Firebase Notification Service

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/firebase-notification`
3. **Service Name:** `firebase-notification`

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
MYSQL_URL=<mysql-firebase_connection_string>
```

### 4.8. Deploy Gateway (Quan trọng nhất)

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai/gateway`
3. **Service Name:** `gateway`

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
PORT=8080
```

**Public Port:** BẮT BUỘC phải bật và set port = `8080`

**Custom Domain (Tùy chọn):**
- Railway tự động tạo domain: `gateway-production.up.railway.app`
- Bạn có thể thêm custom domain trong Settings

### 4.9. Deploy Frontend

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. **Settings** → **"Root Directory"** → `edufinai-frontend`
3. **Service Name:** `frontend`
4. Railway sẽ tự động detect Dockerfile và build React app

**Environment Variables:**
```
REACT_APP_API_URL=https://gateway-production.up.railway.app
NODE_ENV=production
```

**Public Port:** Bật và set port = `80` (hoặc để Railway tự động)

**Build Command:** Railway tự động detect `npm run build`

---

## 🔧 Bước 5: Cấu Hình Environment Variables

### 5.1. Parse MySQL Connection String

Railway cung cấp MySQL connection string dạng:
```
mysql://user:password@host:port/database
```

Bạn cần parse nó trong `application-railway.properties`:

```properties
# Railway tự động inject MYSQL_URL
# Format: mysql://user:password@host:port/database
spring.datasource.url=${MYSQL_URL}
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}
```

Hoặc Railway có thể cung cấp các biến riêng lẻ:
- `MYSQL_HOST`
- `MYSQL_PORT`
- `MYSQL_DATABASE`
- `MYSQL_USER`
- `MYSQL_PASSWORD`

### 5.2. Eureka URLs

Tất cả services cần kết nối đến Eureka qua HTTPS:

```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
```

**Lưu ý:** Thay `eureka-production.up.railway.app` bằng domain thực tế của Eureka service.

### 5.3. Service Discovery

Trên Railway, các services có thể giao tiếp qua:
- **Private networking:** Sử dụng service name (ví dụ: `auth-service`)
- **Public URLs:** Sử dụng Railway domain

**Khuyến nghị:** Dùng private networking cho internal communication.

---

## 🔍 Bước 6: Kiểm Tra và Test

### 6.1. Kiểm Tra Eureka Dashboard

1. Mở Eureka public URL: `https://eureka-production.up.railway.app`
2. Kiểm tra các services đã đăng ký:
   - AUTH-SERVICE
   - FINANCE-SERVICE
   - LEARNING-SERVICE
   - GAMIFICATION-SERVICE
   - AI-SERVICE
   - NOTIFICATION-SERVICE
   - GATEWAY

### 6.2. Test Gateway

```bash
curl https://gateway-production.up.railway.app/actuator/health
```

### 6.3. Test Frontend

Mở frontend URL và kiểm tra:
- Frontend load được không
- API calls có hoạt động không
- CORS có được config đúng không

---

## 🐛 Troubleshooting

### Vấn đề 1: Services không đăng ký vào Eureka

**Nguyên nhân:** Eureka URL không đúng hoặc network issue.

**Giải pháp:**
1. Kiểm tra Eureka URL trong environment variables
2. Đảm bảo Eureka đã deploy và running
3. Kiểm tra logs của service: `railway logs <service-name>`

### Vấn đề 2: Database Connection Failed

**Nguyên nhân:** Connection string không đúng hoặc database chưa sẵn sàng.

**Giải pháp:**
1. Kiểm tra `MYSQL_URL` trong environment variables
2. Đảm bảo database đã được tạo và running
3. Kiểm tra network connectivity giữa service và database

### Vấn đề 3: CORS Error

**Nguyên nhân:** Frontend URL chưa được add vào CORS allowed origins.

**Giải pháp:**
1. Update CORS config trong Gateway:
   ```
   app.cors.allowed-origins=https://frontend-production.up.railway.app
   ```
2. Restart Gateway service

### Vấn đề 4: Out of Memory

**Nguyên nhân:** Service sử dụng quá nhiều memory.

**Giải pháp:**
1. Tăng memory limit trong Railway service settings
2. Hoặc optimize application code

### Vấn đề 5: Build Failed

**Nguyên nhân:** Dockerfile có vấn đề hoặc dependencies không tải được.

**Giải pháp:**
1. Kiểm tra logs: `railway logs <service-name>`
2. Test build local: `docker build -t test ./edufinai/auth-service`
3. Kiểm tra internet connection trong build process

---

## 💰 Chi Phí và Giới Hạn

### Free Tier ($5 credit/tháng)

- **Runtime:** ~500 giờ/tháng
- **Storage:** 5GB
- **Bandwidth:** 100GB/tháng

### Ước Tính Chi Phí

Với dự án của bạn (9 services + 6 databases + 1 Redis):

- **Services:** ~$0.01/giờ mỗi service × 9 = $0.09/giờ
- **Databases:** ~$0.02/giờ mỗi database × 6 = $0.12/giờ
- **Redis:** ~$0.01/giờ

**Tổng:** ~$0.22/giờ = ~$158/tháng nếu chạy 24/7

**Nhưng:** Free tier $5 có thể đủ cho:
- Development/testing
- Demo projects
- Low traffic applications

### Khuyến Nghị

1. **Development:** Dùng free tier
2. **Production:** Cân nhắc upgrade hoặc optimize resources
3. **Tối ưu:** 
   - Tắt services không cần thiết khi không dùng
   - Sử dụng Railway's sleep feature
   - Optimize Docker images

---

## 📚 Tài Liệu Tham Khảo

- **Railway Docs:** https://docs.railway.app
- **Railway Pricing:** https://railway.app/pricing
- **Railway Discord:** https://discord.gg/railway

---

## ✅ Checklist Sau Khi Deploy

- [ ] Tất cả services đã deploy thành công
- [ ] Eureka dashboard hiển thị tất cả services
- [ ] Gateway có thể truy cập được
- [ ] Frontend có thể kết nối đến Gateway
- [ ] Database connections hoạt động
- [ ] CORS được config đúng
- [ ] SSL certificates đã được cấp
- [ ] Custom domain đã được setup (nếu có)

---

**Chúc bạn deploy thành công! 🚀**

Nếu gặp vấn đề, hãy kiểm tra logs và tham khảo phần Troubleshooting ở trên.
