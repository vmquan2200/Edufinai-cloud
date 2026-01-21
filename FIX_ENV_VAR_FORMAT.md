# 🔧 Fix: Environment Variable Format Issue

## ❌ Vấn đề hiện tại

Environment variable của bạn:
```
REACT_APP_API_URL="https://gateway-production-b350.up.railway.app/"
```

**Có 2 vấn đề:**
1. ❌ **Có quotes (`"`)** - Railway có thể không parse đúng
2. ❌ **Có trailing slash (`/`)** - Sẽ tạo URL sai: `https://...railway.app//auth/...`

---

## ✅ Giải pháp

### Bước 1: Sửa Environment Variable trong Railway

1. Vào **Railway Dashboard** → **Frontend Service** → **Settings** → **Variables**
2. Tìm `REACT_APP_API_URL`
3. **Xóa** và **thêm lại** với format đúng:

```
REACT_APP_API_URL=https://gateway-production-b350.up.railway.app
```

**Format đúng:**
- ✅ **KHÔNG có quotes** (`"`)
- ✅ **KHÔNG có trailing slash** (`/`)
- ✅ **HTTPS** (không phải HTTP)
- ✅ **Đầy đủ domain**

---

### Bước 2: Rebuild Frontend (QUAN TRỌNG!)

Sau khi sửa env var:

1. Railway Dashboard → **Frontend Service** → **Settings** → **Redeploy**
2. Đợi build xong (có thể mất 2-5 phút)

**Lý do:** React build-time variables được "baked" vào JavaScript bundle tại BUILD TIME. Phải rebuild để áp dụng thay đổi!

---

### Bước 3: Clear Browser Cache

Sau khi rebuild xong:

1. **Hard refresh:** `Ctrl+Shift+R` (Windows) hoặc `Cmd+Shift+R` (Mac)
2. Hoặc **Clear browser cache** hoàn toàn
3. Hoặc mở **Incognito/Private window** để test

---

## 🔍 Kiểm tra sau khi fix

### Cách 1: Network Tab (Chính xác nhất)

1. Mở Frontend → **F12** → **Network**
2. Thử đăng nhập
3. Tìm request đến `/auth/auth/token`
4. Xem **Request URL**:

**✅ Đúng:**
```
https://gateway-production-b350.up.railway.app/auth/auth/token
```

**❌ Sai:**
```
http://localhost:8080/auth/auth/token
https://gateway-production-b350.up.railway.app//auth/auth/token  (có double slash)
```

### Cách 2: Xem Source Code trong Browser

1. F12 → **Sources** → Tìm file `main.js` hoặc `bundle.js`
2. Search: `gateway-production` hoặc `localhost:8080`
3. Nếu thấy `gateway-production-b350.up.railway.app` → ✅ Đúng
4. Nếu thấy `localhost:8080` → ❌ Chưa rebuild

### Cách 3: Build Logs

1. Railway Dashboard → **Frontend Service** → **Deployments** → Latest → **View Logs**
2. Tìm dòng build
3. Kiểm tra env vars có được inject không

---

## 📝 Lưu ý về `process.env` trong Browser Console

**Bạn không thể check `process.env.REACT_APP_API_URL` trong browser console!**

**Lý do:**
- `process.env` chỉ tồn tại tại **BUILD TIME** (trong Node.js build process)
- Sau khi build, giá trị được **baked vào** JavaScript bundle
- Trong browser runtime, `process` không tồn tại
- Đó là lý do bạn thấy: `Uncaught ReferenceError: process is not defined`

**Cách kiểm tra đúng:**
- ✅ Xem **Network Tab** để xem URL thực tế được gọi
- ✅ Xem **Source Code** trong browser để tìm giá trị đã được baked
- ✅ Xem **Build Logs** trong Railway

---

## ✅ Checklist

- [ ] `REACT_APP_API_URL` **KHÔNG có quotes** (`"`)
- [ ] `REACT_APP_API_URL` **KHÔNG có trailing slash** (`/`)
- [ ] `REACT_APP_API_URL` dùng **HTTPS**
- [ ] Frontend đã được **Redeploy** sau khi sửa
- [ ] Browser cache đã được **clear**
- [ ] Network Tab cho thấy URL đúng (không phải localhost:8080)

---

## 🎯 Quick Fix

1. **Sửa env var:** Bỏ quotes và trailing slash
   ```
   REACT_APP_API_URL=https://gateway-production-b350.up.railway.app
   ```

2. **Redeploy:** Frontend service

3. **Clear cache:** Browser

4. **Test:** Đăng nhập lại

---

## 🐛 Nếu vẫn không hoạt động

### Kiểm tra Build Logs:

1. Railway Dashboard → Frontend Service → Deployments → Latest → View Logs
2. Tìm dòng có `REACT_APP_API_URL`
3. Xem giá trị có đúng không

### Kiểm tra Gateway:

```bash
# Test Gateway health
curl https://gateway-production-b350.up.railway.app/actuator/health

# Test Auth endpoint
curl -X POST https://gateway-production-b350.up.railway.app/auth/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test"}'
```

### Kiểm tra CORS:

Đảm bảo Gateway có:
```
CORS_ALLOWED_ORIGINS=*
```

---

## 🎉 Sau khi fix

Frontend sẽ gọi đến:
```
https://gateway-production-b350.up.railway.app/auth/auth/token
```

Login sẽ hoạt động! ✅
