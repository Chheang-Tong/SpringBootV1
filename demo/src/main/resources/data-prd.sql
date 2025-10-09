-- Minimal admin
INSERT INTO users (id, email, password) VALUES
(1, 'admin@company.com', '$2a$10$u.cjJuJhHrsCUFsOAzOjWO3/izCQsdEW/kEHGTH1BlvWefM01ZIEu')
ON CONFLICT (id) DO NOTHING;

INSERT INTO user_roles (user_id, roles) VALUES (1, 'ADMIN') ON CONFLICT DO NOTHING;

-- Starter product
INSERT INTO products (id, sku, name, description, price, stock, image_url) VALUES
(1, 'SKU-PRM', 'Premium Coffee', 'High-quality arabica beans', 5.00, 200, NULL)
ON CONFLICT (id) DO NOTHING;
