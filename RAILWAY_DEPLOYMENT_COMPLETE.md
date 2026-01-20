# 🚀 Railway Deployment Guide - Complete Setup

Hướng dẫn deploy toàn bộ hệ thống Edufinai lên Railway một cách tự động, không cần setup thủ công.

## 📋 Tổng quan

Hệ thống bao gồm:
- **Eureka** - Service Discovery
- **MySQL** - Database
- **Gateway** - API Gateway
- **Auth Service** - Authentication & Authorization
- **Finance Service** - Quản lý tài chính
- **Learning Service** - Quản lý học tập
- **Gamification Service** - Hệ thống gamification
- **AI Service** - AI/ML services
- **Firebase Notification Service** - Push notifications

---

## 🎯 Thứ tự Deploy (QUAN TRỌNG)

Deploy theo thứ tự sau để đảm bảo dependencies được resolve đúng:

1. **MySQL Database** (tạo trước)
2. **Eureka Server** (service discovery)
3. **Auth Service** (các service khác có thể cần)
4. **Finance Service**
5. **Learning Service**
6. **Gamification Service**
7. **AI Service**
8. **Firebase Notification Service**
9. **Gateway** (deploy cuối cùng)

---

## 📦 Bước 1: Chuẩn bị MySQL Database

### 1.1. Tạo MySQL Service trên Railway

1. Vào Railway Dashboard → **New Project**
2. Click **+ New** → **Database** → **MySQL**
3. Railway tự động tạo MySQL instance

### 1.2. Tạo các Database cần thiết

Vào **MySQL** service → **Connect** → dùng Railway CLI hoặc MySQL client để tạo databases:

```sql
CREATE DATABASE IF NOT EXISTS identity;
CREATE DATABASE IF NOT EXISTS finance;
CREATE DATABASE IF NOT EXISTS learning;
CREATE DATABASE IF NOT EXISTS gamification;
CREATE DATABASE IF NOT EXISTS ai_service;
CREATE DATABASE IF NOT EXISTS firebase;
```

**Hoặc** để Spring Boot tự tạo (nếu `ddl-auto=update`).

### 1.3. Lưu lại MySQL Environment Variables

MySQL service sẽ có các biến môi trường sau (Railway tự động tạo):
- `MYSQL_DATABASE`
- `MYSQL_ROOT_PASSWORD`
- `MYSQL_URL`
- `MYSQLDATABASE`
- `MYSQLHOST`
- `MYSQLPASSWORD`
- `MYSQLPORT`
- `MYSQLUSER`

**Lưu ý:** Ghi nhớ tên MySQL service (ví dụ: `MySQL` hoặc `mysql-db`) để reference trong các service khác.

---

## 🔧 Bước 2: Deploy Eureka Server

### 2.1. Tạo Eureka Service

1. Railway Dashboard → **New Project** (hoặc thêm vào project hiện tại)
2. **+ New** → **GitHub Repo**
3. Chọn repo → **Root Directory:** `edufinai/eureka`
4. Railway tự động detect Dockerfile và build

### 2.2. Thêm Environment Variables

Vào **Settings** → **Variables**, thêm:

```
SPRING_PROFILES_ACTIVE=railway
```

**Lưu ý:** Eureka không cần database, chỉ cần Spring profile.

### 2.3. Lưu lại Eureka URL

Sau khi deploy xong, lưu lại **Public Domain** của Eureka service (ví dụ: `https://eureka-production.up.railway.app`).

---

## 🔐 Bước 3: Deploy Auth Service

### 3.1. Tạo Auth Service

1. **+ New** → **GitHub Repo**
2. **Root Directory:** `edufinai/auth-service`

### 3.2. Thêm Environment Variables

