# 🔧 SỬA LỖI 503 SERVICE UNAVAILABLE - LEARNING SERVICE

## 🔍 Nguyên nhân

Lỗi **503 Service Unavailable** nghĩa là **Gateway không thể kết nối được với learning-service**. Có thể do:

1. **learning-service chưa đăng ký với Eureka** (service chưa chạy hoặc lỗi)
2. **Eureka instance configuration sai** (hostname/ip-address không đúng)
3. **Service name không match** (Gateway tìm `LEARNING-SERVICE` nhưng service đăng ký với tên khác)

## ✅ Đã sửa

### 1. Sửa Eureka Configuration
- ✅ Đổi `prefer-ip-address=false` → `prefer-ip-address=true`
- ✅ Dùng `RAILWAY_PRIVATE_DOMAIN` cho hostname và ip-address (giống các service khác)
- ✅ Đảm bảo `secure-port-enabled=false` và `non-secure-port-enabled=true`

## 🛠️ Cách kiểm tra và sửa

### Bước 1: Kiểm tra learning-service có đang chạy không

1. Vào **Railway Dashboard**
2. Chọn project của bạn
3. Tìm service **learning-service**
4. Kiểm tra:
   - Status phải là **"Active"** hoặc **"Deployed"**
   - Logs không có lỗi startup
   - Port đã được assign

### Bước 2: Kiểm tra learning-service có đăng ký với Eureka không

**Cách 1: Qua Eureka Dashboard** (nếu có)
1. Mở Eureka Dashboard (thường là `http://eureka-service-url:8761`)
2. Tìm service **LEARNING-SERVICE** trong danh sách
3. Nếu không thấy → service chưa đăng ký

**Cách 2: Qua logs của learning-service**
Tìm trong logs dòng:
```
Registering application LEARNING-SERVICE with eureka with status UP
DiscoveryClient_LEARNING-SERVICE/...: registering service...
DiscoveryClient_LEARNING-SERVICE/... - registration status: 204
```

Nếu thấy `registration status: 204` → đăng ký thành công.

### Bước 3: Kiểm tra Gateway có tìm thấy learning-service không

**Qua logs của Gateway**, tìm:
```
LoadBalancerClientFilter: LoadBalancerClientFilter executed
```

Nếu thấy lỗi:
```
LoadBalancerClientFilter: No instances available for LEARNING-SERVICE
```
→ Gateway không tìm thấy learning-service trong Eureka.

### Bước 4: Kiểm tra Eureka instance configuration

Trong logs của learning-service, tìm dòng:
```
DiscoveryClient_LEARNING-SERVICE/127.0.0.1:8080: registering service...
```

**Nếu thấy `127.0.0.1`** → **SAI!** Phải là Railway private domain.

**Nếu thấy Railway private domain** (ví dụ: `*.railway.internal`) → **ĐÚNG!**

## 🔧 Cách sửa

### Nếu learning-service chưa đăng ký với Eureka:

1. **Redeploy learning-service** với code mới (đã sửa Eureka config)
2. **Check logs** sau khi deploy để verify:
   ```
   Registering application LEARNING-SERVICE with eureka with status UP
   registration status: 204
   ```

### Nếu learning-service đăng ký với 127.0.0.1:

1. **Đảm bảo** Railway có set biến môi trường `RAILWAY_PRIVATE_DOMAIN` cho learning-service
2. **Redeploy** learning-service
3. **Check logs** để verify hostname/ip-address đã đúng

### Nếu vẫn không được:

1. **Kiểm tra Eureka có đang chạy không**
   - Eureka phải chạy trước các service khác
   - Check logs của Eureka

2. **Kiểm tra network connectivity**
   - learning-service có thể kết nối đến Eureka không?
   - Gateway có thể kết nối đến Eureka không?

3. **Kiểm tra service name**
   - Gateway tìm: `LEARNING-SERVICE` (uppercase)
   - learning-service đăng ký: `learning-service` (lowercase)
   - Spring Cloud sẽ tự động convert, nhưng nếu có vấn đề, thử đổi `spring.application.name=LEARNING-SERVICE` (uppercase)

## 📝 Logs mẫu khi thành công

### learning-service logs:
```
Registering application LEARNING-SERVICE with eureka with status UP
DiscoveryClient_LEARNING-SERVICE/railway-private-domain:8080: registering service...
DiscoveryClient_LEARNING-SERVICE/railway-private-domain:8080 - registration status: 204
```

### Gateway logs (khi có request):
```
LoadBalancerClientFilter: LoadBalancerClientFilter executed
Route matched: learning-service
LoadBalancerClientFilter: LoadBalancerClientFilter executed with route: learning-service
```

## ⚠️ Lưu ý quan trọng

1. **Thứ tự khởi động services**:
   - Eureka phải chạy trước
   - Sau đó các service khác (gateway, learning-service, etc.)

2. **Sau khi sửa config**, phải **redeploy** service để áp dụng thay đổi

3. **Nếu vẫn lỗi 503**, check:
   - learning-service có đang chạy không?
   - Eureka có đang chạy không?
   - Network connectivity giữa services

## 🚀 Checklist

- [ ] learning-service đang chạy trên Railway
- [ ] learning-service đã đăng ký với Eureka (check logs)
- [ ] Eureka instance config dùng RAILWAY_PRIVATE_DOMAIN (không phải 127.0.0.1)
- [ ] Gateway có thể kết nối đến Eureka
- [ ] Service name match: Gateway tìm `LEARNING-SERVICE`, service đăng ký `learning-service` (Spring Cloud tự convert)

---

**Sau khi deploy code mới, nếu vẫn lỗi 503, vui lòng gửi logs từ learning-service và gateway để mình debug tiếp.**
