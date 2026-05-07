# 🛒 Dự án Web Bán Hàng 1 (Spring Boot)


##  Công nghệ sử dụng
- **Backend:** Java 21, Spring Boot, Spring Data JPA, Spring Security.
- **Database:** MySQL.
- **Mapper:** MapStruct.
- **Tools:** Lombok, Docker, Docker Compose.
- **Frontend:** Thymeleaf, CSS.

##  Cấu trúc dự án
Dự án được tổ chức theo kiến trúc phân lớp (Layered Architecture):
- `Controller`: Tiếp nhận yêu cầu.
- `Service`: Xử lý logic nghiệp vụ.
- `Repository`: Truy vấn dữ liệu.
- `Entity`: Định nghĩa cấu trúc database.
- `DTO`: Vận chuyển dữ liệu bảo mật.

## Cách chạy dự án
1. Clone dự án: `git clone https://github.com/nguyennh-it/webbanhang1.git`
2. Cấu hình database trong file `application.yaml`.
3. Chạy lệnh `mvn spring-boot:run`.
