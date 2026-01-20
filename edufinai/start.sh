#!/bin/bash

# Script để khởi động EduFinAI Backend Services với Docker

echo "🚀 Đang khởi động EduFinAI Backend Services..."
echo ""

# Kiểm tra Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker chưa được cài đặt. Vui lòng cài Docker trước."
    exit 1
fi

# Kiểm tra Docker Compose
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose chưa được cài đặt. Vui lòng cài Docker Compose trước."
    exit 1
fi

# Kiểm tra Docker đang chạy
if ! docker info &> /dev/null; then
    echo "❌ Docker daemon chưa chạy. Vui lòng khởi động Docker."
    exit 1
fi

echo "✅ Docker đã sẵn sàng"
echo ""

# Kiểm tra file .env (tùy chọn)
if [ ! -f .env ]; then
    echo "⚠️  File .env không tồn tại. Tạo file .env mẫu..."
    echo "GEMINI_API_KEY=" > .env
    echo "✅ Đã tạo file .env. Bạn có thể thêm GEMINI_API_KEY vào file này nếu cần."
    echo ""
fi

# Build và khởi động services
echo "📦 Đang build và khởi động services..."
echo "⏳ Lần đầu tiên có thể mất 10-15 phút..."
echo ""

docker-compose up -d --build

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Services đã được khởi động thành công!"
    echo ""
    echo "📊 Kiểm tra trạng thái:"
    echo "   docker-compose ps"
    echo ""
    echo "📝 Xem logs:"
    echo "   docker-compose logs -f"
    echo ""
    echo "🌐 Truy cập:"
    echo "   - Eureka Dashboard: http://localhost:8761"
    echo "   - API Gateway: http://localhost:8080"
    echo ""
    echo "⏳ Đợi 1-2 phút để các services đăng ký vào Eureka..."
    echo ""
    echo "Để dừng services: docker-compose down"
else
    echo ""
    echo "❌ Có lỗi xảy ra khi khởi động services."
    echo "Kiểm tra logs: docker-compose logs"
    exit 1
fi
