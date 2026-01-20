# 🐳 Docker Setup - EduFinAI Backend

## ⚡ Quick Start

### Windows
```bash
start.bat
```

### Linux/Mac
```bash
chmod +x start.sh
./start.sh
```

## 📋 Manual Commands

### Start All Services
```bash
docker-compose up -d --build
```

### Stop All Services
```bash
docker-compose down
```

### View Logs
```bash
docker-compose logs -f
```

### Check Status
```bash
docker-compose ps
```

## 🌐 Access Points

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **MySQL Ports**: 3310-3315 (for debugging)

## 📝 Environment Variables

Tạo file `.env` trong thư mục này để set Gemini API Key (tùy chọn):

```
GEMINI_API_KEY=your_api_key_here
```

## 📚 Full Documentation

Xem `DOCKER_SETUP_GUIDE.md` ở thư mục gốc để biết hướng dẫn chi tiết.
