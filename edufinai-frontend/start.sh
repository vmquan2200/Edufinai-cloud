#!/bin/bash

# Script để khởi động Frontend với Docker

echo "🚀 Đang khởi động EduFinAI Frontend..."
echo ""

# Kiểm tra Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker chưa được cài đặt. Vui lòng cài Docker trước."
    exit 1
fi

# Kiểm tra Docker đang chạy
if ! docker info &> /dev/null; then
    echo "❌ Docker daemon chưa chạy. Vui lòng khởi động Docker."
    exit 1
fi

echo "✅ Docker đã sẵn sàng"
echo ""

# Build và khởi động frontend
echo "📦 Đang build và khởi động frontend..."
echo "⏳ Lần đầu tiên có thể mất 5-10 phút..."
echo ""

docker-compose up -d --build

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Frontend đã được khởi động thành công!"
    echo ""
    echo "🌐 Truy cập: http://localhost:3000"
    echo ""
    echo "📝 Xem logs: docker-compose logs -f"
    echo ""
    echo "Để dừng frontend: docker-compose down"
else
    echo ""
    echo "❌ Có lỗi xảy ra khi khởi động frontend."
    echo "Kiểm tra logs: docker-compose logs"
    exit 1
fi
