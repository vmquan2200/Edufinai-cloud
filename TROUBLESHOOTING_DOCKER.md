# 🔧 Troubleshooting Docker - EduFinAI

## ❌ Lỗi: "The system cannot find the file specified" khi chạy docker-compose

### Nguyên nhân:
**Docker Desktop chưa được khởi động hoặc Docker daemon chưa sẵn sàng.**

### Cách khắc phục:

#### Bước 1: Kiểm tra Docker Desktop đang chạy

1. **Mở Docker Desktop:**
   - Tìm "Docker Desktop" trong Start Menu
   - Hoặc click vào icon Docker ở system tray (góc dưới bên phải)

2. **Đợi Docker Desktop khởi động hoàn toàn:**
   - Icon Docker ở system tray phải có màu **xanh** (không phải màu vàng/đỏ)
   - Trong Docker Desktop phải hiển thị "Docker Desktop is running"

#### Bước 2: Kiểm tra Docker hoạt động

Mở PowerShell/CMD và chạy:

```bash
# Kiểm tra Docker version
docker --version

# Kiểm tra Docker daemon đang chạy
docker info
```

Nếu lệnh `docker info` thành công (không có lỗi), Docker đã sẵn sàng.

#### Bước 3: Chạy lại docker-compose

```bash
cd edufinai
docker-compose up -d --build
```

---

## ⚠️ Warning: "the attribute `version` is obsolete"

Đây chỉ là **warning**, không phải lỗi. Docker Compose mới không cần `version` nữa.

**Đã fix:** File `docker-compose.yml` đã được cập nhật để bỏ dòng `version: "3.9"`.

---

## 🔍 Các Lỗi Docker Thường Gặp

### 1. Docker Desktop không khởi động được

**Triệu chứng:** Click vào Docker Desktop nhưng không mở hoặc bị treo.

**Giải pháp:**
- Restart máy tính
- Kiểm tra Windows WSL2 đã được enable chưa:
  ```powershell
  wsl --status
  ```
- Nếu chưa có WSL2, cài đặt:
  ```powershell
  wsl --install
  ```
- Sau đó restart máy và thử lại

### 2. "Port is already allocated"

**Triệu chứng:** `Bind for 0.0.0.0:8080 failed: port is already allocated`

**Giải pháp:**

Tìm process đang dùng port:
```powershell
# Windows PowerShell
netstat -ano | findstr :8080
```

Kill process:
```powershell
taskkill /PID <PID> /F
```

Hoặc đổi port trong `docker-compose.yml`:
```yaml
ports:
  - "8081:8080"  # Thay vì 8080:8080
```

### 3. "Cannot connect to the Docker daemon"

**Triệu chứng:** `Cannot connect to the Docker daemon at unix:///var/run/docker.sock`

**Giải pháp:**
- Đảm bảo Docker Desktop đang chạy
- Restart Docker Desktop
- Kiểm tra Docker Desktop settings → General → "Use the WSL 2 based engine" đã được bật

### 4. "Out of memory" hoặc containers bị kill

**Triệu chứng:** Containers tự động dừng hoặc bị kill.

**Giải pháp:**
- Tăng memory limit trong Docker Desktop:
  - Settings → Resources → Advanced
  - Tăng Memory lên ít nhất 4GB (khuyến nghị 8GB)
  - Apply & Restart

### 5. Build failed: "mvn clean package" error

**Triệu chứng:** Build Docker image thất bại ở bước Maven.

**Giải pháp:**
- Kiểm tra internet connection (Maven cần download dependencies)
- Xóa cache và rebuild:
  ```bash
  docker-compose build --no-cache auth-service
  ```
- Kiểm tra logs chi tiết:
  ```bash
  docker-compose build auth-service 2>&1 | tee build.log
  ```

### 6. Services không kết nối được với nhau

**Triệu chứng:** Services không thể giao tiếp qua Docker network.

**Giải pháp:**
- Kiểm tra tất cả services cùng network:
  ```bash
  docker network inspect edufinai_edufin-net
  ```
- Restart tất cả services:
  ```bash
  docker-compose restart
  ```
- Kiểm tra service names trong `docker-compose.yml` phải đúng

---

## 🛠️ Script Kiểm Tra Docker

Chạy script này để kiểm tra Docker trước khi chạy project:

```bash
cd edufinai
check-docker.bat
```

Script sẽ kiểm tra:
- ✅ Docker đã cài đặt chưa
- ✅ Docker Desktop đang chạy chưa
- ✅ Docker Compose có sẵn không

---

## 📋 Checklist Trước Khi Chạy

- [ ] Docker Desktop đã được cài đặt
- [ ] Docker Desktop đang chạy (icon xanh ở system tray)
- [ ] `docker info` chạy thành công
- [ ] Ports 8080, 8761, 3000, 3310-3315, 6379 chưa bị chiếm
- [ ] Đủ RAM (ít nhất 4GB, khuyến nghị 8GB)
- [ ] Đủ disk space (ít nhất 10GB)

---

## 💡 Tips

1. **Luôn đợi Docker Desktop khởi động hoàn toàn** trước khi chạy docker-compose
2. **Kiểm tra icon Docker ở system tray** - màu xanh = OK, màu vàng/đỏ = có vấn đề
3. **Xem logs** khi có lỗi: `docker-compose logs -f <service-name>`
4. **Restart Docker Desktop** nếu gặp lỗi kỳ lạ
5. **Kiểm tra Windows WSL2** nếu Docker Desktop không khởi động được

---

## 🆘 Vẫn Không Giải Quyết Được?

1. Restart máy tính
2. Reinstall Docker Desktop
3. Kiểm tra Windows version (cần Windows 10/11 64-bit)
4. Kiểm tra Virtualization đã được bật trong BIOS

---

**Chúc bạn thành công! 🚀**
