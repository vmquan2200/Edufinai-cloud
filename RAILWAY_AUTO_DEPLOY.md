# 🚀 Railway Auto-Deploy Guide - Tự động Detect và Deploy

Hướng dẫn deploy tự động trên Railway - Railway sẽ tự động detect Dockerfile và deploy!

---

## ✅ Railway Auto-Detection

Railway tự động detect và deploy khi:
1. ✅ **Có Dockerfile** trong root directory của service
2. ✅ **Có pom.xml** (cho Maven projects)
3. ✅ **Có package.json** (cho Node.js projects)

**Không cần cấu hình thêm!** Railway sẽ tự động:
- Detect build system (Maven, Gradle, npm, etc.)
- Build application
- Deploy container
- Inject PORT và các biến môi trường

---

## 📋 Cấu trúc Dockerfile đã được tối ưu

Tất cả Dockerfile đã được tối ưu cho Railway:

### ✅ Features:
- ✅ **Multi-stage build** - Tối ưu image size
- ✅ **Dependency caching** - Build nhanh hơn
- ✅ **PORT từ env** - Railway tự động inject PORT
- ✅ **Spring profile** - Tự động set `SPRING_PROFILES_ACTIVE=railway`
- ✅ **Health check ready** - Sẵn sàng cho health checks

### 📁 Dockerfile Locations:

```
edufinai/
├── eureka/Dockerfile ✅
├── gateway/Dockerfile ✅
├── auth-service/Dockerfile ✅
├── finance-service/Dockerfile ✅
├── learning-service/Dockerfile ✅
├── gamification-service/Dockerfile ✅
├── ai-service/Dockerfile ✅
└── firebase-notification/Dockerfile ✅
```

---

## 🎯 Cách Deploy Tự Động trên Railway

### Bước 1: Tạo Project trên Railway

1. Vào https://railway.app → **New Project**
2. Đặt tên project (ví dụ: `edufinai-cloud`)

### Bước 2: Thêm MySQL Database

1. **+ New** → **Database** → **MySQL**
2. Railway tự động tạo MySQL instance
3. Tạo các databases cần thiết (xem bước 3)

### Bước 3: Tạo Databases

Vào **MySQL** service → **Connect** → MySQL client:

```sql
CREATE DATABASE IF NOT EXISTS identity;
CREATE DATABASE IF NOT EXISTS finance;
CREATE DATABASE IF NOT EXISTS learning;
CREATE DATABASE IF NOT EXISTS gamification;
CREATE DATABASE IF NOT EXISTS ai_service;
CREATE DATABASE IF NOT EXISTS firebase;
```

**Hoặc** để Spring Boot tự tạo (nếu `ddl-auto=update`).

### Bước 4: Deploy Services (Railway tự động detect!)

Với mỗi service, chỉ cần:

1. **+ New** → **GitHub Repo**
2. Chọn repo của bạn
3. **Root Directory:** `edufinai/[service-name]`
4. Railway **tự động detect Dockerfile** và build!

**Không cần cấu hình build command hay start command!**

---

## 📦 Deploy từng Service

### 1. Eureka Server

```
Root Directory: edufinai/eureka
```

