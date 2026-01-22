# 🔧 DEBUG LỖI 500 KHI TẠO BÀI HỌC

## ✅ Đã sửa

### 1. Sửa URL Auth Service
- ✅ Đổi từ `http://auth-service` → `http://AUTH-SERVICE` (service discovery)

### 2. Thêm logging chi tiết
- ✅ Log từng bước trong quá trình tạo lesson
- ✅ Log lỗi với stack trace đầy đủ
- ✅ Log validation errors

### 3. Cải thiện error handling
- ✅ Validate tất cả required fields
- ✅ Handle database constraint violations
- ✅ Handle mapping errors

## 🔍 Cách kiểm tra logs trên Railway

### Bước 1: Mở logs của learning-service
1. Vào Railway Dashboard
2. Chọn project của bạn
3. Chọn service **learning-service**
4. Click tab **Deployments** hoặc **Logs**

### Bước 2: Tìm logs khi tạo lesson
Sau khi thử tạo lesson, tìm các dòng log bắt đầu bằng:
- `Creating lesson with title: ...`
- `Step 1: Getting user info...`
- `Step 2: Getting or creating creator...`
- `Step 3: Mapping request to entity...`
- `Step 4: Generating slug...`
- `Step 5: Setting timestamps...`
- `Step 6: Validating lesson...`
- `Step 7: Saving lesson to database...`
- `Step 8: Mapping lesson to response...`

### Bước 3: Xác định bước nào bị lỗi
Log sẽ cho biết chính xác bước nào fail:

#### Nếu lỗi ở Step 1:
```
Step 1 failed: Error calling auth-service: ...
```
**Nguyên nhân**: Không kết nối được auth-service hoặc JWT token không hợp lệ
**Giải pháp**: 
- Kiểm tra auth-service đang chạy
- Kiểm tra JWT token có được forward đúng không
- Kiểm tra Eureka service discovery

#### Nếu lỗi ở Step 2:
```
Step 2 failed: Creator not found: ...
```
**Nguyên nhân**: Không tạo được creator record
**Giải pháp**: Kiểm tra database connection

#### Nếu lỗi ở Step 3:
```
Step 3 failed: Error mapping request to entity: ...
```
**Nguyên nhân**: 
- JSON parsing error (quizJson)
- Missing required fields
- Invalid data format
**Giải pháp**: Kiểm tra request body từ frontend

#### Nếu lỗi ở Step 7:
```
Step 7 failed: Error saving lesson to database: ...
Database constraint violation: ...
```
**Nguyên nhân**: 
- Slug đã tồn tại (unique constraint)
- Foreign key violation (creator_id không tồn tại)
- Null constraint violation
- Data too long for column
**Giải pháp**: 
- Kiểm tra database schema
- Kiểm tra dữ liệu gửi lên

## 📋 Checklist debug

### 1. Kiểm tra request từ frontend
```javascript
// Trong CreateLessonPage.jsx, log request trước khi gửi:
console.log('Request data:', {
  title: formData.title,
  description: formData.description,
  content: formData.content,
  difficulty: formData.difficulty,
  tags: formData.tags,
  durationMinutes: formData.durationMinutes,
  quizJson: formData.quizJson
});
```

### 2. Kiểm tra JWT token
- Token có được gửi trong header `Authorization: Bearer <token>`?
- Token có hợp lệ không? (decode tại https://jwt.io)
- Token có claim `SCOPE_ROLE_CREATOR` không?

### 3. Kiểm tra database
```sql
-- Kiểm tra bảng lesson có tồn tại không
SHOW TABLES LIKE 'lesson';

-- Kiểm tra schema
DESCRIBE lesson;

-- Kiểm tra creator có tồn tại không
SELECT * FROM creator WHERE creator_id = 'your-creator-id';

-- Kiểm tra slug đã tồn tại chưa
SELECT slug FROM lesson WHERE slug = 'your-slug';
```

### 4. Kiểm tra service discovery
- Eureka đang chạy?
- learning-service đã đăng ký với Eureka?
- AUTH-SERVICE đã đăng ký với Eureka?
- learning-service có thể resolve AUTH-SERVICE không?

## 🐛 Các lỗi thường gặp và cách sửa

### Lỗi 1: "Error calling auth-service my-info"
**Nguyên nhân**: Không kết nối được auth-service
**Giải pháp**:
1. Kiểm tra auth-service đang chạy
2. Kiểm tra Eureka service discovery
3. Kiểm tra network connectivity giữa services

### Lỗi 2: "User info is null or missing ID"
**Nguyên nhân**: JWT token không hợp lệ hoặc không có quyền
**Giải pháp**:
1. Đăng xuất và đăng nhập lại
2. Kiểm tra user có role CREATOR không
3. Kiểm tra JWT token có được forward đúng không

### Lỗi 3: "Database constraint violation"
**Nguyên nhân**: 
- Slug đã tồn tại
- Foreign key violation
- Null constraint
**Giải pháp**:
1. Kiểm tra slug có unique không
2. Kiểm tra creator_id có tồn tại trong bảng creator không
3. Kiểm tra tất cả required fields có giá trị không

### Lỗi 4: "Error mapping request to entity"
**Nguyên nhân**: 
- quizJson format không đúng
- Missing required fields
**Giải pháp**:
1. Kiểm tra quizJson có phải valid JSON không
2. Kiểm tra tất cả required fields có được gửi lên không

## 📝 Log mẫu khi thành công

```
Creating lesson with title: Bài học về tài chính
Step 1: Getting user info from auth-service
Step 1 completed: User info retrieved, creator ID: abc-123-def
Step 2: Getting or creating creator
Step 2 completed: Creator retrieved/created: abc-123-def
Step 3: Mapping request to entity
Step 3 completed: Entity mapped successfully
Step 4: Generating slug
Step 4 completed: Lesson slug generated: bai-hoc-ve-tai-chinh
Step 5: Setting timestamps and status
Step 5 completed: Timestamps and status set
Step 6: Validating lesson
Step 6 completed: Lesson validation passed
Step 7: Saving lesson to database
LessonService.create() called for lesson with title: Bài học về tài chính
Lesson validation passed. Calculating total questions from quizJson
Total questions calculated: 5
Saving lesson to database...
Lesson saved successfully with ID: xyz-789-ghi
Step 7 completed: Lesson saved successfully with ID: xyz-789-ghi
Step 8: Mapping lesson to response
Step 8 completed: Response mapped successfully
Lesson creation completed successfully. Lesson ID: xyz-789-ghi
```

## 🚀 Sau khi deploy code mới

1. **Redeploy learning-service** trên Railway
2. **Test lại** tạo lesson
3. **Check logs** để xem bước nào fail
4. **Gửi logs** cho mình nếu vẫn còn lỗi

---

**Nếu vẫn còn lỗi, vui lòng copy toàn bộ logs từ Railway và gửi cho mình để debug tiếp.**
