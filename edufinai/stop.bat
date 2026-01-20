@echo off
REM Script để dừng EduFinAI Backend Services (Windows)

echo.
echo 🛑 Đang dừng EduFinAI Backend Services...
echo.

docker-compose down

if errorlevel 1 (
    echo.
    echo ❌ Có lỗi xảy ra.
    pause
    exit /b 1
)

echo.
echo ✅ Đã dừng tất cả services.
echo.
echo Để xóa volumes (MẤT DỮ LIỆU): docker-compose down -v
echo.
pause
