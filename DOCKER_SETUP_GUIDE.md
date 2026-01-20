# 🐳 Hướng Dẫn Chạy Dự Án EduFinAI Với Docker

## 📋 Yêu Cầu Hệ Thống

- **Docker**: phiên bản 20.10 trở lên
- **Docker Compose**: phiên bản 1.29 trở lên
- **RAM**: tối thiểu 8GB (khuyến nghị 16GB)
- **Disk**: tối thiểu 10GB trống
- **OS**: Windows 10/11, macOS, hoặc Linux

## 🔧 Kiểm Tra Cài Đặt

```bash
# Kiểm tra Docker
docker --version

# Kiểm tra Docker Compose
docker-compose --version

# Kiểm tra Docker đang chạy
docker ps
```

---

## 🚀 CÁCH 1: Chạy Toàn Bộ Hệ Thống (Backend + Frontend)

### Bước 1: Chuẩn Bị Môi Trường

#### 1.1. Tạo file `.env` cho Gemini API Key (tùy chọn)

Nếu bạn muốn sử dụng tính năng AI, tạo file `.env` trong thư mục `edufinai/`:

```bash
# edufinai/.env
GEMINI_API_KEY=your_gemini_api_key_here
```

> **Lưu ý**: Nếu không có API key, AI service vẫn chạy nhưng một số tính năng AI sẽ không hoạt động.

#### 1.2. Kiểm tra Ports Đã Sử Dụng

Đảm bảo các ports sau chưa được sử dụng:
- `8080` - Gateway (API Gateway)
- `8761` - Eureka (Service Discovery)
- `3000` - Frontend
- `3310-3315` - MySQL databases
- `6379` - Redis

Nếu port bị chiếm, bạn có thể:
- Dừng service đang dùng port đó
- Hoặc sửa port trong `docker-compose.yml`

### Bước 2: Build và Chạy Backend Services

```bash
# Di chuyển vào thư mục backend
cd edufinai

# Build và khởi động tất cả services
docker-compose up -d --build
```

Lệnh này sẽ:
- Build tất cả Docker images cho các microservices
- Khởi động các MySQL databases
- Khởi động Redis
- Khởi động Eureka Server
- Khởi động tất cả microservices (auth, finance, learning, gamification, ai, notification)
- Khởi động Gateway

**Thời gian**: Lần đầu tiên có thể mất 10-15 phút để download dependencies và build images.

### Bước 3: Kiểm Tra Trạng Thái Services

```bash
# Xem trạng thái tất cả containers
docker-compose ps

# Xem logs của tất cả services
docker-compose logs -f

# Xem logs của một service cụ thể
docker-compose logs -f gateway
docker-compose logs -f auth-service
docker-compose logs -f finance-service
```

### Bước 4: Kiểm Tra Eureka Dashboard

Mở trình duyệt và truy cập:
```
http://localhost:8761
```

Bạn sẽ thấy các services đã đăng ký:
- `AUTH-SERVICE`
- `FINANCE-SERVICE`
- `LEARNING-SERVICE`
- `GAMIFICATION-SERVICE`
- `AI-SERVICE`
- `FIREBASE-NOTIFICATION`
- `GATEWAY`

> ⚠️ **Lưu ý**: Các services có thể mất 1-2 phút để đăng ký vào Eureka. Hãy đợi cho đến khi tất cả services hiển thị trong dashboard.

### Bước 5: Build và Chạy Frontend

Mở terminal mới:

```bash
# Di chuyển vào thư mục frontend
cd edufinai-frontend

# Build và chạy frontend với Docker
docker-compose up -d --build
```

Frontend sẽ chạy tại: **http://localhost:3000**

### Bước 6: Kiểm Tra Gateway

Gateway đang chạy tại: **http://localhost:8080**

Bạn có thể test các endpoints:
- `http://localhost:8080/auth/auth/health` (nếu có)
- `http://localhost:8080/finance/v1/categories` (cần JWT token)

