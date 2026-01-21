# 🔧 Troubleshooting: Failed to fetch khi đăng nhập

Hướng dẫn khắc phục lỗi "Failed to fetch" khi đăng nhập trên frontend.

---

## 🔍 Nguyên nhân phổ biến

### 1. ❌ REACT_APP_API_URL chưa được set hoặc sai

**Triệu chứng:**
- Frontend gọi đến `http://localhost:8080` thay vì Railway Gateway URL
- Browser console hiển thị: `Failed to fetch` hoặc `CORS error`

**Kiểm tra:**

1. Vào Railway Dashboard → **Frontend Service** → **Variables**
2. Kiểm tra `REACT_APP_API_URL` có được set chưa
3. Kiểm tra URL có đúng format không:
   - ✅ Đúng: `https://gateway-production.up.railway.app`
   - ❌ Sai: `http://gateway-production.up.railway.app` (thiếu HTTPS)
   - ❌ Sai: `https://gateway-production.up.railway.app/` (có trailing slash)

**Giải pháp:**

```
REACT_APP_API_URL=https://gateway-production.up.railway.app
```

**Hoặc reference từ Gateway service:**

```
REACT_APP_API_URL=${{Gateway.RAILWAY_PUBLIC_DOMAIN}}
```

**Sau khi sửa:**
1. Trigger rebuild: **Settings** → **Redeploy**
2. Hoặc push code mới để trigger rebuild

---

### 2. ❌ CORS Configuration không đúng

**Triệu chứng:**
- Browser console hiển thị: `CORS policy: No 'Access-Control-Allow-Origin' header`
- Network tab hiển thị: `OPTIONS` request failed

**Kiểm tra:**

1. Vào Railway Dashboard → **Gateway Service** → **Variables**
2. Kiểm tra `CORS_ALLOWED_ORIGINS`:

```
CORS_ALLOWED_ORIGINS=*
```

**Hoặc set cụ thể Frontend domain:**

```
CORS_ALLOWED_ORIGINS=https://frontend-production.up.railway.app
```

**Giải pháp:**

Gateway config đã có CORS, nhưng cần đảm bảo:
- `allowCredentials: false` (đúng với config hiện tại)
- `allowedOrigins: *` hoặc frontend domain cụ thể

---

### 3. ❌ Gateway không route đúng đến Auth Service

**Triệu chứng:**
- Gateway trả về 404 hoặc 502
- Eureka có AUTH-SERVICE nhưng Gateway không route được

**Kiểm tra:**

1. **Kiểm tra Eureka Dashboard:**
   - Truy cập: `https://[eureka-domain]/`
   - Xem AUTH-SERVICE có register không
   - Xem service name có đúng `AUTH-SERVICE` (viết hoa) không

2. **Kiểm tra Gateway routing:**

Gateway config:
```yaml
- id: auth-service
  uri: lb://AUTH-SERVICE
  predicates:
    - Path=/auth/**
  filters:
    - name: RewritePath
      args:
        regexp: /auth/(?<segment>.*)
        replacement: /identity/${segment}
```

**Giải pháp:**

Đảm bảo:
- Service name trong Eureka là `AUTH-SERVICE` (viết hoa)
- Gateway route đúng: `/auth/**` → `lb://AUTH-SERVICE`
- Rewrite path đúng: `/auth/xxx` → `/identity/xxx`

---

### 4. ❌ HTTPS/HTTP Mismatch

**Triệu chứng:**
- Mixed content error
- Browser block requests từ HTTPS frontend đến HTTP backend

**Kiểm tra:**

1. Frontend URL: Phải là HTTPS
2. Gateway URL: Phải là HTTPS
3. Browser console: Kiểm tra mixed content warnings

**Giải pháp:**

- Railway tự động cung cấp HTTPS cho tất cả public domains
- Đảm bảo `REACT_APP_API_URL` dùng HTTPS

---

### 5. ❌ Frontend chưa rebuild sau khi thay đổi env vars

**Triệu chứng:**
- Env vars đã set nhưng frontend vẫn dùng giá trị cũ
- Browser cache vẫn dùng build cũ

**Giải pháp:**

1. **Trigger rebuild:**
   - Railway Dashboard → Frontend Service → **Settings** → **Redeploy**
   - Hoặc push code mới để trigger rebuild

2. **Clear browser cache:**
   - Hard refresh: `Ctrl+Shift+R` (Windows) hoặc `Cmd+Shift+R` (Mac)
   - Hoặc clear browser cache

