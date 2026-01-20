# 🚀 Railway Deployment - Single Project (Monorepo)

Hướng dẫn deploy **TẤT CẢ services trong cùng 1 project** trên Railway - cách đơn giản và dễ quản lý nhất!

---

## ✅ Ưu điểm của Single Project

- ✅ **Dễ quản lý**: Tất cả services trong 1 nơi
- ✅ **Reference variables đơn giản**: Không cần nhớ tên project
- ✅ **Shared resources**: Dùng chung MySQL, dễ monitor
- ✅ **Cost effective**: Quản lý billing tập trung
- ✅ **Deploy cùng lúc**: Có thể deploy nhiều services cùng lúc

---

## 📋 Tổng quan Services

Trong cùng 1 project, bạn sẽ có:
1. **MySQL** - Database (1 instance cho tất cả databases)
2. **Eureka** - Service Discovery
3. **Auth Service** - Authentication
4. **Finance Service** - Finance Management
5. **Learning Service** - Learning Management
6. **Gamification Service** - Gamification
7. **AI Service** - AI/ML Services
8. **Firebase Notification Service** - Notifications
9. **Gateway** - API Gateway

---

## 🎯 Bước 1: Tạo Project và MySQL Database

### 1.1. Tạo Project mới trên Railway

1. Vào https://railway.app → **New Project**
2. Đặt tên project (ví dụ: `edufinai-cloud`)
3. Click **+ New** → **Database** → **MySQL**

### 1.2. Tạo các Databases

Vào **MySQL** service → **Connect** → dùng MySQL client hoặc Railway CLI:

```sql
CREATE DATABASE IF NOT EXISTS identity;
CREATE DATABASE IF NOT EXISTS finance;
CREATE DATABASE IF NOT EXISTS learning;
CREATE DATABASE IF NOT EXISTS gamification;
CREATE DATABASE IF NOT EXISTS ai_service;
CREATE DATABASE IF NOT EXISTS firebase;
```

**Hoặc** để Spring Boot tự tạo (nếu `ddl-auto=update`).

### 1.3. Lưu lại tên MySQL Service

Ghi nhớ tên MySQL service trên Railway (thường là `MySQL` hoặc tên bạn đặt).

---

## 🔧 Bước 2: Deploy Eureka Server

### 2.1. Thêm Eureka Service vào Project

1. Trong cùng project → **+ New** → **GitHub Repo**
2. Chọn repo của bạn
3. **Root Directory:** `edufinai/eureka`
4. Railway tự động detect Dockerfile và build

### 2.2. Environment Variables

Vào **Eureka** service → **Settings** → **Variables**, thêm:

```
SPRING_PROFILES_ACTIVE=railway
```

**Lưu ý:** Eureka không cần database, chỉ cần Spring profile.

### 2.3. Lưu lại tên Eureka Service

Ghi nhớ tên Eureka service (thường là `eureka` hoặc tên bạn đặt).

---

## 🔐 Bước 3: Deploy Auth Service

### 3.1. Thêm Auth Service

1. Trong cùng project → **+ New** → **GitHub Repo**
2. Chọn cùng repo
3. **Root Directory:** `edufinai/auth-service`

### 3.2. Environment Variables

Vào **Auth Service** → **Settings** → **Variables**, thêm:

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

**Lưu ý:** 
- Thay `MySQL` và `Eureka` bằng tên service thực tế trên Railway của bạn
- Trong cùng 1 project, Railway tự động detect các services, nên bạn chỉ cần đảm bảo tên đúng

---

## 💰 Bước 4: Deploy Finance Service

### 4.1. Thêm Finance Service

1. **+ New** → **GitHub Repo** (cùng repo)
2. **Root Directory:** `edufinai/finance-service`

