@echo off
REM Script để khởi động EduFinAI Backend Services với Docker (Windows)

echo.
echo 🚀 Đang khởi động EduFinAI Backend Services...
echo.

REM Kiểm tra Docker
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker chưa được cài đặt. Vui lòng cài Docker trước.
    pause
    exit /b 1
)

REM Kiểm tra Docker Compose
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker Compose chưa được cài đặt. Vui lòng cài Docker Compose trước.
    pause
    exit /b 1
)

REM Kiểm tra Docker đang chạy
docker info >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker daemon chưa chạy. Vui lòng khởi động Docker Desktop.
    pause
    exit /b 1
)

echo ✅ Docker đã sẵn sàng
echo.

REM Kiểm tra file .env (tùy chọn)
if not exist .env (
    echo ⚠️  File .env không tồn tại. Tạo file .env mẫu...
    echo GEMINI_API_KEY= > .env
    echo ✅ Đã tạo file .env. Bạn có thể thêm GEMINI_API_KEY vào file này nếu cần.
    echo.
)

REM Build và khởi động services
echo 📦 Đang build và khởi động services...
echo ⏳ Lần đầu tiên có thể mất 10-15 phút...
echo.

docker-compose up -d --build

if errorlevel 1 (
    echo.
    echo ❌ Có lỗi xảy ra khi khởi động services.
    echo Kiểm tra logs: docker-compose logs
    pause
    exit /b 1
)

echo.
echo ✅ Services đã được khởi động thành công!
echo.
echo 📊 Kiểm tra trạng thái:
echo    docker-compose ps
echo.
echo 📝 Xem logs:
echo    docker-compose logs -f
echo.
echo 🌐 Truy cập:
echo    - Eureka Dashboard: http://localhost:8761
echo    - API Gateway: http://localhost:8080
echo.
echo ⏳ Đợi 1-2 phút để các services đăng ký vào Eureka...
echo.
echo Để dừng services: docker-compose down
echo.
pause