Railway tự động:
- ✅ Detect Dockerfile
- ✅ Build với Maven
- ✅ Deploy container
- ✅ Inject PORT (Railway tự động set)

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=railway
```

---

### 2. Auth Service

```
Root Directory: edufinai/auth-service
```

Railway tự động detect và build!

**Environment Variables:**
```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=identity
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=auth-service
EUREKA_INSTANCE_IP=auth-service
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
```

---

### 3. Finance Service

```
Root Directory: edufinai/finance-service
```

**Environment Variables:**
```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=finance
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=finance-service
EUREKA_INSTANCE_IP=finance-service
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
CORS_ALLOWED_ORIGINS=*
```

---

### 4. Learning Service

```
Root Directory: edufinai/learning-service
```

**Environment Variables:**
```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=learning
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=learning-service
EUREKA_INSTANCE_IP=learning-service
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
```

---

### 5. Gamification Service

```
Root Directory: edufinai/gamification-service
```

**Environment Variables:**
```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=gamification
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=gamification-service
EUREKA_INSTANCE_IP=gamification-service
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
SPRING_PROFILES_ACTIVE=railway
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=
GAMIFICATION_SERVICE_URL=http://gamification-service
```

---

### 6. AI Service

```
Root Directory: edufinai/ai-service
```

**Environment Variables:**
```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=ai_service
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=ai-service
EUREKA_INSTANCE_IP=ai-service
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
SPRING_PROFILES_ACTIVE=railway
GEMINI_API_KEY=your-gemini-api-key-here
CORS_ALLOWED_ORIGINS=*
FINANCE_SERVICE_URL=http://finance-service
AUTH_SERVICE_URL=http://auth-service
GAMIFICATION_SERVICE_URL=http://gamification-service
LEARNING_SERVICE_URL=http://learning-service
```

**⚠️ QUAN TRỌNG:** Thay `your-gemini-api-key-here` bằng Gemini API key thực tế!

---

### 7. Firebase Notification Service

```
Root Directory: edufinai/firebase-notification
```

**Environment Variables:**
```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=firebase
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=firebase-notification
EUREKA_INSTANCE_IP=firebase-notification
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
```

---

### 8. Gateway (Deploy cuối cùng)

```
Root Directory: edufinai/gateway
```

**Environment Variables:**
```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=gateway
EUREKA_INSTANCE_IP=gateway
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
SPRING_PROFILES_ACTIVE=railway
CORS_ALLOWED_ORIGINS=*
```

---

## 🎯 Thứ tự Deploy

Deploy theo thứ tự này:

1. ✅ **MySQL Database** (tạo trước)
2. ✅ **Eureka Server**
3. ✅ **Auth Service**
4. ✅ **Finance Service**
5. ✅ **Learning Service**
6. ✅ **Gamification Service**
7. ✅ **AI Service**
8. ✅ **Firebase Notification Service**
9. ✅ **Gateway** (deploy cuối cùng)

---

## ✅ Railway Auto-Detection Checklist

Khi bạn thêm service mới, Railway sẽ tự động:

- [x] **Detect Dockerfile** trong root directory
- [x] **Detect Maven** (từ pom.xml)
- [x] **Build application** với Maven
- [x] **Create Docker image**
- [x] **Deploy container**
- [x] **Inject PORT** environment variable
- [x] **Generate public domain** (nếu cần)

**Bạn chỉ cần:**
1. Chọn **Root Directory**
2. Thêm **Environment Variables**
3. Railway làm phần còn lại!

---

## 🔍 Troubleshooting

### Railway không detect Dockerfile

1. Kiểm tra Dockerfile có trong root directory không
2. Kiểm tra Root Directory có đúng không (ví dụ: `edufinai/eureka`)
3. Kiểm tra Dockerfile syntax có đúng không

### Build failed

1. Xem logs trong Railway Dashboard
2. Kiểm tra pom.xml có đúng không
3. Kiểm tra Java version (phải là 21)
4. Kiểm tra dependencies có đầy đủ không

### Service không start

1. Kiểm tra PORT có được inject đúng không
2. Kiểm tra environment variables có đầy đủ không
3. Xem logs để debug
4. Kiểm tra database connection

### PORT không được inject

1. Railway tự động inject PORT, không cần set thủ công
2. Đảm bảo Dockerfile sử dụng `$PORT` hoặc `-Dserver.port=$PORT`
3. Kiểm tra application config có đọc PORT từ env không

---

## 💡 Tips & Best Practices

1. **Luôn deploy Eureka trước** - Các service khác cần Eureka để register
2. **Deploy từng service một** - Đợi service trước chạy xong rồi mới deploy service tiếp theo
3. **Kiểm tra logs** - Luôn kiểm tra logs sau khi deploy
4. **Monitor Eureka Dashboard** - Đảm bảo tất cả services đã register
5. **Test endpoints** - Test các endpoints sau khi deploy xong

---

## 🎉 Hoàn thành!

Sau khi hoàn thành, Railway đã tự động:
- ✅ Detect và build tất cả services
- ✅ Deploy containers
- ✅ Inject PORT và environment variables
- ✅ Generate public domains

**Gateway URL:** `https://[gateway-public-domain]`

Tất cả requests từ frontend sẽ đi qua Gateway và được route đến các microservices!

---

## 📝 Quick Reference

**Tất cả environment variables:** Xem `RAILWAY_QUICK_REFERENCE.md`

**Chi tiết từng bước:** Xem `RAILWAY_SINGLE_PROJECT_DEPLOY.md`

**File env variables cho từng service:** Xem `edufinai/[service-name]/railway-env-variables.txt`
