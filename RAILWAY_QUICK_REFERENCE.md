# 🚀 Railway Quick Reference - Copy & Paste Environment Variables

File này chứa tất cả environment variables cần thiết cho từng service. Copy và paste trực tiếp vào Railway Dashboard.

## 🎯 Single Project Deployment (Khuyến nghị)

**Deploy tất cả services trong cùng 1 project** - cách đơn giản và dễ quản lý nhất!

Xem hướng dẫn chi tiết: `RAILWAY_SINGLE_PROJECT_DEPLOY.md`

---

## 📋 Service Names Reference

Trước khi copy, thay các giá trị sau:
- `MySQL` → Tên MySQL service trên Railway của bạn (trong cùng project)
- `Eureka` → Tên Eureka service trên Railway của bạn (trong cùng project)

**Lưu ý:** Trong cùng 1 project, Railway tự động detect các services, bạn chỉ cần đảm bảo tên đúng.

---

## 1️⃣ Eureka Service

```
SPRING_PROFILES_ACTIVE=railway
```

---

## 2️⃣ Auth Service

```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=identity
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=auth-service
EUREKA_INSTANCE_IP=auth-service
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
JWT_VALID_DURATION=3600
JWT_REFRESHABLE_DURATION=36000
```

---

## 3️⃣ Finance Service

```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=finance
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=finance-service
EUREKA_INSTANCE_IP=finance-service
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
CORS_ALLOWED_ORIGINS=*
AUTH_SERVICE_URL=http://auth-service
GAMIFICATION_SERVICE_URL=http://GAMIFICATION-SERVICE
```

---

## 4️⃣ Learning Service

```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=learning
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=learning-service
EUREKA_INSTANCE_IP=learning-service
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
```

---

## 5️⃣ Gamification Service

```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=gamification
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=gamification-service
EUREKA_INSTANCE_IP=gamification-service
SPRING_PROFILES_ACTIVE=railway
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=
GAMIFICATION_SERVICE_URL=http://gamification-service
```

---

## 6️⃣ AI Service

```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=ai_service
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=ai-service
EUREKA_INSTANCE_IP=ai-service
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

## 7️⃣ Firebase Notification Service

```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=firebase
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=firebase-notification
EUREKA_INSTANCE_IP=firebase-notification
SPRING_PROFILES_ACTIVE=railway
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
```

---

## 8️⃣ Gateway Service

```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
EUREKA_INSTANCE_HOSTNAME=gateway
EUREKA_INSTANCE_IP=gateway
SPRING_PROFILES_ACTIVE=railway
CORS_ALLOWED_ORIGINS=*
```

---

## 📝 Database Names Reference

Đảm bảo các database sau đã được tạo trong MySQL:

- `identity` - Auth Service
- `finance` - Finance Service
- `learning` - Learning Service
- `gamification` - Gamification Service
- `ai_service` - AI Service
- `firebase` - Firebase Notification Service

---

## ✅ Deploy Order

1. MySQL Database
2. Eureka Server
3. Auth Service
4. Finance Service
5. Learning Service
6. Gamification Service
7. AI Service
8. Firebase Notification Service
9. Gateway (deploy cuối cùng)

---

## 🔗 Useful Links

- Railway Dashboard: https://railway.app
- Eureka Dashboard: `https://[eureka-public-domain]/`
- Gateway URL: `https://[gateway-public-domain]`
