# 🎨 Frontend Railway Deployment Guide

Hướng dẫn deploy Frontend React lên Railway với environment variables.

---

## 📋 Tổng quan

Frontend React app cần các environment variables sau:
- **REACT_APP_API_URL** - Gateway URL (bắt buộc)
- **REACT_APP_FIREBASE_*** - Firebase configuration (bắt buộc nếu dùng Firebase)
- **REACT_APP_AUTH_ENABLED** - Feature flag (optional)

---

## ⚠️ QUAN TRỌNG: React Build-time Variables

React **KHÔNG hỗ trợ runtime environment variables**. Tất cả biến `REACT_APP_*` phải được set **TRƯỚC KHI BUILD**.

Railway sẽ tự động inject các biến này vào Docker build stage.

---

## 🚀 Bước 1: Deploy Frontend trên Railway

### 1.1. Tạo Frontend Service

1. Railway Dashboard → Project của bạn
2. **+ New** → **GitHub Repo**
3. Chọn repo
4. **Root Directory:** `edufinai-frontend`
5. Railway tự động detect Dockerfile và build

---

## 🔧 Bước 2: Thêm Environment Variables

Vào **Frontend Service** → **Settings** → **Variables**, thêm:

### 2.1. API Gateway URL (BẮT BUỘC)

```
REACT_APP_API_URL=https://gateway-production.up.railway.app
```

**Hoặc reference từ Gateway service:**

```
REACT_APP_API_URL=${{Gateway.RAILWAY_PUBLIC_DOMAIN}}
```

**Lưu ý:** 
- Phải là **HTTPS URL** (Railway tự động cung cấp HTTPS)
- Không có trailing slash (`/`)
- Thay `gateway-production.up.railway.app` bằng Gateway public domain thực tế của bạn

---

### 2.2. Firebase Configuration (BẮT BUỘC nếu dùng Firebase)

Lấy từ Firebase Console: https://console.firebase.google.com/

```
REACT_APP_FIREBASE_API_KEY=AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
REACT_APP_FIREBASE_AUTH_DOMAIN=your-project.firebaseapp.com
REACT_APP_FIREBASE_PROJECT_ID=your-project-id
REACT_APP_FIREBASE_STORAGE_BUCKET=your-project.appspot.com
REACT_APP_FIREBASE_MESSAGING_SENDER_ID=123456789012
REACT_APP_FIREBASE_APP_ID=1:123456789012:web:abcdefghijklmnop
REACT_APP_FIREBASE_MEASUREMENT_ID=G-XXXXXXXXXX
REACT_APP_FIREBASE_VAPID_KEY=your-vapid-key-here
```

**Cách lấy Firebase Config:**

1. Vào Firebase Console → Project Settings
2. Scroll xuống **Your apps** → Web app
3. Copy các giá trị từ config object
4. VAPID Key: Project Settings → Cloud Messaging → Web Push certificates

---

### 2.3. Feature Flags (Optional)

```
REACT_APP_AUTH_ENABLED=true
```

---

## 📝 Full Environment Variables Template

Copy tất cả vào Railway Variables:

```bash
# API Gateway URL (Reference từ Gateway service)
REACT_APP_API_URL=${{Gateway.RAILWAY_PUBLIC_DOMAIN}}

# Firebase Configuration
REACT_APP_FIREBASE_API_KEY=your-firebase-api-key
REACT_APP_FIREBASE_AUTH_DOMAIN=your-project.firebaseapp.com
REACT_APP_FIREBASE_PROJECT_ID=your-project-id
REACT_APP_FIREBASE_STORAGE_BUCKET=your-project.appspot.com
REACT_APP_FIREBASE_MESSAGING_SENDER_ID=your-messaging-sender-id
REACT_APP_FIREBASE_APP_ID=your-app-id
REACT_APP_FIREBASE_MEASUREMENT_ID=your-measurement-id
REACT_APP_FIREBASE_VAPID_KEY=your-vapid-key

# Feature Flags
REACT_APP_AUTH_ENABLED=true
```

**Lưu ý:** Thay các giá trị `your-*` bằng giá trị thực tế từ Firebase Console và Gateway URL.

---

## 🔍 Kiểm tra Environment Variables

### Cách 1: Xem trong Railway Dashboard

1. Vào Frontend Service → **Variables**
2. Kiểm tra tất cả biến `REACT_APP_*` đã được set chưa

