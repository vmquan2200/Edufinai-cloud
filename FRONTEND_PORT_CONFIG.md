# 🔌 Frontend Port Configuration trên Railway

Hướng dẫn cấu hình port cho Frontend khi generate service domain trên Railway.

---

## ✅ Câu trả lời ngắn gọn

**Port cho Frontend: `80`**

Railway sẽ tự động detect từ Dockerfile (`EXPOSE 80`).

---

## 📋 Chi tiết

### Port trong Dockerfile:

```dockerfile
EXPOSE 80
```

### Port trong nginx.conf:

```nginx
listen ${PORT:-80};
```

- Nginx sẽ listen trên port từ environment variable `PORT`
- Nếu không có `PORT`, mặc định là `80`
- Railway tự động inject `PORT` variable

---

## 🎯 Khi Generate Service Domain trên Railway

Khi Railway hỏi: **"Enter the port your app is listening on"**

**Trả lời: `80`**

Hoặc để trống - Railway sẽ tự động detect từ Dockerfile.

---

## 🔧 Cách Railway xử lý Port

### 1. Railway tự động detect:

Railway sẽ:
1. Đọc `EXPOSE 80` từ Dockerfile
2. Tự động set `PORT=80` environment variable
3. Nginx sẽ listen trên port đó

### 2. Nếu Railway assign port khác:

Railway có thể assign port khác (ví dụ: `3000`, `8080`), nhưng:
- Nginx config đã được cập nhật để dùng `${PORT}` từ env
- Railway sẽ tự động inject `PORT` variable
- Nginx sẽ tự động listen trên port đó

---

## ✅ Kiểm tra Port

### Cách 1: Xem trong Railway Dashboard

1. Vào Frontend Service → **Settings** → **Variables**
2. Tìm `PORT` variable
3. Giá trị sẽ là port mà Railway assign (thường là `80` hoặc port khác)

### Cách 2: Xem trong Logs

1. Vào Frontend Service → **Deployments** → Latest → **View Logs**
2. Tìm dòng: `Listening on port ...`

### Cách 3: Test Health Check

```bash
curl https://[frontend-domain]/health
```

---

## 🎯 Quick Reference

| Câu hỏi | Trả lời |
|---------|---------|
| **Port cho Frontend?** | `80` |
| **Railway có tự detect không?** | ✅ Có, từ Dockerfile |
| **Có cần set thủ công không?** | ❌ Không, Railway tự động |
| **Nginx listen trên port nào?** | `${PORT:-80}` (từ env, mặc định 80) |

---

## 💡 Lưu ý

1. **Railway tự động detect** - Không cần set thủ công
2. **Nginx đã được config** - Tự động dùng PORT từ env
3. **Public domain** - Railway tự động map port 80 đến public domain
4. **HTTPS** - Railway tự động cung cấp HTTPS cho public domain

---

## 🎉 Kết luận

**Khi Railway hỏi port: Nhập `80` hoặc để trống**

Railway sẽ tự động detect và cấu hình đúng!
