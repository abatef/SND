-- 1. Users Table
CREATE TABLE Users
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100)        NOT NULL,
    email        VARCHAR(100) UNIQUE NOT NULL,
    phone        VARCHAR(20),
    home_address VARCHAR(30),
    address_line VARCHAR(30),
    city         VARCHAR(30),
    state        VARCHAR(30),
    country      VARCHAR(30),
    zip_code     CHAR(10),
    location     GEOMETRY(POINT, 4326), -- Use PostGIS for spatial data support
    preferences  JSONB,
    wishlist_id  INT UNIQUE,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

alter table Users add COLUMN role varchar(10) check ( role in ('USER', 'ADMIN', 'MANAGER', 'OWNER') );

CREATE INDEX idx_user_email on Users(email);

-- 2. Stores Table
CREATE TABLE Stores
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    address      VARCHAR(255),
    address_line VARCHAR(30),
    city         VARCHAR(30),
    state        VARCHAR(30),
    country      VARCHAR(30),
    zip_code     CHAR(10),
    location     GEOMETRY(POINT, 4326),
    rating       DECIMAL(3, 2) CHECK ( rating BETWEEN 0 AND 5),
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Products Table
CREATE TABLE Products
(
    id             SERIAL PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    category       VARCHAR(50),
    description    TEXT,
    image_url      VARCHAR(255),
    barcode        VARCHAR(50) UNIQUE,
    average_rating DECIMAL(3, 2) CHECK ( average_rating between 0 AND 5),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_prod_bcode on Products(barcode);

-- 4. StoreProduct Table (for Store-Product Relationship)
CREATE TABLE StoreProduct
(
    store_id     INT REFERENCES Stores (id) ON DELETE CASCADE,
    product_id   INT REFERENCES Products (id) ON DELETE CASCADE,
    quantity     INT       DEFAULT 0,
    price        DECIMAL(10, 2) NOT NULL,
    discount     DECIMAL(5, 2) CHECK ( discount BETWEEN 0 AND 100),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (store_id, product_id) -- Each product can appear only once per store
);


-- 5. Wishlist Table
CREATE TABLE Wishlist
(
    id      SERIAL PRIMARY KEY,
    user_id INT UNIQUE REFERENCES Users (id) ON DELETE CASCADE
);

CREATE TABLE WishlistItem
(
    wishlist_id INT references Wishlist (id),
    product_id  INT references Products (id),
    PRIMARY KEY (wishlist_id, product_id)
);

-- 6. Reviews Table
CREATE TABLE Reviews
(
    review_id  SERIAL PRIMARY KEY,
    user_id    INT REFERENCES Users (id) ON DELETE SET NULL,
    product_id INT REFERENCES Products (id) ON DELETE CASCADE,
    store_id   INT REFERENCES Stores (id) ON DELETE CASCADE,
    rating     INT CHECK (rating >= 1 AND rating <= 5),
    comment    TEXT,
    timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 7. Notifications Table
CREATE TABLE Notifications
(
    notification_id SERIAL PRIMARY KEY,
    user_id         INT REFERENCES Users (id) ON DELETE CASCADE,
    message         TEXT NOT NULL,
    type            VARCHAR(50), -- E.g., "Price Alert", "Availability Alert"
    read_status     BOOLEAN   DEFAULT FALSE,
    timestamp       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Ownership Table (for Store Owners relationship)
CREATE TABLE StoreManager
(
    user_id  INT REFERENCES Users (id) ON DELETE CASCADE,
    store_id INT REFERENCES Stores (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, store_id) -- Many-to-many relationship
);

alter table StoreManager add column userRole varchar(8) check ( userRole in ('OWNER', 'MANAGER', 'ADMIN') );


