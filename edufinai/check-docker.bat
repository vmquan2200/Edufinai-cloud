@echo off
REM Script kiểm tra Docker trước khi chạy

echo.
echo 🔍 Đang kiểm tra Docker...
echo.

REM Kiểm tra Docker đã cài đặt chưa
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker chưa được cài đặt!
    echo.
    echo 📥 Vui lòng cài đặt Docker Desktop từ:
    echo    https://www.docker.com/products/docker-desktop
    echo.
    pause
    exit /b 1
)

echo ✅ Docker đã được cài đặt
docker --version
echo.

REM Kiểm tra Docker Desktop đang chạy
echo 🔍 Đang kiểm tra Docker Desktop đang chạy...
docker info >nul 2>&1
if errorlevel 1 (
    echo.
    echo ❌ Docker Desktop chưa chạy!
    echo.
    echo 📌 CÁCH KHẮC PHỤC:
    echo    1. Mở Docker Desktop từ Start Menu
    echo    2. Đợi Docker Desktop khởi động hoàn toàn (icon Docker ở system tray)
    echo    3. Kiểm tra icon Docker ở góc dưới bên phải màn hình (màu xanh = đang chạy)
    echo    4. Chạy lại lệnh: docker-compose up -d --build
    echo.
    echo 💡 Tip: Đợi Docker Desktop hiển thị "Docker Desktop is running" trước khi tiếp tục
    echo.
    pause
    exit /b 1
)

echo ✅ Docker Desktop đang chạy
echo.

REM Kiểm tra Docker Compose
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo ⚠️  Docker Compose không tìm thấy, nhưng có thể dùng 'docker compose' (không có dấu gạch ngang)
    echo.
) else (
    echo ✅ Docker Compose đã sẵn sàng
    docker-compose --version
    echo.
)

echo ✅ Tất cả kiểm tra đã hoàn tất!
echo.
echo Bạn có thể chạy: docker-compose up -d --build
echo.
