# 🔧 Fix: Frontend đang gọi localhost:8080 thay vì Railway Gateway

## 🔍 Vấn đề

Frontend đang gọi `http://localhost:8080` thay vì Railway Gateway URL:
```
POST http://localhost:8080/auth/auth/token net::ERR_BLOCKED_BY_CLIENT
```

**Nguyên nhân:** `REACT_APP_API_URL` chưa được inject vào build hoặc chưa được set trong Railway.

---

## ✅ Giải pháp

### Bước 1: Kiểm tra Environment Variables trong Railway

1. Vào **Railway Dashboard** → **Frontend Service** → **Settings** → **Variables**
2. Kiểm tra có `REACT_APP_API_URL` chưa
3. Nếu chưa có, thêm:

```
REACT_APP_API_URL=https://gateway-production.up.railway.app
```

**Hoặc dùng reference:**

```
REACT_APP_API_URL=${{Gateway.RAILWAY_PUBLIC_DOMAIN}}
```

**Lưu ý quan trọng:**
- ✅ Phải là **HTTPS** (không phải HTTP)
- ✅ Không có trailing slash (`/`)
- ✅ Thay `gateway-production.up.railway.app` bằng Gateway domain thực tế của bạn

---

### Bước 2: Rebuild Frontend Service

**QUAN TRỌNG:** Sau khi set env vars, **PHẢI rebuild** Frontend!

1. Railway Dashboard → **Frontend Service** → **Settings** → **Redeploy**
2. Hoặc push code mới để trigger rebuild

**Lý do:** React build-time variables (`REACT_APP_*`) được "baked" vào JavaScript bundle tại BUILD TIME, không phải runtime!

---

### Bước 3: Clear Browser Cache

Sau khi rebuild:

1. **Hard refresh:** `Ctrl+Shift+R` (Windows) hoặc `Cmd+Shift+R` (Mac)
2. Hoặc **Clear browser cache** hoàn toàn
3. Hoặc mở **Incognito/Private window** để test

---

## 🔍 Kiểm tra sau khi fix

### Cách 1: Browser Console

Mở Frontend → F12 → Console, chạy:

```javascript
console.log('API URL:', process.env.REACT_APP_API_URL);
```

**Kết quả mong đợi:**
- ✅ `https://gateway-production.up.railway.app` (hoặc Gateway URL của bạn)
- ❌ `undefined` hoặc `http://localhost:8080` → Chưa rebuild hoặc env var chưa set

### Cách 2: Network Tab

1. F12 → **Network**
2. Thử đăng nhập
3. Xem request đến `/auth/auth/token`
4. **Request URL** phải là: `https://gateway-production.up.railway.app/auth/auth/token`
5. Không phải: `http://localhost:8080/auth/auth/token`

### Cách 3: Xem Build Logs

1. Railway Dashboard → **Frontend Service** → **Deployments** → Latest → **View Logs**
2. Tìm dòng build để xem env vars có được inject không
3. Tìm: `REACT_APP_API_URL` trong logs

---

## 🐛 Troubleshooting

### Vẫn thấy localhost:8080 sau khi rebuild

**Nguyên nhân có thể:**

1. **Env var chưa được set đúng:**
   - Kiểm tra lại Railway Variables
   - Đảm bảo tên đúng: `REACT_APP_API_URL` (không có typo)
   - Đảm bảo giá trị đúng format

2. **Railway không inject vào build:**
   - Railway tự động inject env vars vào build stage
   - Nhưng cần đảm bảo Dockerfile đúng
   - Xem build logs để confirm

3. **Browser cache:**
   - Clear cache hoàn toàn
   - Hoặc dùng Incognito mode

### ERR_BLOCKED_BY_CLIENT

Lỗi này có thể do:
- **Ad blocker** - Tắt ad blocker và thử lại
- **Browser security policy** - Thử browser khác
- **Mixed content** - Đảm bảo cả Frontend và Gateway đều dùng HTTPS

---

## 📝 Checklist

- [ ] `REACT_APP_API_URL` đã được set trong Railway Frontend Variables
- [ ] `REACT_APP_API_URL` dùng HTTPS (không phải HTTP)
- [ ] `REACT_APP_API_URL` không có trailing slash
- [ ] Frontend đã được **Redeploy** sau khi set env vars
- [ ] Browser cache đã được clear
- [ ] Build logs cho thấy env vars được inject
- [ ] Browser console không còn hiển thị `localhost:8080`

---

## 🎯 Quick Fix Summary

1. **Set env var:** `REACT_APP_API_URL=https://[gateway-domain]`
2. **Redeploy:** Frontend service
3. **Clear cache:** Browser
4. **Test:** Đăng nhập lại

---

## 💡 Lưu ý quan trọng

**React Environment Variables:**
- `REACT_APP_*` variables được **baked vào** JavaScript bundle tại BUILD TIME
- **KHÔNG phải** runtime variables
- Phải **rebuild** sau khi thay đổi
- Railway tự động inject vào build stage khi deploy

**Railway Auto-injection:**
- Railway tự động pass tất cả env vars vào Docker build
- Không cần ARG trong Dockerfile (nhưng có cũng được)
- React sẽ tự động đọc `process.env.REACT_APP_*` trong build

---

## 🎉 Sau khi fix

Frontend sẽ gọi đến:
```
https://gateway-production.up.railway.app/auth/auth/token
```

Thay vì:
```
http://localhost:8080/auth/auth/token
```

Login sẽ hoạt động! ✅
