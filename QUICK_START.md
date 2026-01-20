# 🚀 Quick Start Guide - EduFinAI

Hướng dẫn nhanh để chạy dự án EduFinAI với Docker.

## ⚡ Cách Nhanh Nhất

### Windows

1. **Khởi động Backend Services:**
   ```bash
   cd edufinai
   start.bat
   ```

2. **Khởi động Frontend** (mở terminal mới):
   ```bash
   cd edufinai-frontend
   start.bat
   ```

3. **Truy cập:**
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761

### Linux/Mac

1. **Khởi động Backend Services:**
   ```bash
   cd edufinai
   chmod +x start.sh
   ./start.sh
   ```

2. **Khởi động Frontend** (mở terminal mới):
   ```bash
   cd edufinai-frontend
   chmod +x start.sh
   ./start.sh
   ```

3. **Truy cập:**
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8080
   - Eureka Dashboard: http://localhost:8761

---

## 📋 Yêu Cầu

- Docker Desktop đã cài đặt và đang chạy
- Ports: 8080, 8761, 3000, 3310-3315, 6379 chưa được sử dụng

---

## 🔧 Cách Thủ Công (Nếu Scripts Không Hoạt Động)

### Backend

```bash
cd edufinai
docker-compose up -d --build
```

### Frontend

```bash
cd edufinai-frontend
docker-compose down
docker-compose up -d --build
```

---

## 🛑 Dừng Services

### Backend
```bash
cd edufinai
docker-compose down
```

Hoặc chạy `stop.bat` / `stop.sh`

### Frontend
```bash
cd edufinai-frontend
docker-compose down
```

---

## 📚 Tài Liệu Chi Tiết

Xem file `DOCKER_SETUP_GUIDE.md` để biết hướng dẫn chi tiết và troubleshooting.

---

## ⏱️ Thời Gian Khởi Động

- **Lần đầu**: 10-15 phút (download dependencies, build images)
- **Các lần sau**: 2-5 phút

---

## ✅ Kiểm Tra Services Đã Sẵn Sàng

1. Mở Eureka Dashboard: http://localhost:8761
2. Đợi 1-2 phút để các services đăng ký
3. Kiểm tra tất cả services hiển thị trong dashboard

---

**Chúc bạn chạy thành công! 🎉**
