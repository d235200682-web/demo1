# 🛍️ TechShop – Website bán hàng (Java Spring Boot)

Java 17 + Spring Boot 3 + JPA + H2 (file DB) + giao diện HTML/JS thuần. Không cần cài DB.

## Chạy trên VS Code
1. Cài **JDK 17+**, **Maven**, và extension *Extension Pack for Java* + *Spring Boot Extension Pack*.
2. Mở thư mục `shop` bằng VS Code.
3. Mở terminal: `mvn spring-boot:run` (hoặc mở `App.java` → **Run**).
4. Truy cập http://localhost:8080

Tài khoản mẫu: `admin/admin123` (Admin), `nhanvien/nv123456` (Nhân viên), `khach/khach123` (Khách hàng).
Console DB: dữ liệu lưu ở thư mục `data/`.

## Đẩy lên GitHub
```bash
git init && git add . && git commit -m "TechShop"
git branch -M main
git remote add origin https://github.com/<user>/<repo>.git
git push -u origin main
```

## Phân quyền
| Chức năng | Khách | Nhân viên | Admin |
|---|:-:|:-:|:-:|
| Xem/tìm sản phẩm, giỏ hàng | ✅ | ✅ | ✅ |
| Đặt hàng, xem lịch sử, hủy đơn chờ xác nhận | ✅ | ✅ | ✅ |
| Thêm/sửa sản phẩm | ❌ | ✅ | ✅ |
| Xóa sản phẩm | ❌ | ❌ | ✅ |
| Xem/cập nhật trạng thái mọi đơn | ❌ | ✅ | ✅ |
| Thêm/sửa/khóa tài khoản, đổi vai trò | ❌ | ❌ | ✅ |

## Hướng dẫn sử dụng
- **Khách:** Đăng ký → chọn danh mục/tìm kiếm → *Thêm vào giỏ* → tab *Giỏ* chỉnh số lượng/xóa → nhập thông tin giao hàng, chọn thanh toán → *Xác nhận*. Xem lại ở tab *Đơn hàng*.
- **Nhân viên/Admin:** tab *Quản trị* để quản lý sản phẩm; tab *Đơn hàng* đổi trạng thái (Chờ xác nhận → Đang giao → Đã giao / Đã hủy).
- **Admin:** mục *Quản lý tài khoản* để thêm, sửa tên, đổi vai trò, khóa/mở khóa (tài khoản bị khóa không đăng nhập được, phiên đang mở bị vô hiệu).

## Kiểm thử
Chạy `mvn test` (JUnit + MockMvc): sai mật khẩu, truy cập trái quyền (401/403), dữ liệu sản phẩm sai, giỏ hàng rỗng.
Checklist thủ công: đăng ký trùng tên → báo lỗi; đặt vượt tồn kho → báo lỗi; hủy đơn → hoàn lại tồn kho; khóa tài khoản → không đăng nhập được.

## Tối ưu & bảo mật
- Index cột `category`, truy vấn lọc/tìm kiếm ở DB; mật khẩu băm BCrypt; giá đơn hàng tính lại phía server; kiểm tra dữ liệu ở backend; escape HTML chống XSS; `@Transactional` cho đặt hàng/hủy đơn.

## API chính
`POST /api/register|login|logout`, `GET /api/me`, `GET|POST|PUT|DELETE /api/products`, `GET|POST|PUT /api/users`, `PUT /api/users/{id}/lock`, `POST|GET /api/orders`, `PUT /api/orders/{id}/status`.

## Xử lý sự cố
- `mvn` không nhận ra → cài Maven và thêm vào PATH (kiểm tra bằng `mvn -v`, cần Java 17+: `java -version`).
- Cổng 8080 bị chiếm → đổi `server.port` trong `application.properties`.
- Lỗi file DB bị khóa → tắt app đang chạy, hoặc xóa thư mục `data/`.
