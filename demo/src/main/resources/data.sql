-- admin user: bcrypt of "Admin@1234"
INSERT INTO users (id, email, password) VALUES
  (1, 'admin@example.com', '$2a$10$u.cjJuJhHrsCUFsOAzOjWO3/izCQsdEW/kEHGTH1BlvWefM01ZIEu')
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, roles) VALUES (1, 'ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO products (sku, name, description, price, stock, image_url) VALUES
('SKU-COFFEE', 'Coffee Beans 1kg', 'Arabica blend', 12.50, 100, null),
('SKU-MUG', 'Ceramic Mug 350ml', 'White mug', 5.90, 200, null)
ON CONFLICT DO NOTHING;
