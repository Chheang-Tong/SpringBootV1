-- users
INSERT INTO users (first_name, last_name, surname, email, password, phone_number, gender, address)
VALUES
  ('Admin', 'User', 'User Admin', 'admin@example.com',
   '$2a$10$u.cjJuJhHrsCUFsOAzOjWO3/izCQsdEW/kEHGTH1BlvWefM01ZIEu',
   '012345678', 'Male', 'Phnom Penh'),
  ('User1', 'Demo', 'Demo User1', 'user1@example.com',
   '$2a$10$hZrb2ZbP9e7QxCkPsmvLCeYz5v8H3P7cElC7Y/M2Dke3yXdtV1gR2',
   '098765432', 'Female', 'Phnom Penh')
ON CONFLICT (email) DO NOTHING;

-- USER ROLES
INSERT INTO user_roles (user_id, roles) VALUES
(1, 'ADMIN'), (1, 'USER'),
(2, 'USER')
ON CONFLICT DO NOTHING;

-- PRODUCTS
INSERT INTO products (id, sku, name, description, price, stock, image_url) VALUES
(1, 'SKU-ESP', 'Espresso', 'Strong single shot coffee', 2.00, 100, NULL),
(2, 'SKU-LAT', 'Latte', 'Creamy coffee with milk', 3.00, 120, NULL),
(3, 'SKU-MOC', 'Mocha', 'Chocolate coffee blend', 3.50, 80, NULL)
ON CONFLICT (id) DO NOTHING;
