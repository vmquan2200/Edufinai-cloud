@echo off
REM Script để khởi động Frontend với Docker (Windows)

echo.
echo 🚀 Đang khởi động EduFinAI Frontend...
echo.

REM Kiểm tra Docker
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker chưa được cài đặt. Vui lòng cài Docker trước.
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

REM Build và khởi động frontend
echo 📦 Đang build và khởi động frontend...
echo ⏳ Lần đầu tiên có thể mất 5-10 phút...
echo.

docker-compose up -d --build

if errorlevel 1 (
    echo.
    echo ❌ Có lỗi xảy ra khi khởi động frontend.
    echo Kiểm tra logs: docker-compose logs
    pause
    exit /b 1
)

echo.
echo ✅ Frontend đã được khởi động thành công!
echo.
echo 🌐 Truy cập: http://localhost:3000
echo.
echo 📝 Xem logs: docker-compose logs -f
echo.
echo Để dừng frontend: docker-compose down
echo.
pause
