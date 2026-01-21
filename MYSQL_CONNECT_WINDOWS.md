# 🪟 Kết nối MySQL từ Windows Terminal

Hướng dẫn kết nối MySQL Railway từ Windows Terminal.

---

## ✅ Cách 1: Dùng MySQL Client (Command Line)

### Bước 1: Cài đặt MySQL Client trên Windows

#### Option A: Cài MySQL Server (bao gồm client)

1. Tải MySQL Installer từ: https://dev.mysql.com/downloads/installer/
2. Chọn **MySQL Installer for Windows**
3. Chọn **Custom** installation
4. Chỉ cài **MySQL Command Line Client** (không cần MySQL Server)
5. Hoặc cài **MySQL Server** (sẽ có cả client)

#### Option B: Dùng MySQL qua WSL (Windows Subsystem for Linux)

Nếu bạn đã có WSL:

```bash
# Trong WSL terminal
sudo apt update
sudo apt install mysql-client
```

#### Option C: Dùng Chocolatey (Package Manager cho Windows)

```powershell
# Mở PowerShell as Administrator
choco install mysql
```

---

### Bước 2: Kiểm tra MySQL Client đã cài chưa

Mở **Windows Terminal** hoặc **Command Prompt** hoặc **PowerShell**, chạy:

```bash
mysql --version
```

Nếu hiển thị version (ví dụ: `mysql  Ver 8.0.xx`), bạn đã cài thành công!

---

### Bước 3: Kết nối MySQL Railway

```bash
mysql -h ballast.proxy.rlwy.net -P 39516 -u root -p railway
```

Sau khi nhấn Enter, nó sẽ hỏi password:
```
Enter password: 
```

Nhập password: `UWFWdFoxQUZWUjyzIPcDSpeJnQaEDDHP`

**Lưu ý:** Password sẽ không hiển thị khi bạn gõ (bảo mật).

---

### Bước 4: Tạo các Databases cần thiết

Sau khi kết nối thành công, bạn sẽ thấy prompt:

```sql
mysql>
```

Chạy các lệnh sau để tạo databases:

```sql
CREATE DATABASE IF NOT EXISTS identity;
CREATE DATABASE IF NOT EXISTS finance;
CREATE DATABASE IF NOT EXISTS learning;
CREATE DATABASE IF NOT EXISTS gamification;
CREATE DATABASE IF NOT EXISTS ai_service;
CREATE DATABASE IF NOT EXISTS firebase;

-- Kiểm tra databases đã tạo
SHOW DATABASES;

-- Thoát khỏi MySQL
EXIT;
```

---

## ✅ Cách 2: Dùng MySQL Workbench (GUI - Dễ dùng hơn)

### Bước 1: Tải MySQL Workbench

1. Tải từ: https://dev.mysql.com/downloads/workbench/
2. Cài đặt MySQL Workbench

### Bước 2: Tạo Connection mới

1. Mở MySQL Workbench
2. Click **+** để tạo connection mới
3. Điền thông tin:

```
Connection Name: Railway MySQL
Hostname: ballast.proxy.rlwy.net
Port: 39516
Username: root
Password: UWFWdFoxQUZWUjyzIPcDSpeJnQaEDDHP
Default Schema: railway
```

4. Click **Test Connection** để kiểm tra
5. Click **OK** để lưu

### Bước 3: Kết nối và tạo Databases

1. Double-click vào connection vừa tạo
2. Mở **Query Tab** (hoặc nhấn `Ctrl+T`)
3. Chạy các lệnh SQL:

```sql
CREATE DATABASE IF NOT EXISTS identity;
CREATE DATABASE IF NOT EXISTS finance;
CREATE DATABASE IF NOT EXISTS learning;
CREATE DATABASE IF NOT EXISTS gamification;
CREATE DATABASE IF NOT EXISTS ai_service;
CREATE DATABASE IF NOT EXISTS firebase;

SHOW DATABASES;
```

---

## ✅ Cách 3: Dùng DBeaver (Free, Cross-platform)

### Bước 1: Tải DBeaver

1. Tải từ: https://dbeaver.io/download/
2. Chọn **Windows Installer**
3. Cài đặt DBeaver

### Bước 2: Tạo Connection

1. Mở DBeaver
2. Click **New Database Connection** (icon database)
3. Chọn **MySQL**
4. Điền thông tin:

```
Server Host: ballast.proxy.rlwy.net
Port: 39516
Database: railway
Username: root
Password: UWFWdFoxQUZWUjyzIPcDSpeJnQaEDDHP
```

