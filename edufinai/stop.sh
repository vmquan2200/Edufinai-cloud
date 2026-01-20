#!/bin/bash

# Script để dừng EduFinAI Backend Services

echo "🛑 Đang dừng EduFinAI Backend Services..."
echo ""

docker-compose down

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Đã dừng tất cả services."
    echo ""
    echo "Để xóa volumes (MẤT DỮ LIỆU): docker-compose down -v"
else
    echo ""
    echo "❌ Có lỗi xảy ra."
fi
