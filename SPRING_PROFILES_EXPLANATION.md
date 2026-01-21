# 🔧 Spring Profiles: railway vs docker - Khi nào dùng gì?

Giải thích sự khác biệt giữa `railway` và `docker` profile và khi nào nên dùng profile nào.

---

## 📋 Tổng quan Profiles

Dự án có 3 profiles chính:
1. **`default`** (application.yaml) - Local development
2. **`docker`** (application-docker.yaml) - Docker Compose local
3. **`railway`** (application-railway.yaml) - Railway cloud deployment

---

## 🎯 Khi deploy trên Railway: Dùng `railway`

### ✅ Đúng: `SPRING_PROFILES_ACTIVE=railway`

**Lý do:**

1. **Dockerfile đã set sẵn:**
   ```dockerfile
   ENV SPRING_PROFILES_ACTIVE=railway
   ```

2. **application-railway.yaml được tối ưu cho Railway:**
   ```yaml
   server:
     port: ${PORT:0}  # Railway tự động inject PORT
   
   spring:
     datasource:
       url: jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}?useSSL=true
       # Dùng Railway environment variables
   
   eureka:
     client:
       service-url:
         defaultZone: ${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE}
         # Railway public domain với HTTPS
   ```

3. **Railway-specific features:**
   - ✅ PORT từ Railway env
   - ✅ Database connection qua Railway private network
   - ✅ Eureka với HTTPS public domain
   - ✅ SSL enabled cho database

---

## 🐳 Khi chạy Docker Compose local: Dùng `docker`

### ✅ Đúng: `SPRING_PROFILES_ACTIVE=docker`

**Lý do:**

1. **application-docker.yaml được tối ưu cho Docker Compose:**
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://mysql-auth:3306/identity?useSSL=false
       # Dùng Docker service names
   
   eureka:
     client:
       service-url:
         defaultZone: http://eureka:8761/eureka/
         # Docker internal network
   ```

2. **Docker Compose-specific features:**
   - ✅ Service names (mysql-auth, eureka)
   - ✅ Internal Docker network
   - ✅ No SSL (local development)
   - ✅ Fixed ports

---

## 📊 So sánh chi tiết

| Feature | `docker` Profile | `railway` Profile |
|---------|------------------|-------------------|
| **Database Host** | `mysql-auth` (Docker service) | `${MYSQLHOST}` (Railway env) |
| **Database Port** | `3306` (fixed) | `${MYSQLPORT}` (Railway env) |
| **Eureka URL** | `http://eureka:8761` (Docker service) | `${EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE}` (Railway public domain) |
| **SSL** | `useSSL=false` | `useSSL=true` |
| **HTTPS** | No | Yes (Railway public domains) |
| **PORT** | `0` (auto) | `${PORT}` (Railway inject) |
| **Environment** | Local Docker Compose | Railway Cloud |

---

## 🔍 Kiểm tra Profile hiện tại

### Trong Dockerfile:

```dockerfile
# Gateway Dockerfile
ENV SPRING_PROFILES_ACTIVE=railway  # ✅ Đúng cho Railway

# Auth Service Dockerfile  
ENV SPRING_PROFILES_ACTIVE=railway  # ✅ Đúng cho Railway
```

### Trong Railway Environment Variables:

Bạn có thể **override** profile từ Railway Dashboard:

```
SPRING_PROFILES_ACTIVE=railway
```

**Lưu ý:** Railway env vars sẽ override Dockerfile ENV, nhưng cả hai đều set `railway` nên không vấn đề.

---

## ✅ Kết luận

### Khi deploy trên Railway:

```bash
SPRING_PROFILES_ACTIVE=railway  # ✅ ĐÚNG
```

**Không dùng:**
```bash
SPRING_PROFILES_ACTIVE=docker   # ❌ SAI - sẽ không connect được database và Eureka
```

### Khi chạy Docker Compose local:

```bash
SPRING_PROFILES_ACTIVE=docker   # ✅ ĐÚNG
```

---

## 🎯 Quick Reference

| Môi trường | Profile | File Config |
|------------|---------|-------------|
| **Local Development** | `default` | `application.yaml` |
| **Docker Compose** | `docker` | `application-docker.yaml` |
| **Railway Cloud** | `railway` | `application-railway.yaml` |

---

## 🔧 Cách set Profile

### 1. Trong Dockerfile (đã set sẵn):

```dockerfile
ENV SPRING_PROFILES_ACTIVE=railway
```

### 2. Trong Railway Environment Variables:

```
SPRING_PROFILES_ACTIVE=railway
```

### 3. Override khi cần (không khuyến nghị):

Nếu muốn test `docker` profile trên Railway (không nên):

```
SPRING_PROFILES_ACTIVE=docker
```

Nhưng sẽ **KHÔNG hoạt động** vì:
- Database host `mysql-auth` không tồn tại trên Railway
- Eureka URL `http://eureka:8761` không đúng format Railway

---

## 💡 Best Practice

1. **Luôn dùng `railway` profile khi deploy trên Railway**
2. **Không cần thay đổi** - Dockerfile đã set đúng
3. **Chỉ override** nếu có lý do đặc biệt
4. **Kiểm tra logs** nếu có vấn đề về profile

---

## 🐛 Troubleshooting

### Service không connect được database

**Kiểm tra:**
1. Profile có đúng `railway` không?
2. Environment variables có đầy đủ không?
   - `MYSQLHOST`
   - `MYSQLPORT`
   - `MYSQLDATABASE`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`

### Service không register vào Eureka

**Kiểm tra:**
1. Profile có đúng `railway` không?
2. `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` có đúng không?
3. Eureka service đã running chưa?

### Port conflict

**Kiểm tra:**
1. Profile có đúng `railway` không?
2. Railway có inject PORT không?
3. Application có đọc `${PORT}` không?

---

## 🎉 Tóm tắt

**Khi deploy trên Railway bằng Dockerfile đã build:**

```
SPRING_PROFILES_ACTIVE=railway  ✅ ĐÚNG - Dùng cái này!
```

**Không dùng:**
```
SPRING_PROFILES_ACTIVE=docker   ❌ SAI - Chỉ dùng cho Docker Compose local
```

**Dockerfile đã set đúng rồi, bạn không cần làm gì thêm!** 🎉