---

## 🎯 CÁCH 2: Chạy Từng Phần (Development)

Nếu bạn muốn chạy từng phần để debug hoặc phát triển:

### Chỉ Chạy Infrastructure (MySQL, Redis, Eureka)

```bash
cd edufinai
docker-compose up -d mysql-auth mysql-finance mysql-learning mysql-gamification mysql-ai mysql-firebase redis eureka
```

### Chạy Từng Service

```bash
# Chỉ chạy auth-service
docker-compose up -d auth-service

# Chỉ chạy finance-service
docker-compose up -d finance-service

# Chỉ chạy learning-service
docker-compose up -d learning-service
```

---

## 🛠️ Các Lệnh Hữu Ích

### Xem Logs

```bash
# Logs của tất cả services
docker-compose logs -f

# Logs của một service cụ thể
docker-compose logs -f gateway
docker-compose logs -f auth-service
docker-compose logs -f finance-service
docker-compose logs -f learning-service
docker-compose logs -f gamification-service
docker-compose logs -f ai-service
docker-compose logs -f firebase-notification

# Logs của database
docker-compose logs -f mysql-auth
docker-compose logs -f mysql-finance
```

### Restart Services

```bash
# Restart tất cả services
docker-compose restart

# Restart một service cụ thể
docker-compose restart gateway
docker-compose restart auth-service
```

### Stop và Start

```bash
# Dừng tất cả services (giữ lại containers)
docker-compose stop

# Khởi động lại services đã dừng
docker-compose start

# Dừng và xóa containers (KHÔNG xóa volumes/data)
docker-compose down

# Dừng và xóa tất cả (bao gồm volumes - MẤT DỮ LIỆU!)
docker-compose down -v
```

### Rebuild Services

```bash
# Rebuild một service cụ thể
docker-compose build auth-service
docker-compose up -d auth-service

# Rebuild tất cả services
docker-compose build
docker-compose up -d
```

### Kiểm Tra Resource Usage

```bash
# Xem tài nguyên sử dụng
docker stats

# Xem disk usage
docker system df
```

---

## 🔍 Troubleshooting

### 1. Services Không Khởi Động

**Vấn đề**: Services không thể kết nối đến database hoặc Eureka.

**Giải pháp**:
```bash
# Kiểm tra logs
docker-compose logs auth-service

# Kiểm tra database đã sẵn sàng chưa
docker-compose ps mysql-auth

# Restart service
docker-compose restart auth-service
```

### 2. Port Đã Được Sử Dụng

**Vấn đề**: `Bind for 0.0.0.0:8080 failed: port is already allocated`

**Giải pháp**:
```bash
# Windows PowerShell
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Hoặc đổi port trong docker-compose.yml
```

### 3. Database Connection Error

**Vấn đề**: `Communications link failure` hoặc `Access denied`

**Giải pháp**:
```bash
# Kiểm tra MySQL container đang chạy
docker-compose ps mysql-auth

# Kiểm tra logs MySQL
docker-compose logs mysql-auth

# Restart MySQL
docker-compose restart mysql-auth

# Kiểm tra kết nối từ service đến MySQL
docker-compose exec auth-service ping mysql-auth
```

### 4. Eureka Services Không Đăng Ký

**Vấn đề**: Services không xuất hiện trong Eureka dashboard.

**Giải pháp**:
```bash
# Đợi 2-3 phút (services cần thời gian để đăng ký)
# Kiểm tra logs của service
docker-compose logs auth-service | grep -i eureka

# Kiểm tra Eureka đang chạy
curl http://localhost:8761

# Restart service
docker-compose restart auth-service
```

### 5. Frontend Không Kết Nối Được Backend

**Vấn đề**: Frontend không thể gọi API.

