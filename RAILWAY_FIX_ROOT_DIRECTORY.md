# 🔧 Fix Lỗi Railway: "could not determine how to build"

## ❌ Lỗi Gặp Phải

```
⚠ Script start.sh not found
✖ Railpack could not determine how to build the app.
```

## 🔍 Nguyên Nhân

Railway đang cố detect cách build từ **root directory** của repository, nhưng:
- Dự án của bạn là **monorepo** với nhiều services
- Dockerfile nằm trong các thư mục con (`edufinai/eureka`, `edufinai/auth-service`, etc.)
- Railway không tự động detect được Dockerfile ở root

## ✅ Giải Pháp

**Bạn PHẢI chỉ định Root Directory cho mỗi service!**

---

## 📝 Cách Fix Chi Tiết

### Bước 1: Tạo Service trên Railway

1. Trong Railway project, click **"+ New"**
2. Chọn **"GitHub Repo"**
3. Chọn repository: `Edufinai-cloud`
4. Railway sẽ tạo service mới

### Bước 2: Set Root Directory

**QUAN TRỌNG:** Sau khi tạo service, Railway sẽ báo lỗi "could not determine how to build" - **ĐÂY LÀ BÌNH THƯỜNG!**

1. Click vào service vừa tạo
2. Vào tab **"Settings"** (hoặc click icon ⚙️)
3. Tìm mục **"Root Directory"** hoặc **"Source"** → **"Root Directory"**
4. Nhập đường dẫn đến thư mục chứa Dockerfile:
   - Eureka: `edufinai/eureka`
   - Auth Service: `edufinai/auth-service`
   - Finance Service: `edufinai/finance-service`
   - Learning Service: `edufinai/learning-service`
   - Gamification Service: `edufinai/gamification-service`
   - AI Service: `edufinai/ai-service`
   - Firebase Notification: `edufinai/firebase-notification`
   - Gateway: `edufinai/gateway`
   - Frontend: `edufinai-frontend`
5. Railway sẽ tự động detect Dockerfile và bắt đầu build

### Bước 3: Kiểm Tra Build

1. Vào tab **"Deployments"** hoặc **"Logs"**
2. Kiểm tra Railway đã detect Dockerfile chưa
3. Nếu thành công, bạn sẽ thấy:
   ```
   ✓ Detected Dockerfile
   Building Docker image...
   ```

---

## 🎯 Ví Dụ Cụ Thể: Deploy Eureka

### Cách 1: Qua Railway UI

1. **"+ New"** → **"GitHub Repo"** → Chọn `Edufinai-cloud`
2. Railway tạo service mới (có thể tên là `Edufinai-cloud`)
3. Click vào service → **Settings**
4. Tìm **"Root Directory"**
5. Nhập: `edufinai/eureka`
6. Railway tự động detect Dockerfile và build

### Cách 2: Qua Railway CLI (Nếu có)

```bash
railway link
railway service create eureka
railway variables set RAILWAY_ROOT_DIRECTORY=edufinai/eureka
railway up
```

---

## 📋 Checklist Root Directory cho Tất Cả Services

- [ ] **Eureka:** `edufinai/eureka`
- [ ] **Auth Service:** `edufinai/auth-service`
- [ ] **Finance Service:** `edufinai/finance-service`
- [ ] **Learning Service:** `edufinai/learning-service`
- [ ] **Gamification Service:** `edufinai/gamification-service`
- [ ] **AI Service:** `edufinai/ai-service`
- [ ] **Firebase Notification:** `edufinai/firebase-notification`
- [ ] **Gateway:** `edufinai/gateway`
- [ ] **Frontend:** `edufinai-frontend`

---

## 🖼️ Hình Ảnh Hướng Dẫn (Mô Tả)

### Trong Railway Dashboard:

```
Project: EduFinAI
├── + New (button)
│   ├── GitHub Repo
│   ├── Database
│   └── ...
│
└── Services:
    ├── eureka (service)
    │   ├── Settings ⚙️
    │   │   └── Root Directory: edufinai/eureka ← SET Ở ĐÂY
    │   ├── Variables
    │   ├── Networking
    │   └── Deployments
    │
    └── auth-service (service)
        └── Settings ⚙️
            └── Root Directory: edufinai/auth-service ← SET Ở ĐÂY
```

---

## ⚠️ Lưu Ý Quan Trọng

1. **Root Directory phải đúng:** Nếu sai, Railway sẽ không tìm thấy Dockerfile
2. **Không có dấu `/` ở đầu:** Dùng `edufinai/eureka` không phải `/edufinai/eureka`
3. **Case-sensitive:** Đảm bảo viết đúng chữ hoa/thường
4. **Sau khi set Root Directory:** Railway sẽ tự động trigger build mới

---

## 🐛 Troubleshooting

### Vấn đề: Sau khi set Root Directory vẫn báo lỗi

**Giải pháp:**
1. Kiểm tra Dockerfile có tồn tại trong thư mục đó không
2. Kiểm tra đường dẫn Root Directory đúng chưa
3. Xem logs để biết lỗi cụ thể: **Deployments** → Click vào deployment → **View Logs**

### Vấn đề: Railway không tự động build sau khi set Root Directory

**Giải pháp:**
1. Click **"Redeploy"** hoặc **"Deploy"** button
2. Hoặc push một commit mới lên GitHub (nếu đã enable auto-deploy)

### Vấn đề: Build failed sau khi set Root Directory

**Giải pháp:**
1. Xem logs chi tiết trong **Deployments** → **Logs**
2. Kiểm tra Dockerfile có lỗi syntax không
3. Kiểm tra dependencies có thể download được không

---

## ✅ Sau Khi Fix

Sau khi set Root Directory đúng, bạn sẽ thấy:

```
✓ Detected Dockerfile
Building Docker image...
Step 1/10 : FROM maven:3.9.6-eclipse-temurin-21 AS builder
...
✓ Build successful
```

---

## 📚 Tài Liệu Tham Khảo

- **Railway Docs về Root Directory:** https://docs.railway.app/develop/variables#root-directory
- **Railway Monorepo Guide:** https://docs.railway.app/develop/monorepo

---

**Sau khi fix, tiếp tục với hướng dẫn deploy trong `RAILWAY_DEPLOYMENT_GUIDE.md`! 🚀**