Copy từ file `edufinai/auth-service/railway-env-variables.txt`:

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
JWT_VALID_DURATION=3600
JWT_REFRESHABLE_DURATION=36000
```

**Lưu ý:** Thay `MySQL` và `Eureka` bằng tên service thực tế trên Railway của bạn.

---

## 💰 Bước 4: Deploy Finance Service

### 4.1. Tạo Finance Service

1. **+ New** → **GitHub Repo**
2. **Root Directory:** `edufinai/finance-service`

### 4.2. Thêm Environment Variables

Copy từ file `edufinai/finance-service/railway-env-variables.txt`:

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

## 📚 Bước 5: Deploy Learning Service

### 5.1. Tạo Learning Service

1. **+ New** → **GitHub Repo**
2. **Root Directory:** `edufinai/learning-service`

### 5.2. Thêm Environment Variables

Copy từ file `edufinai/learning-service/railway-env-variables.txt`:

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

## 🎮 Bước 6: Deploy Gamification Service

### 6.1. Tạo Gamification Service

1. **+ New** → **GitHub Repo**
2. **Root Directory:** `edufinai/gamification-service`

### 6.2. Thêm Environment Variables

Copy từ file `edufinai/gamification-service/railway-env-variables.txt`:

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

**Lưu ý:** Redis là optional, nếu không có thì bỏ qua các biến `REDIS_*`.

---

## 🤖 Bước 7: Deploy AI Service

### 7.1. Tạo AI Service

1. **+ New** → **GitHub Repo**
2. **Root Directory:** `edufinai/ai-service`

### 7.2. Thêm Environment Variables

Copy từ file `edufinai/ai-service/railway-env-variables.txt`:

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

**⚠️ QUAN TRỌNG:** Thay `your-gemini-api-key-here` bằng Gemini API key thực tế từ https://aistudio.google.com/

---

## 🔔 Bước 8: Deploy Firebase Notification Service

### 8.1. Tạo Firebase Notification Service

1. **+ New** → **GitHub Repo**
2. **Root Directory:** `edufinai/firebase-notification`

### 8.2. Thêm Environment Variables

Copy từ file `edufinai/firebase-notification/railway-env-variables.txt`:

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

**Lưu ý:** Đảm bảo file `firebase-service-account.json` đã được thêm vào `src/main/resources/`.

---

## 🌐 Bước 9: Deploy Gateway (Deploy cuối cùng)

### 9.1. Tạo Gateway Service

1. **+ New** → **GitHub Repo**
2. **Root Directory:** `edufinai/gateway`

### 9.2. Thêm Environment Variables

Copy từ file `edufinai/gateway/railway-env-variables.txt`:

```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/

EUREKA_INSTANCE_HOSTNAME=gateway
EUREKA_INSTANCE_IP=gateway
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false

SPRING_PROFILES_ACTIVE=railway

CORS_ALLOWED_ORIGINS=*
```

**Lưu ý:** Gateway deploy cuối cùng để đảm bảo tất cả services đã register vào Eureka.

---

## ✅ Kiểm tra Deployment

### 1. Kiểm tra Eureka Dashboard

Truy cập: `https://[eureka-public-domain]/`

Bạn sẽ thấy tất cả services đã register:
- AUTH-SERVICE
- FINANCE-SERVICE
- LEARNING-SERVICE
- GAMIFICATION-SERVICE
- AI-SERVICE
- NOTIFICATION-SERVICE
- GATEWAY

### 2. Test Gateway Endpoints

Gateway public domain: `https://[gateway-public-domain]`

Test các endpoints:
- `GET /auth/health` → Auth Service
- `GET /finance/api/...` → Finance Service
- `GET /learning/api/...` → Learning Service
- `GET /gamification/api/v1/gamify/...` → Gamification Service
- `GET /ai/api/...` → AI Service
- `GET /notification/api/notifications/...` → Notification Service

---

## 🔍 Troubleshooting

### Service không register vào Eureka

1. Kiểm tra `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` có đúng không
2. Kiểm tra Eureka service đã running chưa
3. Xem logs của service để debug

### Lỗi kết nối Database

1. Kiểm tra MySQL service đã running chưa
2. Kiểm tra database đã được tạo chưa
3. Kiểm tra `MYSQLHOST`, `MYSQLPASSWORD` có đúng không
4. Kiểm tra `MYSQLDATABASE` có match với database name không

### Gateway không route được requests

1. Kiểm tra service đã register vào Eureka chưa
2. Kiểm tra service name trong Eureka có đúng không (phải viết hoa: `AUTH-SERVICE`, `FINANCE-SERVICE`, ...)
3. Xem logs của Gateway để debug

### Reference variables không hoạt động

1. Đảm bảo format đúng: `${{ServiceName.VARIABLE_NAME}}`
2. Đảm bảo service name match với tên trên Railway
3. Kiểm tra service đã được deploy chưa

---

## 📝 Checklist Deploy

- [ ] MySQL Database đã được tạo và các database đã được tạo
- [ ] Eureka Server đã deploy và running
- [ ] Auth Service đã deploy và register vào Eureka
- [ ] Finance Service đã deploy và register vào Eureka
- [ ] Learning Service đã deploy và register vào Eureka
- [ ] Gamification Service đã deploy và register vào Eureka
- [ ] AI Service đã deploy và register vào Eureka (đã set GEMINI_API_KEY)
- [ ] Firebase Notification Service đã deploy và register vào Eureka
- [ ] Gateway đã deploy và có thể route requests
- [ ] Tất cả services hiển thị trên Eureka Dashboard
- [ ] Test các endpoints qua Gateway thành công

---

## 🎉 Hoàn thành!

Sau khi hoàn thành tất cả các bước trên, hệ thống của bạn đã sẵn sàng trên Railway!

**Gateway URL:** `https://[gateway-public-domain]`

Tất cả requests từ frontend sẽ đi qua Gateway và được route đến các microservices tương ứng.
