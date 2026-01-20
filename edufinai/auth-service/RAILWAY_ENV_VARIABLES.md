# Railway Environment Variables cho Auth Service

## Hướng dẫn cấu hình Environment Variables trên Railway

Khi deploy auth-service trên Railway, bạn cần thêm các biến môi trường sau vào **Settings → Variables** của auth-service project.

### 1. Database Connection (Reference từ MySQL Service)

Thêm các biến này bằng cách **Reference** từ MySQL service trên Railway:

```
MYSQL_DATABASE=${{MySQL.MYSQL_DATABASE}}
MYSQL_ROOT_PASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
MYSQL_URL=${{MySQL.MYSQL_URL}}
MYSQLDATABASE=${{MySQL.MYSQLDATABASE}}
MYSQLHOST=${{MySQL.MYSQLHOST}}
MYSQLPASSWORD=${{MySQL.MYSQLPASSWORD}}
MYSQLPORT=${{MySQL.MYSQLPORT}}
MYSQLUSER=${{MySQL.MYSQLUSER}}
```

**Hoặc nếu Railway tự động inject, bạn có thể dùng trực tiếp:**

```
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}
```

### 2. Eureka Server Connection

Thay thế `[EUREKA_SERVICE_URL]` bằng URL thực tế của Eureka service trên Railway:

```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=${{Eureka.RAILWAY_PUBLIC_DOMAIN}}/eureka/
```

**Hoặc nếu Eureka service có tên là "eureka":**

```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/
```

**Hoặc dùng private domain (nếu các service trong cùng project):**

```
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://${{Eureka.RAILWAY_PRIVATE_DOMAIN}}:${{Eureka.PORT}}/eureka/
```

### 3. Service Instance Configuration

```
EUREKA_INSTANCE_HOSTNAME=auth-service
EUREKA_INSTANCE_IP=auth-service
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false
```

### 4. JWT Configuration (Optional - có default values)

```
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
JWT_VALID_DURATION=3600
JWT_REFRESHABLE_DURATION=36000
```

### 5. Spring Profile

```
SPRING_PROFILES_ACTIVE=railway
```

### 6. Port (Railway tự động set, nhưng có thể override)

```
PORT=9000
```

---

## Cách thêm Environment Variables trên Railway

1. Vào **Railway Dashboard** → Chọn **auth-service project**
2. Vào tab **Variables**
3. Click **+ New Variable**
4. Thêm từng biến theo danh sách trên

**Lưu ý:**
- Để reference biến từ service khác, dùng format: `${{ServiceName.VARIABLE_NAME}}`
- Railway tự động inject `PORT` và `RAILWAY_*` variables
- Đảm bảo MySQL service đã được deploy trước khi deploy auth-service

---

## Example: Full Environment Variables List

```bash
# Database (Reference từ MySQL service)
MYSQLHOST=${{MySQL.RAILWAY_PRIVATE_DOMAIN}}
MYSQLPORT=3306
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=${{MySQL.MYSQL_ROOT_PASSWORD}}

# Eureka (Reference từ Eureka service)
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=https://eureka-production.up.railway.app/eureka/

# Service Instance
EUREKA_INSTANCE_HOSTNAME=auth-service
EUREKA_INSTANCE_IP=auth-service
EUREKA_SECURE_PORT_ENABLED=true
EUREKA_NON_SECURE_PORT_ENABLED=false

# Spring Profile
SPRING_PROFILES_ACTIVE=railway

# JWT (Optional)
JWT_SECRET=1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij
JWT_VALID_DURATION=3600
JWT_REFRESHABLE_DURATION=36000
```

---

## Troubleshooting

### Lỗi kết nối Database
- Kiểm tra MySQL service đã running chưa
- Kiểm tra biến `MYSQLHOST` có đúng private domain không
- Kiểm tra `MYSQLPASSWORD` có match với MySQL service không

### Lỗi kết nối Eureka
- Kiểm tra Eureka service đã running chưa
- Kiểm tra URL Eureka có đúng format không (phải có `/eureka/` ở cuối)
- Nếu dùng HTTPS, đảm bảo `EUREKA_SECURE_PORT_ENABLED=true`

### Service không register vào Eureka
- Kiểm tra `EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE` có đúng không
- Kiểm tra `register-with-eureka=true` trong config
- Xem logs của auth-service để debug
