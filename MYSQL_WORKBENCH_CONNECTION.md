# 🔌 Hướng Dẫn Kết Nối MySQL Workbench với Docker Databases

## 📋 Thông Tin Kết Nối

Dự án có **6 MySQL databases** chạy trong Docker containers. Tất cả đều có cùng thông tin đăng nhập:

- **Username**: `root`
- **Password**: `123456`
- **Host**: `localhost` hoặc `127.0.0.1`

### Danh Sách Databases và Ports

| Database | Container Name | Port | Database Name |
|----------|---------------|------|---------------|
| **Auth Service** | mysql-auth | **3310** | `identity` |
| **Finance Service** | mysql-finance | **3311** | `finance` |
| **Gamification Service** | mysql-gamification | **3312** | `gamification` |
| **AI Service** | mysql-ai | **3313** | `ai_service` |
| **Learning Service** | mysql-learning | **3314** | `learning` |
| **Firebase Notification** | mysql-firebase | **3315** | `firebase` |

---

## 🚀 Cách Kết Nối MySQL Workbench

### Bước 1: Mở MySQL Workbench

Khởi động MySQL Workbench trên máy của bạn.

### Bước 2: Tạo Connection Mới

1. Click vào dấu **`+`** bên cạnh "MySQL Connections" hoặc chọn **`Database` → `Manage Connections...`**
2. Click **`+`** để thêm connection mới

### Bước 3: Điền Thông Tin Kết Nối

Điền thông tin cho **từng database** như sau:

#### 🔐 Auth Service Database

```
Connection Name: Auth Service (Identity)
Hostname: localhost
Port: 3310
Username: root
Password: 123456
Default Schema: identity
```

#### 💰 Finance Service Database

```
Connection Name: Finance Service
Hostname: localhost
Port: 3311
Username: root
Password: 123456
Default Schema: finance
```

#### 🎮 Gamification Service Database

```
Connection Name: Gamification Service
Hostname: localhost
Port: 3312
Username: root
Password: 123456
Default Schema: gamification
```

#### 🤖 AI Service Database

```
Connection Name: AI Service
Hostname: localhost
Port: 3313
Username: root
Password: 123456
Default Schema: ai_service
```

#### 📚 Learning Service Database

```
Connection Name: Learning Service
Hostname: localhost
Port: 3314
Username: root
Password: 123456
Default Schema: learning
```

#### 🔔 Firebase Notification Database

```
Connection Name: Firebase Notification
Hostname: localhost
Port: 3315
Username: root
Password: 123456
Default Schema: firebase
```

### Bước 4: Test Connection

1. Click vào nút **`Test Connection`** để kiểm tra kết nối
2. Nếu thành công, bạn sẽ thấy thông báo: **"Successfully made the MySQL connection"**
3. Click **`OK`** để lưu connection

### Bước 5: Kết Nối

1. Double-click vào connection vừa tạo trong danh sách
2. Nhập password nếu được yêu cầu: `123456`
3. Bạn sẽ thấy database schema và có thể bắt đầu query!

---

## 📸 Hình Ảnh Minh Họa

### Giao Diện MySQL Workbench Connection Setup

```
┌─────────────────────────────────────────┐
│ MySQL Workbench                         │
├─────────────────────────────────────────┤
│ MySQL Connections                       │
│                                         │
│  [+ New Connection]                    │
│                                         │
│  Connection Name: [Auth Service]       │
│  Hostname:        [localhost]          │
│  Port:            [3310]               │
│  Username:        [root]               │
│  Password:        [••••••]             │
│  Default Schema:  [identity]           │
│                                         │
│  [Test Connection]  [OK]  [Cancel]    │
└─────────────────────────────────────────┘
```

---

## ✅ Kiểm Tra Containers Đang Chạy

Trước khi kết nối, đảm bảo các MySQL containers đang chạy:

```powershell
# Windows PowerShell
docker ps | findstr mysql

# Hoặc
docker ps --filter "name=mysql"
```

Bạn sẽ thấy 6 containers:
- `mysql-auth`
- `mysql-finance`
- `mysql-gamification`
- `mysql-ai`
- `mysql-learning`
- `mysql-firebase`

