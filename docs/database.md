Thiết kế và xây dựng hệ thống Backend E-Commerce bán thiết bị điện tử bằng Spring Boot 3, Java 21, tuân thủ chính xác schema PostgreSQL trên Supabase đã được cung cấp. Tuyệt đối không tự ý thay đổi tên bảng, tên cột, kiểu dữ liệu, quan hệ hoặc cấu trúc database hiện có.

# Yêu cầu bắt buộc

* Database sử dụng Supabase PostgreSQL.
* Mapping Entity phải khớp 100% với schema SQL hiện tại.
* Không tạo migration làm thay đổi schema.
* Chỉ sử dụng Hibernate với chế độ validate.
* Tất cả Entity phải map đúng tên bảng, cột, khóa chính, khóa ngoại.
* Sử dụng UUID cho toàn bộ bảng UUID.
* Tương thích hoàn toàn với dữ liệu hiện có.

Cấu hình bắt buộc:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate
```

# Kiến trúc dự án

* Java 21
* Spring Boot 3.x
* Spring Security
* Spring Data JPA
* PostgreSQL Driver
* Flyway (chỉ quản lý seed data, không tạo schema)
* Redis
* MapStruct
* Lombok
* Validation
* OpenFeign
* Micrometer
* Testcontainers

# Cấu trúc thư mục

src/main/java/com/electronics/
├── common/
├── config/
├── security/
├── modules/
│   ├── product/
│   ├── category/
│   ├── brand/
│   ├── inventory/
│   ├── customer/
│   ├── order/
│   ├── promotion/
│   ├── coupon/
│   ├── warranty/
│   ├── review/
│   ├── shipping/
│   └── admin/

# Quy tắc Mapping Entity

* Luôn dùng @Table(name = "...").
* Luôn dùng @Column(name = "...").
* UUID dùng kiểu java.util.UUID.
* JSONB dùng JsonNode hoặc Map<String, Object>.
* TEXT[] dùng List<String>.
* TIMESTAMP WITH TIME ZONE dùng OffsetDateTime.
* DECIMAL dùng BigDecimal.

Ví dụ:

```java
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "sku_base", nullable = false, unique = true)
    private String skuBase;

    @Column(name = "base_price", nullable = false)
    private BigDecimal basePrice;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "specifications", columnDefinition = "jsonb")
    private JsonNode specifications;

    @Column(name = "images", columnDefinition = "text[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> images;
}
```

# Các Module cần xây dựng

* Product Management
* Category Management
* Brand Management
* SKU Management
* Store Inventory
* Customer Management
* Wishlist
* Promotions
* Coupons
* Orders
* Warranty
* Reviews
* Shipping
* Admin RBAC
* Audit Logs

# Repository Requirements

* Mỗi Entity phải có JpaRepository riêng.
* Sử dụng Specification cho filtering.
* Hỗ trợ phân trang, sắp xếp, tìm kiếm.

# Service Layer

* Áp dụng Transaction hợp lý.
* Xử lý business logic tách biệt.
* Validation đầy đủ.
* Exception handling chuẩn REST.

# API Standards

* RESTful chuẩn.
* Response thống nhất:

```json
{
  "success": true,
  "message": "Success",
  "data": {},
  "timestamp": "2026-04-27T17:00:00Z"
}
```

# Security

* JWT Authentication.
* Role-based Authorization.
* Permission-based Access Control.
* Tích hợp Supabase JWT verification.

# Admin Module

* Quản lý sản phẩm
* Quản lý đơn hàng
* Quản lý khách hàng
* Quản lý kho
* Quản lý bảo hành
* Quản lý khuyến mãi
* Quản lý đánh giá
* Quản lý nhân viên
* Phân quyền động

# Tối ưu hiệu năng

* Redis Cache cho Product, Category, Brand.
* Query tối ưu bằng EntityGraph.
* Phân trang server-side.
* Projection cho danh sách lớn.

# Testing

* Unit Test: JUnit 5 + Mockito.
* Integration Test: Testcontainers PostgreSQL.
* API Test đầy đủ.

# Quy tắc quan trọng

* Không sử dụng schema tự sinh.
* Không tạo bảng mới ngoài schema được cung cấp.
* Không đổi tên field.
* Không thêm cột tùy ý.
* Tất cả code phải bám sát Supabase SQL hiện tại.

# Mục tiêu đầu ra

* Source code production-ready.
* Clean Architecture.
* Domain-Driven Design.
* Dễ mở rộng.
* Dễ bảo trì.
* Sẵn sàng kết nối NestJS BFF và Next.js Frontend.

Hãy sinh toàn bộ source code Spring Boot theo đúng schema Supabase đã cung cấp, đảm bảo mapping chính xác tuyệt đối và sẵn sàng chạy ngay.
