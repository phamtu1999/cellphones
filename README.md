# Cellphones E-Commerce Monorepo

Hệ thống thương mại điện tử cao cấp thiết kế theo mô hình Monorepo.

## Cấu trúc dự án

- `apps/backend`: Spring Boot 3 (Core Logic)
- `apps/bff`: NestJS (API Gateway/Aggregation)
- `apps/storefront`: Next.js 15 (Giao diện khách hàng)
- `apps/admin`: Next.js 15 (Trang quản trị)

## Công nghệ sử dụng

- **Backend**: Java 21, Spring Boot 3, JPA, Hibernate (validate mode), Redis, Kafka, MapStruct.
- **Frontend**: Next.js 15, TypeScript, TailwindCSS, Shadcn/UI.
- **Database**: Supabase PostgreSQL.

## Hướng dẫn chạy dự án

### 1. Chạy hạ tầng (Infrastructure)
Sử dụng Docker Compose để khởi chạy Database, Redis và Kafka:
```bash
docker-compose up -d postgres redis zookeeper kafka
```

### 2. Chạy Backend
```bash
cd apps/backend
mvn spring-boot:run
```

### 3. Chạy BFF
```bash
cd apps/bff
npm install
npm run start:dev
```

### 4. Chạy Frontend
```bash
cd apps/storefront # hoặc apps/admin
npm install
npm run dev
```

## API Standard
Tất cả API trả về định dạng:
```json
{
  "success": true,
  "message": "Success",
  "data": {},
  "timestamp": "2026-04-27T17:00:00Z"
}
```
