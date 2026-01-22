# 🎯 SCRIPT DEMO SẢN PHẨM EDUFINAI
## Hệ thống Quản lý Tài chính Cá nhân thông minh với AI

---

## 📋 MỤC LỤC
1. [Giới thiệu tổng quan](#1-giới-thiệu-tổng-quan)
2. [Kiến trúc hệ thống](#2-kiến-trúc-hệ-thống)
3. [Demo các tính năng chính](#3-demo-các-tính-năng-chính)
4. [Điểm nổi bật kỹ thuật](#4-điểm-nổi-bật-kỹ-thuật)
5. [Kết luận](#5-kết-luận)

---

## 1. GIỚI THIỆU TỔNG QUAN

### 1.1. Lời mở đầu
> "Xin chào thầy cô và các bạn. Hôm nay em xin được trình bày về dự án **EduFinAI** - một hệ thống quản lý tài chính cá nhân thông minh, tích hợp AI để đưa ra các phân tích và gợi ý tài chính cho người dùng."

### 1.2. Vấn đề cần giải quyết
- **Vấn đề 1**: Người dùng khó theo dõi và quản lý chi tiêu hàng ngày
- **Vấn đề 2**: Thiếu công cụ phân tích tài chính thông minh
- **Vấn đề 3**: Chưa có hệ thống gamification để khuyến khích thói quen tài chính tốt
- **Vấn đề 4**: Thiếu nguồn học tập về tài chính cá nhân có hệ thống

### 1.3. Giải pháp của EduFinAI
EduFinAI cung cấp:
- ✅ **Quản lý tài chính toàn diện**: Thu chi, mục tiêu, báo cáo
- ✅ **AI Assistant**: Phân tích và đưa ra gợi ý tài chính thông minh
- ✅ **Gamification**: Hệ thống thử thách, leaderboard, badge
- ✅ **Học tập tài chính**: Bài học, quiz, theo dõi tiến độ
- ✅ **Microservices Architecture**: Dễ mở rộng và bảo trì

---

## 2. KIẾN TRÚC HỆ THỐNG

### 2.1. Kiến trúc tổng quan
> "EduFinAI được xây dựng theo kiến trúc **Microservices**, sử dụng **Spring Cloud** và **React**, được deploy trên **Railway**."

**Các service chính:**
- **Gateway Service**: API Gateway, routing và load balancing
- **Auth Service**: Xác thực và quản lý người dùng
- **Finance Service**: Quản lý giao dịch, mục tiêu tài chính
- **AI Service**: Tích hợp Google Gemini AI để phân tích và chat
- **Learning Service**: Quản lý bài học và quiz
- **Gamification Service**: Thử thách, leaderboard, badge
- **Notification Service**: Push notification qua Firebase
- **Eureka**: Service discovery

### 2.2. Công nghệ sử dụng
**Backend:**
- Spring Boot 3.x, Spring Cloud Gateway
- MySQL, Redis
- JWT Authentication
- Google Gemini AI API

**Frontend:**
- React 18
- Tailwind CSS
- Recharts (biểu đồ)

**Infrastructure:**
- Railway (deployment)
- Docker
- Eureka Service Discovery

---

## 3. DEMO CÁC TÍNH NĂNG CHÍNH

### 3.1. ĐĂNG NHẬP & DASHBOARD

**Bước 1: Đăng nhập**
> "Đầu tiên, người dùng đăng nhập vào hệ thống. Hệ thống sử dụng JWT để xác thực."

**Hành động:**
- Mở trang đăng nhập
- Nhập thông tin (hoặc dùng tài khoản demo)
- Đăng nhập thành công → chuyển đến Home Page

**Bước 2: Dashboard tổng quan**
> "Sau khi đăng nhập, người dùng sẽ thấy Dashboard tổng quan với các thông tin chính:"

**Hiển thị:**
- **Số dư hiện tại**: Hiển thị số tiền hiện có
- **Thu nhập/Chi tiêu tháng này**: Tổng thu nhập và chi tiêu
- **Tỷ lệ tiết kiệm**: Phần trăm tiết kiệm so với thu nhập
- **Mục tiêu tài chính**: Các mục tiêu đang theo dõi với thanh tiến độ
- **Giao dịch gần đây**: 3 giao dịch mới nhất
- **Báo cáo AI hôm nay**: Phân tích tự động từ AI

**Điểm nhấn:**
> "Đặc biệt, phần **Báo cáo AI hôm nay** sử dụng Google Gemini AI để phân tích dữ liệu tài chính và đưa ra insight, nguyên nhân gốc rễ, và hành động ưu tiên."

---

### 3.2. QUẢN LÝ TÀI CHÍNH (Finance)

**Bước 1: Tab Thu chi**
> "Trong phần Quản lý Tài chính, người dùng có thể xem và quản lý tất cả giao dịch."

**Tính năng:**
- Xem danh sách giao dịch (thu nhập, chi tiêu, rút tiền)
- Lọc theo ngày, loại giao dịch
- Thêm giao dịch mới (nút "+")
- Xóa giao dịch

**Demo:**
1. Click "Thêm thu chi"
2. Chọn loại: Thu nhập hoặc Chi tiêu
3. Nhập thông tin: Tên, số tiền, danh mục, ngày
4. Lưu → Giao dịch xuất hiện ngay

**Bước 2: Tab Mục tiêu**
> "Người dùng có thể tạo và theo dõi các mục tiêu tài chính như mua xe, đi du lịch, v.v."

**Tính năng:**
- Xem danh sách mục tiêu (Active, Completed, Cancelled)
- Tạo mục tiêu mới
- Nạp tiền vào mục tiêu
- Rút tiền từ mục tiêu
- Xem lịch sử giao dịch của mục tiêu
- Xác nhận hoàn thành mục tiêu

**Demo:**
1. Click "Mục tiêu mới"
2. Nhập: Tên mục tiêu, số tiền cần, hạn chót
3. Lưu → Mục tiêu xuất hiện với thanh tiến độ
4. Click "Nạp tiền" → Chọn mục tiêu, nhập số tiền
5. Thanh tiến độ cập nhật realtime

**Bước 3: Tab Báo cáo**
> "Hệ thống tự động tạo báo cáo chi tiết với biểu đồ trực quan."

**Hiển thị:**
- Biểu đồ tròn: Phân bổ chi tiêu theo danh mục
- Biểu đồ cột: Thu nhập và chi tiêu theo tháng
- Chi tiết từng danh mục: Thu nhập và chi tiêu

**Điểm nhấn:**
> "Tất cả biểu đồ được render realtime từ dữ liệu thực tế, giúp người dùng dễ dàng nhận biết xu hướng tài chính."

---

### 3.3. CHAT VỚI AI

**Bước 1: Mở Chat**
> "Một trong những tính năng nổi bật nhất của EduFinAI là Chat với AI, sử dụng Google Gemini AI."

**Tính năng:**
- Chat trực tiếp với AI về tài chính
- AI có thể trả lời câu hỏi, đưa ra lời khuyên
- Lưu lịch sử chat theo conversation
- Format response đẹp với tips và disclaimers

**Demo:**
1. Mở trang Chat
2. Gõ câu hỏi: "Tôi nên tiết kiệm bao nhiêu phần trăm thu nhập?"
3. AI trả lời với format có cấu trúc
4. Tiếp tục hỏi: "Làm thế nào để đầu tư an toàn?"
5. Xem lịch sử chat ở sidebar

**Điểm nhấn:**
> "AI được train để hiểu ngữ cảnh tài chính và đưa ra lời khuyên phù hợp. Tất cả câu trả lời đều có disclaimer để người dùng hiểu rõ đây chỉ là gợi ý, không phải lời khuyên tài chính chuyên nghiệp."

---

### 3.4. HỌC TẬP TÀI CHÍNH (Learning)

**Bước 1: Xem danh sách bài học**
> "EduFinAI cung cấp hệ thống học tập tài chính với các bài học có cấu trúc."

**Tính năng:**
- Xem danh sách bài học theo chủ đề
- Lọc theo độ khó (Cơ bản, Trung bình, Nâng cao)
- Lọc theo tag (Budgeting, Investing, Saving, Debt, Tax)
- Xem tiến độ học tập

**Demo:**
1. Mở trang Learning
2. Xem danh sách bài học
3. Click vào một bài học
4. Xem nội dung, làm quiz
5. Xem điểm số và tiến độ

**Bước 2: Làm Quiz**
> "Mỗi bài học có quiz để kiểm tra kiến thức."

**Tính năng:**
- Câu hỏi trắc nghiệm
- Tính điểm tự động
- Lưu lịch sử làm bài
- Chỉ tính điểm cao nhất

**Điểm nhấn:**
> "Hệ thống chỉ tính điểm cao nhất của mỗi bài học, khuyến khích người dùng làm lại để cải thiện điểm số."

---

### 3.5. THỬ THÁCH & LEADERBOARD (Gamification)

**Bước 1: Xem Challenges**
> "EduFinAI sử dụng gamification để khuyến khích người dùng có thói quen tài chính tốt."

**Tính năng:**
- Xem danh sách thử thách (Active, Completed)
- Thử thách có thể là: Hoàn thành quiz, đạt mục tiêu tiết kiệm, v.v.
- Nhận điểm và badge khi hoàn thành

**Demo:**
1. Mở trang Challenges
2. Xem thử thách đang active
3. Xem thử thách đã hoàn thành
4. Click vào một thử thách để xem chi tiết

**Bước 2: Leaderboard**
> "Hệ thống có leaderboard để tạo động lực cạnh tranh lành mạnh."

**Tính năng:**
- Leaderboard theo 4 loại:
  - 📅 **Daily**: Reset mỗi ngày
  - 📆 **Weekly**: Reset mỗi tuần
  - 📊 **Monthly**: Reset mỗi tháng
  - 🏆 **Alltime**: Tổng điểm từ trước đến nay
- Xem top 20 người dẫn đầu
- Xem vị trí của mình

**Demo:**
1. Chọn loại leaderboard (ví dụ: Alltime)
2. Xem top 20
3. Xem vị trí của mình ở cuối trang
4. Chuyển sang Daily/Weekly/Monthly để so sánh

**Bước 3: Badge**
> "Người dùng nhận badge khi đạt các thành tích nhất định."

**Tính năng:**
- Xem danh sách badge đã đạt được
- Xem badge chưa đạt được (để có mục tiêu)

**Điểm nhấn:**
> "Leaderboard sử dụng Redis để đảm bảo hiệu suất cao, và tự động reset theo chu kỳ (daily, weekly, monthly)."

---

### 3.6. BÁO CÁO AI TỰ ĐỘNG

**Bước 1: Xem báo cáo trên Home**
> "Hệ thống tự động tạo báo cáo phân tích tài chính hàng ngày."

**Nội dung báo cáo:**
- **Insight**: Phân tích tổng quan về tình hình tài chính
- **Root Cause**: Nguyên nhân gốc rễ của các vấn đề (nếu có)
- **Priority Action**: Hành động ưu tiên nên làm

**Demo:**
1. Về trang Home
2. Xem card "Báo cáo hôm nay"
3. Đọc insight, root cause, priority action
4. Click nút refresh để tạo báo cáo mới

**Điểm nhấn:**
> "Báo cáo được tạo tự động bằng cách AI phân tích dữ liệu từ Finance Service, Learning Service, và Gamification Service, sau đó tổng hợp thành báo cáo dễ hiểu."

---

## 4. ĐIỂM NỔI BẬT KỸ THUẬT

### 4.1. Microservices Architecture
> "EduFinAI sử dụng kiến trúc microservices, mỗi service độc lập và có thể scale riêng."

**Lợi ích:**
- Dễ bảo trì và mở rộng
- Mỗi service có thể deploy độc lập
- Fault isolation: Lỗi ở một service không ảnh hưởng toàn hệ thống

### 4.2. Service Discovery với Eureka
> "Tất cả services đăng ký với Eureka, Gateway tự động route request đến service phù hợp."

**Lợi ích:**
- Không cần hardcode URL
- Tự động load balancing
- Dễ thêm service mới

### 4.3. JWT Authentication
> "Hệ thống sử dụng JWT để xác thực, token được forward tự động từ Gateway xuống các service."

**Flow:**
1. User đăng nhập → Auth Service tạo JWT
2. Frontend lưu JWT
3. Mỗi request gửi JWT trong header
4. Gateway forward JWT xuống service
5. Service validate JWT và xử lý request

### 4.4. AI Integration
> "Tích hợp Google Gemini AI để cung cấp phân tích thông minh."

**Sử dụng:**
- Chat với người dùng
- Tạo báo cáo tự động
- Phân tích dữ liệu tài chính

**Xử lý lỗi:**
- Retry khi gặp rate limit
- Fallback khi AI không available
- Cache response để giảm API calls

### 4.5. Redis cho Leaderboard
> "Leaderboard sử dụng Redis Sorted Set để đảm bảo hiệu suất cao."

**Lợi ích:**
- Query top N nhanh chóng (O(log N))
- Tự động sort theo điểm
- Dễ reset theo chu kỳ

### 4.6. Real-time Updates
> "Frontend sử dụng React hooks để tự động refresh data khi có thay đổi."

**Tính năng:**
- Auto-refresh sau khi thêm/sửa/xóa
- Loading states
- Error handling với retry

### 4.7. Responsive Design
> "Giao diện responsive, hoạt động tốt trên cả desktop và mobile."

**Sử dụng:**
- Tailwind CSS
- Flexbox và Grid
- Mobile-first approach

---

## 5. KẾT LUẬN

### 5.1. Tổng kết
> "EduFinAI là một hệ thống quản lý tài chính cá nhân toàn diện, kết hợp quản lý tài chính, học tập, gamification, và AI để tạo trải nghiệm tốt nhất cho người dùng."

**Điểm mạnh:**
- ✅ Kiến trúc microservices dễ mở rộng
- ✅ Tích hợp AI thông minh
- ✅ Gamification tạo động lực
- ✅ Học tập có hệ thống
- ✅ Giao diện đẹp, dễ sử dụng

### 5.2. Hướng phát triển
- Thêm nhiều loại thử thách
- Tích hợp ngân hàng (Open Banking)
- Mobile app (React Native)
- Multi-language support
- Advanced analytics với ML

### 5.3. Lời kết
> "Cảm ơn thầy cô và các bạn đã lắng nghe. Em sẵn sàng trả lời câu hỏi."

---

## 📝 GHI CHÚ CHO NGƯỜI DEMO

### Checklist trước khi demo:
- [ ] Đảm bảo tất cả services đang chạy trên Railway
- [ ] Test đăng nhập trước
- [ ] Chuẩn bị dữ liệu mẫu (giao dịch, mục tiêu)
- [ ] Test các tính năng chính trước
- [ ] Chuẩn bị câu trả lời cho câu hỏi thường gặp

### Câu hỏi thường gặp và câu trả lời:

**Q: Tại sao chọn Microservices?**
A: Vì dự án có nhiều domain khác nhau (Finance, Learning, Gamification), microservices giúp tách biệt logic và dễ mở rộng.

**Q: Làm thế nào đảm bảo data consistency?**
A: Mỗi service có database riêng. Khi cần data từ service khác, gọi qua REST API. Với leaderboard, sử dụng Redis để đảm bảo performance.

**Q: Xử lý lỗi như thế nào?**
A: Mỗi service có error handling riêng. Gateway có thể retry khi service fail. Frontend có error boundary và retry logic.

**Q: Security như thế nào?**
A: Sử dụng JWT authentication, validate ở mỗi service. Gateway forward token xuống service. CORS được cấu hình ở Gateway.

**Q: Làm thế nào scale khi có nhiều user?**
A: Có thể scale từng service độc lập. Redis cho leaderboard đảm bảo performance. Có thể thêm load balancer và cache layer.

---

## 🎬 TIMING CHO DEMO (Tổng: ~15-20 phút)

1. **Giới thiệu** (2 phút)
2. **Kiến trúc** (2 phút)
3. **Dashboard & Finance** (4 phút)
4. **Chat AI** (2 phút)
5. **Learning** (2 phút)
6. **Gamification** (3 phút)
7. **Điểm nổi bật kỹ thuật** (3 phút)
8. **Kết luận & Q&A** (2 phút)

---

**Chúc bạn demo thành công! 🚀**