---

## 🔍 Debugging Steps

### Bước 1: Kiểm tra Browser Console

1. Mở Frontend → F12 → **Console**
2. Tìm error messages:
   - `Failed to fetch`
   - `CORS policy`
   - `Network error`
   - `Mixed content`

### Bước 2: Kiểm tra Network Tab

1. F12 → **Network**
2. Thử đăng nhập
3. Tìm request đến `/auth/auth/token`
4. Xem:
   - **Request URL**: Có đúng Gateway URL không?
   - **Status**: 200, 404, 502, CORS error?
   - **Response**: Có response không?

### Bước 3: Kiểm tra Gateway Logs

1. Railway Dashboard → **Gateway Service** → **Deployments** → Latest → **View Logs**
2. Tìm:
   - Incoming requests
   - Routing errors
   - Connection errors

### Bước 4: Kiểm tra Auth Service Logs

1. Railway Dashboard → **Auth Service** → **Deployments** → Latest → **View Logs**
2. Tìm:
   - Incoming requests từ Gateway
   - Authentication errors
   - Database connection errors

### Bước 5: Test Gateway trực tiếp

```bash
# Test Gateway health
curl https://gateway-production.up.railway.app/actuator/health

# Test Auth Service qua Gateway
curl -X POST https://gateway-production.up.railway.app/auth/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test"}'
```

---

## ✅ Checklist Khắc phục

- [ ] `REACT_APP_API_URL` đã được set trong Railway Frontend Variables
- [ ] `REACT_APP_API_URL` dùng HTTPS (không phải HTTP)
- [ ] `REACT_APP_API_URL` không có trailing slash (`/`)
- [ ] Frontend đã được rebuild sau khi set env vars
- [ ] Browser cache đã được clear
- [ ] Gateway service đang running
- [ ] Auth Service đã register vào Eureka với tên `AUTH-SERVICE`
- [ ] Gateway có route `/auth/**` đến `lb://AUTH-SERVICE`
- [ ] `CORS_ALLOWED_ORIGINS` đã được set trong Gateway Variables
- [ ] Network tab không có CORS errors
- [ ] Gateway logs không có routing errors

---

## 🎯 Quick Fix

### Nếu chưa set REACT_APP_API_URL:

1. Vào Railway Dashboard → **Frontend Service** → **Variables**
2. Thêm:
   ```
   REACT_APP_API_URL=${{Gateway.RAILWAY_PUBLIC_DOMAIN}}
   ```
3. **Redeploy** Frontend service

### Nếu CORS error:

1. Vào Railway Dashboard → **Gateway Service** → **Variables**
2. Đảm bảo có:
   ```
   CORS_ALLOWED_ORIGINS=*
   ```
3. **Redeploy** Gateway service

### Nếu Gateway không route được:

1. Kiểm tra Eureka Dashboard: AUTH-SERVICE có register không?
2. Kiểm tra service name: Phải là `AUTH-SERVICE` (viết hoa)
3. Kiểm tra Gateway logs để xem routing errors

---

## 📝 Test Commands

### Test Gateway:

```bash
curl https://[gateway-domain]/actuator/health
```

### Test Auth Service qua Gateway:

```bash
curl -X POST https://[gateway-domain]/auth/auth/token \
  -H "Content-Type: application/json" \
  -d '{"username":"your-username","password":"your-password"}'
```

### Test từ Browser Console:

```javascript
// Kiểm tra API URL
console.log('API URL:', process.env.REACT_APP_API_URL);

// Test login API
fetch('https://[gateway-domain]/auth/auth/token', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    username: 'test',
    password: 'test'
  })
})
.then(r => r.json())
.then(console.log)
.catch(console.error);
```

---

## 🎉 Sau khi khắc phục

1. ✅ Frontend có thể gọi API đến Gateway
2. ✅ Gateway route requests đến Auth Service
3. ✅ Auth Service xử lý authentication
4. ✅ Login thành công!

---

## 💡 Tips

1. **Luôn dùng HTTPS** - Railway tự động cung cấp HTTPS
2. **Kiểm tra logs** - Railway logs rất hữu ích để debug
3. **Test từng bước** - Test Gateway → Test Auth Service → Test Frontend
4. **Clear cache** - Browser cache có thể gây vấn đề
5. **Rebuild sau khi thay đổi env vars** - React build-time variables cần rebuild
