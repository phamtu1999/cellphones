Xây dựng một nền tảng thương mại điện tử bán thiết bị điện tử cao cấp tương tự CellphoneS, sử dụng kiến trúc BFF (Backend For Frontend) với hệ thống quản trị Admin riêng biệt, kết nối Supabase PostgreSQL.

# Kiến trúc hệ thống

* Frontend khách hàng: Next.js 15, TypeScript, TailwindCSS, Shadcn/UI, Zustand, TanStack Query.
* Admin Dashboard: Next.js 15, TypeScript, TailwindCSS, Shadcn/UI, React Hook Form, TanStack Table, ECharts.
* BFF Layer: NestJS, Redis, JWT, Rate Limiting.
* Core Backend: Spring Boot 3, Java 21, Spring Security, JPA, Redis, Kafka.
* Database: Supabase PostgreSQL.
* Storage: Supabase Storage.
* Authentication: Supabase Auth + RBAC.

# Phân hệ ứng dụng

## 1. Customer Web

* Trang chủ
* Danh mục sản phẩm
* Chi tiết sản phẩm
* Giỏ hàng
* Thanh toán
* Tài khoản cá nhân
* Theo dõi đơn hàng
* Wishlist
* Đánh giá sản phẩm

## 2. Admin Dashboard

* Dashboard thống kê doanh thu
* Quản lý sản phẩm
* Quản lý danh mục
* Quản lý thương hiệu
* Quản lý kho hàng
* Quản lý đơn hàng
* Quản lý khách hàng
* Quản lý đánh giá
* Quản lý mã giảm giá
* Quản lý banner
* Quản lý Flash Sale
* Quản lý nội dung CMS
* Quản lý nhân viên
* Phân quyền Role/Permission
* Nhật ký hoạt động (Audit Logs)
* Báo cáo doanh thu
* Cấu hình hệ thống

# Phân quyền hệ thống

* SUPER_ADMIN
* ADMIN
* MANAGER
* STAFF
* CUSTOMER

# Chức năng từng vai trò

* SUPER_ADMIN: Toàn quyền hệ thống.
* ADMIN: Quản trị toàn bộ nghiệp vụ.
* MANAGER: Quản lý đơn hàng, sản phẩm, báo cáo.
* STAFF: Xử lý đơn hàng, hỗ trợ khách hàng.
* CUSTOMER: Mua hàng.

# API BFF

* /api/home
* /api/products
* /api/categories
* /api/cart
* /api/checkout
* /api/orders
* /api/profile

# API Admin

* /api/admin/dashboard
* /api/admin/products
* /api/admin/categories
* /api/admin/brands
* /api/admin/inventory
* /api/admin/orders
* /api/admin/customers
* /api/admin/reviews
* /api/admin/coupons
* /api/admin/banners
* /api/admin/flash-sales
* /api/admin/cms
* /api/admin/users
* /api/admin/roles
* /api/admin/audit-logs
* /api/admin/reports
* /api/admin/settings

# Database bổ sung

Các bảng cần thêm cho Admin:

* roles
* permissions
* role_permissions
* admin_users
* audit_logs
* system_settings
* cms_pages
* flash_sales
* flash_sale_items
* banners
* notifications

# Dashboard Widgets

* Tổng doanh thu hôm nay
* Đơn hàng mới
* Người dùng mới
* Sản phẩm bán chạy
* Tồn kho thấp
* Biểu đồ doanh thu
* Top khách hàng
* Top danh mục

# Tính năng nâng cao

* Realtime cập nhật đơn hàng.
* Export Excel/PDF.
* Import sản phẩm hàng loạt.
* Bulk actions.
* Dark Mode.
* Multi-language.
* Notification Center.
* Activity Timeline.

# Yêu cầu bảo mật

* JWT Access Token + Refresh Token.
* RBAC.
* CSRF Protection.
* Rate Limiting.
* IP Whitelist cho Super Admin.
* Two-Factor Authentication.
* Audit Logging đầy đủ.

# UI/UX Admin

* Sidebar cố định.
* Dashboard hiện đại.
* Responsive.
* Data Table mạnh mẽ.
* Form tối ưu thao tác.
* Loading Skeleton.
* Error Boundary.
* Toast Notification.

# Triển khai

* Customer Frontend: Vercel
* Admin Dashboard: Vercel
* BFF: Railway
* Backend: Render hoặc VPS
* Database: Supabase

# Cấu trúc Monorepo

apps/
├── storefront/
├── admin/
├── bff/
└── backend/

packages/
├── ui/
├── shared-types/
├── shared-utils/
└── config/

# Yêu cầu đầu ra

* Thiết kế database hoàn chỉnh.
* Xây dựng toàn bộ REST API.
* Tạo source code production-ready.
* Docker Compose đầy đủ.
* GitHub Actions CI/CD.
* Unit Test, Integration Test, E2E Test.
* Tài liệu Swagger.
* Seed dữ liệu mẫu.

Hãy xây dựng toàn bộ hệ thống với Clean Architecture, Domain-Driven Design, khả năng mở rộng lớn, hiệu năng cao, và bảo mật chuẩn enterprise.