### 4.2. Environment Variables

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
AUTH_SERVICE_URL=http://auth-service
GAMIFICATION_SERVICE_URL=http://GAMIFICATION-SERVICE
```

---

## 📚 Bước 5: Deploy Learning Service

### 5.1. Thêm Learning Service

1. **+ New** → **GitHub Repo** (cùng repo)
2. **Root Directory:** `edufinai/learning-service`

### 5.2. Environment Variables

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

### 6.1. Thêm Gamification Service

1. **+ New** → **GitHub Repo** (cùng repo)
2. **Root Directory:** `edufinai/gamification-service`

### 6.2. Environment Variables

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

### 7.1. Thêm AI Service

1. **+ New** → **GitHub Repo** (cùng repo)
2. **Root Directory:** `edufinai/ai-service`

### 7.2. Environment Variables

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

### 8.1. Thêm Firebase Notification Service

1. **+ New** → **GitHub Repo** (cùng repo)
2. **Root Directory:** `edufinai/firebase-notification`

### 8.2. Environment Variables

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

### 9.1. Thêm Gateway Service

1. **+ New** → **GitHub Repo** (cùng repo)
2. **Root Directory:** `edufinai/gateway`

### 9.2. Environment Variables

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

## 🎯 Thứ tự Deploy (QUAN TRỌNG)

Deploy theo thứ tự này trong cùng 1 project:

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

## 📊 Cấu trúc Project trên Railway

Sau khi deploy xong, project của bạn sẽ trông như thế này:

```
edufinai-cloud (Project)
├── MySQL (Database)
├── Eureka (Service)
├── Auth Service (Service)
├── Finance Service (Service)
├── Learning Service (Service)
├── Gamification Service (Service)
├── AI Service (Service)
├── Firebase Notification Service (Service)
└── Gateway (Service)
```

Tất cả trong **1 project**, dễ quản lý và monitor!

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
4. Đảm bảo tên service trong reference đúng (ví dụ: `MySQL`, `Eureka`)

### Lỗi kết nối Database

1. Kiểm tra MySQL service đã running chưa
2. Kiểm tra database đã được tạo chưa
3. Kiểm tra `MYSQLHOST`, `MYSQLPASSWORD` có đúng không
4. Kiểm tra `MYSQLDATABASE` có match với database name không
5. **Quan trọng:** Đảm bảo tên MySQL service trong reference đúng (ví dụ: `${{MySQL.RAILWAY_PRIVATE_DOMAIN}}`)

### Reference variables không hoạt động

1. Đảm bảo format đúng: `${{ServiceName.VARIABLE_NAME}}`
2. Đảm bảo service name match với tên trên Railway (case-sensitive)
3. Kiểm tra service đã được deploy chưa
4. Trong cùng 1 project, Railway tự động detect services, nhưng vẫn cần đảm bảo tên đúng

### Cách xem tên service trên Railway

1. Vào Railway Dashboard
2. Click vào service bạn muốn reference
3. Xem tên service ở trên cùng (ví dụ: `MySQL`, `Eureka`, `auth-service`)
4. Dùng chính xác tên đó trong reference: `${{ServiceName.VARIABLE}}`

---

## 💡 Tips & Best Practices

1. **Đặt tên service rõ ràng**: Đặt tên service dễ nhớ (ví dụ: `MySQL`, `Eureka`, `auth-service`)
2. **Deploy từng bước**: Deploy theo thứ tự, đợi service trước chạy xong rồi mới deploy service tiếp theo
3. **Kiểm tra logs**: Luôn kiểm tra logs sau khi deploy để đảm bảo không có lỗi
4. **Monitor Eureka**: Thường xuyên kiểm tra Eureka Dashboard để đảm bảo tất cả services đã register
5. **Backup variables**: Lưu lại tất cả environment variables vào file để dễ quản lý

---

## 🎉 Hoàn thành!

Sau khi hoàn thành tất cả các bước trên, hệ thống của bạn đã sẵn sàng trên Railway trong **1 project duy nhất**!

**Gateway URL:** `https://[gateway-public-domain]`

Tất cả requests từ frontend sẽ đi qua Gateway và được route đến các microservices tương ứng.

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