### Cách 2: Xem trong Build Logs

1. Vào Frontend Service → **Deployments** → Latest deployment → **View Logs**
2. Tìm dòng build để xem env vars có được inject không

### Cách 3: Kiểm tra trong Browser Console

Sau khi deploy, mở browser console và check:

```javascript
// Kiểm tra API URL
console.log('API URL:', process.env.REACT_APP_API_URL);

// Kiểm tra Firebase config
console.log('Firebase Project:', process.env.REACT_APP_FIREBASE_PROJECT_ID);
```

**Lưu ý:** Trong production build, `process.env` chỉ chứa các giá trị đã được build vào code, không phải runtime values.

---

## 🎯 Thứ tự Deploy

Deploy theo thứ tự này:

1. ✅ **MySQL Database**
2. ✅ **Eureka Server**
3. ✅ **Auth Service**
4. ✅ **Finance Service**
5. ✅ **Learning Service**
6. ✅ **Gamification Service**
7. ✅ **AI Service**
8. ✅ **Firebase Notification Service**
9. ✅ **Gateway** (deploy trước Frontend)
10. ✅ **Frontend** (deploy cuối cùng - cần Gateway URL)

---

## 🔧 Troubleshooting

### Frontend không connect được đến Gateway

**Kiểm tra:**
1. `REACT_APP_API_URL` có đúng không?
2. Gateway service đã running chưa?
3. Gateway URL có đúng format không? (HTTPS, không có trailing slash)
4. CORS có được config đúng không? (Gateway đã set `CORS_ALLOWED_ORIGINS=*`)

**Test Gateway:**
```bash
curl https://gateway-production.up.railway.app/actuator/health
```

### Firebase không hoạt động

**Kiểm tra:**
1. Tất cả Firebase env vars đã được set chưa?
2. Firebase config có đúng không?
3. Firebase project có enable Web app chưa?
4. VAPID key có đúng không?

**Test Firebase:**
- Mở browser console
- Check Firebase initialization errors
- Check Firebase config values

### Environment variables không được inject vào build

**Nguyên nhân:**
- React build-time variables phải được set TRƯỚC KHI BUILD
- Railway tự động inject vào build stage, nhưng cần đảm bảo:
  1. Variables được set trong Railway Dashboard
  2. Variables có prefix `REACT_APP_`
  3. Rebuild sau khi thay đổi variables

**Giải pháp:**
1. Set variables trong Railway Dashboard
2. Trigger rebuild: **Settings** → **Redeploy** hoặc push code mới

### Build failed

**Kiểm tra:**
1. Node version có đúng không? (Dockerfile dùng `node:18-alpine`)
2. Dependencies có install được không?
3. Build command có chạy được không?
4. Xem build logs để debug

---

## 💡 Tips & Best Practices

1. **Luôn deploy Gateway trước Frontend** - Frontend cần Gateway URL
2. **Set Gateway URL bằng reference** - Dùng `${{Gateway.RAILWAY_PUBLIC_DOMAIN}}` để tự động update
3. **Kiểm tra HTTPS** - Đảm bảo Gateway URL là HTTPS
4. **Test sau khi deploy** - Kiểm tra frontend có connect được đến Gateway không
5. **Monitor logs** - Xem browser console và Railway logs để debug

---

## 📊 Cấu trúc Environment Variables

### Trong Code (src/services/*.js):

```javascript
const GATEWAY_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';
```

### Trong Railway:

```
REACT_APP_API_URL=https://gateway-production.up.railway.app
```

### Sau khi build:

Giá trị `https://gateway-production.up.railway.app` sẽ được **baked vào** JavaScript bundle.

---

## 🎉 Hoàn thành!

Sau khi deploy xong:

1. ✅ Frontend sẽ tự động build với env vars từ Railway
2. ✅ Frontend sẽ connect đến Gateway qua `REACT_APP_API_URL`
3. ✅ Firebase sẽ hoạt động với config từ env vars
4. ✅ Tất cả API calls sẽ đi qua Gateway

**Frontend URL:** `https://[frontend-public-domain]`

---

## 📝 Quick Reference

**File env variables template:** `edufinai-frontend/railway-env-variables.txt`

**File .env.example:** `edufinai-frontend/.env.example`

**Dockerfile:** `edufinai-frontend/Dockerfile` (đã được cập nhật để nhận env vars)
