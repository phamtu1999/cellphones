-- Kích hoạt tiện ích tạo UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-------------------------------------------------------
-- NHÓM 1: SẢN PHẨM & KHO HÀNG
-------------------------------------------------------

-- 1. Danh mục
CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) UNIQUE NOT NULL,
    description TEXT,
    image_url TEXT,
    parent_id UUID REFERENCES categories(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 2. Thương hiệu
CREATE TABLE brands (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) UNIQUE NOT NULL,
    logo_url TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 3. Sản phẩm chính
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    brand_id UUID REFERENCES brands(id),
    category_id UUID REFERENCES categories(id),
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) UNIQUE NOT NULL,
    sku_base VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    base_price DECIMAL(15, 2) NOT NULL,
    thumbnail_url TEXT,
    images TEXT[],
    specifications JSONB, -- Lưu RAM, CPU, Pin...
    status VARCHAR(20) DEFAULT 'active',
    is_featured BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 4. Thuộc tính (Màu sắc, Dung lượng)
CREATE TABLE product_attributes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL
);

-- 5. Giá trị thuộc tính (Đỏ, 128GB)
CREATE TABLE product_attribute_values (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    attribute_id UUID REFERENCES product_attributes(id),
    value VARCHAR(50) NOT NULL
);

-- 6. Biến thể sản phẩm (SKUs)
CREATE TABLE product_skus (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    sku_code VARCHAR(50) UNIQUE NOT NULL,
    price_override DECIMAL(15, 2),
    stock_total INTEGER DEFAULT 0,
    attribute_values JSONB, -- {"color": "Titan", "storage": "128GB"}
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 7. Cửa hàng
CREATE TABLE stores (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    city VARCHAR(50),
    is_active BOOLEAN DEFAULT true
);

-- 8. Tồn kho chi tiết tại cửa hàng
CREATE TABLE store_inventory (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    store_id UUID REFERENCES stores(id),
    sku_id UUID REFERENCES product_skus(id),
    quantity INTEGER DEFAULT 0,
    UNIQUE(store_id, sku_id)
);

-------------------------------------------------------
-- NHÓM 2: KHÁCH HÀNG & THÀNH VIÊN (Smember)
-------------------------------------------------------

-- 9. Cấp độ Smember
CREATE TABLE member_tiers (
    id SERIAL PRIMARY KEY,
    tier_name VARCHAR(20) UNIQUE, -- S-Mem, S-Vip
    min_spending DECIMAL(15, 2),
    discount_percent FLOAT
);

-- 10. Hồ sơ người dùng
CREATE TABLE profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    full_name VARCHAR(150),
    phone_number VARCHAR(15),
    avatar_url TEXT,
    tier_id INTEGER REFERENCES member_tiers(id) DEFAULT 1,
    total_spent DECIMAL(15, 2) DEFAULT 0,
    role VARCHAR(20) DEFAULT 'CUSTOMER',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 11. Yêu thích
CREATE TABLE wishlists (
    user_id UUID REFERENCES profiles(id) ON DELETE CASCADE,
    product_id UUID REFERENCES products(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, product_id)
);

-------------------------------------------------------
-- NHÓM 3: BÁN HÀNG & KHUYẾN MÃI
-------------------------------------------------------

-- 12. Chương trình khuyến mãi
CREATE TABLE promotions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    discount_type VARCHAR(20), -- PERCENT, FIXED
    discount_value DECIMAL(15, 2),
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    is_active BOOLEAN DEFAULT true
);

-- 13. Áp dụng KM
CREATE TABLE promotion_applies (
    promotion_id UUID REFERENCES promotions(id),
    applied_to_type VARCHAR(20), -- PRODUCT, CATEGORY
    applied_to_id UUID,
    PRIMARY KEY (promotion_id, applied_to_id)
);

-- 14. Đơn hàng
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID REFERENCES profiles(id),
    order_number VARCHAR(20) UNIQUE NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    shipping_address TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 15. Chi tiết đơn hàng
CREATE TABLE order_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID REFERENCES orders(id) ON DELETE CASCADE,
    sku_id UUID REFERENCES product_skus(id),
    quantity INTEGER NOT NULL,
    price_at_purchase DECIMAL(15, 2) NOT NULL
);

-- 16. Mã giảm giá (Coupons)
CREATE TABLE coupons (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    discount_value DECIMAL(15, 2) NOT NULL,
    usage_limit INTEGER,
    end_date TIMESTAMP WITH TIME ZONE
);

-------------------------------------------------------
-- NHÓM 4: HẬU CẦN & BẢO HÀNH (CareS)
-------------------------------------------------------

-- 17. Định danh thiết bị (IMEI/Serial)
CREATE TABLE product_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sku_id UUID REFERENCES product_skus(id),
    serial_number VARCHAR(100) UNIQUE NOT NULL,
    status VARCHAR(20) DEFAULT 'IN_STOCK'
);

-- 18. Phiếu bảo hành
CREATE TABLE warranty_tickets (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_item_id UUID REFERENCES product_items(id),
    user_id UUID REFERENCES profiles(id),
    description TEXT,
    status VARCHAR(20) DEFAULT 'RECEIVED',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- 19. Phí ship
CREATE TABLE shipping_rates (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    province_code VARCHAR(10),
    base_fee DECIMAL(12, 2)
);

-- 20. Đơn vị vận chuyển
CREATE TABLE shipping_carriers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100),
    is_active BOOLEAN DEFAULT true
);

-------------------------------------------------------
-- NHÓM 5: NỘI DUNG & SEO
-------------------------------------------------------

-- 21. SEO Metadata
CREATE TABLE seo_metadata (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    entity_type VARCHAR(20), -- PRODUCT, CATEGORY
    entity_id UUID,
    meta_title VARCHAR(255),
    meta_description TEXT
);

-- 22. Đánh giá
CREATE TABLE reviews (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id UUID REFERENCES products(id),
    user_id UUID REFERENCES profiles(id),
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-------------------------------------------------------
-- NHÓM 6: QUẢN TRỊ
-------------------------------------------------------

-- 23. Phân quyền Admin
CREATE TABLE role_permissions (
    role_name VARCHAR(20), -- ADMIN, SALES, WAREHOUSE
    permission_code VARCHAR(50), -- PRODUCT_EDIT, ORDER_APPROVE
    PRIMARY KEY (role_name, permission_code)
);