5. Click **Test Connection**
6. Click **Finish**

### Bước 3: Tạo Databases

1. Right-click vào connection → **SQL Editor** → **New SQL Script**
2. Chạy các lệnh:

```sql
CREATE DATABASE IF NOT EXISTS identity;
CREATE DATABASE IF NOT EXISTS finance;
CREATE DATABASE IF NOT EXISTS learning;
CREATE DATABASE IF NOT EXISTS gamification;
CREATE DATABASE IF NOT EXISTS ai_service;
CREATE DATABASE IF NOT EXISTS firebase;
```

---

## ✅ Cách 4: Dùng Railway CLI (Nếu đã cài)

### Bước 1: Cài Railway CLI

```powershell
# PowerShell
iwr https://railway.app/install.ps1 | iex
```

Hoặc dùng npm:

```bash
npm install -g @railway/cli
```

### Bước 2: Login Railway

```bash
railway login
```

### Bước 3: Connect MySQL

```bash
railway connect mysql
```

Railway sẽ tự động connect đến MySQL service trong project của bạn.

---

## ✅ Cách 5: Dùng PowerShell với Invoke-Sqlcmd (Nếu có SQL Server Tools)

Nếu bạn đã cài SQL Server Management Studio hoặc SQL Server Tools:

```powershell
$password = ConvertTo-SecureString "UWFWdFoxQUZWUjyzIPcDSpeJnQaEDDHP" -AsPlainText -Force
$credential = New-Object System.Management.Automation.PSCredential("root", $password)

Invoke-Sqlcmd -ServerInstance "ballast.proxy.rlwy.net,39516" -Database "railway" -Credential $credential -Query "CREATE DATABASE IF NOT EXISTS identity;"
```

**Lưu ý:** Cách này chỉ hoạt động nếu bạn có SQL Server Tools và MySQL connector.

---

## 🎯 Khuyến nghị

### Cho người mới:
- ✅ **MySQL Workbench** - GUI dễ dùng, trực quan
- ✅ **DBeaver** - Free, hỗ trợ nhiều database

### Cho người quen command line:
- ✅ **MySQL Client** - Nhanh, nhẹ
- ✅ **Railway CLI** - Tích hợp tốt với Railway

---

## 🔍 Troubleshooting

### Lỗi: 'mysql' is not recognized

**Giải pháp:**
1. Đảm bảo MySQL đã được cài đặt
2. Thêm MySQL vào PATH:
   - Tìm MySQL installation folder (thường là `C:\Program Files\MySQL\MySQL Server 8.0\bin`)
   - Thêm vào System PATH environment variable
   - Restart terminal

### Lỗi: Access denied

**Giải pháp:**
1. Kiểm tra password có đúng không
2. Kiểm tra host có được phép remote access không
3. Thử dùng `-p` và nhập password khi được hỏi (không gõ password trực tiếp trong command)

### Lỗi: Can't connect to MySQL server

**Giải pháp:**
1. Kiểm tra internet connection
2. Kiểm tra host và port có đúng không
3. Kiểm tra firewall có block port 39516 không
4. Thử ping host: `ping ballast.proxy.rlwy.net`

### Lỗi: SSL connection error

**Giải pháp:**
Thêm `--ssl-mode=DISABLED` hoặc `--ssl-mode=REQUIRED`:

```bash
mysql -h ballast.proxy.rlwy.net -P 39516 -u root -p --ssl-mode=REQUIRED railway
```

---

## 📝 Quick Reference

### Command đầy đủ với tất cả options:

```bash
mysql -h ballast.proxy.rlwy.net -P 39516 -u root -p --ssl-mode=REQUIRED railway
```

### Command với password trực tiếp (không khuyến nghị - không an toàn):

```bash
mysql -h ballast.proxy.rlwy.net -P 39516 -u root -pUWFWdFoxQUZWUjyzIPcDSpeJnQaEDDHP railway
```

**Lưu ý:** Không có khoảng trắng giữa `-p` và password!

### Command để chạy SQL file:

```bash
mysql -h ballast.proxy.rlwy.net -P 39516 -u root -p railway < create_databases.sql
```

---

## 🎉 Hoàn thành!

Sau khi kết nối thành công và tạo các databases, bạn có thể:
- ✅ Deploy các services lên Railway
- ✅ Services sẽ tự động connect đến MySQL
- ✅ Spring Boot sẽ tự tạo tables nếu `ddl-auto=update`
