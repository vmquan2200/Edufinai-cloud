# 🎯 SCRIPT DEMO NGẮN GỌN - EDUFINAI

## 📌 GIỚI THIỆU (1 phút)
- **EduFinAI**: Hệ thống quản lý tài chính cá nhân thông minh với AI
- **Giải quyết**: Quản lý chi tiêu, phân tích tài chính, học tập, gamification
- **Tech Stack**: Spring Cloud Microservices + React, deploy trên Railway

---

## 🏗️ KIẾN TRÚC (1 phút)
- **7 Microservices**: Gateway, Auth, Finance, AI, Learning, Gamification, Notification
- **Eureka**: Service Discovery
- **JWT**: Authentication
- **Google Gemini AI**: Phân tích và chat

---

## 🎬 DEMO CÁC TÍNH NĂNG (10 phút)

### 1. DASHBOARD (2 phút)
- ✅ Số dư, thu chi, tiết kiệm
- ✅ Mục tiêu tài chính với progress bar
- ✅ Giao dịch gần đây
- ✅ **Báo cáo AI hôm nay** (insight, root cause, priority action)

### 2. QUẢN LÝ TÀI CHÍNH (3 phút)
- **Tab Thu chi**: Xem, thêm, xóa giao dịch
- **Tab Mục tiêu**: Tạo mục tiêu, nạp/rút tiền, xem lịch sử
- **Tab Báo cáo**: Biểu đồ tròn (phân bổ), biểu đồ cột (theo tháng)

### 3. CHAT AI (1 phút)
- Chat với Google Gemini AI về tài chính
- Format response đẹp với tips
- Lưu lịch sử conversation

### 4. HỌC TẬP (1 phút)
- Xem bài học theo chủ đề, độ khó
- Làm quiz, xem điểm số
- Theo dõi tiến độ học tập

### 5. GAMIFICATION (2 phút)
- **Challenges**: Xem thử thách active/completed
- **Leaderboard**: Daily/Weekly/Monthly/Alltime
- **Badge**: Xem badge đã đạt được

### 6. BÁO CÁO AI (1 phút)
- Tự động phân tích dữ liệu từ Finance + Learning + Gamification
- Đưa ra insight, root cause, priority action

---

## 💡 ĐIỂM NỔI BẬT KỸ THUẬT (2 phút)
1. **Microservices**: Dễ scale, fault isolation
2. **Eureka**: Service discovery tự động
3. **JWT**: Secure authentication
4. **Redis**: Leaderboard hiệu suất cao
5. **AI Integration**: Google Gemini với error handling
6. **Real-time**: Auto refresh, loading states

---

## ✅ KẾT LUẬN (1 phút)
- Hệ thống toàn diện: Finance + Learning + Gamification + AI
- Kiến trúc tốt, dễ mở rộng
- Giao diện đẹp, UX tốt

---

## 📝 CHECKLIST TRƯỚC DEMO
- [ ] Tất cả services đang chạy trên Railway
- [ ] Test đăng nhập
- [ ] Có dữ liệu mẫu (giao dịch, mục tiêu)
- [ ] Test các tính năng chính

---

## ❓ CÂU HỎI THƯỜNG GẶP

**Q: Tại sao Microservices?**
A: Tách biệt domain, dễ mở rộng, fault isolation.

**Q: Data consistency?**
A: Mỗi service có DB riêng, gọi qua REST API khi cần.

**Q: Security?**
A: JWT authentication, validate ở mỗi service.

**Q: Scale như thế nào?**
A: Scale từng service độc lập, Redis cho performance.

---

**Tổng thời gian: ~15 phút**
