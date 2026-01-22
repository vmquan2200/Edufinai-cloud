# 🔧 HƯỚNG DẪN SỬA LỖI: CREATOR BỊ GÁN QUYỀN MOD

## 🔍 Nguyên nhân
Khi đăng nhập bằng tài khoản Creator, hệ thống lại cấp quyền Mod. Vấn đề có thể do:
1. **Database**: User trong database đang có role `MOD` thay vì `CREATOR`
2. **Code bug**: Code tạo/update user có lỗi mapping role

## ✅ Đã sửa trong code

### 1. Thêm logging để debug
- ✅ Logging trong `createUserByAdmin()` - log role khi tạo user
- ✅ Logging trong `getMyInfo()` - log role khi lấy thông tin user
- ✅ Logging trong `buildScope()` - log scope trong JWT token

### 2. Thêm endpoint để fix role
- ✅ Endpoint `PATCH /users/{userId}/role` - Admin có thể update role của user

## 🛠️ Cách sửa lỗi

### Cách 1: Sửa qua API (Khuyến nghị)

**Bước 1**: Đăng nhập bằng tài khoản Admin

**Bước 2**: Gọi API để update role của user Creator:

```bash
PATCH https://gateway-production-b350.up.railway.app/auth/users/{userId}/role
Authorization: Bearer <admin-jwt-token>
Content-Type: application/json

{
  "role": "CREATOR"
}
```

**Lưu ý**: Thay `{userId}` bằng ID của user Creator cần sửa.

### Cách 2: Sửa trực tiếp trong Database (Nếu có quyền truy cập)

**Bước 1**: Kết nối vào MySQL database của auth-service trên Railway

**Bước 2**: Chạy SQL query:

```sql
-- Tìm user_id của user Creator
SELECT id, username, email FROM users WHERE username = 'your-creator-username';

-- Xóa role MOD (nếu có)
DELETE FROM user_roles WHERE user_id = 'user-id-here' AND role_id = 'MOD';

-- Thêm role CREATOR
INSERT INTO user_roles (user_id, role_id) 
VALUES ('user-id-here', 'CREATOR')
ON DUPLICATE KEY UPDATE role_id = 'CREATOR';
```

**Lưu ý**: 
- Thay `'your-creator-username'` bằng username của user Creator
- Thay `'user-id-here'` bằng ID thực tế của user

### Cách 3: Tạo lại user Creator (Nếu không thể sửa)

**Bước 1**: Xóa user Creator cũ (qua Admin Dashboard hoặc API)

**Bước 2**: Tạo lại user với role CREATOR:

```bash
POST https://gateway-production-b350.up.railway.app/auth/users/admin/users
Authorization: Bearer <admin-jwt-token>
Content-Type: application/json

{
  "username": "creator_username",
  "password": "password",
  "firstName": "Creator",
  "lastName": "Name",
  "email": "creator@example.com",
  "role": "CREATOR"
}
```

## 🔍 Kiểm tra sau khi sửa

### 1. Kiểm tra trong Database
```sql
SELECT u.id, u.username, r.name as role_name
FROM users u
LEFT JOIN user_roles ur ON u.id = ur.user_id
LEFT JOIN roles r ON ur.role_id = r.name
WHERE u.username = 'your-creator-username';
```

Kết quả phải có `role_name = 'CREATOR'`, không phải `'MOD'`.

### 2. Kiểm tra qua API
```bash
GET https://gateway-production-b350.up.railway.app/auth/users/my-info
Authorization: Bearer <creator-jwt-token>
```

Response phải có:
```json
{
  "result": {
    "id": "...",
    "username": "...",
    "roles": [
      {
        "name": "CREATOR"
      }
    ]
  }
}
```

### 3. Kiểm tra JWT Token
Decode JWT token và kiểm tra claim `scope`:
- Phải có `ROLE_CREATOR`
- Không được có `ROLE_MOD`

Bạn có thể decode JWT tại: https://jwt.io

## 📝 Log để debug

Sau khi deploy code mới, check logs của auth-service trên Railway:

1. **Khi tạo user Creator**:
   ```
   Creating user with role: CREATOR
   User created with roles: [CREATOR]
   User saved with ID: ..., roles: [CREATOR]
   ```

2. **Khi đăng nhập**:
   ```
   Building scope for user: creator_username, roles: [CREATOR]
   Added role to scope: ROLE_CREATOR
   Final scope for user creator_username: ROLE_CREATOR
   ```

3. **Khi lấy thông tin user**:
   ```
   getMyInfo for user: creator_username, roles: [CREATOR]
   ```

Nếu thấy log có `MOD` thay vì `CREATOR`, vấn đề là trong database.

## ⚠️ Lưu ý quan trọng

1. **Sau khi sửa role trong database**, user cần **đăng xuất và đăng nhập lại** để JWT token mới được tạo với role đúng.

2. **Nếu vẫn còn lỗi**, check:
   - User có nhiều roles không? (có thể có cả CREATOR và MOD)
   - JWT token cũ vẫn còn trong localStorage? (cần clear và login lại)

3. **Để tránh lỗi tương tự trong tương lai**:
   - Luôn verify role sau khi tạo user
   - Check logs khi tạo user mới
   - Test đăng nhập sau khi tạo user

## 🚀 Deploy code mới

Sau khi sửa code, cần:

1. **Commit và push code**:
   ```bash
   git add .
   git commit -m "Fix: Add logging and endpoint to fix creator role issue"
   git push
   ```

2. **Redeploy auth-service trên Railway**

3. **Test lại**:
   - Tạo user Creator mới
   - Đăng nhập và verify role
   - Check logs

---

**Nếu vẫn còn vấn đề, vui lòng gửi logs từ auth-service để debug tiếp.**