Nếu không thấy containers, khởi động lại:

```powershell
cd E:\UTH\DTDM\JavaWeb\edufinai
docker-compose up -d mysql-auth mysql-finance mysql-gamification mysql-ai mysql-learning mysql-firebase
```

---

## 🔍 Troubleshooting

### Lỗi: "Cannot connect to MySQL server"

**Nguyên nhân**: Container chưa khởi động hoặc port bị chiếm.

**Giải pháp**:
1. Kiểm tra container đang chạy:
   ```powershell
   docker ps | findstr mysql-auth
   ```
2. Kiểm tra port có đang được sử dụng:
   ```powershell
   netstat -ano | findstr :3310
   ```
3. Restart container:
   ```powershell
   docker restart mysql-auth
   ```

### Lỗi: "Access denied for user 'root'@'localhost'"

**Nguyên nhân**: Sai password hoặc username.

**Giải pháp**:
- Kiểm tra lại password: `123456`
- Kiểm tra username: `root`
- Đảm bảo đang kết nối đúng port

### Lỗi: "Unknown database 'identity'"

**Nguyên nhân**: Database chưa được tạo hoặc container mới khởi động.

**Giải pháp**:
1. Đợi vài giây để container khởi động hoàn toàn
2. Kiểm tra logs:
   ```powershell
   docker logs mysql-auth
   ```
3. Kết nối lại mà không chọn Default Schema, sau đó tạo database thủ công nếu cần

### Lỗi: "Connection timeout"

**Nguyên nhân**: Firewall hoặc Docker network issue.

**Giải pháp**:
1. Kiểm tra Docker Desktop đang chạy
2. Thử kết nối với `127.0.0.1` thay vì `localhost`
3. Kiểm tra Windows Firewall không block port

---

## 💡 Tips Hữu Ích

### 1. Lưu Tất Cả Connections

Tạo tất cả 6 connections và lưu lại để dễ dàng truy cập sau này.

### 2. Sử Dụng Connection Groups

Trong MySQL Workbench, bạn có thể tạo **Connection Groups** để tổ chức:
- Tạo group: "EduFinAI Databases"
- Kéo tất cả 6 connections vào group này

### 3. Export/Import Connections

Để backup connections:
- **Export**: `Database` → `Manage Connections...` → Click connection → `Export`
- **Import**: `Database` → `Manage Connections...` → `Import`

### 4. Sử Dụng Query Tab

Sau khi kết nối, bạn có thể:
- Xem tất cả tables: Click vào database trong Navigator
- Chạy queries: Mở tab SQL Editor
- Xem data: Double-click vào table

### 5. Kiểm Tra Tables

Để xem tất cả tables trong một database:

```sql
USE identity;  -- hoặc finance, gamification, etc.
SHOW TABLES;
```

Hoặc:

```sql
SELECT TABLE_NAME 
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'identity';
```

---

## 📊 Quick Reference Table

| Service | Port | Database | Tables (ví dụ) |
|---------|------|----------|----------------|
| Auth | 3310 | identity | users, roles, permissions |
| Finance | 3311 | finance | transactions, categories, goals |
| Gamification | 3312 | gamification | challenges, rewards, badges |
| AI | 3313 | ai_service | conversations, reports |
| Learning | 3314 | learning | lessons, enrollments, learners |
| Firebase | 3315 | firebase | device_tokens, notifications |

---

## 🎯 Ví Dụ Query

### Xem tất cả users trong Auth Service:

```sql
USE identity;
SELECT * FROM users;
```

### Xem transactions trong Finance Service:

```sql
USE finance;
SELECT * FROM transactions LIMIT 10;
```

### Xem lessons trong Learning Service:

```sql
USE learning;
SELECT * FROM lessons;
```

---

## 🔐 Bảo Mật

⚠️ **Lưu ý**: 
- Password `123456` chỉ dùng cho môi trường development
- **KHÔNG** sử dụng password này trong production
- Đổi password mạnh hơn khi deploy lên môi trường thực tế

---

**Chúc bạn sử dụng MySQL Workbench thành công! 🎉**