**Giải pháp**:
- Kiểm tra Gateway đang chạy: `http://localhost:8080`
- Kiểm tra CORS settings trong Gateway
- Kiểm tra browser console để xem lỗi cụ thể
- Đảm bảo frontend đang gọi đúng URL: `http://localhost:8080`

### 6. Out of Memory

**Vấn đề**: Containers bị kill do thiếu memory.

**Giải pháp**:
- Tăng Docker memory limit trong Docker Desktop settings
- Hoặc chạy ít services cùng lúc
- Hoặc tăng RAM cho máy

### 7. Build Failed

**Vấn đề**: `mvn clean package` failed trong Docker build.

**Giải pháp**:
```bash
# Xóa cache và rebuild
docker-compose build --no-cache auth-service

# Hoặc build local trước để test
cd auth-service
mvn clean package
```

### 8. Database Schema Không Tự Động Tạo

**Vấn đề**: Tables không được tạo tự động.

**Giải pháp**:
- Kiểm tra `spring.jpa.hibernate.ddl-auto=update` trong `application-docker.properties`
- Xem logs của service để kiểm tra lỗi migration
- Có thể cần chạy SQL scripts thủ công

---

## 📊 Kiểm Tra Health của Services

### Gateway
```bash
curl http://localhost:8080/actuator/health
```

### Eureka
```bash
curl http://localhost:8761
```

### Kiểm Tra Database Connections

```bash
# Kết nối vào MySQL container
docker-compose exec mysql-auth mysql -uroot -p123456

# Kiểm tra databases
SHOW DATABASES;

# Kiểm tra tables trong database identity
USE identity;
SHOW TABLES;
```

---

## 🗑️ Cleanup (Xóa Tất Cả)

**CẢNH BÁO**: Lệnh này sẽ xóa TẤT CẢ containers, images, volumes và networks!

```bash
# Dừng và xóa containers + volumes
docker-compose down -v

# Xóa images
docker-compose down --rmi all

# Xóa tất cả (nếu cần)
docker system prune -a --volumes
```

---

## 📝 Ghi Chú Quan Trọng

1. **Lần Đầu Chạy**: Có thể mất 10-15 phút để build và download dependencies.

2. **Database Data**: Data được lưu trong Docker volumes, sẽ được giữ lại khi restart containers (trừ khi dùng `docker-compose down -v`).

3. **Environment Variables**: 
   - `GEMINI_API_KEY` có thể được set trong `.env` file hoặc environment variables.
   - Các biến khác được set trong `docker-compose.yml`.

4. **Network**: Tất cả services giao tiếp qua Docker network `edufin-net`.

5. **Ports Mapping**:
   - Gateway: `8080:8080` (exposed)
   - Eureka: `8761:8761` (exposed)
   - MySQL: `3310-3315:3306` (exposed cho debugging)
   - Redis: `6379:6379` (exposed)
   - Frontend: `3000:80` (exposed)

6. **Service Discovery**: Services tự động đăng ký vào Eureka và giao tiếp qua service names (ví dụ: `http://auth-service`).

---

## 🎉 Sau Khi Chạy Thành Công

1. **Frontend**: http://localhost:3000
2. **Gateway**: http://localhost:8080
3. **Eureka Dashboard**: http://localhost:8761
4. **API Documentation** (nếu có Swagger):
   - Finance Service: http://localhost:8080/finance/swagger-ui.html
   - Learning Service: http://localhost:8080/learning/swagger-ui.html
   - Gamification Service: http://localhost:8080/gamification/swagger-ui.html

---

## 📞 Hỗ Trợ

Nếu gặp vấn đề, hãy:
1. Kiểm tra logs: `docker-compose logs -f <service-name>`
2. Kiểm tra Eureka dashboard để xem services đã đăng ký chưa
3. Kiểm tra ports có bị chiếm không
4. Kiểm tra Docker resources (memory, disk)

---

**Chúc bạn chạy thành công! 🚀**
